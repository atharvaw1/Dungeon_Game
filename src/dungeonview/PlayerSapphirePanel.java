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
 * The sapphire panethat displays players sapphire count.
 */
public class PlayerSapphirePanel extends JPanel {

  private ReadOnlyDungeon model;

  /**
   * Constructor takes in model.
   * @param model dungeon model
   */
  public PlayerSapphirePanel(ReadOnlyDungeon model) {
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
      img = ImageIO.read(new File("images/emerald.png"));
    } catch (IOException e) {
      System.out.println("No image found stench");
    }
    g.drawImage(img, 20, 20, null);
    g.setColor(Color.CYAN);
    g.setFont(new Font("Arial Black", Font.PLAIN, 10));
    g.drawString(String.valueOf(((List) model.describePlayer().get(1)).stream()
            .filter(x -> x == Treasure.SAPPHIRE).count()), 30, 90);

    if (!model.playerIsAlive()) {
      setBackground(Color.RED);
    } else {
      setBackground(Color.BLACK);
    }

  }


}
