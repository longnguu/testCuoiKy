package com.example.demotmdt.UIFragment;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import android.app.ActionBar;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.SearchView;

import com.example.demotmdt.Adapter.DanhMucAdapter;
import com.example.demotmdt.Adapter.PhotoAdapter;
import com.example.demotmdt.Adapter.SanPhamAdapter;
import com.example.demotmdt.Class.DanhMuc;
import com.example.demotmdt.Class.Photo;
import com.example.demotmdt.Class.SanPham;
import com.example.demotmdt.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    ViewPager viewPager;
    CircleIndicator circleIndicator,circleIndicator1;
    PhotoAdapter photoAdapter;
    List<Photo> mListPhoto;
    Timer mTimer;
    RecyclerView recyclerView,recyclerViewSP;
    DanhMucAdapter danhMucAdapter;
    ArrayList<DanhMuc> danhMucs;
    public static ArrayList<SanPham> sanPhams;
    public static SanPhamAdapter sanPhamAdapter;
    GestureDetector gestureDetector;
    ScrollView scrollView;
    CardView topCV;
    SearchView topSV;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            getActivity().getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        viewPager = view.findViewById(R.id.ViewPager    );
        circleIndicator = view.findViewById(R.id.circleindicator);
        mListPhoto = getListPhoto();
        photoAdapter = new PhotoAdapter(getContext(),mListPhoto);
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        recyclerView = view.findViewById(R.id.list_danhmuc);
        recyclerViewSP=view.findViewById(R.id.list_sanpham);
        scrollView = view.findViewById(R.id.scrollHomeFg);
        topCV = getActivity().findViewById(R.id.topnav);
        topSV = getActivity().findViewById(R.id.topSearchView);
        recyclerView.setHorizontalScrollBarEnabled(true);
        danhMucs = new ArrayList<DanhMuc>();
        //Tự phát sinh 50 dữ liệu mẫu
            danhMucs.add(new DanhMuc("Khung giờ săn sale" , "",R.drawable.ic_baseline_favorite_24));
            danhMucs.add(new DanhMuc("Gì cũng rẻ" , "",R.drawable.ic_baseline_favorite_24));
            danhMucs.add(new DanhMuc("Mã giảm giá" , "",R.drawable.ic_baseline_favorite_24));
            danhMucs.add(new DanhMuc("Freeship" , "",R.drawable.ic_baseline_favorite_24));
            danhMucs.add(new DanhMuc("Săn xu mỗi ngày" , "",R.drawable.ic_baseline_favorite_24));
            danhMucs.add(new DanhMuc("Săn thưởng" , "",R.drawable.ic_baseline_favorite_24));
            danhMucs.add(new DanhMuc("Hoàn xu" , "",R.drawable.ic_baseline_favorite_24));
            danhMucs.add(new DanhMuc("Thương hiệu tốt giá tốt" , "",R.drawable.ic_baseline_favorite_24));
            danhMucs.add(new DanhMuc("Hàng quốc tế" , "",R.drawable.ic_baseline_favorite_24));
            danhMucs.add(new DanhMuc("Nạp thẻ" , "",R.drawable.ic_baseline_favorite_24));
            danhMucs.add(new DanhMuc("Dịch vụ" , "",R.drawable.ic_baseline_favorite_24));
            danhMucs.add(new DanhMuc("Ưu đãi thành viên" , "",R.drawable.ic_baseline_favorite_24));
            danhMucs.add(new DanhMuc("Ưu đãi đối tác" , "",R.drawable.ic_baseline_favorite_24));
            danhMucs.add(new DanhMuc("Chơi là trúng" , "",R.drawable.ic_baseline_favorite_24));
            sanPhams=new ArrayList<>();
        databaseReference.child("SanPham").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sanPhams.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                        String ten= dataSnapshot1.child("ten").getValue(String.class);
                        SanPham sanPham = new SanPham(ten);
                        sanPham.setImg(dataSnapshot1.child("img").getValue(String.class));
                        sanPham.setMaSP(dataSnapshot1.getKey());
                        sanPham.setUID(dataSnapshot.getKey());
                        sanPham.setMota(dataSnapshot1.child("mota").getValue(String.class));
                        sanPham.setGia(dataSnapshot1.child("gia").getValue(String.class));
                        sanPham.setDaBan("0");
                        sanPhams.add(sanPham);
                    }
                    sanPhamAdapter.updateSanPham(sanPhams);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                    int color = R.color.white10;
                    if (i1<20)
                        color=R.color.white10;
                        else if(i1<40)
                            color=R.color.white20;
                            else if(i1<60)
                                color=R.color.white30;
                                else if(i1<80)
                                    color=R.color.white40;
                                    else if (i1<100)
                                        color=R.color.white50;
                                        else if (i1<120)
                                            color=R.color.white60;
                                            else if (i1<140)
                                                color=R.color.white70;
                                                else if(i1<160)
                                                    color=R.color.white80;
                                                    else if (i1<180)
                                                        color=R.color.white90;
                                                        else
                                                            color=R.color.white100;
                    Window window = getActivity().getWindow();
                    window.setStatusBarColor(getActivity().getResources().getColor(color));
                    topCV.setCardBackgroundColor(getActivity().getResources().getColor(color));
                    topSV.setBackgroundColor(getActivity().getResources().getColor(color));
                }
            });
        }
        danhMucAdapter = new DanhMucAdapter(danhMucs, getContext());

        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),2,RecyclerView.HORIZONTAL,false);

        recyclerView.setAdapter(danhMucAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        sanPhamAdapter=new SanPhamAdapter(sanPhams,getContext());
        linearLayoutManager = new GridLayoutManager(getContext(),2,RecyclerView.VERTICAL,false);
        recyclerViewSP.setAdapter(sanPhamAdapter);
        recyclerViewSP.setLayoutManager(linearLayoutManager);


        autoSildeImages();
        super.onViewCreated(view, savedInstanceState);
    }


    private List<Photo> getListPhoto() {
        List<Photo> photoList = new ArrayList<Photo>();
        photoList.add(new Photo(R.drawable.banner));
        photoList.add(new Photo(R.drawable.banner1));
        photoList.add(new Photo(R.drawable.banner2));
        photoList.add(new Photo(R.drawable.banner3));
        return photoList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
    public void autoSildeImages(){
        if (mListPhoto ==null || mListPhoto.isEmpty() || viewPager == null){
            return;
        }
        if (mTimer==null){
            mTimer= new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = mListPhoto.size() - 1;
                        if (currentItem < totalItem){
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        }else viewPager.setCurrentItem(0);
                    }
                });
            }
        },500,3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer!=null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}