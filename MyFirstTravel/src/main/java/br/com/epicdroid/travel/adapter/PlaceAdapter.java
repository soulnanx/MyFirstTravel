package br.com.epicdroid.travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.entity.Place;

public class PlaceAdapter extends ArrayAdapter<Place> {

    private int resource;

    public PlaceAdapter(Context context, int resource, List<Place> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        Place place = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, null);
            itemHolder = new ItemHolder(convertView, place);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }

        if (place != null) {
            itemHolder.value.setText("$" + place.getValue().toString());
            itemHolder.title.setText(place.getTitle());
            itemHolder.place = place;
        }

        return convertView;
    }

    public class ItemHolder {
        TextView title;
        TextView value;
        public Place place;

        ItemHolder(View view, Place place) {
            this.place = place;
            this.title = (TextView) view.findViewById(R.id.item_txt_title);
            this.value = (TextView) view.findViewById(R.id.item_txt_value);
        }
    }
}
