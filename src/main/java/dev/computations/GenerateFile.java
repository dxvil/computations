package dev.computations;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class GenerateFile {
    static final ReentrantLock reentrantLock = new ReentrantLock();
    public static File generateTxtFullNumbers(int amountOfIteration, int maxValueOfNum) throws IOException {
        String fileName = "./" + RandomValuesGenerator.generateRandomFileName() + ".txt";
        File file = new File(fileName);
        try(FileWriter fileWriter = new FileWriter(file);) {
                //amount of items
                for(int i = 0; i < amountOfIteration; i++) {
                    RandomValuesGenerator.generateListOfInteger(maxValueOfNum)
                            .stream()
                            .parallel()
                            .forEach(num -> {
                                try {
                                    reentrantLock.lock();
                                    fileWriter.write(num + ",");
                                    fileWriter.write(System.getProperty("line.separator"));
                                    reentrantLock.unlock();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            });
                }

            return file;
        } catch (IOException e) {
            throw new RuntimeException("Something went wrong" + e);
        }
    }
}
