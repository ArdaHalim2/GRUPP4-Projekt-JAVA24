package se.iths.crud;

import se.iths.entity.Student;
import se.iths.repositories.StudentRepo;
import java.util.Scanner;

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
                    0. Go back to edit menu""");

            System.out.print("Enter your choice: ");
            String userAnswer = scanner.nextLine();
            System.out.println();

            switch (userAnswer) {
                case "1" -> addStudent();
                case "2" -> updateStudent();
                case "3" -> deleteStudent();
                case "4" -> showAllStudents();
                case "0" -> runMenu = false;
                default -> System.out.println("Invalid menu choice. Please try again.");
            }
        }
    }

    public void addStudent() {

        System.out.print("Enter student name: ");
        String studentName = scanner.nextLine();

        Student student = new Student(studentName);

        if (studentRepo.persistStudentToDatabase(student)) {
            System.out.println("Student was added to the database");
        } else {
            System.out.println("Student was not added to the database");
        }
    }

    public void updateStudent() {
        System.out.print("Enter the id of the student you want to update: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();

        var studentToUpdate = studentRepo.getStudentByIdFromDatabase(studentId);

        if (studentToUpdate.isPresent()) {
            System.out.print("Enter new name for the student: ");
            String updateStudentName = scanner.nextLine();

            Student student = studentToUpdate.get();
            student.setName(updateStudentName);

            if (studentRepo.mergeStudentInDatabase(student)) {
                System.out.println("Student was updated in the database");
            } else {
                System.out.println("Student was not updated in the database");
            }
        } else {
            System.out.println("No student was found wit the id: " + studentId);
        }
    }

    public void deleteStudent() {
        System.out.print("Enter the id of the student you want to delete: ");
        int idToDelete = scanner.nextInt();
        scanner.nextLine();

        var studentToDelete = studentRepo.getStudentByIdFromDatabase(idToDelete);

        if (studentToDelete.isPresent()) {
            if (studentRepo.removeStudentFromDatabase(studentToDelete.get())) {
                System.out.println("Student with id: " + idToDelete + " was deleted");
            } else {
                System.out.println("Failed to remove student with id: " + idToDelete);
            }
        } else {
            System.out.println("No student was found with the id: " + idToDelete);
        }
    }

    public void showAllStudents() {
        studentRepo.getAllStudentsFromDatabase()
                .ifPresentOrElse(
                        students -> students.forEach(System.out::println),
                        () -> System.out.println("No students found")
                );
    }

}
