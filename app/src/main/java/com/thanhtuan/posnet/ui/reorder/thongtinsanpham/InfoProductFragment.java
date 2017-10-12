package com.thanhtuan.posnet.ui.reorder.thongtinsanpham;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
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
import android.widget.EditText;
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
import com.thanhtuan.posnet.ui.reorder.search.SearchFragment;
import com.thanhtuan.posnet.util.NumberTextWatcherForThousand;
import com.thanhtuan.posnet.util.RecyclerViewUtil;
import com.thanhtuan.posnet.util.SharePreferenceUtil;
import com.thanhtuan.posnet.ui.index.MainActivity;
import com.thanhtuan.posnet.ui.reorder.ReOrderActivity;
import com.thanhtuan.posnet.ui.reorder.thongtingiaohang.ReorderFragment;

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
    private RecyclerView rcvKhuyenMai;

    private Button btnReOrder;

    private List<ItemKM>   listKMAll;      /*Tất cả sản phẩm khuyến mãi của sản phẩm*/
    private Product product;
    private KhuyenMaiAdapter adapter;

    private DataManager dataManager;
    private CompositeDisposable mSubscriptions;

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
        rcvKhuyenMai = view.findViewById(R.id.rcvKhuyenMai);
        RecyclerViewUtil.setupRecyclerView(rcvKhuyenMai,new KhuyenMaiAdapter(getActivity(),listKMAll,product),getActivity());
        btnReOrder = view.findViewById(R.id.btnReOrder);

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
                            listKMAll = product.getListItemkm();
                            adapter = new KhuyenMaiAdapter(getActivity(),listKMAll,product);
                            rcvKhuyenMai.setAdapter(adapter);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search, menu);

        ImageView btnQRcode = ((ReOrderActivity)getActivity()).getQR();
        EditText edtSearch = ((ReOrderActivity)getActivity()).getSearch();
        TextView txtvLogo = ((ReOrderActivity)getActivity()).getLogo();

        edtSearch.setVisibility(View.GONE);
        edtSearch.setText("");
        txtvLogo.setVisibility(View.VISIBLE);

        txtvLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ReOrderActivity)getActivity()).callFragment(new SearchFragment(),"Search");
            }
        });
    }
}
