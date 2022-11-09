import java.io.InputStreamReader;
import java.io.StringReader;

import controller.IPController;
import controller.IPControllerImpl;
import model.IPModel;
import model.IPModelImpl;
import view.IPView;
import view.IPViewImpl;

/**
 * This class contains the main method for running the Image Processing program.
 */
public class ImageProcessing {
  /**
   * This method is the main method that runs the program on a controller.
   *
   * @param args An array list of Strings representing the user's inputs.
   */
  public static void main(String[] args) {
    IPModel m1 = new IPModelImpl();
    IPView v1 = new IPViewImpl();
    Readable is = new StringReader("load res/techsupport.ppm ts save /users/ry.cheung/desktop/techsupportpng.jpg ts q");
    Readable re = new InputStreamReader(System.in);
    IPController c1 = new IPControllerImpl(m1, v1, re);
    
    c1.startIP();
  }
  
}
