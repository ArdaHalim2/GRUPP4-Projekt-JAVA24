package se.iths.repositories;

import static se.iths.repositories.JPAUtil.*;
import se.iths.entity.Test;
import se.iths.entity.Student;
import java.util.List;
import java.util.Optional;

public class TestRepo {

    public Optional<List<Test>> getAllTestsFromDatabase() {
        try {
            var allTestsList = getEntityManager()
                    .createQuery("SELECT t FROM Test t", Test.class)
                    .getResultList();

            return Optional.ofNullable(allTestsList);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<List<Test>> getAmountOfTestsByCategoryOrderedByStudentScoreDescFromDatabase(String category, int amount) {
        try {
            var allTestsList = getEntityManager()
                    .createQuery("SELECT t FROM Test t WHERE t.category = :category ORDER BY t.studentScore DESC", Test.class)
                    .setParameter("category", category)
                    .setMaxResults(amount)
                    .getResultList();

            return Optional.ofNullable(allTestsList);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<List<String>> getDistinctCategoriesFromDatabase() {
        try {
            var allCategoriesList = getEntityManager()
                    .createQuery("SELECT DISTINCT t.category FROM Test t", String.class)
                    .getResultList();

            return Optional.ofNullable(allCategoriesList);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<List<Test>> getAllTestsByStudentFromDatabase(Student student) {
        try {
            var allTestsList = getEntityManager()
                    .createQuery("SELECT t FROM Test t WHERE t.student = :student", Test.class)
                    .setParameter("student", student)
                    .getResultList();

            return Optional.ofNullable(allTestsList);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Test> getTestByIdFromDatabase(int id) {
        try {
            var test = getEntityManager().find(Test.class, id);

            return Optional.of(test);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean persistTestToDatabase(Test test) {

        try {
            inTransaction(entityManager -> {
                entityManager.persist(test);
            });
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean removeTestFromDatabase(Test test) {

        try {
            inTransaction(entityManager -> {
                var testToRemove = entityManager.find(Test.class, test.getId());
                entityManager.remove(testToRemove);
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean mergeTestToDatabase(Test test) {

        try {
            inTransaction(entityManager -> {
                entityManager.merge(test);
            });
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
