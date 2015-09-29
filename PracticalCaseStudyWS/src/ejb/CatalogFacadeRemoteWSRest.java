/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicen� Font Sagrist�, 2012
 */
package ejb;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import TO.CategoryTO;
import TO.PetTO;

/**
 * Session EJB Remote Interfaces
 */
@Remote
@Path("/WSCatalogRest")
public interface CatalogFacadeRemoteWSRest {
	  /**
	   * Remotely invoked method.
	   */
	  @GET
	  @Produces("application/xml")
	  @Path("/listAllCategories")
	  public Collection<CategoryTO> listAllCategories();
	  @GET
	  @Produces("application/xml")
	  @Path("/listAllPets")
	  public Collection<PetTO> listAllPets();
	  @GET
	  @Produces("application/xml")
	  @Path("/listPetsByCategory/{category}")
	  public Collection<PetTO> listPetsByCategory(@PathParam("category") String category);
	  @GET
	  @Produces("application/json")
	  @Path("/showPet/{petId}")
	  public PetTO showPet(@PathParam("petId") int petId);
}
