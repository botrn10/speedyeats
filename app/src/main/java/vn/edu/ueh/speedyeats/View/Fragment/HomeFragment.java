package vn.edu.ueh.speedyeats.View.Fragment;


import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;


import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import vn.edu.ueh.speedyeats.View.Adapter.BannerAdapter;
import vn.edu.ueh.speedyeats.View.Adapter.LoaiProductAdapter;
import vn.edu.ueh.speedyeats.View.Adapter.ProductAdapter;
import vn.edu.ueh.speedyeats.Model.LoaiProduct;
import vn.edu.ueh.speedyeats.Model.Product;
import vn.edu.ueh.speedyeats.R;
import vn.edu.ueh.speedyeats.View.CartActivity;
import vn.edu.ueh.speedyeats.View.CategoryActivity;
import vn.edu.ueh.speedyeats.View.DetailSPActivity;
import vn.edu.ueh.speedyeats.View.SearchActivity;
import vn.edu.ueh.speedyeats.my_interface.IClickCTHD;
import vn.edu.ueh.speedyeats.my_interface.IClickLoaiProduct;
import vn.edu.ueh.speedyeats.my_interface.IClickOpenBottomSheet;
import vn.edu.ueh.speedyeats.Util.NetworkUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;


import org.jetbrains.annotations.NotNull;


import java.util.ArrayList;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {


    private SwipeRefreshLayout swipeHome;
    private Product product;


    // Data Product
    private  ArrayList<Product> arr_sp_gr, arr_sp_pz, arr_sp_kt, arr_sp_hbg, arr_sp_sl;
    private ProductAdapter productGRAdapter, productPZAdapter, productKTAdapter, productHBGAdapter, productSLAdapter;
    private RecyclerView rcvGaran, rcvPizza,rcvKhoaitay,rcvHamburger,rcvSalad;


    // Banner
    private ArrayList<String> arrayList;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private BannerAdapter bannerAdapter;


    // Infor User
    private Toolbar toolbarHome;
    private View view;
    private CircleImageView cirAvatarHome;
    private TextView tvNameHome, tvEmailHome;
    public static final int MY_REQUEST_CODE = 10;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();


    // Loai Product
    private RecyclerView rcvLoaiProduct;
    private LoaiProductAdapter loaiProductAdapter;
    private List<LoaiProduct> mlistproduct;


    //Search Data
    private EditText edtSearchHome;


    private ImageView imgHomeMessage, imgHomeCart;


    private TextView tvNumberCart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbarHome);
        setHasOptionsMenu(true);
        InitWidget();
        Event();
        if (NetworkUtil.isNetworkConnected(getContext())){
            LoadInfor();
            Banner();
            InitProduct();
            GetDataGaran();
            GetDataPizza();
            GetDataKhoaitay();
            GetDataHamburger();
            GetDataSalad();
            LoadFavorite();
        }


        return view;
    }


    private void LoadFavorite() {
        firestore.collection("Favorite").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("ALL")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.size() > 0){
                            int num = 0;
                            Log.d("numCart", "Number: " + queryDocumentSnapshots.size());
                            for (QueryDocumentSnapshot q : queryDocumentSnapshots){
                                num++;
                                tvNumberCart.setVisibility(View.VISIBLE);
                                tvNumberCart.setText(num+"");
                            }
                        } else {
                            tvNumberCart.setVisibility(View.GONE);
                        }
                    }
                });
    }


    private void replace(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NotNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_two);
        View view = MenuItemCompat.getActionView(menuItem);


        CircleImageView cirToolbarProfile = view.findViewById(R.id.cir_toolbar_profile);
        firestore.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Profile")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                            if(documentSnapshot!=null){
                                try{
                                    if(documentSnapshot.getString("avatar").length()>0){
                                        Picasso.get().load(documentSnapshot.getString("avatar").trim()).into(cirToolbarProfile);
                                    }
                                }catch (Exception e){
                                    Log.d("ERROR",e.getMessage());
                                }
                            }
                        }
                    }
                });
        cirToolbarProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Menu two clicked", Toast.LENGTH_SHORT).show();
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_one:
                Toast.makeText(getContext(), "Menu one", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_two:
                replace(new ProfileFragment());
                break;


        }
        return super.onOptionsItemSelected(item);
    }


    private void Event() {


        edtSearchHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });






        imgHomeCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
            }
        });


        swipeHome.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeHome.setRefreshing(false);
                        LoadFavorite();
