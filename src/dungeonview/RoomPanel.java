package dungeonview;

import dungeonmodel.Direction;
import dungeonmodel.MonsterState;
import dungeonmodel.ReadOnlyDungeon;
import dungeonmodel.Smell;
import dungeonmodel.Treasure;
import dungeonmodel.Weapon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.JPanel;


/**
 * RoomPanel to display each individual room of the dungeon.
 */
public class RoomPanel extends JPanel {

  private final int id;
  BufferedImage myPicture;
  BufferedImage player;
  private ReadOnlyDungeon model;

  /**
   * Constructor takes in model rows, cols and maxcols.
   * @param row row no fo room
   * @param col col no of room
   * @param maxCol max cols in dungeon
   * @param model model
   */
  public RoomPanel(int row, int col, int maxCol, ReadOnlyDungeon model) {
    setLayout(new BorderLayout());
    setSize(new Dimension(150, 150));
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");

    }

    try {
      myPicture = ImageIO.read(new File("images/black.png"));
    } catch (IOException e) {
      throw new IllegalArgumentException("No image exists");
    }
    try {
      player = ImageIO.read(new File("images/player.png"));
    } catch (IOException e) {
      throw new IllegalArgumentException("No image exists");
    }

    this.model = model;
    this.id = row * maxCol + col;


  }


  public int getID() {
    return id;
  }

  private BufferedImage overlay(BufferedImage starting, BufferedImage overlay,
                                int offsetX, int offsetY, int scale) {


    int w = Math.max(starting.getWidth(), overlay.getWidth());
    int h = Math.max(starting.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = combined.getGraphics();
    g.drawImage(starting, 0, 0, null);


    Image scaledImage = overlay.getScaledInstance(combined.getWidth() / scale,
            combined.getHeight() / scale,
            Image.SCALE_SMOOTH);
    g.drawImage(scaledImage, offsetX, offsetY, null);
    return combined;
  }


  private void updateMyPicture() {

    List<Direction> connections = model.describeConnectionsAtLocation();
    String path = "images/newdungeon2/";
    if (connections.contains(Direction.EAST)) {
      path += "E";
    }
    if (connections.contains(Direction.NORTH)) {
      path += "N";
    }
    if (connections.contains(Direction.SOUTH)) {
      path += "S";
    }
    if (connections.contains(Direction.WEST)) {
      path += "W";
    }
    path += ".png";

    //Get room image
    try {
      BufferedImage room = ImageIO.read(new File(path));
      myPicture = overlay(myPicture, room, 0, 0, 1);
    } catch (IOException e) {
      System.out.println("No image found " + path);
    }


    //Overlay treasure
    List<Treasure> treasures = model.describeTreasureAtLocation();
    if (treasures.contains(Treasure.DIAMOND)) {
      try {
        BufferedImage treasure = ImageIO.read(new File("images/diamond.png"));
        myPicture = overlay(myPicture, treasure, 110, 50, 5);
      } catch (IOException e) {
        System.out.println("No image found diamond");
      }
    }
    if (treasures.contains(Treasure.RUBY)) {
      try {
        BufferedImage treasure = ImageIO.read(new File("images/ruby.png"));
        myPicture = overlay(myPicture, treasure, 120, 50, 5);
      } catch (IOException e) {
        System.out.println("No image found ruby");
      }
    }
    if (treasures.contains(Treasure.SAPPHIRE)) {
      try {
        BufferedImage treasure = ImageIO.read(new File("images/emerald.png"));
        myPicture = overlay(myPicture, treasure, 130, 50, 5);
      } catch (IOException e) {
        System.out.println("No image found sapphire");
      }
    }

    //Overlay weapons
    if (model.describeWeaponsAtLocation().contains(Weapon.CROOKEDARROW)) {
      try {
        BufferedImage weapon = ImageIO.read(new File("images/arrow-white.png"));
        myPicture = overlay(myPicture, weapon, 100, 110, 5);
      } catch (IOException e) {
        System.out.println("No image found arrow");
      }
    }


    //Overlay Otyugh
    if (model.getMonsterInLocation() != null
            && model.getMonsterInLocation().getState() != MonsterState.DEAD
            && Objects.equals(model.getMonsterInLocation().toString(), "Otyugh")) {
      try {
        BufferedImage smell = ImageIO.read(new File("images/otyugh.png"));
        myPicture = overlay(myPicture, smell, 0, 0, 1);
      } catch (IOException e) {
        System.out.println("No image found Otyugh.");
      }
    }

    //Overlay Thief
    if (model.getMonsterInLocation() != null
            && model.getMonsterInLocation().getState() != MonsterState.DEAD
            && Objects.equals(model.getMonsterInLocation().toString(), "Thief")) {

      try {
        BufferedImage smell = ImageIO.read(new File("images/thief.png"));
        myPicture = overlay(myPicture, smell, 90, 30, 2);

      } catch (IOException e) {
        System.out.println("No image found Thief.");
      }
    }


  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int playerRoomno = model.getPlayerLocation()[0] * model.getMaxCols()
            + model.getPlayerLocation()[1];

    if (playerRoomno == id && model.getVisited().get(id)) {

      updateMyPicture();
      BufferedImage temp = myPicture;
      //Overlay stench
      if (model.describeSmellAtLocation() == Smell.STRONG) {
        try {
          BufferedImage smell = ImageIO.read(new File("images/stench02.png"));
          temp = overlay(myPicture, smell, 0, 0, 1);
        } catch (IOException e) {
          System.out.println("No image found stench");
        }
      } else if (model.describeSmellAtLocation() == Smell.WEAK) {
        try {
          BufferedImage smell = ImageIO.read(new File("images/stench01.png"));
          temp = overlay(myPicture, smell, 0, 0, 1);
        } catch (IOException e) {
          System.out.println("No image found stench");
        }
      }
      //Overlay player
      temp = overlay(temp, player, 20, 30, 2);
      Image scaledImage = temp.getScaledInstance(this.getWidth(), this.getHeight(),
              Image.SCALE_SMOOTH);
      g.drawImage(scaledImage, 0, 0, null);


    } else {
      Image scaledImage = myPicture.getScaledInstance(this.getWidth(), this.getHeight(),
              Image.SCALE_SMOOTH);
      g.drawImage(scaledImage, 0, 0, null);
    }
  }

  /**
   * Reset the view when new game is started.
   */
  public void resetView() {
    try {
      myPicture = ImageIO.read(new File("images/black.png"));
    } catch (IOException e) {
      throw new IllegalArgumentException("No image exists");
    }
  }

  /**
   * Update to new model in case of new/restart game.
   * @param model new model
   */
  public void updateModel(ReadOnlyDungeon model) {
    this.model = model;
  }
}
