package application;

public class Persona {

	private String nombre;
	private String cita;


	public Persona(String nombre, String cita) {
		super();
		this.nombre = nombre;
		this.cita = cita;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getCita() {
		return cita;
	}


	public void setCita(String cita) {
		this.cita = cita;
	}




}
