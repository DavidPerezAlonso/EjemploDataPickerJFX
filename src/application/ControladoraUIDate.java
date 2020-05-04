package application;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControladoraUIDate {

	@FXML
	private Button btnGrabar;


	@FXML
	private TableView<Persona> tabla;

	@FXML
	private TableColumn<Persona,String> col_nombre;

	@FXML
	private TableColumn<Persona,String> col_cita;

	@FXML
	private TextField txtNombre;

	@FXML
	private DatePicker date_cita;


	ObservableList<Persona> datos ;



	public void initialize() throws SQLException{

		datos = FXCollections.observableArrayList();

		ConexionBBDD con = new ConexionBBDD();
		datos = con.ObtenerPersonas();
		if(datos.size()!=0)
			tabla.setItems(this.datos);

		col_nombre.setCellValueFactory(new PropertyValueFactory<Persona,String>("nombre"));
		col_cita.setCellValueFactory(new PropertyValueFactory<Persona,String>("cita"));

	}

	public void Guardar() throws SQLException{

		if( txtNombre.getText().equals("") || date_cita.getValue() == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error en selección de datos");
			alert.setContentText("NO HAY NINGUN ELEMENTO SELECCIONADO!");
			alert.showAndWait();
			return;
		}

		DateTimeFormatter isoFecha = DateTimeFormatter.ISO_LOCAL_DATE;
		String fcita = date_cita.getValue().format(isoFecha);

		String nombre = txtNombre.getText();

		Persona nueva = new Persona(nombre,  fcita);
		ConexionBBDD con = new ConexionBBDD();
		con.InsertarPersona(nombre, fcita);
		datos = con.ObtenerPersonas();
		tabla.setItems(this.datos);


	}




}