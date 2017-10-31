package com.yunzhou.commonlibrary.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.yunzhou.commonlibrary.R;
import com.yunzhou.commonlibrary.activity.fragment.HomeFragment;
import com.yunzhou.libcommon.views.BottomNavigation.BottomNavigationViewEx;

public class HomeTabActivity extends AppCompatActivity {

    private final static String[] TITLES = {"Home", "Dashboard", "Notification", "Other"};

    private ViewPager mViewPager;
    private BottomNavigationViewEx mBottomGroupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_tab);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mBottomGroupView = (BottomNavigationViewEx) findViewById(R.id.bottomView);
        mBottomGroupView.clearAllAnimation();

        mBottomGroupView.updateBadgePoint(0);
        mBottomGroupView.updateBadgePoint(1);
        mBottomGroupView.updateBadgePoint(2);
//        mBottomGroupView.updateBadgePoint(3);

//        mBottomGroupView.updateBadgeCount(0, 99);
//        mBottomGroupView.updateBadgeCount(1, 9);
//        mBottomGroupView.updateBadgeCount(2, 999);
//        mBottomGroupView.updateBadgeCount(3, 25);
//        mBottomGroupView.updateBadgePoint(3);


        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomGroupView.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBottomGroupView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        mViewPager.setCurrentItem(0);
//                        mBottomGroupView.clearBadge(0);
                        mBottomGroupView.updateOffset();
                        break;
                    case R.id.navigation_dashboard:
                        mViewPager.setCurrentItem(1);
                        mBottomGroupView.clearBadge(1);
                        break;
                    case R.id.navigation_notifications:
                        mViewPager.setCurrentItem(2);
                        mBottomGroupView.clearBadge(2);
                        break;
//                    case R.id.navigation_other:
//                        mViewPager.setCurrentItem(3);
//                        mBottomGroupView.clearBadge(3);
//                        break;

                }
                return true;
            }
        });
    }

    private static class MyAdapter extends FragmentPagerAdapter{

        private Fragment[] fragments = new Fragment[TITLES.length];

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(fragments[position] == null) {
                HomeFragment fragment = new HomeFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", TITLES[position]);
                fragment.setArguments(bundle);
                fragments[position] = fragment;
            }
            return fragments[position];
        }

        @Override
        public int getCount() {
            return TITLES.length - 1;
        }
    }
}
