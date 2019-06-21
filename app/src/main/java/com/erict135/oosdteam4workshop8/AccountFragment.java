package com.erict135.oosdteam4workshop8;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myapps.a740657.travelexpertscustomer.configurationset.ConfigurationSet;
import com.myapps.a740657.travelexpertscustomer.model.Customer;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AccountFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private TextView tvAgentId;
    private EditText etCustFirstName,etCustLastName,etCustAddress,etCustCity,etCustProv,etCustCountry,etCustPostal;
    private EditText etCustEmail,etCustBusPhone,etCustHomePhone;
    private TextView tvTapForAgent;
    private ImageView ivEditCustomer,ivSaveCustomer;

    private static Customer c;
    private static AccountFragment accountFragment;
    private int responsecode;
    private Handler handler;
    private AlphaAnimation alphaAnimation;

    private static final String CUSTOMERUPDATEURL = ConfigurationSet.getRESTCustomerUpdateURL();
    private static final String CUSTOMERASSIGNAGENTURL = ConfigurationSet.getRESTCustomerAssignAgentURL();

    public AccountFragment() {
    }

    public static AccountFragment newInstance(int sectionNumber, Customer customer) {
        accountFragment= new AccountFragment();
        c=customer;
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        accountFragment.setArguments(args);
        return accountFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);

        tvAgentId=(TextView)rootView.findViewById(R.id.tvAgentId);
        etCustFirstName=(EditText)rootView.findViewById(R.id.etCustFirstName);
        etCustLastName=(EditText)rootView.findViewById(R.id.etCustLastName);
        etCustAddress=(EditText)rootView.findViewById(R.id.etCustAddress);
        etCustCity=(EditText)rootView.findViewById(R.id.etCustCity);
        etCustProv=(EditText)rootView.findViewById(R.id.etCustProv);
        etCustPostal=(EditText)rootView.findViewById(R.id.etCustPostal);
        etCustCountry=(EditText)rootView.findViewById(R.id.etCustCountry);
        etCustEmail=(EditText)rootView.findViewById(R.id.etCustEmail);
        etCustBusPhone=(EditText)rootView.findViewById(R.id.etCustBusPhone);
        etCustHomePhone=(EditText)rootView.findViewById(R.id.etCustHomePhone);
        tvTapForAgent=(TextView)rootView.findViewById(R.id.tvTapForAgent);

        ivEditCustomer=(ImageView)rootView.findViewById(R.id.ivEditCustomer);
        ivEditCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivEditCustomer.setBackgroundColor(Color.argb(100,50,50,50));
                ivSaveCustomer.setEnabled(true);
                ivEditCustomer.setEnabled(false);

                etCustFirstName.setEnabled(true);
                etCustLastName.setEnabled(true);
                etCustAddress.setEnabled(true);
                etCustCity.setEnabled(true);
                etCustProv.setEnabled(true);
                etCustCountry.setEnabled(true);
                etCustPostal.setEnabled(true);
                etCustEmail.setEnabled(true);
                etCustBusPhone.setEnabled(true);
                etCustHomePhone.setEnabled(true);
            }
        });
        ivSaveCustomer=(ImageView)rootView.findViewById(R.id.ivSaveCustomer);
        ivSaveCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivEditCustomer.setBackgroundColor(Color.argb(0,50,50,50));
                ivEditCustomer.setEnabled(true);
                ivSaveCustomer.setEnabled(false);

                Customer cUPDATE = new Customer();
                cUPDATE.setCustomerId(c.getCustomerId());
                cUPDATE.setAgentId(c.getAgentId());
                cUPDATE.setCustFirstName(etCustFirstName.getText().toString());
                cUPDATE.setCustLastName(etCustLastName.getText().toString());
                cUPDATE.setCustAddress(etCustAddress.getText().toString());
                cUPDATE.setCustCity(etCustCity.getText().toString());
                cUPDATE.setCustProv(etCustProv.getText().toString());
                cUPDATE.setCustCountry(etCustCountry.getText().toString());
                cUPDATE.setCustPostal(etCustPostal.getText().toString());
                cUPDATE.setCustHomePhone(etCustHomePhone.getText().toString());
                cUPDATE.setCustBusPhone(etCustBusPhone.getText().toString());
                cUPDATE.setCustEmail(etCustEmail.getText().toString());

                new updateCustomer(cUPDATE).execute();

                etCustFirstName.setEnabled(false);
                etCustLastName.setEnabled(false);
                etCustAddress.setEnabled(false);
                etCustCity.setEnabled(false);
                etCustProv.setEnabled(false);
                etCustCountry.setEnabled(false);
                etCustPostal.setEnabled(false);
                etCustEmail.setEnabled(false);
                etCustBusPhone.setEnabled(false);
                etCustHomePhone.setEnabled(false);
            }
        });

        tvAgentId.setText(String.valueOf(c.getAgentId()));
        etCustFirstName.setText(c.getCustFirstName());
        etCustLastName.setText(c.getCustLastName());
        etCustAddress.setText(c.getCustAddress());
        etCustCity.setText(c.getCustCity());
        etCustProv.setText(c.getCustProv());
        etCustCountry.setText(c.getCustCountry());
        etCustPostal.setText(c.getCustPostal());
        etCustEmail.setText(c.getCustEmail());
        etCustBusPhone.setText(c.getCustBusPhone());
        etCustHomePhone.setText(c.getCustHomePhone());

        if(c.getAgentId()==0){
            tvTapForAgent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AssignAgent(c).execute();
                }
            });
            tvTapForAgent.setVisibility(View.VISIBLE);
            alphaAnimation = new AlphaAnimation(1f, 0.2f);
            alphaAnimation.setDuration(2000);
            tvTapForAgent.setAnimation(alphaAnimation);
            handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    tvTapForAgent.animate();
                    alphaAnimation = new AlphaAnimation(1f, 0.2f);
                    alphaAnimation.setDuration(2000);
                    tvTapForAgent.setAnimation(alphaAnimation);
                    handler.postDelayed(this,2000);
                }
            };
            handler.postDelayed(runnable, 0);
        }else{
            tvTapForAgent.setVisibility(View.INVISIBLE);
        }

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    class AssignAgent extends AsyncTask<Void, Void, Void> {
        private Customer customer;
        private int agentid;
        public AssignAgent(Customer cust){
            customer=cust;
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(CUSTOMERASSIGNAGENTURL);

                Type type = new TypeToken<Customer>(){}.getType();
                String jsonOUT = new Gson().toJson(customer,type);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setConnectTimeout(10000);
                urlConnection.connect();

                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(jsonOUT);
                out.close();

                responsecode = urlConnection.getResponseCode();
                agentid = Integer.valueOf(urlConnection.getHeaderField("AGENTID"));
                urlConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.println(Log.DEBUG, "URL", "MALFORMED");
            } catch (IOException e) {
                e.printStackTrace();
                Log.println(Log.DEBUG, "IO", "IO");
            } catch (Exception e) {
                e.printStackTrace();
                Log.println(Log.DEBUG, "EXCEPTION", "EXCEPTION");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(responsecode==200){
                MainActivity.setAgentId(agentid);
                tvAgentId.setText(String.valueOf(agentid));
                tvTapForAgent.clearAnimation();
                tvTapForAgent.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(),"You've been assigned an agent!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(),"Something went wrong, contact admin", Toast.LENGTH_SHORT).show();
            }
        }
    }
    class updateCustomer extends AsyncTask<Void, Void, Void> {
        private Customer customer;
        public updateCustomer(Customer cust){
            customer=cust;
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(CUSTOMERUPDATEURL);

                Type type = new TypeToken<Customer>(){}.getType();
                String jsonOUT = new Gson().toJson(customer,type);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setConnectTimeout(10000);
                urlConnection.connect();

                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(jsonOUT);
                out.close();

                responsecode = urlConnection.getResponseCode();
                urlConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.println(Log.DEBUG, "URL", "MALFORMED");
            } catch (IOException e) {
                e.printStackTrace();
                Log.println(Log.DEBUG, "IO", "IO");
            } catch (Exception e) {
                e.printStackTrace();
                Log.println(Log.DEBUG, "EXCEPTION", "EXCEPTION");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(responsecode==200){
                Toast.makeText(getContext(),"Your profile has been updated", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(),"Update FAILED", Toast.LENGTH_SHORT).show();
            }
        }
    }
}