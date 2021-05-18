package com.example.a2021swp_weatherwear;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectTopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectTopFragment extends Fragment implements TopTextAdaptor.OnListItemSelectedInterface {

    // Add RecyclerView member
    private RecyclerView recyclerTopView;
    private FirebaseDatabase database, databaseAdd;
    private DatabaseReference databaseReference, databaseReferenceAdd;
    private TopTextAdaptor mAdaptor;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectTopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectTopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectTopFragment newInstance(String param1, String param2) {
        SelectTopFragment fragment = new SelectTopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_select_top, container, false);

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        ArrayList<String> list = new ArrayList<>();
//        for (int i=0; i<100; i++) {
//            list.add(String.format("TEXT %d", i)) ;
//        }
        database = FirebaseDatabase.getInstance(); // 파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("Garment"); // DB 테이블 연결
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                list.clear(); // 기존 배열리스트가 존재하지않게 초기화
                for (DataSnapshot snapshot : dataSnapshot.child("Top").getChildren()) { // 반복문으로 데이터 list 를 추출해냄
                    list.add(snapshot.getValue().toString()); // 담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비
                }
                mAdaptor.notifyDataSetChanged(); // 리스트 저장 및 새로고침해야 반영이 됨
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비를 가져오던중 에러 발생 시
                Log.e("FragLike", String.valueOf(databaseError.toException())); // 에러문 출력
            }
        });

        // Add the following lines to create RecyclerView
        recyclerTopView = view.findViewById(R.id.recyclerViewTop);
        recyclerTopView.setHasFixedSize(true);
        recyclerTopView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mAdaptor = new TopTextAdaptor(list, this);
        recyclerTopView.setAdapter(mAdaptor);

        return view;
    }

    @Override
    public void onItemSelected(View v, int position) {
        TopTextAdaptor.ViewHolder viewHolder = (TopTextAdaptor.ViewHolder)recyclerTopView.findViewHolderForAdapterPosition(position);
        Toast.makeText(getActivity(), viewHolder.TxtOuter.getText().toString(), Toast.LENGTH_SHORT).show();

        // DB 연결
        databaseAdd = FirebaseDatabase.getInstance();
        // TODO: User2 부분은 실제로 사용자 값으로 넣을 것
        databaseReferenceAdd = databaseAdd.getReference("User").child("User2").child("Top");

        // TODO: 클릭 시 저장됨, 그러나 재 클릭 시 다시 삭제되도록 할
        databaseReferenceAdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // 클릭을 할떄 이미 데이터가 있는지 확인하기. 없으면 저장, 있으면 삭제!
//                if ( getUserOuterData(position) ) {  // 데이터가 이미 있음!!
//                    databaseReferenceAdd.child(String.valueOf(position)).removeValue();
//                    System.out.println("데이터 삭제한당!");
//                } else {
//                    databaseReferenceAdd.child(String.valueOf(position)).setValue("test test");
//                    System.out.println("데이터 저장할게~");
//                }
                String str = viewHolder.TxtOuter.getText().toString();
                databaseReferenceAdd.child(String.valueOf(position)).setValue(str);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("error : not added ", error.toString());
            }
        });
    }
}