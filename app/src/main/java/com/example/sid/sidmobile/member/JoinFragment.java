package com.example.sid.sidmobile.member;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sid.sidmobile.HomeFragment;
import com.example.sid.sidmobile.R;
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
public class JoinFragment extends Fragment {
    CheckBox chk_all, chk_must, chk_choice;
    CheckBox chk_must1, chk_must2, chk_choice1;
    Button join_submit;

    EditText nickname, email, password, password_check, phone, address;

    String jsonString = null;

    //
    Gson gson=null;
    private final String server_url = "http://192.168.0.3:8080/mobile.jsp";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_join, container, false);

        gson=new Gson();


        chk_all = (CheckBox) rootView.findViewById(R.id.chk_all);
        chk_must = (CheckBox) rootView.findViewById(R.id.chk_must);
        chk_choice = (CheckBox) rootView.findViewById(R.id.chk_choice);

        chk_must1 = (CheckBox) rootView.findViewById(R.id.chk_must1);
        chk_must2 = (CheckBox) rootView.findViewById(R.id.chk_must2);
        chk_choice1 = (CheckBox) rootView.findViewById(R.id.chk_choice1);

        nickname = (EditText) rootView.findViewById(R.id.nickname);
        email = (EditText) rootView.findViewById(R.id.email);
        password = (EditText) rootView.findViewById(R.id.password);
        password_check=(EditText)rootView.findViewById(R.id.password_check);
        phone = (EditText) rootView.findViewById(R.id.phone);
        address = (EditText) rootView.findViewById(R.id.address);


        //약관동의 전체
        chk_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_all.isChecked()) {
                    chk_must.setChecked(true);
                    chk_choice.setChecked(true);
                    chk_must1.setChecked(true);
                    chk_must2.setChecked(true);
                    chk_choice1.setChecked(true);
                } else if (!chk_all.isChecked()) {
                    chk_must.setChecked(false);
                    chk_choice.setChecked(false);
                    chk_must1.setChecked(false);
                    chk_must2.setChecked(false);
                    chk_choice1.setChecked(false);
                }
            }
        });

        //약관동의 필수
        chk_must.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_must.isChecked()) {
                    chk_must1.setChecked(true);
                    chk_must2.setChecked(true);
                } else if (!chk_must.isChecked()) {
                    chk_must1.setChecked(false);
                    chk_must2.setChecked(false);
                }
            }
        });

        //약관동의 선택
        chk_choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_choice.isChecked()) {
                    chk_choice1.setChecked(true);
                } else if (!chk_choice.isChecked()) {
                    chk_choice1.setChecked(false);
                }

            }
        });

        //회원가입 클릭시
        join_submit = (Button) rootView.findViewById(R.id.join_submit);
        join_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!(chk_must1.isChecked() && chk_must2.isChecked())) {
                    Toast.makeText(getContext(), "약관에 동의하셔야 합니다.", Toast.LENGTH_SHORT).show();

                } else if (nickname.length() < 4 || nickname.length() > 12) {
                    Toast.makeText(getContext(), "Nickname을 제대로 입력하세요.", Toast.LENGTH_SHORT).show();

                } else if (!isEmail(email.getText().toString())) {
                    Toast.makeText(getContext(), "E-Mail을 제대로 입력하세요", Toast.LENGTH_SHORT).show();

                } else if (CheckPassWord(password.getText().toString(), password_check.getText().toString()) == 1) {
                    Toast.makeText(getContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();

                } else if (CheckPassWord(password.getText().toString(), password_check.getText().toString()) == 2) {
                    Toast.makeText(getContext(), "입력하신 비밀번호와 비밀번호 확인이 일치하지 않습니다", Toast.LENGTH_SHORT).show();

                } else if (CheckPassWord(password.getText().toString(), password_check.getText().toString()) == 3) {
                    Toast.makeText(getContext(), "비밀번호는 문자, 숫자, 특수문자의 조합으로 6~16자리로 입력해주세요.", Toast.LENGTH_SHORT).show();

                } else if (CheckPassWord(password.getText().toString(), password_check.getText().toString()) == 4) {
                    Toast.makeText(getContext(), "비밀번호는 문자, 숫자, 특수문자의 조합으로 6~16자리로 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else {

                    RequestQueue queue = Volley.newRequestQueue(getActivity());

                    Member member = new Member();
                    member.setNickname(nickname.getText().toString());
                    member.setEmail(email.getText().toString());
                    member.setPwd(password.getText().toString());
                    member.setPhone(phone.getText().toString());
                    member.setAddress(address.getText().toString());
                    member.setAdmin(1);

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

                                    }else{
                                        Toast.makeText(getActivity(), "가입 실패.", Toast.LENGTH_SHORT).show();
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

                    getFragmentManager().beginTransaction().replace(R.id.containerView,new HomeFragment()).commit();
                }

            }
        });

        return rootView;
    }


    // email 체크
    public static boolean isEmail(String email) {
        if (email == "") return false;
        boolean b = Pattern.matches("[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", email.trim());
        return b;
    }

    /// 비밀번호 유효성 체크 (문자, 숫자, 특수문자의 조합으로 6~16자리)
    public static int CheckPassWord(String ObjUserPassWord, String objUserPassWordRe) {
        int result=0;
        Log.d("result","0");

        if (ObjUserPassWord.equals("")) {
            //"비밀번호를 입력해주세요"
            Log.d("result","1");
            return result = 1;

        } else if (!ObjUserPassWord.equals(objUserPassWordRe)) {
            //"입력하신 비밀번호와 비밀번호확인이 일치하지 않습니다"
            Log.d("result","2");
            return result = 2;
        } else if (ObjUserPassWord.length() < 6) {
            //"비밀번호는 문자, 숫자, 특수문자의 조합으로 6~16자리로 입력해주세요."
            Log.d("result","3");
            return result = 3;
        } else if (!ObjUserPassWord.matches("^.*(?=^.{6,16}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$")) {
            //"비밀번호는 문자, 숫자, 특수문자의 조합으로 6~16자리로 입력해주세요."
            Log.d("result","4");
            return result = 4;
        }

        return result;
    }

}