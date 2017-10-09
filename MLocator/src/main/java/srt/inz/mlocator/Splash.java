package srt.inz.mlocator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {
	//MediaPlayer ourSong;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.spalsh);
		//ourSong=MediaPlayer.create(MainActivity.this, R.raw.vlc);
		//ourSong.start();
			Thread timer=new Thread(){
			public void run()
			{
				try{
			sleep(1000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}finally{
			Intent i=new Intent(getApplicationContext(),MainPage.class);
			startActivity(i);
		}
		}
		};
		timer.start();
		}
	@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			//ourSong.release();
			finish();
		
	}

		

		}
