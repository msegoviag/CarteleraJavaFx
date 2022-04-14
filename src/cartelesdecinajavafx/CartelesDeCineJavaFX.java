package cartelesdecinajavafx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Miguel Segovia Gil
 *
 * Clase principal
 */
public class CartelesDeCineJavaFX implements Initializable {

    private TextField txtTitulo;
    @FXML
    private MenuBar menuCartel;
    @FXML
    private Menu menuArchivo;
    @FXML
    private MenuItem menuGuardar;
    @FXML
    private MenuItem menuSalir;
    @FXML
    private Menu menuEdicion;
    @FXML
    private MenuItem menuAdd;
    @FXML
    private MenuItem menuBorrar;
    @FXML
    private TableView<Cartel> tablaCartel;
    @FXML
    private ImageView imagenCartel;
    @FXML
    private Button botonVerCartel;

    private ObservableList<Cartel> carteles;
    @FXML
    private TableColumn tablaPelicula;
    @FXML
    private TableColumn tablaYear;
    @FXML
    private TableColumn tablaRutaCartel;
    private static boolean cambios = false;
    private static boolean datosGuardados = true;
    List<Cartel> list;

    /**
     * Método inicializador, localizamos y cargamos el archivo carteles.dat e
     * inicializamos la tabla al abrir la aplicación. Si el archivo no existe o
     * esta vacío no se iniciará.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        FileInputStream input;
        File file = new File("carteles.dat");
        try {

            if (file.length() == 0 && file.exists()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Informacion");
                alert.setContentText("¡El archivo carteles.dat está vacio!");
                alert.showAndWait();
                Platform.exit();

            } else {
                input = new FileInputStream(file);

                try (ObjectInputStream ois = new ObjectInputStream(input)) {
                    list = (List<Cartel>) ois.readObject();

                    this.tablaCartel.refresh();
                    this.tablaCartel.setItems(FXCollections.observableList(list));
                    this.tablaPelicula.setCellValueFactory(new PropertyValueFactory("nombre"));
                    this.tablaYear.setCellValueFactory(new PropertyValueFactory("anyo"));
                    this.tablaRutaCartel.setCellValueFactory(new PropertyValueFactory("ruta"));
                    input.close();
                }

            }

        } catch (FileNotFoundException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("No se ha encontrado el archivo carteles.dat."
                    + " No se han podido cargar los datos.");
            alert.showAndWait();
            Platform.exit();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("¡El archivo está dañado o no se puede leer");
            alert.showAndWait();
            Platform.exit();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CartelesDeCineJavaFX.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static void closeWindowEvent(WindowEvent e, Stage primaryStage) {
        if (cambios || datosGuardados == false) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.getButtonTypes().add(ButtonType.CANCEL);
            alert.getButtonTypes().add(ButtonType.YES);
            alert.setTitle("Salir de la aplicación");
            alert.setContentText(String.format("¿Salir sin guardar los cambios?"));
            alert.initOwner(primaryStage.getOwner());
            Optional<ButtonType> res = alert.showAndWait();

            if (res.isPresent()) {
                if (res.get().equals(ButtonType.CANCEL)) {
                    e.consume();
                }
            }
        }

    }

    @FXML
    private void verCarteles(ActionEvent event) throws FileNotFoundException {

        try {
            if (this.tablaCartel.getSelectionModel().getSelectedItem() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Informacion");
                alert.setContentText("¡No se ha seleccionado ningún cartel!");
                alert.showAndWait();
            } else {
                FileInputStream input = new FileInputStream(this.tablaCartel.getSelectionModel().getSelectedItem().getRuta());
                Image image = new Image(input);
                this.imagenCartel.setImage(image);
                if (this.imagenCartel.getImage().isError()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Informacion");
                    alert.setContentText("No se puede visualizar la imagen, formato inválido.");
                    alert.showAndWait();
                }
            }

        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("No se puede visualizar la imagen. Ruta inválida.");
            alert.showAndWait();

        }

    }

    @FXML
    private void salirApp(ActionEvent event) {

        if (cambios || datosGuardados == false) {
            Alert avisa = new Alert(Alert.AlertType.CONFIRMATION,
                    "Hay datos sin guardar. ¿Salir sin guardar los cambios?",
                    ButtonType.NO, ButtonType.YES);
            Optional<ButtonType> result = avisa.showAndWait();
            if (result.orElse(ButtonType.NO) == ButtonType.YES) {
                //Platform.exit();
                Stage stage = (Stage) this.menuCartel.getScene().getWindow();
                stage.close();
            }
        } else {
            Stage stage = (Stage) this.menuCartel.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void addCartel(ActionEvent event) {

        try {
            // Cargo la vista, el documento fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DialogAltaCartel.fxml"));

            // Cargo la ventana
            Parent root = loader.load();

            // Cojo el controlador
            DialogAltaCartel controlador = loader.getController();
            controlador.initAttributtes(carteles);

            // Creo la Escena
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Añadir cartel de película");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            // recojo el cartel que mando desde la otra ventana
            Cartel c = controlador.getCartel();
            if (c != null) {

                // Añado el cartel
                FXCollections.observableList(list).add(c);
                this.tablaCartel.setItems(FXCollections.observableList(list));
                // Refresco la tabla
                this.tablaCartel.refresh();
                cambios = true;

            }
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }

    }

    @FXML
    private void borrarCartel(ActionEvent event) {

        Cartel c = this.tablaCartel.getSelectionModel().getSelectedItem();

        if (c == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debes seleccionar un cartel");
            alert.showAndWait();
        } else {

            // Elimino el cartel
            Alert avisa = new Alert(Alert.AlertType.CONFIRMATION,
                    "¿Seguro que desea borrar este cartel",
                    ButtonType.NO, ButtonType.YES);
            Optional<ButtonType> result = avisa.showAndWait();
            if (result.orElse(ButtonType.NO) == ButtonType.YES) {
                FXCollections.observableList(list).remove(c);

                this.tablaCartel.setItems(FXCollections.observableList(list));
                this.tablaCartel.refresh();
                cambios = true;

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Información");
                alert.setContentText("Se ha borrado el cartel");
                alert.showAndWait();
            }

        }

    }

    @FXML
    private void guardarCarteles(ActionEvent event) throws IOException {

        try (FileOutputStream fos = new FileOutputStream("carteles.dat"); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(new ArrayList<>(list));
            datosGuardados = true;
            cambios = false;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("Datos guardados correctamente.");
            alert.showAndWait();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText("ERROR al guardar el cartel."
                    + "Comprueba los permisos o el directorio de guardado.");
            alert.showAndWait();
        }

    }

}
