package by.glesap.task3.reader;

import java.io.IOException;
import java.util.List;

public interface CustomFileReader {
  List<String> readFile(String fileName) throws IOException;
}
