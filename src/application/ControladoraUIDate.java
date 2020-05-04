package application;


import java.time.format.DateTimeFormatter;

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


	ObservableList<Persona> datos = FXCollections.observableArrayList(

			new Persona("David", "01/05/2020"),
			new Persona("Cris", "05/06/2020")
			);



	public void initialize(){
		tabla.setItems(this.datos);

		col_nombre.setCellValueFactory(new PropertyValueFactory<Persona,String>("nombre"));
		col_cita.setCellValueFactory(new PropertyValueFactory<Persona,String>("cita"));

	}

	public void Guardar(){

		if( txtNombre.getText().equals("") || date_cita.getValue() == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Error en selección de datos");
			alert.setContentText("NO HAY NINGUN ELEMENTO SELECCIONADO!");
			alert.showAndWait();
			return;
		}

		DateTimeFormatter isoFecha = DateTimeFormatter.ISO_LOCAL_TIME;
		String fcita = date_cita.getValue().format(isoFecha);

		String nombre = txtNombre.getText();

		Persona nueva = new Persona(nombre,  fcita);
		datos.add(nueva);


	}




}