@echo off
javac -d out -cp ".;src/Dependencies/json-20200518.jar" src/main/java/Main.java
java -cp ".;src/Dependencies/json-20200518.jar;out" Main
