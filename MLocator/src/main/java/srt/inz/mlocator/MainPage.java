package srt.inz.mlocator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainPage extends Activity {

	TextView t;
	Button lo,re;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        t=(TextView)findViewById(R.id.wel);
        lo=(Button)findViewById(R.id.log);
        re=(Button)findViewById(R.id.regi);
        
        lo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(getApplicationContext(), Login.class);
				startActivity(i);
				
			}
		});
        
        re.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(getApplicationContext(), Register.class);
				startActivity(i);
				
			}
		});
        
        
    }
}
