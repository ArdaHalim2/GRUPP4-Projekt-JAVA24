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

    @Column(name = "testCategory")
    private String category;

    @Column(name = "testMaxScore")
    private Integer maxScore;

    @ColumnDefault("0")
    @Column(name = "testStudentScore")
    private Integer studentScore;

    @Column(name = "testDate")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_studentId", nullable = false)
    private Student student;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public Integer getStudentScore() {
        return studentScore;
    }

    public void setStudentScore(Integer studentScore) {
        this.studentScore = studentScore;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", maxScore=" + maxScore +
                ", studentScore=" + studentScore +
                ", date=" + date +
                '}';
    }
}
