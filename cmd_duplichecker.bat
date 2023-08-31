@echo off
setlocal enabledelayedexpansion

:: Prompt the user to enter the paths of the two files
set /p "file1=Enter the path of the first file: "
set /p "file2=Enter the path of the second file: "

:: Check if the entered files exist
if not exist "!file1!" (
    echo The first file does not exist.
    exit /b 1
)

if not exist "!file2!" (
    echo The second file does not exist.
    exit /b 1
)

:: Calculate hash values for file1 and file2
certutil -hashfile "!file1!" SHA256 > hash1.txt
certutil -hashfile "!file2!" SHA256 > hash2.txt

:: Extract the hash values from the output
for /f "tokens=2" %%a in (hash1.txt) do set hash1=%%a
for /f "tokens=2" %%a in (hash2.txt) do set hash2=%%a

:: Compare the hash values
if "%hash1%"=="%hash2%" (
    echo Files are identical.
) else (
    echo Files are NOT identical.
)

:: Clean up temporary files
del hash1.txt
del hash2.txt

endlocal
