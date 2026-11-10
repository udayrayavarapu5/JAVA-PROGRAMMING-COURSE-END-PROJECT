import java.io.*;
import java.util.*;

/** Saves and loads the student list using Java serialization. */
public class FileHandler {
    private static final String FILE_NAME = "students.dat";

    public static void saveToFile(List<Student> students) {
        try (ObjectOutputStream out =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(new ArrayList<>(students));
            System.out.println("Records saved to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Student> loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();           // first run: nothing saved yet
        }
        try (ObjectInputStream in =
                     new ObjectInputStream(new FileInputStream(file))) {
            return (List<Student>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading file: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
