package core.domain;

import java.util.List;



/**
 * @author Nahuel
 * @version 1.0
 * @created 27-Sep-2012 3:11:34 PM
 */
public class CentroDeReciclaje {

	private String descripcion;
	private String direccion;
	private int idCentroDeReciclaje;
	private String latitud;
	private String longitud;
	private List<Integer> tiposDeProducto;

	public CentroDeReciclaje(String descripcion, String direccion, int idCentroDeReciclaje, String latitud, String longitud, List<Integer> tiposDeProducto){
		this.setDescripcion(descripcion);
		this.setDireccion(direccion);
		this.setIdCentroDeReciclaje(idCentroDeReciclaje);
		this.setLatitud(latitud);
		this.setLongitud(longitud);
		this.tiposDeProducto = tiposDeProducto;
	}

	
	public void setTiposDeProducto(List<Integer> tiposDeProducto){
		this.tiposDeProducto = tiposDeProducto;
	}
	
	public List<Integer> getTiposDeProduct(){
		return this.tiposDeProducto;
	}

	public boolean atiendeTipoDeProducto(Integer idTipoDeProducto){
		return tiposDeProducto.contains(idTipoDeProducto);
	}


	public String getLongitud() {
		return longitud;
	}


	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}


	public String getLatitud() {
		return latitud;
	}


	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}


	public int getIdCentroDeReciclaje() {
		return idCentroDeReciclaje;
	}


	public void setIdCentroDeReciclaje(int idCentroDeReciclaje) {
		this.idCentroDeReciclaje = idCentroDeReciclaje;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}