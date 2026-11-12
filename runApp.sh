#!/bin/bash

cd "$(dirname "$0")"
javac -d bin src/main/*.java
java -cp bin main.MainMenu
