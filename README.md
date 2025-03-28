
### **National Autonomous University of Mexico** <br>
### **School of Engineering**
### **Compilers**

**TEAM MEMBERS:**  
- 320068234  
- 320239126  
- 320257599  
- 320278107  
- 117002029  

**Group:** 05  
**Semester:** 2025-II  

**Mexico City, Mexico. March 2025.**


# Lexer-Compilers-Team2
Repo for the Lexer of Compliers subject (School of Engineering, 2025-2 semester)

## Introduction
#### Problem statement.
> Create a lexical analyzer using the concepts learned in the theoretical sessions, capable of identifying the basic lexical elements (tokens) and classifying them according to the type of token they belong to.

#### Motivation.
> The creation of our lexical analyzer is a fundamental step, as it allows us to convert the input text into tokens and classify them into predefined categories, making it easier for the compiler to interpret them. Since lexical analysis is the first phase in the process of building a compiler, understanding how it works will provide us with the necessary foundation to develop the subsequent stages, enabling us to progress towards the complete creation of our compiler.

#### Objectives.
> Develop an initial idea of a Context-Free Grammar (CFG) that applies right recursion and left factoring, with the purpose of creating a lexical analyzer capable of identifying and classifying the different types of tokens present in a text string.

## Theoretical framework 
_Lexical Analyzer:_ A lexical analyzer is a program that converts the input text into tokens and classifies these tokens into predefined categories. The lexical analyzer plays a crucial role in the analysis phase, as it provides the tokens to the parser to facilitate syntax analysis.

_Token:_ A token is a sequence of characters grouped into a single entity. Each token represents a set of character sequences that convey a specific meaning.\
Classification:
* Keywords
* Identifiers
* Literals
* Operators
* Punctuation
* Special characters
*  Constants
  
_Lexical Errors:_ A lexical error happens when the lexer finds an invalid sequence of characters that cannot be classified as an actual token. These errors tend to happen due to exceeding length of numeric constants, identifiers that are way too long, illegal characters, and others.

_Finite Automata:_ A finite automata is an abstract machine that recognizes patterns by processing symbols step by step, transitioning between a finite number of states. It determines if this input should be accepted or rejected based on the final state it reaches.

_Finite Automata in Token Recognition:_ Because the lexical structure of most programming languages can be described using a regular language, lexical analyzers often rely on finite automata to identify valid tokens. This is achieved by defining regular expressions for all possible tokens, and then transforming them into a finite automaton (usually a deterministic finite automaton).

## Body

As the first step, we decided to develop automata to represent each type of token using the JFLAP program. This decision allowed us to establish a solid foundation and perform a general analysis to understand how each token was constructed in our lexical analyzer. Using JFLAP facilitated the visualization of the created automata, enabling us to validate that each one generated the correct tokens. Additionally, the tool provides an option to test input strings, which allowed us to verify the correct functionality of each automaton. Below are the images of the created automata:

> Keywords Automata
![Keywords Automata](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/AutomatasJFLAP/KeywordsAutomata.jpg)

> Identifiers Automata
![Identifiers Automata](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/AutomatasJFLAP/IdentifiersAutomata.jpg) <br>
_The states q4, q5, and q6 could have been included in the same state as q3; however, to improve understanding, they were implemented as separate states._ <br>

> Operators Automata <br>
![Operators Automata](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/AutomatasJFLAP/OperatorsAutomata.png) 

> Puntuactors Automata <br>
![Puntuactors Automata](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/AutomatasJFLAP/PuntuactorsAutomata.jpg) <br>
_Note: Brackets also function as punctuation symbols, but JFLAP does not recognize them, so they were not included in the automata. However, they were considered in the implementation of our lexical analyzer._ <br>

> Constant Automata <br>
![Constant Automata](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/AutomatasJFLAP/ConstantAutomata.png)

> Literals Automata <br>
![Literals Automata](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/AutomatasJFLAP/LiteralsAutomata.jpg)

