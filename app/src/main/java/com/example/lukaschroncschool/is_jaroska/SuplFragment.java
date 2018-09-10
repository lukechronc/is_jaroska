package com.example.lukaschroncschool.is_jaroska;

import android.app.Activity;
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


public class SuplFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_supl, container, false);
        FastSave.init(getContext());
        ListView listview = (ListView) rootView.findViewById(R.id.link_list);
        //Methods m = new Methods(getActivity());
        //m.RunCookies();
        listview.setAdapter(new LinkAdapter(getContext(),new ArrayList<Link>()));
        new getCards().execute(FastSave.getInstance().getObject("login_cookie",Map.class));


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),WebViewActivity.class);
                ArrayList<String> owos = FastSave.getInstance().getObject("owo_links",ArrayList.class);
                Log.d("owo_link",owos.get(0));

                intent.putExtra("link",owos.get(position));
                startActivity(intent);
            }
        });


        return rootView;

    }
    private class getCards extends AsyncTask<Map<String,String>, String, ArrayList<Link>> {
        private BulletinFragment parent;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ArrayList<Link> doInBackground(Map<String,String>... params) {
            ArrayList<Link> result = new ArrayList<Link>();
            ArrayList<String> owos = new ArrayList<String>();
            Document doc;
            doc = Connect(params[0]);
            if(doc.select("form").isEmpty()) {
                Elements links = doc.select("table.table-striped.table-sm a");
                int id = 0;
                for(Element owo : links){
                    result.add(new Link(owo.text(),owo.attr("href"),id));
                    owos.add(owo.attr("href"));
                    id = id + 1;
                }
                FastSave.getInstance().saveObject("owo_links",owos);
            }


            return result;
        }
        @Override
        protected void onPostExecute(ArrayList<Link> result){
            ListView listView = getActivity().findViewById(R.id.link_list);
            listView.setAdapter(new LinkAdapter(getActivity(),result));
            FastSave.getInstance().saveObject("supl_links",result);
        }
    }
        private Document Connect(Map<String,String> cookie){
            try {
                Document doc = Jsoup.connect("https://is.jaroska.cz/index.php?akce=42&akcicka=0")
                        .cookies(cookie)
                        .get();
                return doc;
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }


}
