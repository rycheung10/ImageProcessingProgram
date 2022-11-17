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

  public IPHistogramImpl(ArrayList<DataPoint> redData, ArrayList<DataPoint> greenData,
                         ArrayList<DataPoint> blueData, ArrayList<DataPoint> intensityData) {

    this.redBarsData = redBarsData;
    this.greenBarsData = greenBarsData;
    this.blueBarsData = blueBarsData;
    this.intensityBarsData = intensityBarsData;
  }


  //probably have to adjust the height within each class
  @Override
  public void addData(Map<Integer, Integer> redData, Map<Integer, Integer> greenData,
                      Map<Integer, Integer> blueData, Map<Integer, Integer> intensityData) {

      for(int i = 0; i <= 255; i++) {
        this.redBarsData.add(new DataPoint(Color.red, redData.get(i), 1));
      }

      for(int j = 0; j <= 255; j++) {
        this.greenBarsData.add(new DataPoint(Color.green, greenData.get(j), 1));
      }

      for(int k = 0; k <= 255; k++) {
        this.blueBarsData.add(new DataPoint(Color.blue, blueData.get(k), 1));
      }

      for(int l = 0; l <= 255; l++) {
        this.intensityBarsData.add(new DataPoint(Color.white, greenData.get(l), 1));
      }
  }

}
