package br.com.epicdroid.travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.epicdroid.travel.MyMenuItem;
import br.com.epicdroid.travel.R;

public class MenuAdapter extends ArrayAdapter<MyMenuItem> {

    private int resource;

    public MenuAdapter(Context context, int resource, List<MyMenuItem> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        MyMenuItem itemMenu = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, null);
            itemHolder = new ItemHolder(convertView, itemMenu);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }

        if (itemMenu != null) {
            itemHolder.name.setText(itemMenu.getIdStringResource());
            itemHolder.image.setImageResource(itemMenu.getIdDrawableResource());
            itemHolder.itemMenu = itemMenu;
        }

        return convertView;
    }

    public class ItemHolder {
        TextView name;
        ImageView image;
        public MyMenuItem itemMenu;

        ItemHolder(View view, MyMenuItem itemMenu) {
            this.itemMenu = itemMenu;
            this.name = (TextView) view.findViewById(R.id.item_txt_name);
            this.image = (ImageView) view.findViewById(R.id.item_img);
        }
    }
}
