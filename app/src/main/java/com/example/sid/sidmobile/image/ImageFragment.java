package com.example.sid.sidmobile.image;

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
import com.example.sid.sidmobile.vo.MemberVO;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hs on 2016-01-09.
 */
public class ImageFragment extends Fragment {
    TextView tv = null;
    String jsonString = null;
    private final String server_url = "http://192.168.0.28:8080/mobile.jsp";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_image_one, container, false);

        tv = (TextView) rootView.findViewById(R.id.textView);


        Gson gson = new Gson();
        MemberVO member = new MemberVO();
        member.setNickname("nick");
        member.setEmail("asd@asd.com");
        member.setAddress("here");

        jsonString = gson.toJson(member);
        Button btn = (Button) rootView.findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(getActivity());

                StringRequest postRequest = new StringRequest(Request.Method.GET, server_url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("success","done");
                                tv.setText(response.toString());

                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                Log.d("ERROR","error => "+error.toString());
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("abc", jsonString);

                        return params;
                    }
                };
                queue.add(postRequest);

            }
        });


        return rootView;
    }
}