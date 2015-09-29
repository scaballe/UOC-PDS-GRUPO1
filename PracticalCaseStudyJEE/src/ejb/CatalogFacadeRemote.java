/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2012
 */
package ejb;

import java.util.Collection;

import javax.ejb.Remote;

import jpa.PetJPA;

/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface CatalogFacadeRemote {
	  /**
	   * Remotely invoked method.
	   */
	  public Collection<?> listAllCategories();
	  public Collection<?> listAllPets();
	  public Collection<?> listPetsByCategory(String category);
	  public PetJPA showPet(int petId);
}
