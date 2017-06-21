package com.thanhtuan.posnet.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.model.NewFeeds;
import com.thanhtuan.posnet.util.RecyclerViewUtil;
import com.thanhtuan.posnet.view.activity.CheckActivity;
import com.thanhtuan.posnet.view.adapter.NewfeedAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.rcvNewfeed)
    RecyclerView rcvNewfeed;

    private List<NewFeeds> mNewfeed;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);

        mNewfeed = new ArrayList<>();
        if (getActivity() == null) return view;
        RecyclerViewUtil.setupRecyclerView(rcvNewfeed,new NewfeedAdapter(mNewfeed,getActivity()),getActivity());
        initData();

        return view;
    }

    private void addControls(){
        if (getActivity() == null) return;
        NewfeedAdapter adapter = new NewfeedAdapter(mNewfeed, getActivity());
        rcvNewfeed.setAdapter(adapter);
    }

    private void initData() {
        NewFeeds newFeeds1 = new NewFeeds("Khuyến mãi","Tivi Led Sony", "khgkdjahkjdhfkjhfkjhàhsàhsakjfhákjfhsàhạkfhàhjkầhjkàhsàhákfhkjh");
        NewFeeds newFeeds2 = new NewFeeds("Thông báo nhân sự","Tăng lương", "khgkdjahkjdhfkjhfkjhàhsàhsakjfhákjfhsàhạkfhàhjkầhjkàhsàhákfhkjh");
        NewFeeds newFeeds3 = new NewFeeds("Trả lương","Lương tháng 5", "khgkdjahkjdhfkjhfkjhàhsàhsakjfhákjfhsàhạkfhàhjkầhjkàhsàhákfhkjh");
        mNewfeed.add(newFeeds1);
        mNewfeed.add(newFeeds2);
        mNewfeed.add(newFeeds3);

        addControls();
    }

    @OnClick(R.id.btnCheckPR)
    public void ClickCheckPR(){
        Intent intent = new Intent(getActivity(), CheckActivity.class);
        getActivity().startActivity(intent);
    }
}
