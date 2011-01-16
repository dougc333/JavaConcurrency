README

TestStringReverse tests the different types of shutdown.
ex.shutdown(): 
ex.shutdownNow():
System.exit(1);
Uncomment the different types of shutdown statements and observe the behavior.
System.exit(1) will terminate the JVM, there is no waiting for thread tasks to complete
ex.shutdownNow() will execute InterruptedException
ex.shutdown() will cause the tasks to finish. 

This is also a demo of the use of an executor, future, and shutdown code in java.util.concurrent.

To run the programs you have to compile in a dos box if using windows and execute the compiled
program from the command line. 

Example:
javac StringLengthRev2.java
java StringLengthRev2

