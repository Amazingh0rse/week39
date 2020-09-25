/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.PersonDTO;
import dto.PersonsDTO;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;

/**
 *
 * @author Amazingh0rse
 */
public interface IPersonFacade {

    public PersonDTO addPerson(String fName, String lName, String phone) throws MissingInputException ;

    public PersonDTO deletePerson(long id) throws PersonNotFoundException;

    public PersonDTO getPerson(long id) throws PersonNotFoundException;

    public PersonsDTO getAllPersons();

    public PersonDTO editPerson(PersonDTO p) throws PersonNotFoundException, MissingInputException;

}
