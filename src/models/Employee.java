package models;

public class Employee implements IPerson {
  private final int id;
  private final String name;
  private final int managerId;
  private final double salary;

  public Employee(int id, String name, double salary, int managerId) {
    this.id = id;
    this.name = name;
    this.salary = salary;
    this.managerId = managerId;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getSalary() {
    return salary;
  }

  public int getManagerId() {
    return managerId;
  }

  @Override
  public String toString() {
    return "Employee," + id + "," + name + "," + salary + "," + managerId;
  }

}
