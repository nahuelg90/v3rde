package core.domain;

import java.util.List;

/**
 * @author Nahuel
 * @version 1.0
 * @created 27-Sep-2012 3:12:05 PM
 */
public class Usuario {

	private String apellido;
	private List<Cupon> cupones;
	private int idUsuario;
	private String mail;
	private String nombre;
	private String localidad;
	int puntajeDisponible;

	public Usuario(int idUsuario, String apellido, String mail, List<Cupon> cupones, String nombre, String localidad, int puntajeDisponible){

		this.setApellido(apellido);
		this.setCupones(cupones);
		this.setIdUsuario(idUsuario);
		this.setMail(mail);
		this.setNombre(nombre);
		this.setLocalidad(localidad);
		this.puntajeDisponible = puntajeDisponible; 
		
	}
	
	public int getPuntajeDisponible(){
		return puntajeDisponible;
	}
	
	public void setPuntajeDisponible(int puntaje){
		this.puntajeDisponible = puntaje;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public List<Cupon> getCupones() {
		return cupones;
	}

	public void setCupones(List<Cupon> cupones) {
		this.cupones = cupones;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	
}