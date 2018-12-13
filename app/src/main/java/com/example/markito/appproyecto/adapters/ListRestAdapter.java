package com.example.markito.appproyecto.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.markito.appproyecto.R;
import com.example.markito.appproyecto.items.ItemListRest;

import java.util.ArrayList;

public class ListRestAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<ItemListRest> list;

    public ListRestAdapter(Context context, ArrayList<ItemListRest> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.item_buscar_rest, null);
        }
        TextView maneListRest = (TextView) convertView.findViewById(R.id.);*/
        return null;
    }
}
