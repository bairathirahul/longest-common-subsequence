# Longest Common Subsequence

## Execution
To compile the program, use following command on the command prompt:
`javac LCS.java`

To execute the program use following command on the command prompt:
`java LCS <input filename> <output filename>`

Please make sure that the file provided as input is readable and the output file is writable. The validation for these cases are done prior. So the program will exit with error message if there is a failure in any of these situations.

## Introduction
In this project, we are supposed to compare the DNA strands. This comparison involves finding of the longest common sub-sequence between two strands. Each strand is represented as a string containing only characters from the set {A, C, G, T} (initial letter of the base names). In this program, it is done by the method "getLCS" which is nothing else but the implementation of Longest Common Subsequence dynamic programming algorithm. This method takes two strings as input and returns the longest common subsequence in those strings.

## Data Structures
Here the data structures used by the "getLCS" method is describe, as it is the core implementation of the algorithm. The implementation of dynamic programming LCS requires two matrices, let's say LENGTH & DIRECTION both of sizes (m + 1) x (n + 1). Here m & n are the length of given input sequences and let's assume the input sequences are A & B. 

The element i,j of LENGTH matrix stores the length of LCS for substrings A[1...i] & B[1...j]. This matrix is represented by a 2-dimensional integer array. After the execution of LCS algorithm last entry of this matrix, i.e. LENGTH[m][n] contains the length of LCS for the given strings. 

The element i,j of DIRECTION matrix stores the direction at which last character of LCS can be found for substrings A[1...i] & B[1...j]. It is also represented using a 2-dimensional integer array.

## Program Design
The program does following functions:
* Read lines of input file whose filename is provided as the first command-line parameter and does the required validations.
* After reading two lines, it determines the LCS of those two lines
* Write the generated LCS and it's length in the output file, whose name is provided as the second command-line parameter
* At the end, it declares the performance of execution by indicating the time lapsed.

The program structure consists of two method, "main" and "getLCS".

The "main" method is the entry point of the program. It requires the input and output file names to be provided as the command-line parameters. This method, first of all, checks whether the input file is readable and output file is writeable. Next, it reads the input file line by line and perform required validation. For each pair of lines, it executes the "getLCS" method which returns the LCS. Finally, the output of "getLCS" is written to the output file with the time lapsed at the end.

The "getLCS" method is the implementation of LCS dynamic programming algorithm. It fills the arrays "length" and "directions" with the length of LCS and direction where LCS can be found. At the end, using these two matrices, it generate the LCS and returns it.

## Development Environment
The program is written in Java and has been tested using Java 1.9. However, the Java packages that are used by this program were available in the earlier versions as well. This program will work with Java version 1.6 and later. 

## RESULTS
### Things that will work with this implementation:
* The program successfully reads text files containing strings in the format specified in assignment description.
* The program successfully generates sub-sequences and computes performance for any input file.
* The program successfully writes the contents of the final output to the output file, provided that the file is writeable.
* Program is capable of handling any dirty data by indicating the error and exiting gracefully.

### Things that will not work with this implementation:
The program will run for extremely long time or may hang if the input is extremely large because the running time of LCS algorithm is O(mn). However, the program will handle those cases and exits successfully. It is also to continue with other inputs elegantly.