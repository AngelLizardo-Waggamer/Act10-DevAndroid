package aahl.apiconsumerapp.ui.CatalogoLocations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import aahl.apiconsumerapp.R;

public class CatalogoLocations extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_catalogo_locations, container, false);

        return view;

    }
}