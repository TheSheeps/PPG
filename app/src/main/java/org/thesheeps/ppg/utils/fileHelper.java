package org.thesheeps.ppg.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Read and write to files.
 */

public class fileHelper {

    public static void writeToFile(File file, String string, Boolean append) throws IOException {

        FileOutputStream fos = new FileOutputStream(file, append);
        fos.write(string.getBytes());
        fos.close();
    }

    public static ArrayList<String> readFromFile(File file) throws IOException {

        ArrayList<String> lines = new ArrayList<>();

        FileInputStream in = new FileInputStream(file);
        BufferedReader buf = new BufferedReader(new InputStreamReader(in));

        String line;
        while ((line = buf.readLine()) != null) {
            lines.add(line);
        }

        buf.close();
        return lines;
    }
}