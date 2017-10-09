package srt.inz.mlocator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import srt.inz.connnectors.Connectivity;
import srt.inz.connnectors.Constants;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class Login extends Activity{
	
	EditText us,ps;
	String sus,sps,sh1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
	 us=(EditText)findViewById(R.id.username);
	 ps=(EditText)findViewById(R.id.password);
	Button lgb=(Button)findViewById(R.id.button1);
		lgb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				sus=us.getText().toString();
				sps=ps.getText().toString();
				SharedPreferences shr=getSharedPreferences("kynum", MODE_APPEND);
				SharedPreferences.Editor edd=shr.edit();
				edd.putString("sssnum", sus);
				edd.commit();
				
				if(sus.equals("")||sps.equals(""))
				{
					
					Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
					return;
				}
				else
				{
					
					if(getValidate(sus, sps))
					{
						ilog lg =new ilog();
						lg.execute();
					
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
					}
			
				}
				
			}
		});
		
	}
	
	public class ilog extends AsyncTask<String,String,String> {
	    
	    @Override
	    protected String doInBackground(String... params) {


	            String urlParameters = null;
	            try {
	                urlParameters =  "phonenumber=" + URLEncoder.encode(sus, "UTF-8") + "&&"
	                        + "password=" + URLEncoder.encode(sps, "UTF-8");
	            } catch (UnsupportedEncodingException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	            sh1 = Connectivity.excutePost(Constants.LOGIN_URL,
	                    urlParameters);
	            Log.e("You are at", "" + sh1);
	            

	       return sh1;
	    }
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			if(sh1.contains("success"))
			{
				Toast.makeText(getApplicationContext(), "Successfully Logged in.", Toast.LENGTH_LONG).show();
				Intent i=new Intent(getApplicationContext(), Second_servactivity.class);
				startActivity(i);
				
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Usernmae or Password error.", Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), "New User? Register First", Toast.LENGTH_LONG).show();
				
				
			}
			super.onPostExecute(result);
			
			
			
		}
		
	}
	
	private boolean getValidate(String st_phone, String st_password) {
	      
		
		if (st_password.length()==0)
        {
            ps.setError("Please enter Password");
            return false;
        }
        
        if (st_phone.length()!=10)
        {
            us.setError("Invalid phone No:");
            return false;
        }
     
        return true;
    }
	

}
