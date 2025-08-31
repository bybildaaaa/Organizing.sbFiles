package services;

import models.IPerson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileReaderService {
  public static List<IPerson> readSbFiles(String fileName) {
    List<IPerson> persons = new ArrayList<>();
    File dir = new File(fileName);
    File[] files = dir.listFiles((ignored, name) -> name.toLowerCase().endsWith(".sb"));

    if (files == null || files.length == 0) {
      System.out.println("No files found in " + fileName);
      return persons;
    }

    try (BufferedWriter errorWriter = new BufferedWriter(new FileWriter("error.log", true))) {
      for (File file : files) {
        try (Stream<String> lines = Files.lines(file.toPath())) {
          lines.forEach(line -> {
            try {
              persons.add(Parser.parseLine(line));
            } catch (IllegalArgumentException e) {
              try {
                errorWriter.write(line);
                errorWriter.newLine();
              } catch (IOException e1) {
                System.err.println("Error writing to error.log");
              }
            }
          });
        } catch (IOException e) {
          System.err.println("Failed to read file: " + file.getName());
        }
      }
    } catch (IOException e) {
      System.err.println("Error while working with error.log");
    }

    return persons;
  }
}
