package se.iths.crud;

import se.iths.Main;
import se.iths.entity.Test;
import se.iths.repositories.TestRepo;
import se.iths.repositories.StudentRepo;
import java.time.LocalDate;

public class TestCrud {

    private final TestRepo testRepo = new TestRepo();

    public void testCrudMenu() {

        boolean runMenu = true;

        while (runMenu) {

            System.out.println("""
                    
                    \tCRUD TEST
                    
                    \t1. Add Test
                    \t2. Update Test
                    \t3. Delete Test
                    \t4. Show all tests
                    \t0. Go back to edit menu""");

            String userAnswer = Main.getValidString("Enter your choice: ");

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

        int studentId = Main.getValidInt("Enter student id associated with this test: ");

        var testStudent = new StudentRepo().getStudentByIdFromDatabase(studentId);

        if (testStudent.isPresent()) {
            String category = Main.getValidString("Enter test category: ");
            int maxScore = Main.getValidInt("Enter max score for the test: ");
            int studentScore = Main.getValidInt("Enter student score for the test: ");

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
        int testId = Main.getValidInt("Enter the id of the test you want to update: ");

        var testToUpdate = testRepo.getTestByIdFromDatabase(testId);

        if (testToUpdate.isPresent()) {
            String updatedCategory = Main.getValidString("Enter new category for the test: ");

            Test test = testToUpdate.get();
            test.setCategory(updatedCategory);

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
        int testId = Main.getValidInt("Enter the id of the test you want to delete: ");

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
