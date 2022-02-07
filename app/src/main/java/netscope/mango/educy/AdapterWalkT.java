package netscope.mango.educy;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;



import java.util.ArrayList;
import java.util.List;



public class AdapterWalkT extends RecyclerView.Adapter<AdapterWalkT.ItemViewHolder> implements WalkthroughTouchAdpt {
    private final List<String> mItems = new ArrayList<>();
    private final WalkthroughDragListner mDragStartListener;
    private int count = 1;
    private Context context;

    public AdapterWalkT(Context ctx, Walkthrough dragStartListener) {
        mDragStartListener = dragStartListener;
        context = ctx;
        mItems.add(0, "Educy");
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_walkthrough, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        if(count > 1) {
            animationCardIn(holder.profilContainer);
        }
    }

    private void animationCardIn(View view){
        int start = 50;
        int duration = 500;

        AlphaAnimation animAlpha = new AlphaAnimation(0.5f, 1.0f);
        animAlpha.setDuration(duration);
        animAlpha.setStartOffset(start);
        animAlpha.setFillAfter(true);

        ScaleAnimation animScale1 = new ScaleAnimation(1.2f, 1f, 0.85f, 1f, Animation.RELATIVE_TO_SELF, (float)0.5, Animation.RELATIVE_TO_SELF, (float)0.5);
        animScale1.setDuration(duration);
        animScale1.setStartOffset(start);
        animScale1.setFillAfter(true);

        AnimationSet animSet = new AnimationSet(false);
        animSet.addAnimation(animAlpha);
        animSet.addAnimation(animScale1);

        view.startAnimation(animSet);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        return false;
    }

    @Override
    public void onItemDismiss(int position) {
        count++;
        mItems.remove(position);
        mItems.add(0, "Educy");
        notifyItemRemoved(position);
        mDragStartListener.onFinishDrag();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public final FrameLayout profilContainer;
        public ItemViewHolder(View itemView) {
            super(itemView);
            profilContainer = (FrameLayout) itemView.findViewById(R.id.profilContainer);
        }
    }
}
