package view;

import java.util.Map;

public interface IPHistogram {

  void addData(Map<Integer, Integer> redData, Map<Integer, Integer> greenData,
                      Map<Integer, Integer> blueData, Map<Integer, Integer> intensityData);
}
