package com.skttheo.apippe4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_layout);

        ListView view = findViewById(R.id.liste_fdp);
        ListView listView = findViewById(R.id.listeProduit);

        findViewById(R.id.boutonfdp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        if (MainActivity.categorie == 1) {
            showProduit(this, (ListView)findViewById(R.id.liste_fdp), view);
        } else {
            showUser(this, (ListView)findViewById(R.id.liste_fdp), view);
        }
    }
    public static void showUser(SelectActivity mainActivity, ListView listView, View view){
        System.out.println("fdp de merde" + MainActivity.ID);
        System.out.println("fdp de merde" + MainActivity.ID);
        System.out.println("fdp de merde" + MainActivity.ID);
        System.out.println("fdp de merde" + MainActivity.ID);
        System.out.println("fdp de merde" + MainActivity.ID);
        String url = "http://192.168.59.100:8090/boutique/public/api/user/" + MainActivity.ID ;

        ArrayList<String> list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        for (JSONObject jsonObject : toJson(response)) {
                            try {
                                list.add("Nom : " + jsonObject.getString("nom"));
                                list.add("Prémon : " + jsonObject.getString("prenom"));
                                list.add("Mail : " + jsonObject.getString("email"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_list_item_1, list);
                        listView.setAdapter(ArrayAdapter);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                list.add("erreur");
            }
        });
        Volley.newRequestQueue(mainActivity).add(stringRequest);
    }

    public static void showProduit(SelectActivity mainActivity, ListView listView, View view){

        String url = "http://192.168.59.100:8090/boutique/public/api/produit/" + MainActivity.ID ;

        ArrayList<String> list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        for (JSONObject jsonObject : toJson(response)) {
                            try {
                                list.add("Produit : " + jsonObject.getString("libelle"));
                                list.add("Tarif : " + jsonObject.getString("tarif"));
                                list.add("Description : " + jsonObject.getString("description"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_list_item_1, list);
                        listView.setAdapter(ArrayAdapter);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                list.add("erreur");
            }
        });
        Volley.newRequestQueue(mainActivity).add(stringRequest);
    }

    public static ArrayList<JSONObject> toJson(String response) {
        ArrayList<JSONObject> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getJSONObject(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
