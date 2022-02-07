package netscope.mango.educy;

        import android.content.Intent;
        import android.os.Bundle;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import android.view.View;
        import android.widget.TextView;

        import java.util.ArrayList;

public class Category extends AppCompatActivity implements View.OnClickListener, CatListner {
    private int numSelected = 11;
    private int totalMenuCount = 14;
    private TextView selectedInfo;
    String student="";
    String teacher="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        student=getIntent().getStringExtra("student");
        teacher=getIntent().getStringExtra("teacher");
        selectedInfo = (TextView) findViewById(R.id.selectedInfo);

        //Top 40 Category
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        ArrayList<CatModel> rowListItem2 = getAllItemList2();
        CatAdapter rcAdapter2 = new CatAdapter(this, rowListItem2);

        RecyclerView rView2 = (RecyclerView) findViewById(R.id.recyclerView);
        rView2.setLayoutManager(layoutManager);
        rView2.setAdapter(rcAdapter2);
        rView2.setNestedScrollingEnabled(false);
        rcAdapter2.setClickListener(this);
    }

    private ArrayList<CatModel> getAllItemList2(){
        ArrayList<CatModel> allItems = new ArrayList<>();
        CatModel dt;

        dt = new CatModel("IT", false, "Pharmacy", false, "Business", false);
        allItems.add(dt);
        dt = new CatModel("Fashion", false, "Medical", true, "Electrical", false);
        allItems.add(dt);
        dt = new CatModel("Mechanical", true, "Chemical", false, "Media", false);
        allItems.add(dt);
        dt = new CatModel("Textile", false, "Physics", false, "Chemistry", false);
        allItems.add(dt);
        dt = new CatModel("Mathematics", false, "Islamic", true, null, false);
        allItems.add(dt);
        return  allItems;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonNext:
                if(teacher!=null) {
                    Intent i = new Intent(Category.this, Teacher.class);
                    startActivity(i);
                }
                else {
                    Intent i = new Intent(Category.this, Student.class);
                    startActivity(i);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void itemClickedAdd() {
        numSelected++;
        setSelectedInfo();
    }

    @Override
    public void itemClickedRemove() {
        numSelected--;
        setSelectedInfo();
    }

    private void setSelectedInfo(){
        selectedInfo.setText(numSelected + " out of " + totalMenuCount + " selected");
    }
}
