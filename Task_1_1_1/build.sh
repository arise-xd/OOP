#!/bin/bash

mkdir -p out
mkdir -p docs

javac -d out src/main/java/ru/nsu/skovalev4/sort/HeapSort.java \
             src/main/java/ru/nsu/skovalev4/sort/Main.java

javadoc -d docs src/main/java/ru/nsu/skovalev4/sort/HeapSort.java \
               src/main/java/ru/nsu/skovalev4/sort/Main.java

java -cp out ru.nsu.skovalev4.sort.Main