package services;

import models.Employee;
import models.IPerson;
import models.Manager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DepartmentService {
  public static Map<Manager, List<Employee>> createDepartmentFiles(List<IPerson> persons, String paramSort, String paramOrder) {
    Map<Manager, List<Employee>> departments = new HashMap<>();

    for (IPerson p : persons) {
      if (p instanceof Manager m) {
        departments.putIfAbsent(m, new ArrayList<>());
      }
    }

    for (IPerson p : persons) {
      if (p instanceof Employee e) {
        Manager manager = findManagerById(e.getManagerId(), departments.keySet());
        if (manager != null) {
          departments.get(manager).add(e);
        }
      }
    }

    Comparator<Employee> comparator = null;
    if ("name".equals(paramSort)) {
      comparator = Comparator.comparing(Employee::getName, String.CASE_INSENSITIVE_ORDER);
    } else if ("salary".equals(paramSort)) {
      comparator = Comparator.comparingDouble(Employee::getSalary);
    }

    if (comparator != null && "desc".equals(paramOrder)) {
      comparator = comparator.reversed();
    }

    for (Map.Entry<Manager, List<Employee>> entry : departments.entrySet()) {
      Manager manager = entry.getKey();
      List<Employee> employees = entry.getValue();

      if (comparator != null) {
        employees.sort(comparator);
      }
      String fileName = manager.getDepartment() + ".sb";
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        writer.write(manager.toString());
        writer.newLine();

        for (Employee e : employees) {
          writer.write(e.toString());
          writer.newLine();
        }
      } catch (IOException e) {
        System.err.println("Error writing file: " + fileName);
      }

    }

    return departments;

  }

  private static Manager findManagerById(int id, Set<Manager> managers) {
    return managers.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
  }
}
