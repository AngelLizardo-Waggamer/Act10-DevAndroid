package aahl.apiconsumerapp.ui.Personajes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import aahl.apiconsumerapp.R;

public class CatalogoPersonajes extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_catalogo_personajes, container, false);

        return view;

    }

}
