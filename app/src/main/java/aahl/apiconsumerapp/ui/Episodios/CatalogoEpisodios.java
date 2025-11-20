package aahl.apiconsumerapp.ui.Episodios;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import aahl.apiconsumerapp.R;

public class CatalogoEpisodios extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_catalogo_episodios, container, false);

        return view;
    }
}