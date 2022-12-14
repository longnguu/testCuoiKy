package com.example.demotmdt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demotmdt.Class.SanPham;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class ThemSanPham extends AppCompatActivity {
    Uri imageUri;
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    StorageReference storageReference= firebaseStorage.getReference();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ImageView imageView;
    TextView tenSP,loaiSP,moTa,gia,soLuong;
    ProgressDialog progressDialog;
    String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        AnhXa();
        Button pick = (Button) findViewById(R.id.upLoad);
        Button save=(Button) findViewById(R.id.saveQLSP);
        imageView = (ImageView) findViewById(R.id.qlspIMG);
        mobile=getIntent().getStringExtra("mobile");
        pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectIMG();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                Calendar calendar = Calendar.getInstance();
                storageReference.child("image"+calendar.getTimeInMillis()+".png");
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                Bitmap bitmap = imageView.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
                byte[] data=baos.toByteArray();
                UploadTask uploadTask = storageReference.child("image"+calendar.getTimeInMillis()+".png").putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        if (taskSnapshot.getMetadata() != null) {
                            if (taskSnapshot.getMetadata().getReference() != null) {
                                Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUrl = uri.toString();
                                        if (imageUrl.isEmpty()){
                                            imageUrl= "https://firebasestorage.googleapis.com/v0/b/demotmdt-26982.appspot.com/o/error-image-generic.png?alt=media&token=dbfe9456-ba97-458f-8abf-dfd6e644dd25";
                                        }
                                        if (check()){
                                            SanPham sanPham=new SanPham(tenSP.getText().toString(),soLuong.getText().toString(),gia.getText().toString(), moTa.getText().toString(),imageUrl,loaiSP.getText().toString());
                                            final String currentTimeStamp= String.valueOf(System.currentTimeMillis()).substring(0,10);
                                            sanPham.setMaSP(currentTimeStamp);
                                            sanPham.setDaBan("0");
                                            databaseReference.child("SanPham").child(mobile).child(currentTimeStamp).setValue(sanPham);
//                                            Intent intent = new Intent(ThemSanPham.this, QuanLySanPham.class);
//                                            intent.putExtra("email",getIntent().getStringExtra("email"));
//                                            intent.putExtra("mobile",getIntent().getStringExtra("mobile"));
//                                            intent.putExtra("name",getIntent().getStringExtra("name"));
//                                            startActivity(intent);
                                            Toast.makeText(ThemSanPham.this, "???? th??m.", Toast.LENGTH_SHORT).show();
                                            tenSP.setText("");
                                            soLuong.setText("");
                                            gia.setText("");
                                            moTa.setText("");
                                            loaiSP.setText("");
                                            imageView.setImageDrawable(null);
                                        }else
                                            Toast.makeText(ThemSanPham.this, "Vui l??ng nh???p ????? th??ng tin", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
    }

    private void AnhXa() {
        tenSP =(TextView) findViewById(R.id.addSP_Ten);
        gia =(TextView) findViewById(R.id.addSP_Gia);
        loaiSP =(TextView) findViewById(R.id.addSP_Loai);
        moTa =(TextView) findViewById(R.id.addSP_Mota);
        soLuong =(TextView) findViewById(R.id.addSP_SoLuong);
    }
    private boolean check(){
        if (tenSP.getText().toString().isEmpty() || gia.getText().toString().isEmpty()||loaiSP.getText().toString().isEmpty()||moTa.getText().toString().isEmpty()||soLuong.getText().toString().isEmpty()){
            return false;
        }else return true;
    }

    private void selectIMG() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==100 && data !=null && data.getData()!=null){
            Uri uriimgt=data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(ThemSanPham.this.getContentResolver(), uriimgt);
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);

        }
    }
}