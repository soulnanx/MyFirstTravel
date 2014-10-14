package br.com.epicdroid.travel.pojo;

import java.util.ArrayList;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.fragment.CreateTravelFragment;
import br.com.epicdroid.travel.fragment.DocumentFragment;
import br.com.epicdroid.travel.fragment.FinanceFragment;
import br.com.epicdroid.travel.fragment.NoteFragment;
import br.com.epicdroid.travel.fragment.PlaceFragment;
import br.com.epicdroid.travel.fragment.TravelFragment;

public class MyMenuItem {

    private String fragmentName;
    private int idStringResource;
    private int idDrawableResource;

    public MyMenuItem() {
    }

    private MyMenuItem(String fragmentName, int idStringResource, int idDrawableResource) {
        this.fragmentName = fragmentName;
        this.idStringResource = idStringResource;
        this.idDrawableResource = idDrawableResource;
    }

    public ArrayList<MyMenuItem> getItemsMenu(){
        ArrayList<MyMenuItem> list = new ArrayList<MyMenuItem>();
        list.add(new MyMenuItem(TravelFragment.class.getName(), R.string.item_title_travel, android.R.drawable.ic_menu_myplaces));
        list.add(new MyMenuItem(NoteFragment.class.getName(), R.string.item_title_notes, android.R.drawable.ic_menu_edit));
        list.add(new MyMenuItem(FinanceFragment.class.getName(), R.string.item_title_debits, android.R.drawable.ic_menu_add));
        list.add(new MyMenuItem(PlaceFragment.class.getName(), R.string.item_title_places, android.R.drawable.ic_menu_mapmode));
        list.add(new MyMenuItem(DocumentFragment.class.getName(), R.string.item_title_documents, android.R.drawable.ic_menu_gallery));

        return list;
    }

    public String getFragmentName() {
        return fragmentName;
    }

    public int getIdStringResource() {
        return idStringResource;
    }

    public int getIdDrawableResource() {
        return idDrawableResource;
    }
}
