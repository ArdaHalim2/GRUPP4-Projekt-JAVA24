package se.iths;

import se.iths.crud.*;
import se.iths.entity.*;
import se.iths.repositories.*;

import java.util.Scanner;

public class Main {

    private static Student currentStudent = null;
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentRepo studentRepo = new StudentRepo();
    private static final StudentCrud studentCrud = new StudentCrud();

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

            String userInput = getValidString("\tEnter your choice: ");

            switch (userInput) {
                case "1" -> logInUser();
                case "2" -> crudMenu();
                case "3" -> new Quiz(scanner, currentStudent).quizMenu();
                case "4" -> new Statistic(currentStudent).statisticMenu();
                case "0" -> runMenu = false;
                default -> System.out.println("\tInvalid menu choice. Please try again.");
            }
        }
    }

    private static void logInUser() {
        int userId = getValidInt("Enter your user id: ");

        var student = studentRepo.getStudentByIdFromDatabase(userId);

        if (student.isPresent()) {
            currentStudent = student.get();
            System.out.println("LOG IN SUCCESSFUL");
        } else {
            System.out.println("LOG IN FAILED");
        }
    }

    private static void crudMenu() {

        if (currentStudent == null) {
            System.out.println("Login required.");
            return;
        }

        boolean runMenu = true;

        while (runMenu) {
            System.out.println("""
                    
                    \tCRUD ENTITIES
                    \tChoose which one to edit:
                    
                    \t1. Student
                    \t2. Test
                    \t3. Country
                    \t4. City
                    \t5. Lake
                    \t0. Go back to main menu""");

            String userInput = getValidString("\tEnter your choice: ");

            switch (userInput) {
                case "1" -> studentCrud.studentCrudMenu();
                case "2" -> new TestCrud().testCrudMenu();
                case "3" -> new CountryCrud().countryCrudMenu();
                case "4" -> new CityCrud().cityCrudMenu();
                case "5" -> new LakeCrud().lakeCrudMenu();
                case "0" -> runMenu = false;
                default -> System.out.println("Invalid menu choice. Please try again.");
            }
        }
    }

    public static int getValidInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Must be numeric. Please try again.");
            }
        }
    }

    public static String getValidString(String prompt) {
        while(true){
            System.out.print(prompt);
            String userInput = scanner.nextLine();
            System.out.println();

            if(!userInput.isBlank()){
                return userInput;
            } else {
                System.out.println("Invalid input. String can not be empty. Please try again.");
            }
        }
    }
}
