package com.example.gfa_printing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.gfa_printing.ServiceModel;

public class MainActivity extends AppCompatActivity {

    private Button btnSubmit;
    String responseText;
    Activity activity;
    ArrayList<ServiceModel> services=new ArrayList<ServiceModel>();
    private ProgressDialog progressDialog;
    ListView listView;

    //Base URL of our web service
    public static final String BASE_URL = "http://192.168.1.102:8080/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        listView = (ListView) findViewById(android.R.id.list);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                services.clear();

                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Chargement de services");
                progressDialog.setCancelable(false);
                progressDialog.show();
                //Call WebService
                getWebServiceResponseData();
            }
        });
    }

    protected Void getWebServiceResponseData() {

        //Creating Retrofit rest adapter
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Creating an object of our api interface
        ServiceArrayAPI api = retrofit.create(ServiceArrayAPI.class);
        Call<List<ServiceModel>> call = api.getCountries();

        call.enqueue(new Callback<List<ServiceModel>>() {

            @Override
            public void onResponse(Call<List<ServiceModel>> call, Response<List<ServiceModel>> response) {

                try {

                    services= (ArrayList)response.body();

                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    // For populating list data
                    ServiceList serviceList = new ServiceList(activity, services);
                    listView.setAdapter(serviceList);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Toast.makeText(getApplicationContext(),"You Selected "+services.get(position).getServiceName()+ " as service",Toast.LENGTH_SHORT).show();        }
                    });
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Call<List<ServiceModel>> call, Throwable t) {
                Log.d("Failure",t.toString());
            }
        });
        return null;
    }
}