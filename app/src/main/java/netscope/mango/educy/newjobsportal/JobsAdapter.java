package netscope.mango.educy.newjobsportal;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import netscope.mango.educy.R;


public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {

    List<MDPost> data;
    Context mContext;
    OnClickListner listner;

    public JobsAdapter(List<MDPost> data, Context mContext)
    {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobs_card,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MDPost mdPost = data.get(position);

        holder.title.setText(mdPost.getTitle());
        holder.description.setText(mdPost.getDescription());
        if (mdPost.getImage() != null){
            Picasso.with(holder.logo.getContext()).load(mdPost.getImage()).into(holder.logo);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, description;
        ImageView logo;

        public ViewHolder(View itemView) {
            super(itemView);

            logo = itemView.findViewById(R.id.logo);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
        }
    }

    public interface OnClickListner{
        public void onClick(int position, MDPost mdPost);
    }

    public void setOnClickListener(OnClickListner listner){
        this.listner = listner;
    }
}
