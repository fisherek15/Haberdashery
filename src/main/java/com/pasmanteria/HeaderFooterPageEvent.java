package com.pasmanteria;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.vaadin.server.VaadinService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

/**
 * Created by Adrian on 13/11/2017.
 */
public class HeaderFooterPageEvent extends PdfPageEventHelper {

    private static HelpfulMethods hm;
    private PdfTemplate t;
    private Image total;

    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(30, 16);
        try {
            total = Image.getInstance(t);
            total.setRole(PdfName.ARTIFACT);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        addHeader(writer);
        addFooter(writer);
    }

    private void addHeader(PdfWriter writer){
        PdfPTable header = new PdfPTable(3);
        try {
            // set defaults
            header.setWidths(new int[]{3, 24, 8});
            header.setTotalWidth(527);
            header.setLockedWidth(true);
            header.getDefaultCell().setFixedHeight(40);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            //header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

            // add image
            Image logo = Image.getInstance(HeaderFooterPageEvent.class.getResource("/document_logo.png"));
            header.addCell(logo);

            // add text
            PdfPCell text = new PdfPCell();
            text.setPaddingBottom(15);
            text.setPaddingLeft(10);
            text.setBorder(Rectangle.NO_BORDER);
            //text.setBorderColor(BaseColor.LIGHT_GRAY);
            text.addElement(new Phrase("Sklep pasmanteryjny", new Font(Font.FontFamily.HELVETICA, 12)));
            text.addElement(new Phrase("Danuta Hering", new Font(Font.FontFamily.HELVETICA, 8)));
            header.addCell(text);

            PdfPCell text2 = new PdfPCell();
            text2.setPaddingBottom(15);
            text2.setBorder(Rectangle.NO_BORDER);
            //text2.setBorderColor(BaseColor.LIGHT_GRAY);
            text2.addElement(new Phrase(hm.convertDateToHeaderOfDocument(new Date()), new Font(Font.FontFamily.HELVETICA, 10)));
            header.addCell(text2);

            // write content
            header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (MalformedURLException e) {
            throw new ExceptionConverter(e);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }

    private void addFooter(PdfWriter writer){
        PdfPTable footer = new PdfPTable(3);
        try {
            // set defaults
            footer.setWidths(new int[]{24, 4, 2});
            footer.setTotalWidth(527);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setFixedHeight(30);
            footer.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            //footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

            // add copyright
            footer.addCell(new Phrase("\u00A9 Pasmanteria POS", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));

            // add current page count
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            footer.addCell(new Phrase(String.format("Strona %d z", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));

            // add placeholder for total page count
            PdfPCell totalPageCount = new PdfPCell(total);
            totalPageCount.setBorder(Rectangle.NO_BORDER);
            //totalPageCount.setBorderColor(BaseColor.LIGHT_GRAY);
            footer.addCell(totalPageCount);

            // write page
            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            footer.writeSelectedRows(0, -1, 34, 50, canvas);
            canvas.endMarkedContentSequence();
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        int totalLength = String.valueOf(writer.getPageNumber()).length();
        int totalWidth = totalLength * 5;
        ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
                new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)),
                totalWidth, 6, 0);
    }
}