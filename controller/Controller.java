package controller;

import exceptions.*;
import exceptions.AdtException;
import model.ProgramState;
import model.values.IValue;
import model.values.RefValue;
import repository.IRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Controller {
    private IRepository repository;
    private ExecutorService executor;

    public Controller(IRepository repository){
        this.repository = repository;
    }


    public Map<Integer, IValue> safeGarbageCollector(List<Integer> usedAddresses, Map<Integer, IValue> heap) {
        Map<Integer, IValue> inUseFromSymTable = heap.entrySet().stream()
                .filter(e -> usedAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        List<Integer> referentialValuesInHeap = this.getUsedAddresses(inUseFromSymTable.values());

        return Stream.concat(inUseFromSymTable.entrySet().stream(),
                        heap.entrySet().stream()
                                .filter(e->referentialValuesInHeap.contains(e.getKey())))
                .distinct()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<Integer> getUsedAddresses(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                })
                .collect(Collectors.toList());
    }

    public List<ProgramState> removeCompletedPrg(List<ProgramState> inPrgList) {
        return inPrgList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    void oneStepForAllPrograms(List<ProgramState> programList) throws IControllerException, InterruptedException {
        programList.forEach(prg -> repository.logPrgStateExec(prg));

        List<Callable<ProgramState>> callList = programList.stream()
                .map((ProgramState p)-> (Callable<ProgramState>)(p::oneStep)).toList();

        List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
                .map(future->{try{return future.get();} catch (InterruptedException | ExecutionException | IRepositoryException | AdtException |
                                                               IStatementException | IExpressionException e)
                {throw new IControllerException(e.toString());}}).filter(Objects::nonNull).toList();

        programList.addAll(newPrgList);
        programList.forEach(prg -> repository.logPrgStateExec(prg));
        repository.setPrgStates(programList);
    }

    public void allSteps() throws IControllerException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> prgList = removeCompletedPrg(repository.getPrgStates());
        while(!prgList.isEmpty()){
            List<IValue> symTables = prgList.stream().flatMap(programState -> programState.getSymTable().getContent().values().stream()).distinct().toList();
            Map<Integer, IValue> newHeap = this.safeGarbageCollector(this.getUsedAddresses(symTables), prgList.getFirst().getHeap().getContent());
            prgList.getFirst().getHeap().setContent(newHeap);

            this.oneStepForAllPrograms(prgList);
            prgList = removeCompletedPrg(repository.getPrgStates());
        }
        executor.shutdownNow();
        repository.setPrgStates(prgList);
    }
}