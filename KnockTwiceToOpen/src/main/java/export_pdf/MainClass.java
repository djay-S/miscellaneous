package export_pdf;

import lombok.Data;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.Color;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//https://www.youtube.com/watch?v=VcBilR9oO1w
public class MainClass {
    public static void main(String[] args) throws IOException {

        PDDocument document = new PDDocument();
        PDPage firstPage = new PDPage(PDRectangle.A4);
        document.addPage(firstPage);

        PDPageContentStream contentStream = new PDPageContentStream(document, firstPage);

        try {
            String name = "User";
            String line2 = "794541322";

            PDRectangle trimBox = firstPage.getTrimBox();
            PDRectangle mediaBox = firstPage.getMediaBox();
            float pageWidth = mediaBox.getWidth();
            float pageHeight = mediaBox.getHeight();

            MyTextClass textClass = new MyTextClass(document, contentStream);

            PDFont font = PDType1Font.TIMES_ROMAN;
            PDFont italicFont = PDType1Font.TIMES_ROMAN;

            String[] contactInfo = new String[]{"mob1", "mob2"};
            textClass.addMultiLineText(contactInfo, 18, (pageWidth - font.getStringWidth("mob1") / 1000 * 15 - 10), pageHeight - 25, font, 15, Color.BLACK);

            textClass.addSingleLineText("India Tadka", 25, pageHeight - 150, font, 40, Color.BLACK);
            textClass.addSingleLineText("Customer Name:" + name, 25, pageHeight - 250, font, 16, Color.BLACK);
            textClass.addSingleLineText("Mo. No.: " + line2, 25, pageHeight - 274, font, 16, Color.BLACK);

            String invoiceNo = "Invoice: 123456";
            float textWidth = MyTextClass.getTextWidth(invoiceNo, font, 16);

            textClass.addSingleLineText(invoiceNo, pageWidth - 25 - textWidth, pageHeight - 250, font, 16, Color.BLACK);

            Format dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
            String timeStamp = "Time: " + dateFormat.format(new Date());
            textWidth = MyTextClass.getTextWidth(timeStamp, font, 16);

            textClass.addSingleLineText(timeStamp, pageWidth - 25 - textWidth, pageHeight - 274, font, 16, Color.BLACK);

            MyTableClass tableClass = new MyTableClass(document, contentStream);

            int[] cellWidths = {70, 160, 120, 90, 100};
            tableClass.setTable(cellWidths, 30, 25, (int) (pageHeight - 350));
            tableClass.setTableFont(16, font, Color.BLACK);

            List<Invoice> invoiceList = List.of(
                    new Invoice(1, "Masala Dosa", 120, 2),
                    new Invoice(2, "Idli", 80, 4),
                    new Invoice(3, "Dhokla", 50, 4),
                    new Invoice(4, "Samosa", 20, 6)
            );

            List<String> headerColumns = List.of("Sl. No.", "Items", "Price", "Qty", "Total");

            Color headerRowColor = new Color(240, 93, 11);
            Color rowColor = Color.LIGHT_GRAY;

            addHeaderRowToPdfTable(headerColumns, tableClass,headerRowColor);
            addInvoiceItemToPdfTable(invoiceList, tableClass,rowColor);

            int subTotal = invoiceList.stream().mapToInt(Invoice::getTotal).sum();
            double gst = 0.05;
            long gstValue = Math.round(subTotal * gst);
            long total = subTotal + gstValue;

            tableClass.addCell("", null);
            tableClass.addCell("", null);
            tableClass.addCell("Sub Total", null);
            tableClass.addCell("", null);
            tableClass.addCell(String.valueOf(subTotal), null);

            tableClass.addCell("", null);
            tableClass.addCell("", null);
            tableClass.addCell("GST", null);
            tableClass.addCell((gst * 100) + "%", null);
            tableClass.addCell(String.valueOf(gstValue), null);

            tableClass.addCell("", null);
            tableClass.addCell("", null);
            tableClass.addCell("Grand Total", null);
            tableClass.addCell("", null);
            tableClass.addCell(String.valueOf(total), null);

            String[] paymentMethod = {"Methods of payment we accept:", "Cash, Card, UPI"};
            textClass.addMultiLineText(paymentMethod, 15, 25, 180, italicFont, 10, Color.DARK_GRAY);

            contentStream.setStrokingColor(Color.BLACK);
            contentStream.setLineWidth(2);
            contentStream.moveTo(pageWidth - 250, 150);
            contentStream.lineTo(pageWidth - 25, 150);
            contentStream.stroke();

            String authSign = "Authorised Signature";
            textWidth = MyTextClass.getTextWidth(authSign, italicFont, 16);
            textClass.addSingleLineText(authSign, (pageWidth - 250 + pageWidth - 25 - textWidth) / 2, 125, italicFont, 16, Color.BLACK);

            String bottomLine = "Thank You.. Visit Again.";
            textWidth = MyTextClass.getTextWidth(bottomLine, italicFont, 16);
            textClass.addSingleLineText(bottomLine, (pageWidth - textWidth) / 2, 50, italicFont, 20, Color.DARK_GRAY);

            Color footerColor = new Color(255, 91, 0);
            contentStream.setNonStrokingColor(footerColor);
            contentStream.addRect(0, 0, pageWidth, 30);
            contentStream.fill();

            System.out.println("Pdf created");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            contentStream.close();
            document.save("\\pdfExport.pdf");
            document.close();
            System.out.println("Exiting.");
        }
    }

