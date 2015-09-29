/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2011
 */
import java.rmi.*;

	/**
	 * Implementation for the RMI Practical Case.
	 */
public class RMICatalogImpl implements RMICatalogInterface {

	private static final long serialVersionUID = 1L;
	  
	  protected RMICatalogImpl(){
		
	  }

	  /**
	   * Implementation of the remotely invoked methods.
	   */
	  public java.util.Collection<?> listAllCategories() throws RemoteException {
		   java.util.Collection<?> categories = new RMICatalogImplDAO().listAllCategories();
	       System.out.flush();
	       return categories;
	  }
	  
	  public java.util.Collection<?> listAllPets() throws RemoteException {
		   java.util.Collection<?> pets = new RMICatalogImplDAO().listAllPets();
	       System.out.flush();
	       return pets;
	  }
	  public java.util.Collection<?> listPetsByCategory(String category) throws RemoteException {
		   java.util.Collection<?> pets = new RMICatalogImplDAO().listPetsByCategory(category);
	       System.out.flush();
	       return pets;
	  }
	  
	  public RMIPetTO showPet(int petId) throws RemoteException {
		   RMIPetTO pet = new RMICatalogImplDAO().showPet(petId);
	       System.out.flush();
	       return pet;
	  }
}
