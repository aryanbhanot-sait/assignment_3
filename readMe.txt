Word Tracker Program
==================

How to Install
-------------
1. Make sure you have Java installed on your computer
2. Extract all the files from the zip file to a folder

How to Use
---------
The Word Tracker program reads text files and keeps track of all the words it finds. It remembers which files the words were in and which lines they appeared on.

To run the program, use this command:
java -jar WordTracker.jar <input.txt> -pf/-pl/-po [-f<output.txt>]

Where:
- <input.txt> is the file you want to process
- Choose one of these options:
  -pf = print words with files
  -pl = print words with files and line numbers
  -po = print words with files, line numbers, and how many times each word appears
- [-f<output.txt>] is optional - use this if you want to save the output to a file

Examples
--------
1. Process a file and show words with files:
   java -jar WordTracker.jar test1.txt -pf

2. Process a file and show words with files and line numbers:
   java -jar WordTracker.jar test2.txt -pl

3. Process a file, show words with files, line numbers, and occurrences, and save to a file:
   java -jar WordTracker.jar test3.txt -po -fresults.txt

Important Notes
--------------
- The program creates a file called repository.ser to remember all the words it has seen
- Each time you run the program, it adds new words to this repository
- If you want to start fresh, just delete the repository.ser file
- The program ignores case (so "Hello" and "hello" are treated as the same word)
- The program removes punctuation from words
