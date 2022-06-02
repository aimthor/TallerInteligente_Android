package com.example.tallerinteligente;

public class Vehiculo {
    String _marca;
    String _modelo;
    String _estado;
    String _matricula;

    public Vehiculo(){

    }

    public Vehiculo(String Marca, String Modelo, String Estado, String Matricula){
        _marca = Marca;
        _modelo = Modelo;
        _estado = Estado;
        _matricula = Matricula;
    }

    public String get_marca() {
        return _marca;
    }

    public String get_estado() {
        return _estado;
    }

    public String get_matricula() {
        return _matricula;
    }

    public String get_modelo() {
        return _modelo;
    }

    public void set_estado(String _estado) {
        this._estado = _estado;
    }

    public void set_marca(String _marca) {
        this._marca = _marca;
    }

    public void set_matricula(String _matricula) {
        this._matricula = _matricula;
    }

    public void set_modelo(String _modelo) {
        this._modelo = _modelo;
    }
}
