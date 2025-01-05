package src.java.view.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import src.java.adt.myDictionary.MyDictionary;
import src.java.adt.myHeap.MyHeap;
import src.java.adt.myList.MyList;
import src.java.adt.myStack.MyStack;
import src.java.controller.Controller;
import src.java.model.ProgramState;
import src.java.model.expressions.*;
import src.java.model.statements.*;
import src.java.model.types.BoolType;
import src.java.model.types.IntType;
import src.java.model.types.RefType;
import src.java.model.types.StringType;
import src.java.model.values.BoolValue;
import src.java.model.values.IntValue;
import src.java.model.values.StringValue;
import src.java.repository.IRepository;
import src.java.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class ProgramListController {

    @FXML
    private ListView<String> programListView;
    private List<IStatement> programs;

    @FXML
    public void initialize() {
        programs = new ArrayList<>();

        // Example 1: int v; v=2; Print(v)
        IStatement prog1 = new CompoundStatement(
                new VarDeclStatement("v", new IntType()),
                new CompoundStatement((
                        new AssignmentStatement("v", new ValueExpression(new IntValue(2)))),
                        new PrintStatement(new VariableExpression("v"))));

        programs.add(prog1);

        // Example 2: int a; int b; a=2+3*5; b=a+1; Print(b)
        IStatement prog2 = new CompoundStatement(
                new VarDeclStatement("a", new IntType()), new CompoundStatement(
                new VarDeclStatement("b", new IntType()),
                new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression('+',
                        new ValueExpression(new IntValue(2)), new ArithmeticExpression('*',
                        new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                        new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression('+',
                                new VariableExpression("a"), new ValueExpression(new IntValue(1)))),
                                new PrintStatement(new VariableExpression("b"))))));

        programs.add(prog2);

        // Example 3: bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
        IStatement prog3 = new CompoundStatement(new VarDeclStatement("a", new BoolType()), new CompoundStatement(
                new VarDeclStatement("v", new IntType()), new CompoundStatement(new AssignmentStatement("a",
                new ValueExpression(new BoolValue(true))), new CompoundStatement(new IfStatement(
                new VariableExpression("a"), new AssignmentStatement("v", new ValueExpression(new IntValue(2)
        )), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(
                new VariableExpression("v"))))));

        programs.add(prog3);

        // Example 4: string varf; varf = "test.in"; openRFile(varf); int varc; readFile(varf, varc);
                // Print(varc); readFile(varf, varc); Print(varc); closeRFile(varf);
        IStatement prog4 = new CompoundStatement(
                        new VarDeclStatement("varf", new StringType()),
                        new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                                new CompoundStatement(new OpenRFileStatement(new VariableExpression("varf")),
                                        new CompoundStatement(new VarDeclStatement("varc", new IntType()),
                                                new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                new CompoundStatement(new ReadFile(new VariableExpression("varf"), "varc"),
                                                                        new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                                new CloseRFileStatement(new VariableExpression("varf"))))))))));

        programs.add(prog4);

        // Example 5: Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        IStatement prog5 =  new CompoundStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new HeapNewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                new CompoundStatement(
                                        new HeapNewStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new PrintStatement(new VariableExpression("a")))))));
        programs.add(prog5);

        // Example 6: Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        IStatement prog6 = new CompoundStatement(
                        new VarDeclStatement("v", new RefType(new IntType())),
                        new CompoundStatement(
                                new HeapNewStatement("v", new ValueExpression(new IntValue(20))),
                                new CompoundStatement(
                                        new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                        new CompoundStatement(
                                                new HeapNewStatement("a", new VariableExpression("v")),
                                                new CompoundStatement(
                                                        new PrintStatement(new RefExpression(new VariableExpression("v"))),
                                                        new PrintStatement(new ArithmeticExpression('+',
                                                                new RefExpression(new RefExpression(new VariableExpression("a"))),
                                                                new ValueExpression(new IntValue(5)))))))));

        programs.add(prog6);

        // Example 7: Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        IStatement prog7 =  new CompoundStatement(
                new VarDeclStatement("v", new RefType(new IntType())),
                new CompoundStatement(
                        new HeapNewStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new RefExpression(new VariableExpression("v"))),
                                new CompoundStatement(
                                        new HeapWriteStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression('+',
                                                new RefExpression(new VariableExpression("v")),
                                                new ValueExpression(new IntValue(5))))))));

        programs.add(prog7);

        // Example 8: Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStatement prog8 = new CompoundStatement(
                        new VarDeclStatement("v", new RefType(new IntType())),
                        new CompoundStatement(
                                new HeapNewStatement("v", new ValueExpression(new IntValue(20))),
                                new CompoundStatement(
                                        new VarDeclStatement("a", new RefType(new RefType(new IntType()))),
                                        new CompoundStatement(
                                                new HeapNewStatement("a", new VariableExpression("v")),
                                                new CompoundStatement(
                                                        new HeapNewStatement("v", new ValueExpression(new IntValue(30))),
                                                        new PrintStatement(new RefExpression(new RefExpression(new VariableExpression("a")))))))));

        programs.add(prog8);

        // Example 9: int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStatement prog9 =  new CompoundStatement(
                new VarDeclStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignmentStatement("v", new ArithmeticExpression('-', new VariableExpression("v"), new ValueExpression(new IntValue(1))))
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );

        programs.add(prog9);

        // Example 10: int v; Ref int a; v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))
        IStatement prog10 = new CompoundStatement(new VarDeclStatement("v", new IntType()),
                new CompoundStatement(new VarDeclStatement("a", new RefType(new IntType())),
                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new HeapNewStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(new ForkStatement(
                                                new CompoundStatement(new HeapWriteStatement("a", new ValueExpression(new IntValue(30))),
                                                        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new RefExpression(new VariableExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new PrintStatement(new RefExpression(new VariableExpression("a"))))
                                        )
                                )
                        )
                )
        );

        programs.add(prog10);

        // Example 11: Ref(int) a; int v; new(a,10); fork(v=20; fork(wH(a, 40); print(rH(a));); print(v);); v=30; print(v); print(rH(a));
        IStatement prog11 =  new CompoundStatement(
                new VarDeclStatement("a", new RefType(new IntType())),
                new CompoundStatement(
                        new VarDeclStatement("v", new IntType()),
                        new CompoundStatement(
                                new HeapNewStatement("a", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new ForkStatement(
                                                new CompoundStatement(
                                                        new AssignmentStatement("v", new ValueExpression(new IntValue(20))),
                                                        new CompoundStatement(
                                                                new ForkStatement(
                                                                        new CompoundStatement(
                                                                                new HeapWriteStatement("a", new ValueExpression(new IntValue(40))),
                                                                                new PrintStatement(new RefExpression(new VariableExpression("a")))
                                                                        )
                                                                ),
                                                                new PrintStatement(new VariableExpression("v"))
                                                        )
                                                )
                                        ),
                                        new CompoundStatement(
                                                new AssignmentStatement("v", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new RefExpression(new VariableExpression("a"))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        programs.add(prog11);

        // Example 12: string varf; varf="test.in"; openRFile(varf); fork(int varc; readFile(varf, varc); print(varc);); int varc; readFile(varf, varc); print(varc); closeRFile(varf);
        IStatement prog12 =  new CompoundStatement(
                new VarDeclStatement("varf", new StringType()),
                new CompoundStatement(
                        new AssignmentStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(
                                new OpenRFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(
                                        new ForkStatement(
                                                new CompoundStatement(
                                                        new VarDeclStatement("varc", new IntType()),
                                                        new CompoundStatement(
                                                                new ReadFile(new VariableExpression("varf"), "varc"),
                                                                new PrintStatement(new VariableExpression("varc"))
                                                        )
                                                )
                                        ),
                                        new CompoundStatement(
                                                new VarDeclStatement("varc", new IntType()),
                                                new CompoundStatement(
                                                        new ReadFile(new VariableExpression("varf"), "varc"),
                                                        new CompoundStatement(
                                                                new PrintStatement(new VariableExpression("varc")),
                                                                new CloseRFileStatement(new VariableExpression("varf"))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        programs.add(prog12);

        // Example 13: int v; int v;
        IStatement prog13 = new CompoundStatement(new VarDeclStatement("v", new IntType()), new VarDeclStatement("v", new IntType()));

        programs.add(prog13);

        // Convert programs to their string representations
        ObservableList<String> programStrings = FXCollections.observableArrayList();
        for (IStatement statement: programs) {
            programStrings.add(statement.toString());
        }

        programListView.setItems(programStrings);
    }

    @FXML
    private void executeProgram() {
        String selectedProgram = programListView.getSelectionModel().getSelectedItem();
        if (selectedProgram == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("No program selected!");
            alert.showAndWait();
            return;
        }

        int index = programListView.getSelectionModel().getSelectedIndex();
        IStatement program = programs.get(index);

        try {
            ProgramState programState = new ProgramState(
                    new MyStack<>(),
                    new MyDictionary<>(),
                    new MyList<>(),
                    program,
                    new MyDictionary<>(),
                    new MyHeap<>());

            IRepository repository= new Repository(programState, "log" + (index + 1) + ".txt");
            Controller controller = new Controller(repository);

            // Create and show the main window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/src/resources/fxml/MainWindow.fxml"));
            Parent root = loader.load();
            MainWindowController mainWindowController = loader.getController();
            mainWindowController.setController(controller);

            Stage stage = new Stage();
            stage.setTitle("Program Execution");
            stage.setScene(new Scene(root, 800, 600));
            stage.show();

            // Close the program selection window
            ((Stage) programListView.getScene().getWindow()).close();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Error creating program state: " + e.getMessage());
            alert.showAndWait();
        }
    }
}
