/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2012
 */
package ejb;

import java.util.Collection;

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebService;

import TO.CategoryTO;
import TO.PetTO;

/**
 * Session EJB Remote Interfaces
 */
@Remote
@WebService
public interface CatalogFacadeRemoteWSSoap {
	  /**
	   * Remotely invoked method.
	   */
	  @WebMethod public Collection<CategoryTO> listAllCategories();
	  @WebMethod public Collection<PetTO> listAllPets();
	  @WebMethod public Collection<PetTO> listPetsByCategory(String category);
	  @WebMethod public PetTO showPet(int petId);
}
