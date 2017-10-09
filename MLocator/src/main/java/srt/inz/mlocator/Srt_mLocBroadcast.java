package srt.inz.mlocator;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class Srt_mLocBroadcast extends BroadcastReceiver
{ 
	

		String strMessage = "";
		String strMessagen;


		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
		
			 
	        Bundle myBundle = intent.getExtras();
		        SmsMessage [] messages = null;
		      

		        if (myBundle != null)
		        {
		            Object [] pdus = (Object[]) myBundle.get("pdus");
		            messages = new SmsMessage[pdus.length];

		            for (int i = 0; i < messages.length; i++)
		            {
		                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
		                strMessagen = messages[i].getOriginatingAddress();
		                //strMessage += " : ";
		                strMessage += messages[i].getMessageBody();
		                strMessage += "\n";
		            

		            Toast.makeText(context, strMessage, Toast.LENGTH_SHORT).show();
		            if(strMessage.contains("ring"))
		            {
		            	
		            	Toast.makeText(context, "Ringing...", Toast.LENGTH_SHORT).show();
		            	AudioManager amanager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		    			  amanager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		            	Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		            	Ringtone r= RingtoneManager.getRingtone(context, soundUri);
		            	r.play();
		            	
		            }
		            else if(strMessage.contains("loc"))
		            {
		            	Toast.makeText( context,""+strMessagen,Toast.LENGTH_SHORT).show();
		            	
		            	
		            	
		     			 try {
		     				SmsManager smsManager = SmsManager.getDefault();
		    			//	smsManager.sendTextMessage(strMessagen, null, "hi", null, null);
		     				smsManager.sendTextMessage(strMessagen, null, " Your phone is at : " +SrtService.stplace , null, null);
		     				Toast.makeText(context, "SMS Sent!",
		     							Toast.LENGTH_SHORT).show();
		     				
		     				
		     			  } catch (Exception e) {
		     				Toast.makeText(context,
		     					"Failed!",
		     					Toast.LENGTH_LONG).show();
		     				e.printStackTrace();
		     			  }
		     			
		            }
		            else if (strMessage.contains("moden"))
		            {
		            	AudioManager amanager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		  			  amanager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		  			 Toast.makeText(context, "Mode changed to Normal", Toast.LENGTH_SHORT).show();
		  			
		            }
		            }
		        }
		       
			
		}
  
   
    }

