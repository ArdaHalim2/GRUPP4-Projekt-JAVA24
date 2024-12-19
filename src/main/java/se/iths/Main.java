package se.iths;

import se.iths.entity.Student;
import se.iths.repositories.StudentRepo;

import java.util.Scanner;

public class Main {

    private static Student currentStudent = new Student();
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentRepo studentRepo = new StudentRepo();
    private static final StudentCrud studentCrud = new StudentCrud(scanner);
    private static final TestCrud testCrud = new TestCrud(scanner);
    private static final CityCrud cityCrud = new CityCrud(scanner);
    private static final CountryCrud countryCrud = new CountryCrud(scanner);
    private static final LakeCrud lakeCrud = new LakeCrud(scanner);
    private static final Quiz quiz = new Quiz(scanner, currentStudent);

    public static void main(String[] args) {
        printStartMenu();
    }

    public static void printStartMenu() {

        boolean runMenu = true;

        while (runMenu) {
            System.out.println("""
                START MENU
                
                1. Log in
                2. Edit
                3. Take Quiz
                0. Exit program
                """);

            String userChoiceStartMenu = scanner.nextLine();

            switch (userChoiceStartMenu) {
                case "1" -> logInUser();
                case "2" -> crudMenu();
                case "3" -> quiz.quizMenu();
                case "0" -> runMenu = false;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void logInUser() {

        System.out.println("Enter your user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        var student = studentRepo.getStudentByIdFromDatabase(userId);

        if (student.isPresent()) {
            currentStudent = student.get();
            System.out.println("You are logged in!");
        } else {
            System.out.println("You are not logged in!");
        }
    }

    public static void crudMenu() {

        boolean runMenu = true;

        while(runMenu) {
            System.out.println("""
                CRUD ENTITIES
                Choose which one to edit:
                
                1. Student
                2. Test
                3. Country
                4. City
                5. Lake
                0. Go back to main menu
                """);

            String userAnswer = scanner.nextLine();

            switch (userAnswer) {
                case "1" -> studentCrud.studentCrudMenu();
                case "2" -> testCrud.testCrudMenu();
                case "3" -> countryCrud.countryCrudMenu();
                case "4" -> cityCrud.cityCrudMenu();
                case "5" -> lakeCrud.lakeCrudMenu();
                case "0" -> runMenu = false;
                default -> System.out.println("Invalid choice");
            }
        }
    }
}
