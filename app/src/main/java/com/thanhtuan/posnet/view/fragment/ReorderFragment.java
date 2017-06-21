package com.thanhtuan.posnet.view.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thanhtuan.posnet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReorderFragment extends Fragment {


    public ReorderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reorder, container, false);
    }

}
