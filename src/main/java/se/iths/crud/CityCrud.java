package se.iths.crud;

import se.iths.Main;
import se.iths.entity.City;
import se.iths.repositories.CityRepo;
import se.iths.repositories.CountryRepo;

public class CityCrud {

    private final CityRepo cityRepo = new CityRepo();

    public void cityCrudMenu() {

        boolean runMenu = true;

        while (runMenu) {
            System.out.println("""
                    
                    \tCRUD CITY
                    
                    \t1. Add City
                    \t2. Update City
                    \t3. Delete City
                    \t4. Show all cities
                    \t0. Go back to edit menu""");

            String userAnswer = Main.getValidString("\tEnter your choice: ");

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
        int countryId = Main.getValidInt("Enter country id for the city to add: ");

        var cityCountry = new CountryRepo().getCountryByIdFromDatabase(countryId);

        if (cityCountry.isPresent()) {
            String cityName = Main.getValidString("Enter city name: ");

            City city = new City(cityName, cityCountry.get());

            if (cityRepo.persistCityToDatabase(city)) {
                System.out.println("City was added to the database.");
            } else {
                System.out.println("City was not added to the database.");
            }
        } else {
            System.out.println("Country with id: " + countryId + " was not found.");
        }
    }

    public void updateCity() {
        int cityId = Main.getValidInt("Enter city id for the city to update: ");

        var cityToUpdate = cityRepo.getCityByIdFromDatabase(cityId);

        if (cityToUpdate.isPresent()) {
            String updatedCityName = Main.getValidString("Enter the new city name: ");

            City city = cityToUpdate.get();
            city.setName(updatedCityName);

            if (cityRepo.mergeCityInDatabase(city)) {
                System.out.println("City was updated.");
            } else {
                System.out.println("City was not updated.");
            }
        } else {
            System.out.println("City with id: " + cityId + " was not found.");
        }
    }

    public void deleteCity() {
        int idToDelete = Main.getValidInt("Enter the id of the city you want to delete: ");

        var cityToDelete = cityRepo.getCityByIdFromDatabase(idToDelete);

        if (cityToDelete.isPresent()) {
            if (cityRepo.removeCityFromDatabase(cityToDelete.get())) {
                System.out.println("City with id: " + idToDelete + " was deleted");
            } else {
                System.out.println("Failed to delete city with id: " + idToDelete);
            }
        } else {
            System.out.println("No city was found with id: " + idToDelete);
        }
    }


    public void showAllCities() {
        System.out.println("---- List of All Cities ----");
        cityRepo.getAllCitiesFromDatabase().get().forEach(city -> {
            System.out.println("City ID: " + city.getId());
            System.out.println("City Name: " + city.getName());
            System.out.println("Country: " + city.getCountry().getName());
            System.out.println("------------------------------");
        });
    }

}
