#!/bin/bash

cd "$(dirname "$0")"
javac -d bin -cp test-lib/junit-platform-console-standalone-1.13.0-M3.jar $(find src -name "*.java")
java -cp bin main.MainMenu
