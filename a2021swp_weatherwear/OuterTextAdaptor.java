package com.example.a2021swp_weatherwear;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OuterTextAdaptor extends RecyclerView.Adapter<OuterTextAdaptor.ViewHolder> {

    private ArrayList<String> mData = null;

    // 아이템 뷰를 저장하는 뷰홀더 클래스
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView TxtOuter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조 (hold strong reference)
            TxtOuter = itemView.findViewById(R.id.recyclerTxt);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    OuterTextAdaptor(ArrayList<String> list) {
        mData = list ;
    }

    @NonNull
    @Override
    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false) ;
        OuterTextAdaptor.ViewHolder vh = new OuterTextAdaptor.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position 에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String text = mData.get(position) ;
        holder.TxtOuter.setText(text) ;
    }

    // getItemCount() - 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return mData.size();
    }
}
