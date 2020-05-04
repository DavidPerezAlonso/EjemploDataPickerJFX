package application;
	import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ConexionBBDD {

	private String url= "";
	private   String user = "";
	private String pwd = "";
	private   String usr = "";
	private   Connection conexion;

	public ConexionBBDD()  {


		Properties propiedades = new Properties();
		InputStream entrada = null;
		try {
			File miFichero = new File("src/application/datos.ini");
			if (miFichero.exists()){
				entrada = new FileInputStream(miFichero);
				propiedades.load(entrada);
				url=propiedades.getProperty("url");
				user=propiedades.getProperty("user");
				pwd=propiedades.getProperty("pwd");
				usr=propiedades.getProperty("usr");
			}

			else
				System.out.println("Fichero no encontrado");
		} catch (IOException ex) {
			ex.printStackTrace();
		}finally {
			if (entrada != null) {
				try {
					entrada.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conexion = DriverManager.getConnection(url, user, pwd);

			if(conexion.isClosed())
				System.out.println("Fallo en Conexión con la Base de Datos");


		}catch (Exception e) {
			System.out.println("ERROR en conexión con ORACLE");
			e.printStackTrace();
		}
	}



	/*
	 * El método InsertarPersona devuelve un código de error para los siguientes casos:
	 *
	 * 0 - Persona insertada OK!
	 * 1 - Se ha queriro introducir uan persona con un email existente (Primary key violated)
	 * 2 - Otro fallo en el tipo de datos o en la base de datos al hacer la inserción
	 *
	 *
	 */
	public int InsertarPersona(String nombre, String fecha) throws SQLException{

		// Preparo la sentencia SQL
		String insertsql = "INSERT INTO " + usr +".CITAS VALUES (?,?,?)";

		PreparedStatement pstmt = conexion.prepareStatement (insertsql);
		pstmt.setString(1, nombre);
		pstmt.setString(2, fecha);
		pstmt.setDate(3,java.sql.Date.valueOf(fecha));

		//ejecuto la sentencia
		try{
			int resultado = pstmt.executeUpdate();

			if(resultado != 1)
				System.out.println("Error en la inserción " + resultado);
			else
				System.out.println("Persona insertada con éxito!!!");

			return 0;
		}catch(SQLException sqle){

			int pos = sqle.getMessage().indexOf(":");
			String codeErrorSQL = sqle.getMessage().substring(0,pos);

			if(codeErrorSQL.equals("ORA-00001") ){
				System.out.println("Ya existe una persona con  ese email!!");
				return 1;
			}
			else{
				System.out.println("Ha habido algún problema con  Oracle al hacer la insercion");
				return 2;
			}

		}

	}



	public ObservableList<Persona> ObtenerPersonas() throws SQLException{

		ObservableList<Persona> listapersonas = FXCollections.observableArrayList();

		//Preparo la conexión para ejecutar sentencias SQL de tipo update
		Statement stm = conexion.createStatement();

		// Preparo la sentencia SQL CrearTablaPersonas
		String selectsql = "SELECT * FROM " + usr +".CITAS";

		//ejecuto la sentencia
		try{
			ResultSet resultado = stm.executeQuery(selectsql);

			int contador = 0;
			while(resultado.next()){
				contador++;

				String nombre = resultado.getString(1);
				String fecha = resultado.getString(2);
				
				String fecha2 = resultado.getDate(3).toString();
				System.out.println(fecha2);

				Persona nueva = new Persona(nombre, fecha);
				listapersonas.add(nueva);
			}

			if(contador==0){
				System.out.println("no data found");
			}

		}catch(SQLException sqle){

			int pos = sqle.getMessage().indexOf(":");
			String codeErrorSQL = sqle.getMessage().substring(0,pos);

			System.out.println(codeErrorSQL);
		}

		return listapersonas;
	}




}

