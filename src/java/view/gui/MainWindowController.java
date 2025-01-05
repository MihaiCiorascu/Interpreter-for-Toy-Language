package src.java.view.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import src.java.adt.myDictionary.MyDictionary;
import src.java.adt.myList.MyList;
import src.java.exceptions.AdtException;
import src.java.model.ProgramState;
import src.java.model.values.IValue;
import src.java.model.statements.IStatement;
import src.java.controller.Controller;
import src.java.adt.myHeap.MyIHeap;
import src.java.adt.myStack.MyIStack;
import src.java.adt.myStack.MyStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import src.java.model.values.StringValue;
import java.lang.reflect.Field;

public class MainWindowController {
    private Controller controller;
    private ProgramState selectedProgram;

    @FXML
    private TextField numberOfPrgStatesTextField;

    @FXML
    private TableView<Map.Entry<Integer, IValue>> heapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, String> heapAddressColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, IValue>, String> heapValueColumn;

    @FXML
    private ListView<String> outputListView;

    @FXML
    private ListView<String> fileTableListView;

    @FXML
    private ListView<Integer> prgStateIdentifiersListView;

    @FXML
    private ListView<String> exeStackListView;

    @FXML
    private TableView<Map.Entry<String, IValue>> symTableView;

    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> symTableVarNameColumn;

    @FXML
    private TableColumn<Map.Entry<String, IValue>, String> symTableValueColumn;

    @FXML
    private Button runOneStepButton;

    @FXML
    public void initialize() {
        heapAddressColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey().toString()));
        heapValueColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        symTableVarNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        symTableValueColumn
                .setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getValue().toString()));

        prgStateIdentifiersListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedProgram = controller.getRepo().getPrgStates().stream()
                                .filter(p -> p.getId() == newValue)
                                .findFirst()
                                .orElse(null);
                        populateExeStack();
                        populateSymTable();
                    }
                }
        );
    }

    public void setController(Controller controller) {
        this.controller = controller;
        populateAll();
    }

    private void populateAll() {
        populateHeapTable();
        populateOutput();
        populateFileTable();
        populatePrgStateIdentifiers();
        populateNumberOfPrgStates();

        if (selectedProgram == null && !controller.getRepo().getPrgStates().isEmpty()) {
            selectedProgram = controller.getRepo().getPrgStates().get(0);
            prgStateIdentifiersListView.getSelectionModel().select(0);
        }

        if (selectedProgram != null) {
            populateExeStack();
            populateSymTable();
        }
    }

    private void populateNumberOfPrgStates() {
        numberOfPrgStatesTextField.setText(String.valueOf(controller.getRepo().getPrgStates().size()));
    }

    private void populateHeapTable() {
        MyIHeap<Integer, IValue> heap = controller.getRepo().getPrgStates().get(0).getHeap();
        ObservableList<Map.Entry<Integer, IValue>> heapEntries = FXCollections.observableArrayList(
                heap.getContent().entrySet());
        heapTableView.setItems(heapEntries);
    }

    private void populateOutput() {
        ObservableList<String> output = FXCollections.observableArrayList();
        if (!controller.getRepo().getPrgStates().isEmpty()) {
            MyList<IValue> outList = (MyList<IValue>) controller.getRepo().getPrgStates().get(0).getOutput();
            try {
                Field listField = MyList.class.getDeclaredField("elements");
                listField.setAccessible(true);
                @SuppressWarnings("unchecked")
                List<IValue> list = (List<IValue>) listField.get(outList);
                output.addAll(list.stream()
                        .map(Object::toString)
                        .collect(Collectors.toList()));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error accessing output list: " + e.getMessage());
                alert.showAndWait();
            }
        }
        outputListView.setItems(output);
    }

    private void populateFileTable() {
        ObservableList<String> files = FXCollections.observableArrayList();
        if (!controller.getRepo().getPrgStates().isEmpty()) {
            MyDictionary<StringValue, BufferedReader> fileTable = (MyDictionary<StringValue, BufferedReader>) controller.getRepo()
                    .getPrgStates().get(0).getFileTable();
            files.addAll(fileTable.getValues().stream()
                    .filter(br -> br != null)
                    .map(br -> br.toString())
                    .collect(Collectors.toList()));
        }
        fileTableListView.setItems(files);
    }

    private void populatePrgStateIdentifiers() {
        ObservableList<Integer> identifiers = FXCollections.observableArrayList();
        identifiers.addAll(controller.getRepo().getPrgStates().stream()
                .map(ProgramState::getId)
                .collect(Collectors.toList()));
        prgStateIdentifiersListView.setItems(identifiers);
    }

    private void populateExeStack() {
        ObservableList<String> exeStack = FXCollections.observableArrayList();
        if (selectedProgram  != null) {
            List<String> stackElements = new ArrayList<>();
            MyIStack<IStatement> stack = selectedProgram.getExeStack();
            MyIStack<IStatement> tempStack = new MyStack<>();

            try {
                // Copy elements to temporary stack and collect their string representations
                while (!stack.isEmpty()) {
                    IStatement stmt = stack.pop();
                    stackElements.add(0, stmt.toString());
                    tempStack.push(stmt);
                }

                // Restore the original stack
                while (!tempStack.isEmpty()) {
                    stack.push(tempStack.pop());
                }
            } catch (AdtException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error accessing stack: " + e.getMessage());
                alert.showAndWait();
            }

            exeStack.addAll(stackElements);
        }
        exeStackListView.setItems(exeStack);
    }

    private void populateSymTable() {
        ObservableList<Map.Entry<String, IValue>> symTableEntries = FXCollections.observableArrayList();
        if (selectedProgram != null) {
            MyDictionary<String, IValue> symTable = (MyDictionary<String, IValue>) selectedProgram.getSymTable();
            try {
                Field dictField = MyDictionary.class.getDeclaredField("dictionary");
                dictField.setAccessible(true);
                @SuppressWarnings("unchecked")
                Map<String, IValue> dict = (Map<String, IValue>) dictField.get(symTable);
                symTableEntries.addAll(dict.entrySet());
            } catch (NoSuchFieldException | IllegalAccessException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error accessing symbol table: " + e.getMessage());
                alert.showAndWait();
            }
        }
        symTableView.setItems(symTableEntries);
    }

    @FXML
    private void runOneStep() {
        if (controller == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No program selected!");
            alert.showAndWait();
            return;
        }

        List<ProgramState> prgList = controller.removeCompletedPrg(controller.getRepo().getPrgStates());
        if (prgList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Nothing left to execute!");
            alert.showAndWait();
            return;
        }

        try {
            controller.oneStepForAllPrograms(prgList);
            populateAll();
        } catch (InterruptedException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
