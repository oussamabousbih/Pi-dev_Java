package GUI;

import Entities.TypesServices;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import services.CRUDTypesServices;

public class DeleteController implements Initializable {

    @FXML
    private Button CONFDEL;
    @FXML
    private Button NO;
    @FXML
    private ListView<TypesServices> FXlist;

    private CRUDTypesServices crudTypesServices;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Delete(ActionEvent event) {
        // Check if an item is selected
        int selectedIndex = FXlist.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            return;
        }

        // Get the selected item
        TypesServices selectedTypeService = FXlist.getSelectionModel().getSelectedItem();

        // Delete the item using the CRUDTypesServices class
        crudTypesServices.deleteTypesServices(selectedTypeService);

        // Remove the item from the list view
        FXlist.getItems().remove(selectedIndex);
    }

    @FXML
    private void Back(ActionEvent event) throws IOException {
        // Load the main menu FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TypeService.fxml"));
        Parent root = loader.load();

        // Show the main menu
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /*public void setCRUDTypesServices(CRUDTypesServices crudTypesServices) {
        this.crudTypesServices = crudTypesServices;
        FXlist.getItems().addAll(crudTypesServices.getTypeServicesList());
    }*/
}
