package com.ariel.countrylist.Activities;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ariel.countrylist.Fragments.first_fragment;
import com.ariel.countrylist.R;

public class MainActivity extends AppCompatActivity implements first_fragment.OnFirstFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create new fragment and transaction
        Fragment newFragment = new first_fragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack if needed
        transaction.replace(R.id.fragment_container, newFragment);
        //transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

        // move to first fragment
        //getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new first_fragment()).commit();
    }

    @Override
    public void OnFirstFragmentInteractionListener(Uri uri) {

    }
}
