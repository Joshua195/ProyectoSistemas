package com.proyecto.sistemas.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cp")
public class CP implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idcp;
    private Integer idcompra;
    private Integer idproducto;

    public CP(Integer idcompra, Integer idproducto) {
        this.idcompra = idcompra;
        this.idproducto = idproducto;
    }

    public Integer getIdcp() {
        return idcp;
    }

    public void setIdcp(Integer idcp) {
        this.idcp = idcp;
    }

    public Integer getIdcompra() {
        return idcompra;
    }

    public void setIdcompra(Integer idcompra) {
        this.idcompra = idcompra;
    }

    public Integer getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(Integer idproducto) {
        this.idproducto = idproducto;
    }

    @Override
    public String toString() {
        return "CPRepository{" +
                "idcp=" + idcp +
                ", idcompra=" + idcompra +
                ", idproducto=" + idproducto +
                '}';
    }
}
