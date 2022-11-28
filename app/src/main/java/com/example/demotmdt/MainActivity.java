package com.example.demotmdt;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.demotmdt.Adapter.MessengerAdapter;
import com.example.demotmdt.Adapter.ViewPageAdapter;
import com.example.demotmdt.Class.MessengerList;
import com.example.demotmdt.Class.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseReference myRef;
    User user = new User();
    String userKey;
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    ViewPageAdapter viewPageAdapter;
    ImageView iconMess,iconCart;
    SearchView searchView;
    RecyclerView recyclerViewSearch;
    public static CardView unSeenMain;
    public static TextView textUnSeenMain;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unSeenMain =(CardView) findViewById(R.id.unseenMain);
        textUnSeenMain=(TextView)findViewById(R.id.textUnseenMain);
        viewPager = (ViewPager) findViewById(R.id.viewpg);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navnav);
        iconMess = (ImageView) findViewById(R.id.topnavMess);
        iconCart = (ImageView) findViewById(R.id.topnavCart);
        searchView = (SearchView)findViewById(R.id.topSearchView);
        recyclerViewSearch = (RecyclerView) findViewById(R.id.recycleViewMainSearch);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        iconMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,UIMessenger.class);
                intent.putExtra("email",getIntent().getStringExtra("email"));
                intent.putExtra("mobile",getIntent().getStringExtra("mobile"));
                intent.putExtra("name",getIntent().getStringExtra("name"));
                startActivity(intent);
            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navhome:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.navfavourite:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.navThongBao:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.navprofile:
                        viewPager.setCurrentItem(3);
                        break;
                    default:
                        viewPager.setCurrentItem(0);
                        break;
                }
                return true;
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.navhome).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.navfavourite).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.navThongBao).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.navprofile).setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPageAdapter);

        Intent intent= getIntent();
        String email = intent.getStringExtra("email");
        myRef = FirebaseDatabase.getInstance().getReference();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu,menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView =(SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
    public static void updateUnSeen(List<MessengerList> messengerLists){
        int i=0;
        for (MessengerList messengerList:messengerLists){
            if (messengerList.getUnseenMessenger()>0){
                i++;
            }
        }
        if (i>0){
            unSeenMain.setVisibility(View.VISIBLE);
            textUnSeenMain.setText(String.valueOf(i));
        }else{
            unSeenMain.setVisibility(View.GONE);
        }
    }


}