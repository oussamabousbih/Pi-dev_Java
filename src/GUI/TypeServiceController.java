package GUI;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entities.TypesServices;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import services.CRUDTypesServices;

public class TypeServiceController implements Initializable {

    @FXML
    private TextField FXSearchTF;
    @FXML
    private ListView<TypesServices> FXlist;
    @FXML
    private Button FXadd;
    @FXML
    private Button FXUpdate;
    @FXML
    private Button FXDelete;

    private CRUDTypesServices crudTypesServices;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crudTypesServices = new CRUDTypesServices();
        // Populate the list view with the initial data
        List<TypesServices> typesServicesList = crudTypesServices.showTypesServices();
        FXlist.getItems().addAll(typesServicesList);
    }

    @FXML
    public void AddType(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddType.fxml"));
        Parent root = loader.load();
        AddTypeController atc = loader.getController();
        // Optional: pass any other necessary data to the controller using setters
        // e.g. atc.setSomeData(someValue);
        // ...
    }

    @FXML
    public void UpdateType(ActionEvent event) throws IOException {
        // Check if an item is selected
        int selectedIndex = FXlist.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            return;
        }

        // Get the selected item
        TypesServices selectedTypeService = FXlist.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateType.fxml"));
        Parent root = loader.load();
        /*UpdateTypeController utc = loader.getController();
        utc.setCrudTypesServices(crudTypesServices);
        utc.setSelectedTypeService(selectedTypeService);*/
        // Optional: pass any other necessary data to the controller using setters
        // e.g. utc.setSomeData(someValue);
        // ...

        // Show the update window
        // ...
    }
    
/*
    @FXML
    public void DeleteType(ActionEvent event) {
        // Check if an item is selected
        int selectedIndex = FXlist.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            return;
        }

        // Get the selected item
        TypesServices selectedTypeService = FXlist.getSelectionModel().getSelectedItem();

        // Delete the item using the CRUDTypesServices class
        crudTypesServices.deleteTypeServices(selectedTypeService);

        // Remove the item from the list view
        FXlist.getItems().remove(selectedIndex);
    }

*/
    
}
