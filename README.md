**TEAM MEMBERS:**  
- 320068234  
- 320239126  
- 320257599  
- 320278107  
- 117002029  

**Group:** 03  
**Semester:** 2025-II  

**Mexico City, Mexico. February 2025.**


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

Finally, we implemented the lexial analyzer, using the prior constructed regular expressions to define token patterns within our code. The lexer processes the input, compares it with the defined regex and classifies them into the token categories.

> Keywords Automata:

> Identifiers Automata:
```^[A-Za-z_][A-Za-z0-9_]*$ ```

> Operators Automata: ```">>=|<<=|\\+=|-=|\\*=|/=|%=|==|!=|>=|<=|&&|\\|\\||\\+\\+|--|&=|\\|=|\\^=|=|>|<|!|\\+|-|\\*|/|%|&|\\||\\^" ```

> Puntuactors Automata: ```"\\*|\\(|\\)|\\.|,|:|;|\\{|\\}|->"; ```

> Constant Automata
```"-?[0-9]+(\\.[0-9]+)?"; ```

> Literals Automata :
```"\".*\""; ```
## Results 

Mediante capturas de pantalla y una breve descripción seguida de la captura se presentan los resultados finales de su aplicación.

## Conclusions

Se presenta un análisis de los resultados obtenidos, donde se destaca la importancia
de la aplicación de los conceptos teóricos para resolver el problema. Es importante
resaltar lo siguiente:
* No es describir si les gustó la actividad o no.
* No es decir que se obtuvo de la práctica.
* No es describir lo que fue difícil.

The development of the lexical analyzer demonstrated the effectiveness of the theoretical concepts learned so far in the course, especially regarding the identification and classification of tokens. The implementation of context-free grammars and the correct classification of tokens were essential in solving the posed problem. Through this process, the applicability of concepts related to the construction of lexical analyzers was validated, showing how topics covered in previous semesters and current classes were integrated to effectively structure the solution.

## Bibliography 
[1] C. Staff, “What is lexical analysis?,” *Coursera*, Apr. 10, 2024. Available: [https://www-coursera-org.translate.goog/articles/lexical-analysis?_x_tr_sl=en&_x_tr_tl=es&_x_tr_hl=es&_x_tr_pto=tc]

[2] “CS 340: Lecture 2: Finite Automata, Lexical Analysis”. GitHub Pages. [Online]. Available: [https://ycpcs.github.io/cs340-fall2016/lectures/lecture02.html]

[3] “Introduction of Finite Automata - GeeksforGeeks”. GeeksforGeeks.[Online]. Available: [https://www.geeksforgeeks.org/introduction-of-finite-automata/]
