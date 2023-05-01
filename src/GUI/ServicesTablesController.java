/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import services.CRUDService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.util.List;
import entities.Service;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Yasmine Rajhi
 */
public class ServicesTablesController implements Initializable {

    @FXML
    private TextField search;
    @FXML
    private Button add_service;
    @FXML
    private Button ext;
    @FXML
    private ListView<Service> lst_service;
    @FXML
    private Button btn_update;
    @FXML
    private Button btn_delete;

    /**
     * Initializes the controller class.
     */
        private List<Service> services = new ArrayList<>();
    @FXML
    private Button btn_ov;
    @FXML
    private Button btn_serv;
    @FXML
    private Button btn_types;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Select all elements from the database
        CRUDService crud_s = new CRUDService();
        List<Service> services = crud_s.showServices();
    
        // Clear the list view and add the services to it
        lst_service.getItems().clear();
        services.forEach((s) -> {
            lst_service.getItems().add(s);
            });
    }    

    @FXML
    private void search_service(ActionEvent event) {
    
        String searchText = search.getText().toLowerCase(); // Convertir le texte en minuscules pour une recherche insensible à la casse
        ObservableList<Service> filteredList = FXCollections.observableArrayList();
        for (Service serv : services) {
            if (serv.getNomService().toLowerCase().contains(searchText) || serv.getProprietaire().toLowerCase().contains(searchText)) {
                filteredList.add(serv);
            }
        }
        lst_service.setItems(filteredList);

        if (filteredList.isEmpty()) {
            System.out.println("No services found for search query: " + searchText);
        } else {
            System.out.println("Filtered list:");
            for (Service service : filteredList) {
                System.out.println(service.getNomService());
            }
        }
    }

    @FXML
    private void add_form_redirect(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("AddForm.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) add_service.getScene().getWindow(); // get the current stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.err.println("ghalta page change : " + ex.getMessage());
        }
    }

    @FXML
    private void extract_data(ActionEvent event) {
        // Get all the services from the database
    CRUDService crud_s = new CRUDService();
    List<Service> services = crud_s.showServices();

    // Create a new workbook
        Workbook workbook = new XSSFWorkbook();

    // Create a new sheet in the workbook
        Sheet sheet = workbook.createSheet("Services");

    // Create a header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Nom");
        headerRow.createCell(2).setCellValue("Propriétaire");
        headerRow.createCell(3).setCellValue("Type");
        headerRow.createCell(4).setCellValue("Prix");
        headerRow.createCell(5).setCellValue("Date de début");
        headerRow.createCell(6).setCellValue("Date de fin");

    // Create rows for each service and add data to them
        for (int i = 0; i < services.size(); i++) {
            Service service = services.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(service.getId());
            row.createCell(1).setCellValue(service.getNomService());
            row.createCell(2).setCellValue(service.getProprietaire());
            row.createCell(3).setCellValue(service.getId_type());
            row.createCell(4).setCellValue(service.getPrix());
            row.createCell(5).setCellValue(service.getDate_debut().toString());
            row.createCell(6).setCellValue(service.getDate_fin().toString());
        }

    // Write the workbook to an Excel file
        try {
            String filePath = "C:\\Users\\Yasmine Rajhi\\Downloads\\services.xlsx";
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            System.out.println("Data extracted and saved to services.xlsx");
        } catch (IOException ex) {
            System.err.println("Error extracting data: " + ex.getMessage());
        }
    }

    /*private void update_redirect(ActionEvent event, Service service) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddForm.fxml"));
        Parent root = loader.load();
        UpdateFormController controller = loader.getController();
        controller.initData(service); // pass the service as a parameter to the controller
        Scene scene = new Scene(root);
        Stage stage = (Stage) btn_update.getScene().getWindow(); // get the current stage
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
            System.err.println("Error changing page: " + ex.getMessage());
        }
    }*/

    @FXML
    private void delete_service(ActionEvent event) {
        // Get the selected service from the list view
        Service selectedService = (Service) lst_service.getSelectionModel().getSelectedItem();

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
            CRUDService crud_s = new CRUDService();
            crud_s.deleteServices(selectedService);

            // Refresh the list view to reflect the changes
            lst_service.getItems().clear();
            List<Service> services = crud_s.showServices();
            services.forEach((s) -> {
                lst_service.getItems().add(s);
            });
        }
    }

    @FXML
    private void update_redirect(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddForm.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) btn_update.getScene().getWindow(); // get the current stage
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
            System.err.println("Error changing page: " + ex.getMessage());
        }
    }

    @FXML
    private void home(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) btn_ov.getScene().getWindow(); // get the current stage
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
            System.err.println("Error changing page: " + ex.getMessage());
        }
    }

    @FXML
    private void serv_page(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServicesTables.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) btn_serv.getScene().getWindow(); // get the current stage
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
            System.err.println("Error changing page: " + ex.getMessage());
        }
    }

    @FXML
    private void type_page(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TypesTables.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) btn_types.getScene().getWindow(); // get the current stage
        stage.setScene(scene);
        stage.show();
        } catch (IOException ex) {
            System.err.println("Error changing page: " + ex.getMessage());
        }
    }
    
}
