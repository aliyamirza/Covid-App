package com.example.hp.covidapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 17-11-2020.
 */
public class Adapter  extends ArrayAdapter<Model> {

    private Context context;
    private List<Model> modelList;
    private List<Model> modelListFiltered;


    public Adapter(Context context, List<Model> modelList) {
        super(context, R.layout.list, modelList);

        this.context = context;
        this.modelList = modelList;
        this.modelListFiltered = modelList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, null, true);
        TextView tvCountry = (TextView) view.findViewById(R.id.tvCountry);
        ImageView imgFlag = (ImageView) view.findViewById(R.id.imgFlag);

        tvCountry.setText(modelListFiltered.get(position).getCountry());
        Glide.with(context).load(modelListFiltered.get(position).getFlag()).into(imgFlag);

        return view;

    }

    @Override
    public Model getItem(int position)
    {
        return modelListFiltered.get(position);
    }

    @Override
    public int getCount() {

        return modelListFiltered.size();

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
                if (constraint == null || constraint.length() == 0) {
                    filterResults.count = modelList.size();
                    filterResults.values = modelList;

                } else {
                    List<Model> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for (Model itemsModel : modelList) {
                        if (itemsModel.getCountry().toLowerCase().contains(searchStr)) {
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

                modelListFiltered = (List<Model>) results.values;
                AffectedCountries.modelList = (List<Model>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}