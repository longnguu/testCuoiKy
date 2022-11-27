package com.example.demotmdt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.demotmdt.Adapter.SanPhamAdapter;
import com.example.demotmdt.Class.SanPham;
import com.example.demotmdt.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class QuanLySanPham extends AppCompatActivity {
    Uri imageUri;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference= firebaseStorage.getReference();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ImageView imageView;
    CardView cardView;

    RecyclerView recyclerView;

    ArrayList<SanPham> sanPhams;
    SanPhamAdapter sanPhamAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);
        cardView = findViewById(R.id.qlsp_add);
        recyclerView =findViewById(R.id.list_sanphamQLSP);
        sanPhams=new ArrayList<>();

        sanPhamAdapter=new SanPhamAdapter(sanPhams,this );
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        recyclerView.setAdapter(sanPhamAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        databaseReference.child("SanPham").child(getIntent().getStringExtra("mobile")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sanPhams.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        String ten= dataSnapshot.child("ten").getValue(String.class);
                        SanPham sanPham = new SanPham(ten);
                        sanPham.setImg(dataSnapshot.child("img").getValue(String.class));
                        sanPham.setMaSP(dataSnapshot.getKey());
                        sanPham.setUID(getIntent().getStringExtra("mobile"));
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
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuanLySanPham.this, ThemSanPham.class);
                intent.putExtra("email",getIntent().getStringExtra("email"));
                intent.putExtra("mobile",getIntent().getStringExtra("mobile"));
                intent.putExtra("name",getIntent().getStringExtra("name"));
                startActivity(intent);
            }
        });

    }
}