/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2012
 */
package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.model.SelectItem;
import javax.faces.bean.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import jpa.CategoryJPA;
import ejb.CatalogFacadeRemote;

/**
 * Managed Bean ListCategoriesMBean
 */
@ManagedBean(name = "catalog")
@SessionScoped
public class ListCategoriesMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private CatalogFacadeRemote catalogRemote;		
	protected Collection<SelectItem> categoryList = new ArrayList<SelectItem>();
	
	public ListCategoriesMBean() throws Exception
	{
		this.categoryList();
	}

	/**
	 * Method get which return Categories Collection
	 * @return Collection
	 */
	public Collection<SelectItem> getCategoryList()
	{
		return categoryList;
	}
	
	/**
	 * Method that takes a collection of instances of CategoryJPA calling 
	 * method listAllCategories of the EJB Session
	 * @throws Exception
	 */
	public void categoryList() throws Exception
	{	
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		catalogRemote = (CatalogFacadeRemote) ctx.lookup("java:app/PracticalCaseStudyJEE.jar/CatalogFacadeBean!ejb.CatalogFacadeRemote");
		@SuppressWarnings("unchecked")
		Collection<CategoryJPA> categoryCollection = (Collection<CategoryJPA>) catalogRemote.listAllCategories();
		for (Iterator<CategoryJPA> iter2 = categoryCollection.iterator(); iter2.hasNext();)
		{
			CategoryJPA pet2 = (CategoryJPA) iter2.next();
			SelectItem item = new SelectItem(pet2.getName());
			this.categoryList.add(item);			
		}
		this.categoryList.add(new SelectItem("ALL PETS"));
	}
}
