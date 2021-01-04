package com.example.gfa_printing;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.gfa_printing.ServiceModel;

import java.util.ArrayList;

public class ServiceList extends BaseAdapter {
    private Activity context;
    ArrayList<ServiceModel> services;


    public ServiceList(Activity context, ArrayList countries) {
        //   super(context, R.layout.row_item, countries);
        this.context = context;
        this.services=countries;

    }

    public static class ViewHolder
    {
        TextView textViewId;
        TextView textViewService;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;

        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder vh;
        if(convertView==null) {
            vh=new ViewHolder();
            row = inflater.inflate(R.layout.row_item, null, true);
            vh.textViewId = (TextView) row.findViewById(R.id.textViewId);
            vh.textViewService = (TextView) row.findViewById(R.id.textViewService);
            // store the holder with the view.
            row.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.textViewService.setText(services.get(position).getServiceName());
        vh.textViewId.setText(""+services.get(position).getId());

        return  row;
    }

    public long getItemId(int position) {
        return position;
    }

    public Object getItem(int position) {
        return position;
    }

    public int getCount() {

        if(services.size()<=0)
            return 1;
        return services.size();
    }
}
