/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.event;
import java.io.File;
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
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import services.eventService;

/**
 * FXML Controller class
 *
 * @author ThinkPad
 */
public class AfficherEventController implements Initializable {

    @FXML
    private TableColumn<event, Integer> fxid;
    @FXML
    private TableColumn<event, String> fxnom;
    @FXML
    private TableColumn<event, String> fxdiscription;
    @FXML
    private TableColumn<event, String> fximage;
    @FXML
    private TableColumn<event, String> fxdatedebut;
    @FXML
    private TableColumn<event, String> fxdatefin;
    @FXML
    private TableColumn<event, String> fxstatus;
    @FXML
    private TableColumn<event, String> fxadresse;
    @FXML
    private TableView<event> tfevent;
    @FXML
    private Button downloadButton;
    private ObservableList<event> obs;

    /**
     * Initializes the controller class.
     */
    @Override
  public void initialize(URL url, ResourceBundle rb) {
    eventService service = new eventService();
    try {
        List<event> e = service.recuperer();
            obs = FXCollections.observableArrayList(e);
        fxid.setCellValueFactory(new PropertyValueFactory<event, Integer>("id"));
        fxnom.setCellValueFactory(new PropertyValueFactory<event, String>("nom_event"));
        fxdiscription.setCellValueFactory(new PropertyValueFactory<event, String>("discription_event"));
        fximage.setCellValueFactory(new PropertyValueFactory<event, String>("image_event"));
        fxdatedebut.setCellValueFactory(new PropertyValueFactory<event, String>("date_debut_event"));
        fxdatefin.setCellValueFactory(new PropertyValueFactory<event, String>("date_fin_event"));
        fxadresse.setCellValueFactory(new PropertyValueFactory<event, String>("adresse_event"));
        fxstatus.setCellValueFactory(new PropertyValueFactory<event, String>("status"));
        tfevent.setItems(obs);
    } catch (SQLException ex) {
        Logger.getLogger(AfficherEventController.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    // Add event handler for download button
    downloadButton.setOnAction(event -> {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
           contentStream.setFont(PDType0Font.load(document, new File("c:/windows/fonts/Arial.ttf")), 12);
             contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);
            contentStream.showText("List of events:");
            contentStream.newLineAtOffset(0, -20);
            for (event ev : obs)
            {
                contentStream.showText(ev.getId() + " " + ev.getNom_event() + " " + ev.getDiscription_event() + " " + ev.getImage_event() + " " + ev.getDate_debut_event() + " " + ev.getDate_fin_event() + " " + ev.getAdresse_event() + " " + ev.getStatus());
                contentStream.newLineAtOffset(0, -20);
            }
            contentStream.endText();
            contentStream.close();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save PDF");
            fileChooser.setInitialFileName("document.pdf");
            File file = fileChooser.showSaveDialog(downloadButton.getScene().getWindow());
            if (file != null) {
                document.save(file);
            }
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    });
}
}