package se.iths;

import se.iths.entity.City;
import se.iths.repositories.CityRepo;
import java.util.Scanner;

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
                case "3" -> deleteCity();
                case "4" -> showAllCities();
                case "0" -> runMenu = false;
                default -> System.out.println("Invalid menu choice. Please try again.");
            }
        }
    }

    public void addCity() {

        System.out.println("Enter city name: ");
        String cityName = scanner.nextLine();

        System.out.println("Enter city country id: ");
        int cityCountry = scanner.nextInt();
        scanner.nextLine();
        City city = new City(cityName, new);

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

        var cityOptional = cityRepo.getCityByIdFromDatabase(cityId);

        if (cityOptional.isPresent()) {

            var city = cityOptional.get();

            System.out.println("Enter new name for the city: ");
            String updateCityName = scanner.nextLine();

            city.setName(updateCityName);

            boolean isUpdated = cityRepo.mergeCityInDatabase(city);

            if (isUpdated) {
                System.out.println("City was updated in the database");
            } else {
                System.out.println("City was not updated in the database");
            }
        } else {
            System.out.println("No city was found wit the id: " + cityId);
        }
    }

    public void deleteCity() {
        System.out.println("Enter city ID to delete: ");
        int idToDelete = scanner.nextInt();

        var cityOptional = cityRepo.getCityByIdFromDatabase(idToDelete);

        if (cityOptional.isPresent()) {
            boolean wasDeleted = cityRepo.removeCityFromDatabase(cityOptional.get());

            if (wasDeleted) {
                System.out.println("City with id: " + idToDelete + " was deleted");
            } else {
                System.out.println("Failed to delete city with id: " + idToDelete);
            }
        } else {
            System.out.println("No city was found with the id: " + idToDelete);
        }
    }

    public void showAllCities() {
        cityRepo.getAllCitiesFromDatabase()
                .ifPresentOrElse(
                        city -> city.forEach(System.out::println),
                        () -> System.out.println("No cities was found.")
                );
    }
}
