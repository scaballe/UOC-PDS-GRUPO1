/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2012
 */
package jpa;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

/**
 * JPA Class CategoryJPA
 */
@Entity
@Table(name="practicalcase.category")
public class CategoryJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private Collection<PetJPA> pets; 
	
	/**
	 * Class constructor methods
	 */
	public CategoryJPA() {
		super();
	}	
	public CategoryJPA(String name) {		
		this.name = name;
	}
	
	/**
	 *  Methods get/set the fields of database
	 *  Id Primary Key field
	 */
	@Id
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Methods get/set persistent relationships
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	@JoinColumn(name = "category") 
	public Collection<PetJPA> getPetsbyCategory() {
		return pets;
	}	
	public void setPetsbyCategory(Collection<PetJPA> pets) {
 		this.pets = pets; 
	}
}
