package vn.edu.ueh.speedyeats.View.Admin;

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

public class AdminProductActivity extends AppCompatActivity {

    private RecyclerView rcvAdminProduct;
    private AppCompatImageView imgAddProduct;
    private ArrayList<Product> mlistProduct;
    private AdminProductAdapter adapter;
    private ImageView imgBackAdminProduct;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private Spinner spinnerLoaiSP;
    private ArrayList<String> mlistLoaiSP;
    private String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);

        InitWidget();
        Init();
        Event();
        HandleGetDataAllProduct(); // Load all products when activity starts
    }

    private void Event() {
        imgAddProduct.setOnClickListener(view -> {
            Intent intent = new Intent(AdminProductActivity.this, AdminAddSPActivity.class);
            startActivity(intent);
        });

        imgBackAdminProduct.setOnClickListener(view -> finish());
    }

    private void HandleGetDataAllProduct() {
        db.collection("SanPham").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                mlistProduct.clear(); // Clear previous data

                for (QueryDocumentSnapshot d : queryDocumentSnapshots) {
                    String tensp = d.getString("tensp");
                    Long giatien = d.getLong("giatien");
                    String hinhanh = d.getString("hinhanh");
                    String loaisp = d.getString("loaisp");
                    String mota = d.getString("mota");
                    Long soluong = d.getLong("soluong");
                    String hansudung = d.getString("hansudung");
                    Long type = d.getLong("type");
                    String trongluong = d.getString("trongluong");

                    // Kiểm tra null và gán giá trị mặc định nếu cần
                    giatien = (giatien != null) ? giatien : 0L; // Gán giá trị mặc định là 0 nếu giatien là null
                    soluong = (soluong != null) ? soluong : 0L; // Gán giá trị mặc định là 0 nếu soluong là null
                    type = (type != null) ? type : 0L; // Gán giá trị mặc định là 0 nếu type là null

                    // Thêm sản phẩm vào danh sách
                    mlistProduct.add(new Product(d.getId(), tensp, giatien, hinhanh, loaisp, mota, soluong, hansudung, type, trongluong));
                }

                // Cập nhật adapter
                setupAdapter();
            }
        });
    }

    private void setupAdapter() {
        adapter = new AdminProductAdapter(AdminProductActivity.this, mlistProduct, new IClickCTHD() {
            @Override
            public void onClickCTHD(int pos) {
                Product product = mlistProduct.get(pos);
                Intent intent = new Intent(AdminProductActivity.this, AdminAddSPActivity.class);
                intent.putExtra("SP", product);
                startActivity(intent);
            }
        });

        rcvAdminProduct.setLayoutManager(new LinearLayoutManager(AdminProductActivity.this, RecyclerView.VERTICAL, false));
        rcvAdminProduct.setAdapter(adapter);
    }

    private void Init() {
        mlistProduct = new ArrayList<>();
        mlistLoaiSP = new ArrayList<>();
        mlistLoaiSP.add("Tất cả");

        db.collection("LoaiProduct").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot d : queryDocumentSnapshots) {
                    mlistLoaiSP.add(d.getString("tenloai"));
                }
                setupSpinner();
            }
        });
    }

    private void setupSpinner() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AdminProductActivity.this, R.layout.support_simple_spinner_dropdown_item, mlistLoaiSP);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spinnerLoaiSP.setAdapter(arrayAdapter);
        spinnerLoaiSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = mlistLoaiSP.get(i);
                mlistProduct.clear(); // Clear current product list

                if (category.equals("Tất cả")) {
                    HandleGetDataAllProduct(); // Load all products
                } else {
                    loadProductsByCategory(category); // Load products by category
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Do nothing
            }
        });
    }

    private void loadProductsByCategory(String category) {
        db.collection("SanPham").whereEqualTo("loaisp", category).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                mlistProduct.clear(); // Clear previous data

                for (QueryDocumentSnapshot d : queryDocumentSnapshots) {
                    String tensp = d.getString("tensp");
                    Long giatien = d.getLong("giatien");
                    String hinhanh = d.getString("hinhanh");
                    String loaisp = d.getString("loaisp");
                    String mota = d.getString("mota");
                    Long soluong = d.getLong("soluong");
                    String hansudung = d.getString("hansudung");
                    Long type = d.getLong("type");
                    String trongluong = d.getString("trongluong");

                    // Kiểm tra null và gán giá trị mặc định nếu cần
                    giatien = (giatien != null) ? giatien : 0L; // Gán giá trị mặc định là 0 nếu giatien là null
                    soluong = (soluong != null) ? soluong : 0L; // Gán giá trị mặc định là 0 nếu soluong là null
                    type = (type != null) ? type : 0L; // Gán giá trị mặc định là 0 nếu type là null

                    // Thêm sản phẩm vào danh sách
                    mlistProduct.add(new Product(d.getId(), tensp, giatien, hinhanh, loaisp, mota, soluong, hansudung, type, trongluong));
                }

                // Cập nhật adapter
                setupAdapter(); // Cập nhật adapter sau khi lấy sản phẩm
            }
        });
    }

    private void InitWidget() {
        rcvAdminProduct = findViewById(R.id.rcv_admin_product);
        imgAddProduct = findViewById(R.id.image_add_product);
        imgBackAdminProduct = findViewById(R.id.img_back_admin_product);
        spinnerLoaiSP = findViewById(R.id.spinner_loai_sp);
    }
}
