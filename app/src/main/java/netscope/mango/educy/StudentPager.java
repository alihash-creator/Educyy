package netscope.mango.educy;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class StudentPager extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public StudentPager(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Student1 tab1 = new Student1();
                return tab1;
            case 1:
                Student2 tab2 = new Student2();
                return tab2;
            case 2:
                Student3 tab3 = new Student3();
                return tab3;
            case 3:
                Student4 tab4 = new Student4();
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