package dungeonview;

import dungeonmodel.ReadOnlyDungeon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;


/**
 * The panel for the player status displayed throughout the game.
 */
public class PlayerControlsPanel extends JPanel {

  private ReadOnlyDungeon model;

  /**
   * Constructor takes in the readonly model to create player elements.
   * @param model dungeon model
   */
  public PlayerControlsPanel(ReadOnlyDungeon model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    setBackground(Color.BLACK);
  }

  @Override
  protected void paintComponent(Graphics g) {

    super.paintComponent(g);

    g.setFont(new Font("Arial Black", Font.PLAIN, 20));
    g.setColor(Color.CYAN);
    g.drawString("Controls:", 20, 20);

    g.setFont(new Font("Arial Black", Font.PLAIN, 10));
    g.drawString("Move: <- , ^ , v , ->", 0, 40);
    g.drawString("Pickup Treasure: T", 0, 60);
    g.drawString("Pickup Weapons: P", 0, 80);
    g.drawString("Shoot: S+direction+distance", 0, 100);

    if (!model.playerIsAlive()) {
      setBackground(Color.RED);
    } else {
      setBackground(Color.BLACK);
    }


  }


}
