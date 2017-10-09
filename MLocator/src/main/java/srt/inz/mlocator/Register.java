package srt.inz.mlocator;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.internal.em;

import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class Register extends Activity{
	
	EditText nam,mail,phn,usr,pas,cpas;
	String sh;
	String snam,smail,sphn,susr,spas,scpas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		 nam=(EditText)findViewById(R.id.name);
		 mail=(EditText)findViewById(R.id.email);
		 phn=(EditText)findViewById(R.id.phone);
		 usr=(EditText)findViewById(R.id.username);
		 pas=(EditText)findViewById(R.id.password);
		 cpas=(EditText)findViewById(R.id.confpassword);
		
		Button reg=(Button)findViewById(R.id.button1);
		reg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				snam=nam.getText().toString();
				smail=mail.getText().toString();
				susr=usr.getText().toString();
				sphn=phn.getText().toString();
				spas=pas.getText().toString();
				scpas=cpas.getText().toString();
				
						if(snam.equals("")||smail.equals("")||susr.equals("")||sphn.equals("")||spas.equals("")||scpas.equals(""))
						{
							Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
							return;
						}
						
						if(!spas.contentEquals(scpas))
						{
							Toast.makeText(getApplicationContext(), "Password Mismatch", Toast.LENGTH_SHORT).show();
							return;
						}
						else
						{
							if(getValidate(snam, sphn, smail, spas))
							{
								ireg rg= new ireg();
								rg.execute();
							}
							else
							{
								Toast.makeText(getApplicationContext(), "Validation error", Toast.LENGTH_SHORT).show();
							}
						}
				
				
			}
		});
	}
	public class ireg extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
			String urlParameters = null;
			try {

				urlParameters =  "Name=" + URLEncoder.encode(snam, "UTF-8") + "&&"
                        + "Email=" + URLEncoder.encode(smail, "UTF-8")+ "&&"
                        + "Phonenumber=" + URLEncoder.encode(sphn, "UTF-8") + "&&"
                        + "Username=" + URLEncoder.encode(susr, "UTF-8") + "&&"
                        + "Password=" + URLEncoder.encode(spas, "UTF-8");		
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error:"+e);
			}
			 sh = Connectivity.excutePost(Constants.REGISTER_URL,
	                    urlParameters);
	            Log.e("You are at", "" + sh);
			
			return sh;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			if(sh.contains("Successfully Registered"))
			{
				Toast.makeText(getApplicationContext(), sh, Toast.LENGTH_LONG).show();
				Intent i = new Intent(Register.this, Login.class);
				startActivity(i);
				
			}
			else
			{
				Toast.makeText(getApplicationContext(), sh, Toast.LENGTH_LONG).show();
			}			
			
			super.onPostExecute(result);				
		
		}
		
		
	}
	
	
	private boolean getValidate(String st_name,String st_phone, String st_email, String st_password) {
	      
		
		if (st_password.length()==0)
        {
            pas.setError("Please enter Password");
            return false;
        }
        
        if (st_phone.length()!=10)
        {
            phn.setError("Invalid phone No:");
            return false;
        }
        if (st_name.length()==0)
        {
            nam.setError("Enter the Name");
            return false;
        }
        if(st_email.indexOf("@")==-1)
        {
        	mail.setError("Invalid emailId");
            return false;
        }

        return true;
    }
	
}

