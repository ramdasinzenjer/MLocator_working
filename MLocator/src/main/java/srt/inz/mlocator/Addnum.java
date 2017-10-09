package srt.inz.mlocator;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
import android.widget.TextView;
import android.widget.Toast;

public class Addnum extends Activity{

	
	EditText p1;
	String sp,sp1,sh;
	Button sub,nex;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnum);
		p1=(EditText)findViewById(R.id.nm1);
		
		sub=(Button)findViewById(R.id.btn_submit);
		nex=(Button)findViewById(R.id.btn_skip);


		/*TelephonyManager tMgr =(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
	    sp = tMgr.getLine1Number();*/
		SharedPreferences share1=this.getSharedPreferences("kynum", MODE_WORLD_READABLE);
		sp=share1.getString("sssnum","");


		sub.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sp1=p1.getText().toString();
				
				add_number adn=new add_number();
				adn.execute();
			}
		});
		nex.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in=new Intent(getApplicationContext(),Second_servactivity.class);
				startActivity(in);	
			}
		});
	
		
	}
	public class add_number extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String urlParameters = null;
            try {
                urlParameters =  "phonenumber=" + URLEncoder.encode(sp, "UTF-8") + "&&"
                        + "trusted=" + URLEncoder.encode(sp1, "UTF-8");
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error:"+e);
			}
            
            sh=Connectivity.excutePost(Constants.ADDNUMBER_URL,
                    urlParameters);
            Log.e("You are at", "" + sh);
			return sh;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			if(sh.contains("success"))
			{
				Toast.makeText(getApplicationContext(), "Trusted list Updated", Toast.LENGTH_LONG).show();
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
			}
			
			super.onPostExecute(result);
		
			
		}
		
		
	}
	

}
