package com.example.navigation_smd_7a.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.navigation_smd_7a.Fragment.DeliveredFragment;
import com.example.navigation_smd_7a.Fragment.NewOrderFragment;
import com.example.navigation_smd_7a.Fragment.ScheduleFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 0:
                return new ScheduleFragment();
            case 1:
                return new DeliveredFragment();
            default:
                return new NewOrderFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
