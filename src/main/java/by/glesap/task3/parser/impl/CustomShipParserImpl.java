package by.glesap.task3.parser.impl;

import by.glesap.task3.entity.Ship;
import by.glesap.task3.parser.CustomShipParser;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomShipParserImpl implements CustomShipParser {
  private static final Logger logger = LoggerFactory.getLogger(CustomShipParserImpl.class);
  /**
   * Regex that literally finds correct int numbers in string.
   */
  private static final String SHIP_PARAMETERS_REGEX = "\\w+";

  @Override
  public List<Ship> parseShips(List<String> lines) {
    List<Ship> ships = new ArrayList<>();
    logger.info("Parsing ships from lines");
    Pattern namePattern = Pattern.compile(SHIP_PARAMETERS_REGEX);
    for (String line : lines) {
      String name = null;
      Integer capacity = null;
      Integer containerAmount = null;
      Matcher matcher = namePattern.matcher(line);
      if (matcher.find()) {
        name = matcher.group();
      }
      if (matcher.find()) {
        capacity = Integer.parseInt(matcher.group());
      }
      if (matcher.find()) {
        containerAmount = Integer.parseInt(matcher.group());
      }
      if (name != null && capacity != null && containerAmount != null) {
        ships.add(new Ship(name, capacity, containerAmount));
      }
    }
    return ships;
  }
}
