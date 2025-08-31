import models.Employee;
import models.IPerson;
import models.Manager;
import services.DepartmentService;
import services.FileReaderService;
import services.StatisticsService;

import java.util.List;
import java.util.Map;

public class Main {
  public static void main(String[] args) {
    String inputDir = ".";
    String paramOrder = null;
    String paramSort = null;
    boolean paramStat = false;
    String paramOutput = "console";
    String paramPathOutput = null;

    for (String arg : args) {
      if (arg.startsWith("--sort=") || arg.startsWith("-s=")) {
        paramSort = arg.split("=")[1].trim();
      } else if (arg.startsWith("--order=")) {
        paramOrder = arg.split("=")[1].trim();
      } else if (arg.startsWith("--stat")) {
        paramStat = true;
      } else if (arg.startsWith("--output") || arg.startsWith("-o")) {
        paramOutput = arg.split("=")[1].trim();
      } else if (arg.startsWith("--path")) {
        paramPathOutput = arg.split("=")[1].trim();
      } else {
        System.out.println("Unknown argument: " + arg);
        return;
      }
    }

    if (paramSort == null && paramOrder != null) {
      System.err.println("Error: you can't set order without sorting");
      return;
    }
    if (paramSort != null && !paramSort.equals("name") && !paramSort.equals("salary")) {
      System.err.println("Error: set param sorting");
      return;
    }
    if (paramOrder != null && !paramOrder.equals("asc") && !paramOrder.equals("desc")) {
      System.err.println("Error: set param order");
      return;
    }

    List<IPerson> persons = FileReaderService.readSbFiles(inputDir);

    Map<Manager, List<Employee>> departments = DepartmentService.createDepartmentFiles(persons, paramSort, paramOrder);

    if (paramStat) {
      if ("file".equals(paramOutput) && (paramPathOutput == null || paramPathOutput.isEmpty())) {
        System.err.println("Error: --output=file requires --path=<file>");
        return;
      }
      StatisticsService.generateStatistics(departments, paramOutput, paramPathOutput);
    } else {
      if ("file".equals(paramOutput) || "console".equals(paramOutput) || paramPathOutput != null) {
        System.err.println("Error: output/path flags are only valid with --stat");
      }
    }

  }
}