package se.iths;

import se.iths.entity.City;
import se.iths.entity.Student;
import se.iths.repositories.CityRepo;

import java.util.Optional;
import java.util.Scanner;

import static se.iths.Main.printStartMenu;

public class CityCrud {

    private final CityRepo cityRepo = new CityRepo();
    private Scanner scanner;

    public CityCrud(Scanner scanner) {
        this.scanner = scanner;
    }

    public void cityCrudMenu() {

        boolean runMenu = true;

        while (runMenu) {
            System.out.println("""
                    CRUD CITY
                    
                    1. Add City
                    2. Update City
                    3. Delete City
                    4. Show all cities
                    0. Go back to edit menu
                    """);

            String userAnswer = scanner.nextLine();

            switch (userAnswer) {
                case "1" -> addCity();
                case "2" -> updateCity();
                case "3" -> deleteStudent();
                case "4" -> showAllStudents();
                case "0" -> runMenu = false;
                default -> System.out.println("Invalid input");
            }
        }
    }

    public void addCity() {

        System.out.println("Enter city name: ");
        String cityName = scanner.nextLine();
        City city = new City(cityName);

        if (cityRepo.persistCityToDatabase(city)) {
            System.out.println("City was added to the database");
        } else {
            System.out.println("City was not added to the database");
        }
    }

    public void updateCity() {

        System.out.println("Enter the ID of the city you want to update: ");
        int cityId = scanner.nextInt();
        scanner.nextLine();

        var city = studentRepo.getStudentByIdFromDatabase(studentId);

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
