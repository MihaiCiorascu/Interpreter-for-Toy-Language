package src.java.adt.myBinaryTree;

import src.java.model.statements.CompoundStatement;
import src.java.model.statements.IStatement;

public class BinaryTreeBuilder implements ITreeBuilder<IStatement>{

    @Override
    public MyBinaryTree<IStatement> buildTree(IStatement program) {
        if(!(program instanceof CompoundStatement)) {
            return new MyBinaryTree<>(program);
        }

        CompoundStatement compoundStatement = (CompoundStatement) program;
        IStatement leftStmt = compoundStatement.getFirstStatement();
        IStatement rightStmt = compoundStatement.getSecondStatement();

        MyBinaryTree<IStatement> leftSubTree = new MyBinaryTree<>(leftStmt);
        MyBinaryTree<IStatement> rightSubTree = new MyBinaryTree<>(rightStmt);

        return new MyBinaryTree<>(program, leftSubTree, rightSubTree);
    }
}
