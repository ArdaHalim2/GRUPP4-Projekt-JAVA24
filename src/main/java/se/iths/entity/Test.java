package se.iths.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "test", schema = "demo")
public class Test {
    @Id
    @Column(name = "testId", nullable = false)
    private Integer id;

    @Column(name = "testName", nullable = false)
    private String testName;

    @Column(name = "testCategory", length = 100)
    private String testCategory;

    @Column(name = "testMaxScore")
    private Integer testMaxScore;

    @ColumnDefault("0")
    @Column(name = "testStudentScore")
    private Integer testStudentScore;

    @Column(name = "testDate")
    private LocalDate testDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_studentId", nullable = false)
    private Student testStudent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestCategory() {
        return testCategory;
    }

    public void setTestCategory(String testCategory) {
        this.testCategory = testCategory;
    }

    public Integer getTestMaxScore() {
        return testMaxScore;
    }

    public void setTestMaxScore(Integer testMaxScore) {
        this.testMaxScore = testMaxScore;
    }

    public Integer getTestStudentScore() {
        return testStudentScore;
    }

    public void setTestStudentScore(Integer testStudentScore) {
        this.testStudentScore = testStudentScore;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    public Student getTestStudent() {
        return testStudent;
    }

    public void setTestStudent(Student testStudent) {
        this.testStudent = testStudent;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", testName='" + testName + '\'' +
                ", testCategory='" + testCategory + '\'' +
                ", testMaxScore=" + testMaxScore +
                ", testStudentScore=" + testStudentScore +
                ", testDate=" + testDate +
                '}';
    }
}
