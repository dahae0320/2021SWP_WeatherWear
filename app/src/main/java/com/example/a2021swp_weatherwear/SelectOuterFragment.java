package com.example.a2021swp_weatherwear;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectOuterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectOuterFragment extends Fragment {

    // Add RecyclerView member
    private RecyclerView recyclerOuterView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectOuterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectOuterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectOuterFragment newInstance(String param1, String param2) {
        SelectOuterFragment fragment = new SelectOuterFragment();
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
        View view = inflater.inflate(R.layout.fragment_select_outer, container, false);

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        ArrayList<String> list = new ArrayList<>();
        for (int i=0; i<100; i++) {
            list.add(String.format("TEXT %d", i)) ;
        }

        // Add the following lines to create RecyclerView
        recyclerOuterView = view.findViewById(R.id.recyclerViewOuter);
        recyclerOuterView.setHasFixedSize(true);
        recyclerOuterView.setLayoutManager(new LinearLayoutManager(getActivity()));
        OuterTextAdaptor adapter = new OuterTextAdaptor(list);
        recyclerOuterView.setAdapter(adapter);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
//        RecyclerView recyclerView = null;
//        recyclerView = recyclerView.findViewById(R.id.recyclerViewOuter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
//        OuterTextAdaptor adapter = new OuterTextAdaptor(list) ;
//        recyclerView.setAdapter(adapter) ;

        return view;
    }
}