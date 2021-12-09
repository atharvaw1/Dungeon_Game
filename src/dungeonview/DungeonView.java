package dungeonview;

import dungeoncontroller.GuiController;
import dungeonmodel.Direction;
import dungeonmodel.MonsterState;
import dungeonmodel.ReadOnlyDungeon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


/**
 * Main view class to represent the entire dungeon game.
 */
public class DungeonView extends JFrame implements View {

  private final DungeonPanel dungeonPanel;
  private GuiController controller;
  private ReadOnlyDungeon model;
  private final PlayerStatusPanel playerStatusPanel;

  /**
   * Constructor takes in model and creates the game interface.
   * @param model dungeon model
   */
  public DungeonView(ReadOnlyDungeon model) {


    super("Dungeon");

    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    this.model = model;
    pack();
    this.setSize(800, 800);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    setJMenuBar(createMenueBar());

    playerStatusPanel = new PlayerStatusPanel(model);

    add(playerStatusPanel, BorderLayout.PAGE_START);


    dungeonPanel = new DungeonPanel(model);
    dungeonPanel.setPreferredSize(new Dimension(model.getMaxCols() * 150,
            model.getMaxRows() * 150));

    JScrollPane scrollableDungeonPanel = new JScrollPane(dungeonPanel);

    add(scrollableDungeonPanel, BorderLayout.CENTER);


  }


