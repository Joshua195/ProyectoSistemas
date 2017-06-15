package com.proyecto.sistemas.model;

import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idventas;
    private String categoria;
    private Integer acumulado;

    public Integer getIdventas() {
        return idventas;
    }

    public void setIdventas(Integer idventas) {
        this.idventas = idventas;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getAcumulado() {
        return acumulado;
    }

    public void setAcumulado(Integer acumulado) {
        this.acumulado = acumulado;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "idventas=" + idventas +
                ", categoria='" + categoria + '\'' +
                ", acumulado=" + acumulado +
                '}';
    }
}
