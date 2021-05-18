package com.example.a2021swp_weatherwear;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerLikeAdaptor extends RecyclerView.Adapter<RecyclerLikeAdaptor.ViewHolder> {

    private ArrayList<RecyclerLikeItem> mData = null;

    RecyclerLikeAdaptor(ArrayList<RecyclerLikeItem> list) {
        mData = list;
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconLikeOuter, iconLikeTop, iconLikeBottom;
        TextView strLikeOuter, strLikeTop, strLikeBottom;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // 뷰 객체에 대한 참조. (hold strong reference)
            iconLikeOuter = itemView.findViewById(R.id.imgLikeOuter);
            iconLikeTop = itemView.findViewById(R.id.imgLikeTop);
            iconLikeBottom = itemView.findViewById(R.id.imgLikeBottom);
            strLikeOuter = itemView.findViewById(R.id.txtLikeOuter);
            strLikeTop = itemView.findViewById(R.id.txtLikeTop);
            strLikeBottom = itemView.findViewById(R.id.txtLikeBottom);
        }
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @NonNull
    @Override
    public RecyclerLikeAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_likeitem, parent, false) ;
        RecyclerLikeAdaptor.ViewHolder vh = new RecyclerLikeAdaptor.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(@NonNull RecyclerLikeAdaptor.ViewHolder holder, int position) {
        RecyclerLikeItem item = mData.get(position) ;

        holder.iconLikeOuter.setImageDrawable(item.getIconLikeOuter());
        holder.iconLikeTop.setImageDrawable(item.getIconLikeTop());
        holder.iconLikeBottom.setImageDrawable(item.getIconLikeBottom());
        holder.strLikeOuter.setText(item.getStrLikeOuter());
        holder.strLikeTop.setText(item.getStrLikeTop());
        holder.strLikeBottom.setText(item.getStrLikeBottom());
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }
}
