package com.example.demotmdt.UIFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demotmdt.Class.MemoryData;
import com.example.demotmdt.Class.MessengerList;
import com.example.demotmdt.Class.User;
import com.example.demotmdt.LichSuDonHang;
import com.example.demotmdt.Login;
import com.example.demotmdt.QuanLySanPham;
import com.example.demotmdt.R;
import com.example.demotmdt.ThongTinCaNhan;
import com.example.demotmdt.UIMessenger;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    TextView prThongTin,prQLSP,prLSDH,nameProfile,Logout;
    ImageView imgProfile;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AnhXa(view);
        Picasso.get().load(getActivity().getIntent().getStringExtra("imgUS")).into(imgProfile);
        nameProfile.setText(getActivity().getIntent().getStringExtra("name"));
        prThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ThongTinCaNhan.class);
                intent.putExtra("email",getActivity().getIntent().getStringExtra("email"));
                intent.putExtra("mobile",getActivity().getIntent().getStringExtra("mobile"));
                intent.putExtra("name",getActivity().getIntent().getStringExtra("name"));
                startActivity(intent);
            }
        });
        prQLSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), QuanLySanPham.class);
                intent.putExtra("email",getActivity().getIntent().getStringExtra("email"));
                intent.putExtra("mobile",getActivity().getIntent().getStringExtra("mobile"));
                intent.putExtra("name",getActivity().getIntent().getStringExtra("name"));
                startActivity(intent);
            }
        });
        prLSDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LichSuDonHang.class);
                intent.putExtra("email",getActivity().getIntent().getStringExtra("email"));
                intent.putExtra("mobile",getActivity().getIntent().getStringExtra("mobile"));
                intent.putExtra("name",getActivity().getIntent().getStringExtra("name"));
                startActivity(intent);
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Login.class));
            }
        });
    }

    private void AnhXa(View view) {
        prThongTin = (TextView)  view.findViewById(R.id.prThongtin);
        prQLSP=(TextView) view.findViewById(R.id.prQLSP);
        prLSDH=(TextView) view.findViewById(R.id.prLSDH);
        nameProfile = (TextView) view.findViewById(R.id.nameProfile);
        imgProfile=(ImageView) view.findViewById(R.id.imgProfile);
        Logout =(TextView) view.findViewById(R.id.logOut);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}