package se.iths;

import se.iths.crud.*;
import se.iths.entity.*;
import se.iths.repositories.*;
import java.util.Scanner;

public class Main {

    private static Student currentStudent = null;
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentRepo studentRepo = new StudentRepo();
    private static final StudentCrud studentCrud = new StudentCrud(scanner);

    public static void main(String[] args) {
        printStartMenu();
    }

    private static void printStartMenu() {

        boolean runMenu = true;

        while (runMenu) {
            System.out.println("""
                
                    \tSTART MENU
                
                    \t1. Log in
                    \t2. Edit
                    \t3. Take Quiz
                    \t4. Show Statistics
                    \t0. Exit Program""");


            System.out.print("\tEnter your choice: ");
            String userChoiceStartMenu = scanner.nextLine();
            System.out.println();

            switch (userChoiceStartMenu) {
                case "1" -> logInUser();
                case "2" -> crudMenu();
                case "3" -> new Quiz(scanner, currentStudent).quizMenu();
                case "4" -> new Statistic(scanner, currentStudent).statisticMenu();
                case "0" -> runMenu = false;
                default -> System.out.println("Invalid menu choice. Please try again.");
            }
        }
    }

    private static void logInUser() {

        System.out.print("Enter your user id: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        var student = studentRepo.getStudentByIdFromDatabase(userId);

        if (student.isPresent()) {
            currentStudent = student.get();
            System.out.println("LOG IN SUCCESSFUL");
        } else {
            System.out.println("LOG IN FAILED");
        }
    }

    private static void crudMenu() {

        if(currentStudent == null){
            System.out.println("Login required.");
            return;
        }

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
                0. Go back to main menu""");

            System.out.print("Enter your choice: ");
            String userAnswer = scanner.nextLine();
            System.out.println();

            switch (userAnswer) {
                case "1" -> studentCrud.studentCrudMenu();
                case "2" -> new TestCrud(scanner).testCrudMenu();
                case "3" -> new CountryCrud(scanner).countryCrudMenu();
                case "4" -> new CityCrud(scanner).cityCrudMenu();
                case "5" -> new LakeCrud(scanner).lakeCrudMenu();
                case "0" -> runMenu = false;
                default -> System.out.println("Invalid menu choice. Please try again.");
            }
        }
    }
}
