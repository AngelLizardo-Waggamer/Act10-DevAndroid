package aahl.apiconsumerapp.ui.Episodios.LogicaRecyclerViews;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import aahl.apiconsumerapp.R;
import aahl.apiconsumerapp.interfaces.EpisodeClickListener;

public class EpisodeViewHolder extends RecyclerView.ViewHolder{

    public TextView tvNombreEpisodio;
    public TextView tvCodigoEpisodio;
    public TextView tvFechaEmisionEpisodio;
    public Button btnDetallesEpisodio;

    public EpisodeViewHolder(@NonNull View itemView, final EpisodeClickListener listener){
        super(itemView);
        tvNombreEpisodio = itemView.findViewById(R.id.tvNombre_item_episodio);
        tvCodigoEpisodio = itemView.findViewById(R.id.tvCodigo_item_episodio);
        tvFechaEmisionEpisodio = itemView.findViewById(R.id.tvFechaEmision_item_episodio);
        btnDetallesEpisodio = itemView.findViewById(R.id.btnDetalles_item_episodio);

        btnDetallesEpisodio.setOnClickListener(v -> {
            if (listener != null) {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onEpisodeClicked(position);
                }
            }
        });

    }

}
