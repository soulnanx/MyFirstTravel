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

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.adapter.DebitAdapter;
import br.com.epicdroid.travel.adapter.NoteAdapter;
import br.com.epicdroid.travel.components.DialogCreateDebit;
import br.com.epicdroid.travel.components.DialogShowDebit;
import br.com.epicdroid.travel.components.DialogShowNote;
import br.com.epicdroid.travel.components.DialogUpdateNote;
import br.com.epicdroid.travel.entity.Debit;
import br.com.epicdroid.travel.entity.Note;

public class FinanceFragment extends Fragment {

    public static final int POSITION = 1;
    public static final String NAME_TAB = "finances";

    private UIHelper uiHelper;
    private View view;
    private SqlAdapter adapter;

    private Debit debitSelected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_finance, container, false);

        init();
        configureActionMode();
        return view;
    }

    private void init() {
        setHasOptionsMenu(true);

        uiHelper = new UIHelper(view);
        adapter = Persistence.getAdapter(FinanceFragment.this.getActivity());
        uiHelper.listViewDebits = (ListView) view.findViewById(R.id.debit_list);
        uiHelper.listViewDebits.setOnItemLongClickListener(eventOnLongClickDebit());
        uiHelper.listViewDebits.setOnItemClickListener(eventOnClickDebit());
        setList();
    }

    private AdapterView.OnItemClickListener eventOnClickDebit() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FinanceFragment.this.debitSelected = ((DebitAdapter.ItemHolder) view.getTag()).debit;
                new DialogShowDebit(FinanceFragment.this.getActivity(), debitSelected).show();
            }
        };
    }

    private AdapterView.OnItemLongClickListener eventOnLongClickDebit() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                FinanceFragment.this.debitSelected = ((DebitAdapter.ItemHolder) view.getTag()).debit;

                if(uiHelper.mMode!=null){
                    return false;
                } else {
                    uiHelper.mMode = getActivity().startActionMode(uiHelper.mCallback);
                }

                return true;
            }
        };
    }

    private void configureActionMode() {

        uiHelper.mCallback = new ActionMode.Callback() {
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                uiHelper.mMode = null;
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
                    case R.id.item_menu_delete:
                        deleteDebit();
                        mode.finish();
                        break;
                    case R.id.item_menu_edit:
//                        updateDebit();
                        mode.finish();
                        break;
                }
                return false;
            }
        };
    }

    private void deleteDebit() {
        adapter.delete(debitSelected);
        Toast.makeText(getActivity().getBaseContext(), debitSelected.getTitle() + " was deleted!", Toast.LENGTH_LONG).show();
        setList();
    }

    private void updateNote() {
//        new DialogUpdateNote(FinanceFragment.this.getActivity(), FinanceFragment.this, debitSelected).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_finance, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_new_debit:
                new DialogCreateDebit(FinanceFragment.this.getActivity(), FinanceFragment.this).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void setList(){
        uiHelper.listViewDebits.setAdapter(new DebitAdapter(FinanceFragment.this.getActivity(), R.layout.item_debit, adapter.findAll(Debit.class)));
    }
    private class UIHelper{
        ListView listViewDebits;
        ActionMode.Callback mCallback;
        ActionMode mMode;

        private UIHelper(View view) {
            this.listViewDebits = (ListView) view.findViewById(R.id.debit_list);
        }
    }


}
