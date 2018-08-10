package com.ranok.ui.info_position;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ranok.ui.info_position.lot_info.LotInfoFragment;
import com.ranok.ui.info_position.main_info.MainInfoFragment;

public class InfoPositionPagerAdapter extends FragmentPagerAdapter {


    Fragment[] fragments  = {new MainInfoFragment(), new LotInfoFragment()};
    String[] titles = {"Основные","Партии"};


    public InfoPositionPagerAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
