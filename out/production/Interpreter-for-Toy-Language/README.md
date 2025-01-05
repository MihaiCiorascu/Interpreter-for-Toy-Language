# Interpreter-for-Toy-Language
Our mini interpreter uses three main structures:


### Execution Stack (ExeStack)
A stack of statements to execute the current program.

### Table of Symbols (SymTable)
A table that keeps the variables' values.

### Output (Out)
Keeps all messages printed by the toy program.


All these three main structures denote the program state (**PrgState**).  
Our interpreter can execute multiple programs, each with a different **PrgState** (different **ExeStack**, **SymTable**, and **Out**).

At the beginning:
- **ExeStack** contains the original program.
- **SymTable** and **Out** are empty.

During evaluation:
- **ExeStack** holds the remaining program to evaluate.
- **SymTable** contains evaluated variables with their assigned values.
- **Out** accumulates printed values.
