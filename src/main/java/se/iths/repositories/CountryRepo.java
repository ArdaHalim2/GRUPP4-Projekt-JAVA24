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

//        public List<Country> getRandomCountries(int count) {
//        EntityManager em = JPAUtil.getEntityManager();
//
//            try {
//
//                String query = "SELECT c FROM Country c WHERE c.countryCapital IS NOT NULL";
//                TypedQuery<Country> typedQuery = em.createQuery(query, Country.class);
//                List<Country> countries = typedQuery.getResultList();
//
//                Random rand = new Random();
//                while (countries.size() > count) {
//                    countries.remove(rand.nextInt(countries.size()));
//                }
//                return countries;
//        } finally {
//            em.close();
//        }
//    }
}
