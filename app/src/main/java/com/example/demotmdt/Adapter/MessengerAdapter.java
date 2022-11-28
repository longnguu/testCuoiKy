package com.example.demotmdt.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demotmdt.Chat;
import com.example.demotmdt.Class.MemoryData;
import com.example.demotmdt.Class.MessengerList;
import com.example.demotmdt.MainActivity;
import com.example.demotmdt.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessengerAdapter extends RecyclerView.Adapter<MessengerAdapter.ViewHolder> {

    private List<MessengerList> messengerLists;
    private final Context mContext;

    public MessengerAdapter(List<MessengerList> messengerLists, Context mContext) {
        this.messengerLists = messengerLists;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MessengerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.messenger_list_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MessengerAdapter.ViewHolder holder, int position) {
        MessengerList list2=  messengerLists.get(position);
        list2.setPos(position);
        if (!list2.getProfilePic().isEmpty()){
            Picasso.get().load(list2.getProfilePic()).into(holder.profilePic);
        }
        holder.name.setText(list2.getName());
        holder.lastMessenger.setText(list2.getLastMessenger());
        if (list2.getUnseenMessenger()==0){
            holder.unseenMessenger.setVisibility(View.GONE);
            holder.lastMessenger.setTextColor(Color.parseColor("#959595"));
        }else{
            holder.unseenMessenger.setVisibility(View.VISIBLE);
            holder.unseenMessenger.setText(list2.getUnseenMessenger()+"");
            holder.lastMessenger.setTextColor(mContext.getResources().getColor(R.color.yellow_pri));

        }
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Chat.class);
                intent.putExtra("name",list2.getName());
                intent.putExtra("profilePic",list2.getProfilePic());
                intent.putExtra("chatKey",list2.getChatKey());
                intent.putExtra("mobile",list2.getEmail());
                final String currentTimeStamp= String.valueOf(System.currentTimeMillis()).substring(0,10);
                MemoryData.saveLastMsgTS(currentTimeStamp,list2.getChatKey(), view.getContext());
                messengerLists.get(list2.getPos()).setUnseenMessenger(0);
                Chat.loadingFirstTime=true;
                updateData(messengerLists);
                notifyDataSetChanged();
                MainActivity.updateUnSeen(messengerLists);
                mContext.startActivity(intent);
            }
        });
    }
    public void updateData(List<MessengerList> messengerLists){

        this.messengerLists = messengerLists;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messengerLists.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profilePic;
        private TextView name,lastMessenger,unseenMessenger;
        private LinearLayout rootLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = (CircleImageView) itemView.findViewById(R.id.profilePicListMessenger);
            name= (TextView) itemView.findViewById(R.id.nameMessengerList);
            lastMessenger= (TextView) itemView.findViewById(R.id.lassMessengerList);
            unseenMessenger= (TextView) itemView.findViewById(R.id.unseenMessengerList);
            rootLayout = (LinearLayout) itemView.findViewById(R.id.rootLayoutMessengerList);
        }
    }
}
