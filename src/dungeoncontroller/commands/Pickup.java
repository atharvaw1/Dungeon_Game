package dungeoncontroller.commands;

import dungeonmodel.Dungeon;

import java.io.IOException;
import java.util.Scanner;




/**
 * Implements pickup command for player. Player can pick up either treasure or weapons
 * based on the location.
 */
public class Pickup implements DungeonCommand {


  @Override
  public void execute(Dungeon model, Scanner scan, Appendable out) {

    try {
      out.append("What would you like to pickup treasure or weapons (T/W)?\n");
      String input = scan.next();
      switch (input) {
        case "T":
          model.pickupTreasure();
          out.append("Picked up all the treasure.\n");
          break;
        case "W":
          model.pickupWeapons();
          out.append("Picked up all the weapons.\n");
          break;
        default:
          out.append("Invalid command.\n");
          break;
      }

    } catch (IOException e) {
      throw new IllegalStateException("Append failed.");
    }

  }
}
