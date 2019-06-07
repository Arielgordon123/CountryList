package com.ariel.countrylist.Adapters;

import android.util.Log;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.svgloader.SvgLoader;
import com.ariel.countrylist.Handlers.Country;
import com.ariel.countrylist.R;


import java.util.ArrayList;


public class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.MyViewHolder> {
        private ArrayList<Country> mDataset;
        private Activity activity;

    private ArrayList<Country> countryListFiltered;
    private ContactsAdapterListener listener;
        View.OnClickListener mOnItemClickListener;

//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                String charString = charSequence.toString();
//
//                if (charString.isEmpty()) {
//                    countryListFiltered = mDataset;
//                } else {
//                    ArrayList<Country> filteredList = new ArrayList<>();
//                    for (Country country : mDataset) {
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//                        if (country.getName().toLowerCase().contains(charString.toLowerCase())) {
//                            Log.i("TEST ", country.getName());
//                            filteredList.add(country);
//                        }
//                    }
//
//                    countryListFiltered = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = countryListFiltered;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                countryListFiltered = (ArrayList<Country>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }

    public interface ContactsAdapterListener {
        void onCountrySelected(Country country);
    }

    // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder

        public class MyViewHolder extends RecyclerView.ViewHolder  {

            // each data item is just a string in this case
            public TextView CountryView;
            public TextView nativeName;
            public ImageView imageFlag;
            public MyViewHolder(@NonNull View itemView, int viewType) {
                super(itemView);
                itemView.setTag(this);
                CountryView = itemView.findViewById(R.id.countryName);
                nativeName = itemView.findViewById(R.id.nativeName);
                imageFlag = itemView.findViewById(R.id.flagImage);
                itemView.setOnClickListener(mOnItemClickListener);
            }

        }
        public void setOnItemClickListener(View.OnClickListener itemClickListener) {
            mOnItemClickListener = itemClickListener;
        }
        // Provide a suitable constructor (depends on the kind of dataset)
        public MyRecycleViewAdapter(ArrayList<Country> myDataset, Activity activity) {

            mDataset = myDataset;
            this.activity = activity;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyRecycleViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            // create a new view
            View v = (View) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mylistitem, parent, false);

           // v.setOnClickListener(mOnClickListener);
            MyViewHolder vh = new MyViewHolder(v,viewType);

            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element

            holder.CountryView.setText(mDataset.get(position).getName());
            holder.nativeName.setText(mDataset.get(position).getNativeName());
//            Picasso.get().load(mDataset.get(position).getFlag()).into(holder.imageFlag);
            SvgLoader.pluck()
                    .with(activity)
                    .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                    .load(mDataset.get(position).getFlag(), holder.imageFlag);

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }


//        @Override
//        public void onClick(View v) {
//
//            Toast.makeText(activity,"test",Toast.LENGTH_LONG).show();
//        }
    }