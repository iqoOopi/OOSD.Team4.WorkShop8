package com.erict135.oosdteam4workshop8;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.erict135.oosdteam4workshop8.configurationset.ConfigurationSet;
import com.erict135.oosdteam4workshop8.model.Customer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BrowseFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private LinearLayout layout;
    private List<Package> packages;

    private StringBuffer buffer;
    private int responsecode;
    private static Customer c;

    private final String PACKAGESURL = ConfigurationSet.getRESTPackages();

    private static BrowseFragment browseFragment;

    public BrowseFragment() {
    }

    public  static BrowseFragment getInstance(){
        return browseFragment;
    }

    public static BrowseFragment newInstance(int sectionNumber,Customer customer) {
        if(browseFragment==null){
            browseFragment = new BrowseFragment();
        }
        c=customer;
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        browseFragment.setArguments(args);
        return browseFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_browse, container, false);
        layout=(LinearLayout)rootView.findViewById(R.id.container_browse);

        buffer = new StringBuffer();

        new getPackages().execute();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }
    class getPackages extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(PACKAGESURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                if(MainActivity.isLoggedin()) {
                    urlConnection.setRequestProperty("AUTHTOKEN", c.getTOKEN());
                    urlConnection.setRequestProperty("customerId", c.getCustomerId().toString());
                }
                urlConnection.setConnectTimeout(20000);
                urlConnection.connect();

                responsecode = urlConnection.getResponseCode();
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line = "";
                while ((line = br.readLine()) != null) {
                    buffer.append(line);
                }
                urlConnection.disconnect();
            }catch(Exception e){

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Gson gson = new Gson();

            Type type = new TypeToken<ArrayList<Package>>(){}.getType();
            packages = gson.fromJson(buffer.toString(),type);

            int cid=0;
            try{
                cid=c.getCustomerId();
            }catch(NullPointerException e){
                cid=0;
            }
            for(int i=0;i<packages.size();i++){
                BrowseElementFragment browseElementFragment = BrowseElementFragment.newInstance(packages.get(i),cid,getContext(),c);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .add(layout.getId(),browseElementFragment,"package_"+i)
                        .commit();
            }
        }
    }
}