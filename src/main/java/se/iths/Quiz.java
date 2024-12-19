package se.iths;

import se.iths.entity.*;
import se.iths.repositories.CityRepo;
import se.iths.repositories.CountryRepo;
import se.iths.repositories.LakeRepo;
import se.iths.repositories.TestRepo;

import java.time.LocalDate;
import java.util.Random;
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

        Random rand = new Random();

        switch (rand.nextInt(3)) {
            case 0 -> getCapitalQuestion();
            case 1 -> getLakeQuestion();
            case 2 -> getCitiesQuestion();
        }


    }

    public void getCapitalQuestion() {

        Test test = new Test("capitals", 5, LocalDate.now(), currentStudent);

        var randomCountries = countryRepo.getRandomCountries(5);

        if (randomCountries.isPresent()) {
            for (Country country : randomCountries.get()) {
                System.out.println("What is the capital of " + country.getName() + "?");
                String userAnswer = scanner.nextLine();
                checkRightAnswer(userAnswer, country.getCapital(), test);
            }

            
            addTestToDatabase(test);
        }

    }

    public void getLakeQuestion() {

        Test test = new Test("lakes", 5, LocalDate.now(), currentStudent);

        var randomLakes = lakeRepo.getRandomLakes(5);

        if (randomLakes.isPresent()) {
            for (Lake lake : randomLakes.get()) {
                System.out.println("In which country is the lake " + lake.getName() + " located?");
                String userAnswer = scanner.nextLine();
                checkRightAnswer(userAnswer, lake.getCountry().getName(), test);
            }

            addTestToDatabase(test);
        }

    }

    public void getCitiesQuestion() {

        Test test = new Test("cities", 5, LocalDate.now(), currentStudent);

        var randomCitys = cityRepo.getRandomCities(5);

        if (randomCitys.isPresent()) {
            for (City city : randomCitys.get()) {
                System.out.println("In what country is " + city.getName() + " located?");
                String userAnswer = scanner.nextLine();
                checkRightAnswer(userAnswer, city.getCountry().getName(), test);
            }

            addTestToDatabase(test);
        }
    }

    public void checkRightAnswer(String userAnswer, String rightAnswer, Test test) {
        if (userAnswer.equals(rightAnswer)) {
            System.out.println("Right answer!");
            test.incrementStudentScoreByOne();
        } else {
            System.out.println("Wrong answer!");
        }
    }

    public void addTestToDatabase(Test test) {
        var wasAdded = testRepo.persistTestToDatabase(test);

        if(wasAdded) {
            System.out.println("Test was added to the database!");
        } else {
            System.out.println("Test was not added to the database!");
        }
    }

}
