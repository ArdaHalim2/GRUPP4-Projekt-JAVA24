package se.iths.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import java.time.LocalDate;

@Entity
@Table(name = "test", schema = "demo")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "testId", nullable = false)
    private Integer id;

    @Column(name = "testCategory", nullable = false)
    private String category;

    @ColumnDefault("0")
    @Column(name = "testMaxScore")
    private int maxScore;

    @ColumnDefault("0")
    @Column(name = "testStudentScore")
    private int studentScore;

    @Column(name = "testDate", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_studentId", nullable = false)
    private Student student;

    public Test(){}

    public Test(String category, int maxScore, int studentScore, LocalDate date, Student student) {
        this.category = category;
        this.maxScore = maxScore;
        this.studentScore = studentScore;
        this.date = date;
        this.student = student;
    }

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

    public void setStudent(Student student) { this.student = student; }

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
