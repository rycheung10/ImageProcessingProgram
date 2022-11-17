package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;

public class IPHistogramImpl extends JPanel implements IPHistogram{

  ArrayList<DataPoint> redBarsData;
  ArrayList<DataPoint> greenBarsData;
  ArrayList<DataPoint> blueBarsData;
  ArrayList<DataPoint> intensityBarsData;

  int height;
  int width;

  public IPHistogramImpl(int height, int width) {

    this.redBarsData = new ArrayList<DataPoint>();
    this.greenBarsData = new ArrayList<DataPoint>();
    this.blueBarsData = new ArrayList<DataPoint>();
    this.intensityBarsData = new ArrayList<DataPoint>();
    this.height = height;
    this.width = width;
  }


  //probably have to adjust the height (currently its like a percentage out of 100)
  @Override
  public void addData(Map<Integer, Integer> redData, Map<Integer, Integer> greenData,
                      Map<Integer, Integer> blueData, Map<Integer, Integer> intensityData) {

      for(int i = 0; i <= 255; i++) {
        this.redBarsData.add(new DataPoint(Color.red, (redData.get(i) / height) * 100, 1));
      }

      for(int j = 0; j <= 255; j++) {
        this.greenBarsData.add(new DataPoint(Color.green, (greenData.get(j) / height) * 100, 1));
      }

      for(int k = 0; k <= 255; k++) {
        this.blueBarsData.add(new DataPoint(Color.blue, (blueData.get(k) / height) * 100, 1));
      }

      for(int l = 0; l <= 255; l++) {
        this.intensityBarsData.add(new DataPoint(Color.white, greenData.get(l), 1));
      }
  }

}
