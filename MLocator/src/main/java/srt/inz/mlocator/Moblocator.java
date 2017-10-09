package srt.inz.mlocator;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("NewApi") public class Moblocator extends FragmentActivity implements OnMapReadyCallback {
	
	GoogleMap googleMap;
	MarkerOptions markerOptions;
	LatLng[] latLng= new LatLng[3];
	LatLng llg;
	
	String respo,shared_num,frndloc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maploc);
		
		SharedPreferences share1=this.getSharedPreferences("keynum", MODE_WORLD_READABLE);
		shared_num=share1.getString("srtnum","");
		
		SupportMapFragment supportMapFragment = (SupportMapFragment) 
				getSupportFragmentManager().findFragmentById(R.id.mapp);

		// Getting a reference to the map
		supportMapFragment.getMapAsync(this);
		
		
		/*Button btn_back = (Button) findViewById(R.id.back);
		btn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intnt=new Intent(getApplicationContext(),Mobiserv.class);
				startActivity(intnt);
				
			}
		});*/
		// Getting reference to btn_find of the layout activity_main
        Button btn_find = (Button) findViewById(R.id.btn_find);
        
        
        
        // Defining button click event listener for the find button
        OnClickListener findClickListener = new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// Getting reference to EditText to get the user input location
				//EditText etLocation = (EditText) findViewById(R.id.et_location);
	
				// Getting user input location
				//String location = etLocation.getText().toString();
				String location = frndloc;
				loc_ret lr=new loc_ret();
		        
		        lr.execute();
		        
		
			}
		};
		
		// Setting button click event listener for the find button
		btn_find.setOnClickListener(findClickListener);	
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {

	}

	private class GeocoderTask extends AsyncTask<String, Void, List<Address>>{

		@Override
		protected List<Address> doInBackground(String... locationName) {
			// Creating an instance of Geocoder class
			Geocoder geocoder = new Geocoder(getBaseContext());
			List<Address> addresses = null;
			
			try {
				// Getting a maximum of 3 Address that matches the input text
				addresses = geocoder.getFromLocationName(locationName[0], 3);
			} catch (IOException e) {
				e.printStackTrace();
			}			
			return addresses;
		}
		
		
		@Override
		protected void onPostExecute(List<Address> addresses) {			
	        
	        if(addresses==null || addresses.size()==0){
				Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
			}
	        
	        // Clears all the existing markers on the map
	        googleMap.clear();
			
	        // Adding Markers on Google Map for each matching address
			for(int i=0;i<addresses.size();i++){				
				
				Address address = (Address) addresses.get(i);
				
		        // Creating an instance of GeoPoint, to display in Google Map
		       llg = new LatLng(address.getLatitude(), address.getLongitude());
		       drawMarker(llg);
				/*latLng[0]= new LatLng(8.476216, 76.97124);
		        latLng[1] = new LatLng(8.514925	,76.988406);
		        latLng[2] = new LatLng(8.614925, 76.988406);*/
		        
		      /*  String addressText = String.format("%s, %s",
                        address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                        address.getCountryName());
		        
		        
		        for (int j = 0; j < latLng.length; j++) {
		        	drawMarker(latLng[j]);
		        }*/

		       
			}			
		}		
	}
	private void drawMarker(LatLng point) {
	    // Creating an instance of MarkerOptions
	   // MarkerOptions markerOptions = new MarkerOptions();

	    MarkerOptions markerOptionsc=new MarkerOptions();
	    // Setting latitude and longitude for the marker
	    
	 //   markerOptions.position(point);

	    markerOptionsc.position(llg);
	    markerOptionsc.title("center");

	    double radiusInMeters = 100.0;
	     //red outline
	    int strokeColor = 0xffff0000;
	    //opaque red fill
	    int shadeColor = 0x44ff0000; 


	    CircleOptions circleOptions = new CircleOptions().center(llg).radius(radiusInMeters).fillColor(shadeColor).strokeColor(strokeColor).strokeWidth(2);
	    googleMap.addCircle(circleOptions);
	    // Adding marker on the Google Map
	  //  googleMap.addMarker(markerOptions);
	    
	  //  googleMap.addMarker(markerOptionsc);
	    CameraUpdate center=
	            CameraUpdateFactory.newLatLng(llg);
	        CameraUpdate zoom=CameraUpdateFactory.zoomTo(10);

	        googleMap.moveCamera(center);
	        googleMap.animateCamera(zoom);
	        
	        
	        final Circle circle = googleMap.addCircle(new CircleOptions().center(llg)
	                .strokeColor(Color.CYAN).radius(1000));

	        ValueAnimator vAnimator = new ValueAnimator();
	        vAnimator.setRepeatCount(ValueAnimator.INFINITE);
	        vAnimator.setRepeatMode(ValueAnimator.RESTART);  /* PULSE */
	        vAnimator.setIntValues(0, 100);
	        vAnimator.setDuration(1000);
	        vAnimator.setEvaluator(new IntEvaluator());
	        vAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
	        vAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
	            @Override
	            public void onAnimationUpdate(ValueAnimator valueAnimator) {
	                float animatedFraction = valueAnimator.getAnimatedFraction();
	                // Log.e("", "" + animatedFraction);
	                circle.setRadius(animatedFraction * 100);
	            }
	        });
	        vAnimator.start();
	        
	}
		public class loc_ret extends AsyncTask<String, String, String>
		{

			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				String urlParameters = null;
				try 
				{
					
					urlParameters =  "phonenumber=" + URLEncoder.encode(shared_num, "UTF-8");
	
				}
				catch(Exception e)
				{
					System.out.println("Error"+e);
				}
				respo=Connectivity.excutePost(Constants.REQUESTLOCATION_URL,
	                    urlParameters);
	            Log.e("You are at", "" + respo);		
				return respo;
			}
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				if(respo.contains("success"))
				{
					Toast.makeText(getApplicationContext(), "Location fetching", Toast.LENGTH_SHORT).show();
					parsingmethod();
					
				}
				else 
				{
					Toast.makeText(getApplicationContext(), "No current locations available ", Toast.LENGTH_SHORT).show();
				}
				
				
			}
			
			 public void parsingmethod(){
				  try
				  {
					  JSONObject job=new JSONObject(respo);
					  JSONObject job1=job.getJSONObject("Event");
					  JSONArray ja=job1.getJSONArray("Details");
				int	length=ja.length();
					  for(int i=0;i<length;i++)
					  {
						  JSONObject c=ja.getJSONObject(i);
						  //storing json item in variable
						  frndloc=c.getString("Location");
					//	 Toast.makeText(getApplicationContext(), ""+frndloc, Toast.LENGTH_LONG).show();				 
				  
						}
					  if(frndloc!=null && !frndloc.equals("")){
							new GeocoderTask().execute(frndloc);		
						}
					 }
				  catch(JSONException e)
				  {
					  e.printStackTrace();
					  }
				  }
			
		}
	
	
}
