package com.usuarios.models.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usuarios.models.dao.ITelefonoDAO;
import com.usuarios.models.entity.Telefono;

@Service
public class TelefonoServiceImpl implements ITelefonoService{

	@Autowired
	private ITelefonoDAO telefonoDAO;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Telefono> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Telefono findById(Long id) {

		return telefonoDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Telefono save(Telefono telefono) {
		// TODO Auto-generated method stub
		return telefonoDAO.save(telefono);
	}

	@Override
	@Transactional(readOnly = true)
	public void delete(Long id) {
		telefonoDAO.deleteById(id);
	}

	@Override
	public List<Telefono> findAllById(Long id) {
		List<Long> lista = new ArrayList<Long>();
		lista.add(id);
		return (List<Telefono>) telefonoDAO.findAllById(lista);
	}


	
}
