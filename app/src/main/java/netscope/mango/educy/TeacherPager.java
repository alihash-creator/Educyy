package netscope.mango.educy;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class TeacherPager extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TeacherPager(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Teacher1 tab1 = new Teacher1();
                return tab1;
            case 1:
                Teacher2 tab2 = new Teacher2();
                return tab2;
            case 2:
                Teacher3 tab3 = new Teacher3();
                return tab3;
            case 3:
                Teacher4 tab4 = new Teacher4();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}