package client.Android;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowPetActivity extends Activity {
    private String photo;
    private TextView idnumber;
    private TextView date;
    private TextView name;
    private TextView description;
    private TextView price;
    private TextView category;
    private ImageView petImage;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle=getIntent().getExtras();
        
        setContentView(R.layout.showpet);
     
		idnumber = (TextView) findViewById(R.id.idnumber); 
		date = (TextView) findViewById(R.id.date);
		name = (TextView) findViewById(R.id.name); 
		description = (TextView) findViewById(R.id.description);
		price = (TextView) findViewById(R.id.price); 
		category = (TextView) findViewById(R.id.category);
        petImage = (ImageView)findViewById(R.id.photo);
               
		JSONObject showpet = getPet(bundle.getString("idpet"));

		try {
			idnumber.setText(((Integer)showpet.get("id")).toString());
			date.setText((String)showpet.get("datebirth"));
			name.setText((String)showpet.get("name"));
			description.setText((String)showpet.get("description"));
			price.setText(((Double)showpet.get("price")).toString());
			category.setText((String)showpet.get("category"));
			photo = (String)showpet.get("photo");
	        downloadFile("http://10.0.2.2:8080/PracticalCaseWS/resources/imganimals/"+photo);
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}
	
	public JSONObject getPet(String idpet){
		
		JSONObject pet= new JSONObject();
		HttpGet getRequest;
		try {			 
			DefaultHttpClient httpClient = new DefaultHttpClient();
			getRequest = new HttpGet("http://10.0.2.2:8080/PracticalCaseWS/WSCatalogRest/showPet/"+idpet);
    		getRequest.addHeader("accept", "application/json");
	 
			HttpResponse response = httpClient.execute(getRequest);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					   + response.getStatusLine().getStatusCode());
			}
	       	String respStr = EntityUtils.toString(response.getEntity());
	       	pet = new JSONObject(respStr);
		}
	    catch(Exception ex)
	    {
	       	Log.e("ServicioRest","Error!", ex);
	    }
		return pet;
	}
	
	Bitmap bmImg; 
	void downloadFile(String fileUrl){
         URL myFileUrl =null;          
         try {
              myFileUrl= new URL(fileUrl);
         } catch (MalformedURLException e) {
              e.printStackTrace();
         }
         try {
              HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
              conn.setDoInput(true);
              conn.connect();
              InputStream is = conn.getInputStream();
              
              bmImg = BitmapFactory.decodeStream(is);
              petImage.setImageBitmap(bmImg);
         } catch (IOException e) {
              e.printStackTrace();
         }
    }
}


