package client.Android;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import client.Android.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListCategoryActivity extends ListActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    
        List<String> category = getCategory();
        
        setListAdapter(new ArrayAdapter<String>(this, R.layout.main, category));

		ListView listView = getListView();
		listView.setTextFilterEnabled(true);

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
		    Intent i = new Intent(ListCategoryActivity.this, ListPetsActivity.class);
		    i.putExtra("category", ((TextView) view).getText().toString());
		    startActivity(i);        
		}});         	
	}
	
	public List<String> getCategory(){
		
		List<String> category= new ArrayList<String>();
		Document doc = null;
		
		try {			 
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet("http://10.0.2.2:8080/PracticalCaseWS/WSCatalogRest/listAllCategories");
			getRequest.addHeader("accept", "application/xml");
	 
			HttpResponse response = httpClient.execute(getRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatusLine().getStatusCode());
			}
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse((response.getEntity().getContent()));
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("category");
		  	for (int temp = 0; temp < nList.getLength(); temp++) 
		  	{						
		  		Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) 
				{				 
					Element eElement = (Element) nNode;
					category.add(getTagValue("name", eElement));	    		  	
				}
			}
			httpClient.getConnectionManager().shutdown();
		}
		catch(Exception ex)
		{
			Log.e("Connection error: ","Error!", ex);
		}
		return category;
	}
	
	private static String getTagValue(String sTag, Element eElement) 
	{
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();		 
		Node nValue = (Node) nlList.item(0);		
		return nValue.getNodeValue();
	}
}