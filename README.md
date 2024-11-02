# Interpreter-for-Toy-Language
Our mini interpreter uses three main structures:
–
Execution Stack (ExeStack): a stack of statements to execute the currrent program
–
Table of Symbols (SymTable): a table which keeps the variables values
–
Output (Out): that keeps all the mesages printed by the toy program
All these three main structures denote the program state (PrgState). Our interpreter can execute multiple programs but for each of them use a different PrgState structures (that means different ExeStack, SymTable and Out structures).
At the beginning, ExeStack contains the original program, and SymTable and Out are empty. After the evaluation has started, ExeStack contains the remaining part of the program that must be evaluated, SymTable contains the variables (from the variable declarations statements evaluated so far) with their assigned values, and Out contains the values printed so far.
