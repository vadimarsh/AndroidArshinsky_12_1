package com.example.androidarshinsky_12_2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ItemsDataAdapter extends BaseAdapter {

    private List<DataItem> items;
    private LayoutInflater inflater;
    private FloatingActionButton buttonadd;


    public ItemsDataAdapter(Context context, List<DataItem> items) {

        if (items == null) {
            this.items = new ArrayList<>();
        } else {
            this.items = items;
        }

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

    // Самый интересный обязательный метод.
    // Создает или возвращает переиспользуемый View, с новыми данными
    // для конкретной позиции. BaseAdapter – хитрый класс,
    // он не держит в памяти все View - это дорого и будет тормозить.
    // Поэтому он рисует только то что видно. Для этого есть convertView.
    // Если нет чего переиспользовать, то создается новый View.
    // А потом напоняет старую или новую View нужными данными.
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
        buttonadd = view.findViewById(R.id.fab);
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
                MainActivity context = (MainActivity) v.getContext();
                context.callToSave();
            }
        });

        return view;
    }

}
