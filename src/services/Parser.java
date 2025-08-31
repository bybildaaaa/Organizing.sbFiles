package services;

import models.Employee;
import models.Manager;
import models.IPerson;

public class Parser {

  public static IPerson parseLine(String line) {
    try {
      if (line == null || line.trim().isEmpty()) {
        throw new IllegalArgumentException("Empty line");
      }
      String[] parts = line.split(",");
      if (parts.length != 5) {
        throw new IllegalArgumentException("Invalid format " + line);
      }
      String type = parts[0].trim();
      int id = Integer.parseInt(parts[1].trim());
      String name = parts[2].trim();
      double salary = Double.parseDouble(parts[3].trim());
      if (id <= 0) {
        throw new IllegalArgumentException("Id should be > 0");
      }
      if (salary <= 0) {
        throw new IllegalArgumentException("Salary should be > 0");
      }

      if ("Manager".equalsIgnoreCase(type)) {
        String department = parts[4].trim();
        return new Manager(id, name, salary, department);
      } else if ("Employee".equalsIgnoreCase(type)) {
        int managerId = Integer.parseInt(parts[4].trim());
        if (managerId <= 0) {
          throw new IllegalArgumentException("ManagerId should be > 0");
        }
        return new Employee(id, name, salary, managerId);
      } else {
        throw new IllegalArgumentException("Unknown type: " + type);
      }

    } catch (Exception e) {
      throw new IllegalArgumentException("Parsing error: " + line, e);
    }
  }

}
