package com.example.sid.sidmobile.member;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.sid.sidmobile.R;

/**
 * Created by hs on 2016-01-09.
 */
public class JoinFragment extends Fragment {

    EditText et_nickname=null;
    EditText et_email=null;
    EditText et_password=null;
    EditText et_phone=null;
    EditText et_address=null;

    Button submit=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup)inflater.inflate(R.layout.fragment_join, container, false);

        et_nickname=(EditText)rootView.findViewById(R.id.et_nickname);
        et_email=(EditText)rootView.findViewById(R.id.et_email);
        et_password=(EditText)rootView.findViewById(R.id.et_password);
        et_phone=(EditText)rootView.findViewById(R.id.et_phone);
        et_address=(EditText)rootView.findViewById(R.id.et_address);

        submit=(Button)rootView.findViewById(R.id.btn_submit_join);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return rootView;
    }
}