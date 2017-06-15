package com.proyecto.sistemas.model;


import java.util.Arrays;

public class Item {

    private Integer idproducto;
    private String nombre;
    private String categoria;
    private Integer cantidad;
    private Double precio;
    private String descripcion;
    private boolean descuento;

    public Item(Integer idproducto, String nombre, String categoria, Integer cantidad, Double precio, String descripcion) {
        this.idproducto = idproducto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public Item(Producto producto){
        this.idproducto = producto.getIdproducto();
        this.nombre = producto.getNombre();
        this.categoria = producto.getCategoria();
        this.cantidad = 1;
        this.precio = producto.getPrecio();
        this.descripcion = producto.getDescripcion();
    }

    public Item() {
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isDescuento() {
        return descuento;
    }

    public void setDescuento(boolean descuento) {
        this.descuento = descuento;
    }

    @Override
    public String toString() {
        return "Producto:\t\t" + nombre;
    }
}
