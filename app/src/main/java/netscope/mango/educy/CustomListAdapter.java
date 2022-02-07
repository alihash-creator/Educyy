package netscope.mango.educy;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;


public class CustomListAdapter extends ArrayAdapter<newsgetset> {

    ArrayList<newsgetset> products;
    Context context;
    int resource;

    public CustomListAdapter(Context context, int resource, ArrayList<newsgetset> products) {
        super(context, resource, products);
        this.products = products;
        this.context = context;
        this.resource = resource;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.newslistview_item, null, true);

        }
        newsgetset product = getItem(position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.pic);
        Picasso.with(context).load(product.getImage()).into(imageView);

        TextView title = (TextView) convertView.findViewById(R.id.title);
        title.setText(product.getTitle());

        TextView desc = (TextView) convertView.findViewById(R.id.description);
        desc.setText(product.getDesc());
        TextView author = (TextView) convertView.findViewById(R.id.author);
        author.setText(product.getAuthor());
        TextView date = (TextView) convertView.findViewById(R.id.date);
        date.setText(product.getDate());

        return convertView;
    }
}