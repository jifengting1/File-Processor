# File-Processor

This is a class, named FileProcessor, that implements the FileProcessorInterface interface and passes the provided tests.

Program fileprocessor is a simple command-line utility written in Java with the following specification:
NAME
fileprocessor - utility for analyzing a text file


SYNOPSIS
fileprocessor OPT <filename>

where OPT can be zero or more of
-s
(-r|-k) <string s>
-t [integer n]
-l


COMMAND-LINE ARGUMENTS AND OPTIONS

<filename>: file to be analyzed

-s: if specified, fileprocessor will sort the lines in the file alphanumerically, (1) ignoring (but keeping) non-alphanumeric characters, (2) with numbers preceding letters, and (3) with capital letters preceding lowercase letters (i.e., 0-9, A-Z, a-z). Any line containing only non-alphanumeric characters is removed.

(-r|-k) <string s>: if specified, fileprocessor will remove (-r) or keep (-k) only lines in the file which contain string s (case sensitive). Options -r and -k are mutually exclusive. 
  
-t [integer n]: if specified, fileprocessor will keep only the first n characters of each line. Value n must be a positive integer. If n is omitted, its default value is 1.

-l: if specified, fileprocessor will add the line number at the beginning of each line, followed by a space and with the first line having line number 1.
If no OPT flag is specified, fileprocessor will leave the file unchanged.  
NOTES

While the last command-line parameter provided is always treated as the filename, OPT flags can be provided in any order and will be applied in the order in which they are listed above (i.e., -s first and -l last).


EXAMPLES OF USAGE

Example 1: \
fileprocessor -s -l file1.txt

File content before:\
Hello \
Beatrice \
albert \
@#$% \
#%Albert \
--’’--911 \
hello 

File content after: \
1 --’’--911 \
2 #%Albert \
3 Beatrice \
4 Hello \
5 albert \
6 hello

Example 2:  \
fileprocessor -s -l -r Hell file1.txt

File content before: \
Hello \
Beatrice \
albert \
@#$% \
#%Albert \
--’’--911 \
hello

File content after: \
1 --’’--911 \
2 #%Albert \
3 Beatrice \
4 albert \
5 hello

Example 3:  \
fileprocessor -l -s -k ello file1.txt

File content before: \
Hello \
Beatrice \
albert \
@#$% \
#%Albert \
--’’--911 \
hello

File content after: \
1 Hello \
2 hello

Example 4:  \
fileprocessor -t 2 -s -k l file1.txt

File content before: \
Hello \
Beatrice \
albert \
@#$% \
#%Albert \
--’’--911 \
hello

File content after: \
#% \
He \
al \
he

Example 5:  \
fileprocessor -t -s -k l -l file1.txt

File content before: \
Hello \
Beatrice \
albert \
@#$% \
#%Albert \
--’’--911 \
hello 

File content after: \
1 # \
2 H \
3 a \
4 h
