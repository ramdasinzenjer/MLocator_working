package srt.inz.mlocator;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

import srt.inz.connnectors.ApplicationPreference;
import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class SrtService extends Service implements LocationListener {
	
	static String stplace,r;
	Location location; double latitude,longitude;
	ApplicationPreference applicationPreference;

	public SrtService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();
		applicationPreference=(ApplicationPreference)getApplication();

	}

	@Override
	public void onStart(Intent intent, int startId) {
		// Perform your long running operations here.
		
		getmyloc();
		Toast.makeText(this, "Message Reading Service Started", Toast.LENGTH_LONG).show();

	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
		PackageManager pm  = this.getPackageManager();
        ComponentName componentName = new ComponentName(getBaseContext(), srt.inz.mlocator.Srt_mLocBroadcast.class);
        pm.setComponentEnabledSetting(componentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP);
		
	}
	
	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	public class savloc extends AsyncTask<String, String, String>
	{

	
	@Override
	protected String doInBackground(String... arg0) {
	// TODO Auto-generated method stub
		String urlParameters = null;
		try 
		{			
					SharedPreferences share1=getSharedPreferences("kynum", MODE_WORLD_READABLE);
					String shared_num=share1.getString("sssnum","");
					
					urlParameters =  "phonenumber=" + URLEncoder.encode(shared_num, "UTF-8")+"&&"
							+"Location=" + URLEncoder.encode(stplace, "UTF-8");
				}
				catch(Exception e)
				{
						System.out.println("Error:"+e);
				}
		r=Connectivity.excutePost(Constants.SAVELOCATION_URL, urlParameters);
				return r;
	}
	@Override
	protected void onPostExecute(String result) {
	// TODO Auto-generated method stub
		if(r.contains("success"))
		{
			Toast.makeText(getApplicationContext(), "Location updated ", Toast.LENGTH_SHORT).show();
	
	
		}
			else
			{
			Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
			}
		super.onPostExecute(result);
	
	
		}

	}
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
			Geocoder gc= new Geocoder(this, Locale.ENGLISH);
	        // Getting latitude of the current location
	        latitude =  location.getLatitude();
	
	        // Getting longitude of the current location
	        longitude =  location.getLongitude();
	
	try {
		List<Address> addresses = gc.getFromLocation(latitude,longitude, 1);
		
		if(addresses != null) {
			Address returnedAddress = addresses.get(0);
			StringBuilder strReturnedAddress = new StringBuilder("");
			for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) 
			{
				strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
				
			}
		
			stplace=strReturnedAddress.toString();
			
			 // 	applicationPreference.setMyLocation(stplace);
			Toast.makeText( getBaseContext(),stplace,Toast.LENGTH_SHORT).show();
			
			new savloc().execute();
		}
	//stores the current address to shared preferene shr.
		
		else{
			Toast.makeText(getBaseContext(),"GPS Disabled",Toast.LENGTH_SHORT).show();
		
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
	
	public void getmyloc()
	{
		try {
						
			 LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			    // Creating a criteria object to retrieve provider
			    Criteria criteria = new Criteria();

			    // Getting the name of the best provider
			    String provider = locationManager.getBestProvider(criteria, true);

			    // Getting Current Location
			    location = locationManager.getLastKnownLocation(provider);
			    

			    if(location!=null){
			            onLocationChanged(location);
			            
			    }

			    locationManager.requestLocationUpdates(provider, 120000, 0, this);
		} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
		}
	}
	
}