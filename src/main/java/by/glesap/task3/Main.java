package by.glesap.task3;

import by.glesap.task3.entity.Ship;
import by.glesap.task3.parser.CustomShipParser;
import by.glesap.task3.parser.impl.CustomShipParserImpl;
import by.glesap.task3.reader.CustomFileReader;
import by.glesap.task3.reader.impl.CustomFileReaderImpl;
import by.glesap.task3.util.StorageLoader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
  public static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) throws InterruptedException {
    StorageLoader storage = new StorageLoader();
    storage.start();
    CustomFileReader reader = new CustomFileReaderImpl();
    List<String> lines;
    try {
      lines = reader.readFile("data/input.txt");
    } catch (IOException e) {
      logger.error(e.getMessage());
      logger.error("Shutting down...");
      return;
    }
    CustomShipParser parser = new CustomShipParserImpl();
    List<Ship> ships = parser.parseShips(lines);
    try (ExecutorService service = Executors.newCachedThreadPool()) {
      for (Ship ship : ships) {
        service.submit(ship);
        TimeUnit.MICROSECONDS.sleep(100);
      }
    }
  }
}