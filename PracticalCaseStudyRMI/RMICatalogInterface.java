/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2011
 */
import java.rmi.*;

	/**
	 * Remote Interface for the RMI Practical Case.
	 */
public interface RMICatalogInterface extends Remote {
	  /**
	   * Remotely invoked method.
	   */
	  public java.util.Collection<?> listAllCategories() throws RemoteException;
	  public java.util.Collection<?> listAllPets() throws RemoteException;
	  public java.util.Collection<?> listPetsByCategory(String category) throws RemoteException;
	  public RMIPetTO showPet(int petId) throws RemoteException;
}
