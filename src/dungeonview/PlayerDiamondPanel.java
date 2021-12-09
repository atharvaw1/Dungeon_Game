package dungeonview;

import dungeonmodel.ReadOnlyDungeon;
import dungeonmodel.Treasure;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * Class that implements the diamond panel to show the players diamonds.
 */
public class PlayerDiamondPanel extends JPanel {

  private ReadOnlyDungeon model;

  /**
   * Consturctor takes in model.
   * @param model dungeon model
   */
  public PlayerDiamondPanel(ReadOnlyDungeon model) {
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
      img = ImageIO.read(new File("images/diamond.png"));
    } catch (IOException e) {
      System.out.println("No image found stench");
    }
    g.drawImage(img, 20, 30, null);
    g.setColor(Color.CYAN);
    g.setFont(new Font("Arial Black", Font.PLAIN, 10));
    g.drawString(String.valueOf(((List) model.describePlayer().get(1)).stream()
            .filter(x -> x == Treasure.DIAMOND).count()), 30, 90);

    if (!model.playerIsAlive()) {
      setBackground(Color.RED);
    } else {
      setBackground(Color.BLACK);
    }
  }


}
