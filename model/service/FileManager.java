package model.service;

import com.google.gson.*;

import model.core.PracticeTest;
import model.core.QuestionBank;
import model.entity.Question;

import java.io.*;
import java.lang.reflect.Type;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.Base64;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.util.LinkedList;
import java.awt.Graphics;

//This class manages the data persistance of the application.
public class FileManager {

    //Define the file paths. The main data dir is in the user's home dir. In that dir, there are two folders to store question data and subject/topic data.
    private static final String DATA_DIRECTORY = System.getProperty("user.home") + File.separator + "CQB_Data";
    private static final String QUESTIONS_FILE = DATA_DIRECTORY + File.separator + "questions.json";
    private static final String SUBJECTS_TOPICS_FILE = DATA_DIRECTORY + File.separator + "subjects_topics.json";
    private static final String PRACTICE_TEST_FILE = DATA_DIRECTORY + File.separator + "practice_test.json";

    private static final Gson gson = createGsonInstance();
    
    private static Gson createGsonInstance() {
        return new GsonBuilder()
            .registerTypeAdapter(ImageIcon.class, new ImageIconSerializer())
            .registerTypeAdapter(ImageIcon.class, new ImageIconDeserializer())
            .setPrettyPrinting()
            .create();
    }
    
    // Create data directory if it doesn't exist
    public static void initializeDataDirectory() {
        File directory = new File(DATA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    
    // Save all questions to file with a try catch block to handle exceptions. seroalize the questions list to json and write it to the file
    // using the file writer. Close the writer and print a message to the console. If there is an error a message is printed.
    public static void saveQuestions() {
        try {
            initializeDataDirectory();
            FileWriter writer = new FileWriter(QUESTIONS_FILE);
            gson.toJson(QuestionBank.questions, writer);
            writer.close();
            System.out.println("Questions saved to: " + QUESTIONS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save questions: " + e.getMessage());
        }
    }
    
    // Load questions from file
    public static void loadQuestions() {
        try {
            File file = new File(QUESTIONS_FILE);
            if (!file.exists()) {
                return;
            }
            
            FileReader reader = new FileReader(file);
            Type questionListType = new com.google.gson.reflect.TypeToken<LinkedList<Question>>(){}.getType();
            LinkedList<Question> loadedQuestions = gson.fromJson(reader, questionListType);
            reader.close();
            
            if (loadedQuestions != null) {
                QuestionBank.questions = loadedQuestions;
                System.out.println("Loaded " + loadedQuestions.size() + " questions");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load questions: " + e.getMessage());
        }
    }
    
    // Save subjects and topics
    public static void saveSubjectsAndTopics() {
        try {
            initializeDataDirectory();
            FileWriter writer = new FileWriter(SUBJECTS_TOPICS_FILE);
            gson.toJson(Question.globalSubjectTopicManager, writer);
            writer.close();
            System.out.println("Subjects and topics saved to: " + SUBJECTS_TOPICS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save subjects and topics: " + e.getMessage());
        }
    }
    
    // Load subjects and topics
    public static void loadSubjectsAndTopics() {
        try {
            File file = new File(SUBJECTS_TOPICS_FILE);
            if (!file.exists()) {
                return;
            }
            
            FileReader reader = new FileReader(file);
            SubjectTopicManager loadedManager = gson.fromJson(reader, SubjectTopicManager.class);
            reader.close();
            
            if (loadedManager != null) {
                Question.globalSubjectTopicManager = loadedManager;
                System.out.println("Subjects and topics loaded successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load subjects and topics: " + e.getMessage());
        }
    }
    
    //save oractuce test data
    public static void savePracticeTest() {
        try {
            initializeDataDirectory();
            FileWriter writer = new FileWriter(PRACTICE_TEST_FILE);
            gson.toJson(PracticeTest.practiceQuestions, writer);
            writer.close();
            System.out.println("Practice test saved to: " + PRACTICE_TEST_FILE);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save practice test: " + e.getMessage());
        }
    }
    
    //load ractice test data
    public static void loadPracticeTest() {
        try {
            File file = new File(PRACTICE_TEST_FILE);
            if (!file.exists()) {
                return;
            }
            
            FileReader reader = new FileReader(file);
            Type questionListType = new com.google.gson.reflect.TypeToken<LinkedList<Question>>(){}.getType();
            LinkedList<Question> loadedQuestions = gson.fromJson(reader, questionListType);
            reader.close();
            
            if (loadedQuestions != null) {
                // We need to match the loaded questions with the ones in QuestionBank
                // to ensure we have the correct references
                LinkedList<Question> matchedQuestions = new LinkedList<>();
                for (Question loadedQuestion : loadedQuestions) {
                    for (Question bankQuestion : QuestionBank.questions) {
                        if (bankQuestion.getId().equals(loadedQuestion.getId())) {
                            matchedQuestions.add(bankQuestion);
                            break;
                        }
                    }
                }
                PracticeTest.practiceQuestions = matchedQuestions;
                System.out.println("Loaded " + matchedQuestions.size() + " practice test questions");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load practice test: " + e.getMessage());
        }
    }

    // Save all data
    public static void saveAllData() {
        saveQuestions();
        saveSubjectsAndTopics();
        savePracticeTest();
    }

    // Load all data
    public static void loadAllData() {
        loadSubjectsAndTopics();
        loadQuestions();
        loadPracticeTest(); 
    }
    
    // Custom serializer for ImageIcon (ai generated)
    private static class ImageIconSerializer implements JsonSerializer<ImageIcon> {
        @Override
        public JsonElement serialize(ImageIcon src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject json = new JsonObject();
            if (src == null) {
                return JsonNull.INSTANCE;
            }
            try {
                // Get image from ImageIcon
                Image image = src.getImage();
                BufferedImage bufferedImage;
                
                // Convert to BufferedImage if needed
                if (!(image instanceof BufferedImage)) {
                    // Create a compatible BufferedImage
                    bufferedImage = new BufferedImage(
                        src.getIconWidth() > 0 ? src.getIconWidth() : 1,
                        src.getIconHeight() > 0 ? src.getIconHeight() : 1,
                        BufferedImage.TYPE_INT_ARGB
                    );
                    
                    // Draw the original image onto the BufferedImage
                    Graphics g = bufferedImage.createGraphics();
                    g.drawImage(image, 0, 0, null);
                    g.dispose();
                } else {
                    bufferedImage = (BufferedImage) image;
                }
                
                // Proceed with serialization
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "png", baos);
                String base64 = Base64.getEncoder().encodeToString(baos.toByteArray());
                json.addProperty("imageData", base64);
                json.addProperty("width", src.getIconWidth());
                json.addProperty("height", src.getIconHeight());
                return json;
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to serialize image: " + e.getMessage());
            }
            return JsonNull.INSTANCE;
        }
    }
    
    // Custom deserializer for ImageIcon (ai generated)
    private static class ImageIconDeserializer implements JsonDeserializer<ImageIcon> {
        @Override
        public ImageIcon deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (json.isJsonNull()) {
                return null;
            }
            try {
                JsonObject jsonObject = json.getAsJsonObject();
                if (jsonObject.has("imageData")) {
                    String base64 = jsonObject.get("imageData").getAsString();
                    byte[] imageData = Base64.getDecoder().decode(base64);
                    ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
                    BufferedImage bufferedImage = ImageIO.read(bais);
                    return new ImageIcon(bufferedImage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}