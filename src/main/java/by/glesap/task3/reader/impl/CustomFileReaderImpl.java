package by.glesap.task3.reader.impl;

import by.glesap.task3.reader.CustomFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CustomFileReaderImpl implements CustomFileReader {
  private static final Logger logger = LoggerFactory.getLogger(CustomFileReaderImpl.class);
  private static final String FILENAME = "data/input.txt";

  @Override
  public List<String> readFile(String fileName) throws IOException {
    logger.info("readFile() called");
    List<String> list = new ArrayList<>();
    try {
      Path path = Paths.get(fileName);
      if (!Files.exists(path)) {
        path = Paths.get(FILENAME);
      }
      list = Files.readAllLines(path);
      logger.info("File {} read successfully", FILENAME);

    } catch (IOException e) {
      throw new IOException(e);
    }
    return list;
  }
}
