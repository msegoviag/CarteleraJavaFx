/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cartelesdecinajavafx;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.stage.Stage;
import javafx.stage.FileChooser;

/**
 *
 * @author Miguel Segovia Gil
 */
public class DialogAltaCartel implements Initializable {

    @FXML
    private Button botonAceptar;
    @FXML
    private Button botonCancelar;
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtYear;
    @FXML
    private TextField txtRutaImagen;
    @FXML
    private Button botonElegirImagen;
    private Stage stagePrincipal;

    private Cartel cartel;

    private ObservableList<Cartel> carteles;

    public void setStagePrincipal(Stage stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void initAttributtes(ObservableList<Cartel> carteles) {
        this.carteles = carteles;
    }

    @FXML
    private void addAltaCartel(ActionEvent event) {

        /**
         * Cojo los datos de los TextField
         */
        try {
            if (this.txtTitulo.getText().isEmpty() || this.txtYear.getText().isEmpty() || this.txtRutaImagen.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Informacion");
                alert.setContentText("Hay campos de textos vacíos.");
                alert.showAndWait();
            }

            String nombreTitulo = this.txtTitulo.getText();
            int year = Integer.parseInt(this.txtYear.getText());
            String nombreRuta = this.txtRutaImagen.getText();

            Cartel c = new Cartel(nombreTitulo, year, nombreRuta);

            this.cartel = c;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("Se ha añadido el cartel correctamente");
            alert.showAndWait();

            Stage stage = (Stage) this.botonAceptar.getScene().getWindow();
            stage.close();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("El año introducido es incorrecto, introduce un número.");
            alert.showAndWait();
        }

    }

    @FXML
    private void cancelarAltaCartel(ActionEvent event) {

        Stage stage = (Stage) this.botonCancelar.getScene().getWindow();
        stage.close();

    }

    public Cartel getCartel() {
        return cartel;
    }

    @FXML
    private void botonRutaCartel(ActionEvent event) {

        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Todos los ficheros", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"));
            File file = fileChooser.showOpenDialog(stagePrincipal);
            if (file != null) {
                String ruta = file.getAbsolutePath();
                this.txtRutaImagen.setText(ruta);
            }

        } catch (Exception e) {

        }

    }

}
