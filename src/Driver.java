import dungeoncontroller.CmdControllerImpl;
import dungeoncontroller.GuiController;
import dungeoncontroller.GuiControllerImpl;
import dungeoncontroller.TextController;
import dungeonmodel.Dungeon;
import dungeonmodel.DungeonImpl;
import dungeonmodel.rng.IRandomNumberGenerator;
import dungeonmodel.rng.RandomNumberGenerator;

import java.io.InputStreamReader;

/**
 * The driver class of the project that runs all the code. The input, output and control happens
 * in this class.
 */
public class Driver {

  /**
   * Main method of the project where code execution starts from.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {

    if (args.length == 7) {

      try {
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int interconnectivity = Integer.parseInt(args[2]);
        boolean wrapping = args[3].equals("true");
        int treasurePercent = Integer.parseInt(args[4]);
        int monsterCount = Integer.parseInt(args[5]);
        String playerName = args[6];

        IRandomNumberGenerator rng = new RandomNumberGenerator();
        Dungeon wrappingDungeon = new DungeonImpl(interconnectivity, wrapping, x, y, rng,
                treasurePercent, monsterCount, playerName);

        Readable input = new InputStreamReader(System.in);
        Appendable output = System.out;

        TextController c = new CmdControllerImpl(input, output);

        c.playGame(wrappingDungeon);

      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Command line aruguments are not valid.");
      }


    } else {
      GuiController c = new GuiControllerImpl();
      c.playGame();
    }

  }
}
