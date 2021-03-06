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
 * Use the {@link SelectBottomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectBottomFragment extends Fragment implements BottomTextAdaptor.OnListItemSelectedInterface {

    // Add RecyclerView member
    private RecyclerView recyclerBottomView;
    private FirebaseDatabase database, databaseAdd;
    private DatabaseReference databaseReference, databaseReferenceAdd;
    private BottomTextAdaptor mAdaptor;
    private static String userNick;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectBottomFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectBottomFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectBottomFragment newInstance(String param1, String param2, String nick) {
        SelectBottomFragment fragment = new SelectBottomFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        userNick = nick;
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
        View view = inflater.inflate(R.layout.fragment_select_bottom, container, false);

        // ????????????????????? ????????? ????????? ????????? ??????.
        ArrayList<String> list = new ArrayList<>();
//        for (int i=0; i<100; i++) {
//            list.add(String.format("TEXT %d", i));
//        }
        database = FirebaseDatabase.getInstance(); // ?????????????????? ?????????????????? ??????
        databaseReference = database.getReference("Garment"); // DB ????????? ??????
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // ?????????????????? ????????????????????? ???????????? ???????????? ???
                list.clear(); // ?????? ?????????????????? ?????????????????? ?????????
                for (DataSnapshot snapshot : dataSnapshot.child("Bottom").getChildren()) { // ??????????????? ????????? list ??? ????????????
                    list.add(snapshot.getValue().toString()); // ?????? ??????????????? ?????????????????? ?????? ????????????????????? ?????? ??????
                }
                mAdaptor.notifyDataSetChanged(); // ????????? ?????? ??? ?????????????????? ????????? ???
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // ????????? ??????????????? ?????? ?????? ???
                Log.e("FragLike", String.valueOf(databaseError.toException())); // ????????? ??????
            }
        });

        // Add the following lines to create RecyclerView
        recyclerBottomView = view.findViewById(R.id.recyclerViewBottom);
        recyclerBottomView.setHasFixedSize(true);
        recyclerBottomView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        mAdaptor = new BottomTextAdaptor(list, this);
        recyclerBottomView.setAdapter(mAdaptor);

        return view;
    }

    @Override
    public void onItemSelected(View v, int position) {
        BottomTextAdaptor.ViewHolder viewHolder = (BottomTextAdaptor.ViewHolder)recyclerBottomView.findViewHolderForAdapterPosition(position);
        Toast.makeText(getActivity(), viewHolder.TxtOuter.getText().toString(), Toast.LENGTH_SHORT).show();

        // DB ??????
        databaseAdd = FirebaseDatabase.getInstance();
        // TODO: User2 ????????? ????????? ????????? ????????? ?????? ???
        databaseReferenceAdd = databaseAdd.getReference("User").child(userNick).child("Bottom");

        // TODO: ?????? ??? ?????????, ????????? ??? ?????? ??? ?????? ??????????????? ???
        databaseReferenceAdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

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