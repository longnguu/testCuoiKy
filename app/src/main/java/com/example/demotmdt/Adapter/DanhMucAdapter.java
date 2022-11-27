package com.example.demotmdt.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demotmdt.Class.DanhMuc;
import com.example.demotmdt.R;

import java.util.List;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder> {
    List<DanhMuc> danhMucList;
    private Context mContext;

    public DanhMucAdapter(List<DanhMuc> danhMucList, Context mContext) {
        this.danhMucList = danhMucList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public DanhMucAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.danhmucitemlayout, parent, false);
        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull DanhMucAdapter.ViewHolder holder, int position) {
        DanhMuc computer =danhMucList.get(position);
        holder.tenDanhMuc.setText(computer.getName());
    }

    @Override
    public int getItemCount() {
        return danhMucList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemview;
        public TextView tenDanhMuc;
        public Button detail_button;

        public ViewHolder(View itemView) {
            super(itemView);
            itemview = itemView;
            tenDanhMuc = itemView.findViewById(R.id.studentname);

            //Xử lý khi nút Chi tiết được bấm
        }
    }
}
