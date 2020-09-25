package facades;

import entities.Address;
import entities.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author Amazingh0rse
 */
public class Tester {

    public static void main(String[] args) {

        //Create emf pointing to the dev-database
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();

        EntityManager em = emf.createEntityManager();

        Person p1 = new Person("Karl", "Gudesøn", "87654321");
        Person p2 = new Person("Gravballemanden", "Svendsen", "12345678");
        Person p3 = new Person("Tut", "Ankh And", "13467964");

        Address a1 = new Address("Skovkrogen 7", "2920", "CPH");
        Address a2 = new Address("Gotham street", "9999", "Gotham");
        Address a3 = new Address("Silent hill", "666", "Scary place");

        p1.setAddress(a1);
        p2.setAddress(a1);
        p3.setAddress(a3);

        try {
            em.getTransaction().begin();
            em.createQuery("DELETE from Person").executeUpdate();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }
}