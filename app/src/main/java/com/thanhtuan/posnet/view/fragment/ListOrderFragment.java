package com.thanhtuan.posnet.view.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.Product;
import com.thanhtuan.posnet.view.activity.ReOrderActivity;
import com.thanhtuan.posnet.view.adapter.OrderAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListOrderFragment extends Fragment {
    @BindView(R.id.listOrder)
    ExpandableListView listOrder;

    private List<Product> list;

    public ListOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_order, container, false);
        ButterKnife.bind(this,view);

        list = ((ReOrderActivity)getActivity()).listPRBuy;
        OrderAdapter adapter = new OrderAdapter(getActivity(),list);
        listOrder.setAdapter(adapter);
        return view;
    }

}
