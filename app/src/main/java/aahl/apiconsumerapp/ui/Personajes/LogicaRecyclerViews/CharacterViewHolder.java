package aahl.apiconsumerapp.ui.Personajes.LogicaRecyclerViews;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import aahl.apiconsumerapp.R;
import aahl.apiconsumerapp.interfaces.CharacterClickListener;

public class CharacterViewHolder extends RecyclerView.ViewHolder {

    public ImageView ivImagenPersonaje;
    public TextView tvNombrePersonaje;
    public TextView tvEspeciePersonaje;
    public TextView tvUltimaLocalizacionPersonaje;
    public Button btnDetallesPersonaje;

    public CharacterViewHolder(@NonNull View itemView, final CharacterClickListener listener) {
        super(itemView);
        ivImagenPersonaje = itemView.findViewById(R.id.ivImagen_item_personaje);
        tvNombrePersonaje = itemView.findViewById(R.id.tvNombre_item_personaje);
        tvEspeciePersonaje = itemView.findViewById(R.id.tvEspecie_item_personaje);
        tvUltimaLocalizacionPersonaje = itemView.findViewById(R.id.tvUltimaLocalizacion_item_personaje);
        btnDetallesPersonaje = itemView.findViewById(R.id.btnDetalles_item_personaje);

        btnDetallesPersonaje.setOnClickListener(v -> {
            if (listener != null) {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onCharacterClicked(position);
                }
            }
        });
    }

}

