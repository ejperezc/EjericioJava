package com.usuarios.models.services;

import java.util.List;

import com.usuarios.models.entity.Telefono;

public interface ITelefonoService {
	
	public List<Telefono> findAll();
	
	public List<Telefono> findAllById(Long id);
	
	public Telefono findById(Long id);
	
	public Telefono save(Telefono telefono);
	
	public void delete(Long id);

}
