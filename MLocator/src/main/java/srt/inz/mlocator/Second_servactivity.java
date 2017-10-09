package srt.inz.mlocator;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Second_servactivity extends Activity {

	String sph,spass,rsh,sp,response;
	TextView tv;
	Button emsg,fnd,adnum;
	EditText ephn,epass;
	
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_servactivity);
		ephn=(EditText)findViewById(R.id.fr_phn);
		epass=(EditText)findViewById(R.id.fr_pass);
		SharedPreferences share1=this.getSharedPreferences("kynum", MODE_APPEND);
		sp=share1.getString("sssnum","");
		
		emsg=(Button)findViewById(R.id.button3);
		fnd=(Button)findViewById(R.id.bt_fnd);
		adnum=(Button)findViewById(R.id.bt_addnum);
		
		emsg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Trustlistmsg().execute();
			}
		});
		
		adnum.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),Addnum.class);
				startActivity(i);
			}
		});
		
		fnd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sph=ephn.getText().toString();
				spass=epass.getText().toString();
				
				new safe_log().execute();
			}
		});
		
		 
	}
	public class safe_log extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String urlParameters = null;
            try {
                urlParameters =  "phonenumber=" + URLEncoder.encode(sph, "UTF-8") + "&&"
                        + "password=" + URLEncoder.encode(spass, "UTF-8");
								
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error:"+e);
			}
            rsh=Connectivity.excutePost(Constants.FRIENDLOGIN_URL,
                    urlParameters);
			return null;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			if(rsh.contains("success"))
			{
				Toast.makeText(getApplicationContext(), "Hello User !... "+sph, Toast.LENGTH_LONG).show();
				Intent i=new Intent(getApplicationContext(),Moblocator.class);
				startActivity(i);
				
				SharedPreferences share=getSharedPreferences("keynum", MODE_WORLD_READABLE);
				SharedPreferences.Editor ed=share.edit();
				ed.putString("srtnum",sph);
				ed.commit();
				
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Incorrect phone number or ID ", Toast.LENGTH_LONG).show();
				
			}
			
			super.onPostExecute(result);
		
			
		}
		
		
	}

	 public void mstartService(View view) {
		startService(new Intent(this, srt.inz.mlocator.SrtService.class));
		        	        
	}

	// Stop the service
	public void stopService(View view) {
		stopService(new Intent(this, srt.inz.mlocator.SrtService.class));
	
	}


	public void send_msg(String num)
	{
		try{
		SmsManager smsManager = SmsManager.getDefault();
				smsManager.sendTextMessage(num, null, " Emergency : I am in trouble at  "+SrtService.stplace , null, null);
				Toast.makeText(getBaseContext(), "SMS Sent!",
							Toast.LENGTH_SHORT).show();
		}catch (Exception e) {
				Toast.makeText(getApplicationContext(),
     					"Failed!",
     					Toast.LENGTH_SHORT).show();
     				e.printStackTrace();
     			  }
		
	}
	
	class Trustlistmsg extends AsyncTask<String, String, String>
	  {
		 
		
		  @Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			  String urlParameters = null;
	            try {
	                urlParameters =  "phonenumber=" + URLEncoder.encode(sp, "UTF-8");
		}
		catch(Exception e)
		{
			System.out.println("Error"+e);
		}
		
	            response=Connectivity.excutePost(Constants.GETNUMBER_URL, urlParameters);
	            Log.e("You are at", "" + response);
		return response;
		}
		  @Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			parsingmethod();
		}
	  }
	  public void parsingmethod(){
		  try
		  {
			  JSONObject job=new JSONObject(response);
			  JSONObject job1=job.getJSONObject("Event");
			  JSONArray ja=job1.getJSONArray("Details");
			int  length=ja.length();
			  for(int i=0;i<length;i++)
			  {
				  JSONObject c=ja.getJSONObject(i);
				  //storing json item in variable
				 // name2=c.getString(TAG_ID);
				 String phn2=c.getString("trusted");
				 
				  send_msg(phn2);
				 
				}
				  
			 }
		  catch(JSONException e)
		  {
			  e.printStackTrace();
			  }
		  }
	
}

