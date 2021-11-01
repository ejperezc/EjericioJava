package com.usuarios.helper;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import com.usuarios.models.entity.Telefono;
import com.usuarios.models.entity.Usuario;
import com.usuarios.models.entity.UsuarioReq;

public class UsuarioHelper {

	public static Usuario setUsuario(Usuario usuarioBD, UsuarioReq usuario) {
		usuarioBD.setName(usuario.getName());
		usuarioBD.setEmail(usuario.getEmail());
		usuarioBD.setPassword(usuario.getPassword());
		usuarioBD.setModified(new Date());
		usuarioBD.setLastLogin(new Date());
		return usuarioBD;
	}

	public static boolean validaEmail(String email) {
		String emailREGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";                               
        Pattern pattern = Pattern.compile(emailREGEX ); 
        if (email == null){ 
            return false; 
        }
        return pattern.matcher(email).matches();
	}

	public static boolean validaPass(String password) {
		String passREGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=(.*\\d){2})[a-zA-Z\\d]{4,}$";
						//	(?=(?:.*\d){2})(?=.*[A-Z])(?=.*[a-z])
        Pattern pattern = Pattern.compile(passREGEX); 
        if (password == null){ 
            return false; 
        }
        
        boolean flag = pattern.matcher(password).matches();
        
        return flag;
	}

	public static boolean validateUser(String username, String password) {
		if(username.equals("admin") && password.equals("admin123")) return true;
		return false;
	}

	public static List<Telefono> getListadoTelefono(Usuario usuario) {
		
		return null;
	}

	public static Usuario setUsuarioReq(UsuarioReq ur) {

		Usuario usuario = new Usuario();
		usuario.setName(ur.getName());
		usuario.setEmail(ur.getEmail());
		usuario.setPassword(ur.getPassword());
	
		return usuario;
	}

	public static UsuarioReq setUsuarioToReq(Usuario ur) {
		UsuarioReq usuario = new UsuarioReq();
		usuario.setName(ur.getName());
		usuario.setEmail(ur.getEmail());
		usuario.setPassword(ur.getPassword());
		return usuario;
	}
	


}
