package se.yrgo.domain;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;

@Entity
public class Tutor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String tutorId;
    private String name;
    private int salary;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "TUTOR_FK")
    private Set<Student> teachingGroup;

    @ManyToMany(mappedBy = "tutors")
    private Set<Subject> subjectsToTeach;

    public Tutor() {

    }

    public Tutor(String tutorId, String name, int salary) {
        this.tutorId = tutorId;
        this.name = name;
        this.salary = salary;
        this.teachingGroup = new HashSet<Student>();
        this.subjectsToTeach = new HashSet<Subject>();
    }

    public void addSubjectsToTeach(Subject subject) {
        this.subjectsToTeach.add(subject);
        subject.getTutors().add(this);
    }

    public void createStudentAndAddtoTeachingGroup(String studentName, String enrollmentID, String street, String city,
            String zipcode) {
        Student student = new Student(studentName, enrollmentID, street, city, zipcode);
        this.addStudentToTeachingGroup(student);
    }

    public void addStudentToTeachingGroup(Student newStudent) {
        this.teachingGroup.add(newStudent);
    }

    public Set<Subject> getSubjectsToTeach() {
        return this.subjectsToTeach;
    }

    public Set<Student> getTeachingGroup() {
        Set<Student> unmodifiable = Collections.unmodifiableSet(this.teachingGroup);
        return unmodifiable;
    }

    public String getName() {
        return name;
    }
    

    @Override
    public String toString() {
        return String.format("Tutor: %s with id: %s has a salary of: %d", name, tutorId, salary);
    }

    public int getId() {
        return id;
    }

    public String getTutorId() {
        return tutorId;
    }

    public int getSalary() {
        return salary;
    }

}
