/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.gestion_rdv.services;

//********IO-stream****************
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//*************PDF************************
import java.util.Calendar;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import entities.DummyUser;
import entities.gestion_rdv.DummyCalandar;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import services.gestion_rdv.CrudCalandarDay;
import services.gestion_rdv.exceptions.ConnectionOrPrepareStatmentException;

/**
 *
 * @author rbaih
 */
public class CalandarPDF {

     public void PdfCalandarGenerated(DummyUser doctor) {

        try {

            // Create a new document object
            Document document = new Document();
            //path to user/downloads
            String pathUserDownloads = System.getProperty("user.home")+ "/Downloads/";
            // Set the output file path and name
            PdfWriter.getInstance(document, new FileOutputStream(pathUserDownloads+"calendarPdf"
                    + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ".pdf"));

            // Open the document for writing
            document.open();

            // Create a small side title for date of request
            
            Font smallTitleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.UNDERLINE, new BaseColor(11, 61, 82));
            Paragraph dateOfRequest = new Paragraph("Your Previous Calandars requested on " + LocalDate.now(), smallTitleFont);
            dateOfRequest.setAlignment(Element.ALIGN_CENTER);

            //adding space before and after the paragraph
            dateOfRequest.setSpacingAfter(200);
            // Add the title to the document
            document.add(dateOfRequest);

            // Add the logo to the document
            Image logo;
            try {
                logo = Image.getInstance("src/resources/images/logo.png");
                logo.scaleAbsolute(150f, 150f);
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
            } catch (BadElementException | IOException ex) {
                ex.printStackTrace();
            }

            // getting all previous calandars :
            Collection<DummyCalandar> calandarArray;
            try {
                calandarArray = new CrudCalandarDay().getcalandar_Pdf_Excel(doctor.getId());
            } catch (ConnectionOrPrepareStatmentException ex) {
                calandarArray = new ArrayList<>(); // case of failure make it empty   
                ex.printStackTrace();
            }

// looping all calandars :
            for (DummyCalandar calandar : calandarArray) {
                //go next page first
                document.newPage();

                // Create a new table for the calendar's SLots
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10f);
                table.setSpacingAfter(10f);
                table.setHorizontalAlignment(Element.ALIGN_CENTER);

                // Set Up header With Calandar Date
                Font dateHeaderFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, new BaseColor(255, 255, 255));
                PdfPCell cell;
                cell = new PdfPCell(new Phrase(calandar.getDate().format(DateTimeFormatter.ISO_DATE), dateHeaderFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor(new BaseColor(40, 120, 82));
                cell.setColspan(5);
                table.addCell(cell);

                // SetUp the header row to the table
                Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, new BaseColor(255, 255, 255));
                cell = new PdfPCell(new Phrase("Time", headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor(new BaseColor(40, 61, 82));
                table.addCell(cell);
                //
                cell = new PdfPCell(new Phrase("Status", headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor(new BaseColor(40, 61, 82));
                table.addCell(cell);
                //
                cell = new PdfPCell(new Phrase("Reason", headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor(new BaseColor(40, 61, 82));
                table.addCell(cell);
                //
                cell = new PdfPCell(new Phrase("Note", headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor(new BaseColor(40, 61, 82));
                table.addCell(cell);
                //
                cell = new PdfPCell(new Phrase("Appointment", headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setBackgroundColor(new BaseColor(40, 61, 82));
                table.addCell(cell);

                // loop slots to fill up rows 
                int i = 0;
                for (DummyCalandar.Slot slot : calandar.getSlots()) {

                    // Add table content
                    Font contentFont = new Font(Font.FontFamily.HELVETICA, 10);
                    cell = new PdfPCell(new Phrase(slot.getTime().toString(), contentFont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setBackgroundColor(i % 2 == 0 ? new BaseColor(238, 238, 238) : new BaseColor(255, 255, 255));
                    table.addCell(cell);

                    // Add status
                    cell = new PdfPCell(new Phrase(slot.getStatus(), contentFont));
                    cell.setBackgroundColor(i % 2 == 0 ? new BaseColor(238, 238, 238) : new BaseColor(255, 255, 255));
                    table.addCell(cell);

                    // Add reason
                    cell = new PdfPCell(new Phrase(slot.getReason(), contentFont));
                    cell.setBackgroundColor(i % 2 == 0 ? new BaseColor(238, 238, 238) : new BaseColor(255, 255, 255));
                    table.addCell(cell);

                    // Add note
                    cell = new PdfPCell(new Phrase(slot.getNote(), contentFont));
                    cell.setBackgroundColor(i % 2 == 0 ? new BaseColor(238, 238, 238) : new BaseColor(255, 255, 255));
                    table.addCell(cell);

                    // Add appointment
                    String appointmentData = slot.getAppointment().getReasonApp() + "\n"
                            + slot.getAppointment().getPatient().getName() + "\n"
                            + slot.getAppointment().getPatient().getEmail() + "\n"
                            + slot.getAppointment().getPatient().getPhone();
                    if (slot.getAppointment().getReasonApp() == null) {
                        appointmentData = "-";
                    }
                    cell = new PdfPCell(new Phrase(appointmentData, contentFont));
                    if (appointmentData.equals("-")) {
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    }
                    cell.setBackgroundColor(i % 2 == 0 ? new BaseColor(238, 238, 238) : new BaseColor(255, 255, 255));
                    table.addCell(cell);

                    i++; // used to make odd even decoration
                }
                //done
                //Add table to document
                document.add(table);
            }

            document.close();

            System.out.println("Calendar generated successfully!");
        } catch (FileNotFoundException | DocumentException ex) {
            ex.printStackTrace();
        }

    }
}
