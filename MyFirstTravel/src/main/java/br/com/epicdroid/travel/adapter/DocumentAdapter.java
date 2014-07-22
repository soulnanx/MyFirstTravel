package br.com.epicdroid.travel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.math.BigDecimal;
import java.util.List;

import br.com.epicdroid.travel.R;
import br.com.epicdroid.travel.entity.Document;
import br.com.epicdroid.travel.utils.TextFormatUtils;

/**
 * Created by call on 7/22/14.
 */
public class DocumentAdapter extends ArrayAdapter<Document> {

    private int resource;

    public DocumentAdapter(Context context, int resource, List<Document> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemHolder itemHolder;
        Document document = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, null);
            itemHolder = new ItemHolder(convertView, document);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }

        if (document != null) {
            itemHolder.description.setText(document.getDescription().toString());
            itemHolder.title.setText(document.getTitle());
            UrlImageViewHelper.setUrlDrawable(itemHolder.image, document.getImagePath());

            itemHolder.document = document;
        }

        return convertView;
    }

    public class ItemHolder {
        TextView title;
        TextView description;
        ImageView image;
        public Document document;

        ItemHolder(View view, Document document) {
            this.document = document;
            this.title = (TextView) view.findViewById(R.id.item_txt_title);
            this.description = (TextView) view.findViewById(R.id.item_txt_description);
            this.image = (ImageView) view.findViewById(R.id.item_img);
        }
    }
}
