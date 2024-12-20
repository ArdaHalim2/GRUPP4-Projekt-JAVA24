package se.iths.repositories;

import static se.iths.repositories.JPAUtil.*;
import se.iths.entity.Country;
import java.util.List;
import java.util.Optional;

public class CountryRepo {

    public Optional<List<Country>> getRandomCountries(int count) {
        try {
            var randomCountries = getEntityManager()
                    .createNativeQuery("SELECT * FROM country ORDER BY RAND() LIMIT :amount", Country.class)
                    .setParameter("amount", count)
                    .getResultList();

            return Optional.of(randomCountries);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Hämta alla länder från databasen
    public Optional<List<Country>> getAllCountries() {
        try {
            var allCountriesList = getEntityManager()
                    .createQuery("SELECT c FROM Country c", Country.class)
                    .getResultList();

            return Optional.of(allCountriesList);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Country> getCountryByIdFromDatabase(int id) {
        try {
            var country = getEntityManager().find(Country.class, id);
            return Optional.of(country);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Hämta alla länder by name
    public Optional<Country> getCountryByName(String name) {
        try {
            var country = getEntityManager()
                    .createQuery("SELECT c FROM Country c WHERE c.name = :name", Country.class)
                    .setParameter("name", name)
                    .getSingleResult();

            return Optional.of(country);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // lägg till land
    public boolean persistCountryToDatabase(Country country) {
        try {
            inTransaction(entityManager -> {
                entityManager.persist(country);
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Ta bort ett land
    public boolean removeCountryFromDatabase(Country country) {

        try {
            inTransaction(entityManager -> {
                var countryToRemove = entityManager.find(Country.class, country.getId());
                entityManager.remove(countryToRemove);
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // uppdatera land
    public boolean updateCountryInDatabase(Country country) {
        try {
            inTransaction(entityManager -> {
                entityManager.merge(country);
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
