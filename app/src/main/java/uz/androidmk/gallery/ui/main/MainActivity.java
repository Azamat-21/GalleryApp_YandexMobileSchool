package uz.androidmk.gallery.ui.main;

import android.app.ProgressDialog;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import butterknife.BindView;
import uz.androidmk.gallery.Base;
import uz.androidmk.gallery.R;
import uz.androidmk.gallery.data.db.DbHelper;
import uz.androidmk.gallery.data.db.model.Info;
import uz.androidmk.gallery.data.network.ConnectionChecker;
import uz.androidmk.gallery.ui.main.Feed.FeedFragment;
import uz.androidmk.gallery.ui.main.Files.FilesFragment;

/**
 * This Class has 3 main jobs
 * 1. To make the request
 * 2. Initialization of fragments with viewpager
 * 3. To notify fragments when the list from the request is ready
 */
public class MainActivity extends Base implements MainMvp.view{

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.main_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.constraintLayout)
    ConstraintLayout constraintLayout;

    @BindView(R.id.main_linear_layout)
    LinearLayout noInternetLayout;

    @BindView(R.id.btn_retry)
    Button btn_retry;

    ProgressDialog progressDialog;
    Snackbar snackbar;

    MainPresenter mainPresenter;
    ConnectionChecker connectionChecker;
    FilesFragment filesFragment;
    FeedFragment feedFragment;

    @Override
    protected void onCreated() {
        //Setting up toolbar
        setSupportActionBar(toolbar);
        toolbar.setTitle("Gallery");

        //initializations
        mainPresenter = new MainPresenter(this, this);
        connectionChecker = new ConnectionChecker(this);
        progressDialog = new ProgressDialog(this);
        snackbar = Snackbar.make(constraintLayout, "No internet Connection", Snackbar.LENGTH_LONG);

        //Connecting fragment adapter and viewpager with tab layout
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());

        feedFragment = FeedFragment.newInstance(1);
        fragmentAdapter.addFragment(feedFragment, "Feed");

        filesFragment = FilesFragment.newInstance(2);
        fragmentAdapter.addFragment(filesFragment, "Files");

        //adding the fragment adapter to the viewpager and set it up with tabLayout
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //Initial condition if no data is available in database
        if (mainPresenter.getListFromDatabase().isEmpty())
            initialRequest();

        //swipe to refesh to make request
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                noInternetLayout.setVisibility(View.GONE);
                makeRequest();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //Retry request button
        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noInternetLayout.setVisibility(View.GONE);
                initialRequest();
            }
        });

////        for Debugging purpose
//        final DbHelper dbHelper = DbHelper.getInstance(this);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dbHelper.deleteAllImages();
////                deleteDatabase("galleryDatabase");
//            }
//        });
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    //This method is to do initial request on Network
    private void initialRequest() {
        if (connectionChecker.isConnectionAvailable()) {
            //Progress dialog
            progressDialog.setTitle("Loading...");
            progressDialog.setMessage("Please wait.");
            progressDialog.setCancelable(false);
            progressDialog.show();
            //make the request
            mainPresenter.makeRequest();
        }
        else {
            snackbar.show();
            noInternetLayout.setVisibility(View.VISIBLE);
        }
    }

    //To make the network request for swipe to refresh
    //with checking connection state
    public void makeRequest() {

        if(connectionChecker.isConnectionAvailable()){
            mainPresenter.makeRequest();
        }else {
            snackbar.show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    //This method is invoked as soon as the list is ready from the network
    @Override
    public void requestListReady(ArrayList<Info> list) {
        progressDialog.dismiss();
        swipeRefreshLayout.setRefreshing(false);
        feedFragment.onListUpdated(list);
        filesFragment.onListUpdated(list);
    }




}
