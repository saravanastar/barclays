package com.ask.barclays.exercise1;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ExerciseOneTest {

    public static String RESOURCE_PATH = "./input/testData1/";

    public InputStream loadInputData(String path, String fileName) {
        InputStream targetStream = null;
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(path + fileName).getFile());
        try {
            targetStream = new FileInputStream(file);
//            System.setIn(targetStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return targetStream;
    }
}
