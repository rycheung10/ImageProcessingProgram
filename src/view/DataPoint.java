package view;

import java.awt.*;

/**
 * Class represents each data point/bar on a histogram
 */
public class DataPoint {

  Color color;
  int height;
  int width;

  /**
   * Constructor that represents each individual histogram bar
   *
   * @param color Color of the bar
   * @param height Height of the bar
   * @param width Width of the bar
   */
  public DataPoint(Color color, int height, int width) {
    this.color = color;
    this.height = height;
    this.width = width;
  }
}
