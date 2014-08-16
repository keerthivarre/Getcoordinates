package com.example.getcoordinates;
  
import java.io.IOException;  
import java.util.List; 
import java.util.Locale;





import android.app.Activity;  
import android.app.AlertDialog;  
import android.app.Dialog;
import android.app.TabActivity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;  
import android.content.Context;  
import android.content.DialogInterface;  
import android.content.Intent;  
import android.content.pm.ActivityInfo;  
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.location.Address;  
import android.location.Geocoder;  
import android.location.Location;  
import android.location.LocationListener;  
import android.location.LocationManager;  
import android.net.Uri;
import android.os.Bundle;  
import android.os.Handler;
import android.provider.Settings;  
import android.util.Log;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.webkit.WebSettings.TextSize;
import android.widget.Button;  
import android.widget.EditText;  
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;  
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;  
import android.widget.TabHost.TabSpec;
  
public class LocationActivity extends Activity   
implements OnClickListener {  
   
 private LocationManager locationManager=null;  
 private LocationListener locationListener=null;   
 private Button btnGetLocation = null;  
 private EditText editLocation = null;   
 private ProgressBar pb =null;  
   
 private static final String TAG = "Debug";  
 private Boolean flag = false;  
 

  
 @Override  
 public void onCreate(Bundle savedInstanceState) {  
  super.onCreate(savedInstanceState);  
  setContentView(R.layout.activity_location);  
    
    
  //if you want to lock screen for always Portrait mode    
  setRequestedOrientation(ActivityInfo  
  .SCREEN_ORIENTATION_PORTRAIT);  
  
  pb = (ProgressBar) findViewById(R.id.progressBar);  
  pb.setVisibility(View.INVISIBLE);  
  
    
  editLocation = (EditText) findViewById(R.id.editTextLocation);   
  
  final ImageButton btButton = (ImageButton) findViewById(R.id.imagebutton);  
  btButton.setOnClickListener(this);
  
  ImageView imview=(ImageView) findViewById(R.id.animationImage);
	imview.setBackgroundResource(R.layout.animation);
	AnimationDrawable frameAnimation = (AnimationDrawable) imview.getBackground();
	
	
	if (frameAnimation.isRunning()) { 
		frameAnimation.stop(); 
		
		} 
	else { 
		frameAnimation.start(); 
		
		} 
  Button list=(Button) findViewById(R.id.list);
  list.setOnClickListener(new View.OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent list=new Intent(getApplicationContext(),SendActivity.class);
		startActivity(list);
	}
});
  
  
 
  
 
    
  locationManager = (LocationManager)   
  getSystemService(Context.LOCATION_SERVICE);  
  
 }  
 
 
  
 @Override  
 public void onClick(View v) {  
  flag = displayGpsStatus();  
 if (flag) {  
     
   Log.v(TAG, "onClick");    
     
  /* editLocation.setText("Please!! move your device to"+  
   " see the changes in coordinates."+"\nWait..");  */
     
   pb.setVisibility(View.VISIBLE);  
   locationListener = new MyLocationListener();  
   ProgressBar spinner = new android.widget.ProgressBar(this,
           null,
           android.R.attr.progressBarStyle);

spinner.getIndeterminateDrawable().setColorFilter(0xFF000000, android.graphics.PorterDuff.Mode.MULTIPLY);
  
   locationManager.requestLocationUpdates(LocationManager  
   .GPS_PROVIDER, 1000055000, 10,locationListener);
   
 
 
 
   
  }

 
      
    else {  
   alertbox("Gps Status!!", "Your GPS is: OFF");  
    }  
 
  
  
 
 }
  
 /*----Method to Check GPS is enable or disable ----- */  
 private Boolean displayGpsStatus() {  
 ContentResolver contentResolver = getBaseContext()  
  .getContentResolver(); 
  boolean gpsStatus =
  Settings.Secure  
  .isLocationProviderEnabled(contentResolver,   
  LocationManager.GPS_PROVIDER); 
  if (gpsStatus) { 
   return true;  
  
  } else {  
   return false;  
  }  
 }  
  
 /*----------Method to create an AlertBox ------------- */  
 protected void alertbox(String title, String mymessage) {  
  final AlertDialog.Builder builder = new AlertDialog.Builder(this);  
  builder.setMessage("Your Device's GPS is Disable")  
  .setCancelable(false)  
  .setTitle("** Gps Status **")  
  .setPositiveButton("OK", 
		  
   new DialogInterface.OnClickListener() {  
   public void onClick(DialogInterface dialog, int id) {  
   // finish the current activity  
   //AlertBoxAdvance.this.finish();
	
   
   Intent myIntent = new Intent(  
   Settings.ACTION_LOCATION_SOURCE_SETTINGS);  
   startActivity(myIntent); 
   }  
   })  
   .setNegativeButton("Cancel",  
   new DialogInterface.OnClickListener() {  
   public void onClick(DialogInterface dialog, int id) {  
    // cancel the dialog box  
    dialog.cancel();  
    }  
   });
  pb.setVisibility(View.INVISIBLE);  
  AlertDialog alert = builder.create();  
  alert.show();  
 }  
   
 
 /*private class TabsActivity extends TabActivity {
	 
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_location);
	 
			Resources resssources = getResources(); 
			TabHost tabHost = getTabHost(); 
	 
			// Android tab
			Intent intentAndroid = new Intent().setClass(this, Tab1Activity.class);
			TabSpec tabSpecAndroid = tabHost
			  .newTabSpec("Android")
			  .setIndicator("", resssources.getDrawable(R.drawable.tab1))
			  .setContent(intentAndroid);
	 
			// Apple tab
			Intent intentApple = new Intent().setClass(this, Tab2Activity.class);
			TabSpec tabSpecApple = tabHost
			  .newTabSpec("Apple")
			  .setIndicator("", resssources.getDrawable(R.drawable.tab2))
			  .setContent(intentApple);
	 
			// Windows tab
			Intent intentWindows = new Intent().setClass(this, Tab3Activity.class);
			TabSpec tabSpecWindows = tabHost
			  .newTabSpec("Windows")
			  .setIndicator("", resssources.getDrawable(R.drawable.tab3))
			  .setContent(intentWindows);
	 
			// Blackberry tab
			
			// add all tabs 
			tabHost.addTab(tabSpecAndroid);
			tabHost.addTab(tabSpecApple);
			tabHost.addTab(tabSpecWindows);
		
	 
			//set Windows tab as default (zero based)
			tabHost.setCurrentTab(2);
		}
	 
	}*/
 /*----------Listener class to get coordinates ------------- */  
 private class MyLocationListener implements LocationListener { 

	 public Location location ;

	 public Location getLocation(){
		 return location;
	 }


        @Override  
        public void onLocationChanged(Location loc) {  
            
            editLocation.setText("");  
            pb.setVisibility(View.INVISIBLE);  
             
            String longitude = "Longitude: " +loc.getLongitude();    
      Log.v(TAG, longitude);  
      String latitude = "Latitude: " +loc.getLatitude();  
      Log.v(TAG, latitude);  
            
      location  = loc;
    /*----------to get City-Name from coordinates ------------- */  
      String cityName = null;                
      Geocoder gcd = new Geocoder(getBaseContext(),   
   Locale.getDefault());               
      List<Address>  addresses;    
      try {    
      addresses = gcd.getFromLocation(loc.getLatitude(), loc  
   .getLongitude(), 1);    
      if (addresses.size() > 0)    
         System.out.println(addresses.get(0).getLocality());    
         cityName=addresses.get(0).getLocality();    
        } 
     
      catch (IOException e) {              
        e.printStackTrace(); 
        
      }   
            
      String s = longitude+"\n"+latitude +  
   "\n\nMy Currrent City is: "+cityName;  
           editLocation.setText(s);
           
          final String text =" https://maps.google.com/?q=" + loc.getLatitude()+","+loc.getLongitude();
           System.out.println("location values"+ text);
           
           
             /*Intent waIntent = new Intent(Intent.ACTION_SEND);
             waIntent.setType("text/plain");
                     
             waIntent.setPackage("com.whatsapp");
             if (waIntent != null) {
                 waIntent.putExtra(Intent.EXTRA_TEXT,text); //text);//
                 startActivity(Intent.createChooser(waIntent, "Share with"));
             } 
        }
            /* Intent gmailIntent = new Intent();
             gmailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
             String subject = null;
			gmailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
             gmailIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(gmailIntent, "send to"));
             
        }*/
           Intent i=new Intent(android.content.Intent.ACTION_SEND);
           i.setType("text/plain");
           i.putExtra(android.content.Intent.EXTRA_SUBJECT,"");
           i.putExtra(android.content.Intent.EXTRA_TEXT,text);
           startActivity(Intent.createChooser(i,"Share via"));
           
           
           /*Intent imgintent=new Intent(android.content.Intent.ACTION_SEND);
          imgintent.setType("image/png");
           imgintent.putExtra(Intent.EXTRA_STREAM,R.drawable.i4);
           startActivity(Intent.createChooser(imgintent,getResources().getText(R.string.share)));*/
           
           
           
           Button save=(Button) findViewById(R.id.btn);
           save.setOnClickListener(new View.OnClickListener() {
          	
          	@Override
          	public void onClick(View arg0) {
          		// TODO Auto-generated method stub
          		
          		EditText et=
        				(EditText) findViewById(R.id.editTextLocation);
          		 
          		Intent intent=new Intent(getApplicationContext(),SaveActivity.class);
          		
          		intent.putExtra("text",text);
          		startActivity(intent);
          		/*TextView tv1 = (TextView)findViewById(R.id.save);
               	DbActivity dbHelper = new DbActivity(getApplicationContext());
                   String[] data = dbHelper.getData();
                   for(int j=0;j<data.length;j++){
                   	tv1.append(data[j]+"\n");
                   }*/
        		
          		
          		
          	}
          	
          });
          
           
        }
           
       
		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub

		}


		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub

		}


		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub

		}
           
           
              
        }  
  
        public void onProviderDisabled(String provider) {  
            // TODO Auto-generated method stub           
        }  
  
        public void onProviderEnabled(String provider) {  
            // TODO Auto-generated method stub           
        }  
  
        public void onStatusChanged(String provider,   
  int status, Bundle extras) {  
            // TODO Auto-generated method stub           
        }  
        
        
    }  
 