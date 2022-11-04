package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import controller.ImageUtil;

import static model.IPModelState.PixelComponents.Blue;
import static model.IPModelState.PixelComponents.Green;
import static model.IPModelState.PixelComponents.Max;
import static model.IPModelState.PixelComponents.Red;

/**
 * This class represents an object of a model of an image processor.
 */
public class IPModelImpl extends AIPModel {
  
  /**
   * This first constructor takes no arguments and calls the abstract constructor to create
   * a model.
   */
  public IPModelImpl() {
<<<<<<< HEAD
    super();
=======
    this.addedImages = new HashMap<>();
>>>>>>> 6aa3dfbe552b0c76166551be38662d1ee7a6d625
  }
  
  @Override
  public void addImage(String imgName, PixelInfo[][] imgPixels) throws IllegalArgumentException {
    if (imgName == null || imgName.equals("")) {
      throw new IllegalArgumentException("illegal image name given");
    }
    if (imgPixels == null) {
      throw new IllegalArgumentException("null pixels arraylist given");
    }
    this.addedImages.put(imgName, imgPixels);
  }
  
  @Override
  public void greyscale(PixelComponents components, String imgName, String rename)
      throws IllegalArgumentException {
    // check to see if imgName exists in addedImages already (throw IAE if not)
    this.imageExists(imgName);
    
    // create empty [][] with same size as the one attached to the given string
    PixelInfo[][] greyScaledImage = new PixelInfo[this.getHeight(imgName)][this.getWidth(imgName)];
    
    // for each row
    for (int i = 0; i < this.getHeight(imgName); i++) {
      // for each column
      for (int j = 0; j < this.getWidth(imgName); j++) {
        // get the pixel info of this pixel
        Map<PixelComponents, Integer> pixelInfoGrey = this.getPixelInfo(imgName, i, j);
        // get the desired value based on the component
        int desiredValue = pixelInfoGrey.get(components);
        // create a new pixel with that desired value as R G and B and put it into
        // greyScaledImage[][]
        greyScaledImage[i][j] = new PixelInfo(desiredValue, desiredValue, desiredValue,
            pixelInfoGrey.get(Max));
        
      }
    }
    this.addImage(rename, greyScaledImage);
  }
  
  @Override
  public void flip(boolean vert, String imgName, String rename) throws IllegalArgumentException {
    // check to see if imgName exists in addedImages already (throw IAE if not)
    this.imageExists(imgName);
    
    PixelInfo[][] orig = addedImages.get(imgName);
    PixelInfo[][] flipped = new PixelInfo[this.getHeight(imgName)][this.getWidth(imgName)];
    
    // if user wants to flip vertically
    if (vert) {
      for (int i = 0; i < this.getWidth(imgName); i++) {
        for (int j = 0; j < this.getHeight(imgName); j++) {
          flipped[j][i] = orig[this.getHeight(imgName) - j - 1][i];
        }
      }
    }
    // if user wants to flip horizontally
    else {
      // for each row
      for (int i = 0; i < this.getHeight(imgName); i++) {
        for (int j = 0; j < this.getWidth(imgName); j++) {
          flipped[i][j] = orig[i][this.getWidth(imgName) - j - 1];
        }
      }
    }
    this.addImage(rename, flipped);
  }
  
  @Override
  public void brighten(int amount, String imgName, String rename)
      throws IllegalArgumentException {
    // check to see if imgName exists in addedImages already (throw IAE if not)
    this.imageExists(imgName);
    
    // copy the [][] from the desired image
    PixelInfo[][] brightenedImage = new PixelInfo[this.getHeight(imgName)][this.getWidth(imgName)];
    
    // for each row
    for (int i = 0; i < this.getHeight(imgName); i++) {
      for (int j = 0; j < this.getWidth(imgName); j++) {
        // get the pixel info of this pixel from the copy
        Map<PixelComponents, Integer> pixelInfoBrightened = this.getPixelInfo(imgName, i, j);
        // change the red, green, and blue values by the given amount if it is between 0 and 255
        // (else round to the nearest bound)
        int redBrightened = rgbInBounds(pixelInfoBrightened.get(Red) + amount,
            pixelInfoBrightened);
        int greenBrightened = rgbInBounds(pixelInfoBrightened.get(Green) + amount,
            pixelInfoBrightened);
        int blueBrightened = rgbInBounds(pixelInfoBrightened.get(Blue) + amount,
            pixelInfoBrightened);
        // create new pixel with new R G B values and put it into brightenedImage[][]
        brightenedImage[i][j] = new PixelInfo(redBrightened, greenBrightened, blueBrightened,
            pixelInfoBrightened.get(Max));
      }
    }
    this.addImage(rename, brightenedImage);
  }
  
