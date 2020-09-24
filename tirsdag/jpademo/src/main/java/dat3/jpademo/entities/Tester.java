/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat3.jpademo.entities;

import static dat3.jpademo.entities.Person_.fees;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author Amazingh0rse
 */
public class Tester {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        Person p1 = new Person("Karl", 1643);
        Person p2 = new Person("Gravballemanden", 1357);
        Person p3 = new Person("Tut Ankh And", 0001);

        Address a1 = new Address("Store torv 41", 4239, "Herringløse");
        Address a2 = new Address("Refshaleøen 21", 2312, "Amager");
        Address a3 = new Address("Rødby Havn 32", 2312, "Rødby");

        p1.setAddress(a1);
        p2.setAddress(a2);
        p3.setAddress(a3);

        Fee f1 = new Fee(2000);
        Fee f2 = new Fee(10000);
        Fee f3 = new Fee(100);
        Fee f4 = new Fee(10033);

        p1.AddFee(f1);
        p1.AddFee(f4);
        p2.AddFee(f2);
        p3.AddFee(f3);

        SwimStyle s1 = new SwimStyle("Crawl");
        SwimStyle s2 = new SwimStyle("ButterFly");
        SwimStyle s3 = new SwimStyle("Breast Stroke");
        
        p1.AddSwimStyle(s1);
        p1.AddSwimStyle(s3);
        p2.AddSwimStyle(s2);
        p3.AddSwimStyle(s1);
        p3.AddSwimStyle(s2);
        
        
        
        em.getTransaction().begin();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
        em.getTransaction().commit();
        
        em.getTransaction().begin();
            p1.removeSwimStyle(s3);
        em.getTransaction().commit();
        
        
        System.out.println("p1: " + p1.getP_id() + ", " + p1.getName());
        System.out.println("p2: " + p2.getP_id() + ", " + p2.getName());
        System.out.println("p3: " + p3.getP_id() + ", " + p3.getName());
        System.out.println("Karls gade: " + p1.getAddress().getStreet());

        System.out.println("Lad os se om to-vejs virker: " + a1.getPerson().getName());
        System.out.println("Hvem har betalt f2? Det har: " + f2.getPerson().getName());
        System.out.println("Hvad har folk betalt? ");

        TypedQuery<Fee> q1 = em.createQuery("SELECT F FROM Fee f", Fee.class);
        List<Fee> fees = q1.getResultList();
        for (Fee f : fees) {
            System.out.println(f.getPerson().getName() + ": " + f.getAmount() + "kr. " + f.getPayDate() + " Adre: " + f.getPerson().getAddress().getCity());
        }

    }
}
