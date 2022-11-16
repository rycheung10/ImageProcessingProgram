package view;

import java.awt.*;

import javax.swing.*;

import model.IPModelState;

/**
 * This class represents a view
 */
public class IPViewGUIImpl extends JFrame {
  
  public IPViewGUIImpl(IPModelState model) {
    super();
    this.setTitle("IP Program");
    this.setSize(1500, 1000);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    // create the layout of the GUI
    this.setLayout(new GridLayout(1, 2));
    
//    |-----------------------|  |-----------------------|
//    |                       |  |    histogramPanel     |
//    |       imgPanel        |  |------rightPanel-------|
//    |                       |  |     buttonsPanel      |
//    |_______________________|  |_______________________|
    
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
    JButton loadButton = new JButton("load");
    buttonsPanel.add(loadButton);
    JButton saveButton = new JButton("save");
    buttonsPanel.add(saveButton);
    JButton brightenButton = new JButton("brighten");
    buttonsPanel.add(brightenButton);
    JButton darkenButton = new JButton("darken");
    buttonsPanel.add(darkenButton);
    JButton verticalFlipButton = new JButton("vertical-flip");
    buttonsPanel.add(verticalFlipButton);
    JButton horizontalFlipButton = new JButton("horizontal-flip");
    buttonsPanel.add(horizontalFlipButton);
    JButton redComponentButton = new JButton("red-component");
    buttonsPanel.add(redComponentButton);
    JButton greenComponentButton = new JButton("green-component");
    buttonsPanel.add(greenComponentButton);
    JButton blueComponentButton = new JButton("blue-component");
    buttonsPanel.add(blueComponentButton);
    JButton valueComponentButton = new JButton("value-component");
    buttonsPanel.add(valueComponentButton);
    JButton intensityComponentButton = new JButton("intensity-component");
    buttonsPanel.add(intensityComponentButton);
    JButton lumaComponentButton = new JButton("luma-component");
    buttonsPanel.add(lumaComponentButton);
    JButton blurButton = new JButton("blur");
    buttonsPanel.add(blurButton);
    JButton sharpenButton = new JButton("sharpen");
    buttonsPanel.add(sharpenButton);
    JButton greyscaleLumaButton = new JButton("greyscale-luma");
    buttonsPanel.add(greyscaleLumaButton);
    JButton sepiaButton = new JButton("sepia");
    buttonsPanel.add(sepiaButton);
    
    
    
    
    
    this.setVisible(true);
  }
}
