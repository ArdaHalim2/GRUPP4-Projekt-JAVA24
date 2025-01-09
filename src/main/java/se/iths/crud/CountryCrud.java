package se.iths.crud;

import se.iths.Main;
import se.iths.entity.Country;
import se.iths.repositories.CountryRepo;

public class CountryCrud {

    private final CountryRepo countryRepo = new CountryRepo();

    public void countryCrudMenu() {

        boolean runMenu = true;

        while (runMenu) {

            System.out.println("""
                    
                    \tCRUD COUNTRY
                    
                    \t1. Add Country
                    \t2. Update Country
                    \t3. Delete Country
                    \t4. Show all countries
                    \t0. Go back to edit menu""");

            String userAnswer = Main.getValidString("\tEnter your choice: ");

            switch (userAnswer) {
                case "1" -> addCountry();
                case "2" -> updateCountry();
                case "3" -> deleteCountry();
                case "4" -> showAllCountries();
                case "0" -> runMenu = false;
                default -> System.out.println("Invalid menu choice. Please try again.");
            }
        }
    }

    public void addCountry() {
        String countryName = Main.getValidString("Enter country name: ");
        String countryCapital = Main.getValidString("Enter country capital: ");

        Country country = new Country(countryName, countryCapital);

        if (countryRepo.persistCountryToDatabase(country)) {
            System.out.println("Country was added to the database");
        } else {
            System.out.println("Country was not added to the database");
        }
    }

    public void updateCountry() {
        int countryId = Main.getValidInt("Enter the id of the country you want to update: ");

        var countryToUpdate = countryRepo.getCountryByIdFromDatabase(countryId);

        if (countryToUpdate.isPresent()) {
            String updateCountryName = Main.getValidString("Enter new name for the country: ");
            String updateCountryCapital = Main.getValidString("Enter new capital for the country: ");

            Country country = countryToUpdate.get();
            country.setName(updateCountryName);
            country.setCapital(updateCountryCapital);

            if (countryRepo.updateCountryInDatabase(country)) {
                System.out.println("Country was updated in the database");
            } else {
                System.out.println("Country was not updated in the database");
            }
        } else {
            System.out.println("No country was found with id: " + countryId);
        }
    }


    public void deleteCountry() {
        int countryId = Main.getValidInt("Enter the id of the country you want to delete: ");

        var countryToDelete = countryRepo.getCountryByIdFromDatabase(countryId);

        if (countryToDelete.isPresent()) {
            if (countryRepo.removeCountryFromDatabase(countryToDelete.get())) {
                System.out.println("Country with id: " + countryId + " was deleted");
            } else {
                System.out.println("Failed to delete country with id: " + countryId);
            }
        } else {
            System.out.println("No country was found with id: " + countryId);
        }
    }


    public void showAllCountries() {
        System.out.println("---- List of All Countries ----");
        countryRepo.getAllCountries().get().forEach(country -> {
            System.out.println("ID: " + country.getId());
            System.out.println("Name: " + country.getName());
            System.out.println("Capital: " + country.getCapital());
            System.out.println("Cities: ");
            country.getCities().forEach(city -> System.out.println("  - " + city.getName()));
            System.out.println("Lakes: ");
            country.getLakes().forEach(lake -> System.out.println("  - " + lake.getName()));
            System.out.println("------------------------------");
        });
    }

}
