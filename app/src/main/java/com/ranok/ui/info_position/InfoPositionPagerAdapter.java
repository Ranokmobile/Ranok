package com.ranok.ui.info_position;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ranok.ui.info_position.lot_info.LotInfoFragment;
import com.ranok.ui.info_position.main_info.MainInfoFragment;
import com.ranok.ui.info_position.receipt_info.ReceiptInfoFragment;

public class InfoPositionPagerAdapter extends FragmentPagerAdapter {

    private int cntPages;


    private Fragment[] fragments  = {new MainInfoFragment(), new LotInfoFragment(), new ReceiptInfoFragment()};
    private String[] titles = {"Основные","Партии", "Приёмка"};


    public InfoPositionPagerAdapter(FragmentManager fm, int cntPages) {
        super(fm);
        this.cntPages = cntPages;
    }

    public void setCntPages(int newCntPages) {
        if (newCntPages != cntPages) {
            if (newCntPages == 2) {

            }
            cntPages = newCntPages;
            notifyDataSetChanged();
        }
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
        return cntPages;
    }
}
