package com.thanhtuan.posnet.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.google.android.gms.vision.barcode.Barcode;
import com.thanhtuan.posnet.POSCenterApplication;
import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.data.DataManager;
import com.thanhtuan.posnet.model.ItemSearch;
import com.thanhtuan.posnet.model.StatusSearch;
import com.thanhtuan.posnet.util.RecyclerViewUtil;
import com.thanhtuan.posnet.util.ScanUtil;
import com.thanhtuan.posnet.util.SharePreferenceUtil;
import com.thanhtuan.posnet.view.activity.MainActivity;
import com.thanhtuan.posnet.view.activity.ReOrderActivity;
import com.thanhtuan.posnet.view.adapter.ItemSearchAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    @BindView(R.id.rcvSearch)       RecyclerView rcvSearch;
    private List<ItemSearch> searchList;
    private DataManager dataManager;
    private CompositeDisposable mSubscriptions;

    public  String          codeBar;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);

        searchList = new ArrayList<>();
        dataManager = POSCenterApplication.get(getActivity()).getComponent().dataManager();
        mSubscriptions = new CompositeDisposable();

        RecyclerViewUtil.setupRecyclerView(rcvSearch, new ItemSearchAdapter(searchList, getActivity()),getActivity());

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.clear();
    }

    private void onSearch(String Model, String SiteID){
        mSubscriptions.add(dataManager
                .search(Model, SiteID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribeWith(new DisposableObserver<StatusSearch>() {
                    @Override
                    public void onNext(@NonNull StatusSearch statusSearch) {
                        if (statusSearch.getData() != null){
                            searchList = statusSearch.getData();

                            if (getActivity() == null) return;
                            ItemSearchAdapter adapter = new ItemSearchAdapter(searchList, getActivity());
                            rcvSearch.setAdapter(adapter);
                        }else {
                            Toast.makeText(getActivity(), "Không tìm thấy sản phẩm!", Toast.LENGTH_SHORT).show();
                            searchList = new ArrayList<ItemSearch>();
                            ItemSearchAdapter adapter = new ItemSearchAdapter(searchList, getActivity());
                            rcvSearch.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);
        final MenuItem searchViewItem = menu.findItem(R.id.action_search);
        ((ReOrderActivity)getActivity()).getToolbar().getMenu().findItem(R.id.action_scan).setVisible(true);
        ((ReOrderActivity)getActivity()).getToolbar().getMenu().findItem(R.id.action_store).setVisible(true);

        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (getActivity() == null) return false;
                String SiteID = SharePreferenceUtil.getValueSiteid(getActivity());
                if (newText.length() > 3){
                    onSearch(newText,SiteID);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
                return true;
            case R.id.action_scan:
                Scan();
                return true;
            case R.id.action_store:
                ((ReOrderActivity)getActivity()).callFragment(new ListOrderFragment(),"Sản phẩm đã mua");
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Scan(){
        ScanUtil.startScan(getActivity(), new MaterialBarcodeScanner.OnResultListener() {
            @Override
            public void onResult(Barcode barcode) {
                codeBar = barcode.rawValue;
            }
        });
    }
}
