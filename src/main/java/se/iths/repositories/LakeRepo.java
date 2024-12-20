package se.iths.repositories;

import static se.iths.repositories.JPAUtil.*;
import se.iths.entity.Lake;
import java.util.List;
import java.util.Optional;

public class LakeRepo {

    public Optional<List<Lake>> getRandomLakes(int count) {
        try {
            var randomLakes = getEntityManager()
                    .createNativeQuery("SELECT * FROM lake ORDER BY RAND() LIMIT :amount", Lake.class)
                    .setParameter("amount", count)
                    .getResultList();

            return Optional.of(randomLakes);

        } catch (Exception e) {
            return Optional.empty();
        }
    }


    // Hämta alla sjöar från databasen
    public Optional<List<Lake>> getAllLakesFromDatabase() {
        try {
            var allLakes = getEntityManager()
                    .createQuery("SELECT l FROM Lake l", Lake.class)
                    .getResultList();

            return Optional.of(allLakes);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Hitta en specifik sjö i databasen
    public Optional<Lake> getLakeByIdFromDatabase(int id) {
        try {
            var lake = getEntityManager().find(Lake.class, id);
            return Optional.ofNullable(lake);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Lägga till en ny sjö i databasen
    public boolean persistLakeToDatabase(Lake lake) {

        try {
            inTransaction(entityManager -> {
                entityManager.persist(lake);
            });
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // Ta bort en sjö från databasen
    public boolean removeLakeFromDatabase(Lake lake) {
        try {
            inTransaction(entityManager -> {
                Lake lakeToRemove = entityManager.find(Lake.class, lake.getId());
                entityManager.remove(lakeToRemove);
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Uppdatera information om en sjö i databasen
    public boolean mergeLakeInDatabase(Lake lake) {
        try {
            inTransaction(entityManager -> {
               entityManager.merge(lake);
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
