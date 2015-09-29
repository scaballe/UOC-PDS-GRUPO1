package clientSoap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Iterator;

import clientSoap.PetTO;
import clientSoap.CatalogFacadeBeanWSSoapService;
import clientSoap.CatalogFacadeRemoteWSSoap;

class ClientWSJavaSoap {
    @SuppressWarnings("unchecked")
	public static void main(String args[ ]) 
    {
	    CatalogFacadeBeanWSSoapService service = new CatalogFacadeBeanWSSoapService();
	    CatalogFacadeRemoteWSSoap port = service.getWSCatalogSoapPort();
	    
	    java.util.Collection<CategoryTO> resultCat;
	    java.util.Collection<PetTO> resultPets;
	    PetTO resultPet;
	
	    try {
	    	//Get a Category Collection 
	    	resultCat= port.listAllCategories();
	    	String c; 
	    	int n;
	  
	    	@SuppressWarnings("rawtypes")
	    	ArrayList nameCategory = new ArrayList();
	    	boolean exit = false;
	    	while (!exit)
	    	{
	    		n=1;
	    		Iterator<CategoryTO> catIt = resultCat.iterator(); 
	    		System.out.println("");
	    		System.out.println("          P E T S    S H O P");
	    		System.out.println("          Practical Case Study based on Soap Web Service");
	    		System.out.println("          You can list all the pets or list them by category.");
	    		System.out.println("");
	    		while (catIt.hasNext()) 
	    		{ 
	    			nameCategory.add(catIt.next());	    		  	
	    			System.out.println("          "+n+" - "+((CategoryTO)nameCategory.get(n-1)).getName() + " "); 
	    			n +=1;
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
	    				if (((CategoryTO)nameCategory.get(Integer.parseInt(String.valueOf(c))-1)).getName().equals("ALL PETS"))
	    				{
	    					//Get all Pets Collection
	    					resultPets= port.listAllPets();		      	      
	    				}
	    				else
	    				{
	    					//Get Pets by Category Collection
	    					resultPets= port.listPetsByCategory(((CategoryTO)nameCategory.get(Integer.parseInt(String.valueOf(c))-1)).getName());      	   					      
	    				}
	    				Iterator<?> petsIt = resultPets.iterator(); 
	    				System.out.println("");
	    				System.out.println("          Num  "+formatString("Pet's name",12)+formatString("Description",16)+formatString("Price",7)+formatString("Category",12)); 		      		
	    				while (petsIt.hasNext()) 
	    				{ 
	    					PetTO listPet = (PetTO)petsIt.next();
	    					System.out.println("          "+formatString(String.valueOf(nP),-2)+" - "+formatString(listPet.getName().toString().toUpperCase(),11)+
	    							" "+formatString(listPet.getDescription().toString(),15)+"  "+formatString(Float.toString(listPet.getPrice()),4)+ "  "+formatString(listPet.getCategory().toString(),12)); 			      		
	    					nP +=1;
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
	    						petsIt = resultPets.iterator();
	    						int nPet = 1;
	    						int numPet = 1;
	    						while (petsIt.hasNext()) 
	    						{ 
	    							if (nPet ==Integer.parseInt(String.valueOf(c1)))
	    							{
	    								PetTO listPet = (PetTO)petsIt.next();
	    								numPet = listPet.getId();		      	   
	    							}
	    							else {petsIt.next();}
	    							nPet +=1;		      	   			
	    						}	
		      			
	    						//Get a pet
	    						resultPet= port.showPet(numPet);		      	      
	    						System.out.println("          I N F O R M A T I O N   A B O U T   P E T");					      
	    						System.out.println("");
	    						System.out.println("          ID number      : "+resultPet.getId() + " "); 					   
	    						System.out.println("          Data From      : "+resultPet.getDatebirth() + " "); 			      			
	    						System.out.println("          Pet name       : "+resultPet.getName() + " "); 
	    						System.out.println("          Pet description: "+resultPet.getDescription() + " "); 
	    						System.out.println("          Price          : "+resultPet.getPrice() + " "); 
	    						System.out.println("          Class pet      : "+resultPet.getCategory() + " ");
	    						System.out.println("");
	    						System.out.print("          Press ENTER to continue: ");
	    						getChar();			      	
	    					}
	    				} catch (NumberFormatException e) {
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


