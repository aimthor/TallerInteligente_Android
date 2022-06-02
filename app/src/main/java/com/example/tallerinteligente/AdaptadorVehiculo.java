package com.example.tallerinteligente;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdaptadorVehiculo extends RecyclerView.Adapter<AdaptadorVehiculo.VehiculoViewHolder> {

    List<Vehiculo> vehiculos;

    public AdaptadorVehiculo(List<Vehiculo> vehiculos) {
        this.vehiculos = vehiculos;
    }

    @NonNull
    @Override
    public VehiculoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linea_vehiculo,parent,false);
        VehiculoViewHolder holder = new VehiculoViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VehiculoViewHolder holder, int position) {
        Vehiculo vehiculo = vehiculos.get(position);
        holder.txtModelo.setText(vehiculo.get_modelo());
        holder.txtEstado.setText(vehiculo.get_estado());
        holder.txtMatricula.setText(vehiculo.get_matricula());
        holder.setImgMarca(vehiculo.get_marca());
    }

    @Override
    public int getItemCount() {
        return vehiculos.size();
    }

    public static class VehiculoViewHolder extends RecyclerView.ViewHolder{

        TextView txtModelo, txtEstado, txtMatricula;
        ImageView imgMarca;

        public VehiculoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtModelo = itemView.findViewById(R.id.txtModelo);
            txtMatricula = itemView.findViewById(R.id.txtMatricula);
            txtEstado = itemView.findViewById(R.id.txtEstado);
            imgMarca = itemView.findViewById(R.id.imgMarca);

        }

        public void setImgMarca(String marca) {
            if (marca == null)
            {

            }
            else
            {
                byte[]array = android.util.Base64.decode(marca, Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(array,0,array.length);
                imgMarca.setImageBitmap(bmp);
            }
        }
    }
}
