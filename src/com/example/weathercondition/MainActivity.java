package com.example.weathercondition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	public ListView mCountry_List;
	public ArrayList<String> mArrayList;
    public String getCountryName;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Itemclick();
		initialze();
		getCountryList();
		SetAdapter();

	}

	private void Itemclick() {
		// TODO Auto-generated method stub
		mCountry_List = (ListView) findViewById(R.id.country_list);

	}

	public void getCountryList() {

		Locale[] locale = Locale.getAvailableLocales();
		mArrayList = new ArrayList<String>();
		String country;
		for (Locale loca : locale) {
			country = loca.getDisplayCountry();
			if (country.length() > 0 && !mArrayList.contains(country)) {
				mArrayList.add(country);
			}
		}
		Collections.sort(mArrayList, String.CASE_INSENSITIVE_ORDER);
	}

	public void SetAdapter() {

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_expandable_list_item_1, mArrayList);
		mCountry_List.setAdapter(adapter);
	}

	public void initialze() {
		mCountry_List.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				getCountryName=(String) (mCountry_List.getItemAtPosition(arg2));
				Toast.makeText(getApplicationContext(), "Selected Country Name"+getCountryName,
						Toast.LENGTH_SHORT).show();
				Intent intent=new Intent(MainActivity.this,WeatherReport.class);
				intent.putExtra("SELECTED", getCountryName);
				startActivity(intent);
			}
		});
	}

}
