package uz.androidmk.gallery.ui.main.Feed;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uz.androidmk.gallery.R;
import uz.androidmk.gallery.data.db.DbHelper;
import uz.androidmk.gallery.data.db.model.Info;
import uz.androidmk.gallery.data.network.ConnectionChecker;
import uz.androidmk.gallery.ui.main.MainActivity;

/**
 * This class is intended to show all recieved images in grid format
 * spanning 3 columns
 *
 * 
 *
 */
public class FeedFragment extends Fragment implements FeedMvp.view{

    public static final String ARG_PAGE = "ARG_PAGE";
    private String TAG = "FEED Fragment";
    private int mPage;

    ArrayList<Info> imagesList;
    RecyclerView recyclerView;
    FeedAdapter adapter;
    FeedPresenter feedPresenter;
    SwipeRefreshLayout swipeRefreshLayout;
    Snackbar snackbar;
    ConnectionChecker connectionChecker;

    public FeedFragment() {
        // Required empty public constructor
    }

    //Singleton instance of the fragment
    public static FeedFragment newInstance(int page){
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

        feedPresenter = new FeedPresenter(getActivity());

        imagesList = feedPresenter.getListFromDatabase();

        adapter = new FeedAdapter(getContext(), imagesList);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.feed_recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    //This method is invoked from MainActivity when the network send the ready list
    //it will changer adapter list
    @Override
    public void onListUpdated(ArrayList<Info> list) {
        if(!imagesList.isEmpty())
            imagesList.clear();
        imagesList.addAll(list);
        adapter.notifyDataSetChanged();
    }



}
