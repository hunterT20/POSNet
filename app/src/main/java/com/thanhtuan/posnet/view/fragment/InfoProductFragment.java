package com.thanhtuan.posnet.view.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.thanhtuan.posnet.POSCenterApplication;
import com.thanhtuan.posnet.R;
import com.thanhtuan.posnet.data.DataManager;
import com.thanhtuan.posnet.model.ItemKM;
import com.thanhtuan.posnet.model.Product;
import com.thanhtuan.posnet.model.StatusKho;
import com.thanhtuan.posnet.model.StatusProduct;
import com.thanhtuan.posnet.util.NumberTextWatcherForThousand;
import com.thanhtuan.posnet.util.SharePreferenceUtil;
import com.thanhtuan.posnet.view.activity.MainActivity;
import com.thanhtuan.posnet.view.activity.ReOrderActivity;
import com.thanhtuan.posnet.view.adapter.KhuyenMaiAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */

public class InfoProductFragment extends Fragment{
    private static final String TAG = "FragmentInfo";
    private ListView lvKhuyenMai;
    private TextView txtvNamePR;
    private TextView txtvDonGiaPR;
    private TextView txtvSoSPChon;
    private TextView txtvTamTinh;
    private TextView txtvGiam;
    private TextView txtvTongGia;
    private Button btnReOrder;
    private ImageView imgChitiet;

    private List<ItemKM>   listKMAll;      /*Tất cả sản phẩm khuyến mãi của sản phẩm*/
    private Product product;
    private KhuyenMaiAdapter adapter;

    private DataManager dataManager;
    private CompositeDisposable mSubscriptions;

    long GiamGia = 0;

