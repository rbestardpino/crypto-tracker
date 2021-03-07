package com.rbestardpino.cryptotracker.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

    public static File createAndWrite(String name, String text) {
        File file = new File("tmp/" + name);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }
}
