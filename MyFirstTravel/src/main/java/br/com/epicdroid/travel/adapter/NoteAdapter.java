package br.com.epicdroid.travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.entity.Note;

public class NoteAdapter extends ArrayAdapter<Note> {

    public NoteAdapter(Context context, int resource, List<Note> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_note, null);

        }

        Note p = getItem(position);

        if (p != null) {

            ItemHolder i = new ItemHolder(v);
                i.description.setText(p.getDescription());
                i.title.setText(p.getTitle());
            }

        return v;
    }


    class ItemHolder{
        TextView title;
        TextView description;

        ItemHolder(View view) {
            this.title = (TextView) view.findViewById(R.id.item_txt_title);
            this.description = (TextView) view.findViewById(R.id.item_txt_descripion);
        }
    }
}
