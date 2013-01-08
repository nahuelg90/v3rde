package core.domain;

/**
 * @author Nahuel
 * @version 1.0
 * @created 27-Sep-2012 3:11:43 PM
 */
public class Cupon {

	private String descripcion;
	private String imagen_url;
	private int nroCupon;
	private String titulo;

	public Cupon(String descripcion, String imagen_url, int nroCupon, String titulo){
		this.descripcion = descripcion;
		this.imagen_url = imagen_url;
		this.nroCupon = nroCupon;
		this.titulo = titulo;
	}


	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImagen_url() {
		return imagen_url;
	}

	public void setImagen_url(String imagen_url) {
		this.imagen_url = imagen_url;
	}

	public int getNroCupon() {
		return nroCupon;
	}

	public void setNroCupon(int nroCupon) {
		this.nroCupon = nroCupon;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}//end Cupon