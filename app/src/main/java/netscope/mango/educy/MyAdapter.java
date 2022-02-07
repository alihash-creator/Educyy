package netscope.mango.educy;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import netscope.mango.educy.newjobsportal.JobsAdapter;
import netscope.mango.educy.newjobsportal.MDPost;
import netscope.mango.educy.newmodels.MDNotes;

/**
 * Created by Sannan on 11/6/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    RecyclerView recyclerView;
    Context context;
    ArrayList<String> items = new ArrayList<>();
    ArrayList<String> urls = new ArrayList<>();
    JobsAdapter.OnClickListner listner;

    public void update(String name,String url){
        items.add(name);
        urls.add(url);
        notifyDataSetChanged();//refreshes the recycler view automatically
    }

    public MyAdapter(RecyclerView recyclerView, Context context, ArrayList<String> items, ArrayList<String> urls) {
        this.recyclerView = recyclerView;
        this.context = context;
        this.items = items;
        this.urls = urls;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//to create view for recycler view items
        View view = LayoutInflater.from(context).inflate(R.layout.items,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//        MDNotes mdNotes = items.get(position);
        //initialize the elements of indvi items...
        holder.nameOfFile.setText(items.get(position));

        holder.nameOfFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                listner.onClick(position, md);
                Toast.makeText(context, ""+ urls.get(position), Toast.LENGTH_SHORT).show();
                Uri webpage = Uri.parse(urls.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {//return the no of items
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameOfFile;

        public ViewHolder(View itemView) {//represent indvi list items...
            super(itemView);
            nameOfFile = (TextView)itemView.findViewById(R.id.nameOfFile);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = recyclerView.getChildLayoutPosition(view);
//                    Intent intent = new Intent();
//                    intent.setType(Intent.ACTION_VIEW);//denotes that we are going to view somethong
//                    intent.setData(Uri.parse(urls.get(position)));
//                    context.startActivity(intent);
//                }
//            });
        }
    }

    public interface OnClickListner{
        public void onClick(int position, MDPost mdPost);
    }

    public void setOnClickListener(JobsAdapter.OnClickListner listner){
        this.listner = listner;
    }
}

