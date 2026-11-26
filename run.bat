@echo off
cd /d "%~dp0"
if not exist "Main.class" (
    echo [Notice] Compiled classes not found. Auto-compiling Main.java...
    javac -cp "lib\sqlite-jdbc.jar" Main.java
    if %errorlevel% neq 0 (
        echo [Error] Auto-compilation failed.
        exit /b %errorlevel%
    )
    echo [Build] Compilation successful.
)
java -cp ".;lib\sqlite-jdbc.jar" Main %*
