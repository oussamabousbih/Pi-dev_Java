/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.event;
import entities.event_ticket;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.eventService;
import services.ticketService;

/**
 * FXML Controller class
 *
 * @author ThinkPad
 */
public class AfficheTicketController implements Initializable {

    @FXML
    private TableColumn<event_ticket, Integer> tid;
    @FXML
    private TableColumn<event_ticket, String> tmatricule;
    @FXML
    private TableColumn<event_ticket, Integer> teventid;
    @FXML
    private TableColumn<event_ticket, String> timage;
    @FXML
    private TableColumn<event_ticket, String> tdate;
    @FXML
    private TableColumn<event_ticket, String> tvalide;
    @FXML
    private TableColumn<event_ticket, String> tprix;
    @FXML
    private TableView<event_ticket> tsticket;
    @FXML
    private Button exportButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ticketService service = new ticketService();
        try {
            List<event_ticket>   e = service.recuperer();
           
              
                 ObservableList<event_ticket> obs = FXCollections.observableArrayList(e);
            tid.setCellValueFactory(new PropertyValueFactory<event_ticket, Integer>("id"));
            tmatricule.setCellValueFactory(new PropertyValueFactory<event_ticket, String>("matricule_event"));
            teventid.setCellValueFactory(new PropertyValueFactory<event_ticket, Integer>("event_id_id"));
            timage.setCellValueFactory(new PropertyValueFactory<event_ticket, String>("image"));
            tdate.setCellValueFactory(new PropertyValueFactory<event_ticket, String>("date_ticket"));
            tvalide.setCellValueFactory(new PropertyValueFactory<event_ticket, String>("valide_ticket"));
            tprix.setCellValueFactory(new PropertyValueFactory<event_ticket, String>("prix_ticket"));
        
            tsticket.setItems(obs);
            } catch (SQLException ex) {
                Logger.getLogger(AfficheTicketController.class.getName()).log(Level.SEVERE, null, ex);
            }
         exportButton.setOnAction(event -> {
            exportToExcel();
        });
    }

    private void exportToExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Event Tickets");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Matricule");
        headerRow.createCell(2).setCellValue("Event ID");
        headerRow.createCell(3).setCellValue("Image");
        headerRow.createCell(4).setCellValue("Date");
        headerRow.createCell(5).setCellValue("Valid");
        headerRow.createCell(6).setCellValue("Price");

        // Add data rows
        int rowNum = 1;
        for (event_ticket ticket : tsticket.getItems()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(ticket.getId());
            row.createCell(1).setCellValue(ticket.getMatricule_event());
            row.createCell(2).setCellValue(ticket.getEventID());
            row.createCell(3).setCellValue(ticket.getImage());
            row.createCell(4).setCellValue(ticket.getDate_ticket());
            row.createCell(5).setCellValue(ticket.getValide_ticket());
            row.createCell(  6).setCellValue(ticket.getPrix_ticket());
    }

    // Auto-size columns
    for (int i = 0; i < 7; i++) {
        sheet.autoSizeColumn(i);
    }

    // Write the output to a file
    try (FileOutputStream outputStream = new FileOutputStream("event_tickets.xlsx")) {
        workbook.write(outputStream);
    } catch (IOException e) {
        e.printStackTrace();
    }

    System.out.println("Excel file created successfully.");
}
}