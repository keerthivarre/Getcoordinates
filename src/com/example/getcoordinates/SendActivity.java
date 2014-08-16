package com.example.getcoordinates;

import java.util.List;

import org.w3c.dom.Text;






import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView.FindListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class SendActivity extends ActionBarActivity implements OnItemSelectedListener {

	Spinner spinner;
	Cursor cursor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save);
		 final EditText tv=(EditText) findViewById(R.id.edit);
		Intent in=getIntent();
		 String tv1=in.getStringExtra("text");
		tv.setText(tv1);
		
		
		
	       final EditText inputLabel = (EditText) findViewById(R.id.name);  

		  spinner = (Spinner) findViewById(R.id.spinner);  
	       Button btnAdd = (Button) findViewById(R.id.save);  
	        spinner.setOnItemSelectedListener(this);  
	   String text1=spinner.getSelectedItem().toString();
	        // Loading spinner data from database  
	        loadSpinnerData();  
	        
	        btnAdd.setOnClickListener(new View.OnClickListener() {  
	   
	            @Override  
	            public void onClick(View arg0) {  
	            	 String label = inputLabel.getText().toString()+" \n "+tv.getText().toString();  
	         	   
	                
	                if (label.trim().length() > 0) {  
	                    DataBaseHandler db = new DataBaseHandler(getApplicationContext());  
	                    db.insertLabel(label);  
	   
	                    // making input filed text to blank  
	                    tv.setText(" ");  
	   
	                    // Hiding the keyboard  
	                    InputMethodManager imm = (InputMethodManager)   
	                          getSystemService(Context.INPUT_METHOD_SERVICE);  
	                    imm.hideSoftInputFromWindow(tv.getWindowToken(), 0);  
	   
	                    // loading spinner with newly added data  
	                    loadSpinnerData();
	                    Intent spintent=new Intent(SendActivity.this,SendActivity.class);
	                    spintent.putExtra("sp",spinner.getSelectedItem().toString());
	                    startActivity(spintent);
	                } else {  
	                    Toast.makeText(getApplicationContext(), "Please enter label name",  
	                            Toast.LENGTH_SHORT).show();  
	                }  
	                finish();
	                
	            }
	            
	            
	            }); 
	        
	        
	       
	    }  
	   
	    /** 
	     * Function to load the spinner data from SQLite database 
	     * @return 
	     * */  
	    private void loadSpinnerData() {  
	        DataBaseHandler db = new DataBaseHandler(getApplicationContext());  
	        List<String> labels = db.getAllLabels();  
	   
	        // Creating adapter for spinner  
	        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, labels);  
	   
	        // Drop down layout style - list view with radio button  
	        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
	   
	        // attaching data adapter to spinner 
	        spinner.setAdapter(dataAdapter);
	        
	        
	    }  
	   
	    public void onItemSelected(final AdapterView<?> parent, View view, int position,  
	            long id) {  
	        // On selecting a spinner item  
	        final String label = parent.getItemAtPosition(position).toString();  
	        // Showing selected spinner item  
	       /* Toast.makeText(parent.getContext(), "Saved" ,  
	                Toast.LENGTH_LONG).show();  */
	        Button sendloc=(Button) findViewById(R.id.send);
			sendloc.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					 Intent j=new Intent(android.content.Intent.ACTION_SEND);
			           j.setType("text/plain");
			           j.putExtra(android.content.Intent.EXTRA_SUBJECT,"");
			           j.putExtra(android.content.Intent.EXTRA_TEXT,label);
			           startActivity(Intent.createChooser(j,"Share via"));
				}
				
				
			});
			Button delete=(Button) findViewById(R.id.delete);
			delete.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 DataBaseHandler db = new DataBaseHandler(getApplicationContext());  
			            db.deleteUserData(label);
						
							
				}
			});
           
	   
	    }  
	   
	   
	    public void onNothingSelected1(AdapterView<?> arg0) {  
	        // TODO Auto-generated method stub  
	   
	    
	      
	    /*public boolean onCreateOptionsMenu(Menu menu) {  
	        // Inflate the menu; this adds items to the action bar if it is present.  
	        getMenuInflater().inflate(R.menu.activity_sql_test, menu);  
	        return true;  
	    } 
	  
		

		
	

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.save, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	    }
	public static class PlaceholderFragment extends Fragment {

		/*public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_save, container,
					false);
			return rootView;
		}*/
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}
