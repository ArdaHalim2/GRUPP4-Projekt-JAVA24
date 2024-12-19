package se.iths;

import se.iths.entity.Lake;
import se.iths.entity.Country;
import se.iths.repositories.LakeRepo;

import java.util.Optional;
import java.util.Scanner;

public class LakeCrud {

    private final LakeRepo lakeRepo = new LakeRepo();
    private final Scanner scanner;

    public LakeCrud(Scanner scanner) {
        this.scanner = scanner;
    }

    public void lakeCrudMenu() {
        boolean runMenu = true;
        while (runMenu) {
            System.out.println("""
                    CRUD LAKE
                    
                    1. Add Lake
                    2. Update Lake
                    3. Delete Lake
                    4. Show all Lakes
                    0. Go back to main menu
                    """);

            String userAnswer = scanner.nextLine();

            switch (userAnswer) {
                case "1" -> addLake();
                case "2" -> updateLake();
                case "3" -> deleteLake();
                case "4" -> showAllLakes();
                case "0" -> runMenu = false;
                default -> System.out.println("Invalid input");
            }
        }
    }

    public void addLake() {
        System.out.println("Enter lake name: ");
        String lakeName = scanner.nextLine();

        System.out.println("Enter country ID for the lake: ");
        int countryId = scanner.nextInt();
        scanner.nextLine(); // Rensa scanner

        Country country = new Country();
        country.setId(countryId);

        Lake lake = new Lake();
        lake.setLakeName(lakeName);
        lake.setLakeCountry(country);

        if (lakeRepo.persistLakeToDatabase(lake)) {
            System.out.println("Lake was added to the database.");
        } else {
            System.out.println("Lake was not added to the database.");
        }
    }

    public void updateLake() {
        System.out.println("Enter the ID of the lake you want to update: ");
        int lakeId = scanner.nextInt();
        scanner.nextLine(); // Rensa scanner

        Optional<Lake> lakeOptional = lakeRepo.findLakeInDatabase(new Lake() {{
            setId(lakeId);
        }});

        if (lakeOptional.isPresent()) {
            Lake lake = lakeOptional.get();

            System.out.println("Enter new name for the lake: ");
            String updatedName = scanner.nextLine();

            lake.setLakeName(updatedName);

            boolean isUpdated = lakeRepo.mergeLakeInDatabase(lake);

            if (isUpdated) {
                System.out.println("Lake was updated in the database.");
            } else {
                System.out.println("Lake was not updated in the database.");
            }
        } else {
            System.out.println("No lake found with the ID: " + lakeId);
        }
    }

    public void deleteLake() {
        System.out.println("Enter the ID of the lake to delete: ");
        int lakeId = scanner.nextInt();
        scanner.nextLine(); // Rensa scanner

        Optional<Lake> lakeOptional = lakeRepo.findLakeInDatabase(new Lake() {{
            setId(lakeId);
        }});

        if (lakeOptional.isPresent()) {
            boolean wasDeleted = lakeRepo.removeLakeFromDatabase(lakeOptional.get());

            if (wasDeleted) {
                System.out.println("Lake " + lakeId + " was deleted.");
            } else {
                System.out.println("Failed to delete lake " + lakeId + ".");
            }
        } else {
            System.out.println("No lake found with the ID: " + lakeId);
        }
    }

    public void showAllLakes() {
        lakeRepo.getAllLakesFromDatabase()
                .ifPresentOrElse(
                        lakes -> lakes.forEach(System.out::println),
                        () -> System.out.println("No lakes found.")
                );
    }
}
