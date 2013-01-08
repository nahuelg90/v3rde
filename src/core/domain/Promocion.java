package core.domain;

/**
 * @author Nahuel
 * @version 1.0
 * @created 27-Sep-2012 3:11:52 PM
 */
public class Promocion {

	private String descripcion;
	private int idPromocion;
	private String imagen_url;
	private String puntosNecesarios;
	private String titulo;

	public Promocion(int idPromocion, String titulo, String descrpicion, String puntosNecesarios, String imagen_url){
		this.idPromocion = idPromocion;
		this.titulo = titulo;
		this.descripcion = descrpicion;
		this.puntosNecesarios = puntosNecesarios;
		this.imagen_url = imagen_url;
	}

	/**
	 * 
	 * @param idPromocion
	 * @param idUsuario
	 */
	public void CanjearPromocion(int idPromocion, int idUsuario){

	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getIdPromocion() {
		return idPromocion;
	}

	public void setIdPromocion(int idPromocion) {
		this.idPromocion = idPromocion;
	}

	public String getImagen_url() {
		return imagen_url;
	}

	public void setImagen_url(String imagen_url) {
		this.imagen_url = imagen_url;
	}

	public String getPuntosNecesarios() {
		return puntosNecesarios;
	}

	public void setPuntosNecesarios(String puntosNecesarios) {
		this.puntosNecesarios = puntosNecesarios;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}//end Promocion