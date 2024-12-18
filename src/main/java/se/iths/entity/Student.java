package se.iths.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "student", schema = "demo")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studentId", nullable = false)
    private Integer id;

    @Column(name = "studentName", nullable = false)
    private String studentName;

    @Column(name = "studentDateOfBirth")
    private LocalDate studentDateOfBirth;

    @OneToMany(mappedBy = "testStudent", cascade = CascadeType.ALL)
    private Set<se.iths.entity.Test> tests = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public LocalDate getStudentDateOfBirth() {
        return studentDateOfBirth;
    }

    public void setStudentDateOfBirth(LocalDate studentDateOfBirth) {
        this.studentDateOfBirth = studentDateOfBirth;
    }

    public Set<se.iths.entity.Test> getTests() {
        return tests;
    }

    public void setTests(Set<se.iths.entity.Test> tests) {
        this.tests = tests;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", studentName='" + studentName + '\'' +
                ", studentDateOfBirth=" + studentDateOfBirth +
                ", tests=" + tests +
                '}';
    }
}
