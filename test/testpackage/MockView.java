package testpackage;

import dungeoncontroller.GuiController;
import dungeonmodel.ReadOnlyDungeon;
import dungeonview.View;

import java.io.IOException;


/**
 * Mock view to test the controller in isolation.
 */
public class MockView implements View {

  private final Appendable log;

  /**
   * Constructor takes in log.
   * @param log appendable log to append output
   */
  public MockView(Appendable log) {
    this.log = log;
  }

  @Override
  public void addListener(GuiController listener) {
    try {
      log.append("adding listener.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }
  }

  @Override
  public void refresh() {
    try {
      log.append("refresh called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }

  }

  @Override
  public void makeVisible() {
    try {
      log.append("makeVisible called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }

  }

  @Override
  public void resetView() {
    try {
      log.append("resetView called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }


  }

  @Override
  public void updateModel(ReadOnlyDungeon model) {
    try {
      log.append("updateModel called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }

  }

  @Override
  public void displayWin() {
    try {
      log.append("displayWin called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }

  }

  @Override
  public void displayLoss() {
    try {
      log.append("displayLoss called.\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed");
    }

  }
}
