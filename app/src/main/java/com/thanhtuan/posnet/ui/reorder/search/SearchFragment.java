package com.thanhtuan.posnet.ui.reorder.search;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.thanhtuan.posnet.ui.index.MainActivity;
import com.thanhtuan.posnet.ui.reorder.ReOrderActivity;
import com.thanhtuan.posnet.ui.reorder.listdamua.ListOrderFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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
    private static final String TAG = "SearchFragment";
    @BindView(R.id.rcvSearch) RecyclerView rcvSearch;
    private List<ItemSearch> searchList;
    private DataManager dataManager;
    private CompositeDisposable mSubscriptions;

    public  String codeBar;
    ItemSearchAdapter adapter;
    private Timer timer;

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

        ((ReOrderActivity)getActivity()).getCardViewSearch().setVisibility(View.VISIBLE);
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
                            adapter = new ItemSearchAdapter(searchList, getActivity());
                            rcvSearch.setAdapter(adapter);
                        }else {
                            Toast.makeText(getActivity(), "Không tìm thấy sản phẩm!", Toast.LENGTH_SHORT).show();
                            searchList = new ArrayList<>();
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
        inflater.inflate(R.menu.search, menu);

        ImageView btnQRcode = ((ReOrderActivity)getActivity()).getQR();
        final EditText edtSearch = ((ReOrderActivity)getActivity()).getSearch();
        TextView txtvLogo = ((ReOrderActivity)getActivity()).getLogo();

        edtSearch.setVisibility(View.VISIBLE);
        edtSearch.requestFocus();
        txtvLogo.setVisibility(View.GONE);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(final Editable editable) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (getActivity() == null) return;
                        final String SiteID = SharePreferenceUtil.getValueSiteid(getActivity());
                        if (editable.length() > 3){
                            onSearch(String.valueOf(editable),SiteID);
                        }else if (editable.length() == 0){
                            int size = searchList.size();
                            if (size != 0) {
                                searchList.clear();
                                adapter.notifyItemRangeRemoved(0, size);
                            }
                        }
                    }
                }, 600);

            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm =  (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });

        btnQRcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Scan();
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
