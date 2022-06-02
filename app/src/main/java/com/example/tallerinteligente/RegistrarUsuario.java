package com.example.tallerinteligente;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuario extends AppCompatActivity implements View.OnClickListener {
    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtDNI;
    private EditText txtTelefono;
    private EditText txtCorreo;
    private EditText txtContrasena;
    private Button btnRegistro;
    private Button btnVolver;

    //Variables de los datos que voy a registrar
    private String nombre ="";
    private String apellido ="";
    private String dni ="";
    private String telefono ="";
    private String correo ="";
    private String contrasena ="";

    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference mDatabase;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registro_usuario);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference("usuarios");

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtDNI = findViewById(R.id.txtDNI);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtContrasena = findViewById(R.id.txtContrasena);

        btnRegistro = findViewById(R.id.btnRegistro);
        btnVolver = findViewById(R.id.btnVolver);
        btnRegistro.setOnClickListener(this);
        btnVolver.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnRegistro:
                nombre = txtNombre.getText().toString();
                apellido = txtApellido.getText().toString();
                dni = txtDNI.getText().toString();
                telefono = txtTelefono.getText().toString();
                correo = txtCorreo.getText().toString();
                contrasena = txtContrasena.getText().toString();

                if (!nombre.isEmpty() && !apellido.isEmpty() && !dni.isEmpty() && !correo.isEmpty() && !telefono.isEmpty())
                {
                    if (contrasena.length() >= 6){
                        registrarUsuario();
                    }
                    else{
                        Toast.makeText(this, R.string.minimo_contrasena, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(this, R.string.completar_campos, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnVolver:

                break;
        }
    }

    private void registrarUsuario() {
        mAuth.createUserWithEmailAndPassword(correo, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", nombre);
                    map.put("apellido", apellido);
                    map.put("dni",dni);
                    map.put("telefono",telefono);
                    map.put("correo",correo);
                    map.put("contrasena",contrasena);

                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(RegistrarUsuario.this, Home.class).putExtra("nombre", nombre).putExtra("correo",correo));
                                finish();
                            }
                            else {
                                if (task2.getException() instanceof FirebaseAuthUserCollisionException){ //Si se presenta una colision por usuario existente
                                    Toast.makeText(RegistrarUsuario.this, "Usuario Existente", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(RegistrarUsuario.this, R.string.error_registro, Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                else {
                    Toast.makeText(RegistrarUsuario.this, R.string.error_registro, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
