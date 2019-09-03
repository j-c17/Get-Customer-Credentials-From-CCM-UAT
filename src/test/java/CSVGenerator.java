import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class CSVGenerator {

    private static final String CSV_FILE = System.getProperty("user.dir") + "\\src\\test\\resources\\result\\credentials2.csv";
    private CSVPrinter csvPrinter;

    public CSVGenerator() throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(CSV_FILE), StandardOpenOption.APPEND,
                StandardOpenOption.CREATE);
        this.csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                .withHeader("CardNumber", "LastName", "DOB"));
    }


    public void generateCSV(String CardNumber, String LastName, String DOB) {

        try {
            csvPrinter.printRecord(CardNumber, LastName, DOB);
            csvPrinter.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


