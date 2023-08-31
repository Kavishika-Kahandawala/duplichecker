# Duplicate File checker
A simple program made to checks files whether they are identical or not. This project includes,
- a batch file duplichecker
- A java command line duplichecker
- A GUI java duplichecker

## Batch file duplichecker
Batch files are not as well-suited for calculating and comparing hash values as Java or other programming languages, but can use external tools to achieve the same result. One common tool for calculating hash values in Windows batch scripts is `CertUtil` . The batch script made in that way

## Java command line duplichecker
This Java program accepts two file paths as command-line arguments and then compares the `SHA-256 hash values` of the two files. If the hash values match for both files, it considers them identical. If the hash values differs, it considers them as not identical.

## Java GUI duplichecker
Same using same algorithm used in  `Java command line duplichecker`. But added a GUI, because not everyone prefers command line xD


### To compile and run Java files:
- Open a terminal and navigate to the directory where you saved the Java file.<br><br>
- Compile the Java code using the `javac` command, <br>
```javac fileName.java```
- Compile the Java code using the `javac` command, <br>
```java FileName```