package com.example.lukaschroncschool.is_jaroska;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.appizona.yehiahd.fastsave.FastSave;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


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
        // Initializing ListView and setting adapter
        ArrayList<Card> arrayOfUsers = new ArrayList<Card>();
        CardAdapter adapter = new CardAdapter(getActivity(), arrayOfUsers);
        ListView listView = (ListView) rootView.findViewById(R.id.listview);
        listView.setAdapter(adapter);

        FastSave.init(getContext());

        Map<String,String> login_cookie = FastSave.getInstance().getObject("login_cookie",Map.class);
        new getCards().execute(login_cookie);
        return rootView;
    }
    private class getCards extends AsyncTask<Map<String,String>, String, ArrayList<Card>> {
        private BulletinFragment parent;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Card> doInBackground(Map<String,String>... params) {
            ArrayList<Card> card_array = new ArrayList<Card>();
            int attempt = 0;
            Document doc;
            doc = Connect(params[0]);
            if(doc.select("form").isEmpty() || attempt < 5) {
                Log.d("logged_in_with_class",doc.toString());

                Elements cards = doc.select("div.jumbotron div.card");
                for(int i = 0; i < cards.size();i++){
                    String header = Jsoup.parse(doc.select("div.card-header").text()).text();
                    String body = Jsoup.parse(doc.select("div.card-body").text()).text();
                    String footer = Jsoup.parse(doc.select("div.card-footer").text()).text().replaceFirst("\\s*\\w+\\s+\\w+$", "");
                    Card x = new Card(header,body,footer);
                    card_array.add(x);
                }
                return card_array;

            }
            else{
                attempt = attempt + 1;
                doc = Connect(params[0]);
                Log.d("logged_in_with_class",doc.toString());
            }


        return null;
        }
        @Override
        protected void onPostExecute(ArrayList<Card> result){
            ListView listView = getActivity().findViewById(R.id.listview);
            listView.setAdapter(new CardAdapter(getActivity(),result));


        }
    }
    private Document Connect(Map<String,String> cookie){
        try {
            Document doc = Jsoup.connect("https://is.jaroska.cz")
                    .cookies(cookie)
                    .get();
            return doc;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }



}
