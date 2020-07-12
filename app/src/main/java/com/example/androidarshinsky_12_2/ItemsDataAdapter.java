package com.example.androidarshinsky_12_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ItemsDataAdapter extends BaseAdapter {

    private List<DataItem> items;
    private LayoutInflater inflater;
    private DeleteClickListener listener;


    public ItemsDataAdapter(Context context, List<DataItem> items, DeleteClickListener listener) {

        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = items;
        }
        this.listener = listener;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(DataItem item) {
        this.items.add(item);
        notifyDataSetChanged();
    }

    // Удаляет элемент списка.
    public void removeItem(int position) {
        items.remove(position);
        notifyDataSetChanged();
        listener.onDeleteClicked();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public DataItem getItem(int position) {
        if (position < items.size()) {
            return items.get(position);
        } else {
            return null;
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_layout, parent, false);
        }

        DataItem itemData = items.get(position);

        ImageView image = view.findViewById(R.id.icon);
        TextView title = view.findViewById(R.id.title);
        TextView subtitle = view.findViewById(R.id.subtitle);

        ImageView imgdel = (ImageView)view.findViewById(R.id.imgdel);

        image.setImageDrawable(itemData.getImage());


        title.setText(itemData.getTitle());
        subtitle.setText(itemData.getSubtitle());

        imgdel.setImageDrawable(itemData.getImagClick());
        imgdel.setClickable(true);
        imgdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemsDataAdapter.this.removeItem(position);

            }
        });

        return view;
    }

}
