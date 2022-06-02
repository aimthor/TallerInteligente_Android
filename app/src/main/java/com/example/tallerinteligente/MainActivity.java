package com.example.tallerinteligente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnLogin, btnRegistro;
    EditText txtUsername, txtPassword;
    private ProgressDialog progressDialog;

    //Declaro un objeto de firebaseAuth
    private FirebaseAuth mAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_TallerInteligente);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        btnLogin = findViewById(R.id.btnEntrar);
        btnLogin.setOnClickListener(this);

        btnRegistro = (Button)findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btnRegistro:
                startActivity(new Intent(this, RegistrarUsuario.class));
                break;
            case R.id.btnEntrar:
                iniciarSesion();
                break;
        }
    }

    private void iniciarSesion(){
        String correo = txtUsername.getText().toString().trim();
        String contrasena = txtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(correo)){
            Toast.makeText(this, "Usuario (Correo) vacío", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(contrasena)){
            Toast.makeText(this, "Contraseña vacía", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.signInWithEmailAndPassword(correo, contrasena).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, Home.class).putExtra("Correo", correo).putExtra("uid",mAuth.getUid()));
                    finish();
                }
                else {
                    Toast.makeText(MainActivity.this, "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });
    }
}
