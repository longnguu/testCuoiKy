package com.example.demotmdt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demotmdt.Class.MemoryData;
import com.example.demotmdt.Class.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup extends AppCompatActivity {
    private EditText namedk,passdk,sdtdk,emaildk,confirmpassdk;
    private Button btdk;
    private User us;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    boolean kt=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("users");
        AnhXa();
        btdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (confirmpassdk.getText().toString().trim().equals(passdk.getText().toString().trim()))
                {
                    SignUpUser();
                }
            }
        });

    }
    public void SignUpUser() {

        String gmail =emaildk.getText().toString();
        String pass = passdk.getText().toString();
        String phone = sdtdk.getText().toString();
        if (TextUtils.isEmpty(gmail)) {
            emaildk.setError("Không thể để trống email");
            emaildk.requestFocus();
        } else if (TextUtils.isEmpty(pass)) {
            passdk.setError("Không thể để trống mật khẩu");
            passdk.requestFocus();
        } else if(TextUtils.isEmpty(phone)){
            sdtdk.setError("Không thể để trống mật khẩu");
            sdtdk.requestFocus();
        }
        else {
            progressDialog.show();
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("users").hasChild(phone)){
                        Toast.makeText(Signup.this, "Đã tồn tại", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    else{
                        mAuth.createUserWithEmailAndPassword(gmail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Signup.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                                    us=new User();
                                    us.setTenUser(namedk.getText().toString());
                                    us.setSDT(sdtdk.getText().toString());
                                    us.setMatKhau(passdk.getText().toString());
                                    us.setEmail(emaildk.getText().toString());
                                    us.setImgUS("https://firebasestorage.googleapis.com/v0/b/demotmdt-26982.appspot.com/o/icon_user_default.jpg?alt=media&token=ce5c75d8-4d3b-4d20-8e34-de952becf786");
                                    myRef.child("users").child(phone).setValue(us);
                                    MemoryData.saveData(phone,Signup.this);
                                    Intent intent = new Intent(Signup.this,Login.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(Signup.this, "Đăng ký không thành công", Toast.LENGTH_LONG).show();
                                }
                                progressDialog.dismiss();
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.dismiss();
                }
            });
        }
    }

    private void AnhXa() {
        btdk = findViewById(R.id.btn_register);
        namedk= findViewById(R.id.nameDangKy);
        passdk= findViewById(R.id.passwordDangKy);
        sdtdk = findViewById(R.id.phoneDangKy);
        emaildk = findViewById(R.id.emailDangKy);
        confirmpassdk = findViewById(R.id.confirm_passwordDangKy);

    }
}