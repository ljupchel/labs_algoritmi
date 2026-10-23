package labs;

import java.util.Scanner;

public class Main {

    public static class  Employee {
        public String ID;
        public String name;
        public double baseSalary;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getBaseSalary() {
            return baseSalary;
        }

        public void setBaseSalary(double baseSalary) {
            this.baseSalary = baseSalary;
        }

        public double calculateSalary() {
            return baseSalary;
        }

        public Employee(String ID, String name, double baseSalary) {
            this.ID = ID;
            this.name = name;
            this.baseSalary = baseSalary;
        }

        @Override
        public String toString() {
            return String.format("%s %s salary=%.2f", ID, name, calculateSalary());
        }
    }

    static class Developer extends Employee {
        public int overtimeHours;

        public Developer(String ID, String name, double baseSalary, int overtimeHours) {
            super(ID, name, baseSalary);
            this.overtimeHours = overtimeHours;
        }

        @Override
        public double calculateSalary() {
            return super.calculateSalary() + (300 * overtimeHours);
        }

        @Override
        public String toString() {
            return String.format("%s %s salary=%.2f overtime=%d", ID, name, calculateSalary(), overtimeHours);
        }
    }

    static class TeamLead extends Developer {
        public int teamSize;

        public TeamLead(String ID, String name, double baseSalary, int overtimeHours, int teamSize) {
            super(ID, name, baseSalary, overtimeHours);
            this.teamSize = teamSize;
        }

        @Override
        public double calculateSalary() {
            //TODO
            //Ако тимот има 5 или повеќе членови, вкупната плата се зголемува за 5%
            double salary = super.calculateSalary() + (1000 * teamSize);
            if (teamSize >= 5) {
                return salary *= 1.05;
            } else {
                return super.calculateSalary() + (1000 * teamSize);
            }
        }

        @Override
        public String toString() {
            return String.format("%s %s salary=%.2f overtime=%d team=%d", ID, name, calculateSalary(), overtimeHours, teamSize);
        }
    }

    static class Intern extends Employee {
        public int months;

        public Intern(String ID, String name, double baseSalary, int months) {
            super(ID, name, baseSalary);
            this.months = months;
        }

        @Override
        public double calculateSalary() {
            if (months >= 6) return super.calculateSalary() * 0.8;
            else return super.calculateSalary() * 0.6;
        }

        @Override
        public String toString() {
            return String.format("%s %s salary=%.2f months=%d", ID, name, calculateSalary(), months);
        }
    }

    static class Company {
        String name;
        Employee[] employees;
        int size;

        public Company(String name) {
            this.employees = new Employee[100];
            this.size = 0;
            this.name = name;
        }

        public void hire(Employee employee) {
            employees[size++] = employee;
        }

        public void printAll() {
            System.out.println("=== " + name + " ===");
            for (int i = 0; i < size; i++) {
                System.out.println(employees[i]);
            }
        }

        public void printStatistics() {
            //TODO
            //Developers: X, Team leads: Y, Interns: Z, Others: W.
            int devs = 0, leads = 0, interns = 0, others = 0;
            for (int i = 0; i < size; i++) {
                Employee e = employees[i];
                if (e instanceof TeamLead) { // First checks TeamLead because TeamLead extends from Developer
                    leads++;
                } else if (e instanceof Developer) {
                    devs++;
                } else if (e instanceof Intern) {
                    interns++;
                } else {
                    others++;
                }
            }

            System.out.println("Developers: " + devs + ", Team leads: " + leads + ", Interns: " + interns + ", Others: " + others);

        }

        public void totalPayroll() {
            double sum = 0;
            for (int i = 0; i < size; i++) {
                sum += employees[i].calculateSalary();
            }
            System.out.printf("Total payroll: %.2f\n", sum);
        }

        public void averageSalary() {
            double sum = 0;
            for (int i = 0; i < size; i++) {
                sum += employees[i].calculateSalary();
            }
            System.out.printf("Average salary: %.2f\n", sum / size);
        }

        public void highestPaid() {
            double max = 0;
            int index = 0;
            for (int i = 0; i < size; i++) {
                if (max < employees[i].calculateSalary()) {
                    max = employees[i].calculateSalary();
                    index = i;
                }
            }
            System.out.printf("Highest paid: %s %s (%.2f)\n", employees[index].ID, employees[index].name, max);
        }

        public void countAbove(double threshold) {
            int count = 0;
            for (int i = 0; i < size; i++) {
                if (employees[i].calculateSalary() > threshold) {
                    count++;
                }
            }
            System.out.printf("Employees with salary above %.2f: %d\n", threshold, count);
        }

    }
    public static void main(String[] args) {
        Company company = new Company( "FINKI Soft");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            String type = scanner.next();
            String ID = scanner.next();
            String name = scanner.next();
            double base = scanner.nextDouble();

            if (type.equals("E")) {
                company.hire(new Employee(ID, name, base));
            } else if (type.equals("D")) {
                int overtime = scanner.nextInt();
                company.hire(new Developer(ID, name, base, overtime));
            } else if (type.equals("L")) {
                int overtime = scanner.nextInt();
                int teamSize = scanner.nextInt();
                company.hire(new TeamLead(ID, name, base, overtime, teamSize));
            } else if (type.equals("I")) {
                int months = scanner.nextInt();
                company.hire(new Intern(ID, name, base, months));
            } else {
                System.out.println("Not a valid positon");
            }
        }

        double threshold = scanner.nextDouble();



        company.printAll();
        company.printStatistics();
        company.totalPayroll();
        company.averageSalary();
        company.highestPaid();
        company.countAbove(threshold);

    }
}
