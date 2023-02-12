I wrote this program to improve my skills on working with Threads, I/O operations and working with a big amount of data. The main goal was to efficiently read a big file and do some operations on its data. In my case - it's a calculation of sum of all integer.

What I archived:
- We can generate the file with a big amount of integer values, in range of preferred values. The complexity of it O(n) but I'm using a .parralel() method in Stream, that implements a using of multiple threads under the hood.
- The method that read the file and the method that count the sum guarantee that generated file will be read properly  (UTF-8).
  But it cannot guarantee with a different encoding or file structure, because it doesn't include any extra validations.
- The methods to read and do operations on file effectively based on idea to split a file on a byte chunks and read it in a different threads. The chunks size is based on amount of thread that can be used. The user can choose an amount of thread needed.
- The most challenging part is to get an efficient data structure to get a total sum. The best decisions that Java suggest us - BigInt and AtomicLong. The maximum value that can be stored in an AtomicLong in Java is 9223372036854775807 while there is no maximum value for BigInteger in Java, as it can store arbitrarily large integers. However, it should be noted that the performance of BigInteger can be slower than AtomicLong, especially for large numbers, due to the overhead of storing and manipulating larger numbers.
  In that case I prefer to lower the maximum int of input data to store it in thread-safe AtomicLong, that also working much faster.

