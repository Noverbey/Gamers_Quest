Name: Nathanie Overbey

I had no discussions with anybody about this project

This file contains all the files from project 3 with the addition of:
FuncDecl.java this file contains the parse, print, semantic and execute functions for the funcDecl nonterminal of the grammar
Formulas.java this file contains parse, print, semantic, execute, and executeOut functions of the formals nonterminal of the grammar
FuncCall.java this file contains parse print, semantic, and execute functions for the func-call nonterminal in the grammar

No special features or comments

The way the call stack is implemented is that we enter a completely new scope so the program can only see the formal params and global variables

I first tested the interpreter by writing parts of the parse and print functions for each program then checking to see
if everything was being correctly stored. After I was sure that the parse tree was being created correctly I devloped
the execute functions and tested them on the correct .code files. I finally, created and tested the semantic functions
and used the error .code files to check them.