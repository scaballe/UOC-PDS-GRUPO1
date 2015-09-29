/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2011
 */

import java.sql.*;
import java.util.*;
/**
 * Implementation Class, that follows the DAO pattern, the operations of actions and consults 
 * the tables Category and Pet
 **/
public class RMICatalogImplDAO implements RMICatalogInterfaceDAO{
	
	public RMICatalogImplDAO()
	{
	}

	/**
	 * Gets all the category names in the table category
	 * @return Collection of Category
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public java.util.Collection listAllCategories()
	{
		PreparedStatement pstmt = null;
		Connection conn = null;
		Vector <String> v = new Vector <String>();

		try {
			System.out.println("findAllCategory called");

			//Get DB connection
			conn = getConnection();

			// Find the name field by Category
			pstmt = conn.prepareStatement("SELECT name FROM postgres.practicalcase.Category");
			ResultSet rs = pstmt.executeQuery();

			// Insert every field found into a vector
			while (rs.next()) {
				String id = rs.getString(1);
				v.addElement(id);
			}

			// Return the vector of field name
			return v;
		}
		catch (SQLException sqle)
		{
			System.out.println("Error!. Can not access the database Pet: "+sqle); 
		}
		catch (Exception e)
		{
			System.out.println("Error!. Can not access the database Pet: "+e); 
		}
		finally {
			try { if (pstmt != null) pstmt.close(); }
			catch (Exception e) {}
			try { if (conn != null) conn.close(); }
			catch (Exception e) {}
		}
		return v;
	}
	
	/**
	 * Gets all the pets in the table pet
	 * @return Collection of pets
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public java.util.Collection listAllPets()
	{
		PreparedStatement pstmt = null;
		Connection conn = null;
		Vector <RMIPetTO> v = new Vector <RMIPetTO>();

		try {
			System.out.println("findAllPet called");

			//Get DB connection
			conn = getConnection();

			pstmt = conn.prepareStatement("SELECT * FROM postgres.practicalcase.pet");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				RMIPetTO petTO = new RMIPetTO();
				Integer id  = new Integer(rs.getInt("id"));
				petTO.setId(id.toString());
				
				java.sql.Date date = rs.getDate("datebirth");
				String date1 = date.toString().substring(8,10)+"/"+
				date.toString().substring(5,7)+"/"+
				date.toString().substring(0,4);
				petTO.setDateBirth(date1);
				
				petTO.setPhoto(rs.getString("photo"));
				petTO.setName(rs.getString("name"));
				petTO.setDescription(rs.getString("description"));		
				petTO.setPrice(rs.getFloat("price"));
				
				petTO.setCategory(rs.getString("category"));
				v.addElement(petTO);
			}

			return v;
		}
		catch (SQLException sqle)
		{
			System.out.println("Error!. Can not access the database Pet: "+sqle); 
		}
		catch (Exception e)
		{
			System.out.println("Error!. Can not access the database Pet: "+e); 
		}
		finally {
			/*
			 * Release DB Connection for other beans
			 */
			try { if (pstmt != null) pstmt.close(); }
			catch (Exception e) {}
			try { if (conn != null) conn.close(); }
			catch (Exception e) {}
		}
		return v;
	}
	
	/**
	 * Gets all the pets by category name in the table pet
	 * @return Collection pet
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public java.util.Collection listPetsByCategory(String category)
	{
		PreparedStatement pstmt = null;
		Connection conn = null;
		Vector <RMIPetTO> v = new Vector <RMIPetTO>();

		try {
			System.out.println("findCategory called");

			//Get DB connection
			conn = getConnection();

			pstmt = conn.prepareStatement("SELECT * FROM postgres.practicalcase.pet WHERE category = ?");
			pstmt.setString(1, category);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				RMIPetTO petVO = new RMIPetTO();
				Integer id  = new Integer(rs.getInt("id"));
				petVO.setId(id.toString());
				
				java.sql.Date date = rs.getDate("dateBirth");
				String date1 = date.toString().substring(8,10)+"/"+
				date.toString().substring(5,7)+"/"+
				date.toString().substring(0,4);
				petVO.setDateBirth(date1);
				
				petVO.setPhoto(rs.getString("photo"));
				petVO.setName(rs.getString("name"));
				petVO.setDescription(rs.getString("description"));
				
				Float price = rs.getFloat("price");
				petVO.setPrice(price.toString());
				
				petVO.setCategory(rs.getString("category"));
				v.addElement(petVO);
			}

			return v;
		}
		catch (SQLException sqle)
		{
			System.out.println("Error!. Can not access the database Catalog: "+sqle); 
		}
		catch (Exception e)
		{
			System.out.println("Error!. Can not access the database Catalog: "+e); 
		}
		finally {
			/*
			 * Release DB Connection for other beans
			 */
			try { if (pstmt != null) pstmt.close(); }
			catch (Exception e) {}
			try { if (conn != null) conn.close(); }
			catch (Exception e) {}
		}
		return v;
	}
	
	/**
	 * Get all the details of a pet
	 * @return RMIPetTO
	 */
	public RMIPetTO showPet(int petId)
	{
		PreparedStatement pstmt = null;
		Connection conn = null;
		RMIPetTO petVO = new RMIPetTO();

		try {
			System.out.println("findPet called");

			//Get DB connection
			conn = getConnection();

			pstmt = conn.prepareStatement("SELECT * FROM postgres.practicalcase.pet WHERE id = ?");
			pstmt.setInt(1, petId);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Integer id  = new Integer(rs.getInt("id"));
				petVO.setId(id.toString());

				java.sql.Date date = rs.getDate("dateBirth");
				String date1 = date.toString().substring(8,10)+"/"+
				date.toString().substring(5,7)+"/"+
				date.toString().substring(0,4);
				petVO.setDateBirth(date1);

				petVO.setPhoto(rs.getString("photo"));
				petVO.setName(rs.getString("name"));
				petVO.setDescription(rs.getString("description"));
				
				Float price = rs.getFloat("price");
				petVO.setPrice(price.toString());

				petVO.setCategory(rs.getString("category"));			
			}

			return petVO;
		}
		catch (SQLException sqle)
		{
			System.out.println("Error!. Can not access the database Pet: "+sqle); 
		}
		catch (Exception e)
		{
			System.out.println("Error!. Can not access the database Pet: "+e); 
		}
		finally {
			/*
			 * Release DB Connection for other beans
			 */
			try { if (pstmt != null) pstmt.close(); }
			catch (Exception e) {}
			try { if (conn != null) conn.close(); }
			catch (Exception e) {}
		}
		return petVO;
	}
	
	/**
	 * Gets JDBC connection from the connection pool.
	 * @return The JDBC connection
	 */
	public Connection getConnection() throws Exception {
		try {			
	        Class.forName("org.postgresql.Driver");	
			java.sql.Connection conn;	
	        conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","USER","PASSWORD");			
			return conn;
		}
		catch (Exception e) {
			System.err.println("Could not locate datasource! Reason:");
			e.printStackTrace();
			throw e;
		}
	}
}
