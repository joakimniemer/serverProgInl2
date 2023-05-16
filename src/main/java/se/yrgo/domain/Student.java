package se.yrgo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SecondaryTable;
import jakarta.persistence.Table;

// @Table(name = "TBL_STUDENT")
// @SecondaryTable(name = "TBL_ADDRESS")
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true, nullable = false)
    private String enrollmentID;
    private String name;

    @Embedded
    private Address adress;

    // @Column(table = "TBL_ADDRESS")
    // private String street;
    // @Column(table = "TBL_ADDRESS")
    // private String city;
    // @Column(table = "TBL_ADDRESS")
    // private String zipcode;

    public Student() {
    }

    public Student(String name, String enrollmentID, String street, String city, String zipcode) {
        this.name = name;
        this.enrollmentID = enrollmentID;
        this.adress = new Address(street, city, zipcode);
    }

    public Student(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return String.format("%s livs at: %s", name, adress);
    }

    public Address getAdress() {
        return adress;
    }

    public void setAdress(Address newAddress) {
        this.adress = newAddress;
    }

    public String getEnrollmentID() {
        return enrollmentID;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((enrollmentID == null) ? 0 : enrollmentID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (enrollmentID == null) {
            if (other.enrollmentID != null)
                return false;
        } else if (!enrollmentID.equals(other.enrollmentID))
            return false;
        return true;
    }

}
