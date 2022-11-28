package com.example.demotmdt.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demotmdt.Class.DanhMuc;
import com.example.demotmdt.Class.SanPham;
import com.example.demotmdt.DetailsSanPham;
import com.example.demotmdt.QuanLySanPham;
import com.example.demotmdt.R;
import com.example.demotmdt.ThemSanPham;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    List<SanPham> sanPhams;

    public SanPhamAdapter(List<SanPham> sanPhams, Context context) {
        this.sanPhams = sanPhams;
        this.context = context;
    }

    Context context;
    @NonNull
    @Override
    public SanPhamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sanphamitemlayout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.ViewHolder holder, int position) {
        SanPham sanPham =sanPhams.get(position);
        holder.nameSP.setText(sanPham.getTen());
        holder.giaSP.setText(sanPham.getGia()+" VND");
        holder.slbSP.setText("Đã bán: "+sanPham.getDaBan());
        Picasso.get().load(sanPham.getImg()).into(holder.imgSP);
        System.out.println(sanPham.getImg()+"abc"+sanPham.getTen());
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailsSanPham.class);
                Activity activity = (Activity) context;
                intent.putExtra("mobile",activity.getIntent().getStringExtra("mobile"));
                intent.putExtra("email",activity.getIntent().getStringExtra("email"));
                intent.putExtra("name",activity.getIntent().getStringExtra("name"));
                intent.putExtra("imgUS",activity.getIntent().getStringExtra("imgUS"));
                intent.putExtra("namesp",sanPham.getTen());
                intent.putExtra("imgsp",sanPham.getImg());
                intent.putExtra("giasp",sanPham.getGia());
                intent.putExtra("motasp",sanPham.getMota());
                intent.putExtra("UID",sanPham.getUID());
                intent.putExtra("idSP",sanPham.getMaSP());

                context.startActivity(intent);

            }
        });

    }
    public void updateSanPham(List<SanPham> sanPhams){
        this.sanPhams=sanPhams;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return sanPhams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSP;
        TextView nameSP,giaSP,slbSP;
        LinearLayout layoutItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSP = (ImageView) itemView.findViewById(R.id.imgSP);
            nameSP= (TextView) itemView.findViewById(R.id.tenSP);
            giaSP= (TextView) itemView.findViewById(R.id.giaSP);
            slbSP= (TextView) itemView.findViewById(R.id.slbSP);
            layoutItem =(LinearLayout) itemView.findViewById(R.id.layoutItemSP);
        }
    }
}
