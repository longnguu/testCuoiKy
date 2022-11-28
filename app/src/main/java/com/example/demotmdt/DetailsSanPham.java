package com.example.demotmdt;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.demotmdt.Adapter.SanPhamAdapter;
import com.example.demotmdt.Class.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class DetailsSanPham extends AppCompatActivity {

    String mobile,email,name,namesp,motasp,giasp,img,uid;
    Button btnchinhsua,bthaddcart,btnchat;
    LinearLayout linearLayout;
    ImageView imageView,imgShop;
    TextView textViewName,textViewMota,textViewGia,tenShop;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ProgressDialog progressDialog;
    List<SanPham> sanPhams=new ArrayList<>();
    SanPhamAdapter sanPhamAdapter;
    RecyclerView recyclerView;
    TextView ssp;
    String imgUS;
    String nameShop,imgShopp,mobileShop;
    boolean ktra=true,kt=false;
    String chatKey="0";
    String m_Text;
    String maSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_san_pham);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
        }
        AnhXa();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        ssp = (TextView) findViewById(R.id.soSPDetail);
        mobile = getIntent().getStringExtra("mobile");
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");
        namesp = getIntent().getStringExtra("namesp");
        motasp = getIntent().getStringExtra("motasp");
        giasp = getIntent().getStringExtra("giasp");
        img = getIntent().getStringExtra("imgsp");
        uid = getIntent().getStringExtra("UID");
        maSP = getIntent().getStringExtra("idSP");
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ssp.setText("Tổng sản phẩm: "+snapshot.child("SanPham").child(uid).getChildrenCount());
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    nameShop=dataSnapshot.child(uid).child("tenUser").getValue(String.class);
                    imgShopp =dataSnapshot.child(uid).child("imgUS").getValue(String.class);
                    mobileShop =dataSnapshot.child(uid).child("sdt").getValue(String.class);
                    dataSnapshot.child(uid).child("imgUS").getValue(String.class);
                    tenShop.setText(dataSnapshot.child(uid).child("tenUser").getValue(String.class));
                    imgUS=dataSnapshot.child(uid).child("imgUS").getValue(String.class);
                    Picasso.get().load(dataSnapshot.child(uid).child("imgUS").getValue(String.class)).into(imgShop);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

        databaseReference.child("SanPham").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sanPhams.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String ten= dataSnapshot.child("ten").getValue(String.class);
                    SanPham sanPham = new SanPham(ten);
                    sanPham.setImg(dataSnapshot.child("img").getValue(String.class));
                    sanPham.setMaSP(dataSnapshot.getKey());
                    sanPham.setUID(getIntent().getStringExtra("UID"));
                    sanPham.setMota(dataSnapshot.child("mota").getValue(String.class));
                    sanPham.setGia(dataSnapshot.child("gia").getValue(String.class));
                    sanPham.setDaBan("0");
                    sanPhams.add(sanPham);
                    sanPhamAdapter.updateSanPham(sanPhams);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bthaddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailsSanPham.this);
                builder.setTitle("Nhập số lượng");

// Set up the input
                final EditText input = new EditText(DetailsSanPham.this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        databaseReference.child("GioHang").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ktra=false;
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                                        if (dataSnapshot1.getKey().equals(maSP)){
                                            ktra=true;
                                            databaseReference.child("GioHang").child(mobile).child(maSP).child("soLuong").setValue(String.valueOf(Long.parseLong(dataSnapshot1.child("soLuong").getValue(String.class))+Long.parseLong(m_Text)));
                                        }
                                }
                                if (!ktra){
                                    databaseReference.child("GioHang").child(mobile).child(maSP).child("soLuong").setValue(m_Text);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
        btnchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        kt=true;
                        chatKey= String.valueOf(snapshot.getChildrenCount()+1);
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            if((dataSnapshot.child("user_1").getValue(String.class).equals(mobile)  &&  dataSnapshot.child("user_2").getValue(String.class).equals(uid))
                            || (dataSnapshot.child("user_1").getValue(String.class).equals(uid)     &&  dataSnapshot.child("user_2").getValue(String.class).equals(mobile))){
                                chatKey=dataSnapshot.getKey();
                                kt=false;
                            }
                        }
                        System.out.println(chatKey);
                        if (!kt){
                            Intent intent = new Intent(DetailsSanPham.this,Chat.class);
                            intent.putExtra("name",nameShop);
                            intent.putExtra("profilePic",imgUS);
                            intent.putExtra("chatKey",chatKey);
                            intent.putExtra("mobile",mobileShop);
                            startActivity(intent);
                        }else{
                            databaseReference.child("chat").child(chatKey).child("user_1").setValue(mobile);
                            databaseReference.child("chat").child(chatKey).child("user_2").setValue(uid);
                            Intent intent = new Intent(DetailsSanPham.this,Chat.class);
                            intent.putExtra("name",nameShop);
                            intent.putExtra("profilePic",imgUS);
                            intent.putExtra("chatKey",chatKey);
                            intent.putExtra("mobile",mobileShop);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


        sanPhamAdapter=new SanPhamAdapter(sanPhams,this);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,2, RecyclerView.VERTICAL,false);
        recyclerView.setAdapter(sanPhamAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        textViewName.setText(namesp);
        textViewMota.setText(motasp);
        textViewGia.setText(giasp+" VNĐ");

        Picasso.get().load(img).into(imageView);
        if (uid.equals(mobile)){
            btnchinhsua.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }else{
            btnchinhsua.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
        }

    }

    private void AnhXa() {
        linearLayout = findViewById(R.id.linerBottomDetails);
        btnchinhsua = findViewById(R.id.btnChinhSuaDetails);
        btnchat=findViewById(R.id.btn_chatDetails);
        bthaddcart = findViewById(R.id.btnadd_Cart);
        textViewName=findViewById(R.id.nameSPDetail);
        textViewMota=findViewById(R.id.motaSPDetail);
        textViewGia=findViewById(R.id.giaSPDetail);
        imageView=findViewById(R.id.imgSPDetail);
        tenShop=findViewById(R.id.namePRShop);
        imgShop=findViewById(R.id.imgShopDetai);
        recyclerView=findViewById(R.id.recyclerSPDetail);
    }
}