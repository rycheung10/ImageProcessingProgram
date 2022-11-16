import model.IPModelImpl;
import view.IPViewGUIImpl;

public class ImageProcessingWGUI {
  public static void main (String[] args) {
    IPModelImpl m1 = new IPModelImpl();
    IPViewGUIImpl v1 = new IPViewGUIImpl(m1);
  }
}
