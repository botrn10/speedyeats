package vn.edu.ueh.speedyeats.View.Admin;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import vn.edu.ueh.speedyeats.Model.Product;
import vn.edu.ueh.speedyeats.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import com.squareup.picasso.Picasso;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;


public class AdminAddSPActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {






    private CircleImageView imgAddLoaiProduct;
    private ImageView btnAddBack, btnRefresh, btnSave;
    private EditText edtTenSP, edtGiatienSP, edtHansudungSP, edtTrongluongSP, edtSoluongSP, edtTypeSP, edtMotaSP;
    private ImageView imgAdd;
    private Button btnDanhmuc, btnDelete;
    private AppCompatButton btnEdit;
    private Spinner spinnerDanhMuc;
    private TextView tvTitle;


    private FirebaseFirestore db;
    private List<String> list;
    private Product product;
    private String image = "";
    private static final int LIBRARY_PICKER = 12312;
    private ProgressDialog dialog;
    private String loaisp = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_spactivity);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        InitWidget();
        Init();




        Event();
    }


    private void Event() {
        btnAddBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnDanhmuc.setOnClickListener(view -> spinnerDanhMuc.performClick());
        spinnerDanhMuc.setOnItemSelectedListener(this);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage();
            }
        });


        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtHansudungSP.setText("");
                edtTrongluongSP.setText("");
                edtTenSP.setText("");
                edtGiatienSP.setText("");
                edtTypeSP.setText("");
                edtMotaSP.setText("");
                edtSoluongSP.setText("");
                image = "";
                imgAdd.setImageResource(R.drawable.pl);
            }
        });


        // Nếu xóa bất kỳ 1 sản phẩm nào đó thì những hóa đơn có chứa sản phẩm đó cũng phải bị xóa hoặc dùng nhiều cách khác.
        // Ở đây lựa chọn xóa luôn hóa đơn chứa sản phẩm bị xóa.
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("SanPham").document(product.getId()).delete().addOnSuccessListener(unused -> {
                    db.collection("IDUser").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot q: queryDocumentSnapshots){
                                Log.d("checkiduser", q.getString("iduser"));


                                // Từ iduser mà ta có, lấy ra tất cả id_hoadon có id_product là KlUnpxIGoIFkHlvshil2
                                db.collection("ChitietHoaDon").document(q.getString("iduser")).
                                        collection("ALL").whereEqualTo("id_product", product.getId()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                for (QueryDocumentSnapshot d: queryDocumentSnapshots){
                                                    Log.d("checkidhoadon", d.getString("id_hoadon"));


                                                    // Từ id_hoadon mà ta có, thực hiện xóa id hóa đơn của bảng HoaDon
                                                    db.collection("HoaDon").document(d.getString("id_hoadon")).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(AdminAddSPActivity.this, "Xoá sản phẩm thành công!!!", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            }
                                        });
                            }


                        }
                    });


                    setResult(RESULT_OK);
                    finish();
                }).addOnFailureListener(e -> {
                    Toast.makeText(AdminAddSPActivity.this, "Xoá sản phẩm thất bại!!!", Toast.LENGTH_SHORT).show();
                });


            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    return;
                }
                try {
                    Product sp = new Product();
                    sp.setGiatien(Long.parseLong(edtGiatienSP.getText().toString()));
                    sp.setMota(edtMotaSP.getText().toString());
                    sp.setHansudung(edtHansudungSP.getText().toString());
                    sp.setType(Long.parseLong(edtTypeSP.getText().toString()));
                    sp.setTensp(edtTenSP.getText().toString());
                    sp.setSoluong(Long.parseLong(edtSoluongSP.getText().toString()));
                    sp.setTrongluong(edtTrongluongSP.getText().toString());
                    sp.setLoaisp(spinnerDanhMuc.getSelectedItem().toString());
                    sp.setHinhanh(image);


                    db.collection("SanPham").add(sp).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(@NonNull DocumentReference documentReference) {
                            Toast.makeText(AdminAddSPActivity.this, "Thành công!!!", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminAddSPActivity.this, "Thất bại!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    return;
                }
                try {
                    Product sp = new Product();
                    sp.setGiatien(Long.parseLong(edtGiatienSP.getText().toString()));
                    sp.setMota(edtMotaSP.getText().toString());
                    sp.setHansudung(edtHansudungSP.getText().toString());
                    sp.setType(Long.parseLong(edtTypeSP.getText().toString()));
                    sp.setTensp(edtTenSP.getText().toString());
                    sp.setSoluong(Long.parseLong(edtSoluongSP.getText().toString()));
                    sp.setTrongluong(edtTrongluongSP.getText().toString());
                    sp.setLoaisp(spinnerDanhMuc.getSelectedItem().toString());
                    sp.setHinhanh(image);
                    db.collection("SanPham").document(product.getId()).set(sp)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(@NonNull Void unused) {
                                    Toast.makeText(AdminAddSPActivity.this, "Cập nhật sản phẩm thành công!!!", Toast.LENGTH_SHORT).show();
                                    setResult(RESULT_OK);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AdminAddSPActivity.this, "Cập nhật sản phẩm thất bại!!!", Toast.LENGTH_SHORT).show();
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });




    }


    private void Init() {
        db = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        list.add("Chọn Danh mục");


        // Nhận data từ AdminProductActivity
        if (getIntent() != null && getIntent().hasExtra("SP")) {
            product = (Product) getIntent().getSerializableExtra("SP");
        }
        if (product != null) {


            edtTrongluongSP.setText(product.getTrongluong());
            edtMotaSP.setText(product.getMota());
            edtHansudungSP.setText(product.getHansudung());
            edtSoluongSP.setText(product.getSoluong() + "");
            edtGiatienSP.setText(product.getGiatien() + "");
            edtTenSP.setText(product.getTensp());
            edtTypeSP.setText(product.getType()+"");
            btnEdit.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.GONE);
            tvTitle.setText("Edit sản phẩm");
            if (!TextUtils.isEmpty(product.getHinhanh())) {
                Picasso.get().load(product.getHinhanh()).into(imgAdd);
                image = product.getHinhanh();
            }
        }


        db.collection("LoaiProduct").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot q : queryDocumentSnapshots) {
                    list.add(q.getString("tenloai"));
                    Log.d("TAG", "onSuccess: " + q.getString("tenloai"));
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(AdminAddSPActivity.this, android.R.layout.simple_list_item_1, list);
                spinnerDanhMuc.setAdapter(arrayAdapter);
                if (list.size() > 0) {
                    spinnerDanhMuc.setSelection(1);
                    if (product != null) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).equals(product.getLoaisp())) {
                                spinnerDanhMuc.setSelection(i);
                                break;
                            }
                        }
                    }
                }


            }
        });


    }


    private boolean validate() {
        if (TextUtils.isEmpty(image)) {
            Toast.makeText(this, "Vui lòng chọn hình ảnh", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edtGiatienSP.getText().toString())) {
            Toast.makeText(this, "Vui lòng nhập giá tiền", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edtTenSP.getText().toString())) {
            Toast.makeText(this, "Vui lòng nhập tên sản phẩm", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edtHansudungSP.getText().toString())) {
            Toast.makeText(this, "Vui lòng nhập hạn sử dụng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edtTrongluongSP.getText().toString())) {
            Toast.makeText(this, "Vui lòng nhập Trọng lượng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edtSoluongSP.getText().toString())) {
            Toast.makeText(this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edtTypeSP.getText().toString())) {
            Toast.makeText(this, "Vui lòng nhập type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(edtMotaSP.getText().toString())) {
            Toast.makeText(this, "Vui lòng nhập mô tả", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }


    private void InitWidget() {


        tvTitle = findViewById(R.id.tv_title);
        imgAddLoaiProduct = findViewById(R.id.img_add_loaiproduct);
        btnAddBack = findViewById(R.id.btn_add_back);
        btnRefresh = findViewById(R.id.btn_refresh);
        btnSave = findViewById(R.id.btn_save);
        edtTenSP = findViewById(R.id.edt_tensp);
        edtGiatienSP = findViewById(R.id.edt_giatiensp);
        edtHansudungSP = findViewById(R.id.edt_hansudungsp);
        edtTrongluongSP = findViewById(R.id.edt_trongluongsp);
        edtSoluongSP = findViewById(R.id.edt_soluongsp);
        edtTypeSP = findViewById(R.id.edt_typesp);
        edtMotaSP = findViewById(R.id.edt_motasp);
        imgAdd = findViewById(R.id.image_add);
        btnDanhmuc = findViewById(R.id.btn_danhmuc);
        btnDelete = findViewById(R.id.btn_delete);
        btnEdit = findViewById(R.id.btn_edit);
        spinnerDanhMuc = findViewById(R.id.spinner_danhmuc);


        // Dialog
        dialog = new ProgressDialog(this); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Upload image");
        dialog.setMessage("Uploading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
    }


    private void pickImage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    LIBRARY_PICKER);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Select Image"), LIBRARY_PICKER);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // pick image after request permission success
            pickImage();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == LIBRARY_PICKER && resultCode == RESULT_OK && null != data) {
            try {


                dialog.show();
                Uri uri = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] datas = baos.toByteArray();
                String filename = System.currentTimeMillis() + "";
                StorageReference storageReference;
                storageReference = FirebaseStorage.getInstance("gs://doan-dc57a.appspot.com/").getReference();
                storageReference.child("Profile").child(filename + ".jpg").putBytes(datas).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        if (taskSnapshot.getTask().isSuccessful()) {
                            storageReference.child("Profile").child(filename + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(@NonNull Uri uri) {
                                    imgAdd.setImageBitmap(bitmap);
                                    image = uri.toString();
                                }
                            });
                            Toast.makeText(AdminAddSPActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
            } catch (FileNotFoundException e) {
                Log.d("CHECKED", e.getMessage());
                dialog.dismiss();
            }


        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (position > 0) {
            btnDanhmuc.setText(spinnerDanhMuc.getSelectedItem().toString());
            String s = list.get(position);
            loaisp = s;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }
}


