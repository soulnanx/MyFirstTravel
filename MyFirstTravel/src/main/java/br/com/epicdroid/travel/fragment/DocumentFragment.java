package br.com.epicdroid.travel.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.adapter.DocumentAdapter;
import br.com.epicdroid.travel.application.app;
import br.com.epicdroid.travel.dialogs.DialogCreateDocument;
import br.com.epicdroid.travel.dialogs.DialogCreatePlace;
import br.com.epicdroid.travel.entity.Document;
import br.com.epicdroid.travel.entity.Place;

public class DocumentFragment extends Fragment {

    public static final int POSITION = 4;
    public static final String NAME_TAB = "documents";
    private View view;
    private ListView listViewDocs;
    private ActionMode.Callback mCallback;
    private ActionMode mMode;
    private Document docSelected;
    private app application;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_document, container, false);

        init();
        configureActionMode();
        return view;
    }

    private void init() {
        setHasOptionsMenu(true);
        application = (app) this.getActivity().getApplication();
        listViewDocs = (ListView) view.findViewById(R.id.document_list);
        listViewDocs.setOnItemLongClickListener(eventOnLongClickNote());
        listViewDocs.setOnItemClickListener(eventOnClickNote());
        setList();
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

                switch (item.getItemId()) {
                    case R.id.item_menu_delete:
                        deleteNote();
                        mode.finish();
                        break;
                    case R.id.item_menu_edit:
                        updateNote();
                        mode.finish();
                        break;
                }
                return false;
            }
        };
    }

    private void deleteNote() {
//        adapter.delete(docSelected);
        Toast.makeText(getActivity().getBaseContext(), docSelected.getTitle() + " was deleted!", Toast.LENGTH_LONG).show();
        setList();
    }

    private void updateNote() {
//        new DialogUpdateNote(DocumentFragment.this.getActivity(), DocumentFragment.this, docSelected).show();
    }

    private AdapterView.OnItemClickListener eventOnClickNote() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DocumentFragment.this.docSelected = ((DocumentAdapter.ItemHolder) view.getTag()).document;
//                new DialogShowNote(DocumentFragment.this.getActivity(), docSelected).show();
            }
        };
    }


    private AdapterView.OnItemLongClickListener eventOnLongClickNote() {
        return new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                DocumentFragment.this.docSelected = ((DocumentAdapter.ItemHolder) view.getTag()).document;

                if (mMode != null) {
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
        switch (item.getItemId()) {
            case R.id.item_new_note:
                showDialogCreatePlace();
        }

        return super.onOptionsItemSelected(item);
    }

    private void showDialogCreatePlace() {
        FragmentManager fm = getFragmentManager();
        DialogCreateDocument dialogCreateDocument = new DialogCreateDocument();
//        Bundle b = new Bundle();
//        b.putSerializable(BUNDLE_NEW_PLACE, newPlace);
//        dialogCreatePlace.setArguments(b);
        dialogCreateDocument.setRetainInstance(true);
        dialogCreateDocument.show(fm, "fragment_name");
    }

    public void setList() {
//        listViewDocs.setAdapter(new DocumentAdapter(DocumentFragment.this.getActivity(), R.layout.item_document, adapter.findAll(Document.class)));
    }

}
