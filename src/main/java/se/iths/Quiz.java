package se.iths;

import se.iths.entity.*;
import se.iths.repositories.CityRepo;
import se.iths.repositories.CountryRepo;
import se.iths.repositories.LakeRepo;
import se.iths.repositories.TestRepo;

import java.time.LocalDate;
import java.util.Scanner;

public class Quiz {

    private Scanner scanner;
    private Student currentStudent;
    private CountryRepo countryRepo = new CountryRepo();
    private LakeRepo lakeRepo = new LakeRepo();
    private TestRepo testRepo = new TestRepo();
    private CityRepo cityRepo = new CityRepo();


    public Quiz(Scanner scanner, Student currentStudent) {
        this.scanner = scanner;
        this.currentStudent = currentStudent;
    }

    public void quizMenu() {

        if (currentStudent == null) {
            System.out.println("Login required.");
            return;
        }

//        Random rand = new Random();
//
//        switch (rand.nextInt(3)) {
//            case 0 -> getCapitalQuestion();
//            case 1 -> getLakeQuestion();
//            case 2 -> getCitiesQuestion();
//        }

        boolean runMenu = true;

        while (runMenu) {

            System.out.println("""
                    
                    QUIZ MENU
                    Choose quiz category:
                    
                    1. Capitals
                    2. Lakes
                    3. Cities
                    0. Go back to main menu""");

            System.out.print("Enter your choice: ");
            String userChoice = scanner.nextLine();
            System.out.println();

            switch (userChoice) {
                case "1" -> getCapitalQuestions(5);
                case "2" -> getLakeQuestions(5);
                case "3" -> getCityQuestions(5);
                case "0" -> runMenu = false;
                default -> System.out.println("Invalid menu choice. Please try again");
            }
        }
    }

    private void getCapitalQuestions(int amountOfQuestions) {

        int studentScore = 0;

        var randomCountries = countryRepo.getRandomCountries(amountOfQuestions);

        if (randomCountries.isPresent()) {

            for (Country country : randomCountries.get()) {
                System.out.println("What is the capital of " + country.getName() + "?");
                String userAnswer = scanner.nextLine();
                if (checkForRightAnswer(userAnswer, country.getCapital())) {
                    studentScore++;
                }
            }

            createNewTestAndAddToStudentAndToDatabase("capitals", amountOfQuestions, studentScore);
        }

    }

    private void getLakeQuestions(int amountOfQuestions) {

        int studentScore = 0;

        var randomLakes = lakeRepo.getRandomLakes(amountOfQuestions);

        if (randomLakes.isPresent()) {
            for (Lake lake : randomLakes.get()) {
                System.out.println("In which country is the lake " + lake.getName() + " located?");
                String userAnswer = scanner.nextLine();
                if (checkForRightAnswer(userAnswer, lake.getCountry().getName())) {
                    studentScore++;
                }
            }

            createNewTestAndAddToStudentAndToDatabase("lakes", amountOfQuestions, studentScore);
        }

    }

    private void getCityQuestions(int amountOfQuestions) {

        int studentScore = 0;

        var randomCities = cityRepo.getRandomCities(amountOfQuestions);

        if (randomCities.isPresent()) {
            for (City city : randomCities.get()) {
                System.out.println("In what country is " + city.getName() + " located?");
                String userAnswer = scanner.nextLine();
                if (checkForRightAnswer(userAnswer, city.getCountry().getName())) {
                    studentScore++;  // inkrementerar resutatet när studenten svarar rätt
                }
            }

            createNewTestAndAddToStudentAndToDatabase("cities", amountOfQuestions, studentScore);
        }
    }


    private boolean checkForRightAnswer(String userAnswer, String rightAnswer) {
        if (userAnswer.trim().equalsIgnoreCase(rightAnswer.trim())) { // Trim för att ta bort oönskade mellanslag
            System.out.println("Right answer!\n");
            return true;
        } else {
            System.out.println("Wrong answer! The correct answer is: " + rightAnswer + "\n");
            return false;
        }
    }

    private void createNewTestAndAddToStudentAndToDatabase(String category, int maxScore, int studentScore) {

        // Add new test entity
        Test test = new Test(category, maxScore, studentScore, LocalDate.now(), currentStudent);

        if (testRepo.persistTestToDatabase(test)) {
            System.out.println("Test was added to the database!\n");
        } else {
            System.out.println("Test was not added to the database!\n");
        }

    }

}
