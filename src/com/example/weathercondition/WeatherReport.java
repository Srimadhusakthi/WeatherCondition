package com.example.weathercondition;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.jar.JarException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.TextView;

public class WeatherReport extends Activity {
public TextView mCountryName,mTemperature,mDateTime,mPressure,mchill;
public SimpleDateFormat mSimpleDateFormat;
public ProgressDialog progressDialog;
public String pressure,chill,temperature;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_report);
	    intitialize();
	    setCountryName();
	    setCurrentDatetiem();

	    new WeatherReportList().execute("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22nome%2C%20ak%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
	    
	}

	private void intitialize() {
		// TODO Auto-generated method stub
		mCountryName=(TextView) findViewById(R.id.country_name);
		mTemperature=(TextView) findViewById(R.id.temperature);
		mDateTime=(TextView) findViewById(R.id.date_time);
	    mPressure=(TextView) findViewById(R.id.pressure);
	    mchill=(TextView) findViewById(R.id.chill);
	}
	private void setCountryName(){
		String string=getIntent().getStringExtra("SELECTED");
	    mCountryName.setText(string);
	    
	}
	private void setCurrentDatetiem(){
		mSimpleDateFormat=new SimpleDateFormat("yyyy/MM/dd hh:MM");
		Calendar calendar=Calendar.getInstance();
		String date_time=mSimpleDateFormat.format(calendar.getTime());
		mDateTime.setText(date_time);
	}


class WeatherReportList extends AsyncTask<String, Void,Boolean>{

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	    progressDialog=new ProgressDialog(WeatherReport.this);
	    progressDialog.setMessage("Loading");
	    progressDialog.show();
	}
	@Override
	protected Boolean doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		try{
			HttpGet get=new HttpGet("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22nome%2C%20ak%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
			HttpClient httpClient=new DefaultHttpClient();
			HttpResponse httpResponse=httpClient.execute(get);
			int status=httpResponse.getStatusLine().getStatusCode();
			if(status==200){
				HttpEntity httpEntity=httpResponse.getEntity();
				String data=EntityUtils.toString(httpEntity);
				JSONObject jsonObject=new JSONObject(data);
				JSONObject datas=jsonObject.getJSONObject("query");
				 JSONObject query=datas.getJSONObject("results");
				 JSONObject results=query.getJSONObject("channel");
				 JSONObject channel=results.getJSONObject("atmosphere");
				 JSONObject units=results.getJSONObject("units");
				 JSONObject wind=results.getJSONObject("wind");
				  pressure=channel.getString("pressure");
				  temperature=units.getString("temperature");
				  chill=wind.getString("chill");
				  
				
				 return true;
				
			}}
			catch(IOException exception){
				exception.printStackTrace();
			}catch(JSONException exception){
				exception.printStackTrace();
			}catch(ParseException exception){
				exception.printStackTrace();
			}
			return false;
		}
		
	
	
@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		 mPressure.setText(pressure);
		 mTemperature.setText(temperature);
		 mchill.setText(chill);

			progressDialog.dismiss();
}

}






}
