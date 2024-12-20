package se.iths.crud;

import se.iths.entity.Country;
import se.iths.repositories.CountryRepo;

import java.util.Scanner;

public class CountryCrud {

    private final CountryRepo countryRepo = new CountryRepo();
    private Scanner scanner;

    public CountryCrud(Scanner scanner) {
        this.scanner = scanner;
    }

    public void countryCrudMenu() {

        boolean runMenu = true;

        while (runMenu) {

            System.out.println("""
                    
                    CRUD COUNTRY
                    
                    1. Add Country
                    2. Update Country
                    3. Delete Country
                    4. Show all countries
                    0. Go back to edit menu""");

            System.out.print("Enter your choice: ");
            String userAnswer = scanner.nextLine();
            System.out.println();

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
        System.out.print("Enter country name: ");
        String countryName = scanner.nextLine();
        System.out.print("Enter country capital: ");
        String countryCapital = scanner.nextLine();

        Country country = new Country(countryName, countryCapital);

        if (countryRepo.persistCountryToDatabase(country)) {
            System.out.println("Country was added to the database");
        } else {
            System.out.println("Country was not added to the database");
        }
    }

    public void updateCountry() {
        System.out.print("Enter the id of the country you want to update: ");
        int countryId = scanner.nextInt();
        scanner.nextLine();

        var countryToUpdate = countryRepo.getCountryByIdFromDatabase(countryId);

        if (countryToUpdate.isPresent()) {
            System.out.print("Enter new name for the country: ");
            String updateCountryName = scanner.nextLine();
            System.out.print("Enter new capital for the country: ");
            String updateCountryCapital = scanner.nextLine();

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
        System.out.print("Enter the id of the country you want to delete: ");
        int countryId = scanner.nextInt();
        scanner.nextLine();

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
