import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

 public class Student {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }
}

class StudentManagementSystem {
    private ArrayList<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public boolean removeStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                students.remove(student);
                return true;
            }
        }
        return false;
    }

    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public ArrayList<Student> getAllStudents() {
        return students;
    }
}

 class StudentManagementSystemApp {
    private static final String DATA_FILE = "student_data.txt";
    private static StudentManagementSystem studentSystem = new StudentManagementSystem();

    public static void main(String[] args) {
        loadStudentData();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    removeStudent(scanner);
                    break;
                case 3:
                    searchStudent(scanner);
                    break;
                case 4:
                    displayAllStudents();
                    break;
                case 5:
                    saveStudentData();
                    System.out.println("Exiting the application.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void addStudent(Scanner scanner) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.print("Enter roll number: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter grade: ");
        String grade = scanner.nextLine();

        if (name.isEmpty() || grade.isEmpty()) {
            System.out.println("Name and grade cannot be empty. Student not added.");
        } else {
            Student student = new Student(name, rollNumber, grade);
            studentSystem.addStudent(student);
            System.out.println("Student added successfully.");
        }
    }

    private static void removeStudent(Scanner scanner) {
        System.out.print("Enter roll number of the student to remove: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine();

        if (studentSystem.removeStudent(rollNumber)) {
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student with the given roll number not found.");
        }
    }

    private static void searchStudent(Scanner scanner) {
        System.out.print("Enter roll number to search for a student: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        Student student = studentSystem.searchStudent(rollNumber);
        if (student != null) {
            System.out.println("Student found:\nName: " + student.getName() + "\nRoll Number: " +
                    student.getRollNumber() + "\nGrade: " + student.getGrade());
        } else {
            System.out.println("Student with the given roll number not found.");
        }
    }

    private static void displayAllStudents() {
        ArrayList<Student> students = studentSystem.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("All Student:");
            for (Student student : students) {
                System.out.println("Name: " + student.getName() + "\nRoll Number: " +
                        student.getRollNumber() + "\nGrade: " + student.getGrade() + "\n");
            }
        }
    }

    private static void loadStudentData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String name = data[0];
                    int rollNumber = Integer.parseInt(data[1]);
                    String grade = data[2];
                    Student student = new Student(name, rollNumber, grade);
                    studentSystem.addStudent(student);
                }
            }
            System.out.println("Student data loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting with an empty database.");
        } catch (IOException e) {
            System.err.println("Error loading student data: " + e.getMessage());
        }
    }

    private static void saveStudentData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            ArrayList<Student> students = studentSystem.getAllStudents();
            for (Student student : students) {
                String line = student.getName() + "," + student.getRollNumber() + "," + student.getGrade();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Student data saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving student data: " + e.getMessage());
        }
    }
}
