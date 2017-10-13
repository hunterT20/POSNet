package com.thanhtuan.posnet.ui.reorder.listdamua;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.data.Product;
import com.thanhtuan.posnet.ui.reorder.search.SearchFragment;
import com.thanhtuan.posnet.util.NumberTextWatcherForThousand;
import com.thanhtuan.posnet.ui.reorder.ReOrderActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListOrderFragment extends Fragment {
    @BindView(R.id.listOrder)            ExpandableListView listOrder;
    @BindView(R.id.txtvTongTienOrder)    TextView txtvTonTienOrder;

    private List<Product> productList;

    public ListOrderFragment() {
        // Required empty public constructor
    }


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_order, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true); //cho phép set event cho Option menu

        ((ReOrderActivity)getActivity()).getCardViewSearch().setVisibility(View.GONE); //Ẩn thanh search

        productList = ((ReOrderActivity)getActivity()).listPRBuy;
        OrderAdapter adapter = new OrderAdapter(getActivity(), productList);
        listOrder.setAdapter(adapter);
        for (int i = 0; i < productList.size(); i++){
            listOrder.expandGroup(i);
        }
        int TongTien = ((ReOrderActivity)getActivity()).TongTien();
        txtvTonTienOrder.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(TongTien)) + "đ");
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                ((ReOrderActivity)getActivity()).callFragment(new SearchFragment(),"Tìm kiếm");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
