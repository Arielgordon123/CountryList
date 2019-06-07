package com.ariel.countrylist.Handlers;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class CountriesApi  extends AsyncTask<Void, Void, Void> {
        private String TAG = "CountriesApi";
        String baseUrl = "https://restcountries.eu/rest/v2/all?fields=name;nativeName;borders;flag;";
        private Activity myActivity;
        ArrayList<Country> countryList = new ArrayList<>();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // Toast.makeText(MainActivity.this,"Json Data is downloading", Toast.LENGTH_LONG).show();

        }

    public CountriesApi( Activity activity) {
        myActivity = activity;
    }

    @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response

            String jsonStr = sh.makeServiceCall(baseUrl);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray jsonObj = new JSONArray(jsonStr);

                    // Getting JSON Array node

                    // looping through All Contacts
                    for (int i = 0; i < jsonObj.length(); i++) {
                        JSONObject obj = jsonObj.getJSONObject(i);
                        String name = obj.getString("name");

                        String nativeName = obj.getString("nativeName");
                        String flag = obj.getString("flag");
                        JSONArray bordersJson = obj.getJSONArray("borders");
                        String[] atata = new String[bordersJson.length()];
                        ArrayList<String> borders = new ArrayList<>();
                        for (int j=0; j < bordersJson.length(); j++) {
                            borders.add(bordersJson.getString(j));
                        }
                        Log.e(TAG,"name is: "+ name+ " borders num is: "+ borders.size());
                        // tmp hash map for single contact
                        Country country = new Country(name,nativeName,flag,borders);

                        // adding contact to contact list
                        countryList.add(country);
                    }

                    Log.e(TAG,"Size is: "+ countryList.size());
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    myActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(myActivity.getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
//            ListAdapter adapter = new SimpleAdapter(MainActivity.this, countryList,
//                    R.layout.list_item, new String[]{ "email","mobile"},
//                    new int[]{R.id.email, R.id.mobile});
//            lv.setAdapter(adapter);
        }

}
