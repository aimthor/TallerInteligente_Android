package com.example.tallerinteligente;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegistrarUsuario extends AppCompatActivity implements View.OnClickListener {
    private EditText txtNombre;
    private EditText txtApellido;
    private EditText txtDNI;
    private EditText txtCorreo;
    private EditText txtTelefono;
    private Button btnRegistro;
    private Button btnVolver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registro_usuario);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtDNI = findViewById(R.id.txtDNI);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtTelefono = findViewById(R.id.txtTelefono);

        btnRegistro = findViewById(R.id.btnRegistro);
        btnVolver = findViewById(R.id.btnVolver);
    }

    @Override
    public void onClick(View view) {

    }
}
