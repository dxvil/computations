package dev.computations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomValuesGenerator {
    private static final String CHAR_LIST =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static List<String> generateListOfInteger(int maxValueOfNum) {
        ArrayList<String> listOfRandomNumbers = new ArrayList<>();


        for(int i = 0; i < 1000000; i++) {
            String randomNumber = String.valueOf(
                    ThreadLocalRandom.current().nextInt(0, maxValueOfNum)
            );

            listOfRandomNumbers.add(randomNumber);
        }

        return listOfRandomNumbers;
    }

    public static String generateRandomFileName() {
        StringBuffer randomString = new StringBuffer();
        for (int i = 0; i < CHAR_LIST.length() / 2; i++) {
            int randomNumber = ThreadLocalRandom.current().nextInt(0, CHAR_LIST.length());

            randomString.append(CHAR_LIST.charAt(randomNumber));
        }
        return randomString.toString();
    }
}
