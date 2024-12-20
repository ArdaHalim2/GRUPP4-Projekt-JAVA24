package se.iths.repositories;

import static se.iths.repositories.JPAUtil.*;
import se.iths.entity.Student;
import java.util.List;
import java.util.Optional;

/**
 * Class for handling all transactions between the student table in the database and
 * the application.
 */
public class StudentRepo {

    /**
     * Gets all students from the database.
     * @return Optional list of students if the query succeeded, else an empty Optional.
     */
    public Optional<List<Student>> getAllStudentsFromDatabase() {
        try {
            var allStudentsList = getEntityManager()
                    .createQuery("SELECT s FROM Student s JOIN FETCH s.tests", Student.class)
                    .getResultList();

            return Optional.ofNullable(allStudentsList);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Gets a student from the database by their id.
     * @param id int
     * @return Optional of student if the query succeeded, else an empty Optional.
     */
    public Optional<Student> getStudentByIdFromDatabase(int id) {
        try {
            var student = getEntityManager().find(Student.class, id);
            return Optional.ofNullable(student);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Gets a student from the database by their name.
     * @param name String
     * @return Optional of student if the query succeeded, else an empty Optional.
     */
    public Optional<Student> getStudentByNameFromDatabase(String name) {
        try {
            var student = getEntityManager()
                    .createQuery("SELECT s FROM Student s WHERE s.name = :name", Student.class)
                    .setParameter("name", name)
                    .getSingleResult();

            return Optional.ofNullable(student);

        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Adds a detached student entity (and all its child entities since cascade = CascadeType.ALL) to the database.
     * @param student Student
     * @return boolean: true if the student was persisted to the database, else false.
     */
    public boolean persistStudentToDatabase(Student student) {

        try {
            inTransaction(entityManager -> {
                entityManager.persist(student);
            });
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * Deletes a managed student entity (and all its child entities since cascade = CascadeType.ALL) from the database.
     * @param student Student
     * @return boolean: true if the student was persisted to the database, else false.
     */
    public boolean removeStudentFromDatabase(Student student) {

        try {
            inTransaction(entityManager -> {
                var studentToRemove = entityManager.find(Student.class, student.getId());
                entityManager.remove(studentToRemove);
            });
            return true;
        } catch (Exception e){
            return false;
        }
    }

    /**
     * Updates a detached student entity (and all its child entities since cascade = CascadeType.ALL) in the database.
     * @param student Student
     * @return boolean: true if the student was merged into the database, else false.
     */
    public boolean mergeStudentInDatabase(Student student) {

        try {
            inTransaction(entityManager -> {
                entityManager.merge(student);
            });
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