After the automata were defined and tested, we derived the regex (regular expressions) for each type of token based on their respective DFA (Deterministic Finite Automata. Allowing us to ensure that our logic remained consistent with the designed automata and adhered to the corresponding lexical rules.


> Identifiers Regex:<br>
![Identifiers Regex](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/Regex/IdentifiersRegex.png)<br>

> Operators Regex:<br>
![Operators Regex](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/Regex/OperatorsRegex.jpg)<br>

> Puntuactors Regex:<br>
![Puntuactors Regex](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/Regex/ConstantRegex.png)<br>
> Constant Regex:<br>
![Constant Regex](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/Regex/ConstantRegex.png)

> Literals Regex:<br>
![Literals Regex](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/Regex/LiteralsRegex.png)<br>

We implemented the lexial analyzer, using the prior constructed regular expressions to define token patterns within our code. The lexer processes the input, compares it with the defined regex and classifies them into the token categories.

> Identifiers Regex Implementation:
```^[A-Za-z_][A-Za-z0-9_]*$ ```<br>

> Operators Regex Implementation: ```">>=|<<=|\\+=|-=|\\*=|/=|%=|==|!=|>=|<=|&&|\\|\\||\\+\\+|--|&=|\\|=|\\^=|=|>|<|!|\\+|-|\\*|/|%|&|\\||\\^" ```<br>

> Puntuactors Regex Implementation: ```"\\*|\\(|\\)|\\.|,|:|;|\\{|\\}|->"; ```<br>

> Constant Regex Implementation:
```"-?[0-9]+(\\.[0-9]+)?"; ```<br>

> Literals Regex Implementation:
```"\".*\""; ```<br>

1. Operator composed strings

> Non- Terminal symbols:
```
A → Assignation expression
L → Logic expression
C → Comparison
E → Aritmetic expression
T → Generic term
F → Factor
ID → Identifier
N → number
AO → Assignation Operator
CO → Comparison Operator
LO → Logic Operator
A → ID OP_ASIG A | L
```
<br>

> Productions
```
AO → = | += | -= | *= | /=
L → L || C | L && C | C
C → E CO E | E
CO → == | != | < | > | <= | >=
E → E + T | E - T | T
T → T * F | T / F | F
F → (A) | !F | ID | NUM
```
<br>

2. Strings using pointers
   
> Non- Terminal symbols:
```
S → Statement
D → Declaration
A → Assignation
T → Type
ID → Identifier
E → Expresión
F → Factor
```
<br>

> Productions
```
S → D ; | A ;
D → T * ID // pointer declararion
A → ID = E //normal assignment | *ID = E | ID = &ID   
T → int | float | char
E → NUM | ID | &ID | *ID
ID → char (char | digit)*
NUM → digit+
```
<br>

3. Strings using If-Else structures that compare two variables

> Non- Terminal symbols:
```
S → Start
IF → If – Else Structure
C → Logical Condition
B → If – Else structure Body
ID → Identifier
CO → Comparison Operator
```
<br>

> Productions
```
S → IF  
IF → if ( C ) { B } else { B }  
C → ID OP_COMP ID  
OP_COMP → < | > | <= | >= | == | !=  
B → S | ID = NUM ; | ε  
ID → character (chsracter | digit)*  
NUM → digit+  
```
<br>

4. Strings using the keywords Scanf and Printf

> Non- Terminal symbols:
```
S → Start
R → Reading (scanf)
P → Printing
T → Data Type
ID → Identifier
STR → String to print
FMT → Format (ex. %d, %f, etc.)
```
<br>

> Productions
```
S → R ; P ;
R → scanf ( STR , &ID )
P → printf ( STR , ID )
T → int | float | char
STR → TXT FMT TXT  //Text with format marker
FMT → %d | %f | %c
ID → letra (letra | dígito)*
```
<br>

5. Strings using the ‘for’ structure

> Non- Terminal symbols:
```
S → Start
FOR → For cycle
INIT → Initialization
COND → Condition
UPD → Update
B → For structure’s body
P → Printing
A → Assignment
ID → Identifier
NUM → Number
STR → Printed string
FMT → Printing Format (%d, %f, etc.)
```
<br>

> Productions
```
S → FOR
FOR → for ( INIT ; COND ; UPD ) { B }
INIT → int ID = NUM
COND → ID COND_OP NUM
UPD → ID++
COND_OP→ < | <= | > | >= | != | ==
B → P ; | A ; | P ; A ; | 
P → printf ( STR , ID )
STR → "FMT\n"
FMT → %d | %f | %c
A → ID = NUM
ID → character (character | digit)*
NUM → digit+
```
<br>


Finally, the lexical analyzer was developed using Java as the main programming language. For the graphical interface, it was decided to develop an Android application, which facilitated a more intuitive interaction.
## Results 

### Application start
> First interface when opening the application
![FirstInterface](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/FinalResults/FirstInterface.jpg)

### String input
> A string representing a program in the C language is entered. By clicking the "Tokenize" button, the detected tokens are classified, showing their type and which ones were identified in the code.
![FirstInterface](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/FinalResults/Test1.jpg) 

Some more expamples are shown below with different entries each focusing on a different type of token.
> ### Punctuation Tokens.
![FirstInterface](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/FinalResults/Punctuation.jpg) 

> ### Operator Tokens
![FirstInterface](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/FinalResults/Operators.png) 

> ### Keyword Tokens
![FirstInterface](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/FinalResults/Keywords.jpg) 

> ### Constant Tokens
![FirstInterface](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/FinalResults/Constants.jpg) 

> ### Identifier Tokens
![FirstInterface](https://github.com/GustavoVaAsc/Lexer-Compilers-Team2/blob/main/FinalResults/Identifiers.jpg) 

## Conclusions

The development of the lexical analyzer demonstrated the effectiveness of the theoretical concepts learned so far in the course, especially regarding the identification and classification of tokens. The implementation of context-free grammars and the correct classification of tokens were essential in solving the posed problem. Through this process, the applicability of concepts related to the construction of lexical analyzers was validated, showing how topics covered in previous semesters and current classes were integrated to effectively structure the solution.

## Bibliography 
[1] C. Staff, “What is lexical analysis?,” *Coursera*, Apr. 10, 2024. Available: [https://www-coursera-org.translate.goog/articles/lexical-analysis?_x_tr_sl=en&_x_tr_tl=es&_x_tr_hl=es&_x_tr_pto=tc]

[2] “CS 340: Lecture 2: Finite Automata, Lexical Analysis”. GitHub Pages. [Online]. Available: [https://ycpcs.github.io/cs340-fall2016/lectures/lecture02.html]

[3] “Introduction of Finite Automata - GeeksforGeeks”. GeeksforGeeks.[Online]. Available: [https://www.geeksforgeeks.org/introduction-of-finite-automata/]
