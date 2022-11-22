import view.IPHistogram;

public class MockIPHistogramImpl implements IPHistogram {
  private final StringBuilder log;

  public MockIPHistogramImpl() {
    this.log = new StringBuilder();
  }
  @Override
  public void createHistogramData(String imgName) throws IllegalArgumentException {
    this.log.append("createHistogramData with this imgName: " + imgName);
  }

  /**
   * This method retrieves the private log of the mock histogram impl.
   * @return A String representing a log of all methods called.
   */
  public String getLog() {
    return this.log.toString();
  }
}
