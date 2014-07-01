package br.com.epicdroid.travel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codeslap.persistence.Persistence;
import com.codeslap.persistence.SqlAdapter;

import java.util.List;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.adapter.NoteAdapter;
import br.com.epicdroid.travel.components.DialogCreateNote;
import br.com.epicdroid.travel.entity.Note;

public class NoteFragment extends Fragment {

    public static final int POSITION = 0;
    public static final String NAME_TAB = "notes";
    private View view;
    private ListView listViewNotes;
    private SqlAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note, container, false);
        setHasOptionsMenu(true);
        init();
        return view;
    }

    private void init() {
        adapter = Persistence.getAdapter(NoteFragment.this.getActivity());
        listViewNotes = (ListView) view.findViewById(R.id.note_list);
        setList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_note, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_new_note:
                new DialogCreateNote(NoteFragment.this.getActivity(), this).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setList(){
        listViewNotes.setAdapter(new NoteAdapter(NoteFragment.this.getActivity(), R.layout.item_note, adapter.findAll(Note.class)));
    }

}
