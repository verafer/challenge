package com.challenge.abc;

public class NewsDto {
    String fecha;
    String enlace;
    String enlaceFoto;
    String titulo;
    String resumen;

    public NewsDto() {
    }

    public NewsDto(String fecha, String enlace, String enlaceFoto, String titulo, String resumen) {
        this.fecha = fecha;
        this.enlace = enlace;
        this.enlaceFoto = enlaceFoto;
        this.titulo = titulo;
        this.resumen = resumen;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getEnlace() {
        return enlace;
    }
    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }
    public String getEnlaceFoto() {
        return enlaceFoto;
    }
    public void setEnlaceFoto(String enlaceFoto) {
        this.enlaceFoto = enlaceFoto;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getResumen() {
        return resumen;
    }
    public void setResumen(String resumen) {
        this.resumen = resumen;
    }
}