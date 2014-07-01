package br.com.epicdroid.travel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
    private ActionMode.Callback mCallback;
    private ActionMode mMode;
    private Note noteSelected;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_note, container, false);
        setHasOptionsMenu(true);
        init();
        configureActionMode();
        return view;
    }

    private void configureActionMode() {

        mCallback = new ActionMode.Callback() {
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                mMode = null;
            }


            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.setTitle("1 selected");
                getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                switch(item.getItemId()){
                    case R.id.item_menu_note_delete:
                        deleteNote();
                        mode.finish();
                        break;
                    case R.id.item_menu_note_edit:
                        Toast.makeText(getActivity().getBaseContext(), "Selected Action2 ", Toast.LENGTH_LONG).show();
                        break;
                }
                return false;
            }
        };
    }

    private void deleteNote() {
        adapter.delete(noteSelected);
        Toast.makeText(getActivity().getBaseContext(), noteSelected.getTitle() + " was deleted!", Toast.LENGTH_LONG).show();
        setList();
    }

    private void init() {
        adapter = Persistence.getAdapter(NoteFragment.this.getActivity());
        listViewNotes = (ListView) view.findViewById(R.id.note_list);
        listViewNotes.setOnItemLongClickListener(eventOnLongClickNote());
        setList();
    }

    private AdapterView.OnItemLongClickListener eventOnLongClickNote() {
        return new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                NoteFragment.this.noteSelected = ((NoteAdapter.ItemHolder) view.getTag()).note;

                if(mMode!=null){
                    return false;
                } else {
                    mMode = getActivity().startActionMode(mCallback);
                }

                return true;
            }
        };
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
