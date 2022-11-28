package com.example.demotmdt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.example.demotmdt.Adapter.MessengerAdapter;
import com.example.demotmdt.Class.MemoryData;
import com.example.demotmdt.Class.MessengerList;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UIMessenger extends AppCompatActivity {
    RecyclerView listMessengerRecyclerView;
    String mobile, email, name;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    CircleImageView profilePic;
    ProgressDialog progressDialog;
    List<MessengerList> messengerLists = new ArrayList<>();
    String lastMessenger = "";
    private MessengerAdapter messengerAdapter;
    String chatKey = "";
    int unseenMessenger = 0;
    private boolean dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uimessenger);

        mobile = getIntent().getStringExtra("mobile");
        email = getIntent().getStringExtra("email");
        name = getIntent().getStringExtra("name");

        profilePic = (CircleImageView) findViewById(R.id.userProfilePicMessUI);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        listMessengerRecyclerView = (RecyclerView) findViewById(R.id.listMessengerRecycleView);
        listMessengerRecyclerView.setHasFixedSize(true);
        listMessengerRecyclerView.setLayoutManager(new LinearLayoutManager(UIMessenger.this));

        messengerAdapter = new MessengerAdapter(messengerLists, UIMessenger.this);
        listMessengerRecyclerView.setAdapter(messengerAdapter);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String profilePicUrl = snapshot.child("users").child(mobile).child("imgUS").getValue(String.class);

              if (!profilePicUrl.isEmpty()) {
                  Picasso.get().load(profilePicUrl).into(profilePic);
              }
              progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                unseenMessenger = 0;
                lastMessenger = "";
                chatKey = "";
                messengerLists.clear();
                for (DataSnapshot dataSnapshot : snapshot.child("users").getChildren()) {
                    final String getMobile = dataSnapshot.getKey();
                    messengerLists.clear();
                    dataSet=false;
                    lastMessenger="";
                    chatKey="";
                    unseenMessenger=0;
                    if (!getMobile.equals(mobile)) {
                        messengerLists.clear();
                        final String getName = dataSnapshot.child("tenUser").getValue(String.class);
                        final String profilePic = dataSnapshot.child("imgUS").getValue(String.class);
                        databaseReference.child("chat").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int getChatCount = (int) snapshot.getChildrenCount();
                                if (getChatCount > 0) {
                                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                                        String getKey = dataSnapshot1.getKey();
                                        chatKey = getKey;
                                        if (dataSnapshot1.hasChild("user_1") && dataSnapshot1.hasChild("user_2") && dataSnapshot1.hasChild("messenger")) {
                                            String getUserOne = dataSnapshot1.child("user_1").getValue(String.class);
                                            String getUserTwo = dataSnapshot1.child("user_2").getValue(String.class);
                                            if ((getUserOne.equals(mobile) && getUserTwo.equals(getMobile)) || (getUserOne.equals(getMobile) && getUserTwo.equals(mobile))) {
                                                for (DataSnapshot chatDataSnapShot : dataSnapshot1.child("messenger").getChildren()) {
                                                    final long getMessngerKey = Long.parseLong(chatDataSnapShot.getKey());
                                                    long getLastseenMsg = 0;
                                                    getLastseenMsg = Long.parseLong(MemoryData.getLastMsgTs(UIMessenger.this, getKey));
                                                    if (chatDataSnapShot.child("mobile").getValue(String.class).equals(mobile)){
                                                        lastMessenger = "Bạn: "+chatDataSnapShot.child("msg").getValue(String.class);
                                                    }else lastMessenger = chatDataSnapShot.child("msg").getValue(String.class);

                                                    if (getMessngerKey > getLastseenMsg) {
                                                        unseenMessenger++;
                                                    }
                                                }
                                                MessengerList messengerList = new MessengerList(getName, getMobile, lastMessenger, profilePic, chatKey, unseenMessenger);
                                                messengerLists.add(messengerList);
                                                MainActivity.updateUnSeen(messengerLists);
                                                if(!dataSet){
                                                    dataSet = true;
                                                    messengerAdapter.updateData(messengerLists);
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}