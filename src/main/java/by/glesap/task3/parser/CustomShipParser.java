package by.glesap.task3.parser;

import by.glesap.task3.entity.Ship;

import java.util.List;

public interface CustomShipParser {

  List<Ship> parseShips(List<String> lines);
}
