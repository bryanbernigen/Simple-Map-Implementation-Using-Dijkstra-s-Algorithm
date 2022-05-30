import java.io.File; 
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile {
  public String readFromFile(String fileName) {
    try {
      File myObj = new File("test/"+fileName);
      Scanner myReader = new Scanner(myObj);
      String fullText = "";
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        fullText += data + " ";
      }
      myReader.close();
      return fullText;
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
      return "404";
    }
  }
}