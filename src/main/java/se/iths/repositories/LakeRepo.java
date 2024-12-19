package se.iths.repositories;

import static se.iths.repositories.JPAUtil.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Lake> query = em.createQuery("SELECT l FROM Lake l", Lake.class);
            return Optional.of(query.getResultList());
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    // Hitta en specifik sjö i databasen
    public Optional<Lake> findLakeInDatabase(Lake lake) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(Lake.class, lake.getId()));
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    // Lägga till en ny sjö i databasen
    public boolean persistLakeToDatabase(Lake lake) {
        try {
            JPAUtil.inTransaction(entityManager -> entityManager.persist(lake));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Ta bort en sjö från databasen
    public boolean removeLakeFromDatabase(Lake lake) {
        try {
            JPAUtil.inTransaction(entityManager -> {
                Lake toRemove = entityManager.find(Lake.class, lake.getId());
                if (toRemove != null) {
                    entityManager.remove(toRemove);
                }
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Uppdatera information om en sjö i databasen
    public boolean mergeLakeInDatabase(Lake lake) {
        try {
            JPAUtil.inTransaction(entityManager -> entityManager.merge(lake));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
