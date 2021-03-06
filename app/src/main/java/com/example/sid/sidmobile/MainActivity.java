package com.example.sid.sidmobile;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.sid.sidmobile.design.DesignTab;
import com.example.sid.sidmobile.frame.FrameTab;
import com.example.sid.sidmobile.image.ImageTab;
import com.example.sid.sidmobile.info.InfoTab;
import com.example.sid.sidmobile.make.MakeTab;
import com.example.sid.sidmobile.member.FindFragment;
import com.example.sid.sidmobile.member.JoinFragment;
import com.example.sid.sidmobile.member.SignInFragment;
import com.example.sid.sidmobile.proxy.ProxyTab;
import com.example.sid.sidmobile.service.ServiceFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    DrawerLayout drawer;
    SessionManager sessionManager;
    Button btn_join;
    Button btn_signin;
    Button btn_logout;
    Button btn_find;
    TextView btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //session
        sessionManager=new SessionManager(this);
/*

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       /* fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
       */
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        View headerLayout =
                navigationView.getHeaderView(0);

        btn_join = (Button)headerLayout.findViewById(R.id.btn_join);

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                setTitle("Join");
                FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                xfragmentTransaction.replace(R.id.containerView, new JoinFragment()).commit();
            }
        });

       btn_signin = (Button)headerLayout.findViewById(R.id.btn_signIn);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                setTitle("Sign in");
                FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                xfragmentTransaction.replace(R.id.containerView, new SignInFragment()).commit();
            }
        });
        btn_find = (Button)headerLayout.findViewById(R.id.btn_find);
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
                setTitle("Find");
                FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                xfragmentTransaction.replace(R.id.containerView, new FindFragment()).commit();
            }
        });

        btn_close=(TextView) headerLayout.findViewById(R.id.btn_drawer_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawers();
            }
        });

        btn_logout=(Button)headerLayout.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logoutUser();
            }
        });


        if(!sessionManager.isLoggedIn()){

            btn_logout.setVisibility(View.VISIBLE);
            btn_join.setVisibility(View.GONE);

            Toast.makeText(MainActivity.this, "로그인중", Toast.LENGTH_SHORT).show();
        }else{
            btn_join.setVisibility(View.VISIBLE);
            btn_logout.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "안로그인중", Toast.LENGTH_SHORT).show();
        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawer.closeDrawers();

                if (menuItem.getItemId() == R.id.nav_info) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new InfoTab()).commit();
                }
                if (menuItem.getItemId() == R.id.nav_design) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new DesignTab()).commit();
                }
                if (menuItem.getItemId() == R.id.nav_frame) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new FrameTab()).commit();
                }
                if (menuItem.getItemId() == R.id.nav_image) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new ImageTab()).commit();
                }
                if (menuItem.getItemId() == R.id.nav_proxy) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new ProxyTab()).commit();
                }
                if (menuItem.getItemId() == R.id.nav_make) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new MakeTab()).commit();
                }
                if (menuItem.getItemId() == R.id.nav_service) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView, new ServiceFragment()).commit();
                }

                return false;
            }

        });


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        return false;
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}
