package netscope.mango.educy;
import androidx.recyclerview.widget.RecyclerView;


public interface WalkthroughDragListner {


    void onStartDrag(RecyclerView.ViewHolder viewHolder);

    void onFinishDrag();

}
