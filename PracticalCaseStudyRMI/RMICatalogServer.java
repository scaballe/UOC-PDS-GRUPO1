/*
 * Copyright FUOC.  All rights reserved.
 * @author Vicenç Font Sagristà, 2011
 */
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMICatalogServer {
	/**
	 * Server for the RMI Practical Case.
	 */
	public RMICatalogServer()
	{
	   	try {
    		RMICatalogImpl obj = new RMICatalogImpl();
    		RMICatalogInterface stub = (RMICatalogInterface) UnicastRemoteObject.exportObject(obj, 0);
    	    // Bind the remote object's stub in the registry
    	    Registry registry = LocateRegistry.getRegistry();
    	    registry.bind("PracticalCaseRMI", stub);

    	    System.err.println("Practical Case RMI Server ready");
    	} catch (Exception e) {
    	    System.err.println("Practical Case RMI Server exception: " + e.toString());
    	    e.printStackTrace();
    	}		
	}
	
    public static void main(String args[]) 
    {
    	new RMICatalogServer();
    }    
}
