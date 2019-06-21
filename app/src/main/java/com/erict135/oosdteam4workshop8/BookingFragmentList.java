package com.erict135.oosdteam4workshop8;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.erict135.oosdteam4workshop8.adapters.RecyclerViewAdapter;
import com.erict135.oosdteam4workshop8.configurationset.ConfigurationSet;
import com.erict135.oosdteam4workshop8.model.BookingWPackageDetails;
import com.erict135.oosdteam4workshop8.model.Customer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BookingFragmentList extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private RelativeLayout layout;
    private ArrayList<BookingWPackageDetails> bookingsWdetails;
    private RecyclerView recyclerView;

    private static Customer c;

    private StringBuffer buffer;
    private int responsecode;

    private final String BOOKINGSWPACKAGEURL_ = ConfigurationSet.getRESTBookingsWPackage_();

    private static BookingFragmentList bookingFragmentList;

    public BookingFragmentList() {
    }

    public static BookingFragmentList newInstance(int sectionNumber, Customer cust) {
        if (bookingFragmentList == null) {
            bookingFragmentList = new BookingFragmentList();
        }
        c = cust;
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        bookingFragmentList.setArguments(args);
        return bookingFragmentList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_booking_list, container, false);

        layout = (RelativeLayout) rootView.findViewById(R.id.BookingList);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.rvBookings);

        buffer = new StringBuffer();

        bookingsWdetails = new ArrayList<BookingWPackageDetails>();
        new GetBookings().execute();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    class GetBookings extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(BOOKINGSWPACKAGEURL_ + String.valueOf(c.getCustomerId()));
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("AUTHTOKEN", c.getTOKEN());
                urlConnection.setRequestProperty("customerId", c.getCustomerId().toString());
                urlConnection.setConnectTimeout(10000);
                urlConnection.connect();

                responsecode = urlConnection.getResponseCode();
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line = "";
                while ((line = br.readLine()) != null) {
                    buffer.append(line);
                }
                urlConnection.disconnect();
            } catch (MalformedURLException e1) {
                bookingsWdetails = new ArrayList<>();
            } catch (IOException e2) {
                bookingsWdetails = new ArrayList<>();
            }catch (Exception e0){
                bookingsWdetails = new ArrayList<>();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Type type = new TypeToken<ArrayList<BookingWPackageDetails>>() {
            }.getType();
            bookingsWdetails = new Gson().fromJson(buffer.toString(), type);

            buffer.delete(0,buffer.length());

            if(bookingsWdetails==null){
                bookingsWdetails=new ArrayList<>();
            }
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(bookingsWdetails, getContext());
            adapter.setHasStableIds(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }
}