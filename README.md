<br>________________________________________________________________________________________________

## imageProcessingProgram Overview:

This is Ryan Cheung and Ethan Kong's image processing program that allows users to operate on
images in similar ways to photoshop. After edits are done, the user is able to save their new image
onto their computer! This program follows a standard Model View Controller (MVC) design pattern
coupled with the command design pattern that allows for minimal coupling between classes.

<br>________________________________________________________________________________________________

### Diagram:

Because of the MVC design pattern, we can see minimal coupling between classes in the diagram below.
Please see Diagram.png.
<br>

<br>________________________________________________________________________________________________

## Class/Interface design choices:

- ### Model:
    - ##### PixelInfo class:
        - Represents all the data in a pixel, including Red, Green, Blue,
          Value, Intensity, and Luma
        - Fields:
            - Max: represents the max value that red, green, or blue can have
            - Red: value of red in a pixel
            - Green: value of green in a pixel
            - Blue: value of blue in a pixel
            - Value: highest value amongst red, green, and blue
            - Intensity: average of red, green, and blue
            - Luma: weighted average of red, green, and blue
        - Here, we assigned each pixel to a HashMap that maps the different components of pixel
          information to values.
            - For example: Red could be mapped to 255, Green could be mapped to 0 ...
              Intensity mapped to the average of the mapped values to Red, Green and Blue.
        - Using a HashMap here stores 7 pieces of data (Max, Red, Green, Blue, Value...) into one
          object, allowing for
          dynamic access, simple manipulation of data and reducing code repetition in our model
          methods.
    - ##### IPModelState interface:
        - Represents the methods surrounding the properties of an image/pixel
        - ie. getHeight() and getWidth() - gives the image dimensions
        - getPixelInfo() allows us to obtain the data of a specific pixel
        - These 3 methods in the interface allows methods in the IPModelImpl to easily return
          information about the state of the image, effectively reducing code repetition without
          manipulating the image
    - ##### IPModel interface:
        - This interface extends the IPModelState and represents the controls
          of the program (ie. save(), load(), greyscale(), flip() etc...) that modifies an image or
          the model itself
    - ##### AIPModel abstract class:
        - This abstract class (implements IPModel) is made in anticipation of future models that operate on images in
          different manners.
        - Fields:
            - addedImages - allows a user to load and store images
                - This field is private, as we don't want the controller or view
                  to modify our collection of images. Manipulation of the model object should only
                  happen
                  within the implementation of this class
    - ##### IPModelImpl class:
        - Implements the methods of the model interfaces (extends AIPModel)
        - Uses key word super to call abstract constructor.
        - Private helper: imageExists - is a helper that is utilized by all Model interface methods
          to
          check if the desired image to modified is loaded into the program or not.
            - This method is private as it is only meant to supplement the methods within the class
              and used to reduce code duplication. Furthermore, the methods aren't directly publicly
              used
            - For example: greyscale() takes an image name and utilizes the imageExists method to
              check if the desired image is ready to be modified or not. If the image isn't loaded
              into
              the program, the program should not attempt to modify the image and throw an IAE. The
              program will not end due to the IAE as it will be caught later in the controller.

- ### View:
    - ##### IPView interface:
        - Represents the methods that allow for outputs to be rendered
    - ##### AIPView abstract class:
      - This abstract class (implements IPView) is made in anticipation of future models that display messages
      - All views will have an Appendable as a field (only some will need a model -- our current view does not)
    - ##### IPViewImpl class:
        - Currently only implements the method that renders messages to the output
        - Fields:
            - output: represents the place where the desired messages should be stored to send
              to the viewer
        - renderMessage() throws an IOE when there is an error rendering the input to the output
          (ie. CorruptedAppendable tests)
