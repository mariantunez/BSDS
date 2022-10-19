package ClientPart2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Writes a csv file
 */
public class CSVWriter {

  private BufferedWriter writer;
  private final String OUTPUT_FILE = "RequestRecords.csv";

  public CSVWriter() {
    try {
      this.writer = new BufferedWriter(new FileWriter(OUTPUT_FILE, false));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * @param records StringBuffer of records to write
   */
  public void writeCSVFile(StringBuffer records) {
    String file = records.toString();

    try {
      writer.write(file);
      writer.flush();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }

    System.out.println("Finish writing csv records file");
  }
}
