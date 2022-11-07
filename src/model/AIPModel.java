package model;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents an abstract class of a model.
 */
public abstract class AIPModel implements IPModel {
  
  /**
   * This first constructor takes no parameters and initializes an image processing model with an
   * empty collection of addedImages.
   */
  protected final Map<String, PixelInfo[][]> addedImages;
  
  /**
   * This first constructor takes no arguments and initializes an abstract model.
   */
  public AIPModel() {
    this.addedImages = new HashMap<>();
  }
  
  @Override
  public abstract void addImage(String imgName, PixelInfo[][] imgPixels)
      throws IllegalArgumentException;
  
  @Override
  public abstract void greyscale(PixelComponents components, String imgName, String rename)
      throws IllegalArgumentException;
  
  @Override
  public abstract void flip(boolean vert, String imgName, String rename)
      throws IllegalArgumentException;
  
  @Override
  public abstract void brighten(int amount, String imgName, String rename)
      throws IllegalArgumentException;
  
  @Override
  public abstract void save(String path, String imgName) throws IllegalArgumentException;
  
  @Override
  public abstract void load(String path, String name) throws IllegalArgumentException;
  
  @Override
  public abstract int getHeight(String imgName) throws IllegalArgumentException;
  
  @Override
  public abstract int getWidth(String imgName) throws IllegalArgumentException;
  
  @Override
  public abstract Map<PixelComponents, Integer> getPixelInfo(String imgName, int row, int col)
      throws IllegalArgumentException;

  public abstract void filter(double[][] kernel, String imgName, String rename)
          throws IllegalArgumentException;
}
