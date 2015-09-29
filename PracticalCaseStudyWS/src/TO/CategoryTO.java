/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2012
 */
package TO;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * TO Class CategoryTO
 */
@XmlRootElement(name= "category")
public class CategoryTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	public CategoryTO() {
		super();
	}	
	public CategoryTO(String name) {		
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
