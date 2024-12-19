package se.iths;

import se.iths.entity.Student;
import se.iths.repositories.StudentRepo;
import java.util.Optional;
import java.util.Scanner;

import static se.iths.Main.printStartMenu;

public class StudentCrud {

    private final StudentRepo studentRepo = new StudentRepo();
    private Scanner scanner;

    public StudentCrud(Scanner scanner) {
        this.scanner = scanner;
    }

    public void studentCrudMenu() {
        boolean runMenu = true;
        while (runMenu) {
            System.out.println("""
                    CRUD STUDENT
                    
                    1. Add Student
                    2. Update Student
                    3. Delete Student
                    4. Show all students
                    0. Go back to edit menu
                    """);

            String userAnswer = scanner.nextLine();

            switch (userAnswer) {
                case "1" -> addStudent();
                case "2" -> updateStudent();
                case "3" -> deleteStudent();
                case "4" -> showAllStudents();
                case "0" -> runMenu = false;
                default -> System.out.println("Invalid input");
            }
        }
    }

    public void addStudent() {

        System.out.println("Enter student name: ");
        String studentName = scanner.nextLine();
        Student student = new Student(studentName);

        if (studentRepo.persistStudentToDatabase(student)) {
            System.out.println("Student was added to the database");
        } else {
            System.out.println("Student was not added to the database");
        }
    }

    public void updateStudent() {

        System.out.println("Enter the ID of the student you want to update: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();

        Optional<Student> studentOptional = studentRepo.getStudentByIdFromDatabase(studentId);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            System.out.println("Enter new name for the student: ");
            String updateStudentName = scanner.nextLine();

            student.setName(updateStudentName);

            boolean isUpdated = studentRepo.mergeStudentInDatabase(student);

            if (isUpdated) {
                System.out.println("Student was updated in the database");
            } else {
                System.out.println("Student was not updated in the database");
            }
        } else {
            System.out.println("No student was found wit the name: " + studentId);
        }
    }

    public void deleteStudent() {
        System.out.println("Enter student ID to delete: ");
        int idToDelete = scanner.nextInt();

        var studentToDelete = studentRepo.getStudentByIdFromDatabase(idToDelete);

        if (studentToDelete.isPresent()) {
            boolean wasDeleted = studentRepo.removeStudentFromDatabase(studentToDelete.get());

            if (wasDeleted) {
                System.out.println("Student " + idToDelete + " was deleted");
            } else {
                System.out.println("Failed to remove student " + idToDelete);
            }
        } else {
            System.out.println("No student was found wit the name: " + idToDelete);
        }
    }

    public void showAllStudents() {
        studentRepo.getAllStudentsFromDatabase()
                .ifPresentOrElse(
                        students -> students.forEach(System.out::println),
                        () -> System.out.println("No students found")
                );
    }

    public void goBack() {
        printStartMenu();
    }
}
