package vn.edu.ueh.speedyeats.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import vn.edu.ueh.speedyeats.View.Adapter.CategoryAdapter;
import vn.edu.ueh.speedyeats.Model.Product;
import vn.edu.ueh.speedyeats.R;
import vn.edu.ueh.speedyeats.my_interface.IClickOpenBottomSheet;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;


public class CategoryActivity extends AppCompatActivity {
    private ImageView imgBack;
    private TextView tvCategory;
    private EditText edtSearch;
    private RecyclerView rcvCategory;
    private CategoryAdapter categoryAdapter;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private Product product;
    private ArrayList<Product> arr_khoaitaychien, arr_hamburger,arr_garan, arr_pizza, arr_salad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        InitWidget();
        Init();
        Event();
    }


    private void Init() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("loaiproduct", 1);
        Log.d("zxc", position + "");
        switch (position){
            case 0:
                tvCategory.setText("Khoai tây chiên");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Khoai Tây Chiên").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.size()>0){
                                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                        // lấy id trên firebase
                                        arr_khoaitaychien.add(new Product(d.getId(),d.getString("tensp"),
                                                d.getLong("giatien"),d.getString("hinhanh"),
                                                d.getString("loaisp"),d.getString("mota"),
                                                d.getLong("soluong"),d.getString("hansudung"),
                                                d.getLong("type"),d.getString("trongluong")));
                                    }
                                    categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_khoaitaychien, new IClickOpenBottomSheet() {
                                        @Override
                                        public void onClickOpenBottomSheet(int position) {


                                            product = arr_khoaitaychien.get(position);
                                            SendData();
                                        }
                                    });
                                    rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                                    // Thêm đường phân cách giữa các dòng
                                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                                    rcvCategory.addItemDecoration(itemDecoration);
                                    rcvCategory.setAdapter(categoryAdapter);
                                }


                            }
                        });
                break;
            case 1:
                tvCategory.setText("Hamburger");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Hamburger").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.size()>0){
                                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                        // lấy id trên firebase
                                        arr_hamburger.add(new Product(d.getId(),d.getString("tensp"),
                                                d.getLong("giatien"),d.getString("hinhanh"),
                                                d.getString("loaisp"),d.getString("mota"),
                                                d.getLong("soluong"),d.getString("hansudung"),
                                                d.getLong("type"),d.getString("trongluong")));
                                    }
                                    categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_hamburger, new IClickOpenBottomSheet() {
                                        @Override
                                        public void onClickOpenBottomSheet(int position) {
//                                    setBottomSheetDialog();
                                            product = arr_hamburger.get(position);
                                            SendData();
                                        }
                                    });
                                    rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                                    // Thêm đường phân cách giữa các dòng
                                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                                    rcvCategory.addItemDecoration(itemDecoration);
                                    rcvCategory.setAdapter(categoryAdapter);
                                }


                            }
                        });
                break;
            case 2:
                tvCategory.setText("Gà rán");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Gà rán").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.size()>0){
                                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                        // lấy id trên firebase
                                        arr_garan.add(new Product(d.getId(),d.getString("tensp"),
                                                d.getLong("giatien"),d.getString("hinhanh"),
                                                d.getString("loaisp"),d.getString("mota"),
                                                d.getLong("soluong"),d.getString("hansudung"),
                                                d.getLong("type"),d.getString("trongluong")));
                                    }
                                    categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_garan, new IClickOpenBottomSheet() {
                                        @Override
                                        public void onClickOpenBottomSheet(int position) {
                                            product = arr_garan.get(position);
                                            SendData();
                                        }
                                    });
                                    rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                                    // Thêm đường phân cách giữa các dòng
                                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                                    rcvCategory.addItemDecoration(itemDecoration);
                                    rcvCategory.setAdapter(categoryAdapter);
                                }


                            }
                        });
                break;
            case 3:
                tvCategory.setText("Pizza");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Pizza").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.size()>0){
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


                                        // Kiểm tra dữ liệu hợp lệ trước khi thêm vào danh sách
                                        if (loaisp != null && giatien != null && hinhanh != null && tensp != null) {
                                            // Thêm vào danh sách nếu dữ liệu hợp lệ
                                            arr_pizza.add(new Product(d.getId(), tensp, giatien, hinhanh, loaisp, mota, soluong, hansudung, type, trongluong));
                                        } else {
                                            // Log lỗi nếu dữ liệu không hợp lệ
                                            Log.e("FirestoreError", "Invalid product data: " + tensp);
                                        }
                                    }
                                    categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_pizza, new IClickOpenBottomSheet() {
                                        @Override
                                        public void onClickOpenBottomSheet(int position) {
                                            product = arr_pizza.get(position);
                                            SendData();
                                        }
                                    });
                                    rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                                    // Thêm đường phân cách giữa các dòng
                                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                                    rcvCategory.addItemDecoration(itemDecoration);
                                    rcvCategory.setAdapter(categoryAdapter);
                                }


                            }
                        });
                break;
            case 4:
                tvCategory.setText("Salad");
                firestore.collection("SanPham").
                        whereEqualTo("loaisp","Salad").
                        get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                                if(queryDocumentSnapshots.size()>0){
                                    for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                        // lấy id trên firebase
                                        arr_salad.add(new Product(d.getId(),d.getString("tensp"),
                                                d.getLong("giatien"),d.getString("hinhanh"),
                                                d.getString("loaisp"),d.getString("mota"),
                                                d.getLong("soluong"),d.getString("hansudung"),
                                                d.getLong("type"),d.getString("trongluong")));
                                    }
                                    categoryAdapter = new CategoryAdapter(CategoryActivity.this, arr_salad, new IClickOpenBottomSheet() {
                                        @Override
                                        public void onClickOpenBottomSheet(int position) {
                                            product = arr_salad.get(position);
                                            SendData();
                                        }
                                    });
                                    rcvCategory.setLayoutManager(new LinearLayoutManager(CategoryActivity.this,RecyclerView.VERTICAL,false));
                                    // Thêm đường phân cách giữa các dòng
                                    RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(CategoryActivity.this, DividerItemDecoration.VERTICAL);
                                    rcvCategory.addItemDecoration(itemDecoration);
                                    rcvCategory.setAdapter(categoryAdapter);
                                }


                            }
                        });
                break;


        }
    }


    private void SendData(){
        Intent intent = new Intent(CategoryActivity.this, DetailSPActivity.class);
        intent.putExtra("search", product);
        startActivity(intent);
    }


    private void Event() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void InitWidget() {
        imgBack = findViewById(R.id.img_back);
        tvCategory = findViewById(R.id.tv_category);
        edtSearch = findViewById(R.id.edt_search);
        rcvCategory = findViewById(R.id.rcv_category);


        arr_khoaitaychien = new ArrayList<>();
        arr_hamburger = new ArrayList<>();
        arr_pizza = new ArrayList<>();
        arr_salad = new ArrayList<>();
        arr_garan = new ArrayList<>();




    }


}

