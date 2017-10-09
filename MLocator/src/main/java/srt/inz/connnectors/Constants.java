package srt.inz.connnectors;


public interface Constants {

    //Progress Message
    String LOGIN_MESSAGE="Logging in...";
    String REGISTER_MESSAGE="Register in...";

    //Urls
    String BASE_URL="https://mlocator.000webhostapp.com/";
        
    String LOGIN_URL=BASE_URL+"sf_login.php?";    
    String REGISTER_URL=BASE_URL+"iregister.php?";
    String REQUESTLOCATION_URL=BASE_URL+"locret.php?";
    String ADDNUMBER_URL=BASE_URL+"add_num.php?";   
    String FRIENDLOGIN_URL=BASE_URL+"sf_login.php?";   
    String GETNUMBER_URL=BASE_URL+"trustnumret.php?";
    String SAVELOCATION_URL=BASE_URL+"saveloc.php?";
    

    //Details
    String SMSSERVICE="telSms";
    String ENUMBER="e_num";
    String ENUMBER1="e_num1";
    String ENUMBER2="e_num2";
    String LOGINSTATUS="LoginStatus";
    String MYLOCATON="MyLocation";
    String USERID="UserID";
    
    //SharedPreference
    String PREFERENCE_PARENT="Parent_Pref";

   
}
