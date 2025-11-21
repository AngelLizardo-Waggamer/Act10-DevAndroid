package aahl.apiconsumerapp.ui.Episodios.LogicaRecyclerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import aahl.apiconsumerapp.R;
import aahl.apiconsumerapp.interfaces.EpisodeClickListener;
import aahl.apiconsumerapp.models.Episode;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeViewHolder> {

    private List<Episode> episodes;
    private EpisodeClickListener listener;

    public void setOnClickListener(EpisodeClickListener listener){
        this.listener = listener;
    }

    public EpisodeAdapter(List<Episode> episodes) {
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_episodio, parent, false);

        return new EpisodeViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        Episode episode = episodes.get(position);
        holder.tvNombreEpisodio.setText(episode.getName());
        holder.tvCodigoEpisodio.setText(episode.getEpisode());
        holder.tvFechaEmisionEpisodio.setText(episode.getAir_date());
    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }
}
