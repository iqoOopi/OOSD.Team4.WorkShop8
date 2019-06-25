package com.erict135.oosdteam4workshop8;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.erict135.oosdteam4workshop8.configurationset.ConfigurationSet;
import com.erict135.oosdteam4workshop8.model.Customer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends Activity {

    Button btnSignup,btnCancelRegister;
    EditText etCustFirstName,etCustLastName,etCustAddress,etCustProv,etCustPostal,etCustCity,etCustCountry,etCustEmail,etCustBusPhone,etCustHomePhone,etCustPassword,etPasswordConfirm,etEmailConfirm;

    private static final String CUSTOMERREGISTERURL = ConfigurationSet.getRESTCustomerRegisterURL();

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(RegisterActivity.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupUI(findViewById(R.id.register_activity));

        etCustFirstName=(EditText)findViewById(R.id.etCustFirstName);
        etCustLastName=(EditText)findViewById(R.id.etCustLastName);
        etCustAddress=(EditText)findViewById(R.id.etCustAddress);
        etCustCity=(EditText)findViewById(R.id.etCustCity);
        etCustProv=(EditText)findViewById(R.id.etCustProv);
        etCustPostal=(EditText)findViewById(R.id.etCustPostal);
        etCustCountry=(EditText)findViewById(R.id.etCustCountry);
        etCustHomePhone=(EditText)findViewById(R.id.etCustHomePhone);
        etCustBusPhone=(EditText)findViewById(R.id.etCustBusPhone);

        etCustPassword=(EditText)findViewById(R.id.etCustPassword);
        etCustEmail=(EditText)findViewById(R.id.etCustEmail);

        etPasswordConfirm=(EditText)findViewById(R.id.etPasswordConfirm);
        etEmailConfirm=(EditText)findViewById(R.id.etEmailConfirm);

        btnSignup=(Button)findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(Validate()){
                    Customer c = new Customer();

                    c.setCustomerId(0);
                    c.setCustFirstName(etCustFirstName.getText().toString());
                    c.setCustLastName(etCustLastName.getText().toString());
                    c.setCustAddress(etCustAddress.getText().toString());
                    c.setCustCity(etCustCity.getText().toString());
                    c.setCustProv(etCustProv.getText().toString());
                    c.setCustPostal(etCustPostal.getText().toString());
                    c.setCustCountry(etCustCountry.getText().toString());
                    c.setCustHomePhone(etCustHomePhone.getText().toString());
                    c.setCustBusPhone(etCustBusPhone.getText().toString());

                    c.setCustEmail(etCustEmail.getText().toString());
                    c.setPassword(etCustPassword.getText().toString());
                    c.setUserName(etCustEmail.getText().toString());
                    c.setAgentId(0);

                    new RegisterCustomer(c).execute();
                //}
            }
        });

        btnCancelRegister=(Button)findViewById(R.id.btnCancelRegister);
        btnCancelRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED,new Intent());
                finish();
            }
        });
    }
    private boolean Validate(){
        if(etCustAddress.getText().toString()==etPasswordConfirm.getText().toString()){return false;}
        if(etCustEmail.getText().toString()==etEmailConfirm.getText().toString()){return false;}
        return true;
    }
    class RegisterCustomer extends AsyncTask<Void, Void, Void> {
        private Customer c;
        private int responsecode;
        public RegisterCustomer(Customer customer){
            c=customer;
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Type type = new TypeToken<Customer>(){}.getType();
                String JSONout = new Gson().toJson(c,type);

                //URL url = new URL(CUSTOMERREGISTERURL);
                URL url = new URL("http:10.163.112.8:8080/Team4API/rest/customers/putcustomer");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("PUT");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setConnectTimeout(10000);
                urlConnection.connect();

                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(JSONout);
                out.close();

                responsecode = urlConnection.getResponseCode();
                urlConnection.disconnect();
            }catch(Exception e){

            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(responsecode==200){
                Toast.makeText(getApplicationContext(),"Account created successfully", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),"You may login!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                i.putExtra("custemail",c.getCustEmail());
                setResult(RESULT_OK,i);
                finish();
            }
        }
    }
}