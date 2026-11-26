#!/usr/bin/env bash
cd "$(dirname "$0")"

# Automatically compile if .class files are not present
if [ ! -f "Main.class" ]; then
    echo "[Notice] Compiled classes not found. Auto-compiling Main.java..."
    javac -cp "lib/sqlite-jdbc.jar" Main.java
    if [ $? -ne 0 ]; then
        echo "[Error] Auto-compilation failed."
        exit 1
    fi
    echo "[Build] Compilation successful."
fi

java -cp ".:lib/sqlite-jdbc.jar" Main "$@"
