/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2012
 */
package managedbean;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.PetJPA;
import ejb.CatalogFacadeRemote;

/**
 * Managed Bean ListPetsMBean
 */
@ManagedBean(name = "pets")
@SessionScoped
public class ListPetsMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;	

	@EJB
	private CatalogFacadeRemote petsRemote;
	
	//stores the name of the category of pets to be displayed
	private String category = "ALL PETS";
	//its relate the home image area with a category
	private Rectangle all = new Rectangle(72, 0, 230, 200);
	private Rectangle fish = new Rectangle(0, 182, 72, 72);
	private Rectangle dogs = new Rectangle(60, 250, 72, 72);
	private Rectangle reptiles = new Rectangle(140, 270, 72, 72);
	private Rectangle cats = new Rectangle(225, 240, 72, 72);
	private Rectangle birds = new Rectangle(280, 180, 72, 72);
	//stores all the instances of PetJPA
	private Collection<PetJPA> petsList;
	//stores the screen number where the user is 
	private int screen = 0;
	//stores ten or fewer PetJPA instances that the user can see on a screen
	protected Collection<PetJPA> petsListView;
	//stores the total number of instances of PetJPA
	protected int numberPets = 0;
	
	/**
	 * Constructor method
	 * @throws Exception
	 */
	public ListPetsMBean() throws Exception
	{
		this.petList();
	}
	
	/**
	 * Method that calculates the name of the category after analyzing where the user has
	 * clicked the mouse.
	 */
	public void categoryMap(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();
		String categoryId = e.getComponent().getClientId(context);
		@SuppressWarnings("rawtypes")
		Map requestParams = context.getExternalContext().getRequestParameterMap();
		
		int x = new Integer((String) requestParams.get(categoryId + ".x")).intValue();
		int y = new Integer((String) requestParams.get(categoryId + ".y")).intValue();	
		
		if (birds.contains(new Point(x, y)))
		{
			category = "BIRDS";
		}
		else if (fish.contains(new Point(x, y)))
		{
			category = "FISH";
		}
		else if (dogs.contains(new Point(x, y)))
		{
			category = "DOGS";
		}
		else if (reptiles.contains(new Point(x, y)))
		{
			category = "REPTILES";
		}
		else if (cats.contains(new Point(x, y)))
		{
			category = "CATS";
		}
		else if (all.contains(new Point(x, y)))
		{
			category = "ALL PETS";
		}
		this.setCategory(category);
	}

	/**
	 * Method that returns an instance Collection of 10 or less PetJPA according screen 
	 * where the user is.
	 * @return Collection PetJPA
	 */
	public Collection<PetJPA> getPetListView()
	{
		int n =0;
		petsListView = new ArrayList<PetJPA>();
		for (Iterator<PetJPA> iter2 = petsList.iterator(); iter2.hasNext();)
		{
			PetJPA pet2 = (PetJPA) iter2.next();
			if (n >= screen*10 && n< (screen*10+10))
			{				
				this.petsListView.add(pet2);
			}
			n +=1;
		}
		this.numberPets = n;
		return petsListView;
	}
	
	/**
	 * Returns the total number of instances of PetJPA
	 * @return Pet number
	 */
	public int getNumberPets()
	{ 
		return this.numberPets;
	}
	
	/**
	 * allows forward or backward in user screens
	 */
	public void nextScreen()
	{
		if (((screen+1)*10 < petsList.size()))
		{
			screen +=1;
		}
	}
	public void previousScreen()
	{
		if ((screen > 0))
		{
			screen -=1;
		}
	}

	/**
	 * Get/set the Category name
	 * @return Category name
	 */
	public String getCategory()
	{
		return this.category;
	}
	public void setCategory(String category)
	{
		this.category = category;
	}
	
	public void categoryValueChanged(ValueChangeEvent categoryChanged) {
		this.category = categoryChanged.getNewValue().toString();
	}
	
	/**
	 * Method used for Facelet to call listPetsView Facelet
	 * @return Facelet name
	 * @throws Exception
	 */
	public String listPets() throws Exception
	{
		petList();
		return "listPetsView";
	}
	
	/**
	 * Method used for Facelet to call showPetView Facelet
	 * @return Facelet name
	 */
	public String setShowPet()
	{
		return "showPetView";
	}
	
	/**
	 * Method that gets a list of instances by category or all PetJPA
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void petList() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		screen = 0;
		petsRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
		if (category.equals("ALL PETS"))
		{
			petsList = (Collection<PetJPA>)petsRemote.listAllPets();
		}
		else
		{
			petsList = (Collection<PetJPA>)petsRemote.listPetsByCategory(category);
		}
	}
}
