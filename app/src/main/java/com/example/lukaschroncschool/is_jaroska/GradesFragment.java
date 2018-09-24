package com.example.lukaschroncschool.is_jaroska;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.appizona.yehiahd.fastsave.FastSave;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;


public class GradesFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_grades, container, false);
        FastSave.init(getContext());
        ListView listview = (ListView) rootView.findViewById(R.id.grades_list);
        //Methods m = new Methods(getActivity());
        //m.runCookies();
        listview.setAdapter(new LinkAdapter(getContext(),new ArrayList<Link>()));
        new getCards().execute(FastSave.getInstance().getObject("login_cookie",Map.class));



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
            ArrayList<Card> result = new ArrayList<Card>();
            Document doc;
            doc = Connect(params[0]);
            if(doc.select("form").isEmpty()) {
                Elements cards = doc.select("div.jumbotron div.card");
                for(Element owo : cards){
                    String subject = Jsoup.parse(owo.select("div.card-header p").first().text()).text();
                    String teacher = Jsoup.parse(owo.select("div.card-header p").last().text()).text().replace("(email)","");
                    String grades = Jsoup.parse(owo.select("div.card-body").text()).text();
                    result.add(new Card(subject,teacher,grades));
                }
            }


            return result;
        }
        @Override
        protected void onPostExecute(ArrayList<Card> result){
            ListView listView = getActivity().findViewById(R.id.grades_list);
            listView.setAdapter(new GradesAdapter(getContext(),result));

        }
    }
    private Document Connect(Map<String,String> cookie){
        try {
            Document doc = Jsoup.connect("https://is.jaroska.cz/index.php?akce=650")
                    .cookies(cookie)
                    .get();
            return doc;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
