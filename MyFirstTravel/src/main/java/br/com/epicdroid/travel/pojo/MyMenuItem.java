package br.com.epicdroid.travel.pojo;

import java.util.ArrayList;

import br.com.epicdroid.travel.R;

public class MyMenuItem {

    private int id;
    private int idStringResource;
    private int idDrawableResource;

    public MyMenuItem() {
    }

    private MyMenuItem(int id, int idStringResource, int idDrawableResource) {
        this.id = id;
        this.idStringResource = idStringResource;
        this.idDrawableResource = idDrawableResource;
    }

    public ArrayList<MyMenuItem> getItemsMenu(){
        ArrayList<MyMenuItem> list = new ArrayList<MyMenuItem>();
        list.add(new MyMenuItem(0, R.string.item_title_travel, android.R.drawable.ic_menu_myplaces));
        list.add(new MyMenuItem(1, R.string.item_title_notes, android.R.drawable.ic_menu_edit));
        list.add(new MyMenuItem(2, R.string.item_title_debits, android.R.drawable.ic_menu_add));
        list.add(new MyMenuItem(3, R.string.item_title_places, android.R.drawable.ic_menu_mapmode));
        list.add(new MyMenuItem(4, R.string.item_title_documents, android.R.drawable.ic_menu_gallery));

        return list;
    }

    public int getId() {
        return id;
    }

    public int getIdStringResource() {
        return idStringResource;
    }

    public int getIdDrawableResource() {
        return idDrawableResource;
    }
}
