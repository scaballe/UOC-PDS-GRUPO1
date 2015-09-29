/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2012
 */
package jpa;
import java.io.Serializable;

import javax.persistence.*;

/**
 * JPA Class PetJPA
 */
@Entity
@Table(name="practicalcase.pet")
public class PetJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String datebirth;
	private String photo;
	private String name;
	private String description;
	private float price;
	private CategoryJPA category;

	/**
	 * Class constructor methods
	 */
	public PetJPA() {
		super();
	}
	public PetJPA(int id, String datebirth, String photo, String name, String description,
			Integer price, String category ) {		
		this.id = id;
		this.datebirth = datebirth;
		this.photo = photo;
		this.name = name;
		this.description = description;
		this.price = price;
	}

	/**
	 *  Methods get/set the fields of database
	 */
	@Id
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public String getDatebirth() {
		return datebirth;
	}
	public  void setDatebirth(String datebirth) {
		this.datebirth = datebirth;
	}
	public String getPhoto() {
		return photo;
	}	
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getName() {
		return name;
	}
	public  void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}	
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPrice() {
		return price;
	}	
	public void setPrice(float price) {
		this.price = price;
	}
	
	/**
	 * Methods get/set persistent relationships
	 */
	@ManyToOne
	@JoinColumn (name="category")
	public CategoryJPA getCategory() {
		return category;
	}
	public  void setCategory(CategoryJPA category) {
		this.category = category;
	}
}
