package aahl.apiconsumerapp.ui.Personajes.LogicaRecyclerViews;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.List;

import aahl.apiconsumerapp.R;
import aahl.apiconsumerapp.interfaces.CharacterClickListener;
import aahl.apiconsumerapp.models.Character;
import aahl.apiconsumerapp.models.LocationReference;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterViewHolder> {

    private List<Character> characters;
    private CharacterClickListener listener;

    public void setOnClickListener(CharacterClickListener listener) {
        this.listener = listener;
    }

    public CharacterAdapter(List<Character> characters) {
        this.characters = characters;
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_personaje, parent, false);
        return new CharacterViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Character character = characters.get(position);
        holder.tvNombrePersonaje.setText(character.getName());
        holder.tvEspeciePersonaje.setText(character.getSpecies());

        LocationReference location = character.getLocation();
        if (location != null && location.getName() != null) {
            holder.tvUltimaLocalizacionPersonaje.setText("Última localización: " + location.getName());
        } else {
            holder.tvUltimaLocalizacionPersonaje.setText("Última localización: Desconocida");
        }

        int radiusPx = (int) (9 * holder.ivImagenPersonaje.getContext().getResources().getDisplayMetrics().density);

        Glide.with(holder.ivImagenPersonaje.getContext())
                .load(character.getImage())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .centerCrop()
                .transform(new RoundedCorners(radiusPx))
                .into(holder.ivImagenPersonaje);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }
}
