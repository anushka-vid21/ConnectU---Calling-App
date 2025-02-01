package com.devdroid.mycontacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    PagerAdapter pagerAdapter;

    TabItem contacts,recent;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout=findViewById(R.id.tabLayout);
        contacts=findViewById(R.id.contacts);
        recent=findViewById(R.id.recent);
        viewPager =findViewById(R.id.viewPager);


        pagerAdapter =new PagerAdapter(getSupportFragmentManager(),2);
        viewPager.setAdapter(pagerAdapter);

         tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
             @Override
             public void onTabSelected(TabLayout.Tab tab) {
                 viewPager.setCurrentItem(tab.getPosition());

                 if (tab.getPosition()== 0 || tab.getPosition()==1){
                     pagerAdapter.notifyDataSetChanged();
                 }
             }

             @Override
             public void onTabUnselected(TabLayout.Tab tab) {

             }

             @Override
             public void onTabReselected(TabLayout.Tab tab) {

             }
         });

         viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }
}