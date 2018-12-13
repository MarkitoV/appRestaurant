package com.example.markito.appproyecto.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.markito.appproyecto.R;
import com.example.markito.appproyecto.items.ItemMenu;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder>{
    private Context context;
    private ArrayList<ItemMenu> listData;

    public MenuAdapter(Context context, ArrayList<ItemMenu> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, final int position) {
        holder.parentLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        private TextView         idrestaurant;
        private TextView         name;
        private TextView         price;
        private ImageView        picture;
        private ConstraintLayout parentLayout;
        public MenuViewHolder(View itemView) {
            super(itemView);
            name         = itemView.findViewById(R.id.textNombreMe);
            price        = itemView.findViewById(R.id.textPrecioMe);
            picture      = itemView.findViewById(R.id.image_view_Me);
            parentLayout = itemView.findViewById(R.id.parent_layaut);
        }
        public void setData(ItemMenu itemMenu) {
            name.setText(itemMenu.getName());
            price.setText(itemMenu.getPrice().toString());
        }

    }
}
