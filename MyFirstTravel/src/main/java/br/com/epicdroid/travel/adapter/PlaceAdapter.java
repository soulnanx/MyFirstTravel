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
            itemHolder.address.setText(place.getAddress());
            itemHolder.title.setText(place.getTitle());
            itemHolder.km.setText("50");
            itemHolder.place = place;
        }

        return convertView;
    }

    public class ItemHolder {
        TextView title;
        TextView km;
        TextView address;
        public Place place;

        ItemHolder(View view, Place place) {
            this.place = place;
            this.title = (TextView) view.findViewById(R.id.item_txt_title);
            this.km = (TextView) view.findViewById(R.id.item_txt_km);
            this.address = (TextView) view.findViewById(R.id.item_txt_address);
        }
    }
}
