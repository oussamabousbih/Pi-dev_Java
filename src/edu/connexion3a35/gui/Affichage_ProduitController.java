/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.connexion3a35.gui;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.connexion3a35.entities.Categorie;
import edu.connexion3a35.entities.Produit;
import edu.connexion3a35.services.CategorieCrud;
import edu.connexion3a35.services.ProduitCrud;
import edu.connexion3a35.services.SettingUpListView;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.Arrays;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


/**
 * FXML Controller class
 *
 * @author msi
 */
public class Affichage_ProduitController implements Initializable {

    /**
     * Initializes the controller class.
     */
    final ObservableList options = FXCollections.observableArrayList();
    public Button tf_pdf;

    @FXML
    private Button id_total;
    @FXML
    private ListView<Produit> list_view_produit;
    @FXML
    private Button id_ajouter;

    @FXML
    private TextField filterfield1;

    @FXML
    void ajouter(ActionEvent event) throws IOException {
        Stage stage = (Stage) id_ajouter.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Acceuil_page.fxml"));
        Parent root = loader.load();
        Acceuil_pageController categoriesController = loader.getController();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private Button id_modifier;

    @FXML
    private Button id_supprimer;

    List<Produit> listproduit = new ArrayList<>();
    List<Categorie> listcat = new ArrayList<>();
    private Produit produit;

    public ListView<Produit> getList_view_produit() {
        return list_view_produit;
    }

    public void setList_view_produit(ListView<Produit> list_view_produit) {
        this.list_view_produit = list_view_produit;
    }

    public Button getId_modifier() {
        return id_modifier;
    }

    public void setId_modifier(Button id_modifier) {
        this.id_modifier = id_modifier;
    }

    public Button getId_supprimer() {
        return id_supprimer;
    }

    public void setId_supprimer(Button id_supprimer) {
        this.id_supprimer = id_supprimer;
    }

    public List<Produit> getListproduit() {
        return listproduit;
    }

    public void setListproduit(List<Produit> listproduit) {
        this.listproduit = listproduit;
    }


    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getSelectedindex() {
        return selectedindex;
    }

    public void setSelectedindex(int selectedindex) {
        this.selectedindex = selectedindex;
    }

    int selectedindex;

    @FXML
    void delete_produit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Voulez-vous supprimer ?", ButtonType.APPLY,
                ButtonType.CANCEL);
        alert.setTitle("Information Dialog");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.APPLY) {
            new ProduitCrud().supprimerEntite(this.getProduit().getId());
        }

        list_view_produit.getItems().remove(selectedindex);
    }

    @FXML
    void modify_pro(ActionEvent event) throws IOException {
        Stage stage = (Stage) list_view_produit.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyProduit.fxml"));
        Parent root = loader.load();
        ModifyProduitController produitController = loader.getController();

        produitController.getTf_nomp().setText(produit.getNom_produit());
        produitController.getTf_prix().setText(String.valueOf(produit.getPrix()));
        produitController.getTf_quantite().setText(String.valueOf(produit.getQuantite()));
        produitController.getTd_desc().setText(produit.getDescription());
        produitController.getTf_image().setText(produit.getImage_produit());
        produitController.getTf_marque().setText(produit.getMarque());

        produitController.setProduit(produit);


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // TODO
        id_total.setVisible(false);
        id_modifier.setVisible(false);
        id_supprimer.setVisible(false);
        Produit produit1 = new Produit();
        ProduitCrud pr = new ProduitCrud();
        this.setListproduit(pr.listeDes());

        EventHandler<MouseEvent> customEvent = (event2) -> {
            // storing data to pass on PRODUCTManagement
            if (event2.getClickCount() == 1) {
                Produit produit = this.getList_view_produit().getSelectionModel().getSelectedItem();
                setProduit(produit);
                id_total.setVisible(true);
                id_modifier.setVisible(true);
                selectedindex = list_view_produit.getSelectionModel().getSelectedIndex();
                id_supprimer.setVisible(true);
                System.out.println("produit selected" + this.getProduit());

            }
        };

//list_view_produit.getItems().add(produit);
        //SettingUpListView.listObjectsOn_listView(list_view_produit, listproduit, customEvent);
        SettingUpListView.listProduit_inside_listView(list_view_produit, listproduit, customEvent);
        //rechereche avancee
        ProduitCrud per = new ProduitCrud();
        filterfield1.textProperty().addListener((obs, oldText, newText) -> {
            List<Produit> ae = per.Search(newText);
            list_view_produit.getItems().setAll(ae);});


    }
    public void notif(String title, String text) {
//    Image img = new Image("/gui/logofoot.png");
//    ImageView imgView = new ImageView(img);
//    imgView.setFitWidth(50); // adjust the size of the image as needed
//    imgView.setFitHeight(50);

        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                //.graphic(imgView) // set the image as the graphic for the notification
                .hideAfter(Duration.seconds(7))
                .position(Pos.BOTTOM_RIGHT);

        notificationBuilder.show();
    }
    Stage stage;
    Parent root;
    Scene scene;
    @FXML
    void Stats(ActionEvent event) throws IOException {


            root = FXMLLoader.load(getClass().getResource("Stats.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();


    }
    @FXML
    void calculerT(ActionEvent event) {
        int num = list_view_produit.getItems().size();
        Integer tot = 0;
       for (int i = 0; i < num; i++) {
            Integer val = list_view_produit.getItems().get(i).getQuantite();
            tot += val;

        }

        notif("Nombre disponible de produits","Votre produits disponible   est :\n" +Integer.toString(tot));


        }

// sort the list by price

    @FXML
    void Trier(ActionEvent event) {

            List<Produit> listProduit = list_view_produit.getItems();
            List<Produit> sortedList = listProduit.stream()
                    .sorted(Comparator.comparingDouble(Produit::getPrix))
                    .collect(Collectors.toList());
            list_view_produit.getItems().clear(); // Clear previous items
            list_view_produit.getItems().addAll(sortedList); // Add sorted items



    }


    public void pdf(ActionEvent actionEvent) {
        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream("table.pdf"));
            document.open();

            // Define column widths
            float[] columnWidths = {1f, 2f, 1.5f, 1.5f, 1.5f, 2f, 2f, 1.5f};

            // Create PDF table and set column widths
            PdfPTable pdfTable = new PdfPTable(columnWidths.length);
            pdfTable.setWidths(columnWidths);
            pdfTable.setWidthPercentage(100);

            // Set table title and style
            Paragraph title = new Paragraph("Liste des produits", FontFactory.getFont(FontFactory.TIMES_BOLD, 18, Font.BOLD));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(Chunk.NEWLINE);

            // Add table header and rows
            addTableHeader(pdfTable);
            addRows(pdfTable);

            document.add(pdfTable);

            document.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF");

            alert.setHeaderText("PDF");
            alert.setContentText("Enregistrement effectué avec succès!");

            alert.showAndWait();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
    private void addTableHeader(PdfPTable pdfTable) {
        Stream.of("ID", "Nom", "Prix", "Quantité", "Marque", "Description", "Image", "Catégorie")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    header.setPhrase(new Phrase(columnTitle, FontFactory.getFont(FontFactory.TIMES_BOLD, 14, Font.BOLD)));
                    pdfTable.addCell(header);
                });
    }

    private void addRows(PdfPTable pdfTable) throws BadElementException, IOException {
        for (Produit produit : listproduit) {
            pdfTable.addCell(String.valueOf(produit.getId()));
            pdfTable.addCell(produit.getNom_produit());
            pdfTable.addCell(Float.toString(produit.getPrix()));
            pdfTable.addCell(produit.getQuantite()+"");
            pdfTable.addCell(produit.getMarque());
            pdfTable.addCell(produit.getDescription());

            // Check if image file exists and is a recognized format
            String imagePath = "C:\\Users\\msi\\Desktop\\Connexion3A17\\src\\images\\" + produit.getImage_produit();
            File imageFile = new File(imagePath);
            if (imageFile.exists() && (imagePath.endsWith(".jpg") || imagePath.endsWith(".jpeg") || imagePath.endsWith(".png") || imagePath.endsWith(".gif"))) {
                // Add image cell
                PdfPCell imageCell = new PdfPCell();
                Image image = Image.getInstance(imagePath);
                imageCell.addElement(image);
                pdfTable.addCell(imageCell);
            } else {
                // Add empty cell
                pdfTable.addCell("");
            }

            pdfTable.addCell(String.valueOf(produit.getCategorie_produit()));
        }
    }




}
