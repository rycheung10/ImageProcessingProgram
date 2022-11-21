package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import model.IPModel;
import model.IPModelState.PixelComponents;

import static model.IPModelState.PixelComponents.Blue;
import static model.IPModelState.PixelComponents.Green;
import static model.IPModelState.PixelComponents.Intensity;
import static model.IPModelState.PixelComponents.Red;

//!!!!!!!! each histogram graph is 375 x 250

/**
 * Represents the implementation of the creation of the four histogram graphs for a given image
 */
public class IPHistogramImpl extends JPanel implements IPHistogram {

  private final ArrayList<DataPoint> redBarsData;
  private final ArrayList<DataPoint> greenBarsData;
  private final ArrayList<DataPoint> blueBarsData;
  private final ArrayList<DataPoint> intensityBarsData;
  private final IPModel model;
  private final int height;
  private final int width;

  /**
   * Represents the constructor to create histogram models
   *
   * @param height height of each histogram
   * @param width width of each histogram
   * @param model an IPModel object
   */
  public IPHistogramImpl(int height, int width, IPModel model) {

    this.redBarsData = new ArrayList<DataPoint>();
    this.greenBarsData = new ArrayList<DataPoint>();
    this.blueBarsData = new ArrayList<DataPoint>();
    this.intensityBarsData = new ArrayList<DataPoint>();
    this.height = height;
    this.width = width;
    this.model = model;

  }

  @Override
  public void createHistogramModel(String imgName) {
    Map<Integer, Integer> redData = collectData(Red, imgName);
    Map<Integer, Integer> greenData = collectData(Green, imgName);
    Map<Integer, Integer> blueData = collectData(Blue, imgName);
    Map<Integer, Integer> intensityData = collectData(Intensity, imgName);

    int totalPixelsInImg = this.model.getHeight(imgName) * this.model.getWidth(imgName);

    for (int i = 0; i <= 255; i++) {
      if (redData.containsKey(i)) {
        this.redBarsData.add(new DataPoint(Color.red,
                (int) (((double) redData.get(i) / (double) totalPixelsInImg) * this.height),
                1));
      } else {
        this.redBarsData.add(new DataPoint(Color.red, 0, 1));
      }
    }

    for (int j = 0; j <= 255; j++) {
      if (greenData.containsKey(j)) {
        this.greenBarsData.add(new DataPoint(Color.green,
                (int) (((double) greenData.get(j) / (double) totalPixelsInImg) * this.height),
                1));
      } else {
        this.greenBarsData.add(new DataPoint(Color.green, 0, 1));
      }
    }

    for (int k = 0; k <= 255; k++) {
      if (blueData.containsKey(k)) {
        this.blueBarsData.add(new DataPoint(Color.blue,
                (int) (((double) blueData.get(k) / (double) totalPixelsInImg) * this.height),
                1));
      } else {
        this.blueBarsData.add(new DataPoint(Color.blue, 0, 1));
      }
    }

    for (int l = 0; l <= 255; l++) {
      if (intensityData.containsKey(l)) {
        this.intensityBarsData.add(new DataPoint(Color.black,
                (int) (((double) intensityData.get(l) / (double) totalPixelsInImg) * this.height),
                1));
      } else {
        this.intensityBarsData.add(new DataPoint(Color.black, 0, 1));
      }
    }
  }

  private Map<Integer, Integer> collectData(PixelComponents component, String imgName) {
    Map<Integer, Integer> data = new HashMap<>();
    for (int i = 0; i < this.model.getHeight(imgName); i++) {
      for (int j = 0; j < this.model.getWidth(imgName); j++) {

        int currNum = this.model.getPixelInfo(imgName, i, j).get(component);
        if (data.containsKey(currNum)) {
          data.put(currNum, data.get(currNum) + 1);
        } else {
          data.put(currNum, 1);
        }
      }
    }
    return data;
  }

  // frequency  |
  //            |
  //            |
  //            |
  //            |
  //            |
  //            |
  //             ---------------------------------
  //             color values

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D graphs = (Graphics2D) g;

    if (this.redBarsData.size() > 0 && this.blueBarsData.size() > 0
            && this.greenBarsData.size() > 0 && this.intensityBarsData.size() > 0) {
      for (int i = 0; i <= 255; i++) {
        graphs.setColor(this.redBarsData.get(i).color);
        graphs.fillRect(i * this.redBarsData.get(i).width + ((int)(0.0833 * (double) this.width)),
                this.height - ((int)(0.3125 * (double) this.height)) - this.redBarsData.get(i).height - this.height/2,
                this.redBarsData.get(i).width, this.redBarsData.get(i).height);
      }
//height: 800 width: 600
      for (int i = 0; i <= 255; i++) {
        graphs.setColor(this.greenBarsData.get(i).color);
        graphs.fillRect(i * this.greenBarsData.get(i).width + ((int) (0.583 * (double) this.width)),
                this.height - ((int)(0.3125 * (double) this.height)) - this.greenBarsData.get(i).height - this.height/2,
                this.greenBarsData.get(i).width, this.greenBarsData.get(i).height);
      }

      for (int i = 0; i <= 255; i++) {
        graphs.setColor(this.blueBarsData.get(i).color);
        graphs.fillRect(i * this.blueBarsData.get(i).width + ((int)(0.0833 * (double) this.width)),
                this.height - ((int)(0.125 * (double) this.height)) - this.blueBarsData.get(i).height - this.height/2,
                this.blueBarsData.get(i).width, this.blueBarsData.get(i).height);
      }

      for (int i = 0; i <= 255; i++) {
        graphs.setColor(this.intensityBarsData.get(i).color);
        graphs.fillRect(i * this.intensityBarsData.get(i).width + ((int) (0.583 * (double) this.width)),
                this.height - ((int)(0.125 * (double) this.height)) - this.intensityBarsData.get(i).height - this.height/2,
                this.intensityBarsData.get(i).width, this.intensityBarsData.get(i).height);
      }
    }
  }
}
