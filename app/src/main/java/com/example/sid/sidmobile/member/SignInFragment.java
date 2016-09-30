package com.example.sid.sidmobile.member;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sid.sidmobile.HomeFragment;
import com.example.sid.sidmobile.R;
import com.example.sid.sidmobile.SessionManager;
import com.example.sid.sidmobile.service.ServiceFragment;
import com.example.sid.sidmobile.vo.Member;
import com.example.sid.sidmobile.vo.Result;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by hs on 2016-01-09.
 */
public class SignInFragment extends Fragment {

    EditText id=null;
    EditText pw=null;
    Gson gson = null;
    Member member=null;
    String jsonString=null;
    SessionManager session=null;
    Button btn=null;
    private final String server_url = "http://192.168.0.3:8080/mobile2.jsp";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_signin, container, false);

        id = (EditText) rootView.findViewById(R.id.et_login_id);
       pw = (EditText) rootView.findViewById(R.id.et_login_pw);

        gson = new Gson();
        member = new Member();



        btn=(Button)rootView.findViewById(R.id.btn_signIn_submit);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestQueue queue = Volley.newRequestQueue(getActivity());

                member.setEmail(id.getText().toString());
                member.setPwd(pw.getText().toString());

                jsonString = gson.toJson(member);

                JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.GET, server_url,null,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {
                                // response
                                Log.d("success","done");
                                Result result=gson.fromJson(response.toString(),Result.class);
                                if(result.getResult().equals("1")){
                                    session=new SessionManager(getContext());
                                    session.createLoginSession(id.getText().toString(),pw.getText().toString());
                                    Toast.makeText(getContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                                    getFragmentManager().beginTransaction().replace(R.id.containerView,new ServiceFragment()).commit();

                                }else{
                                    Toast.makeText(getActivity(), "로그인 실패.", Toast.LENGTH_SHORT).show();
                                }

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