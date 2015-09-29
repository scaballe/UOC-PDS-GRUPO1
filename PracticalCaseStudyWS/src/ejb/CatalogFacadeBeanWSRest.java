/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2012
 */
package ejb;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.CategoryJPA;
import jpa.PetJPA;
import TO.PetTO;
import TO.CategoryTO;
import ejb.CatalogFacadeRemoteWSRest;

/**
 * EJB Session Bean Class of example "Practical Case Study WS Rest"
 */
@Stateless
public class CatalogFacadeBeanWSRest implements CatalogFacadeRemoteWSRest {
	
	//Persistence Unit Context
	@PersistenceContext(unitName="PracticalCase") 
	private EntityManager entman;
   
	/**
	 * Method that returns Collection of all categories
	 */
	public java.util.Collection<CategoryTO> listAllCategories() {
		Collection<CategoryTO> allCategories = new ArrayList<CategoryTO>();
		@SuppressWarnings("unchecked")
		Collection<CategoryJPA> allCategoriesJPA = entman.createQuery("from CategoryJPA").getResultList();	
		for (Iterator<CategoryJPA> iter = allCategoriesJPA.iterator(); iter.hasNext();)
		{
			CategoryJPA pet2 = (CategoryJPA) iter.next();
			allCategories.add(new CategoryTO(pet2.getName()));			
		}
		allCategories.add(new CategoryTO("ALL PETS"));
		return allCategories;
	}
	  
	/**
	 * Method that returns Collection of all pets
	 */
	public java.util.Collection<PetTO> listAllPets() {
		Collection<PetTO> allPets = new ArrayList<PetTO>();
		@SuppressWarnings("unchecked")
		Collection<PetJPA> allPetsJPA = entman.createQuery("from PetJPA").getResultList();
		for (Iterator<PetJPA> iter = allPetsJPA.iterator(); iter.hasNext();)
		{
			PetJPA pet = (PetJPA) iter.next();
			allPets.add(new PetTO(pet.getId(), pet.getDatebirth(), pet.getPhoto(), 
					pet.getName(), pet.getDescription(), pet.getPrice(), pet.getCategory().getName()));
		}
	    return allPets;
	}

	/**
	 * Method that returns Collection of all pets by category
	 */
	public java.util.Collection<PetTO> listPetsByCategory(String category) throws PersistenceException {
		Collection<PetTO> allPetsbyCategoryTO = new ArrayList<PetTO>();
		Collection<PetJPA> allPetsbyCategoryJPA = new ArrayList<PetJPA>();
		try
		{		
			@SuppressWarnings("unchecked")
			Collection<CategoryJPA> categories = entman.createQuery("FROM CategoryJPA b WHERE b.name = :name").setParameter("name", category).getResultList();
			if (!categories.isEmpty() || categories.size()==1)
			{
				Iterator<CategoryJPA> iter = categories.iterator();
				CategoryJPA element = (CategoryJPA) iter.next();
				allPetsbyCategoryJPA = element.getPetsbyCategory();
				for (Iterator<PetJPA> iter1 = allPetsbyCategoryJPA.iterator(); iter1.hasNext();)
				{
					PetJPA pet = (PetJPA) iter1.next();
					allPetsbyCategoryTO.add(new PetTO(pet.getId(), pet.getDatebirth(), pet.getPhoto(), 
							pet.getName(), pet.getDescription(), pet.getPrice(), pet.getCategory().getName()));
				}
			}
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	    return allPetsbyCategoryTO;
	}
	  
	/**
	 * Method that returns instance of the class pet
	 */
	public PetTO showPet(int petId)throws PersistenceException {
		PetTO pet1 = null;
		PetJPA pet = null;
		try
		{
			@SuppressWarnings("unchecked")
			Collection<PetJPA> pets = entman.createQuery("FROM PetJPA b WHERE b.id = ?1").setParameter(1, new Integer(petId)).getResultList();
			if (!pets.isEmpty() || pets.size()==1)
			{
				Iterator<PetJPA> iter =pets.iterator();
				pet = (PetJPA) iter.next();				
				pet1 = new PetTO(pet.getId(), pet.getDatebirth(), pet.getPhoto(), 
						pet.getName(), pet.getDescription(), pet.getPrice(), pet.getCategory().getName());
			}
		}catch (PersistenceException e) {
			System.out.println(e);
		} 
	    return pet1;
	}	
}
