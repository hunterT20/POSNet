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
import android.widget.Toast;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.google.android.gms.vision.barcode.Barcode;
import com.thanhtuan.posnet.POSCenterApplication;
import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.data.DataManager;
import com.thanhtuan.posnet.model.ItemKM;
import com.thanhtuan.posnet.model.ItemSearch;
import com.thanhtuan.posnet.model.Product;
import com.thanhtuan.posnet.model.StatusKho;
import com.thanhtuan.posnet.model.StatusProduct;
import com.thanhtuan.posnet.model.StatusSearch;
import com.thanhtuan.posnet.util.InterfaceUtil;
import com.thanhtuan.posnet.util.RecyclerViewUtil;
import com.thanhtuan.posnet.util.ScanUtil;
import com.thanhtuan.posnet.util.SharePreferenceUtil;
import com.thanhtuan.posnet.view.activity.MainActivity;
import com.thanhtuan.posnet.view.activity.ReOrderActivity;
import com.thanhtuan.posnet.view.adapter.ItemSearchAdapter;
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

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckFragment extends Fragment{
    @BindView(R.id.rcvKhuyenMai)    RecyclerView rcvKhuyenMai;
    @BindView(R.id.Thongtin)        ConstraintLayout ThongTin;
    @BindView(R.id.txtvNamePR)      TextView txtvNamePR;
    @BindView(R.id.txtvDonGiaPR)    TextView txtvDonGiaPR;
    @BindView(R.id.txtvSoSPChon)    TextView txtvSoSPChon;
    @BindView(R.id.btnReOrder)      Button btnReOrder;

    private List<ItemKM>   listKMAll;      /*Tất cả sản phẩm khuyến mãi của sản phẩm*/
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
        }
        String SiteID = SharePreferenceUtil.getValueSiteid(getActivity());
        String ItemID = SharePreferenceUtil.getValueItemid(getActivity());
        getListKMAll(SiteID,ItemID);
        checkHangTon(ItemID);
    }

    private void getListKMAll(String SiteID, String ItemID){
        mSubscriptions.add(dataManager
                .getProduct(SiteID,ItemID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribeWith(new DisposableObserver<StatusProduct>() {
                    @Override
                    public void onNext(@NonNull StatusProduct statusProduct) {
                        if (statusProduct.getData() != null){
                            product = statusProduct.getData().get(0);
                            txtvNamePR.setText(product.getItemName());
                            txtvDonGiaPR.setText(String.valueOf(product.getSalesPrice()));
                            listKMAll = product.getListItemkm();
                            if (listKMAll.size() != 0){
                                txtvSoSPChon.setText("Khách hàng được chọn " +
                                        listKMAll.get(0).getPermissonBuyItemAttach() + " sản phẩm!");
                                if (getActivity() == null) return;
                                KMAdapter adapter = new KMAdapter(listKMAll, getActivity());
                                rcvKhuyenMai.setAdapter(adapter);
                            }
                        }else {
                            Toast.makeText(getActivity(), "Sản phẩm không có", Toast.LENGTH_SHORT).show();
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

    private void checkHangTon(String ItemID){
        mSubscriptions.add(dataManager
                .checkKho(ItemID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribeWith(new DisposableObserver<StatusKho>() {
                    @Override
                    public void onNext(@NonNull StatusKho statusKho) {
                        if (statusKho.getData() == null){
                            btnReOrder.setText("Hết hàng");
                            btnReOrder.setEnabled(false);
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

    @OnClick(R.id.imgChitiet)
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
        ((ReOrderActivity)getActivity()).productCurrent = product;
        ((ReOrderActivity)getActivity()).listPRBuy.add(product);
        ((ReOrderActivity)getActivity()).callFragment(new ReorderFragment(),"Thông tin Order");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
                return true;
            case R.id.action_search:
                ((ReOrderActivity)getActivity()).callFragment(new SearchFragment(),"Search");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.home, menu);
    }
}
