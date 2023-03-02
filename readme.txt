A context-free grammar is in Chomsky Normal form if every production is of the form
A → BC
A → a
Where a denotes a terminal and A, B, C denote variables where neither B nor C is the start variable.
In addition, there is a production
S → ε
if and only if ε belongs to the language.
In your assignment you should convert Context Free Grammar (from CFG.txt) to Chomsky Normal Form.
Save CFG.txt to your debug folder, not write the path name in your code. Then you should write each
eliminate state on the screen. At the end CNF should be given to the user.
You can implement your solution in C# or java.
CFG.txt (E is alphabet, € is empty string, - is )
E=0,1
S-A1A
A-0B0|€
B-A|10
Output of the program
CFG Form
S-A1A
A-0B0|€
B-A|10
Eliminate €
………………………….
………………………..
Eliminate unit production
………………………..
………………………..
Eliminate terminals
………………………..
………………………..
Break variable strings longer than 2
………………………..
………………………..
CNF
S-AC|WA|AW|1
A-ZD|ZE|ZZ
B-WZ
C-WA
D-BZ
E-AZ
W-1
Z-0
