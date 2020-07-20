package com.northpolewebservice.covid19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends ArrayAdapter<modelContries> {

    private Context context;
    private List<modelContries> modelContriesList;
    private List<modelContries> modelContriesListFiltered;

    public MyAdapter(Context context, List<modelContries> modelContriesList) {
        super(context,R.layout.listcustomitem,modelContriesList);

        this.context = context;
        this.modelContriesList = modelContriesList;
        this.modelContriesListFiltered = modelContriesList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listcustomitem,null,true);
        TextView tvCountryName = view.findViewById(R.id.tvCountryName);
        ImageView ivCountryFlag = view.findViewById(R.id.ivCountryFlag);

        tvCountryName.setText(modelContriesListFiltered.get(position).getCountry());

        Glide.with(context).load(modelContriesListFiltered.get(position).getFlag()).into(ivCountryFlag);

        return view;
    }

    @Override
    public int getCount() {
        return modelContriesListFiltered.size();
    }

    @Nullable
    @Override
    public modelContries getItem(int position) {
        return modelContriesListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = modelContriesList.size();
                    filterResults.values = modelContriesList;

                }else{
                    List<modelContries> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(modelContries itemsModel:modelContriesList){
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                modelContriesListFiltered = (List<modelContries>) results.values;
                CountriesAffected.modelContriesList = (List<modelContries>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}
