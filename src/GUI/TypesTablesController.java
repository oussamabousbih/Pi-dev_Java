/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Service;
import Entities.TypeService;
import Services.CRUDService;
import Services.CRUDTypeService;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Yasmine Rajhi
 */
public class TypesTablesController implements Initializable {

    @FXML
    private Button btn_overview;
    @FXML
    private Button btn_serv_page;
    @FXML
    private TextField search;
    @FXML
    private Button add_type;
    @FXML
    private Button ext;
    @FXML
    private ListView<TypeService> lst_type;
    @FXML
    private Button btn_update;
    @FXML
    private Button btn_delete;

    /**
     * Initializes the controller class.
     */
            private List<TypeService> type = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Select all elements from the database
        CRUDTypeService crud_ts = new CRUDTypeService();
        List<TypeService> services = crud_ts.showTypesServices();
    
        // Clear the list view and add the types to it
        lst_type.getItems().clear();
        services.forEach((s) -> {
            lst_type.getItems().add(s);
            });
    }    

    @FXML
    private void home(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) btn_overview.getScene().getWindow(); // get the current stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println("ghalta page change : " + ex.getMessage());
        }
    }

    @FXML
    private void serv_page(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ServicesTables.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) btn_serv_page.getScene().getWindow(); // get the current stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println("ghalta page change : " + ex.getMessage());
        }
    }

    @FXML
    private void search(ActionEvent event) {
        
    }

    @FXML
    private void add_type(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AddTypes.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) add_type.getScene().getWindow(); // get the current stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println("ghalta page change : " + ex.getMessage());
        }
    }

    @FXML
    private void extract_data(ActionEvent event) {
        // Get all the services from the database
    CRUDTypeService crud_s = new CRUDTypeService();
    List<TypeService> services = crud_s.showTypesServices();

    // Create a new workbook
        Workbook workbook = new XSSFWorkbook();

    // Create a new sheet in the workbook
        Sheet sheet = workbook.createSheet("Types");

    // Create a header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nom");
        headerRow.createCell(2).setCellValue("Description");

    // Create rows for each service and add data to them
        for (int i = 0; i < services.size(); i++) {
            TypeService service = services.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(service.getId());
            row.createCell(1).setCellValue(service.getNomType());
            row.createCell(2).setCellValue(service.getDescription());
        }

    // Write the workbook to an Excel file
        try {
            String filePath = "C:\\Users\\Yasmine Rajhi\\Downloads\\Types.xlsx";
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            System.out.println("Data extracted and saved to Types.xlsx");
        } catch (IOException ex) {
            System.err.println("Error extracting data: " + ex.getMessage());
        }
    }

    @FXML
    private void update(ActionEvent event) {
        // Get the selected TypeService from the list view
        TypeService selectedTypeService = lst_type.getSelectionModel().getSelectedItem();

        if (selectedTypeService != null) {
            try {
                // Create a new FXMLLoader
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateTypes.fxml"));

                // Load the FXML file
                Parent root = loader.load();

                // Get the controller for UpdateTypes.fxml
                UpdateTypesController updateTypesController = loader.getController();

                // Set the selected TypeService in UpdateTypesController
                updateTypesController.initData(selectedTypeService);

                // Create a new Scene with the root
                Scene scene = new Scene(root);

                // Get the current stage and set the Scene
                Stage stage = (Stage) btn_update.getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                System.err.println("Error loading UpdateTypes.fxml: " + ex.getMessage());
            }
        } else {
            // Show an error message if no TypeService is selected
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a type service to update.");
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
            alert.showAndWait();
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        // Get the selected service from the list view
        TypeService selectedService = (TypeService) lst_type.getSelectionModel().getSelectedItem();

        // Create a confirmation dialog box
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Deleting Service");
        alert.setContentText("Are you sure you want to delete this service?");

        // Customize the dialog box with some CSS
            DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("dialog.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

        // Wait for the user to respond to the dialog box
            Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // User clicked OK, proceed with delete operation

            // Delete the selected service from the database
            CRUDTypeService crud_s = new CRUDTypeService();
            crud_s.deleteTypesServices(selectedService);

            // Refresh the list view to reflect the changes
            lst_type.getItems().clear();
            List<TypeService> types = crud_s.showTypesServices();
            types.forEach((s) -> {
                lst_type.getItems().add(s);
            });
        }
    }   
}
