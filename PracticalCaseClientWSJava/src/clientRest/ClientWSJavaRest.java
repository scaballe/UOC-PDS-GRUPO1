package clientRest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ClientWSJavaRest {

	public ClientWSJavaRest() 
	{
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ParserConfigurationException, SAXException 
	{
		@SuppressWarnings("rawtypes")
		ArrayList nameCategory = new ArrayList();		
		try {
			String c; 
			int n;
			boolean exit = false;
		
			//Get a Category Document 
			Document doc = getDoc("http://localhost:8080/PracticalCaseWS/WSCatalogRest/listAllCategories");
			NodeList nList = doc.getElementsByTagName("category");
			while (!exit)
			{
				n=1;			
				System.out.println("");
				System.out.println("          P E T S    S H O P");
				System.out.println("          Practical Case Study based on Rest Web Service");
				System.out.println("          You can list all the pets or list them by category.");
			  	System.out.println("");
			  	for (int temp = 0; temp < nList.getLength(); temp++) 
			  	{						
			  		Node nNode = nList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) 
					{				 
						Element eElement = (Element) nNode;
						nameCategory.add(getTagValue("name", eElement));	    		  	
						System.out.println("          "+n+" - "+getTagValue("name", eElement) + " "); 
						n +=1;				 
					}
				}
			  	System.out.println("          0 - Exit ");
			  	System.out.print("          Choose a number: ");		      
			  	c = input();			  	
			  	
			  	try 
			  	{
			  		if (c.equals("0"))
			      	{			      	
			  			exit = true;
			      	}
			      	else if (Integer.parseInt(String.valueOf(c))>0 & Integer.parseInt(String.valueOf(c))<=n)
			      	{
			      		int nP = 1;
			      		String c1;
			      	    Document doc1;
			      	    if (nameCategory.get(Integer.parseInt(String.valueOf(c))-1).equals("ALL PETS"))
			      	   	{
			      	    	//Get all Pets Collection
			      	    	doc1 = getDoc("http://localhost:8080/PracticalCaseWS/WSCatalogRest/listAllPets");			      	   			      	 
			      	   	}
			      	   	else
			          	{
			        		//Get Pets by Category XML
			    			doc1 = getDoc("http://localhost:8080/PracticalCaseWS/WSCatalogRest/listPetsByCategory/"+nameCategory.get(Integer.parseInt(String.valueOf(c))-1));			      	   			      	 
			   	    	}
						NodeList nList1 = doc1.getElementsByTagName("pet");
			          	System.out.println("");
			     	    System.out.println("          Num  "+formatString("Pet's name",12)+formatString("Description",16)+formatString("Price",7)+formatString("Category",12)); 		      		
					  	for (int temp = 0; temp < nList1.getLength(); temp++) {								 
					  		Node nNode = nList1.item(temp);
							if (nNode.getNodeType() == Node.ELEMENT_NODE) 
							{				 
								Element eElement = (Element) nNode;
								System.out.println("          "+formatString(String.valueOf(nP),-2)+" - "+formatString(getTagValue("name", eElement).toUpperCase(),11)+
						     				" "+formatString(getTagValue("description", eElement),15)+"  "+formatString(getTagValue("price", eElement),4)+ "  "+formatString(getTagValue("category", eElement),12)); 			      		
								nP +=1;
							}
						}
			      	    System.out.println("");
			      	   	System.out.print("          Puts Pet's number or '0' to exit: ");
			      	   	c1 = input();
			      	   	System.out.println("");
			      	   	System.out.println("");	
				      	try 
				      	{
				      		if (Integer.parseInt(String.valueOf(c1))>=1 & Integer.parseInt(String.valueOf(c1))<nP)
				      		{
				      			String idPet = "";
							  	for (int temp = 0; temp < nList1.getLength(); temp++) 								 
				      			{ 
				      				if (temp+1 ==Integer.parseInt(String.valueOf(c1)))
				      				{				      					
				      					Node nNode = nList1.item(temp);
										if (nNode.getNodeType() == Node.ELEMENT_NODE) 
										{				 
											Element eElement = (Element) nNode;
											idPet = getTagValue("id", eElement);
										}
				      				}
				      			}	
				        		//Get a Pet with format JSON							  	
							  	JSONObject json = getJson("http://localhost:8080/PracticalCaseWS/WSCatalogRest/showPet/"+idPet);
								System.out.println("          I N F O R M A T I O N   A B O U T   P E T");					      
								System.out.println("");
								System.out.println("          ID number      : "+ (Long)json.get("id") + " "); 					   
								System.out.println("          Data From      : "+ (String)json.get("datebirth") + " "); 			      			
								System.out.println("          Pet name       : "+ (String)json.get("name") + " "); 
								System.out.println("          Pet description: "+ (String)json.get("description") + " "); 
								System.out.println("          Price          : "+ (Double)json.get("price") + " "); 
								System.out.println("          Class pet      : "+ (String)json.get("category") + " ");
								System.out.println("");
								System.out.print("          Press ENTER to continue: ");
								getChar();			      																	 
				      		}
				      	} 
				      	catch (NumberFormatException e) {
				      		System.out.println("");
					      	System.out.println ("          You have to enter a valid number");
					      	System.out.println("");
					      	exit = false;    
					    }
			      	}	
			      	else
			      	{
			      		exit = false;
			      	}
			  	} catch (NumberFormatException e) {
			  		System.out.println("");
			  		System.out.println ("          You have to enter a valid number");
			  		System.out.println("");
			  		exit = false;    
			  	} 
			}  
			} catch (Exception e) {
			  System.out.println ("PracticalCase Client exception: " + e);
			} 

	}
	
	/**
	 * from a Element, get the tag's value
	 * @return Document
	 * @throws IOException, ParserConfigurationException, SAXException
	*/
	private static String getTagValue(String sTag, Element eElement) 
	{
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();		 
		Node nValue = (Node) nlList.item(0);
		return nValue.getNodeValue();
	}
	  
	/**
	 * from a url, get data in xml format  
	 * @return Document
	 * @throws IOException, ParserConfigurationException, SAXException
	*/
	private static Document getDoc(String nameUrl) throws IOException, ParserConfigurationException, SAXException
	{
		Document doc = null;
		try {
			URL url = new URL(nameUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/xml");

			if (conn.getResponseCode() != 200) 
			{
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(conn.getInputStream());
			doc.getDocumentElement().normalize();
			conn.disconnect();
			} catch (Exception e) {
				  System.out.println ("PracticalCase Client exception: " + e);
			}
			return doc;			  
	}
	
	/**
	 * from a url, get data in json format  
	 * @return JSONObject
	 * @throws IOException, ParserConfigurationException
	*/
	private static JSONObject getJson(String nameUrl) throws IOException, ParserConfigurationException
	{
		JSONObject jsonObject = null;
		try {
			URL url = new URL(nameUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) 
			{
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(convertStreamToString(conn.getInputStream()));			
			jsonObject = (JSONObject) obj;
			
			conn.disconnect();
		} catch (Exception e) {
			System.out.println ("PracticalCase Client Rest exception: " + e);
		}
		return jsonObject;			  
	}
	
	/**
	 * Reads a InputStream and return a String 
	 * @return string
	*/
    private static String convertStreamToString(InputStream is) 
    {        
    	BufferedReader reader = new BufferedReader(new InputStreamReader(is));        
    	StringBuilder sb = new StringBuilder();         
    	String line = null;        
    	try {            
    		while ((line = reader.readLine()) != null) 
    		{                
    			sb.append(line + "\n");            
    		}        
    	} catch (IOException e) {            
    		e.printStackTrace();        		
    	} finally {            
    		try {                
    			is.close();            
    		} catch (IOException e) {                
    			e.printStackTrace();            
    		}        
    	}        
    	return sb.toString();    
    }
    
	/**
	 * Reads a character from keyboard 
	 * @return char
	 * @throws IOException
	*/
	static public char getChar() throws IOException
	{
		char ch = (char) System.in.read();
	    input(); 
	    return ch;
	}

	/**
	 * Reads a string from keyboard
	 * @return String
	 * @throws IOException
	*/
	static public String input() throws IOException
	{
		String input; 
	    //an instance of the BufferedReader class
	    //will be used to read the data
	    BufferedReader reader;
	    //specify the reader variable 
	    //to be a standard input buffer
	    reader = new BufferedReader(new InputStreamReader(System.in));
	    //read the data entered by the user using 
	    //the readLine() method of the BufferedReader class
	    //and store the value in the name variable
	    input = reader.readLine();
	    return input;
	}

	/**
	* Formats a string according to a given length
	* @param stringFormat
	* @param lengthFormat
	* @return String
	*/
	public static String formatString(String stringFormat, int lengthFormat)
	{
		stringFormat = stringFormat.trim();
	    int lengthName = stringFormat.length();
	    if (lengthFormat>0)
	    {
	    	stringFormat +="                                                            ";
	  	  	stringFormat= stringFormat.substring(0, lengthFormat);
	    }
	    else if (lengthFormat<0)
	    {
	  	  	stringFormat = "1                                                           "+stringFormat;
	  	  	stringFormat = stringFormat.substring(60+lengthName-(-lengthFormat));
	    }
	    return stringFormat;
	}

}
