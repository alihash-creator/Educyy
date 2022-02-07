package netscope.mango.educy.newadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import netscope.mango.educy.R;
import netscope.mango.educy.newmodels.ModelShowTutor;

public class ShowTutorAdapter extends RecyclerView.Adapter<ShowTutorAdapter.ViewHolder>{

    List<ModelShowTutor> data;
    Context mContext;
    OnClickListner listner;

    public ShowTutorAdapter(Context mContext, List<ModelShowTutor> data)
    {
        this.data = data;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_tutor,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ModelShowTutor modelShowTutor = data.get(position);

        holder.name.setText(modelShowTutor.getName());
        holder.subject.setText(modelShowTutor.getSubject());
        holder.qualification.setText(modelShowTutor.getQualification());
        holder.location.setText(modelShowTutor.getLocation());
        if (modelShowTutor.getImage() != null){
            Picasso.with(holder.profile_image.getContext()).load(modelShowTutor.getImage()).into(holder.profile_image);
        }


        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onClick(position, modelShowTutor, 1);
            }
        });

        holder.msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onClick(position, modelShowTutor, 2);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView maincard;
        TextView name, subject, qualification, location;
        ImageView profile_image;
        Button call, msg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            maincard = itemView.findViewById(R.id.maincard);
            name = itemView.findViewById(R.id.name);
            qualification = itemView.findViewById(R.id.qualification);
            subject = itemView.findViewById(R.id.subject);
            location = itemView.findViewById(R.id.location);
            profile_image = itemView.findViewById(R.id.profile_image);
            call = itemView.findViewById(R.id.call);
            msg = itemView.findViewById(R.id.msg);
        }
    }

    public interface OnClickListner{
        public void onClick(int position, ModelShowTutor modelShowTutor, int type);
    }

    public void setOnClickListener(OnClickListner listner){
        this.listner = listner;
    }
}
