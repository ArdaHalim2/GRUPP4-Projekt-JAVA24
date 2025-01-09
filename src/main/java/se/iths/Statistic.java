package se.iths;

import se.iths.entity.Student;
import se.iths.entity.Test;
import se.iths.repositories.*;

import java.util.*;

public class Statistic {

    private Student currentStudent;
    private TestRepo testRepo = new TestRepo();
    private StudentRepo studentRepo = new StudentRepo();


    public Statistic(Student currentStudent) {
        this.currentStudent = currentStudent;
    }

    public void statisticMenu() {

        if (currentStudent == null) {
            System.out.println("Login required.");
            return;
        }

        boolean runMenu = true;

        while (runMenu) {

            System.out.println("""
                    
                    \tSTATISTIC MENU
                    
                    \t1. Show top 5 quiz results by quiz category
                    \t2. Show average quiz result per student
                    \t3. Show average quiz result per quiz category
                    \t0. Go back to main menu""");

            String userChoice = Main.getValidString("\tEnter your choice: ");

            switch (userChoice) {
                case "1" -> showTopFiveTestResultsByCategory();
                case "2" -> showAllStudentsAverageTestScoreInPercent();
                case "3" -> showTestCategoriesAverageTestScoreInPercent();
                case "0" -> runMenu = false;
                default -> System.out.println("Invalid menu choice. Please try again");
            }
        }
    }

    private void showTopFiveTestResultsByCategory(){

        var allTestCategoriesAvailable = testRepo.getDistinctCategoriesFromDatabase();

        if(allTestCategoriesAvailable.isPresent()){

            var allTestCategories = allTestCategoriesAvailable.get();

            for(String category : allTestCategories){
                var amountOfTestsByCategoryOptional = testRepo.getAmountOfTestsByCategoryOrderedByStudentScoreDescFromDatabase(category, 5);

                if(amountOfTestsByCategoryOptional.isPresent()){
                    var amountOfTestsByCategory = amountOfTestsByCategoryOptional.get();

                    if(!amountOfTestsByCategory.isEmpty()){
                        System.out.printf("\n%-10s %-20s %-20s %-20s %-20s %-20s %-30s", "Test id:", "Test category:", "Test max score:", "Student score:", "Student name:", "Student id:", "Test Date: ");

                        for(var test : amountOfTestsByCategory){
                            System.out.printf("\n%-10s %-20s %-20s %-20s %-20s %-20s %-30s", test.getId(), test.getCategory(), test.getMaxScore(), test.getStudentScore(), test.getStudent().getName(), test.getStudent().getId(), test.getDate().toString());
                        }
                        System.out.println();
                    } else {
                        System.out.println("No quiz category with that name was found.");
                    }
                    System.out.println();
                }
            }

            // Nedan är om man själv vill välja kategori att visa.
//
//            System.out.println("\n\tCATEGORIES IN DATABASE: ");
//            allTestCategoriesAvailable.get().forEach(category -> System.out.println("\t- " + category));
//
//            System.out.print("\tWrite the name of the category you want to view the results for: ");
//            String category = scanner.nextLine();
//            System.out.println();
//
//            var amountOfTestsByCategoryOptional = testRepo.getAmountOfTestsByCategoryOrderedByStudentScoreDescFromDatabase(category, 5);
//
//            if(amountOfTestsByCategoryOptional.isPresent()){
//                var amountOfTestsByCategory = amountOfTestsByCategoryOptional.get();
//
//                if(!amountOfTestsByCategory.isEmpty()){
//                    System.out.printf("\n%-10s %-20s %-20s %-20s %-20s %-20s %-30s", "Test id:", "Test category:", "Test max score:", "Student score:", "Student name:", "Student id:", "Test Date: ");
//
//                    for(var test : amountOfTestsByCategory){
//                        System.out.printf("\n%-10s %-20s %-20s %-20s %-20s %-20s %-30s", test.getId(), test.getCategory(), test.getMaxScore(), test.getStudentScore(), test.getStudent().getName(), test.getStudent().getId(), test.getDate().toString());
//                    }
//                    System.out.println();
//                } else {
//                    System.out.println("No quiz category with that name was found. Please try again.");
//                }
//            }
        } else {
            System.out.println("No quiz results were found in database.");
        }
    }

    private void showAllStudentsAverageTestScoreInPercent(){

        var allStudentsOptional = studentRepo.getAllStudentsFromDatabase();

        if(allStudentsOptional.isPresent()){

            var allStudents = allStudentsOptional.get();
            record StudentAverageScore(Student student, double testScoreInAveragePercent){}
            List<StudentAverageScore> studentAverageScores = new ArrayList<>();

            for (Student student : allStudents) {

                // Beräkna och visa enbart studenter som har gjort ett eller flera test.
                if(!student.getTests().isEmpty()) {
                    double studentTestScoreInPercentTotalSum = 0;

                    for (Test test : student.getTests()) {
                        studentTestScoreInPercentTotalSum += ((double) test.getStudentScore() / test.getMaxScore()) * 100;
                    }

                    double studentTestScoreInPercentAverage = studentTestScoreInPercentTotalSum / student.getTests().size();
                    studentAverageScores.add(new StudentAverageScore(student, studentTestScoreInPercentAverage));
                }
            }

            studentAverageScores.sort(Comparator.comparing(StudentAverageScore::testScoreInAveragePercent).reversed());

            System.out.printf("\n%-15s %-20s %-15s", "Student id:", "Student name:", "Average result:");
            for(StudentAverageScore studentAverageScore: studentAverageScores){
                System.out.printf("\n%-15d %-20s %-2.0f %-1s", studentAverageScore.student.getId(), studentAverageScore.student.getName(), studentAverageScore.testScoreInAveragePercent, "%");
            }
            System.out.println();
        }
    }

    private void showTestCategoriesAverageTestScoreInPercent(){

        var allTestCategoriesAvailable = testRepo.getDistinctCategoriesFromDatabase();

        if(allTestCategoriesAvailable.isPresent()){

            var allTestCategories = allTestCategoriesAvailable.get();
            record CategoryAverageScore(String category, double testScoreInAveragePercent){}
            List<CategoryAverageScore> categoryAverageScores = new ArrayList<>();

            for(String category : allTestCategories) {

                var testsByCategoryOptional = testRepo.getAmountOfTestsByCategoryOrderedByStudentScoreDescFromDatabase(category, Integer.MAX_VALUE);

                if(testsByCategoryOptional.isPresent()){

                    var testsByCategory = testsByCategoryOptional.get();

                    double categoryTestScoreInPercentTotalSum = 0;

                    for (Test test : testsByCategory) {
                        categoryTestScoreInPercentTotalSum += ((double) test.getStudentScore() / test.getMaxScore()) * 100;
                    }

                    double testScoreInPercentAverage = categoryTestScoreInPercentTotalSum / testsByCategory.size();
                    categoryAverageScores.add(new CategoryAverageScore(category, testScoreInPercentAverage));
                }
            }

            categoryAverageScores.sort(Comparator.comparing(CategoryAverageScore::testScoreInAveragePercent).reversed());

            System.out.printf("\n%-15s %-15s", "Category:", "Average result:");
            for(CategoryAverageScore categoryAverageScore: categoryAverageScores){
                System.out.printf("\n%-15s %-2.0f %-1s", categoryAverageScore.category, categoryAverageScore.testScoreInAveragePercent, "%");
            }
            System.out.println();
        }
    }
}
