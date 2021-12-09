package dungeonview;

import dungeonmodel.ReadOnlyDungeon;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 * Displays the status of the player throughout the game.
 */
public class PlayerStatusPanel extends JPanel {

  private ReadOnlyDungeon model;

  /**
   * Consturctor takes model as input.
   * @param model dungeon model
   */
  public PlayerStatusPanel(ReadOnlyDungeon model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;

    setLayout(new GridLayout(1, 5));
    setPreferredSize(new Dimension(100, 100));

    add(new PlayerNamePanel(model));
    add(new PlayerWeaponsPanel(model));
    add(new PlayerDiamondPanel(model));
    add(new PlayerRubyPanel(model));
    add(new PlayerSapphirePanel(model));


  }

  void updateModel(ReadOnlyDungeon model) {
    this.model = model;
    this.removeAll();
    setLayout(new GridLayout(1, 5));
    setPreferredSize(new Dimension(100, 100));
    add(new PlayerNamePanel(model));
    add(new PlayerWeaponsPanel(model));
    add(new PlayerDiamondPanel(model));
    add(new PlayerRubyPanel(model));
    add(new PlayerSapphirePanel(model));
    this.revalidate();
  }


}
