package netscope.mango.educy;

        import android.content.Context;
        import androidx.core.content.ContextCompat;
        import androidx.recyclerview.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import java.util.ArrayList;


public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ItemViewHolder> {
    private static ArrayList<CatModel> dataList;
    private LayoutInflater mInflater;
    private Context context;
    private CatListner clicklistener = null;

    public CatAdapter(Context ctx, ArrayList<CatModel> data) {
        context = ctx;
        dataList = data;
        mInflater = LayoutInflater.from(context);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        private Button button1, button2, button3;

        public ItemViewHolder(View itemView) {
            super(itemView);

            button1 = (Button) itemView.findViewById(R.id.button1);
            button2 = (Button) itemView.findViewById(R.id.button2);
            button3 = (Button) itemView.findViewById(R.id.button3);
        }
    }

    public void setClickListener(CatListner listener) {
        this.clicklistener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    private static final String TAG = "Music10";

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        if(dataList.get(position).getTitle1() == null){
            holder.button1.setVisibility(View.GONE);
        }else{
            holder.button1.setVisibility(View.VISIBLE);
            holder.button1.setText(dataList.get(position).getTitle1());
            holder.button1.setActivated(!dataList.get(position).isButton1Selected());
            setButton1(holder);
            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setButton1(holder);
                }
            });
        }
        if(dataList.get(position).getTitle2() == null){
            holder.button2.setVisibility(View.GONE);
        }else{
            holder.button2.setVisibility(View.VISIBLE);
            holder.button2.setText(dataList.get(position).getTitle2());
            holder.button2.setActivated(!dataList.get(position).isButton2Selected());
            setButton2(holder);
            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setButton2(holder);
                }
            });
        }
        if(dataList.get(position).getTitle3() == null){
            holder.button3.setVisibility(View.GONE);
        }else{
            holder.button3.setVisibility(View.VISIBLE);
            holder.button3.setText(dataList.get(position).getTitle3());
            holder.button3.setActivated(!dataList.get(position).isButton3Selected());
            setButton3(holder);
            holder.button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setButton3(holder);
                }
            });
        }
    }

    private void setButton1(ItemViewHolder holder){
        if(holder.button1.isActivated()){
            holder.button1.setBackground(ContextCompat.getDrawable(context, R.drawable.row_cat_button));
            holder.button1.setActivated(false);
            clicklistener.itemClickedRemove();
        }else{
            holder.button1.setBackground(ContextCompat.getDrawable(context, R.drawable.row_cat_b_select));
            holder.button1.setActivated(true);
            clicklistener.itemClickedAdd();
        }
    }

    private void setButton2(ItemViewHolder holder){
        if(holder.button2.isActivated()){
            holder.button2.setBackground(ContextCompat.getDrawable(context, R.drawable.row_cat_button));
            holder.button2.setActivated(false);
            clicklistener.itemClickedRemove();
        }else{
            holder.button2.setBackground(ContextCompat.getDrawable(context, R.drawable.row_cat_b_select));
            holder.button2.setActivated(true);
            clicklistener.itemClickedAdd();
        }
    }

    private void setButton3(ItemViewHolder holder){
        if(holder.button3.isActivated()){
            holder.button3.setBackground(ContextCompat.getDrawable(context, R.drawable.row_cat_button));
            holder.button3.setActivated(false);
            clicklistener.itemClickedRemove();
        }else{
            holder.button3.setBackground(ContextCompat.getDrawable(context, R.drawable.row_cat_b_select));
            holder.button3.setActivated(true);
            clicklistener.itemClickedAdd();
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
