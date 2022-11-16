package view;

import controller.IPControllerGUI;

/**
 * Represents an interface for a view of the IP program's GUI.
 */
public interface IPViewGUI {
  
  /**
   * This method sets the ViewGUI's controller field to link a controller with the view
   * appropriately.
   *
   * @param controller A IPControllerGUIImpl that represents the controller that
   *                   should be linked to the view.
   */
  void controllerGUI(IPControllerGUI controller);
  
  /**
   * This method allows the GUI to be seen by setting it to be visible with a
   * JFrame method.
   */
  void seeGUI();
  
  /**
   * This method allows a customized pop-up message to be delivered through the GUI.
   *
   * @param body  A String representing the body of the pop-up message.
   * @param title A String representing the title of the pop-up message.
   * @param type  An integer representing the type of pop-up message (i.e. informative,
   *              error, etc)
   */
  void renderPopUpMessage(String body, String title, int type);
}
