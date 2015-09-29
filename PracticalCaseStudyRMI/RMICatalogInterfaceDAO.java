/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2011
 */

/**
 * Interface, that follows the DAO pattern, that defines all the operations of actions and consults the tables 
 * Category and Pet
 **/
public interface RMICatalogInterfaceDAO {
	public java.util.Collection<?> listAllCategories() throws Exception;
	public java.util.Collection<?> listAllPets() throws Exception;
	public java.util.Collection<?> listPetsByCategory(String category) throws Exception;
	public RMIPetTO showPet(int pet) throws Exception;
}
