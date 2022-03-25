buildTest:
	javac -cp .:junit-platform-console-standalone-1.6.0.jar TestCases.java

test:
	java -jar junit-platform-console-standalone-1.6.0.jar --class-path . --scan-class-path

build:
	javac Main.java

run:
	java Main

clean:
	rm -rf *.class