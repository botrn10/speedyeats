package vn.edu.ueh.speedyeats.View.Admin;
import vn.edu.ueh.speedyeats.Util.AdminProductValidator;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import vn.edu.ueh.speedyeats.View.Adapter.AdminProductAdapter;
import vn.edu.ueh.speedyeats.Model.Product;
import vn.edu.ueh.speedyeats.R;
import vn.edu.ueh.speedyeats.my_interface.IClickCTHD;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminProductActivity extends AppCompatActivity {

    private RecyclerView rcvAdminProduct;
    private AppCompatImageView imgAddProduct;
    private ImageView imgBackAdminProduct;
    private Spinner spinnerLoaiSP;

    private ArrayList<Product> mlistProduct;
    private ArrayList<Product> mlistAllProduct; // lưu toàn bộ
    private ArrayList<String> mlistLoaiSP;

    private AdminProductAdapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);

        InitWidget();
        Init();
        Event();
        setupAdapter(); // ❗ init 1 lần
        loadAllProducts();
    }

    private void Init() {
        mlistProduct = new ArrayList<>();
        mlistAllProduct = new ArrayList<>();
        mlistLoaiSP = new ArrayList<>();
        mlistLoaiSP.add("Tất cả");

        db.collection("LoaiProduct").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot d : queryDocumentSnapshots) {
                mlistLoaiSP.add(d.getString("tenloai"));
            }
            setupSpinner();
        });
    }

    private void setupAdapter() {
        adapter = new AdminProductAdapter(this, mlistProduct, pos -> {
            Product product = mlistProduct.get(pos);
            Intent intent = new Intent(this, AdminAddSPActivity.class);
            intent.putExtra("SP", product);
            startActivity(intent);
        });

        rcvAdminProduct.setLayoutManager(new LinearLayoutManager(this));
        rcvAdminProduct.setAdapter(adapter);
    }

    private void loadAllProducts() {
        db.collection("SanPham").get().addOnSuccessListener(queryDocumentSnapshots -> {

            mlistAllProduct.clear();

            for (QueryDocumentSnapshot d : queryDocumentSnapshots) {
                Product p = parseProduct(d);

                if (AdminProductValidator.isValidProduct(p)) {
                    mlistAllProduct.add(p);
                }
            }

            applyFilter("Tất cả");
        });
    }

    private void applyFilter(String category) {
        mlistProduct.clear();

        List<Product> filtered = AdminProductValidator
                .filterByCategory(mlistAllProduct, category);

        mlistProduct.addAll(filtered);
        adapter.notifyDataSetChanged();
    }

    private Product parseProduct(QueryDocumentSnapshot d) {
        Long giatien = d.getLong("giatien");
        Long soluong = d.getLong("soluong");
        Long type = d.getLong("type");

        return new Product(
                d.getId(),
                d.getString("tensp"),
                giatien != null ? giatien : 0L,
                d.getString("hinhanh"),
                d.getString("loaisp"),
                d.getString("mota"),
                soluong != null ? soluong : 0L,
                d.getString("hansudung"),
                type != null ? type : 0L,
                d.getString("trongluong")
        );
    }

    private void setupSpinner() {
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                mlistLoaiSP
        );

        spinnerLoaiSP.setAdapter(adapterSpinner);

        spinnerLoaiSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                category = mlistLoaiSP.get(i);
                applyFilter(category); // ❗ filter local
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void Event() {
        imgAddProduct.setOnClickListener(v -> {
            startActivity(new Intent(this, AdminAddSPActivity.class));
        });

        imgBackAdminProduct.setOnClickListener(v -> finish());
    }

    private void InitWidget() {
        rcvAdminProduct = findViewById(R.id.rcv_admin_product);
        imgAddProduct = findViewById(R.id.image_add_product);
        imgBackAdminProduct = findViewById(R.id.img_back_admin_product);
        spinnerLoaiSP = findViewById(R.id.spinner_loai_sp);
    }
}
