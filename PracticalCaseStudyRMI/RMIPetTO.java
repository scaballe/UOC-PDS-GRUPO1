/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2007
 */

import java.io.Serializable;
/**
 * Class that follows the pattern TO, which encapsulates data and a pet
 * 
 */
@SuppressWarnings("serial")
public class RMIPetTO implements Serializable {
	private String datebirth;
	private String photo;
	private String name;
	private String description;
	private float price;
	private String category;
	private String id;

	public RMIPetTO () {
		this.id = "";
		this.datebirth = "";
		this.photo = "";
		this.name = "";
		this.description = "";
		this.price = 0;
		this.category = "";
	}
	
	public RMIPetTO (String id, String dateBirth, String foto, String name,
				String description, String price, String category)
	{
		this.id = id;
		this.datebirth = dateBirth;
		this.photo = foto;
		this.name = name;
		this.description = description;
		this.price = Float.valueOf(price);
		this.category = category;
	}
	
	// get / set methods to access all the variables of VO
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setDateBirth(String dateBirth) {
		this.datebirth = dateBirth;
	}
	public String getDateBirth() {
		return datebirth;
	}
	public void setPhoto(String foto) {
		this.photo = foto;
	}
	public String getPhoto() {
		return photo;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = Float.valueOf(price);
	}
	public String getPriceToString() {
		return Float.toString(price);
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategory() {
		return category;
	}
	public String toString()
	{
		String pet = id+" "+name+" "+description+" "+datebirth+" "+price+""+category;
		return pet;
	}		
}
