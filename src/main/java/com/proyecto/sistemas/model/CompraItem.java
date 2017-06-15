package com.proyecto.sistemas.model;

import java.time.LocalDate;
import java.util.List;

public class CompraItem {
    private Integer idcompras;
    private LocalDate fecha;
    private Double total;
    private Integer idenvio;
    private Integer idusuario;
    private List<Producto> productos;

    public CompraItem() {
    }

    public CompraItem(Integer idcompras, LocalDate fecha, Double total, Integer idenvio, Integer idusuario, List<Producto> productos) {
        this.idcompras = idcompras;
        this.fecha = fecha;
        this.total = total;
        this.idenvio = idenvio;
        this.idusuario = idusuario;
        this.productos = productos;
    }

    public Integer getIdcompras() {
        return idcompras;
    }

    public void setIdcompras(Integer idcompras) {
        this.idcompras = idcompras;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getIdenvio() {
        return idenvio;
    }

    public void setIdenvio(Integer idenvio) {
        this.idenvio = idenvio;
    }

    public Integer getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Integer idusuario) {
        this.idusuario = idusuario;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "CompraItem{" +
                "idcompras=" + idcompras +
                ", fecha=" + fecha +
                ", total=" + total +
                ", idenvio=" + idenvio +
                ", idusuario=" + idusuario +
                ", productos=" + productos +
                '}';
    }
}