  private JMenuBar createMenueBar() {

    JMenuItem menuItemNewGame = new JMenuItem("New Game");
    View frame = this;
    menuItemNewGame.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(java.awt.event.ActionEvent evt) {
        controller.newGame();
      }
    });

    JMenuItem menuItemReGame = new JMenuItem("Restart Game");

    menuItemReGame.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        controller.restartGame();
      }
    });

    JMenuItem menuItemExit = new JMenuItem("Quit");

    menuItemExit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
      }
    });



    JMenuItem showSettings = new JMenuItem("Current Game Settings");

    showSettings.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        JOptionPane.showMessageDialog(null, "Rows: " + model.getMaxRows()
                + " Cols: " + model.getMaxCols() + "\nInterconnectivity: "
                + model.getSettings().get(2) + "\nWrapping: "
                + model.getSettings().get(3) + "\nTreasure Percentage: "
                + model.getSettings().get(4) + "\nMonster Count: "
                + model.getSettings().get(5));
      }
    });


    JPanel settingsPanel = new JPanel();
    settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
    JTextField interconnectivity = new JTextField();
    settingsPanel.add(new JLabel("Interconnectivity:"));
    settingsPanel.add(interconnectivity);
    JTextField dungeonWidth = new JTextField();
    settingsPanel.add(new JLabel("Dungeon Width:"));
    settingsPanel.add(dungeonWidth);
    JTextField dungeonHeight = new JTextField();
    settingsPanel.add(new JLabel("Dungeon Height:"));
    settingsPanel.add(dungeonHeight);
    JCheckBox wrapping = new JCheckBox();
    settingsPanel.add(new JLabel("Wrapping"));
    settingsPanel.add(wrapping);
    JTextField treasurePercent = new JTextField();
    settingsPanel.add(new JLabel("Treasure Percent:"));
    settingsPanel.add(treasurePercent);
    JTextField monsterCount = new JTextField();
    settingsPanel.add(new JLabel("Monster Count:"));
    settingsPanel.add(monsterCount);
    JTextField playerId = new JTextField(5);
    settingsPanel.add(new JLabel("Player Name:"));
    settingsPanel.add(playerId);

    JMenuItem changeSettings = new JMenuItem("Change Game Settings");
    changeSettings.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        int result = JOptionPane.showConfirmDialog(null, settingsPanel,
                "Enter settings you want for new game", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
          try {
            if (controller.isValidIput(Integer.parseInt(interconnectivity.getText()),
                    wrapping.isSelected(),
                    Integer.parseInt(dungeonHeight.getText()),
                    Integer.parseInt(dungeonWidth.getText()),
                    Integer.parseInt(treasurePercent.getText()),
                    Integer.parseInt(monsterCount.getText()),
                    playerId.getText())) {


              controller.newGame(Integer.parseInt(interconnectivity.getText()),
                      wrapping.isSelected(),
                      Integer.parseInt(dungeonHeight.getText()),
                      Integer.parseInt(dungeonWidth.getText()),
                      Integer.parseInt(treasurePercent.getText()),
                      Integer.parseInt(monsterCount.getText()),
                      playerId.getText());
              JOptionPane.showMessageDialog(null, "Starting new game with given settings.");
            }
          } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "One or more invalid settings.");
          }

        }
      }
    });

    JMenuItem controls = new JMenuItem("Controls");
    controls.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        JOptionPane.showMessageDialog(null, "Move: <- , ^ , v , ->\n"
                + "Pickup Treasure: T\n"
                + "Pickup Weapons: P\n"
                + "Shoot: S + direction(arrow keys) + distance(1-5)");
      }
    });




    JMenu gameMenu = new JMenu("Game");

    gameMenu.add(menuItemNewGame);
    gameMenu.add(menuItemReGame);
    gameMenu.add(menuItemExit);

    JMenu settings = new JMenu("Settings");
    settings.add(showSettings);
    settings.add(changeSettings);
    settings.add(controls);

    JMenuBar menuBar = new JMenuBar();
    menuBar.add(gameMenu);
    menuBar.add(settings);


    return menuBar;
  }

  @Override
  public void addListener(GuiController listener) {

    if (listener == null) {
      throw new IllegalArgumentException("Controller cannot be null");
    }
    this.controller = listener;
    // create the MouseAdapter
    MouseAdapter clickAdapter = new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        super.mouseClicked(e);


        RoomPanel selectedPanel = (RoomPanel) SwingUtilities
                .getDeepestComponentAt(dungeonPanel, e.getX(), e.getY());


        listener.handleCellClick(selectedPanel.getID());
      }
    };
    dungeonPanel.addMouseListener(clickAdapter);


    this.addKeyListener(new KeyListener() {

      List<Integer> activekeys = new ArrayList<>();


      @Override
      public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 't') {
          //controller;
        }
      }


      @Override
      public void keyPressed(KeyEvent e) {
        if (!activekeys.contains(e.getKeyCode())) {
          activekeys.add(e.getKeyCode());
        }
        Map<Integer, Direction> directionMap = new HashMap<Integer, Direction>();
        directionMap.put(KeyEvent.VK_UP, Direction.NORTH);
        directionMap.put(KeyEvent.VK_DOWN, Direction.SOUTH);
        directionMap.put(KeyEvent.VK_LEFT, Direction.WEST);
        directionMap.put(KeyEvent.VK_RIGHT, Direction.EAST);


        Map<Integer, Integer> noKeys = new HashMap<Integer, Integer>();
        noKeys.put(KeyEvent.VK_1, 1);
        noKeys.put(KeyEvent.VK_2, 2);
        noKeys.put(KeyEvent.VK_3, 3);
        noKeys.put(KeyEvent.VK_4, 4);
        noKeys.put(KeyEvent.VK_5, 5);


        boolean condition1 = activekeys.stream()
                .anyMatch(directionMap::containsKey);
        boolean condition2 = activekeys.stream()
                .anyMatch(noKeys::containsKey);


        if (activekeys.contains(KeyEvent.VK_S) && condition1 && condition2) {
          if (directionMap.get(activekeys.get(1)) != null
                  && noKeys.get(activekeys.get(2)) != null) {
            if (((List) model.describePlayer().get(2)).size() > 0) {
              boolean success = controller.shoot(directionMap.get(activekeys.get(1)),
                      noKeys.get(activekeys.get(2)));


              if (success) {
                JOptionPane.showMessageDialog(null, "You hear a great howl in the distance!");
              } else {
                JOptionPane.showMessageDialog(null, "You shoot an arrow into the darkness!");
              }
            } else {
              JOptionPane.showMessageDialog(null, "You are out of arrows!");
            }


          }
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        if (activekeys.contains(e.getKeyCode())) {
          switch (e.getKeyCode()) {
            case (KeyEvent.VK_UP):
              controller.movePlayer(Direction.NORTH);
              break;
            case (KeyEvent.VK_DOWN):
              controller.movePlayer(Direction.SOUTH);
              break;
            case (KeyEvent.VK_RIGHT):
              controller.movePlayer(Direction.EAST);
              break;
            case (KeyEvent.VK_LEFT):
              controller.movePlayer(Direction.WEST);
              break;
            case (KeyEvent.VK_T):
              controller.pickupTreasure();
              break;
            case (KeyEvent.VK_P):
              controller.pickupWeapons();
              break;
            default:
              break;
          }
        }

        activekeys = new ArrayList<Integer>();

      }
    });

  }

  @Override
  public void refresh() {
    repaint();
    if (model.getMonsterInLocation() != null
            && model.getMonsterInLocation().getState() != MonsterState.DEAD
            && Objects.equals(model.getMonsterInLocation().toString(), "Thief")) {
      JOptionPane.showMessageDialog(null, "A thief just stole all your treasure!!");
    }
  }

  @Override
  public void makeVisible() {
    setVisible(true);
  }


  @Override
  public void resetView() {
    dungeonPanel.resetView();
    refresh();
  }

  @Override
  public void updateModel(ReadOnlyDungeon model) {
    this.model = model;
    dungeonPanel.updateModel(model);
    dungeonPanel.setPreferredSize(new Dimension(model.getMaxCols() * 150,
            model.getMaxRows() * 150));
    playerStatusPanel.updateModel(model);
    refresh();
  }

  @Override
  public void displayWin() {
    JOptionPane.showMessageDialog(null, "Congratulations!! You Win!!");
  }

  @Override
  public void displayLoss() {
    JOptionPane.showMessageDialog(null, "Chomp Chomp Chomp you are eaten by the Otyugh!");
  }

}
