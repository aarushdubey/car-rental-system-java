@echo off
javac -cp "lib\sqlite-jdbc.jar" Main.java
if %errorlevel% equ 0 (
    echo Compilation successful!
)
pause

