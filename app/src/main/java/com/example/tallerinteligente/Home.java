package com.example.tallerinteligente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements View.OnClickListener {
    TextView txtCorreo;
    String correo = "",uid = "";
    FloatingActionButton floatAgregarVehiculo;
    RecyclerView rv;
    List<Vehiculo> vehiculos;
    AdaptadorVehiculo adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        floatAgregarVehiculo = findViewById(R.id.floatAgregarVehi);
        txtCorreo = findViewById(R.id.txtCorreo);
        Bundle b = getIntent().getExtras();
        correo = b.getString("Correo", "correo:");
        uid = b.getString("uid","");
        txtCorreo.setText(correo.toString());
        floatAgregarVehiculo.setOnClickListener(this);

        rv = findViewById(R.id.rvVehiculos);

        rv.setLayoutManager(new LinearLayoutManager(this));

        vehiculos = new ArrayList<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        adapter = new AdaptadorVehiculo(vehiculos);

        rv.setAdapter(adapter);

        String ruta = "usuarios/users/"+uid+"/vehiculos";

        database.getReference(ruta).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                vehiculos.removeAll(vehiculos);
                for (DataSnapshot snapshot:
                     datasnapshot.getChildren()) {

                    Vehiculo vehiculo = snapshot.getValue(Vehiculo.class);
                    vehiculos.add(vehiculo);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this, "Failed to load comments.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}