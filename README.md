<br>________________________________________________________________________________________________

## imageProcessingProgram Overview:

This is Ryan Cheung and Ethan Kong's image processing program that allows users to operate on
images in similar ways to photoshop. After edits are done, the user is able to save their new image
onto their computer! This program follows a standard Model View Controller (MVC) design pattern
coupled with the command design pattern that allows for minimal coupling between classes.

<br>________________________________________________________________________________________________

#### Changes made since Assignment 4:

- Removed the previous abstract classes AIPModel and AIPView and removed the 'super'ing of
  constructors in its child classes. We originally anticipated abstraction for new models and views
  for different image types. However, we quickly realized that was not
  needed to load, save, and use methods on the new image types, and so we removed it.
- Added new methods filter() and colorTransformation() that allow for the user to blur, sharpen,
  place a sepia tone and greyscale luma filter onto an image
    - Both of these methods utilize specialized helpers that calculate the new values of each
      component within each PixelInfo object
- Added support for new commands (blur, sharpen, sepia and greyscale-luma) in the IPControllerImpl
- The controller now implements the command design pattern with command classes. Each command has
  its own class object
  and inherits the AIPCommand abstract class (which extends IPCommand interface). IPCommand contains
  an execute method, where a model has a command executed upon it. The AIPCommand class contains an
  abstracted constructor for all child classes.
    - To utilize the command design pattern, the IPControllerImpl uses lambda functions to execute
      the commands from a Map
- Added support to load and save new file types (.png, .jpg/.jpeg, .bmp)
- Added functionality in the main ImageProcessing class and method in order to take in command line arguments
  - A valid command line argument would be: "-file name-of-script.txt"
- Created a jar file that can run the program itself.
- The required jar file is named ImageProcessing.jar and can be found in the res folder.
- The required script file is named script.txt and can be found in the res folder.

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

    - ##### IPModelImpl class:
        - Implements the methods of the model interfaces (extends AIPModel)
        - Fields:
            - addedImages - allows a user to load and store images
                - This field is private, as we don't want the controller or view
                  to modify our collection of images. Manipulation of the model object should only
                  happen
                  within the implementation of this class
        - Currently, colorTransformation() assumes that the kernels being used in the transformation
          is of 3x3 size as RGB calculations only require 3 calculations for each of the RGB values.
        - Private helper: imageExists - is a helper that is utilized by all Model interface methods
          to check if the desired image to modified is loaded into the program or not.
            - This method is private as it is only meant to supplement the methods within the class
              and used to reduce code duplication. Furthermore, the methods aren't directly publicly
              used
            - For example: greyscale() takes an image name and utilizes the imageExists method to
              check if the desired image is ready to be modified or not. If the image isn't loaded
              into
              the program, the program should not attempt to modify the image and throw an IAE. The
              program will not end due to the IAE as it will be caught later in the controller.
        - Private helper: numberInBounds() - a helper that ensures a newly calculated decimal value
          for a component of a Pixel is converted into an integer within the bounds 0 and 255

- ### View:
    - ##### IPView interface:
        - Represents the methods that allow for outputs to be rendered
    - ##### IPViewImpl class:
        - All views will have an Appendable as a field (only some will need a model -- our current
          view does not)
        - Currently only implements the method that renders messages to the output
        - Fields:
            - model: represents the model, however it is not being used and is placed here in
              anticipation that it will be used in the future
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

#### CITATION:

techsupport.png and all derivatives are from an image taken by a friend, Matthew Wang,
who has given us, Ethan Kong and Ryan Cheung, permission to use in our project.
