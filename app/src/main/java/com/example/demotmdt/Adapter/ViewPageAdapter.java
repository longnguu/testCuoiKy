package com.example.demotmdt.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.demotmdt.UIFragment.FavouriteFragment;
import com.example.demotmdt.UIFragment.HomeFragment;
import com.example.demotmdt.UIFragment.NotifiFragment;
import com.example.demotmdt.UIFragment.ProfileFragment;

public class ViewPageAdapter extends FragmentStatePagerAdapter {
    public ViewPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public ViewPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new FavouriteFragment();
            case 2:
                return new NotifiFragment();
            case 3:
                return new ProfileFragment();
            default: return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
