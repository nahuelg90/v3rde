package core.domain;

public class UserSession {
	
	private static Usuario usuario = null;
	
	public static boolean IsUserLooged(){
		return (usuario != null);
	}
	
	public static void LogUser(Usuario u){
		usuario = u;
	}
	
	public static Usuario getLoggesUser(){
		return usuario;
	}
	
	public static void LogOut(){
		usuario = null;
	}
	
	public static void SumarPuntos(int Puntos){
		usuario.setPuntajeDisponible(usuario.getPuntajeDisponible()+ Puntos);
	}
	
	public static void RestarPuntos(int Puntos){
		usuario.setPuntajeDisponible(usuario.getPuntajeDisponible() - Puntos);
	}
}
