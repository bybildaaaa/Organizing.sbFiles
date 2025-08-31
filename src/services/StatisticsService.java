package services;

import models.Employee;
import models.Manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsService {
  public static void generateStatistics(Map<Manager, List<Employee>> departments, String paramOutput, String paramPathOutput) {
    List<String> result = new ArrayList<>();
    result.add("department, min, max, mid");

    for (Map.Entry<Manager, List<Employee>> entry : departments.entrySet()) {
      String department = entry.getKey().getDepartment();
      List<Employee> employees = entry.getValue();

      double min = employees.stream().mapToDouble(Employee::getSalary).min().orElse(0.0);
      double max = employees.stream().mapToDouble(Employee::getSalary).max().orElse(0.0);
      double mid = employees.stream().mapToDouble(Employee::getSalary).average().orElse(0.0);

      result.add(department + ", " +
        format(min) + ", " +
        format(max) + ", " +
        format(mid));
    }

    result = result.stream()
      .skip(1)
      .sorted()
      .collect(Collectors.collectingAndThen(
        Collectors.toList(),
        list -> {
          list.add(0, "department, min, max, mid");
          return list;
        }
      ));

    if ("file".equalsIgnoreCase(paramOutput)) {
      File file = new File(paramPathOutput);
      if (file.getParentFile() != null) {
        file.getParentFile().mkdirs();
      }
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(paramPathOutput))) {
        for (String line : result) {
          writer.write(line);
          writer.newLine();
        }
      } catch (IOException e) {
        System.err.println("Error writing statistics to " + paramPathOutput);
      }
    } else {
      result.forEach(System.out::println);
    }
  }

  private static String format(double value) {
    return String.format(Locale.US, "%.2f", Math.ceil(value * 100) / 100.0);
  }
}
