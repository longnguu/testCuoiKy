package com.example.demotmdt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demotmdt.Adapter.ChatAdapter;
import com.example.demotmdt.Class.ChatList;
import com.example.demotmdt.Class.MemoryData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chat extends AppCompatActivity {
    ImageView btnBackChat,btnSendChat;
    CircleImageView profilePicChat;
    TextView nameChat;
    EditText edtChat;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    String chatKey;
    String getUserMobile;
    RecyclerView chattingRecyclerView;
    ChatAdapter chatAdapter;
    boolean loadingFirstTime= true;
    private final List<ChatList> chatLists = new ArrayList<>();
    @SuppressLint("SuspiciousIndentation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        AnhXa();

        chattingRecyclerView.setHasFixedSize(true);
        chattingRecyclerView.setLayoutManager(new LinearLayoutManager(Chat.this));

        chatAdapter = new ChatAdapter(chatLists,Chat.this);
        chattingRecyclerView.setAdapter(chatAdapter);
        final String getName = getIntent().getStringExtra("name");
        final String getProfilePic = getIntent().getStringExtra("profilePic");
        chatKey = getIntent().getStringExtra("chatKey");
        final String getMobile= getIntent().getStringExtra("mobile");


        nameChat.setText(getName);
        getUserMobile= MemoryData.getData(Chat.this);
        if (!getProfilePic.isEmpty()){
            Picasso.get().load(getProfilePic).into(profilePicChat);
        }
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (chatKey.isEmpty()) {
                        chatKey = "1";
                        if (snapshot.hasChild("chat")) {
                            chatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
                        }
                    }
                    loadingFirstTime=true;
                    if (snapshot.hasChild("chat")){
                        if (snapshot.child("chat").child(chatKey).hasChild("messenger")) {
                            chatLists.clear();
                            for(DataSnapshot chatSnapshot: snapshot.child("chat").child(chatKey).child("messenger").getChildren()){
                                if(chatSnapshot.hasChild("msg")&& chatSnapshot.hasChild("mobile")) {
                                    final String messengerTimeStamps = chatSnapshot.getKey();
                                    final String getMobile =chatSnapshot.child("mobile").getValue(String.class);
                                    final String getMsg = chatSnapshot.child("msg").getValue(String.class);

                                    Timestamp timestamp = new Timestamp(Long.parseLong(messengerTimeStamps));
                                    Date date = new Date(timestamp.getDate());
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                                    ChatList chatList = new ChatList(getMobile,getName,getMsg,simpleDateFormat.format(date),simpleTimeFormat.format(date));
                                    chatLists.add(chatList);

                                    if (loadingFirstTime || Long.parseLong(messengerTimeStamps)> Long.parseLong(MemoryData.getLastMsgTs(Chat.this,chatKey))){
                                        loadingFirstTime=false;
                                        MemoryData.saveLastMsgTS(messengerTimeStamps,chatKey,Chat.this);
                                        chatAdapter.updateChat(chatLists);
                                        chattingRecyclerView.scrollToPosition(chatLists.size()-1);
                                    };
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        btnSendChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String getTxtMessenger = edtChat.getText().toString();
                final String currentTimeStamp= String.valueOf(System.currentTimeMillis()).substring(0,10);
                databaseReference.child("chat").child(chatKey).child("user_1").setValue(getUserMobile);
                databaseReference.child("chat").child(chatKey).child("user_2").setValue(getMobile);
                databaseReference.child("chat").child(chatKey).child("messenger").child(currentTimeStamp).child("msg").setValue(getTxtMessenger);
                databaseReference.child("chat").child(chatKey).child("messenger").child(currentTimeStamp).child("mobile").setValue(getUserMobile);
                //MemoryData.saveLastMsgTS(currentTimeStamp,chatKey,Chat.this);
                chatAdapter.updateChat(chatLists);
                edtChat.setText("");
            }
        });
    }

    private void AnhXa() {
        btnBackChat = (ImageView) findViewById(R.id.back_buttonChat);
        btnSendChat =(ImageView) findViewById(R.id.sendBtnChat);
        profilePicChat = (CircleImageView) findViewById(R.id.idProfilePicChat);
        nameChat = (TextView) findViewById(R.id.nameChat);
        edtChat = (EditText) findViewById(R.id.edtChat);
        chattingRecyclerView = (RecyclerView) findViewById(R.id.chatRecyclerView);
    }
}