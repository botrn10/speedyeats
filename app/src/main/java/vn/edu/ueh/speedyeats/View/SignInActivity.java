package vn.edu.ueh.speedyeats.View;

import androidx.annotation.NonNull;
import vn.edu.ueh.speedyeats.Util.AuthValidator;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import vn.edu.ueh.speedyeats.MainActivity;
import vn.edu.ueh.speedyeats.Model.Admin;
import vn.edu.ueh.speedyeats.R;
import vn.edu.ueh.speedyeats.View.Admin.AdminHomeActivity;
import vn.edu.ueh.speedyeats.Util.MyReceiver;
import vn.edu.ueh.speedyeats.Util.NetworkUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import android.util.Log;
import com.google.firebase.FirebaseApp;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignInActivity extends AppCompatActivity {

    private Button btnDangNhap, btnDangKy;
    private EditText edtEmailUser, edtMatKhauUser;
    private TextView tvForgotPassword;
    private CircleImageView cirDangNhapFacebook, cirDangNhapGoogle;
    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    private String sosanh;
    private ProgressDialog progressDialog;

    // Check Internet
    private BroadcastReceiver MyReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        InitWidget();
        MyReceiver = new MyReceiver();      // Check Internet
        broadcastIntent();                  // Check Internet
        if (NetworkUtil.isNetworkConnected(this)){
            Event();

        }

    }

    private void Event() {
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                progressDialog.setContentView(R.layout.layout_loading);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                String strEmail = edtEmailUser.getText().toString().trim();
                String strMatKhau = edtMatKhauUser.getText().toString().trim();

                // thay if cũ
                if (!AuthValidator.isValidLoginInput(strEmail, strMatKhau)) {
                    progressDialog.dismiss();

                    if (AuthValidator.isEmailEmpty(strEmail)) {
                        Toast.makeText(SignInActivity.this, "Bạn chưa nhập tài khoản", Toast.LENGTH_SHORT).show();
                    } else if (AuthValidator.isPasswordEmpty(strMatKhau)) {
                        Toast.makeText(SignInActivity.this, "Bạn chưa nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    }

                    return;
                }

                // Firebase + Admin check
                ArrayList<Admin> arrayList = new ArrayList<>();

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("Admin").get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            for (QueryDocumentSnapshot q : queryDocumentSnapshots) {
                                arrayList.add(new Admin(q.getString("username"), q.getString("pass")));
                            }

                            int tk = 0;

                            for (Admin admin : arrayList) {
                                if (strEmail.equals(admin.getUsername()) &&
                                        strMatKhau.equals(admin.getPassword())) {
                                    tk = 1;
                                    break;
                                } else {
                                    tk = 2;
                                }
                            }

                            switch (tk) {
                                case 1:
                                    progressDialog.dismiss();
                                    startActivity(new Intent(SignInActivity.this, AdminHomeActivity.class));
                                    finish();
                                    break;

                                case 2:
                                    FirebaseAuth auth = FirebaseAuth.getInstance();
                                    auth.signInWithEmailAndPassword(strEmail, strMatKhau)
                                            .addOnCompleteListener(task -> {
                                                progressDialog.dismiss();

                                                if (task.isSuccessful()) {
                                                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                                    finishAffinity();
                                                } else {
                                                    Toast.makeText(SignInActivity.this,
                                                            "Sai tài khoản hoặc mật khẩu",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                    break;
                            }
                        })
                        .addOnFailureListener(e -> {
                            progressDialog.dismiss();
                            Log.e("FIRESTORE", "Error", e);
                            Toast.makeText(SignInActivity.this,
                                    "Lỗi Firestore: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        });
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignUp = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intentSignUp);
            }
        });


    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onBackPressed() {
        progressDialog.dismiss();
        // Sự kiện nhấn back 2 lần để out app
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();

    }

    private void InitWidget() {
        btnDangNhap = findViewById(R.id.btn_dangnhap);
        btnDangKy = findViewById(R.id.btn_dangky);
        edtEmailUser = findViewById(R.id.edt_email_user);
        edtMatKhauUser = findViewById(R.id.edt_matkhau_user);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);

        progressDialog = new ProgressDialog(this);
    }

    // Check Internet
    public void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    // Check Internet
    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(MyReceiver);
    }
}