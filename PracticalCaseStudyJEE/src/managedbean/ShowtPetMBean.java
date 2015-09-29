/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2012
 */
package managedbean;

import java.io.Serializable;
import java.util.*;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.PetJPA;
import ejb.CatalogFacadeRemote;

/**
 * Managed Bean ShowPetMBean
 */
@ManagedBean(name = "petshow")
@SessionScoped
public class ShowtPetMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EJB
	private CatalogFacadeRemote showPetRemote;
	//stores PetJPA instance
	protected PetJPA dataPet;
	//stores PetJPA number id
	protected int idPet = 1;
	
	public ShowtPetMBean() throws Exception 
	{
		setDataPet();
	}
	
	/**
	 * Get/set the id number and PetJPA instance
	 * @return Pet Id
	 */
	public int getIdPet()
	{
		return idPet;
	}
	public void setIdPet(int idPet) throws Exception
	{
		this.idPet = idPet;
		setDataPet();
	}
	public PetJPA getDataPet()
	{
		return dataPet;
	}	
	public void setDataPet() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		showPetRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
		dataPet = (PetJPA) showPetRemote.showPet(idPet);
	}
}
