package dungeonview;

import dungeonmodel.ReadOnlyDungeon;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Panel that displays player name.
 */
public class PlayerNamePanel extends JPanel {

  private ReadOnlyDungeon model;

  /**
   * Consturctor takes in the model.
   * @param model dungeon model
   */
  public PlayerNamePanel(ReadOnlyDungeon model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
    setBackground(Color.BLACK);
  }

  @Override
  protected void paintComponent(Graphics g) {

    super.paintComponent(g);
    BufferedImage img = null;
    try {
      img = ImageIO.read(new File("images/player.png"));
    } catch (IOException e) {
      System.out.println("No image found stench");
    }
    g.drawImage(img, 30, 20, null);

    g.setFont(new Font("Arial Black", Font.PLAIN, 10));
    g.setColor(Color.CYAN);
    if (!model.playerIsAlive()) {
      setBackground(Color.RED);
      g.drawString("You Died!", 20, 90);
    } else {
      setBackground(Color.BLACK);
      g.drawString((String) model.describePlayer().get(0), 20, 90);
    }


  }


}
