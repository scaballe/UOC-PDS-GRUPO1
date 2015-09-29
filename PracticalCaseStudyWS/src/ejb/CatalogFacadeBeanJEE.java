/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenc Font Sagristà, 2012
 */
package ejb;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.CategoryJPA;
import jpa.PetJPA;
import ejb.CatalogFacadeRemoteJEE;

/**
 * EJB Session Bean Class of example "Practical Case Study JEE"
 */
@Stateless
public class CatalogFacadeBeanJEE implements CatalogFacadeRemoteJEE {
	
	//Persistence Unit Context
	@PersistenceContext(unitName="PracticalCase") 
	private EntityManager entman;
   
	/**
	 * Method that returns Collection of all categories
	 */
	public java.util.Collection<CategoryJPA> listAllCategories() {
		@SuppressWarnings("unchecked")
		Collection<CategoryJPA> allCategories = entman.createQuery("from CategoryJPA").getResultList();
		return allCategories;
	}
	  
	/**
	 * Method that returns Collection of all pets
	 */
	public java.util.Collection<PetJPA> listAllPets() {
		@SuppressWarnings("unchecked")
		Collection<PetJPA> allPets = entman.createQuery("from PetJPA").getResultList();
	    return allPets;
	}

	/**
	 * Method that returns Collection of all pets by category
	 */
	public java.util.Collection<?> listPetsByCategory(String category) throws PersistenceException {
		Collection<PetJPA> allPetsbyCategory = null;
		try
		{		
			@SuppressWarnings("unchecked")
			Collection<CategoryJPA> categories = entman.createQuery("FROM CategoryJPA b WHERE b.name = :name").setParameter("name", category).getResultList();
			if (!categories.isEmpty() || categories.size()==1)
			{
				Iterator<CategoryJPA> iter = categories.iterator();
				CategoryJPA element = (CategoryJPA) iter.next();
				allPetsbyCategory = element.getPetsbyCategory();
			}
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	    return allPetsbyCategory;
	}
	  
	/**
	 * Method that returns instance of the class pet
	 */
	public PetJPA showPet(int petId)throws PersistenceException {
		PetJPA pet = null;
		try
		{
			@SuppressWarnings("unchecked")
			Collection<PetJPA> pets = entman.createQuery("FROM PetJPA b WHERE b.id = ?1").setParameter(1, new Integer(petId)).getResultList();
			if (!pets.isEmpty() || pets.size()==1)
			{
				Iterator<PetJPA> iter =pets.iterator();
				pet = (PetJPA) iter.next();				
			}
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	    return pet;
	}	
}
