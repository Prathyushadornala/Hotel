package com.hostel;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class FileManager {

    public static void writeToFile(String fileName, String data) {

        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(data);
            writer.close();

        } catch (IOException e) {
            System.out.println("Error writing file.");
        }
    }

    public static void readFromFile(String fileName) {

        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(fileName));

            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}