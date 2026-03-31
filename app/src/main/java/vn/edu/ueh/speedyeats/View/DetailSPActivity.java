package vn.edu.ueh.speedyeats.View;

import vn.edu.ueh.speedyeats.Util.DetailValidator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import vn.edu.ueh.speedyeats.View.Adapter.BinhLuanAdapter;
import vn.edu.ueh.speedyeats.MainActivity;
import vn.edu.ueh.speedyeats.Model.Binhluan;
import vn.edu.ueh.speedyeats.Model.Product;
import vn.edu.ueh.speedyeats.Presenter.BinhLuanPresenter;
import vn.edu.ueh.speedyeats.Presenter.GioHangPresenter;
import vn.edu.ueh.speedyeats.R;
import vn.edu.ueh.speedyeats.my_interface.BinhLuanView;
import vn.edu.ueh.speedyeats.my_interface.GioHangView;
import vn.edu.ueh.speedyeats.my_interface.IClickCTHD;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailSPActivity extends AppCompatActivity implements BinhLuanView, GioHangView {

    private ImageView viewAnimation;
    private View viewEndAnimation;


    private AppCompatToggleButton toggleButtonFavorite;
    private FloatingActionButton btnAddCartDetail;
    private LinearLayout linearShowAllCmt;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView tvGiaDetail, tvHansudungDetail, tvTrongluongDetail, tvMoTaDetail;
    private ImageView imgDetail;
    private Toolbar toolbar;
    private RecyclerView rcvBinhluan;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private BinhLuanPresenter binhLuanPresenter;
    private ArrayList<Binhluan> mListBinhluan;
    private BinhLuanAdapter adapter;
    private Product product;

    private BottomSheetDialog bottomSheetDialog;
    private TextView tvTenBottom, tvGiaBottom, tvSoluongBottom, tvMotaBottom;
    private ImageView imgBottom, btnMinusBottom, btnPlusBottom;
    private Button btnBottom;
    private int slBottom = 1;
    private GioHangPresenter gioHangPresenter;

    private MainActivity mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_spactivity);

        InitWidget();
        Init();
        Event();

    }

    private void Event() {
        linearShowAllCmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailSPActivity.this, CommentActivity.class);
                intent.putExtra("allcmt", product);
                startActivity(intent);
            }
        });

        btnAddCartDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBottomSheetDialog();
                tvTenBottom.setText(product.getTensp());
                tvGiaBottom.setText(NumberFormat.getInstance().format(product.getGiatien())+"");
                tvMotaBottom.setText(product.getMota());
                Picasso.get().load(product.getHinhanh()).into(imgBottom);
                initBottomSheet();
                bottomSheetDialog.show();
            }
        });
    }

    // Nút yêu thích
    public void onDefaultToggleClick(View view) {

        if (!DetailValidator.isProductValid(product.getId())) {
            Toast.makeText(this, "Sản phẩm không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
            toggleButtonFavorite.setChecked(false);
            return;
        }

        String userId = auth.getCurrentUser().getUid();
        boolean isChecked = toggleButtonFavorite.isChecked();

        if (isChecked) {
            HashMap<String, String> map = new HashMap<>();
            map.put("idproduct", product.getId());
            map.put("iduser", userId);

            db.collection("Favorite").add(map);

        } else {
            db.collection("Favorite")
                    .whereEqualTo("idproduct", product.getId())
                    .whereEqualTo("iduser", userId)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot q : queryDocumentSnapshots) {
                            db.collection("Favorite").document(q.getId()).delete();
                        }
                    });
        }
    }

    private void setBottomSheetDialog(){
        // Bottom sheet Dialog
        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.layout_persistent_bottom_sheet);
        tvTenBottom = bottomSheetDialog.findViewById(R.id.tv_ten_bottom);
        imgBottom = bottomSheetDialog.findViewById(R.id.img_bottom);
        tvGiaBottom = bottomSheetDialog.findViewById(R.id.tv_gia_bottom);
        btnMinusBottom = bottomSheetDialog.findViewById(R.id.btn_minus_bottom);
        btnPlusBottom = bottomSheetDialog.findViewById(R.id.btn_plus_bottom);
        tvSoluongBottom = bottomSheetDialog.findViewById(R.id.tv_soluong_bottom);
        tvMotaBottom = bottomSheetDialog.findViewById(R.id.tv_mota_bottom);
        btnBottom = bottomSheetDialog.findViewById(R.id.btn_bottom);
    }
    public void initBottomSheet(){
        btnMinusBottom.setVisibility(View.GONE);
        btnMinusBottom.setOnClickListener(view -> {
            int current = Integer.parseInt(tvSoluongBottom.getText().toString());

            if (!DetailValidator.canDecrease(current)) return;

            slBottom = current - 1;
            tvSoluongBottom.setText(String.valueOf(slBottom));

            btnMinusBottom.setVisibility(
                    DetailValidator.canDecrease(slBottom) ? View.VISIBLE : View.GONE
            );
        });

        btnPlusBottom.setOnClickListener(view -> {
            int current = Integer.parseInt(tvSoluongBottom.getText().toString());

            // Nếu muốn giới hạn theo stock thì bật dòng này
            // if (!DetailValidator.canIncrease(current, product.getSoluong())) return;

            slBottom = current + 1;
            tvSoluongBottom.setText(String.valueOf(slBottom));

            btnMinusBottom.setVisibility(
                    DetailValidator.canDecrease(slBottom) ? View.VISIBLE : View.GONE
            );
        });

        btnBottom.setOnClickListener(view -> {

            if (!DetailValidator.isProductValid(product.getId())) {
                Toast.makeText(this, "Sản phẩm không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!DetailValidator.isQuantityValid(slBottom)) {
                Toast.makeText(this, "Số lượng không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            gioHangPresenter.AddCart(product.getId(), (long) slBottom);
        });
    }

    private void Init() {
        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("search");

        FirebaseAuth auth = FirebaseAuth.getInstance();

        // ✅ Check null trước khi dùng
        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();

            db.collection("Favorite")
                    .whereEqualTo("iduser", userId)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot q : queryDocumentSnapshots) {
                            if (q.getString("idproduct").equals(product.getId())) {
                                toggleButtonFavorite.setChecked(true);
                            }
                        }
                    });
        }

        // ✅ UI vẫn hiển thị bình thường dù chưa login
        collapsingToolbarLayout.setTitle(product.getTensp());
        tvGiaDetail.setText(NumberFormat.getInstance().format(product.getGiatien()));
        tvHansudungDetail.setText(product.getHansudung());
        tvTrongluongDetail.setText(product.getTrongluong());
        tvMoTaDetail.setText(product.getMota());
        Picasso.get().load(product.getHinhanh()).into(imgDetail);

        Log.d("soluong", "Số lượng sp là: " + product.getSoluong());

        binhLuanPresenter = new BinhLuanPresenter(this);
        binhLuanPresenter.HandleGetBinhLuanLimit(product.getId());
    }
    private void InitWidget() {
        mainActivity = new MainActivity();
        viewAnimation = findViewById(R.id.view_animation);
        viewEndAnimation = findViewById(R.id.view_end_animation);
        toggleButtonFavorite = findViewById(R.id.toogle_btn_favorite);
        btnAddCartDetail = findViewById(R.id.btn_addcart_detail);
        linearShowAllCmt = findViewById(R.id.linear_show_all_cmt);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        tvGiaDetail = findViewById(R.id.tv_gia_detail);
        tvHansudungDetail = findViewById(R.id.tv_hansudung_detail);
        tvTrongluongDetail = findViewById(R.id.tv_trongluong_detail);
        tvMoTaDetail = findViewById(R.id.tv_mota_detail);
        imgDetail = findViewById(R.id.img_detail);
        rcvBinhluan = findViewById(R.id.rcv_binhluan);
        toolbar = findViewById(R.id.toolbar_detail);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mListBinhluan = new ArrayList<>();
        gioHangPresenter = new GioHangPresenter(this);


    }

    @Override
    public void getDataBinhLuan(String idbinhluan, String idproduct, String iduser, String rate, String noidung) {
        mListBinhluan.add(new Binhluan(idbinhluan, idproduct, iduser, rate, noidung));
        adapter = new BinhLuanAdapter(DetailSPActivity.this, mListBinhluan, new IClickCTHD() {
            @Override
            public void onClickCTHD(int pos) {

            }
        });

        rcvBinhluan.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rcvBinhluan.setAdapter(adapter);
    }

    @Override
    public void OnSucess() {
        Toast.makeText(this, "Thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show();
        bottomSheetDialog.dismiss();
    }

    @Override
    public void OnFail() {
        Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getDataSanPham(String id, String idsp, String tensp, Long giatien, String hinhanh, String loaisp, String mota, Long soluong, String hansudung, Long type, String trongluong) {

    }
}