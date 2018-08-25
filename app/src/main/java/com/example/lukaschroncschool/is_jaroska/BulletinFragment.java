package com.example.lukaschroncschool.is_jaroska;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class BulletinFragment extends Fragment {

    public BulletinFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bulletin, container, false);
        ArrayList<Card> arrayOfUsers = new ArrayList<Card>();
        CardAdapter adapter = new CardAdapter(getActivity(), arrayOfUsers);
        ListView listView = (ListView) rootView.findViewById(R.id.listview);
        listView.setAdapter(adapter);
        adapter.add(new Card("owo","wowie","wat"));

        return rootView;
    }



}
