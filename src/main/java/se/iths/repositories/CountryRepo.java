package se.iths.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import se.iths.entity.Country;

import java.util.List;
import java.util.Random;

public class CountryRepo {

    public List<Country> getCountriesWithCapitals(int count) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String query = "SELECT c FROM Country c WHERE c.countryCapital IS NOT NULL";
            TypedQuery<Country> typedQuery = em.createQuery(query, Country.class);
            List<Country> countries = typedQuery.getResultList();

            Random rand = new Random();
            while (countries.size() > count) {
                countries.remove(rand.nextInt(countries.size()));
            }
            return countries;
        } finally {
            em.close();
        }
    }

}
