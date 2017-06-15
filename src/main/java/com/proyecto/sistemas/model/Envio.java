package com.proyecto.sistemas.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "envio")
public class Envio implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idenvio;
    private String calle;
    private String colonia;
    private Integer enumero;
    private String ciudad;
    private String pais;
    private Integer cp;
    private String tel;

    public Envio(String calle, String colonia, Integer enumero, String ciudad, String pais, Integer cp, String tel) {
        this.calle = calle;
        this.colonia = colonia;
        this.enumero = enumero;
        this.ciudad = ciudad;
        this.pais = pais;
        this.cp = cp;
        this.tel = tel;
    }

    public Integer getIdenvio() {
        return idenvio;
    }

    public void setIdenvio(Integer idenvio) {
        this.idenvio = idenvio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public Integer getEnumero() {
        return enumero;
    }

    public void setEnumero(Integer enumero) {
        this.enumero = enumero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "Envio{" +
                "idenvio=" + idenvio +
                ", calle='" + calle + '\'' +
                ", colonia='" + colonia + '\'' +
                ", enumero=" + enumero +
                ", ciudad='" + ciudad + '\'' +
                ", pais='" + pais + '\'' +
                ", cp=" + cp +
                ", tel=" + tel +
                '}';
    }
}
