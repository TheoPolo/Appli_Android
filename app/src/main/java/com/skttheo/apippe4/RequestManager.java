package com.skttheo.apippe4;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequestManager {

    public static void showProduits(MainActivity mainActivity, ListView listView, View view){
        String url = "http://192.168.59.100:8090/boutique/public/api/produit";
        ArrayList<String> list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        for (JSONObject jsonObject : toJson(response)) {
                            try {
                                list.add("libelle : " + jsonObject.getString("libelle"));
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

    public static void showUsers(MainActivity mainActivity, ListView listView, View view){

        String url = "http://192.168.59.100:8090/boutique/public/api/user";

        ArrayList<String> list = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        for (JSONObject jsonObject : toJson(response)) {
                            try {
                                list.add("Nom : " + jsonObject.getString("nom"));
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
