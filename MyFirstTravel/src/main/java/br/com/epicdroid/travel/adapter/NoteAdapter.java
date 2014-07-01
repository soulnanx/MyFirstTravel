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

    private int resource;

    public NoteAdapter(Context context, int resource, List<Note> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        Note note = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, null);
            itemHolder = new ItemHolder(convertView, note);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }

        if (note != null) {
            itemHolder.description.setText(note.getDescription());
            itemHolder.title.setText(note.getTitle());
            itemHolder.note = note;
        }

        return convertView;
    }


    public class ItemHolder{
        TextView title;
        TextView description;
        public Note note;

        ItemHolder(View view, Note note) {
            this.note = note;
            this.title = (TextView) view.findViewById(R.id.item_txt_title);
            this.description = (TextView) view.findViewById(R.id.item_txt_descripion);
        }
    }
}
