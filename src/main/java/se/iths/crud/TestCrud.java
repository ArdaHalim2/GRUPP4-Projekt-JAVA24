package se.iths.crud;

import se.iths.entity.Test;
import se.iths.repositories.TestRepo;
import se.iths.repositories.StudentRepo;

import java.time.LocalDate;
import java.util.Scanner;

public class TestCrud {

    private final TestRepo testRepo = new TestRepo();
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
                    0. Go back to edit menu""");

            System.out.print("Enter your choice: ");
            String userAnswer = scanner.nextLine();
            System.out.println();

            switch (userAnswer) {
                case "1" -> addTest();
                case "2" -> updateTest();
                case "3" -> deleteTest();
                case "4" -> showAllTests();
                case "0" -> runMenu = false;
                default -> System.out.println("Invalid menu choice. Please try again.");
            }
        }
    }

    /**
     * This method is for entering a test manually.
     * Tests are usually added after student finishies a quiz ("automatic")
     */
    public void addTest() {

        System.out.print("Enter student id associated with this test: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();

        var testStudent = new StudentRepo().getStudentByIdFromDatabase(studentId);

        if (testStudent.isPresent()) {
            System.out.print("Enter test category: ");
            String category = scanner.nextLine();

            System.out.print("Enter max score for the test: ");
            int maxScore = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter student score for the test: ");
            int studentScore = scanner.nextInt();
            scanner.nextLine();

            Test test = new Test(category, maxScore, studentScore, LocalDate.now(), testStudent.get());

            if (testRepo.persistTestToDatabase(test)) {
                System.out.println("Test was added to the database");
            } else {
                System.out.println("Test was not added to the database");
            }
        } else {
            System.out.println("No student found with id: " + studentId);
        }
    }

    public void updateTest() {
        System.out.print("Enter the id of the test you want to update: ");
        int testId = scanner.nextInt();
        scanner.nextLine();

        var testToUpdate = testRepo.getTestByIdFromDatabase(testId);

        if (testToUpdate.isPresent()) {
            System.out.print("Enter new category for the test: ");
            String updatedCategory = scanner.nextLine();
            System.out.print("Enter new max score for the test: ");
            int updatedMaxScore = scanner.nextInt();
            scanner.nextLine();

            Test test = testToUpdate.get();
            test.setCategory(updatedCategory);
            test.setMaxScore(updatedMaxScore);

            if (testRepo.mergeTestToDatabase(test)) {
                System.out.println("Test was updated in the database");
            } else {
                System.out.println("Test was not updated in the database");
            }
        } else {
            System.out.println("No test was found with id: " + testId);
        }
    }

    public void deleteTest() {
        System.out.print("Enter the id of the test you want to delete: ");
        int testId = scanner.nextInt();
        scanner.nextLine();

        var testToDelete = testRepo.getTestByIdFromDatabase(testId);

        if (testToDelete.isPresent()) {
            if (testRepo.removeTestFromDatabase(testToDelete.get())) {
                System.out.println("Test with id: " + testId + " was deleted");
            } else {
                System.out.println("Failed to remove test with id: " + testId);
            }
        } else {
            System.out.println("No test was found with id: " + testId);
        }
    }

    public void showAllTests() {
        System.out.println("---- List of All Tests ----");
        testRepo.getAllTestsFromDatabase()
                .ifPresentOrElse(
                        tests -> tests.forEach(test -> {
                            System.out.println("Test ID: " + test.getId());
                            System.out.println("Test Category: " + test.getCategory());
                            System.out.println("Max Score: " + test.getMaxScore());
                            System.out.println("Student Score: " + test.getStudentScore());
                            System.out.println("Test Date: " + test.getDate());
                            System.out.println("Student: " + test.getStudent().getName());
                            System.out.println("--------------------------");
                        }),
                        () -> System.out.println("No tests found.")
                );
    }

}
