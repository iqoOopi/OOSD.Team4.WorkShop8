package com.erict135.oosdteam4workshop8;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erict135.oosdteam4workshop8.model.Customer;


public class HomeFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static int section;
    private static HomeFragment homeFragment;
    private static Customer customer;
    public HomeFragment() {
    }

    public static HomeFragment newInstance(int sectionNumber,Customer c) {
        customer=c;
        section=sectionNumber;
        homeFragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        homeFragment.setArguments(args);
        return homeFragment;
    }
    public static HomeFragment getInstance() {
        return homeFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
}