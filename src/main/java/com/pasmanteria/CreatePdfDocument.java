package com.pasmanteria;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Stream;

/**
 * Created by Adrian on 13/11/2017.
 */
public class CreatePdfDocument {

    private static HelpfulMethods hm;

   public static boolean createPdf(Object queryResult[][], String nameOfDocument, String documentTitle) {

       HeaderFooterPageEvent event = new HeaderFooterPageEvent();

        try {

            Document document = new Document(PageSize.A4, 36, 36, 90, 60);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("raports/" + nameOfDocument + ".pdf"));
            writer.setPageEvent(event);

            float[] columnWidths1 = {20f, 25f, 20f, 70f, 20f, 10f, 15f, 25f,45f};
            float[] columnWidths2 = {16f, 70f, 15f, 10f, 15f};
            float[] columnWidths3 = {25f, 20f, 70f, 20f, 10f, 30f};
            BaseFont base = BaseFont.createFont("assets/fonts/arial-narrow-polish.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font1 = new Font(base, 10, Font.NORMAL);
            Font font2 = new Font(base, 15, Font.BOLD);
            Font font4 = new Font(base, 12, Font.NORMAL);

            document.open();

            if (queryResult[0].length == 9) {
                PdfPTable table = new PdfPTable(9);
                table.setWidthPercentage(100);
                table.setWidths(columnWidths1);

                Paragraph title = new Paragraph(documentTitle, font2);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(Chunk.NEWLINE);

                Stream.of("Nr trans.", "Kod kresk.", "Nr kat.", "Nazwa artykułu", "Ilość", "JM", "Rabat", "Cena", "Data sprzedania")
                        .forEach(columnTitle -> {
                            PdfPCell header = new PdfPCell();
                            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            header.setBorderWidth(1);
                            header.setPhrase(new Phrase(columnTitle, font4));

                            table.addCell(header);
                        });

                for (Object[] row : queryResult) {

                    PdfPCell cell1 = new PdfPCell(new Paragraph(row[0].toString(), font1));
                    PdfPCell cell2 = new PdfPCell(new Paragraph(row[1].toString(), font1));
                    PdfPCell cell3 = new PdfPCell(new Paragraph(row[2].toString(), font1));
                    PdfPCell cell4 = new PdfPCell(new Paragraph(row[3].toString(), font1));
                    PdfPCell cell5 = new PdfPCell(new Paragraph(row[4].toString(), font1));
                    PdfPCell cell6 = new PdfPCell(new Paragraph(row[5].toString(), font1));
                    PdfPCell cell7 = new PdfPCell(new Paragraph(row[6].toString(), font1));
                    PdfPCell cell8 = new PdfPCell(new Paragraph(row[7].toString(), font1));
                    PdfPCell cell9 = new PdfPCell(new Paragraph(row[8].toString(), font1));

                    table.addCell(cell1);
                    table.addCell(cell2);
                    table.addCell(cell3);
                    table.addCell(cell4);
                    table.addCell(cell5);
                    table.addCell(cell6);
                    table.addCell(cell7);
                    table.addCell(cell8);
                    table.addCell(cell9);

                }
                document.add(table);

            } else if(queryResult[0].length == 5) {
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                table.setWidths(columnWidths2);

                Paragraph title = new Paragraph(documentTitle, font2);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(Chunk.NEWLINE);

                Stream.of("Kod kreskowy", "Nazwa artykułu", "Ilość", "JM", "Transakcje")
                        .forEach(columnTitle -> {
                            PdfPCell header = new PdfPCell();
                            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            header.setBorderWidth(1);
                            header.setPhrase(new Phrase(columnTitle, font4));
                            table.addCell(header);
                        });

                for (Object[] row : queryResult) {

                    PdfPCell cell1 = new PdfPCell(new Paragraph(row[0].toString(), font1));
                    PdfPCell cell2 = new PdfPCell(new Paragraph(row[1].toString(), font1));
                    PdfPCell cell3 = new PdfPCell(new Paragraph(row[2].toString(), font1));
                    PdfPCell cell4 = new PdfPCell(new Paragraph(row[3].toString(), font1));
                    PdfPCell cell5 = new PdfPCell(new Paragraph(row[4].toString(), font1));

                    table.addCell(cell1);
                    table.addCell(cell2);
                    table.addCell(cell3);
                    table.addCell(cell4);
                    table.addCell(cell5);
                }
                document.add(table);
            } else if(queryResult[0].length == 6) {
                PdfPTable table = new PdfPTable(6);
                table.setWidthPercentage(100);
                table.setWidths(columnWidths3);

                Paragraph title = new Paragraph(documentTitle, font2);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);
                document.add(Chunk.NEWLINE);

                Stream.of("Kod kresk.", "Nr kat.", "Nazwa artykułu", "Ilość", "JM", "Hurtownia")
                        .forEach(columnTitle -> {
                            PdfPCell header = new PdfPCell();
                            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            header.setBorderWidth(1);
                            header.setPhrase(new Phrase(columnTitle, font4));
                            table.addCell(header);
                        });

                for (Object[] row : queryResult) {

                    PdfPCell cell1 = new PdfPCell(new Paragraph(row[0].toString(), font1));
                    PdfPCell cell2 = new PdfPCell(new Paragraph(row[1].toString(), font1));
                    PdfPCell cell3 = new PdfPCell(new Paragraph(row[2].toString(), font1));
                    PdfPCell cell4 = new PdfPCell(new Paragraph(row[3].toString(), font1));
                    PdfPCell cell5 = new PdfPCell(new Paragraph(row[4].toString(), font1));
                    PdfPCell cell6 = new PdfPCell(new Paragraph(row[5].toString(), font1));

                    table.addCell(cell1);
                    table.addCell(cell2);
                    table.addCell(cell3);
                    table.addCell(cell4);
                    table.addCell(cell5);
                    table.addCell(cell6);
                }
                document.add(table);
            } else {
                System.out.println("Problem z zapisem zawartości dokumentu!");
            }

            document.close();

            return true;
        } catch(DocumentException de) {
            System.out.println(de);
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
   }
}
