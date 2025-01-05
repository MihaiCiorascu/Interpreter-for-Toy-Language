package src.java.controller;

import src.java.adt.myDictionary.MyDictionary;
import src.java.adt.myDictionary.MyIDictionary;
import src.java.adt.myHeap.MyIHeap;
import src.java.exceptions.*;
import src.java.exceptions.AdtException;
import src.java.model.ProgramState;
import src.java.model.types.IType;
import src.java.model.values.IValue;
import src.java.model.values.RefValue;
import src.java.repository.IRepository;

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
    private boolean typeChecked;

    public Controller(IRepository repository){
        this.repository = repository;
        this.executor = Executors.newFixedThreadPool(2);
        this.typeChecked = false;
    }

    public IRepository getRepo() {
        return repository;
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

    public void oneStepForAllPrograms(List<ProgramState> programList) throws IControllerException, InterruptedException {
        programList.forEach(prg -> repository.logPrgStateExec(prg));

        List<Callable<ProgramState>> callList = programList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>) (() -> {
                    try {
                        return p.oneStep();
                    } catch (MyException e) {
                        p.setNotCompleted(false);
                        System.out.println(e.getMessage());
                        return null;
                    }
                }))
                .collect(Collectors.toList());

        List<ProgramState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(p -> p != null && p.isNotCompleted())
                .collect(Collectors.toList());

        programList.addAll(newPrgList);

        programList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (IRepositoryException e) {
                System.out.println(e.getMessage());
            }
        });

        repository.setPrgStates(programList);
    }

    private void typeCheck() {
        ProgramState prg = repository.getPrgStates().get(0);
        MyIDictionary<String, IType> typeEnv = new MyDictionary<>();
        prg.getOriginalProgram().typeCheck(typeEnv);
        typeChecked = true;
    }

    public void allSteps() throws IControllerException, InterruptedException {
        if (!typeChecked) {
            typeCheck();
        }

        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> prgList = removeCompletedPrg(repository.getPrgStates());

        while(!prgList.isEmpty()){
            List<IValue> symTables = prgList.stream()
                    .flatMap(programState -> programState.getSymTable().getContent().values().stream()).distinct().toList();
            Map<Integer, IValue> newHeap = this.safeGarbageCollector(this.getUsedAddresses(symTables), prgList.getFirst().getHeap().getContent());
            prgList.getFirst().getHeap().setContent(newHeap);


            this.oneStepForAllPrograms(prgList);
            prgList = removeCompletedPrg(repository.getPrgStates());
        }
        executor.shutdownNow();
        repository.setPrgStates(prgList);
    }
}