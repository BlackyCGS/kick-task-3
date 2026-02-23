package by.glesap.task3.service;

import by.glesap.task3.entity.Dock;
import by.glesap.task3.entity.Ship;

public interface DockService {
  Dock requestFreeDock();

  void dockShip(Ship ship, Dock dock);

  void undockShip(Ship ship);
}
