package com.proyecto.sistemas.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "compras")
public class Compra implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idcompras;
    private LocalDate fecha;
    private Double total;
    private Integer idenvio;
    private Integer idusuario;

    public Compra() {
    }

    public Compra(LocalDate fecha, Double total, Integer idenvio, Integer idusuario) {
        this.fecha = fecha;
        this.total = total;
        this.idenvio = idenvio;
        this.idusuario = idusuario;
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

    @Override
    public String toString() {
        return "Compra{" +
                "idcompras=" + idcompras +
                ", fecha=" + fecha +
                ", total=" + total +
                ", idenvio=" + idenvio +
                ", idusuario=" + idusuario +
                '}';
    }
}
