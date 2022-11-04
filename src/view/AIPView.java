package view;

import java.io.IOException;

/**
 * This class represents an abstract class of a view.
 */
public abstract class AIPView implements IPView {
  private final Appendable output;
  
  /**
   * Represents the constructor that takes nothing and uses System.out
   * as the default appendable.
   */
  public AIPView() {
    this(System.out);
  }
  
  /**
   * Represents the constructor that takes an Appendable and initializes it.
   * @param output An Appendable that things should be appended to.
   * @throws IllegalArgumentException if the given appendable is null.
   */
  public AIPView(Appendable output) throws IllegalArgumentException {
    if (output == null) {
      throw new IllegalArgumentException("null output");
    }
    this.output = output;
  }
  
  /**
   * Renders the message to the output view.
   *
   * @param msg The desired message to be displayed
   * @throws IOException when message isn't able to be rendered
   */
  @Override
  public void renderMessage(String msg) throws IOException {
    this.output.append(msg);
  }
}