- ### Controller:
    - ##### IPController interface:
        - Represents the method startIP() that allows the user to interact with the model
          and view of the image processing program
    - ##### IPControllerImpl class:
        - This class implements the IPController interface.
        - Fields:
            - model: represents the model object that is being controlled by the user through the
              controller
            - view: represents the view object that messages should be given to to be displayed to
              the user
            - input: represents the user's inputs into the image processing program.
            - programmingRunning - boolean that tracks if the program should continue running.
              This field should be private but not final, because it is exclusive to the controller
              but needs to be reassigned at the appropriate time.

        - Private helper renderMessage() allows us to render messages when certain
          input criteria aren't met or when the program is ending.
        - Private method getStringInput() allows the program to take user commands
        - Private handler method commandHandler() takes the string or integer inputs and calls the
          necessary model methods to perform the desired modifications to the image.
        - Each of these helpers are private as they only serve the purpose of assisting the
          startIP(), which is the public method.

### Other design choices:

- Illegal Argument Exceptions are thrown to prevent illegal objects from being constructed or to
  prevent illegal arguments from being inputted into methods in the model.
- IOExceptions are thrown in the view if a message is unable to be appended to the appendable field.
- Illegal State Exceptions are thrown in the controller when the readable is out of arguments.
- Aside for controller: controller catches illegal argument exceptions and IOExceptions in order to
  prevent the program from breaking prematurely or due to a reason that is invalid.

<br>________________________________________________________________________________________________

### How to use the program:

When the program starts, the terminal will prompt, "What would you like to do?"
The user must use the load command first before attempting to use any image-editing command,
otherwise, the image-editing commands will do nothing. - Ex: "load res/techsupport.ppm tech".

##### Commands that the program accepts:

1. "load [String directory path to image] [String name of img]"
    - loads an image into view
    - Ex: "load res/techsupport.ppm tech"
2. "brighten [integer amount] [String targeted img name] [String desired name of new img]"
    - brightens an image by an integer amount
    - Ex: "brighten 100 tech techBrighten"
3. "vertical-flip [String targeted img name] [String desired name of new img]"
    - vertically flips an image
    - Ex: "vertical-flip tech techFlipped"
4. "horizontal-flip [String targeted img name] [String desired name of new img]"
    - horizontally flips an image
    - Ex: "horizontal-flip tech techFlipped2"
5. "red-component [String targeted img name] [String desired name of new img]"
    - greyscales the image according to the red-component value of each pixel
    - Ex: "red-component tech techRed"
6. "green-component [String targeted img name] [String desired name of new img]"
    - greyscales the image according to the green-component value of each pixel
    - Ex: "green-component tech techGreen"
7. "blue-component [String targeted img name] [String desired name of new img]"
    - greyscales the image according to the blue-component value of each pixel
    - Ex: "blue-component tech techBlue"
8. "value-component [String targeted img name] [String desired name of new img]"
    - greyscales the image according to the value-component value of each pixel (the value-component
      is the maximum value between the red/green/blue components)
    - Ex: "value-component tech techValue"
9. "intensity-component [String targeted img name] [String desired name of new img]"
    - greyscales the image according to the intensity-component value of each pixel
      (the intensity-component is the average value between the red/green/blue components)
    - Ex: "intensity-component tech techIntensity"
10. "luma-component [String targeted img name] [String desired name of new img]"
    - greyscales the image according to the luma-component value of each pixel
      (luma is a weighted average between the rgb component values)
    - Ex: "luma-component tech techLuma"
11. "save [String desired directory path for new image]
    [String name of image created]"
    - saves the desired image to the computer
    - Ex: "save res/techsupportLuma.ppm techLuma"
12. "q" - quits and ends the program

<br>After an attempted command input is given, a success message or an error message will appear if
the
command was executed properly or not. More image-editing commands may be executed over the duration
of the program.

If the user wants to quit the program, the user should input "q" in response to the question
"What would you like to do?"

<br>Important Notes:

- "q" should be treated like its own command and should not be attempted as an
  argument of a different command. Ex: Do not: "vertical flip q" - this will not work
- Spaces between each word/[] is placed with purpose
- To darken image, one should use the brighten command but with negative integer values

<br>________________________________________________________________________________________________

#### CITATION:

techsupport.png and all derivatives are from an image taken by a friend, Matthew Wang,
who has given us, Ethan Kong and Ryan Cheung, permission to use in our project.
