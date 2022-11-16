package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.IPControllerGUI;

/**
 * This class represents a view of the IP program with a GUI.
 */
public class IPViewGUIImpl extends JFrame implements IPViewGUI, ActionListener {
  
  private final String[] buttons;
  private IPControllerGUI controllerGUI;
  
  /**
   * This first constructor creates the view with a GUI of an IP program.
   */
  public IPViewGUIImpl() {
    super();
    
    this.buttons = new String[]{"load", "save", "brighten", "darken", "vertical-flip",
        "horizontal-flip", "red-component", "green-component", "blue-component",
        "value-component", "intensity-component", "luma-component", "blur",
        "sharpen", "greyscale-luma", "sepia"};
    
    this.setTitle("IP Program");
    this.setSize(1500, 1000);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    // create the layout of the GUI
    this.setLayout(new GridLayout(1, 2));

//    |-----------------------||-----------------------|
//    |                       ||    histogramPanel     |
//    |       imgPanel        ||------rightPanel-------|
//    |                       ||     buttonsPanel      |
//    |_______________________||_______________________|
    
    // place panel to left (for image)
    JPanel imgPanel = new JPanel();
    this.add(imgPanel);
    
    // place panel to the right (for histogram and buttons)
    JPanel rightPanel = new JPanel();
    rightPanel.setLayout(new GridLayout(2, 1));
    this.add(rightPanel);
    
    // place panel to top right (for histogram)
    JPanel histogramPanel = new JPanel();
    rightPanel.add(histogramPanel);
    
    // place panel to bottom right (for buttons)
    JPanel buttonsPanel = new JPanel();
    buttonsPanel.setLayout(new GridLayout(4, 4));
    rightPanel.add(buttonsPanel);
    
    // manage panels
    
    // manage image panel
    imgPanel.setBackground(Color.yellow);
    JLabel imageLabel = new JLabel("imgPanel");
    imgPanel.add(imageLabel);
    
    // manage histogram panel
    histogramPanel.setBackground(Color.green);
    JLabel histogramLabel = new JLabel("histogramPanel");
    histogramPanel.add(histogramLabel);
    
    // manage buttons panel
    for (String button : this.buttons) {
      JButton newButton = new JButton(button);
      newButton.setActionCommand(button);
      newButton.addActionListener(this);
      buttonsPanel.add(newButton);
    }
  }
  
  @Override
  public void controllerGUI(IPControllerGUI controller) {
    this.controllerGUI = controller;
  }
  
  @Override
  public void seeGUI() {
    this.setVisible(true);
  }
  
  @Override
  public void renderPopUpMessage(String body, String title, int type) {
    JOptionPane.showMessageDialog(this, body, title, type);
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    String specialArgument = "";
    
    switch (command) {
      case "load":
        specialArgument = this.browseFiles(true);
        break;
      case "save":
        specialArgument = this.browseFiles(false);
        break;
      case "brighten":
        specialArgument = JOptionPane.showInputDialog("Enter amount to brighten:");
        break;
      case "darken":
        specialArgument = JOptionPane.showInputDialog("Enter amount to darken:");
        break;
      default:
        break;
    }
    
    this.controllerGUI.commandHandler(command, specialArgument);
    
  }
  
  /**
   * This method allows the user to browse through files to either upload or save an image
   * appropriately.
   *
   * @param load A boolean representing if the method should help a user load an image (true)
   *             or save an image (false)
   * @return A String representing the path of a file that has either been selected to be
   * uploaded or the desired saving destination.
   */
  private String browseFiles(boolean load) {
    
    JFileChooser fileChooser = new JFileChooser();
    
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "ppm, png, jpg, jpeg, bmp images", "ppm", "png", "jpg", "jpeg", "bmp");
    
    fileChooser.setFileFilter(filter);
    
    int valid;
    
    if (load) {
      valid = fileChooser.showOpenDialog(this);
    } else {
      valid = fileChooser.showSaveDialog(this);
    }
    
    if (valid == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      return f.getAbsolutePath();
    } else {
      return "";
    }
  }
}
