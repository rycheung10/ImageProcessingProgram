package controller;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.IPModel;
import view.IPView;

import static model.IPModelState.PixelComponents.Blue;
import static model.IPModelState.PixelComponents.Green;
import static model.IPModelState.PixelComponents.Intensity;
import static model.IPModelState.PixelComponents.Luma;
import static model.IPModelState.PixelComponents.Red;
import static model.IPModelState.PixelComponents.Value;

/**
 * Represents the controller of the Image Processing program. This class handles inputs and
 * communicates with the model/view.
 */
public class IPControllerImpl implements IPController {
  
  private final IPModel model;
  private final IPView view;
  private final Readable input;
  private boolean programRunning;
  
  /**
   * Constructor that represents a controller .
   *
   * @param model an image processing model
   * @param view  an image processing textview
   * @param input object that allows the constructor to read
   * @throws IllegalArgumentException when given a null object
   */
  public IPControllerImpl(IPModel model, IPView view, Readable input)
      throws IllegalArgumentException {
    
    if (model == null || view == null || input == null) {
      throw new IllegalArgumentException("You can't input null!");
    }
    
    this.model = model;
    this.view = view;
    this.input = input;
    this.programRunning = true;
    
  }
  
  /**
   * Starts the Image Processing program.
   */
  @Override
  public void startIP() throws IllegalStateException {
    Scanner sc = new Scanner(this.input);
    while (programRunning) {
      
      this.renderMessage("What would you like to do?\n");
      
      // throw ISE if readable is out of arguments (sc.next() not possible)
      try {
        this.commandHandler(getStringInput(sc), sc);
      } catch (IllegalArgumentException e) {
        this.renderMessage("Error: " + e.getMessage() + "\n");
      }
    }
  }
  
  /**
   * This method appends a message to the view's appendable.
   *
   * @param s A String representing the desired message to be appended.
   * @throws IllegalStateException when the message is unable to be appended to
   *                               the view's appendable.
   */
  private void renderMessage(String s) throws IllegalStateException {
    try {
      this.view.renderMessage(s);
    } catch (IOException e) {
      throw new IllegalStateException("Error rendering");
    }
  }
  
  /**
   * This method takes a String and a scanner and determines if the string is a valid command. If
   * it is, get the correct amount of arguments from the scanner.
   *
   * @param cmd A String representing a phrase to check to see if it is a command.
   * @param sc  A Scanner that represents the users' inputs.
   * @throws IllegalArgumentException when there are not enough arguments in the readable.
   */
  private void commandHandler(String cmd, Scanner sc) throws IllegalArgumentException {

    double[][] blur =
                    {{0.0625, 0.1250, 0.0625},
                    {0.1250, 0.2500, 0.1250},
                    {0.0625, 0.1250, 0.0625}};

    double[][] sharpen =
            {{-0.125, -0.125, -0.125, -0.125, -0.125},
            {-0.125, 0.250, 0.250, 0.250, -0.125},
            {-0.125, 0.250, 1.000, 0.250, -0.125},
            {-0.125, 0.250, 0.250, 0.250, -0.125},
            {-0.125, -0.125, -0.125, -0.125, -0.125}};

    double[][] greyscale_luma =
                            {{0.216, 0.7152, 0.0722},
                            {0.216, 0.7152, 0.0722},
                            {0.216, 0.7152, 0.0722}};

    double[][] sepia = {{0.393, 0.769, 0.189},
            {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};
    try {
      switch (cmd) {
        case "q":
          this.renderMessage("IP quit!");
          this.programRunning = false;
          break;
        case "load":
          this.model.load(getStringInput(sc), getStringInput(sc));
          this.renderMessage("Load success!\n");
          break;
        case "brighten":
          
          int amount;
          try {
            amount = Integer.parseInt(getStringInput(sc));
          } catch (NumberFormatException e) {
            throw new IllegalStateException();
          }
          
          this.model.brighten(amount, getStringInput(sc), getStringInput(sc));
          this.renderMessage("Brighten success!\n");
          break;
        case "vertical-flip":
          this.model.flip(true, getStringInput(sc), getStringInput(sc));
          this.renderMessage("Vertical flip success!\n");
          break;
        case "horizontal-flip":
          this.model.flip(false, getStringInput(sc), getStringInput(sc));
          this.renderMessage("Horizontal flip success!\n");
          break;
        case "red-component":
          this.model.greyscale(Red, getStringInput(sc), getStringInput(sc));
          this.renderMessage("Red component greyscale success!\n");
          break;
        case "green-component":
          this.model.greyscale(Green, getStringInput(sc), getStringInput(sc));
          this.renderMessage("Green component greyscale success!\n");
          break;
        case "blue-component":
          this.model.greyscale(Blue, getStringInput(sc), getStringInput(sc));
          this.renderMessage("Blue component greyscale success!\n");
          break;
        case "value-component":
          this.model.greyscale(Value, getStringInput(sc), getStringInput(sc));
          this.renderMessage("Value component greyscale success!\n");
          break;
        case "intensity-component":
          this.model.greyscale(Intensity, getStringInput(sc), getStringInput(sc));
          this.renderMessage("Intensity component greyscale success!\n");
          break;
        case "luma-component":
          this.model.greyscale(Luma, getStringInput(sc), getStringInput(sc));
          this.renderMessage("Luma component greyscale success!\n");
          break;
        case "save":
          this.model.save(getStringInput(sc), getStringInput(sc));
          this.renderMessage("Image saved!\n");
          break;
        case "blur":
          this.model.filter(blur, getStringInput(sc), getStringInput(sc));
          this.renderMessage("Blur success!\n");
          break;
        case "sharpen":
          this.model.filter(sharpen, getStringInput(sc), getStringInput(sc));
          this.renderMessage("Sharpen success!\n");
          break;
        case "greyscale-luma":
          this.model.colorTransformation(
                  greyscale_luma, getStringInput(sc), getStringInput(sc));
          this.renderMessage("Greyscale-luma success!\n");
          break;
        case "sepia":
          this.model.colorTransformation(
                  sepia, getStringInput(sc), getStringInput(sc));
          this.renderMessage("Sepia-tone success!\n");
          break;

        default:
          this.renderMessage("Invalid command given: " + cmd + "\n");
      }
    } catch (IllegalStateException e) {
      this.renderMessage("Invalid command arguments given\n");
    }
  }
  
  /**
   * This method gets the next String from the given scanner if possible.
   *
   * @param sc A Scanner representing the user's inputs.
   * @return A String representing the next thing the user inputted into the scanner.
   * @throws IllegalStateException If the readable is out of arguments.
   */
  private String getStringInput(Scanner sc) throws IllegalStateException {
    String s;
    try {
      return sc.next().toLowerCase();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("Readable out of arguments");
    }
  }
}
