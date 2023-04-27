/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.gestion_rdv.services;

//************EXCEL*******************
import entities.DummyUser;
import entities.gestion_rdv.DummyCalandar;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import services.gestion_rdv.CrudCalandarDay;
import services.gestion_rdv.exceptions.ConnectionOrPrepareStatmentException;

/**
 *
 * @author rbaih
 */
public class CalandarEXCEL {

    public void ExcelCalandarGenerated(DummyUser doctor) {

        try {
            FileOutputStream fileOut = null;

            // Create workbook and sheet
            Workbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet("previous_calandars");
            // Create cell styles
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle greenDateStyle = createGreenDateStyle(workbook);
            CellStyle evenRowStyle = createEvenRowStyle(workbook);
            CellStyle oddRowStyle = createOddRowStyle(workbook);

            // Add logo as title
            Row titleRow = sheet.createRow(0);
            titleRow.createCell(2).setCellValue("logo");

            // Add small title
            Row smallTitleRow = sheet.createRow(1);
            smallTitleRow.createCell(2).setCellValue("List of previous calendars");

            
            // Create sample calendar data
            Collection<DummyCalandar> calendarDays;
            try {
                calendarDays = new CrudCalandarDay().getcalandar_Pdf_Excel(doctor.getId());
                // Add calendar data rows
            } catch (ConnectionOrPrepareStatmentException ex) {
                calendarDays = new ArrayList<>();
                ex.printStackTrace();
            }

    //LOOP CALANDARS
            int rowcount = 2;
            for (DummyCalandar calendar : calendarDays) {
                
                rowcount+=3;
                
                // Add table header1 ( date )
                Row rowDate = sheet.createRow(rowcount++);
                Cell cell = rowDate.createCell(2);
                cell.setCellValue(calendar.getDate().format(DateTimeFormatter.ISO_DATE) );
                cell.setCellStyle(greenDateStyle);

                // add table header 2
                Row headerRow = sheet.createRow(rowcount++);
                cell = headerRow.createCell(2);
                cell.setCellValue("Time");
                cell.setCellStyle(headerStyle);
                //
                cell = headerRow.createCell(3);
                cell.setCellValue("Status");
                cell.setCellStyle(headerStyle);
                //
                cell = headerRow.createCell(4);
                cell.setCellValue("Reason");
                cell.setCellStyle(headerStyle);
                //
                cell = headerRow.createCell(5);
                cell.setCellValue("Note");  
                cell.setCellStyle(headerStyle);
                //
                cell = headerRow.createCell(6);
                cell.setCellValue("Appointment");
                cell.setCellStyle(headerStyle);
               
                
                
                

// LoopSlots now 
                for (DummyCalandar.Slot slot : calendar.getSlots()) {
                    
                    //autoSize Sheets
                    sheet.autoSizeColumn(4);
                    sheet.autoSizeColumn(5);
                    sheet.autoSizeColumn(6);
                    
                    // add data of slots now
                    Row dataRow = sheet.createRow(rowcount++);
                    cell = dataRow.createCell(2);
                    cell.setCellValue(slot.getTime().format(DateTimeFormatter.ISO_TIME ));
                    cell.setCellStyle(rowcount% 2 == 0 ? evenRowStyle : oddRowStyle);
                    //
                    cell = dataRow.createCell(3);
                    cell.setCellValue(slot.getStatus() );
                    cell.setCellStyle(rowcount% 2 == 0 ? evenRowStyle : oddRowStyle);
                    //
                    cell = dataRow.createCell(4);
                    cell.setCellValue(slot.getReason());
                    cell.setCellStyle(rowcount% 2 == 0 ? evenRowStyle : oddRowStyle);
                    //
                    cell = dataRow.createCell(5);
                    cell.setCellValue(slot.getNote());
                    cell.setCellStyle(rowcount% 2 == 0 ? evenRowStyle : oddRowStyle);
                    //
                    cell = dataRow.createCell(6);
                    cell.setCellValue(slot.getAppointment().getReasonApp()+"\n"
                            + slot.getAppointment().getPatient().getName()+"\n"
                            + slot.getAppointment().getPatient().getEmail()+"\n"
                            + slot.getAppointment().getPatient().getPhone());
                    if(slot.getAppointment().getPatient().getName()==null)
                        cell.setCellValue("    -");
                    cell.setCellStyle(rowcount% 2 == 0 ? evenRowStyle : oddRowStyle);
                    //
                    
                }

            }

            
            String pathUserDownloads= System.getProperty("user.home")+ "/Downloads/";
// Write workbook to file
            fileOut = new FileOutputStream( pathUserDownloads+"previous_calendars_" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + ".xlsx");
            workbook.write(fileOut);
            fileOut.close();
            // Close workbook
            workbook.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(CalandarEXCEL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CalandarEXCEL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        return style;
    }

    private static CellStyle createGreenDateStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private static CellStyle createEvenRowStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

    private static CellStyle createOddRowStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }

     

}

