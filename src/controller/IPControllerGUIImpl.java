package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import controller.commands.Brighten;
import controller.commands.ColorTransformation;
import controller.commands.Component;
import controller.commands.Filter;
import controller.commands.Flip;
import controller.commands.IPCommand;
import controller.commands.Load;
import controller.commands.Matrices;
import controller.commands.Save;
import model.IPModel;
import view.IPViewGUI;

import javax.swing.JOptionPane;

import static model.IPModelState.PixelComponents.Blue;
import static model.IPModelState.PixelComponents.Green;
import static model.IPModelState.PixelComponents.Intensity;
import static model.IPModelState.PixelComponents.Luma;
import static model.IPModelState.PixelComponents.Red;
import static model.IPModelState.PixelComponents.Value;

/**
 * This class represents the GUI controller for an IP program.
 */
public class IPControllerGUIImpl implements IPControllerGUI {
  
  private final IPModel model;
  private final IPViewGUI view;
  private final Map<String, Function<String, IPCommand>> commands;
  
  /**
   * This first constructor takes a model and ViewGUI and creates a GUI controller
   * that operates with both objects in cohesion and appropriately.
   *
   * @param model An IPModel that represents the model that should be operated on.
   * @param view  An IPViewGUI that represents the view that should represent the model
   *              appropriately and display changes made via the controller.
   * @throws IllegalArgumentException when a given parameter is null.
   */
  public IPControllerGUIImpl(IPModel model, IPViewGUI view) throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("null parameters given");
    }
    this.model = model;
    this.view = view;
    this.commands = new HashMap<>();
    this.loadCommands();
  }
  
  @Override
  public void startIPGUI() {
    this.view.controllerGUI(this);
    this.view.seeGUI();
  }
  
  @Override
  public void commandHandler(String method, String specialArgument) {
    IPCommand command;
    
    Function<String, IPCommand> cmd =
        this.commands.getOrDefault(method, null);
    
    // if cmd is null
    if (cmd == null) {
      this.renderPopUpMessage("Invalid command given: " + method, "Error",
          JOptionPane.ERROR_MESSAGE);
    } else {
      try {
        command = cmd.apply(specialArgument);
        command.execute(this.model);
        // because command successfully went through, an image exists for sure at this stage
        
        // draw the image
        this.view.drawImage("image");
        
        // give a success message
        this.renderPopUpMessage(method + " success!", "Success",
            JOptionPane.INFORMATION_MESSAGE);
      } catch (IllegalArgumentException e) {
        this.renderPopUpMessage("There was a problem: " + e.getMessage(), "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }
  
  /**
   * This method allows a customized pop-up message to be delivered through the view GUI.
   *
   * @param body  A String representing the body of the pop-up message.
   * @param title A String representing the title of the pop-up message.
   * @param type  An integer representing the type of pop-up message (i.e. informative,
   *              error, etc)
   */
  private void renderPopUpMessage(String body, String title, int type) {
    this.view.renderPopUpMessage(body, title, type);
  }
  
  /**
   * This method gets the next integer from the given scanner if possible.
   *
   * @param s A Scanner representing the user's inputs
   * @return An integer representing the next integer the user inputted into the scanner
   * @throws IllegalArgumentException If the readable is out of arguments or if the next input
   *                                  is not an integer
   */
  private int getIntInput(String s) throws IllegalArgumentException {
    try {
      return Integer.parseInt(s);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("invalid integer given");
    }
  }
  
  /**
   * This method loads all valid commands into the commands Map for this controller.
   */
  private void loadCommands() {
    String placeHolder = "image";
    
    this.commands.put("load", str ->
        new Load(str, placeHolder));
    this.commands.put("save", str ->
        new Save(str, placeHolder));
    this.commands.put("brighten", str ->
        new Brighten(getIntInput(str), placeHolder, placeHolder));
    this.commands.put("darken", str ->
        new Brighten(getIntInput(str) * -1, placeHolder, placeHolder));
    this.commands.put("vertical-flip", str ->
        new Flip(true, placeHolder, placeHolder));
    this.commands.put("horizontal-flip", str ->
        new Flip(false, placeHolder, placeHolder));
    this.commands.put("red-component", str ->
        new Component(Red, placeHolder, placeHolder));
    this.commands.put("green-component", str ->
        new Component(Green, placeHolder, placeHolder));
    this.commands.put("blue-component", str ->
        new Component(Blue, placeHolder, placeHolder));
    this.commands.put("value-component", str ->
        new Component(Value, placeHolder, placeHolder));
    this.commands.put("intensity-component", str ->
        new Component(Intensity, placeHolder, placeHolder));
    this.commands.put("luma-component", str ->
        new Component(Luma, placeHolder, placeHolder));
    this.commands.put("blur", str ->
        new Filter(Matrices.blur, placeHolder, placeHolder));
    this.commands.put("sharpen", str ->
        new Filter(Matrices.sharpen, placeHolder, placeHolder));
    this.commands.put("greystrale-luma", str ->
        new ColorTransformation(Matrices.greyscaleluma, placeHolder, placeHolder));
    this.commands.put("sepia", str ->
        new ColorTransformation(Matrices.sepia, placeHolder, placeHolder));
  }
}
