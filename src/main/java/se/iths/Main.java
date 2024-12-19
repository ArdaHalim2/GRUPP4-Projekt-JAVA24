package se.iths;

import se.iths.entity.Student;
import se.iths.repositories.StudentRepo;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentRepo studentRepo = new StudentRepo();
    private static final Crud crud = new Crud();
    private static final Quiz quiz = new Quiz();
    private static Student currentStudent = new Student();

    public static void main(String[] args) {


        printStartMenu();
        String userChoiceStartMenu = scanner.nextLine();

        switch(userChoiceStartMenu) {
            case "1" -> logInUser();
            case "2" -> crud.crudMenu(scanner, currentStudent);
            case "3" -> quiz.quizMenu();
            default -> changeThisToRealMethod();
        }

    }

    public static void printStartMenu(){
        System.out.println("""
                START MENU
                
                1. Log in
                2. Edit
                3. Take Quiz
                """);
    }

    public static void logInUser(){

        System.out.println("Enter your user ID: ");
        int userId = scanner.nextInt();

        var student = studentRepo.getStudentByIdFromDatabase(userId);
        if(student.isPresent()){
            currentStudent = student.get();
            System.out.println("You are logged in!");
        } else {
            System.out.println("You are not logged in!");
        }
    }

    public static void changeThisToRealMethod(){}
}
