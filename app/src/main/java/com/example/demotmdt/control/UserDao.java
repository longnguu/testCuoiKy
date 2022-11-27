package com.example.demotmdt.control;

import androidx.annotation.NonNull;

import com.example.demotmdt.Class.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDao {
    DatabaseReference myRef;
    User user = new User();
    String userKey;
    public User getUserByEmail(String email){
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("users").orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    userKey=dataSnapshot.getKey();
                    user = dataSnapshot.getValue(User.class);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return user;
    }
}
