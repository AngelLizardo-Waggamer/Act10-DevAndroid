package aahl.apiconsumerapp.ui.Episodios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import aahl.apiconsumerapp.R;

public class DetalleEpisodio extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.disp_detalle_episodio, container, false);

        return view;
    }

}
