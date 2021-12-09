package dungeonview;

import dungeonmodel.ReadOnlyDungeon;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * Class representing the panel that shows the dungeon view in the GUI interface.
 */
public class DungeonPanel extends JPanel {

  private ReadOnlyDungeon model;
  private RoomPanel[][] cave;

  /**
   * Constructor takes in model and creates grids of RoomPanels of the dimensions given in model.
   * @param model the dungeon model
   */
  public DungeonPanel(ReadOnlyDungeon model) {


    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");

    }
    this.model = model;
    setLayout(new GridLayout(model.getMaxRows(), model.getMaxCols(), -1, -1));
    cave = new RoomPanel[model.getMaxRows()][model.getMaxCols()];
    for (int row = 0; row < model.getMaxRows(); row++) {
      for (int col = 0; col < model.getMaxCols(); col++) {
        cave[row][col] = new RoomPanel(row, col, model.getMaxCols(), model);
        add(cave[row][col]);
      }
    }


  }

  /**
   * Resets the view of each room for a new game.
   */
  public void resetView() {
    for (int row = 0; row < model.getMaxRows(); row++) {
      for (int col = 0; col < model.getMaxCols(); col++) {
        cave[row][col].resetView();
      }
    }
  }

  /**
   * Updates the model to use for new/restart game.
   * @param model the new model to add
   */
  public void updateModel(ReadOnlyDungeon model) {

    this.removeAll();
    this.model = model;
    setLayout(new GridLayout(model.getMaxRows(), model.getMaxCols(), -1, -1));
    cave = new RoomPanel[model.getMaxRows()][model.getMaxCols()];
    for (int row = 0; row < model.getMaxRows(); row++) {
      for (int col = 0; col < model.getMaxCols(); col++) {
        cave[row][col] = new RoomPanel(row, col, model.getMaxCols(), model);
        add(cave[row][col]);
      }
    }
    this.revalidate();

  }


}
