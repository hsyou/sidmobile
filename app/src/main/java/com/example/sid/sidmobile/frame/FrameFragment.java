package com.example.sid.sidmobile.frame;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.sid.sidmobile.HomeFragment;
import com.example.sid.sidmobile.R;
import com.example.sid.sidmobile.VolleySingleton;
import com.example.sid.sidmobile.vo.Member;
import com.example.sid.sidmobile.vo.Result;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hs on 2016-01-09.
 */
public class FrameFragment extends Fragment {

    Gson gson;
    String jsonString;

    private final String server_url = "http://192.168.0.3:8080/mobile.jsp";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_frame_one, container, false);

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        Member member = new Member();


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


        return rootView;
    }

    public class FrameListAdapter extends BaseAdapter {
        private Activity activity;
        private LayoutInflater inflater;
        private ArrayList<com.example.sid.sidmobile.frame.FrameListAdapter> serviceDetailList;

        // Typeface tfRegular, tfLight;

        public FrameListAdapter(Activity activity,
                                ArrayList<com.example.sid.sidmobile.frame.FrameListAdapter> serviceDetailList) {
            this.activity = activity;
            this.serviceDetailList = serviceDetailList;
        }

        @Override
        public int getCount() {
            return serviceDetailList.size();
        }

        @Override
        public Object getItem(int position) {
            return serviceDetailList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            RatingBar ratingBar;
            TextView name;
            TextView location;
            TextView rating;
            NetworkImageView image;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final com.example.sid.sidmobile.frame.FrameListAdapter.ViewHolder holder;

            View view = convertView;
            if (convertView == null) {
                inflater = activity.getLayoutInflater();
                convertView = inflater.inflate(R.layout.fragment_frame_one, null);

                holder = new com.example.sid.sidmobile.frame.FrameListAdapter.ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.location = (TextView) convertView
                        .findViewById(R.id.location);
                holder.rating = (TextView) convertView
                        .findViewById(R.id.rating_text);
                holder.image = (NetworkImageView) convertView
                        .findViewById(R.id.task_image);
                holder.ratingBar = (RatingBar) convertView
                        .findViewById(R.id.rating_bar);
                convertView.setTag(holder);
            } else {
                holder = (com.example.sid.sidmobile.frame.FrameListAdapter.ViewHolder) convertView.getTag();
            }

            holder.name.setText(serviceDetailList.get(position).getTitle());
            holder.location.setText(serviceDetailList.get(position).getLocation());
            holder.rating.setText(serviceDetailList.get(position).getRating() + "");
            holder.ratingBar.setRating((float) serviceDetailList.get(position)
                    .getRating());
            // ////VOLLEY IMAGE LOADER
            String url = serviceDetailList.get(position).getImage();
            ImageLoader imageLoader = VolleySingleton.getImageLoader();

            imageLoader.get(url, new ImageListener() {

                public void onErrorResponse(VolleyError error) {
                    holder.image.setImageResource(R.drawable.ic_launcher);
                }

                public void onResponse(ImageContainer response, boolean arg1) {
                    if (response.getBitmap() != null) {
                        holder.image.setImageBitmap(response.getBitmap());
                    }
                }
            });

            Log.i("title", serviceDetailList.get(position).getTitle());
            Log.i("location", serviceDetailList.get(position).getLocation());
            Log.i("image", serviceDetailList.get(position).getImage());
            // SET TEXT AND IMAGE HERE

            return convertView;
        }
}