    private static void addHeaderRowToPdfTable(List<String> headerColumns, MyTableClass tableClass, Color headerRowColor) throws IOException {
        for (String column : headerColumns) {
            tableClass.addCell(column, headerRowColor);
        }
    }

    private static void addInvoiceItemToPdfTable(List<Invoice> invoiceList, MyTableClass tableClass, Color rowColor) throws IOException {
        for (Invoice invoice : invoiceList) {
            tableClass.addCell(String.valueOf(invoice.getSlNo()), rowColor);
            tableClass.addCell(invoice.getItem(), rowColor);
            tableClass.addCell(String.valueOf(invoice.getPrice()), rowColor);
            tableClass.addCell(String.valueOf(invoice.getQty()), rowColor);
            tableClass.addCell(String.valueOf(invoice.getTotal()), rowColor);
        }
    }

}

class MyTextClass {
    PDDocument document;
    PDPageContentStream contentStream;

    public MyTextClass(PDDocument document, PDPageContentStream contentStream) {
        this.document = document;
        this.contentStream = contentStream;
    }

    void addSingleLineText(String text, float xPosition, float yPosition, PDFont font, float fontSize, Color color) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(color);
        contentStream.newLineAtOffset(xPosition, yPosition);
        contentStream.showText(text);
        contentStream.endText();
        contentStream.moveTo(0, 0);
    }

    void addMultiLineText(String[] line, float leading, float xPosition, float yPosition, PDFont font, float fontSize, Color color) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(color);
        contentStream.setLeading(leading);
        contentStream.newLineAtOffset(xPosition, yPosition);

        for (String text : line) {
            contentStream.showText(text);
            contentStream.newLine();
        }

        contentStream.endText();
        contentStream.moveTo(0, 0);
    }

    static float getTextWidth(String text, PDFont font, float fontSize) throws IOException {
        return font.getStringWidth(text) / 1000 * fontSize;
    }
}

class MyTableClass {
    PDDocument document;
    PDPageContentStream contentStream;
    private int[] colWidths;
    private int cellHeight;
    private int xPosition;
    private int yPosition;
    private int tolPosition;
    private int colPosition;
    private int xInitialPosition;
    private float fontSize;
    private PDFont font;
    private Color fontColor;

    public MyTableClass(PDDocument document, PDPageContentStream contentStream) {
        this.document = document;
        this.contentStream = contentStream;
    }

    void setTable(int[] colWidths, int cellHeight, int xPosition, int yPosition) {
        this.colWidths = colWidths;
        this.cellHeight = cellHeight;
        this.yPosition = yPosition;
        this.xPosition = xPosition;
        xInitialPosition = xPosition;
    }

    void setTableFont(float fontSize, PDFont font, Color fontColor) {
        this.fontSize = fontSize;
        this.font = font;
        this.fontColor = fontColor;
    }

    void addCell(String text, Color fillColor) throws IOException {
        contentStream.setStrokingColor(1f);

        if (fillColor != null) {
            contentStream.setNonStrokingColor(fillColor);
        }

        contentStream.addRect(xPosition, yPosition, colWidths[colPosition], cellHeight);

        if (fillColor == null) {
            contentStream.stroke();
        } else {
            contentStream.fillAndStroke();
        }

        contentStream.beginText();
        contentStream.setNonStrokingColor(fontColor);

        if (colPosition % 2 == 0) {
            float fontWidth = MyTextClass.getTextWidth(text, font, fontSize);
            contentStream.newLineAtOffset(xPosition + colWidths[colPosition] - 20 - fontWidth, yPosition + 10);
        } else {
            contentStream.newLineAtOffset(xPosition + 20, yPosition + 10);
        }

        contentStream.showText(text);
        contentStream.endText();

        xPosition += colWidths[colPosition];
        colPosition++;

        if (colPosition == colWidths.length) {
            colPosition = 0;
            xPosition = xInitialPosition;
            yPosition -= cellHeight;
        }
    }
}

@Data
class Invoice {
    private int slNo;
    private String item;
    private int price;
    private int qty;
    private int total;

    public Invoice(int slNo, String item, int price, int qty) {
        this.slNo = slNo;
        this.item = item;
        this.price = price;
        this.qty = qty;
        total = price * qty;
    }
}