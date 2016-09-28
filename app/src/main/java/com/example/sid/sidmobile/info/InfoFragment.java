package com.example.sid.sidmobile.info;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sid.sidmobile.R;
import com.example.sid.sidmobile.VolleySingleton;
import com.example.sid.sidmobile.vo.Member;
import com.google.gson.Gson;
import com.navercorp.volleyextensions.volleyer.Volleyer;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hs on 2016-01-09.
 */
public class InfoFragment extends Fragment {
    TextView tv = null;
    String jsonString = null;
    private final String server_url = "http://192.168.0.28:8080/mobile.jsp";
    Member member=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_image_one, container, false);

        tv = (TextView) rootView.findViewById(R.id.textView);

        Gson gson = new Gson();
        member = new Member();
        member.setNickname("nick");
        member.setEmail("asd@asd.com");
        member.setAddress("here");

        jsonString = gson.toJson(member);
        Button btn = (Button) rootView.findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  RequestQueue queue = Volley.newRequestQueue(getActivity());

                Volleyer.volleyer(queue).get(server_url)
                        .addHeader("header1", jsonString)
                        .withListener(new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(Member member) {
                                Log.d("w", "person : " + member);
                                tv.setText(response.toString());

                            }

                        })
                        .execute();
*/
            }
        });


        return rootView;
    }
}