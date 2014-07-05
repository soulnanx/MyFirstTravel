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
import android.widget.ListView;

import com.codeslap.persistence.SqlAdapter;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.components.DialogCreateNote;
import br.com.epicdroid.travel.entity.Note;

public class FinanceFragment extends Fragment {

    public static final int POSITION = 1;
    public static final String NAME_TAB = "finances";

    private ListView listViewNotes;
    private SqlAdapter adapter;
    private ActionMode.Callback mCallback;
    private ActionMode mMode;
    private Note noteSelected;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finance, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_finance, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_new_note:
//                new DialogCreateNote(FinanceFragment.this.getActivity()).show();

        }

        return super.onOptionsItemSelected(item);
    }

    public void setList(){
//        listViewNotes.setAdapter(new NoteAdapter(NoteFragment.this.getActivity(), R.layout.item_note, adapter.findAll(Note.class)));
    }


}
