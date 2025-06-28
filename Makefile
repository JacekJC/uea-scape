client:
	javac -d . -cp ":./lwjgl/*:jar" ./src/*.java ./src/math/*.java ./src/node/*.java
	java ueaScape.Main -c

server:
	java -cp "../lwjgl/*:jar" ../src/main.java -s
