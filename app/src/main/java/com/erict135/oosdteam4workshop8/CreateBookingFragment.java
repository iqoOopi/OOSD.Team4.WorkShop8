package com.erict135.oosdteam4workshop8;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.erict135.oosdteam4workshop8.configurationset.ConfigurationSet;
import com.erict135.oosdteam4workshop8.model.Booking;
import com.erict135.oosdteam4workshop8.model.Customer;
import com.erict135.oosdteam4workshop8.model.Package;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Calendar;

public class CreateBookingFragment extends Fragment {

    private View view;
    private TextView tvpkgName_booking,tvpkgDesc_booking,tvpkgDuration_booking,tvpkgBasePrice_booking;
    private Spinner sptripTypeId;
    private EditText ettravelerCount;
    private Button btnConfirmBooking,btnBack_CreateBooking;
    private ImageView ivPreviewConfirmBooking,ivTravelerCountIncrease,ivTravelerCountDecrease;

    private Package bookPkg;
    private int customerid;
    private int travelerCount;
    private static Customer customer;
    private ArrayAdapter<CharSequence> adapter;

    private int responsecode;
    private static final String CREATEBOOKINGURL = ConfigurationSet.getRESTCustomerCreateBookingURL();

    private static CreateBookingFragment createBookingFragment;
    private static Context context;

    public static CreateBookingFragment newInstance(Package pkg, int cid, Context con, Customer cust){
        customer=cust;
        context=con;
        createBookingFragment = new CreateBookingFragment();
        Bundle args = new Bundle();
        args.putParcelable("package",pkg);
        args.putInt("customerid",cid);
        createBookingFragment.setArguments(args);
        return createBookingFragment;
    }

    public CreateBookingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create_booking, container, false);

        bookPkg = getArguments().getParcelable("package");

        customerid=getArguments().getInt("customerid");

        ivPreviewConfirmBooking=(ImageView)view.findViewById(R.id.ivPreviewConfirmBooking);
        tvpkgName_booking=(TextView)view.findViewById(R.id.tvpkgName_booking);
        tvpkgDesc_booking=(TextView)view.findViewById(R.id.tvpkgDesc_booking);
        tvpkgDuration_booking=(TextView)view.findViewById(R.id.tvpkgDuration_booking);
        tvpkgBasePrice_booking=(TextView)view.findViewById(R.id.tvpkgBasePrice_booking);
        ettravelerCount=(EditText)view.findViewById(R.id.ettravelerCount);
        ivTravelerCountIncrease=(ImageView)view.findViewById(R.id.ivTravelerCountIncrease);
        ivTravelerCountDecrease=(ImageView)view.findViewById(R.id.ivTravelerCountDecrease);
        sptripTypeId=(Spinner)view.findViewById(R.id.sptripTypeId);
        btnConfirmBooking=(Button)view.findViewById(R.id.btnConfirmBooking);
        btnBack_CreateBooking=(Button)view.findViewById(R.id.btnBack_CreateBooking);

        String[] pkgname = bookPkg.getPkgName().split(" ");
        switch(pkgname[0].toLowerCase()){
            case "polynesian":
                Glide.with(context).load(R.drawable.polynesia).fitCenter().into(ivPreviewConfirmBooking);
                break;
            case "asian":
                Glide.with(context).load(R.drawable.thailand).fitCenter().into(ivPreviewConfirmBooking);
                break;
            case "european":
                Glide.with(context).load(R.drawable.europe).fitCenter().into(ivPreviewConfirmBooking);
                break;
            case "caribbean":
                Glide.with(context).load(R.drawable.caribbean).fitCenter().into(ivPreviewConfirmBooking);
                break;
            default:
                Glide.with(context).load(R.drawable.tourism).fitCenter().into(ivPreviewConfirmBooking);
                break;
        }

        tvpkgName_booking.setText(bookPkg.getPkgName());
        tvpkgDesc_booking.setText(bookPkg.getPkgDesc());

        String[] startdateARRAY = bookPkg.getPkgStartDate().toString().split(" ");
        String startdate = startdateARRAY[0] + " " + startdateARRAY[1] + " " + startdateARRAY[2] + ", " + startdateARRAY[startdateARRAY.length-1];
        String[] enddateARRAY = bookPkg.getPkgEndDate().toString().split(" ");
        String enddate = enddateARRAY[0] + " " + enddateARRAY[1] + " " + enddateARRAY[2] + ", " + enddateARRAY[enddateARRAY.length-1];

        tvpkgDuration_booking.setText(startdate + " - " + enddate);

        NumberFormat priceformat = NumberFormat.getCurrencyInstance();
        String price = priceformat.format(bookPkg.getPkgBasePrice());
        tvpkgBasePrice_booking.setText("Per person: " + price);

        adapter = ArrayAdapter.createFromResource(view.getContext(),R.array.tripType_2,R.layout.spinner_item);
//        adapter.setDropDownViewResource(R.layout.spinner_item);
        sptripTypeId.setAdapter(adapter);
        sptripTypeId.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        //init value for travelerCount
        travelerCount = 1;
        ettravelerCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    travelerCount = Integer.valueOf(ettravelerCount.getText().toString());
                    if (travelerCount >= ConfigurationSet.TravelerGroupCount) {
                        adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.tripType_3, R.layout.spinner_item);
                        sptripTypeId.setAdapter(adapter);
                    } else {
                        adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.tripType_2, R.layout.spinner_item);
                        sptripTypeId.setAdapter(adapter);
                    }
                } catch (Exception e) {
                    ///
                    // No input, catch this!
                    ///
                }
            }
        });

        ivTravelerCountIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(ettravelerCount.getText().toString());
                count++;
                ettravelerCount.setText(String.valueOf(count), TextView.BufferType.EDITABLE);
            }
        });
        ivTravelerCountDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(ettravelerCount.getText().toString());
                if(count>1){
                    count--;
                }else{
                    count=1;
                }
                ettravelerCount.setText(String.valueOf(count), TextView.BufferType.EDITABLE);
            }
        });

        btnConfirmBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Booking b = new Booking();
                b.setBookingDate(Calendar.getInstance().getTime());
                b.setBookingNo("REPLACEMERESTSERVICE");
                b.setTravelerCount(travelerCount);
                b.setCustomerId(customerid);
                b.setTripTypeId(sptripTypeId.getSelectedItem().toString().substring(0,1));
                b.setPackageId(bookPkg.getPackageId());

                new createBooking(b,customer).execute();
            }
        });
        btnBack_CreateBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container_browse, BrowseFragment.getInstance())
                        .commit();
            }
        });

        return view;
    }
    class createBooking extends AsyncTask<Void, Void, Void> {
        private Booking b;
        private Customer c;
        createBooking(Booking booking,Customer customer){
            c=customer;
            b=booking;
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Type type = new TypeToken<Booking>(){}.getType();
                String JSONout = new Gson().toJson(b,type);

                URL url = new URL(CREATEBOOKINGURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("AUTHTOKEN", MainActivity.getToken());
                urlConnection.setRequestProperty("customerId", String.valueOf(customerid));
                urlConnection.setConnectTimeout(10000);
                urlConnection.connect();

                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(JSONout);
                out.close();

                responsecode=urlConnection.getResponseCode();
                urlConnection.disconnect();
            }catch(Exception e){

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(responsecode==200){
                Toast.makeText(getContext(),"You've booked your trip!", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container_browse,HomeFragment.newInstance(customerid,customer))
                        .commit();
            }
        }
    }
}