//                        getFragmentManager().beginTransaction().detach(HomeFragment.this).attach(HomeFragment.this).commit();
//                        Toast.makeText(getContext(), "Swipe", Toast.LENGTH_SHORT).show();
                    }
                }, 500);


            }
        });


    }


    private void InitProduct() {
        arr_sp_gr = new ArrayList<>();
        arr_sp_pz = new ArrayList<>();
        arr_sp_kt = new ArrayList<>();
        arr_sp_hbg = new ArrayList<>();
        arr_sp_sl = new ArrayList<>();

    }
    // Danh sách Product
    public void GetDataGaran() {
        firestore.collection("SanPham")
                .whereEqualTo("type", 1)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.size() > 0) {
                            for (QueryDocumentSnapshot d : queryDocumentSnapshots) {
                                Long giatien = d.getLong("giatien");
                                if (giatien != null) {
                                    arr_sp_gr.add(new Product(
                                            d.getId(),
                                            d.getString("tensp"),
                                            giatien,
                                            d.getString("hinhanh"),
                                            d.getString("loaisp"),
                                            d.getString("mota"),
                                            d.getLong("soluong"),
                                            d.getString("hansudung"),
                                            d.getLong("type"),
                                            d.getString("trongluong")
                                    ));
                                }
                            }

                            // Cập nhật Adapter và RecyclerView
                            productGRAdapter = new ProductAdapter(getContext(), arr_sp_gr, 1, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
                                    product = arr_sp_gr.get(position);
                                    TruyenData();
                                }
                            });

                            rcvGaran.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                            rcvGaran.setAdapter(productGRAdapter);
                        }
                    }
                });
    }
    public void GetDataPizza(){
        firestore.collection("SanPham")
                .whereEqualTo("type", 2)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size() > 0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                Long giatien = d.getLong("giatien");
                                String hinhanh = d.getString("hinhanh");


                                // Kiểm tra nếu "giatien" không phải là null
                                if (giatien != null) {
                                    // Nếu có giá trị, thêm sản phẩm vào danh sách
                                    arr_sp_pz.add(new Product(d.getId(), d.getString("tensp"),
                                            giatien, hinhanh,
                                            d.getString("loaisp"), d.getString("mota"),
                                            d.getLong("soluong"), d.getString("hansudung"),
                                            d.getLong("type"), d.getString("trongluong")));
                                } else {
                                    // Nếu "giatien" là null, ghi log và không thêm sản phẩm
                                    Log.e("HomeFragment", "giatien is null for product: " + d.getId());
                                }
                            }
                            productPZAdapter = new ProductAdapter(getContext(), arr_sp_pz, 2, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
                                    product = arr_sp_pz.get(position);
                                    TruyenData();
                                }
                            });

                            rcvPizza.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                            rcvPizza.setAdapter(productPZAdapter);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("HomeFragment", "Error getting documents: ", e);
                    }
                });
    }




    // Sản phẩm nổi bật
    public void GetDataKhoaitay() {
        firestore.collection("SanPham")
                .whereEqualTo("type", 3)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.size() > 0) {
                            for (QueryDocumentSnapshot d : queryDocumentSnapshots) {
                                // Lấy giá trị giatien và kiểm tra null
                                Long giatien = d.getLong("giatien");
                                long giatienValue = (giatien != null) ? giatien.longValue() : 0L; // Gán giá trị mặc định là 0 nếu null


                                // Tạo đối tượng Product và thêm vào danh sách
                                arr_sp_kt.add(new Product(
                                        d.getId(),
                                        d.getString("tensp"),
                                        giatienValue,
                                        d.getString("hinhanh"),
                                        d.getString("loaisp"),
                                        d.getString("mota"),
                                        d.getLong("soluong"),
                                        d.getString("hansudung"),
                                        d.getLong("type"),
                                        d.getString("trongluong")
                                ));
                            }


                            // Cập nhật Adapter và RecyclerView
                            productKTAdapter = new ProductAdapter(getContext(), arr_sp_kt, 3, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {
                                    // Do something
                                    product = arr_sp_kt.get(position);
                                    TruyenData();
                                }
                            });
                            rcvKhoaitay.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
                            rcvKhoaitay.setAdapter(productKTAdapter);
                        }
                    }
                });
    }


    // Sản phẩm đồ uống
    public  void  GetDataHamburger(){
        firestore.collection("SanPham").
                whereEqualTo("type",4).
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                // lấy id trên firebase
                                arr_sp_hbg.add(new Product(d.getId(),d.getString("tensp"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("loaisp"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("hansudung"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            productHBGAdapter = new ProductAdapter(getContext(), arr_sp_hbg, 4, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {


                                    // Do something
                                    product = arr_sp_hbg.get(position);
                                    TruyenData();
                                }
                            });
                            rcvHamburger.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                            rcvHamburger.setAdapter(productHBGAdapter);
                        }


                    }
                });
    }


    // Sản phẩm Hàn Quốc
    public  void  GetDataSalad(){
        firestore.collection("SanPham").
                whereEqualTo("type",5).
                get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                                // lấy id trên firebase
                                arr_sp_sl.add(new Product(d.getId(),d.getString("tensp"),
                                        d.getLong("giatien"),d.getString("hinhanh"),
                                        d.getString("loaisp"),d.getString("mota"),
                                        d.getLong("soluong"),d.getString("hansudung"),
                                        d.getLong("type"),d.getString("trongluong")));
                            }
                            productSLAdapter = new ProductAdapter(getContext(), arr_sp_sl, 5, new IClickOpenBottomSheet() {
                                @Override
                                public void onClickOpenBottomSheet(int position) {


                                    // Do something
                                    product = arr_sp_sl.get(position);
                                    TruyenData();
                                }
                            });
                            rcvSalad.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
                            rcvSalad.setAdapter(productSLAdapter);
                        }


                    }
                });
    }








    private void Banner() {
        arrayList = new ArrayList<>();
        firestore= FirebaseFirestore.getInstance();
        firestore.collection("Banner").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                    arrayList.add(d.getString("hinhanh"));
                }
                bannerAdapter = new BannerAdapter(getContext(), arrayList, new IClickCTHD() {
                    @Override
                    public void onClickCTHD(int pos) {
//                        String s = arrayList.get(pos);
//                        Toast.makeText(getContext(), "clicked: " + s, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), SearchActivity.class);
                        startActivity(intent);
                    }
                });
                viewPager.setAdapter(bannerAdapter);
                circleIndicator.setViewPager(viewPager);
                bannerAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    //3s sang 1 banner khác
                    public void run() {
                        int k=viewPager.getCurrentItem();
                        if(k>=arrayList.size()-1){
                            k  = 0;
                        }else{
                            k++;
                        }
                        handler.postDelayed(this,3000);
                        viewPager.setCurrentItem(k,true);


                    }
                },3000);


            }
        });
    }


    private void LoadInfor() {
        tvEmailHome.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        firestore.collection("User").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("Profile")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(@NonNull QuerySnapshot queryDocumentSnapshots) {
                        if(queryDocumentSnapshots.size()>0){
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                            if(documentSnapshot!=null){
                                try{
                                    tvNameHome.setText(documentSnapshot.getString("hoten").length()>0 ?
                                            documentSnapshot.getString("hoten") : "");


                                    if(documentSnapshot.getString("avatar").length()>0){
                                        Picasso.get().load(documentSnapshot.getString("avatar").trim()).into(cirAvatarHome);
                                    }
                                }catch (Exception e){
                                    Log.d("ERROR",e.getMessage());
                                }
                            }
                        }
                    }
                });
    }


    private void InitWidget() {
        imgHomeCart = view.findViewById(R.id.img_home_cart);
        swipeHome = view.findViewById(R.id.swipe_home);
        tvNumberCart = view.findViewById(R.id.tv_number_cart);
        edtSearchHome = view.findViewById(R.id.edt_search_home);


        toolbarHome = view.findViewById(R.id.toolbar_home);
        cirAvatarHome = view.findViewById(R.id.cir_avatar_home);
        tvNameHome = view.findViewById(R.id.tv_name_home);
        tvEmailHome = view.findViewById(R.id.tv_email_home);
        viewPager = view.findViewById(R.id.viewpager);
        circleIndicator = view.findViewById(R.id.circle_indicator);


        rcvLoaiProduct = view.findViewById(R.id.rcv_loai_product);
        loaiProductAdapter = new LoaiProductAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvLoaiProduct.setLayoutManager(linearLayoutManager);
        loaiProductAdapter.setData(getListLoaiProduct(), new IClickLoaiProduct() {
            @Override
            public void onClickItemLoaiProduct(int position) {
                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra("loaiproduct", position);
                startActivity(intent);
            }
        });

        rcvGaran = view.findViewById(R.id.rcv_Garan);
        rcvPizza = view.findViewById(R.id.rcv_Pizza);
        rcvKhoaitay = view.findViewById(R.id.rcv_Khoaitay);
        rcvHamburger = view.findViewById(R.id.rcv_Hamburger);
        rcvSalad = view.findViewById(R.id.rcv_Salad);




    }


    private List<LoaiProduct> getListLoaiProduct(){
        mlistproduct = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("LoaiProduct").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot d : queryDocumentSnapshots){
                    mlistproduct.add(new LoaiProduct(d.getString("tenloai"), d.getString("hinhanhloai")));
                    rcvLoaiProduct.setAdapter(loaiProductAdapter);
                }


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("error", "Lỗi: " + e);
                Toast.makeText(getContext(), "Lỗi" + e, Toast.LENGTH_SHORT).show();
            }
        });


        return mlistproduct;
    }


    private void TruyenData(){
        Intent intent = new Intent(getContext(), DetailSPActivity.class);
        intent.putExtra("search", product);
        startActivity(intent);
    }


    public TextView getTvNameHome(){
        return tvNameHome;
    }
    public TextView getTvEmailHome(){
        return tvEmailHome;
    }
    public CircleImageView getCirAvatarHome(){
        return cirAvatarHome;
    }
}