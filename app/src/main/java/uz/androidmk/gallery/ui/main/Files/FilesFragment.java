package uz.androidmk.gallery.ui.main.Files;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uz.androidmk.gallery.R;
import uz.androidmk.gallery.data.db.model.Info;

/*
   This class aims to show all the recieved images in a smaller format
   with title and filesize, they are shown in linear layout.
*/

public class FilesFragment extends Fragment implements FilesMvp.view{

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage;


    RecyclerView recyclerView;
    FilesAdapter filesAdapter;
    ArrayList<Info> list;
    FilesPresenter filesPresenter;

    public FilesFragment() {
        // Required empty public constructor
    }

    public static FilesFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        FilesFragment fragment = new FilesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);

        filesPresenter = new FilesPresenter(getActivity());

        list = filesPresenter.getListFromDatabase();

        filesAdapter = new FilesAdapter(getActivity(), list);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflating the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_files, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.files_recycler_view);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(filesAdapter);
    }

    //This method is invoked from MainActivity when the network send the ready list
    //it will changer adapter list
    @Override
    public void onListUpdated(ArrayList<Info> list) {
        if (!this.list.isEmpty())
            this.list.clear();
        this.list.addAll(list);
        filesAdapter.notifyDataSetChanged();
    }


}
