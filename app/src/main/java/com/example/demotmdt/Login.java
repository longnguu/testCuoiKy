package com.example.demotmdt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
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

public class Login extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText usLI,paLI;
    Button btlogin,btsignup;
    CheckBox checkBox;
    SharedPreferences sharedPreferences;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("Datalogin",MODE_PRIVATE);
        AnhXa();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        if (sharedPreferences.getBoolean("checked",false)==true){
            usLI.setText(sharedPreferences.getString("taikhoan",""));
            paLI.setText(sharedPreferences.getString("matkhau",""));
            checkBox.setChecked(true);
        }else checkBox.setChecked(false);
        mAuth = FirebaseAuth.getInstance();
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
        btsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,Signup.class);
                startActivity(intent);
            }
        });
    }
    public void loginUser() {
        String gmail =usLI.getText().toString();
        String pass = paLI.getText().toString();
        progressDialog.show();
        if (TextUtils.isEmpty(gmail)) {
            usLI.setError("Không thể để trống email");
            usLI.requestFocus();
            progressDialog.dismiss();
        } else if (TextUtils.isEmpty(pass)) {
            paLI.setError("Không thể để trống mật khẩu");
            paLI.requestFocus();
            progressDialog.dismiss();
        } else {
            mAuth.signInWithEmailAndPassword(gmail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        checkRemember();
                        Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                    if (dataSnapshot.getValue(User.class).getEmail().equals(gmail.toString())){
                                        Intent intent = new Intent(Login.this, MainActivity.class);
                                        intent.putExtra("email",dataSnapshot.getValue(User.class).getEmail());
                                        intent.putExtra("mobile",dataSnapshot.getValue(User.class).getSDT());
                                        intent.putExtra("name",dataSnapshot.getValue(User.class).getTenUser());
                                        intent.putExtra("imgUS",dataSnapshot.getValue(User.class).getImgUS());
                                        MemoryData.saveData(dataSnapshot.getValue(User.class).getSDT(),Login.this);
                                        startActivity(intent);
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(Login.this, "Đăng nhập không thành công", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }
    public void AnhXa(){
        btlogin = (Button) findViewById(R.id.login_btn);
        btsignup = (Button) findViewById(R.id.signUp_btn);
        usLI = (EditText) findViewById(R.id.emailDangNhap);
        paLI = (EditText) findViewById(R.id.passwordDangNhap);
        checkBox = (CheckBox) findViewById(R.id.cb_savePassword);
    }
    private void checkRemember() {

        String us=usLI.getText().toString().trim()+"";
        String pas= paLI.getText().toString().trim()+"";
        if (checkBox.isChecked()) {
            //   Toast.makeText(Signin.this, "Đã lưu", Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("taikhoan", us);
            editor.putString("matkhau", pas);
            editor.putBoolean("checked", true);
            editor.commit();
        }else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // Toast.makeText(Signin.this, "không lưu", Toast.LENGTH_SHORT).show();
            editor.putString("taikhoan", us);
            editor.putString("matkhau", pas);
            editor.putBoolean("checked", false);
            editor.commit();
        }

    }

}