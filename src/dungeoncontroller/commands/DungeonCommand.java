package dungeoncontroller.commands;

import dungeonmodel.Dungeon;

import java.util.Scanner;



/**
 * Command interface for dungeon to implement command design pattern. Used to execute
 * different commands as given by the user.
 */
public interface DungeonCommand {

  /**
   * Executes the command based on the type of command.
   *
   * @param model  model to execute command on
   * @param scan   scanner to take input from user
   * @param output appendable to return output from user
   */
  void execute(Dungeon model, Scanner scan, Appendable output);
}
