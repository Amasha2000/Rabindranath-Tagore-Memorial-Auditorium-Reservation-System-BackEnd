package com.ruhuna.reservationsystembackend.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ruhuna.reservationsystembackend.entity.Reservation;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

public class PDFGenerator {
    public static byte[] generatePaymentReceipt(Reservation reservation, String paymentType, BigDecimal amount) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);

        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

        Paragraph title = new Paragraph("Payment Receipt", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        table.addCell(new PdfPCell(new Phrase("Reservation ID:", headerFont)));
        table.addCell(new PdfPCell(new Phrase(String.valueOf(reservation.getReservationId()), normalFont)));

        table.addCell(new PdfPCell(new Phrase("Organization Name:", headerFont)));
        table.addCell(new PdfPCell(new Phrase(reservation.getOrganizationName(), normalFont)));

        table.addCell(new PdfPCell(new Phrase("Applicant Name:", headerFont)));
        table.addCell(new PdfPCell(new Phrase(reservation.getApplicantName(), normalFont)));

        table.addCell(new PdfPCell(new Phrase("Reservation Date:", headerFont)));
        table.addCell(new PdfPCell(new Phrase(reservation.getReservedDate().toString(), normalFont)));

        table.addCell(new PdfPCell(new Phrase("Payment Type:", headerFont)));
        table.addCell(new PdfPCell(new Phrase(paymentType, normalFont)));

        table.addCell(new PdfPCell(new Phrase("Amount:", headerFont)));
        table.addCell(new PdfPCell(new Phrase("Rs. " + amount, normalFont)));

        document.add(table);

        Paragraph thankYouMessage = new Paragraph("Thank you for your payment!", normalFont);
        thankYouMessage.setAlignment(Element.ALIGN_CENTER);
        thankYouMessage.setSpacingBefore(20);
        document.add(thankYouMessage);

        document.close();
        return baos.toByteArray();
    }
}
