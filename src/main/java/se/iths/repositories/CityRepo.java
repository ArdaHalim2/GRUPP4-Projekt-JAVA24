package se.iths.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import se.iths.entity.City;

import java.util.List;
import java.util.Optional;

public class CityRepo {

    // Hämta alla städer från databasen
    public Optional<List<City>> getAllCitiesFromDatabase() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<City> query = em.createQuery("SELECT c FROM City c", City.class);
            return Optional.of(query.getResultList());
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    // Hämta en specifik stad från databasen
    public Optional<City> findCityInDatabase(City city) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return Optional.ofNullable(em.find(City.class, city.getId()));
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            em.close();
        }
    }

    // Lägga till en ny stad i databasen
    public boolean persistCityToDatabase(City city) {
        try {
            JPAUtil.inTransaction(entityManager -> entityManager.persist(city));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Ta bort en stad från databasen
    public boolean removeCityFromDatabase(City city) {
        try {
            JPAUtil.inTransaction(entityManager -> {
                City toRemove = entityManager.find(City.class, city.getId());
                if (toRemove != null) {
                    entityManager.remove(toRemove);
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Uppdatera information om en stad i databasen
    public boolean mergeCityInDatabase(City city) {
        try {
            JPAUtil.inTransaction(entityManager -> entityManager.merge(city));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
