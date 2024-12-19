package se.iths;

import se.iths.entity.Student;
import se.iths.repositories.StudentRepo;

import java.util.Scanner;

public class Crud {

    private final StudentRepo studentRepo = new StudentRepo();
    private Scanner scanner;
    private Student currentStudent = new Student();

    public void crudMenu(Scanner scanner, Student currentStudent){

        this.currentStudent = currentStudent;
        this.scanner = scanner;

        System.out.println("""
                CRUD ENTITIES
                Choose which one to edit:
                
                1. Student
                2. Test
                3. Country
                4. City
                5. Lake
                """);

        String userAnswer = scanner.nextLine();

        switch(userAnswer){
            case "1" -> crudStudent();
            case "2" -> changeThisToRealMethod();
            case "3" -> changeThisToRealMethod();
            case "4" -> changeThisToRealMethod();
            case "5" -> changeThisToRealMethod();
            default -> changeThisToRealMethod();
        }
    }

    public void crudStudent(){

        System.out.println("""
                CRUD STUDENT
                
                1. Add Student
                2. Update Student
                3. Delete Student
                4. Show all students
                """);

        String userAnswer = scanner.nextLine();

        switch(userAnswer){
            case "1" -> addStudent();
            case "2" -> changeThisToRealMethod();
            case "3" -> changeThisToRealMethod();
            case "4" -> changeThisToRealMethod();
            default -> changeThisToRealMethod();
        }
    }

    public void addStudent(){

        System.out.println("Enter student name: ");
        String studentName = scanner.nextLine();
        Student student = new Student(studentName);

        if(studentRepo.persistStudentToDatabase(student)){
            System.out.println("Student was added to the database");
        } else {
            System.out.println("Student was not added to the database");
        }
    }

    public void updateStudent(){

        System.out.println("Enter new student name: ");
        String newStudentName = scanner.nextLine();

        currentStudent.setName(newStudentName);

        if(studentRepo.mergeStudentInDatabase(currentStudent)){
            System.out.println("Student was updated in the database");
        } else {
            System.out.println("Student was not updated in the database");
        }
    }

    public void changeThisToRealMethod(){}
}
