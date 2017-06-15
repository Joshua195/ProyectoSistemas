package com.proyecto.sistemas.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pe")
public class PE implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idpe;
    private Integer idproducto;
    private Integer existencia;

    public PE() {
    }

    public PE(Integer idproducto, Integer existencia) {
        this.idproducto = idproducto;
        this.existencia = existencia;
    }

    public Integer getIdpe() {
        return idpe;
    }

    public void setIdpe(Integer idpe) {
        this.idpe = idpe;
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    public Integer getExistencia() {
        return existencia;
    }

    public void setExistencia(Integer existencia) {
        this.existencia = existencia;
    }

    @Override
    public String toString() {
        return "PE{" +
                "idpe=" + idpe +
                ", idproducto=" + idproducto +
                ", existencia=" + existencia +
                '}';
    }
}
