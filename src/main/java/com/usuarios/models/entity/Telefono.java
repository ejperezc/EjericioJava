package com.usuarios.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "telefonos")
public class Telefono implements Serializable {

	@Id
	@Column(nullable = false, name = "id_usuario")
	private Long idUsuario;

	private String number;
	@Column(name = "city_code")
	private String citycode;
	@Column(name = "contry_code")
	private String contrycode;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCitycode() {
		return citycode;
	}

	public void setCitycode(String citycode) {
		this.citycode = citycode;
	}

	public String getContrycode() {
		return contrycode;
	}

	public void setContrycode(String contrycode) {
		this.contrycode = contrycode;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
