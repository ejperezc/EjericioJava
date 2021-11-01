package com.usuarios.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.usuarios.models.entity.Usuario;

public interface IUsuarioDAO  extends CrudRepository<Usuario, Long>{
	
	

}
