package com.ariel.countrylist.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.ariel.countrylist.Adapters.MyRecycleViewAdapter;
import com.ariel.countrylist.Handlers.Country;
import com.ariel.countrylist.Handlers.HttpHandler;
import com.ariel.countrylist.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link first_fragment.OnFirstFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link first_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class first_fragment extends Fragment {
    View curview;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private MyRecycleViewAdapter myAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SearchView searchView;
    private OnFirstFragmentInteractionListener mListener;
    ArrayList<Country> countryList;
    public first_fragment() {
        // Required empty public constructor
    }
    public void setPageTitle(String title) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(Html.fromHtml("<font color='#ffffff'>"+title+ "</font>"));
    }
    /**
     *
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment first_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static first_fragment newInstance(String param1, String param2) {
        first_fragment fragment = new first_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //TODO: Step 4 of 4: Finally call getTag() on the view.
            // This viewHolder will have all required values.
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            Country thisItem = countryList.get(position);
            ArrayList<String> borders = thisItem.getBorders();
            new getCountryByCode((Activity) curview.getContext(),thisItem, borders).execute();
            // viewHolder.getItemId();
            // viewHolder.getItemViewType();
            // viewHolder.itemView;

            Toast.makeText(getContext(), "borders: " +borders.size() , Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        curview = inflater.inflate(R.layout.first_fragment, container, false);
        countryList = new ArrayList<>();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Button getAllCountries = curview.findViewById(R.id.getAllCountries);

        getAllCountries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getAllCountries((Activity) curview.getContext()).execute();
            }
        });
        recyclerView = curview.findViewById(R.id.MyRecycleView);

        myAdapter = new MyRecycleViewAdapter(countryList, (Activity) curview.getContext());

        recyclerView.setLayoutManager(new LinearLayoutManager(curview.getContext()));

        myAdapter.setOnItemClickListener(onItemClickListener);
        //searchCountry = curview.findViewById(R.id.searchCountry);


        getAllCountries countriesApi = new getAllCountries((Activity) curview.getContext());

        countriesApi.execute();
        // Inflate the layout for this fragment
        return curview;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.OnFirstFragmentInteractionListener(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFirstFragmentInteractionListener) {
            mListener = (OnFirstFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFirstFragmentInteractionListener {
        // TODO: Update argument type and name
        void OnFirstFragmentInteractionListener(Uri uri);
    }


    public class getAllCountries  extends AsyncTask<Void, Void, Void> {
        private String TAG = "CountriesApi";
        String baseUrl = "https://restcountries.eu/rest/v2/all?fields=name;nativeName;borders;flag;";
        private Activity myActivity;




        public getAllCountries( Activity activity) {
            myActivity = activity;
            setPageTitle("Country List");
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(MainActivity.this,"Json Data is downloading", Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response

            String jsonStr = sh.makeServiceCall(baseUrl);

           // Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONArray jsonObj = new JSONArray(jsonStr);

                    // in case the list isnt empty
                    countryList.clear();
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
                        //Log.e(TAG,"name is: "+ name+ " borders num is: "+ borders.size());
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
            recyclerView.setAdapter(myAdapter);
        }

    }

    public class getCountryByCode  extends AsyncTask<Void, Void, Void> {
        private String TAG = "CountriesApi";
        private String baseUrl = "https://restcountries.eu/rest/v2/alpha/";
        private String fields = "?fields=name;nativeName;borders;flag;";
        private Activity myActivity;
        private Country myCountry;
        private ArrayList<String> borders;
        private ArrayList<Country> countriesArr;



        public getCountryByCode( Activity activity,Country country, ArrayList<String> _borders) {
            myActivity = activity;
            borders = _borders;
            countriesArr = new ArrayList<>();
            myCountry = country;
            setPageTitle(country.getName());
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Toast.makeText(MainActivity.this,"Json Data is downloading", Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response

            if(borders.size() > 0) {
                for (String border : borders) {
                    String jsonStr = sh.makeServiceCall(baseUrl+ border+fields);

                   // Log.e(TAG, "Response from url: " + jsonStr);
                    if (jsonStr != null) {
                        try {

                            JSONObject obj = new JSONObject(jsonStr);

                            String name = obj.getString("name");


                            String nativeName = obj.getString("nativeName");
                            String flag = obj.getString("flag");
                            JSONArray bordersJson = obj.getJSONArray("borders");
                            String[] atata = new String[bordersJson.length()];
                            ArrayList<String> borders = new ArrayList<>();
                            for (int j = 0; j < bordersJson.length(); j++) {
                                borders.add(bordersJson.getString(j));
                            }
                           // Log.e(TAG, "name is: " + name + " borders num is: " + borders.size());
                            // tmp hash map for single contact
                            Country country = new Country(name, nativeName, flag, borders);

                            // adding contact to contact list
                            countriesArr.add(country);


                        } catch (final JSONException e) {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                            myActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(myActivity.getApplicationContext(),
                                            "Json parsing error: " + e.getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                        }

                    } else {
                        Log.e(TAG, "Couldn't get json from server.");
                        myActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(myActivity.getApplicationContext(),
                                        "Couldn't get json from server. Check LogCat for possible errors!",
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }


                    countryList.clear();
                    countryList.addAll(countriesArr);
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            recyclerView.setAdapter(myAdapter);
        }

    }

}
