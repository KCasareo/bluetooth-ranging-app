package com.kcasareo.application;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kcasareo.ranging.R;

/**
 * Created by Kevin on 29/09/2017.
 */

public class GraphFragment extends Fragment {
    private Graph graph;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.graph, container, false);
    }
}
