package com.usuarios.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usuarios.helper.UsuarioHelper;
import com.usuarios.models.entity.Telefono;
import com.usuarios.models.entity.Usuario;
import com.usuarios.models.entity.UsuarioReq;
import com.usuarios.models.services.ITelefonoService;
import com.usuarios.models.services.IUsuarioService;

@RestController
@RequestMapping("/api")
public class UsuarioRestController {

	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private ITelefonoService telefonoService;
	
	
	@GetMapping("/usuarios")
	public ResponseEntity<?> index(@RequestHeader("username") String username,@RequestHeader("password") String password){
		Map<String, Object> response = new HashMap<>();
		
		if(!UsuarioHelper.validateUser(username,password)){
			response.put("mensaje", "sin autorizacion para realizar peticiones");
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<List<Usuario>>(usuarioService.findAll(),HttpStatus.OK);
	}
	
	@GetMapping("usuarios/{id}")
	public ResponseEntity<?> getUsuario(@RequestHeader("username") String username,@RequestHeader("password") String password,@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Usuario usuario = null;
		UsuarioReq req = null;
		
		if(!UsuarioHelper.validateUser(username,password)){
			response.put("mensaje", "sin autorizacion para realizar peticiones");
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.UNAUTHORIZED);
		}
		
		try {
			usuario = usuarioService.findById(id);	
			req = UsuarioHelper.setUsuarioToReq(usuario);
			req.setPhones(telefonoService.findAllById(id));
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la consulta a la bd");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(usuario == null) {
			response.put("mensaje", "Usuario con el ID: ".concat(id.toString().concat(" No existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<UsuarioReq>(req,HttpStatus.OK);
	}
	
	@PostMapping("usuarios")
	public ResponseEntity<?> create(@RequestHeader("username") String username,@RequestHeader("password") String password,@RequestBody UsuarioReq usuarioReq) {
		
		Usuario newUsuario = null;
		Usuario usuario = null;
		Telefono telefono = new Telefono();
		Map<String, Object> response = new HashMap<>();
		
		if(!UsuarioHelper.validateUser(username,password)){
			response.put("mensaje", "sin autorizacion para realizar peticiones");
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.UNAUTHORIZED);
		}
		
		try {
			
			usuario = UsuarioHelper.setUsuarioReq(usuarioReq);
			
			if(!UsuarioHelper.validaEmail(usuario.getEmail())) {
				response.put("mensaje", "El correo: ".concat(usuario.getEmail()).concat(" no tiene el formato correcto"));
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_ACCEPTABLE);
			}
			
			if(!UsuarioHelper.validaPass(usuario.getPassword())) {
				response.put("mensaje", "El password: ".concat(usuario.getPassword()).concat(" no tiene el formato correcto"));
				return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_ACCEPTABLE);
			}
			
			newUsuario = usuarioService.save(usuario);
			
			for (Telefono tel : usuarioReq.getPhones()) {
				telefono.setNumber(tel.getNumber());
				telefono.setCitycode(tel.getCitycode());
				telefono.setContrycode(tel.getContrycode());
				telefono.setIdUsuario(newUsuario.getId());
			}
			
			
		}catch(DataAccessException e) {
			response.put("mensaje", "El correo: ".concat(usuario.getEmail()).concat(" ya esta registrado"));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_ACCEPTABLE);
		}
		
		
		return new ResponseEntity<Usuario>(newUsuario,HttpStatus.CREATED);
	}
	
	@PutMapping("/usuarios/{id}")
	public ResponseEntity<?> update(@RequestHeader("username") String username,@RequestHeader("password") String password,@RequestBody UsuarioReq usuario, @PathVariable Long id) {
	
		Map<String, Object> response = new HashMap<>();
		
		Usuario usuarioBD = null; 
		Usuario usuarioUpdate = null;
		Telefono telefono = new Telefono();
		
		if(!UsuarioHelper.validateUser(username,password)){
			response.put("mensaje", "sin autorizacion para realizar peticiones");
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.UNAUTHORIZED);
		}
		
		usuarioBD = usuarioService.findById(id);
		if(null == usuarioBD) {
			response.put("mensaje", "Usuario con el ID: ".concat(id.toString().concat(" No existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		if(!UsuarioHelper.validaPass(usuario.getPassword())) {
			response.put("mensaje", "El password: ".concat(usuario.getPassword()).concat(" no tiene el formato correcto"));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_ACCEPTABLE);
		}
		
		if(!UsuarioHelper.validaEmail(usuario.getEmail())) {
			response.put("mensaje", "El correo: ".concat(usuario.getEmail()).concat(" no tiene el formato correcto"));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_ACCEPTABLE);
		}
		
		usuarioBD = UsuarioHelper.setUsuario(usuarioBD,usuario);		
		
		try {
			usuarioUpdate = usuarioService.save(usuarioBD);
			
			for (Telefono tel : usuario.getPhones()) {
				telefono.setNumber(tel.getNumber());
				telefono.setCitycode(tel.getCitycode());
				telefono.setCitycode(tel.getCitycode());
				telefono.setIdUsuario(usuarioUpdate.getId());
				telefonoService.save(telefono);
			}
			
		}catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar la actualización del Usuario");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("Usuario", usuarioUpdate);
		response.put("mensaje","Usuario con el ID: ".concat(id.toString()).concat(" actualizado correctamente"));		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
	@DeleteMapping("/usuarios/{id}")
	public ResponseEntity<?> delete(@RequestHeader("username") String username,@RequestHeader("password") String password,@PathVariable Long id) {
		
		Map<String, Object> response = new HashMap<>();
		Usuario usuarioBD = null; 
		
		if(!UsuarioHelper.validateUser(username,password)){
			response.put("mensaje", "sin autorizacion para realizar peticiones");
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.UNAUTHORIZED);
		}
		
		usuarioBD = usuarioService.findById(id);
		
		if(null == usuarioBD) {
			response.put("mensaje", "Usuario con el ID: ".concat(id.toString().concat(" No existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
		}
		
		usuarioService.delete(id);
		telefonoService.delete(id);
		response.put("mensaje","Usuario con el ID: ".concat(id.toString()).concat(" fue eliminado correctamente"));		
		
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}
	
}
