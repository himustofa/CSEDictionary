package com.kamaldictionary;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	final static String SHARED_NAME_STRING="sharedp";
	final static String USER_NAME_STRING="user";        //age log in korle ta save thakbe ei khane
	
	Button button;
	EditText editText;
	SharedPreferences sharedPreferences;                //user age log in koresilo naki ta suggest korbe
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText=(EditText) findViewById(R.id.userNameEditText);
		button=(Button) findViewById(R.id.enterButton);
		
		Log.d("DICTIONARY", "main activity started");

        //SharePreference holo user log in kore thakle ta save thake logout kora sotteo.
		sharedPreferences=getSharedPreferences(SHARED_NAME_STRING, MODE_PRIVATE);       //MODE_PRIVATE mane amder application diye read write korte parbe
		String userNameString=sharedPreferences.getString(USER_NAME_STRING, "");        //age log in korse naki? "" = at first kicu nai log in kora

        //save userNameString assign into edittext
		editText.setText(userNameString);

        //Click then goto next activity (DictionaryListActivity)
		button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String string = editText.getText().toString();
                Intent intent = new Intent(MainActivity.this, DictionaryListActivity.class);
                intent.putExtra("user", string);

                //onClick save int sharedPreferences. R Editor use kora mane next time login name change kora jabe
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(USER_NAME_STRING, string);
                //Jodi arow kicu like password, phone number, extra
                //editor.putString(USER_NAME_STRING, string);
                //editor.putString(USER_NAME_STRING, string);
                editor.commit();

                startActivity(intent);
            }
        });
/*
		editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String string = editText.getText().toString();
                Intent intent = new Intent(MainActivity.this, DictionaryListActivity.class);
                intent.putExtra("user", string);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(USER_NAME_STRING, string);
                editor.commit();

                startActivity(intent);

                return true;
            }
        });
*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
