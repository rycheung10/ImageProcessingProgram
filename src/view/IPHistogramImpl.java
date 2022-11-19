package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import model.IPModel;
import model.IPModelState;
import model.IPModelState.PixelComponents;

import static model.IPModelState.PixelComponents.Blue;
import static model.IPModelState.PixelComponents.Green;
import static model.IPModelState.PixelComponents.Intensity;
import static model.IPModelState.PixelComponents.Red;

//!!!!!!!! each histogram graph is 375 x 250
public class IPHistogramImpl extends JPanel implements IPHistogram{

  ArrayList<DataPoint> redBarsData;
  ArrayList<DataPoint> greenBarsData;
  ArrayList<DataPoint> blueBarsData;
  ArrayList<DataPoint> intensityBarsData;

  IPModel model;

  int height;
  int width;

  public IPHistogramImpl(int height, int width, IPModel model) {

    this.redBarsData = new ArrayList<DataPoint>();
    this.greenBarsData = new ArrayList<DataPoint>();
    this.blueBarsData = new ArrayList<DataPoint>();
    this.intensityBarsData = new ArrayList<DataPoint>();
    this.height = height;
    this.width = width;
    this.model = model;
  }


  //probably have to adjust the height (currently its like a percentage out of 100)
  @Override
  public void createHistogramModel(String imgName) {
    Map<Integer, Integer> redData = collectData(Red, imgName);
    Map<Integer, Integer> greenData = collectData(Green, imgName);
    Map<Integer, Integer> blueData = collectData(Blue, imgName);
    Map<Integer, Integer> intensityData = collectData(Intensity, imgName);

      for(int i = 0; i <= 255; i++) {
        if(redData.containsKey(i)) {
          this.redBarsData.add(new DataPoint(Color.red, (redData.get(i) / height) * 100, 1));
        }
      }

      for(int j = 0; j <= 255; j++) {
        if(greenData.containsKey(j)) {
          this.greenBarsData.add(new DataPoint(Color.green, (greenData.get(j) / height) * 100, 1));
        }
      }

      for(int k = 0; k <= 255; k++) {
        if(blueData.containsKey(k)) {
          this.blueBarsData.add(new DataPoint(Color.blue, (blueData.get(k) / height) * 100, 1));
        }
      }

      for(int l = 0; l <= 255; l++) {
        if(intensityData.containsKey(l)) {
          this.intensityBarsData.add(new DataPoint(Color.black, greenData.get(l), 1));
        }
      }
  }

  private Map<Integer, Integer> collectData(PixelComponents component, String imgName) {
      Map<Integer, Integer> data = new HashMap<>();
      for(int i = 0; i < this.model.getHeight(imgName); i++) {
        for(int j = 0; j < this.model.getWidth(imgName); j++) {
          int currNum = this.model.getPixelInfo(imgName, i, j).get(component);
          if(data.containsKey(currNum)) {
            data.put(currNum, data.get(currNum) + 1);
          } else {
            data.put(currNum, 1);
          }
        }
      }
      return data;
  }

}
