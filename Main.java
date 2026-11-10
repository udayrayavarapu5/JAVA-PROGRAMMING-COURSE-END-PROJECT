import java.util.*;

/** Menu-driven console interface. */
public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final String HEADER = String.format(
            "%-8s %-18s %-8s %8s %9s  %-3s",
            "RollNo", "Name", "Dept", "Total", "Percent", "Grade");

    public static void main(String[] args) {
        StudentService service = new StudentService();
        service.setStudents(FileHandler.loadFromFile());
        System.out.println(service.count() + " record(s) loaded.");

        int choice;
        do {
            printMenu();
            choice = readInt("Enter choice: ");
            switch (choice) {
                case 1: addStudent(service); break;
                case 2: searchStudent(service); break;
                case 3: displayAll(service); break;
                case 4: updateMarks(service); break;
                case 5: deleteStudent(service); break;
                case 6: printRanking("RANK LIST - BY PERCENTAGE (Comparable)",
                                     service.getRankList()); break;
                case 7: printRanking("RANK LIST - BY NAME (Comparator)",
                                     service.getRankList(
                                         Comparator.comparing(Student::getName,
                                                 String.CASE_INSENSITIVE_ORDER)
                                                   .thenComparingInt(Student::getRollNo)));
                        break;
                case 8: printRanking("RANK LIST - BY ROLL NUMBER (Comparator)",
                                     service.getRankList(
                                         Comparator.comparingInt(Student::getRollNo)));
                        break;
                case 9: printRanking("RANK LIST - BY TOTAL MARKS (Comparator)",
                                     service.getRankList((a, b) -> {
                                         int r = Double.compare(b.getTotal(), a.getTotal());
                                         return r != 0 ? r : Integer.compare(a.getRollNo(), b.getRollNo());
                                     }));
                        break;
                case 10: FileHandler.saveToFile(service.getAllStudents()); break;
                case 0: FileHandler.saveToFile(service.getAllStudents());
                        System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 0);
    }

    private static void printMenu() {
        System.out.println("\n=========== STUDENT RECORD SYSTEM ===========");
        System.out.println(" 1. Add Student");
        System.out.println(" 2. Search Student by Roll Number");
        System.out.println(" 3. Display All Students");
        System.out.println(" 4. Update Marks");
        System.out.println(" 5. Delete Student");
        System.out.println(" 6. Rank List by Percentage");
        System.out.println(" 7. Rank List by Name");
        System.out.println(" 8. Rank List by Roll Number");
        System.out.println(" 9. Rank List by Total Marks");
        System.out.println("10. Save Records");
        System.out.println(" 0. Save and Exit");
        System.out.println("=============================================");
    }

    private static void addStudent(StudentService service) {
        int roll = readInt("Roll number: ");
        if (service.searchByRollNo(roll) != null) {
            System.out.println("A student with this roll number already exists!");
            return;
        }
        String name = readLine("Name: ");
        String dept = readLine("Department: ");
        double[] marks = readMarks();
        service.addStudent(new Student(roll, name, dept, marks));
        System.out.println("Student added successfully.");
    }

    private static void searchStudent(StudentService service) {
        int roll = readInt("Enter roll number: ");
        Student s = service.searchByRollNo(roll);
        if (s == null) {
            System.out.println("Student not found.");
        } else {
            System.out.println(HEADER);
            System.out.println(s);
            System.out.println("Marks: " + Arrays.toString(s.getMarks()));
        }
    }

    private static void displayAll(StudentService service) {
        if (service.count() == 0) {
            System.out.println("No records available.");
            return;
        }
        System.out.println(HEADER);
        for (Student s : service.getAllStudents()) System.out.println(s);
    }

    private static void updateMarks(StudentService service) {
        int roll = readInt("Enter roll number: ");
        if (service.searchByRollNo(roll) == null) {
            System.out.println("Student not found.");
            return;
        }
        service.updateMarks(roll, readMarks());
        System.out.println("Marks updated.");
    }

    private static void deleteStudent(StudentService service) {
        int roll = readInt("Enter roll number: ");
        System.out.println(service.deleteStudent(roll)
                ? "Student deleted." : "Student not found.");
    }

    private static void printRanking(String title, Collection<Student> ranked) {
        if (ranked.isEmpty()) {
            System.out.println("No records available.");
            return;
        }
        System.out.println("\n--- " + title + " ---");
        System.out.println(String.format("%-5s", "Rank") + HEADER);
        int rank = 1;
        for (Student s : ranked) {
            System.out.println(String.format("%-5d", rank++) + s);
        }
    }

    // ---------- input helpers (re-prompt on bad input) ----------

    private static double[] readMarks() {
        int n = readInt("Number of subjects: ");
        while (n <= 0) {
            n = readInt("Must be at least 1. Number of subjects: ");
        }
        double[] marks = new double[n];
        for (int i = 0; i < n; i++) {
            double m;
            do {
                m = readDouble("Marks for subject " + (i + 1) + " (0-100): ");
            } while (m < 0 || m > 100);
            marks[i] = m;
        }
        return marks;
    }

    private static String readLine(String prompt) {
        String s;
        do {
            System.out.print(prompt);
            s = sc.nextLine().trim();
        } while (s.isEmpty());
        return s;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
