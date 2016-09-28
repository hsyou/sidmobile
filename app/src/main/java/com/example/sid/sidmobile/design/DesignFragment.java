package com.example.sid.sidmobile.design;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sid.sidmobile.R;
import com.example.sid.sidmobile.VolleySingleton;
import com.example.sid.sidmobile.vo.Member;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by hs on 2016-01-09.
 */
public class DesignFragment extends Fragment {

    private final String server_url = "http://192.168.0.28:8080/mobile.jsp";
    TextView tv=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_design_one, container, false);

        tv=(TextView)rootView.findViewById(R.id.textView);
        Gson gson = new Gson();
        Member member = new Member();
        member.setNickname("nick");
        member.setEmail("asd@asd.com");
        member.setAddress("here");
        String str=gson.toJson(member);
        StringRequest obj=new StringRequest(Request.Method.GET, server_url,new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                tv.setText(s.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                tv.setText("error");
            }
        });

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(obj);

        return rootView;
    }
}