package br.com.epicdroid.travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.entity.Debit;
import br.com.epicdroid.travel.utils.TextFormatUtils;

public class DebitAdapter extends ArrayAdapter<Debit> {

    private int resource;

    public DebitAdapter(Context context, int resource, List<Debit> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        Debit debit = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, null);
            itemHolder = new ItemHolder(convertView, debit);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }

        if (debit != null) {
            itemHolder.value.setText(TextFormatUtils.showAsMoney(new BigDecimal(debit.getValue().toString())));
            itemHolder.title.setText(debit.getTitle());
            itemHolder.debit = debit;
        }

        return convertView;
    }

    public class ItemHolder {
        TextView title;
        TextView value;
        public Debit debit;

        ItemHolder(View view, Debit debit) {
            this.debit = debit;
            this.title = (TextView) view.findViewById(R.id.item_txt_title);
            this.value = (TextView) view.findViewById(R.id.item_txt_value);
        }
    }
}
