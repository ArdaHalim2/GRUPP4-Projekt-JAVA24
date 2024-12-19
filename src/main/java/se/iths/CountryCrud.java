package se.iths;

import se.iths.entity.Country;
import se.iths.repositories.CountryRepo;

import java.util.Optional;
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
                    0. Go back to edit menu
                    """);

            String userAnswer = scanner.nextLine();

            switch (userAnswer) {
                case "1" -> addCountry();
                case "2" -> updateCountry();
                case "3" -> deleteCountry();
                case "4" -> showAllCountries();
                case "0" -> runMenu = false;
                default -> System.out.println("Invalid input");
            }
        }
    }

    public void addCountry() {
        System.out.println("Enter country name: ");
        String countryName = scanner.nextLine();
        System.out.println("Enter country capital: ");
        String countryCapital = scanner.nextLine();

        Country country = new Country();
        country.setName(countryName);
        country.setCapital(countryCapital);

        if (countryRepo.persistCountryToDatabase(country)) {
            System.out.println("Country was added to the database");
        } else {
            System.out.println("Country was not added to the database");
        }
    }

    public void updateCountry() {
        System.out.println("Enter the ID of the country you want to update: ");
        int countryId = scanner.nextInt();
        scanner.nextLine();

        Optional<Country> countryOptional = countryRepo.getAllCountries()
                .flatMap(countries -> countries.stream()
                        .filter(c -> c.getId() == countryId)
                        .findFirst());

        if (countryOptional.isPresent()) {
            Country country = countryOptional.get();

            System.out.println("Enter new name for the country: ");
            String updateCountryName = scanner.nextLine();
            System.out.println("Enter new capital for the country: ");
            String updateCountryCapital = scanner.nextLine();

            country.setName(updateCountryName);
            country.setCapital(updateCountryCapital);

            boolean isUpdated = countryRepo.updateCountryInDatabase(country);

            if (isUpdated) {
                System.out.println("Country was updated in the database");
            } else {
                System.out.println("Country was not updated in the database");
            }
        } else {
            System.out.println("No country was found with the ID: " + countryId);
        }
    }

    public void deleteCountry() {
        System.out.println("Enter country ID to delete: ");
        int idToDelete = scanner.nextInt();
        scanner.nextLine();

        Optional<Country> countryToDelete = countryRepo.getAllCountries()
                .flatMap(countries -> countries.stream()
                        .filter(c -> c.getId() == idToDelete)
                        .findFirst());

        if (countryToDelete.isPresent()) {
            boolean wasDeleted = countryRepo.removeCountryFromDatabase(countryToDelete.get());

            if (wasDeleted) {
                System.out.println("Country with ID " + idToDelete + " was deleted");
            } else {
                System.out.println("Failed to remove country with ID " + idToDelete);
            }
        } else {
            System.out.println("No country was found with the ID: " + idToDelete);
        }
    }

    public void showAllCountries() {
        countryRepo.getAllCountries()
                .ifPresentOrElse(
                        countries -> countries.forEach(System.out::println),
                        () -> System.out.println("No countries found")
                );
    }
}
