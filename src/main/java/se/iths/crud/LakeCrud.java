package se.iths.crud;

import se.iths.Main;
import se.iths.entity.Lake;
import se.iths.repositories.CountryRepo;
import se.iths.repositories.LakeRepo;

public class LakeCrud {

    private final LakeRepo lakeRepo = new LakeRepo();

    public void lakeCrudMenu() {

        boolean runMenu = true;

        while (runMenu) {

            System.out.println("""
                    
                    \tCRUD LAKE
                    
                    \t1. Add Lake
                    \t2. Update Lake
                    \t3. Delete Lake
                    \t4. Show all Lakes
                    \t0. Go back to edit menu""");

            String userAnswer = Main.getValidString("\tEnter your choice: ");

            switch (userAnswer) {
                case "1" -> addLake();
                case "2" -> updateLake();
                case "3" -> deleteLake();
                case "4" -> showAllLakes();
                case "0" -> runMenu = false;
                default -> System.out.println("Invalid menu choice. Please try again.");
            }
        }
    }

    public void addLake() {

        int countryId = Main.getValidInt("Enter country id for the lake: ");

        var lakeCountry = new CountryRepo().getCountryByIdFromDatabase(countryId);

        if (lakeCountry.isPresent()) {
            String lakeName = Main.getValidString("Enter lake name: ");

            Lake lake = new Lake(lakeName, lakeCountry.get());

            if (lakeRepo.persistLakeToDatabase(lake)) {
                System.out.println("Lake was added to the database.");
            } else {
                System.out.println("Lake was not added to the database.");
            }
        } else {
            System.out.println("Country with id: " + countryId + " was not found.");
        }
    }

    public void updateLake() {
        int lakeId = Main.getValidInt("Enter the id of the lake you want to update: ");

        var lakeToUpdate = lakeRepo.getLakeByIdFromDatabase(lakeId);

        if (lakeToUpdate.isPresent()) {
            String updatedName = Main.getValidString("Enter new name for the lake: ");

            Lake lake = lakeToUpdate.get();
            lake.setName(updatedName);

            if (lakeRepo.mergeLakeInDatabase(lake)) {
                System.out.println("Lake was updated in the database.");
            } else {
                System.out.println("Lake was not updated in the database.");
            }
        } else {
            System.out.println("No lake was found with id: " + lakeId);
        }
    }


    public void deleteLake() {
        int lakeId = Main.getValidInt("Enter the id of the lake you want to delete: ");

        var lakeToDelete = lakeRepo.getLakeByIdFromDatabase(lakeId);

        if (lakeToDelete.isPresent()) {
            if (lakeRepo.removeLakeFromDatabase(lakeToDelete.get())) {
                System.out.println("Lake with id: " + lakeId + " was deleted.");
            } else {
                System.out.println("Failed to delete lake with id: " + lakeId + ".");
            }
        } else {
            System.out.println("No lake found with id: " + lakeId);
        }
    }


    public void showAllLakes() {
        System.out.println("---- List of All Lakes ----");
        lakeRepo.getAllLakesFromDatabase().get().forEach(lake -> {
            System.out.println("Lake ID: " + lake.getId());
            System.out.println("Lake Name: " + lake.getName());
            System.out.println("Country: " + lake.getCountry().getName());
            System.out.println("----------------------------");
        });
    }

}
