package com.erict135.oosdteam4workshop8;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.erict135.oosdteam4workshop8.configurationset.ConfigurationSet;
import com.erict135.oosdteam4workshop8.model.Customer;
import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends Activity {

    private Button btnBrowse, btnLogin,btnRegister;
    private EditText etUsername, etPassword;
    private StringBuffer buffer;
    private String CustEmail, CustPassword;

    private String token = "";
    private int responsecode;

    private final String LOGINURL = ConfigurationSet.getRESTCustomerLoginUrl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        buffer = new StringBuffer();

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnRegister=(Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivityForResult(i,1);
            }
        });

        btnBrowse = (Button) findViewById(R.id.btnBrowse);
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                i.putExtra("loggedin", false);
                startActivity(i);
            }
        });
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustEmail = etUsername.getText().toString();
                CustPassword = etPassword.getText().toString();
                new CheckLogin().execute();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==RESULT_OK) {
            etUsername.requestFocus();
            etUsername.setText(data.getStringExtra("custemail"));
        }
    }

    class CheckLogin extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(LOGINURL);

                StringBuilder sb = new StringBuilder("");
                sb.append("{\"CustEmail\":\"");
                sb.append(CustEmail);
                sb.append("\",\"CustPassword\":\"");
                sb.append(CustPassword);
                sb.append("\"}");
                String jsonOUT=sb.toString();

                ///
                // Simple encode data before transmission.
                // To be decoded by webservice as Base64 encoded!
                // Room for a deeper encryption method
                ///
                jsonOUT=new String(Base64.encode(jsonOUT.getBytes(), Base64.DEFAULT));

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();

                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(jsonOUT);
                out.close();

                responsecode = urlConnection.getResponseCode();
                token = String.valueOf(urlConnection.getHeaderField("AUTHTOKEN"));
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line = "";
                while ((line = br.readLine()) != null) {
                    buffer.append(line);
                }
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
            try {
                Gson gson = new Gson();

                Customer customer = gson.fromJson(buffer.toString(), Customer.class);
                String customerJSON = buffer.toString();
                buffer.delete(0, buffer.length());

                if (responsecode==200) {
                    Toast.makeText(getApplicationContext(), customer.getCustFirstName() + ", you are logged in!", Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    Intent i = new Intent(getBaseContext(), MainActivity.class);
                    i.putExtra("customer", customerJSON);
                    i.putExtra("token",token);
                    i.putExtra("loggedin",true);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid credentials.", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Invalid credentials.", Toast.LENGTH_LONG).show();
            }
        }
    }
}