    public InfoProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_product, container, false);
        ButterKnife.bind(this,view);
        setHasOptionsMenu(true);

        listKMAll = new ArrayList<>();

        if (getActivity() == null) return view;

        dataManager = POSCenterApplication.get(getActivity()).getComponent().dataManager();
        mSubscriptions = new CompositeDisposable();

        addViews(view);
        addEvents();
        return view;
    }

    /**
     * addEvents chứa cái sự kiện diễn ra trong view hiện tại
     * ===> btnReOrder click sẽ add sản phẩm vào list sản phẩm đang mua và thực hiện chuyển view sang ReorderFragment
     * ===> imgChitiet click sẽ hiển thị dialog chứa webView thông tin chi tiết sản phẩm
     * ===> lvKhuyenMai click sẽ thực hiện cộng trừ tiền nếu có tách giá và set chọn cho sản phẩm khuyến mãi
     */
    private void addEvents() {
        btnReOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ReOrderActivity)getActivity()).productCurrent = product;
                ((ReOrderActivity)getActivity()).listPRBuy.add(product);
                ((ReOrderActivity)getActivity()).callFragment(new ReorderFragment(),"Thông tin Order");
            }
        });

        imgChitiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ItemID = SharePreferenceUtil.getValueItemid(getActivity());
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Chi tiết sản phẩm");

                WebView wv = new WebView(getActivity());
                wv.loadUrl("https://dienmaycholon.vn/default/product/thongsokythuat/sap/"+ItemID);
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
        });

        lvKhuyenMai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    ItemKM itemKM = listKMAll.get((int)l);
                    if (!itemKM.getChon()){
                        itemKM.setChon(true);
                        view.setBackgroundResource(R.color.colorAccent);
                        setTachGiaChon(itemKM);
                    }else {
                        itemKM.setChon(false);
                        view.setBackgroundResource(R.color.cardview_light_background);
                        if (itemKM.getTachGia() == 1){
                            setTachGiaKhongChon(itemKM);
                        }
                    }
                }catch (Exception ex){
                    Log.e(TAG, "onItemClick: " + ex);
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSubscriptions.clear();
    }

    /**
     * addViews dùng để khởi tạo những view cần sử dụng
     * @param view: fragment hiện tại
     */
    private void addViews(View view) {
        View headerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header_listview_info_product, null, false);
        View footerView = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_listview_info_product, null, false);

        lvKhuyenMai = view.findViewById(R.id.lvKhuyenMai);
        btnReOrder = view.findViewById(R.id.btnReOrder);

        txtvDonGiaPR = headerView.findViewById(R.id.txtvDonGiaPR);
        txtvNamePR = headerView.findViewById(R.id.txtvNamePR);
        txtvSoSPChon = headerView.findViewById(R.id.txtvSoSPChon);
        imgChitiet = headerView.findViewById(R.id.imgChitiet);

        txtvTamTinh = footerView.findViewById(R.id.txtvTamTinh);
        txtvGiam = footerView.findViewById(R.id.txtvGiam);
        txtvTongGia = footerView.findViewById(R.id.txtvTongGia);

        lvKhuyenMai.addHeaderView(headerView,null,false);
        lvKhuyenMai.addFooterView(footerView,null,false);

        if (((ReOrderActivity)getActivity()).productCurrent != null){
            product = ((ReOrderActivity)getActivity()).productCurrent;
        }
        String SiteID = SharePreferenceUtil.getValueSiteid(getActivity());
        String ItemID = SharePreferenceUtil.getValueItemid(getActivity());
        getListKMAll(SiteID,ItemID);
        checkHangTon(ItemID);
    }

    /**
     * getListKMAll: để lấy danh sách khuyến mãi của sản phẩm
     * @param SiteID: mã số của kho hàng
     * @param ItemID: mã số sản phẩm
     */
    private void getListKMAll(String SiteID, String ItemID){
        mSubscriptions.add(dataManager
                .getProduct(SiteID,ItemID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(dataManager.getScheduler())
                .subscribeWith(new DisposableObserver<StatusProduct>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onNext(@NonNull StatusProduct statusProduct) {
                        if (statusProduct.getData() != null){
                            product = statusProduct.getData().get(0);
                            txtvNamePR.setText(product.getItemName() + " (" + "Loại: " + product.getTypeItemID() + ")");
                            listKMAll = product.getListItemkm();

                            if (listKMAll.size() != 0){
                                txtvSoSPChon.setText("Khách hàng được chọn " +
                                        listKMAll.get(0).getPermissonBuyItemAttach() + " sản phẩm!");
                                if (getActivity() == null) return;
                                adapter = new KhuyenMaiAdapter(getActivity(),listKMAll);
                                lvKhuyenMai.setAdapter(adapter);
                                long TongGia = product.getSalesPrice();
                                long DonGia = product.getSalesPrice();
                                for (ItemKM itemKM : listKMAll){
                                    if (itemKM.getTachGia() == 1){
                                        if (!itemKM.getChon()){
                                            DonGia += itemKM.getPromotionPrice();
                                            GiamGia += itemKM.getGiamGiaKLHKM();
                                            TongGia = TongGia + itemKM.getPromotionPrice() - itemKM.getGiamGiaKLHKM();
                                        }
                                    }
                                }
                                txtvDonGiaPR.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(DonGia)) + "đ");
                                txtvTamTinh.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(DonGia)) + "đ");
                                txtvTongGia.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(TongGia)) + "đ");
                                txtvGiam.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(GiamGia)) + "đ");
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

    /**
     * checkHangTon: kiểm tra tồn kho trong các kho trong hệ thống
     * =====> Nếu còn hàng: btnReOrder không thay đổi
     * =====> Nếu hết hàng: btnReOrder sẽ thành Hết hàng và không cho touch
     * @param ItemID: mã số sản phẩm
     */
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

    /**
     * setTachGiaKhongChon để set giá cho sản phẩm nếu sản phẩm khuyến mãi không chọn trong trường hợp
     * tách giá hoặc không
     * @param itemKM: sản phẩm khuyến mãi
     */
    private void setTachGiaKhongChon(ItemKM itemKM){
        long tonggia = product.getSalesPrice();
        if (itemKM.getTachGia() == 1){
            txtvDonGiaPR.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(tonggia + itemKM.getPromotionPrice())) + "đ");
            tonggia = tonggia + itemKM.getPromotionPrice() - itemKM.getGiamGiaKLHKM();
            GiamGia += GiamGia + itemKM.getGiamGiaKLHKM();
            txtvTongGia.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(tonggia)) + "đ");
            txtvGiam.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(GiamGia)) + "đ");
        }else {
            txtvDonGiaPR.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(tonggia)) + "đ");
            txtvTamTinh.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(tonggia)) + "đ");
            txtvGiam.setText("0đ");
            txtvTongGia.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(tonggia)) + "đ");
        }
    }

    /**
     * setTachGiaChon để set giá cho sản phẩm không tách giá
     */
    private void setTachGiaChon(ItemKM itemKM){
        long tonggia = product.getSalesPrice();
        GiamGia -= itemKM.getGiamGiaKLHKM();
        txtvDonGiaPR.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(tonggia)) + "đ");
        txtvTamTinh.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(tonggia)) + "đ");
        txtvGiam.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(GiamGia)) + "đ");
        txtvTongGia.setText(NumberTextWatcherForThousand.getDecimalFormattedString(String.valueOf(tonggia)) + "đ");
    }
}
