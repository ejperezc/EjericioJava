package com.usuarios.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.usuarios.models.entity.Telefono;

public interface ITelefonoDAO  extends CrudRepository<Telefono, Long>{

	

}
