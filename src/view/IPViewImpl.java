package view;


/**
 * Class represents the implementation of the interface that creates the text view.
 */

public class IPViewImpl extends AIPView {
  
  /**
   * This first constructor takes nothing and uses System.out as the default appendable.
   */
  public IPViewImpl() {
    super(System.out);
  }
  
  /**
   * This second constructor takes an Appendable and initializes it.
   * @param output An Appendable that things should be appended to.
   * @throws IllegalArgumentException if the given appendable is null
   */
  public IPViewImpl(Appendable output) throws IllegalArgumentException {
    super(output);
  }
}
