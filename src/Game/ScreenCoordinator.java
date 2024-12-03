package Game;

import Engine.DefaultScreen;
import Engine.GraphicsHandler;
import Engine.Screen;
import Screens.ArachnerdsProject;
import Screens.CreditsScreen;
import Screens.EndCutscene;
import Screens.IntroCutscene;
import Screens.MenuScreen;
import Screens.PlayBasementLevelScreen;
import Screens.PlayBedroomLevelScreen;
import Screens.PlayFinalLevelScreen;
import Screens.PlayKitchenLevelScreen;
import Screens.PlayLivingRoomLevelScreen;
import Screens.PlayOutsideLevelScreen;



/*
 * Based on the current game state, this class determines which Screen should be shown
 * There can only be one "currentScreen", although screens can have "nested" screens
 */
public class ScreenCoordinator extends Screen {
	// currently shown Screen
	protected Screen currentScreen = new DefaultScreen();
	private boolean arachnophobiaEnabled = false;

	// keep track of gameState so ScreenCoordinator knows which Screen to show
	protected GameState gameState;
	protected GameState previousGameState;

	public GameState getGameState() {
		return gameState;
	}

	// Other Screens can set the gameState of this class to force it to change the currentScreen
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public void initialize() {
		// start game off with Menu Screen
		gameState = GameState.ARACHNERDS;
	}

	@Override
	public void update() {
		do {
			// if previousGameState does not equal gameState, it means there was a change in gameState
			// this triggers ScreenCoordinator to bring up a new Screen based on what the gameState is
			if (previousGameState != gameState) {
				switch(gameState) {
					case MENU:
						currentScreen = new MenuScreen(this);
						break;
					case BASEMENT_LEVEL:
						currentScreen = new PlayBasementLevelScreen(this);
						break;
					case OUTSIDE_LEVEL:
						currentScreen = new PlayOutsideLevelScreen(this);
						break;
					case BEDROOM_LEVEL:
						currentScreen = new PlayBedroomLevelScreen(this);
						break;
					case LIVING_ROOM_LEVEL:
						currentScreen = new PlayLivingRoomLevelScreen(this);
						break;
					case CREDITS:
						currentScreen = new CreditsScreen(this);
						break;
					case INTRO_CUTSCENE:
						currentScreen = new IntroCutscene(this);
						break;
					case ARACHNERDS:
						currentScreen = new ArachnerdsProject(this);
						break;
					case FINAL:
						currentScreen = new PlayFinalLevelScreen(this);
						break;
					case KITCHEN_LEVEL:
						currentScreen = new PlayKitchenLevelScreen(this);
						break;
					case END_CUTSCENE:
						currentScreen = new EndCutscene(this);
						break;
					default:
						break;
				}
				currentScreen.initialize();
			}
			previousGameState = gameState;

			// call the update method for the currentScreen
			currentScreen.update();
		} while (previousGameState != gameState);
	}

	public Screen getCurrentScreen() {
		return this.currentScreen;
	}

	public void setArachnophobiaEnabled(boolean bool){
		arachnophobiaEnabled = bool;
	}

	public boolean getArachnophobiaEnabled(){
		return arachnophobiaEnabled;
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		// call the draw method for the currentScreen
		currentScreen.draw(graphicsHandler);
	}
}
