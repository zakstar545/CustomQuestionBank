package model.service;

import model.entity.Question;

import javax.swing.*;
import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Image;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;


//Service to generate PDFs for practice tests and markschemes with apachepdfbox lin
public class PdfGenerationService {
    
    /**
     * Generate a PDF with either questions or markschemes
     * 
     * @param outputFile The file to save the PDF to
     * @param questions The questions to include in the PDF
     * @param includeMarkscheme Whether to include markschemes (true) or questions (false)
     * @param testTitle The title for the test
     * @return true if successful, false otherwise
     */
    
    public static boolean generatePDF(File outputFile, LinkedList<Question> questions, boolean includeMarkscheme, String testTitle) {
    System.out.println("Generating " + (includeMarkscheme ? "markscheme" : "practice test") + " PDF: " + outputFile.getPath());
        
        try {
            // Create a new document
            PDDocument document = new PDDocument();
            
            // Add a title page
            PDPage titlePage = new PDPage(PDRectangle.A4);
            document.addPage(titlePage);
            PDPageContentStream titleContent = new PDPageContentStream(document, titlePage);
            
            // Add title text
            titleContent.beginText();
            titleContent.setFont(PDType1Font.HELVETICA_BOLD, 24);
            titleContent.newLineAtOffset(100, 700);
            titleContent.showText(testTitle + (includeMarkscheme ? " - Markscheme" : ""));
            titleContent.endText();
            
            // Add some additional info on the title page
            titleContent.beginText();
            titleContent.setFont(PDType1Font.HELVETICA, 14);
            titleContent.newLineAtOffset(100, 650);
            titleContent.showText("Total Questions: " + questions.size());
            titleContent.endText();
            
            // Calculate total marks
            int totalMarks = 0;
            for (Question q : questions) {
                totalMarks += q.getMarks();
            }
            
            titleContent.beginText();
            titleContent.setFont(PDType1Font.HELVETICA, 14);
            titleContent.newLineAtOffset(100, 630);
            titleContent.showText("Total Marks: " + totalMarks);
            titleContent.endText();
            
            // Add date
            titleContent.beginText();
            titleContent.setFont(PDType1Font.HELVETICA, 14);
            titleContent.newLineAtOffset(100, 610);
            titleContent.showText("Date: " + java.time.LocalDate.now().toString());
            titleContent.endText();
            
            //Collect and display subjects and topics
            Set<String> uniqueSubjects = new HashSet<>();
            Set<String> uniqueTopics = new HashSet<>();
            
            for (Question q : questions) {
                uniqueSubjects.add(q.getSubject());
                uniqueTopics.add(q.getTopic());
            }
            
            // Display  subjects
            titleContent.beginText();
            titleContent.setFont(PDType1Font.HELVETICA_BOLD, 14);
            titleContent.newLineAtOffset(100, 580);
            titleContent.showText("Subjects Covered:");
            titleContent.endText();
            
            int yPos = 560;
            for (String subject : uniqueSubjects) {
                titleContent.beginText();
                titleContent.setFont(PDType1Font.HELVETICA, 12);
                titleContent.newLineAtOffset(120, yPos);
                titleContent.showText("• " + subject);
                titleContent.endText();
                yPos -= 15;
            }
            
            // Display topics
            yPos -= 10; // Add extra space between subjects and topics
            titleContent.beginText();
            titleContent.setFont(PDType1Font.HELVETICA_BOLD, 14);
            titleContent.newLineAtOffset(100, yPos);
            titleContent.showText("Topics Covered:");
            titleContent.endText();
            
            yPos -= 20;
            for (String topic : uniqueTopics) {
                titleContent.beginText();
                titleContent.setFont(PDType1Font.HELVETICA, 12);
                titleContent.newLineAtOffset(120, yPos);
                titleContent.showText("• " + topic);
                titleContent.endText();
                yPos -= 15;
            }
            
            titleContent.close();
            
            // For each question
            int questionNumber = 1;
            for (Question question : questions) {
                // Create new page for this question
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);
                
                PDPageContentStream contentStream = new PDPageContentStream(document, page,
                PDPageContentStream.AppendMode.OVERWRITE, true, true);          
                    
                // Add question header
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Question " + questionNumber + ": " + " (" + question.getMarks() + " marks)");
                contentStream.endText();
                
                // Add subject/topic info
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(50, 730);
                contentStream.showText("Subject: " + question.getSubject() + " | Topic: " + question.getTopic());
                contentStream.endText();
                
                // Default bottom position if no image
                float bottomPosition = 710f;
                
                // Add image - question or markscheme based on parameter
                ImageIcon imageIcon = includeMarkscheme ? question.getMarkschemeImage() : question.getQuestionImage();
                if (imageIcon != null) {
                    // Convert the ImageIcon to BufferedImage
                    BufferedImage bufferedImage = toBufferedImage(imageIcon.getImage());
                    
                    // Create PDImageXObject
                    PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);
                    
                    // Calculate appropriate scaling to fit on page
                    float imageWidth = pdImage.getWidth();
                    float imageHeight = pdImage.getHeight();
                    
                    // Maximum width and height available on the page
                    float maxWidth = 500;  // Leaving margins
                    float maxHeight = 400;  // Smaller height to leave room for answer lines
                    
                    // Calculate scale factor
                    float scale = Math.min(maxWidth / imageWidth, maxHeight / imageHeight);
                    float scaledWidth = imageWidth * scale;
                    float scaledHeight = imageHeight * scale;
                    
                    // Draw the image
                    contentStream.drawImage(pdImage, 50, 710 - scaledHeight, scaledWidth, scaledHeight);
                    
                    // Update bottom position based on image
                    bottomPosition = 710 - scaledHeight - 20; // 20px spacing after image
                }
                
                // Add student answer space with dotted lines (only for question PDFs, not markscheme)
                if (!includeMarkscheme) {

                    
                    // Draw dotted lines for answer space
                    // Leave 50px at bottom of page
                    float bottomMargin = 50;
                    float lineSpacing = 25; // Space between lines
                    
                    contentStream.setLineDashPattern(new float[]{2, 2}, 0); // Dotted line pattern
                    
                    while (bottomPosition > bottomMargin) {
                        contentStream.moveTo(50, bottomPosition);
                        contentStream.lineTo(550, bottomPosition);
                        contentStream.stroke();
                        bottomPosition -= lineSpacing;
                    }
                }
                
                contentStream.close();
                questionNumber++;
            }
            
            // Save the document
            document.save(outputFile);
            document.close();
            
            System.out.println("PDF generation complete: " + outputFile.getPath());
            return true;
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error generating PDF: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Converts an Image to a BufferedImage
     * 
     * @param image The Image to convert
     * @return A BufferedImage
     */
    private static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        
        // Create a buffered image with transparency
        BufferedImage bufferedImage = new BufferedImage(
            image.getWidth(null), 
            image.getHeight(null), 
            BufferedImage.TYPE_INT_ARGB
        );
        
        // Draw the image on to the buffered image
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        
        return bufferedImage;
    }
}