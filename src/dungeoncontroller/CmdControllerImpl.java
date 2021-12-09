package dungeoncontroller;

import dungeoncontroller.commands.DungeonCommand;
import dungeoncontroller.commands.Move;
import dungeoncontroller.commands.Pickup;
import dungeoncontroller.commands.Shoot;
import dungeonmodel.Direction;
import dungeonmodel.Dungeon;
import dungeonmodel.Smell;
import dungeonmodel.Treasure;
import dungeonmodel.Weapon;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;


/**
 * Implements a text based controller for the dungeon that takes in a readable and appendable.
 */
public class CmdControllerImpl implements TextController {

  private final Appendable out;
  private final Scanner scan;


  /**
   * Controller constructor that takes in a readable and appendable to read and write input output.
   *
   * @param in  readable input
   * @param out appendable output
   */
  public CmdControllerImpl(Readable in, Appendable out) {


    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable cannot be null.");
    }

    this.scan = new Scanner(in);
    this.out = out;

  }


  @Override
  public void playGame(Dungeon model) {

    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    }


    try {

      List<Direction> directions = model.describeConnectionsAtLocation();
      List<Treasure> treasure = model.describeTreasureAtLocation();
      List<Weapon> weapons = model.describeWeaponsAtLocation();
      Smell smell = model.describeSmellAtLocation();
      List<Treasure> playerTreasure = (List<Treasure>) model.describePlayer().get(1);
      List<Weapon> playerWeapons = (List<Weapon>) model.describePlayer().get(2);
      boolean inCave = model.playerInCave();
      out.append("You are ").append(model.describePlayer().get(0).toString()).append("\n");
      out.append("You have some treasure ")
              .append(String.valueOf(playerTreasure.stream().filter(Treasure.DIAMOND::equals)
                      .count()))
              .append(" diamonds , ")
              .append(String.valueOf(playerTreasure.stream().filter(Treasure.RUBY::equals)
                      .count()))
              .append(" rubies , ")
              .append(String.valueOf(playerTreasure.stream().filter(Treasure.SAPPHIRE::equals)
                      .count()))
              .append(" sapphires \n");
      out.append("You have some weapons ")
              .append(String.valueOf(playerWeapons.stream().filter(Weapon.CROOKEDARROW::equals)
                      .count()))
              .append(" arrows \n");

      while (model.playerIsAlive() && !(model.getPlayerLocation()[0] == model.getEnd()[0]
              && model.getPlayerLocation()[1] == model.getEnd()[1])) {


        directions = model.describeConnectionsAtLocation();
        directions.sort(Comparator.comparing(Direction::toString));
        treasure = model.describeTreasureAtLocation();
        weapons = model.describeWeaponsAtLocation();
        smell = model.describeSmellAtLocation();
        playerTreasure = (List<Treasure>) model.describePlayer().get(1);
        playerWeapons = (List<Weapon>) model.describePlayer().get(2);
        inCave = model.playerInCave();

        out.append("You are in a ").append(inCave ? "cave\n" : "tunnel\n");

        if (smell == Smell.WEAK) {
          out.append("You smell something bad(somewhat pungent) nearby.\n");
        } else if (smell == Smell.STRONG) {
          out.append("You smell something terrible(strongly pungent) nearby.\n");
        }

        out.append("Doors lead to the ")
                .append(directions.toString()
                        .substring(1, directions.toString().length() - 1)).append("\n");

        if (inCave && treasure.size() > 0) {
          out.append("You find ")
                  .append(String.valueOf(treasure.stream().filter(Treasure.DIAMOND::equals)
                          .count()))
                  .append(" diamonds , ")
                  .append(String.valueOf(treasure.stream().filter(Treasure.RUBY::equals).count()))
                  .append(" rubies , ")
                  .append(String.valueOf(treasure.stream().filter(Treasure.SAPPHIRE::equals)
                          .count()))
                  .append(" sapphires \n");
        }
        if (weapons.size() > 0) {
          out.append("You find ").append(String.valueOf(weapons.size())).append(" arrows in the ")
                  .append(inCave ? "cave\n" : "tunnel\n");
        }

        out.append("Move, Pickup , or Shoot (M-P-S)?\n");
        String input = "";
        DungeonCommand cmd = null;
        boolean done = false;

        while (!done && scan.hasNext()) {
          input = scan.next();

          if (input.equals("q")) {
            out.append("Quitting game.");
            return;
          }

          switch (input) {
            case "M":
              cmd = new Move();
              break;
            case "P":
              cmd = new Pickup();
              break;
            case "S":
              cmd = new Shoot();
              break;
            default:
              out.append("Invalid command.\n");
              cmd = null;
              break;
          }
          done = true;
        }

        if (cmd != null) {
          cmd.execute(model, scan, out);
          cmd = null;
        }
        out.append("\n");

      }

      if (!model.playerIsAlive()) {
        out.append("Chomp, chomp, chomp, you are eaten by an Otyugh!\n"
                + "Better luck next time");
      } else if (model.getPlayerLocation()[0] == model.getEnd()[0]
              && model.getPlayerLocation()[1] == model.getEnd()[1]) {
        out.append("Congratulations you have reached the end of the dungeon. \n"
                + "You win!!");
      } else {
        out.append("No more input available.");
      }

    } catch (IOException e) {
      throw new IllegalStateException("Append failed", e);
    }


  }

}
