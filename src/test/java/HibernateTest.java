
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import se.yrgo.domain.Student;
import se.yrgo.domain.Subject;
import se.yrgo.domain.Tutor;

public class HibernateTest {
    public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("databaseConfig");

    public static void main(String[] args) {
        setUpData();
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        System.out.println("--------------------");

        // Task1:
        Subject science = em.find(Subject.class, 2);
        Query task1 = em.createQuery(
                "select tutor.teachingGroup from Tutor as tutor where :subject member of tutor.subjectsToTeach");
        task1.setParameter("subject", science);
        List<Student> list = task1.getResultList();

        for (Student student : list) {
            System.out.println(student);
        }
        System.out.println("--------------------");

        // Task2:
        Query task2 = em
                .createQuery("select student.name, tutor.name from Tutor as tutor join tutor.teachingGroup as student");
        List<Object[]> list2 = task2.getResultList();

        for (Object[] studentTutor : list2) {
            System.out.println("Student: " + studentTutor[0] + ", Tutor: " +
                    studentTutor[1]);
        }
        System.out.println("--------------------");

        // Task3:
        Query test = em.createQuery("select avg(numberOfSemesters) from Subject as subject");
        double answer3 = (double) test.getSingleResult();
        System.out.println("Average semester length: " + answer3);
        System.out.println("--------------------");

        // Task4:
        Query task4 = em.createQuery("select max(salary) from Tutor as tutor");
        Integer answer4 = (Integer) task4.getSingleResult();
        System.out.println(answer4);
        System.out.println("--------------------");

        // Task5:
        Query task5 = em.createNamedQuery("tutorSalary", Tutor.class);
        List<Tutor> l = task5.getResultList();
        for (Tutor tutor : l) {
            System.out.println(tutor);
        }
        System.out.println("--------------------");

        tx.commit();
        em.close();
    }

    public static void setUpData() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Subject mathematics = new Subject("Mathematics", 2);
        Subject science = new Subject("Science", 2);
        Subject programming = new Subject("Programming", 3);
        em.persist(mathematics);
        em.persist(science);
        em.persist(programming);

        Tutor t1 = new Tutor("ABC123", "Johan Smith", 40000);
        t1.addSubjectsToTeach(mathematics);
        t1.addSubjectsToTeach(science);

        Tutor t2 = new Tutor("DEF456", "Sara Svensson", 20000);
        t2.addSubjectsToTeach(mathematics);
        t2.addSubjectsToTeach(science);

        // This tutor is the only tutor who can teach History
        Tutor t3 = new Tutor("GHI678", "Karin Lindberg", 0);
        t3.addSubjectsToTeach(programming);

        em.persist(t1);
        em.persist(t2);
        em.persist(t3);

        t1.createStudentAndAddtoTeachingGroup("Jimi Hendriks", "1-HEN-2019", "Street 1", "city 2", "1212");
        t1.createStudentAndAddtoTeachingGroup("Bruce Lee", "2-LEE-2019", "Street 2", "city 2", "2323");
        t3.createStudentAndAddtoTeachingGroup("Roger Waters", "3-WAT-2018", "Street 3", "city 3", "34343");

        tx.commit();
        em.close();
    }

}