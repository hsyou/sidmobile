package com.example.sid.sidmobile.frame;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.example.sid.sidmobile.R;
import com.example.sid.sidmobile.VolleySingleton;

/**
 * Created by hs on 2016-01-09.
 */
public class FrameFragment extends Fragment {

    TextView list_view=null;

    private final String server_url="";

    public FrameFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frame_one, container, false);

        final RequestQueue queue= VolleySingleton.getInstance(getActivity()).getRequestQueue();

    }
}