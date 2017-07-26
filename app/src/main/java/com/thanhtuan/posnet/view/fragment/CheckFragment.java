package com.thanhtuan.posnet.view.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.google.android.gms.vision.barcode.Barcode;
import com.thanhtuan.posnet.POSCenterApplication;
import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.data.DataManager;
import com.thanhtuan.posnet.model.ItemKM;
import com.thanhtuan.posnet.model.ItemSearch;
import com.thanhtuan.posnet.model.Product;
import com.thanhtuan.posnet.model.StatusProduct;
import com.thanhtuan.posnet.model.StatusSearch;
import com.thanhtuan.posnet.util.RecyclerViewUtil;
import com.thanhtuan.posnet.util.ScanUtil;
import com.thanhtuan.posnet.view.activity.MainActivity;
import com.thanhtuan.posnet.view.activity.ReOrderActivity;
import com.thanhtuan.posnet.view.adapter.KMAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckFragment extends Fragment {
    @BindView(R.id.rcvKhuyenMai)    RecyclerView rcvKhuyenMai;
    @BindView(R.id.Thongtin)        ConstraintLayout ThongTin;
    @BindView(R.id.txtvNamePR)      TextView txtvNamePR;
    @BindView(R.id.txtvDonGiaPR)    TextView txtvDonGiaPR;
    @BindView(R.id.txtvSLPR)        TextView txtvSLPR;
    @BindView(R.id.btnReOrder)      Button btnReOrder;

    private List<ItemKM>   listKMAll;      /*Tất cả sản phẩm khuyến mãi của sản phẩm*/
    private Boolean         coSP = false;   /*Set điều kiện có sản phẩm hay không để */
    public  String          codeBar;
    private Product product;

    private DataManager dataManager;
    private CompositeDisposable mSubscriptions;

    public CheckFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_check, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);

        listKMAll = new ArrayList<>();
        if (getActivity() == null) return view;
        RecyclerViewUtil.setupRecyclerView(rcvKhuyenMai, new KMAdapter(listKMAll,getActivity()),getActivity());

        dataManager = POSCenterApplication.get(getActivity()).getComponent().dataManager();
        mSubscriptions = new CompositeDisposable();

        addViews();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.clear();
    }

    private void addViews() {
        if (((ReOrderActivity)getActivity()).productCurrent != null){
            product = ((ReOrderActivity)getActivity()).productCurrent;
        }else {
            ThongTin.setVisibility(View.GONE);
        }
    }

    private void addControls(){
        if (getActivity() == null) return;
        KMAdapter adapter = new KMAdapter(listKMAll, getActivity());
        rcvKhuyenMai.setAdapter(adapter);
    }

    private void getListKMAll(String SiteID, String ItemID){
        mSubscriptions.add(dataManager
                .getProduct(SiteID,ItemID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribeWith(new DisposableObserver<StatusProduct>() {
                    @Override
                    public void onNext(@NonNull StatusProduct statusProduct) {
                        product = statusProduct.getData().get(0);
                        txtvNamePR.setText(product.getItemName());
                        txtvDonGiaPR.setText(String.valueOf(product.getSalesPrice()));
                        txtvSLPR.setText(String.valueOf(product.getQuantityCan()));
                        setGONE();
                        listKMAll = product.getListItemkm();
                        addControls();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    private void onSearch(String Model, String SiteID){
        mSubscriptions.add(dataManager
                .search(Model, SiteID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribeWith(new DisposableObserver<StatusSearch>() {
                    @Override
                    public void onNext(@NonNull StatusSearch statusSearch) {
                        List<ItemSearch> list = statusSearch.getData();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }));
    }

    @OnClick(R.id.txtvRecheck)
    public void ReCheckClick(){
        getListKMAll("H001","132372");
    }

    @OnClick(R.id.txtvChiTiet)
    public void ChiTietClick(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("Chi tiết sản phẩm");

        WebView wv = new WebView(getActivity());
        wv.loadUrl("https://www.dienmayxanh.com/tivi/tivi-led-asanzo-25t350");
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);

                return true;
            }
        });

        alert.setView(wv);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    @OnClick(R.id.btnReOrder)
    public void ReOrderClick(){
        Log.e("main",product.getItemName());
        ((ReOrderActivity)getActivity()).productCurrent = product;
        ((ReOrderActivity)getActivity()).listPRBuy.add(product);
        ((ReOrderActivity)getActivity()).callFragment(new ReorderFragment(),"Thông tin Order");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!coSP){
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                }else {
                    setVisible();
                }
                return true;
            case R.id.action_scan:
                Scan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);
        final MenuItem searchViewItem = menu.findItem(R.id.action_search);
        ((ReOrderActivity)getActivity()).getToolbar().getMenu().findItem(R.id.action_scan).setVisible(true);

        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                onSearch("UA65HU9000KXXV","H001");
                getListKMAll("H001","132372");
                searchViewAndroidActionBar.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
    }

    private void Scan(){
        ScanUtil.startScan(getActivity(), new MaterialBarcodeScanner.OnResultListener() {
            @Override
            public void onResult(Barcode barcode) {
                txtvNamePR.setText(barcode.rawValue);
                codeBar = barcode.rawValue;
                setGONE();
            }
        });
    }

    private void setGONE(){
        coSP = true;
        ThongTin.setVisibility(View.VISIBLE);
    }

    private void setVisible(){
        coSP = false;
        ThongTin.setVisibility(View.GONE);
    }
}
