package se.iths.repositories;

import se.iths.entity.City;
import java.util.List;
import java.util.Optional;
import static se.iths.repositories.JPAUtil.*;

public class CityRepo {

    public Optional<List<City>> getRandomCities(int count) {
        try {
            var randomCities = getEntityManager()
                    .createNativeQuery("SELECT * FROM city ORDER BY RAND() LIMIT :amount", City.class)
                    .setParameter("amount", count)
                    .getResultList();

            return Optional.of(randomCities);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Hämta alla städer från databasen
    public Optional<List<City>> getAllCitiesFromDatabase() {
        try {
            var cities = getEntityManager().
                    createQuery("SELECT c FROM City c", City.class)
                    .getResultList();

            return Optional.of(cities);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Hämta en specifik stad från databasen
    public Optional<City> getCityByIdFromDatabase(int id) {
        try {
            var city = getEntityManager().find(City.class, id);
            return Optional.of(city);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Lägga till en ny stad i databasen
    public boolean persistCityToDatabase(City city) {
        
        try {
            inTransaction(entityManager -> {
                entityManager.persist(city);
            });
            return true;

        } catch (Exception e){
            return false;
        }
    }

    // Ta bort en stad från databasen
    public boolean removeCityFromDatabase(City city) {

        try {
            inTransaction(entityManager -> {
                City toRemove = entityManager.find(City.class, city.getId());
                entityManager.remove(toRemove);
            });
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    // Uppdatera information om en stad i databasen
    public boolean mergeCityInDatabase(City city) {

        try {
            inTransaction(entityManager -> {
                entityManager.merge(city);
            });
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
