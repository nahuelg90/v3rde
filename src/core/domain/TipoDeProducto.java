package core.domain;

/**
 * @author Nahuel
 * @version 1.0
 * @created 27-Sep-2012 3:11:59 PM
 */
public class TipoDeProducto {

	private String tr_url;
	private int idTipoDeProducto;
	private String infografia_url;
	private String nombre;
	
	public TipoDeProducto(String tr_url, int idTipoDeProducto, String infografia_url, String nombre){	
		this.tr_url = tr_url;
		this.idTipoDeProducto = idTipoDeProducto;
		this.infografia_url = infografia_url;
		this.nombre = nombre;
	}


	public String getTr_url() {
		return tr_url;
	}

	public void setTr_url(String tr_url) {
		this.tr_url = tr_url;
	}

	public int getIdTipoDeProducto() {
		return idTipoDeProducto;
	}

	public void setIdTipoDeProducto(int idTipoDeProducto) {
		this.idTipoDeProducto = idTipoDeProducto;
	}

	public String getInfografia_url() {
		return infografia_url;
	}

	public void setInfografia_url(String infografia_url) {
		this.infografia_url = infografia_url;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}//end TipoDeProducto