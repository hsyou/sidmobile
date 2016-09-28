package com.example.sid.sidmobile.frame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.sid.sidmobile.R;
import com.example.sid.sidmobile.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hs on 2016-01-09.
 */
public class FrameFragment extends Fragment {

    TextView list_view;


    private final String server_url = "http://192.168.0.3:10920/test.jsp";

    public FrameFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final RequestQueue queue = VolleySingleton.getInstance(getActivity()).getRequestQueue();

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_frame_one, container, false);

        list_view = (TextView) rootView.findViewById(R.id.imageView);

        Button btn = (Button) rootView.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, server_url,
                        new JSONObject(),
                        networkSuccessListener(),
                        networkErrorListener());

                queue.add(req);
            }

        });

        return rootView;
    }

    private Response.Listener<JSONObject> networkSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String from_server = null;
                try {
                    from_server = response.getString("test");

                } catch (JSONException e) {
                    Log.d("err","json Ex");
                    e.printStackTrace();
                }
                list_view.setText(from_server);
            }
        };
    }

    private Response.ErrorListener networkErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
                list_view.setText("errorrrrr");
                volleyError.printStackTrace();
                // Log.e("err", volleyError.getMessage());
            }
        };
    }


}