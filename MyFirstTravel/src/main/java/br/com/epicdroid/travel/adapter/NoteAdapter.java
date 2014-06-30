package br.com.epicdroid.travel.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import br.com.epicdroid.travel.entity.Note;

public class NoteAdapter extends ArrayAdapter<Note> {

    private ArrayList<Note> notes;
    private Context context;
    private int idViewResource;

    public NoteAdapter(Context context, int textViewResourceId, ArrayList<Note> notes) {
        super(context, textViewResourceId, notes);
        this.context = context;
        this.notes = notes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }


    class ItemHolder{


    }
}
