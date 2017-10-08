package com.LocaliseFramework.application;

//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.LocaliseFramework.ranging.R;

/**
 * Created by Kevin on 29/09/2017.
 */

public class GraphFragment extends Fragment {
    private Graph graph;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph, container, false);
        graph = (Graph) view.findViewById(R.id.graph);
        return view;
    }

    public static GraphFragment getInstance() {
        GraphFragment fragment = new GraphFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }
}
