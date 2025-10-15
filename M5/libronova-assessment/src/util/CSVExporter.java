package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CSVExporter {

    public static <T> void exportToCSV(String fileName, List<String> headers, List<T> data, Function<T, List<String>> rowMapper) {

        // Create file
        File csvOutputFile = new File(fileName);

        // Try to write to file
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {

            // Write header
            String header = String.join(",", headers);
            pw.println(header);

            // Write data
            for (T item : data) {

                // Map row data
                List<String> rowData = rowMapper.apply(item);

                // Write row
                String line = String.join(",", rowData);
                pw.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
