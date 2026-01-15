package Utils;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EvidenceCapture {

    /**
     * Escribe un titulo en el documento (crea el doc si no existe)
     * @param docxPath ruta completa del docx
     * @param titulo texto del titulo
     * @param fontSize tamaño de letra (ej: 20)
     */
    public static void escribirTituloEnDocumento(String docxPath, String titulo, int fontSize) {
        try {
            File docFile = new File(docxPath);
            XWPFDocument docx;

            if (!docFile.exists()) {
                docx = new XWPFDocument();
            } else {
                try (FileInputStream fis = new FileInputStream(docFile)) {
                    docx = new XWPFDocument(fis);
                }
            }

            // Párrafo título centrado
            XWPFParagraph pTitle = docx.createParagraph();
            pTitle.setAlignment(ParagraphAlignment.CENTER);
            pTitle.setSpacingAfter(300);

            XWPFRun runTitle = pTitle.createRun();
            runTitle.setBold(true);
            runTitle.setFontSize(fontSize);
            runTitle.setText(titulo);

            // Guardar docx
            try (FileOutputStream fos = new FileOutputStream(docFile)) {
                docx.write(fos);
            }
            docx.close();

        } catch (Exception e) {
            throw new RuntimeException("Error escribiendo titulo en DOCX: " + e.getMessage(), e);
        }
    }

    /**
     * Captura screenshot y lo inserta en el mismo DOCX (imagen embebida, no archivos sueltos).
     * Mantiene proporción real de la imagen.
     *
     * @param driver   WebDriver
     * @param docxPath ruta completa del docx
     * @param stepName nombre del paso (ej: CP02_Before_Send)
     */
    public static void captureScreenshotIntoDoc(WebDriver driver, String docxPath, String stepName) {
        try {
            // 1) Screenshot en bytes (NO se guarda archivo aparte)
            byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            // 2) Abrir o crear DOCX
            File docFile = new File(docxPath);
            XWPFDocument docx;

            if (!docFile.exists()) {
                docx = new XWPFDocument();
            } else {
                try (FileInputStream fis = new FileInputStream(docFile)) {
                    docx = new XWPFDocument(fis);
                }
            }

            // 3) Texto + timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String safeStep = (stepName == null || stepName.isBlank())
                    ? "step"
                    : stepName.replaceAll("[^a-zA-Z0-9-_ ]", "").trim().replace(" ", "_");

            // 4) Separador
            XWPFParagraph sep = docx.createParagraph();
            XWPFRun sepRun = sep.createRun();
            sepRun.setText("------------------------------------------------------------");

            // 5) Texto del paso
            XWPFParagraph p = docx.createParagraph();
            p.setSpacingAfter(120);

            XWPFRun run = p.createRun();
            run.setBold(true);
            run.setFontSize(12);
            run.setText("Evidence: " + safeStep + " (" + timestamp + ")");
            run.addBreak();

            // 6) Calcular tamaño proporcional (ancho fijo, alto real)
            int targetWidthPx = 500;

            BufferedImage bufferedImage;
            try (InputStream bis = new ByteArrayInputStream(screenshotBytes)) {
                bufferedImage = ImageIO.read(bis);
            }

            int originalWidth = bufferedImage.getWidth();
            int originalHeight = bufferedImage.getHeight();

            int targetHeightPx = (int) Math.round((double) originalHeight * targetWidthPx / originalWidth);

            // 7) Insertar imagen embebida
            try (InputStream img = new ByteArrayInputStream(screenshotBytes)) {
                XWPFParagraph imgP = docx.createParagraph();
                XWPFRun imgRun = imgP.createRun();

                imgRun.addPicture(
                        img,
                        Document.PICTURE_TYPE_PNG,
                        safeStep + "_" + timestamp + ".png",
                        Units.toEMU(targetWidthPx),
                        Units.toEMU(targetHeightPx)
                );
            }

            // 8) Guardar DOCX
            try (FileOutputStream fos = new FileOutputStream(docFile)) {
                docx.write(fos);
            }
            docx.close();

        } catch (Exception e) {
            throw new RuntimeException("Error capturando evidencia en DOCX: " + e.getMessage(), e);
        }
    }
}