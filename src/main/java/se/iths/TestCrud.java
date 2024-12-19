package se.iths;

import se.iths.entity.Test;
import se.iths.entity.Student;
import se.iths.repositories.TestRepo;
import se.iths.repositories.StudentRepo;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class TestCrud {

    private final TestRepo testRepo = new TestRepo();
    private final StudentRepo studentRepo = new StudentRepo();
    private Scanner scanner;

    public TestCrud(Scanner scanner) {
        this.scanner = scanner;
    }

    public void testCrudMenu() {
        boolean runMenu = true;
        while (runMenu) {
            System.out.println("""
                    CRUD TEST
                    
                    1. Add Test
                    2. Update Test
                    3. Delete Test
                    4. Show all tests
                    0. Go back to main menu
                    """);

            String userAnswer = scanner.nextLine();

            switch (userAnswer) {
                case "1" -> addTest();
                case "2" -> updateTest();
                case "3" -> deleteTest();
                case "4" -> showAllTests();
                case "0" -> runMenu = false;
                default -> System.out.println("Invalid input");
            }
        }
    }

    public void addTest() {
        System.out.println("Enter test category: ");
        String category = scanner.nextLine();

        System.out.println("Enter max score for the test: ");
        int maxScore = scanner.nextInt();

        System.out.println("Enter student ID associated with this test: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();

        Optional<Student> studentOptional = studentRepo.getStudentByIdFromDatabase(studentId);

        if (studentOptional.isPresent()) {
            Test test = new Test();
            test.setCategory(category);
            test.setMaxScore(maxScore);
            test.setStudentScore(0);
            test.setDate(LocalDate.now());
            test.setStudent(studentOptional.get());

            if (testRepo.persistTestToDatabase(test)) {
                System.out.println("Test was added to the database");
            } else {
                System.out.println("Test was not added to the database");
            }
        } else {
            System.out.println("No student found with ID: " + studentId);
        }
    }

    public void updateTest() {
        System.out.println("Enter the ID of the test you want to update: ");
        int testId = scanner.nextInt();
        scanner.nextLine();

        Optional<Test> testOptional = testRepo.getTestByIdFromDatabase(testId);

        if (testOptional.isPresent()) {
            Test test = testOptional.get();

            System.out.println("Enter new category for the test: ");
            String updatedCategory = scanner.nextLine();
            System.out.println("Enter new max score for the test: ");
            int updatedMaxScore = scanner.nextInt();
            scanner.nextLine();

            test.setCategory(updatedCategory);
            test.setMaxScore(updatedMaxScore);

            boolean isUpdated = testRepo.mergeTestToDatabase(test);

            if (isUpdated) {
                System.out.println("Test was updated in the database");
            } else {
                System.out.println("Test was not updated in the database");
            }
        } else {
            System.out.println("No test was found with the ID: " + testId);
        }
    }

    public void deleteTest() {
        System.out.println("Enter test ID to delete: ");
        int testId = scanner.nextInt();
        scanner.nextLine();

        Optional<Test> testOptional = testRepo.getTestByIdFromDatabase(testId);

        if (testOptional.isPresent()) {
            boolean wasDeleted = testRepo.removeTestFromDatabase(testOptional.get());

            if (wasDeleted) {
                System.out.println("Test with ID " + testId + " was deleted");
            } else {
                System.out.println("Failed to remove test with ID " + testId);
            }
        } else {
            System.out.println("No test was found with the ID: " + testId);
        }
    }

    public void showAllTests() {
        testRepo.getAllTestsFromDatabase()
                .ifPresentOrElse(
                        tests -> tests.forEach(System.out::println),
                        () -> System.out.println("No tests found")
                );
    }
}
