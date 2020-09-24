/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Person;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Amazingh0rse
 */
public class PersonFacade implements IPersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacde(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //TODO Remove/Change this before use
    public long getPersonFacadeCount() {
        EntityManager em = emf.createEntityManager();
        try {
            long personCount = (long) em.createQuery("SELECT COUNT(p) FROM Person p").getSingleResult();
            return personCount;
        } finally {
            em.close();
        }

    }

    @Override
    public PersonDTO addPerson(String fName, String lName, String phone) throws MissingInputException {
        EntityManager em = emf.createEntityManager();
        Person person = new Person(fName, lName, phone);
        if ((fName.length() == 0 || lName.length() == 0 || phone.length() == 0)) {
            throw new MissingInputException("Missing input");
        }
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);

    }

    @Override
    public PersonDTO deletePerson(long id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, id);
        if (person == null) {
            throw new PersonNotFoundException("Could not delete, Id don't exist");
        } else {
            try {
                em.getTransaction().begin();
                em.remove(person);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
            return new PersonDTO(person);
        }
    }

    @Override
    public PersonDTO getPerson(long id) throws PersonNotFoundException {
        EntityManager em = getEntityManager();
        Person person = em.find(Person.class, id);
        if (person == null) {
            throw new PersonNotFoundException("No person with provided id found");
        } else {
            try {
                return new PersonDTO(person);
            } finally {
                em.close();
            }
        }
    }

    @Override
    public PersonsDTO getAllPersons() {
        EntityManager em = getEntityManager();
        try {
            return new PersonsDTO(em.createNamedQuery("Person.getAllRows").getResultList());
        } finally {
            em.close();
        }
    }

    @Override
    public PersonDTO editPerson(PersonDTO p) throws MissingInputException {
        EntityManager em = getEntityManager();
        if ((p.getFirstName().length() == 0 || p.getLastName().length() == 0 || p.getPhone().length() == 0)) {
            throw new MissingInputException("Missing input");
        }
        try {
            em.getTransaction().begin();
            Person person = em.find(Person.class, p.getId());
            person.setFirstName(p.getFirstName());
            person.setLastName(p.getLastName());
            person.setPhone(p.getPhone());
            em.getTransaction().commit();
            return new PersonDTO(person);
        } finally {
            em.close();
        }
    }

}
