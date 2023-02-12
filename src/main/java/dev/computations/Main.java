package dev.computations;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //If we use 1000 as max integer in random values range then
        //file size can be around:
        //48mb - 10 FileSize.SMALL
        // 480mb - 100 FileSize.BIG
        // 4800mb - 1000 FileSize.LARGE

        long start = System.currentTimeMillis();
        File file = GenerateFile.generateTxtFullNumbers(FileSize.SMALL.getValue(), 10);
        long end = System.currentTimeMillis();

        long totalTime = (end - start) / 1000;
        System.out.println("Generate Total time taken: " + totalTime + " sec");

        ComputationsOnFiles computationsOnFiles = new ComputationsOnFiles();

        try{
            //2 - amount of threads wanted to use;
            //I have only 4 cores, you can use more if you want and can
            computationsOnFiles.readFileByChunks(file, 2);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}