  /**
   * This method ensures that the given value is within the bounds of accepted RGB values. If not,
   * the value should be rounded to the nearest bound.
   *
   * @param i An integer representing the value that should be checked.
   * @return An integer representing a valid RGB value. If the value is less than 0, return 0. If
   *         the value is greater than 255, return 255.
   */
  private int rgbInBounds(int i, Map<PixelComponents, Integer> p) {
    if (i < 0) {
      return 0;
    } else if (i > p.get(Max)) {
      return p.get(Max);
    } else {
      return i;
    }
  }
  
  @Override
  public void save(String path, String imgName) throws IllegalArgumentException {
    // check to see if imgName exists in addedImages already (throw IAE if not)
    this.imageExists(imgName);
    
    int height = this.getHeight(imgName);
    int width = this.getWidth(imgName);
    int max = this.getPixelInfo(imgName, 0, 0).get(Max);
    
    // create file
    try {
      // create file at path location
      BufferedWriter bw = new BufferedWriter(new FileWriter(path));
      
      // add the formatting of PPM (i.e. P3, width x height, max value, etc)
      bw.write("P3\n");
      bw.write("#Created by RC & EK\n");
      bw.write(width + " " + height + "\n");
      bw.write(max + "\n");
      
      // write the RGB values
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          Map<PixelComponents, Integer> thisPixel = this.getPixelInfo(imgName, i, j);
          bw.write(thisPixel.get(Red) + "\n");
          bw.write(thisPixel.get(Green) + "\n");
          bw.write(thisPixel.get(Blue) + "\n");
        }
      }
      bw.close();
    } catch (IOException e) {
      throw new IllegalArgumentException("error occurred");
    }
  }
  
  @Override
  public void load(String path, String name) throws IllegalArgumentException {
    // add loadedPixels to addedImages
    this.addImage(name, ImageUtil.readPPM(path));
  }
  
  @Override
  public int getHeight(String imgName) throws IllegalArgumentException {
    // check to see if imgName exists in addedImages already (throw IAE if not)
    this.imageExists(imgName);
    return this.addedImages.get(imgName).length;
  }
  
  @Override
  public int getWidth(String imgName) throws IllegalArgumentException {
    // check to see if imgName exists in addedImages already (throw IAE if not)
    this.imageExists(imgName);
    return this.addedImages.get(imgName)[0].length;
  }
  
  @Override
  public Map<PixelComponents, Integer> getPixelInfo(String imgName, int row, int col)
      throws IllegalArgumentException {
    // check to see if imgName exists in addedImages already (throw IAE if not)
    this.imageExists(imgName);
    
    // check to see if row and col are valid coordinate for the specified picture
    int maxRow = this.getHeight(imgName);
    int maxCol = this.getWidth(imgName);
    if (row < 0 || col < 0 || row > maxRow || col > maxCol) {
      throw new IllegalArgumentException("invalid coordinates given");
    }
    
    return this.addedImages.get(imgName)[row][col].getPixelInfo();
  }
<<<<<<< HEAD
=======

>>>>>>> 6aa3dfbe552b0c76166551be38662d1ee7a6d625
  
  /**
   * This method checks if a specified image is not a part of the addedImages.
   *
   * @param imgName A String representing the specified image.
   * @throws IllegalArgumentException when the String name is not found in addedImages.
   */
  private void imageExists(String imgName) throws IllegalArgumentException {
    if (!this.addedImages.containsKey(imgName)) {
      throw new IllegalArgumentException(imgName + " doesn't exist");
    }
  }
}