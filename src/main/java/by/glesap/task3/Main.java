package by.glesap.task3;

import by.glesap.task3.entity.Ship;
import by.glesap.task3.util.StorageLoader;

public class Main {
  public static void main(String[] args) {
    StorageLoader storage = new StorageLoader();
    storage.start();
    Ship ship1 = new Ship("a", 100, 0);
    Ship ship2 = new Ship("b", 100, 10);
    Ship ship3 = new Ship("c", 100, 20);
    Ship ship4 = new Ship("d", 100, 30);

    ship1.start();
    ship2.start();
    ship3.start();
    ship4.start();


  }
}