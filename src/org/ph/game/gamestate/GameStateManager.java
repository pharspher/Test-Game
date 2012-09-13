package org.ph.game.gamestate;

import java.util.ArrayList;

import org.andengine.entity.scene.Scene;
import org.ph.game.testgame.GameContext;

import android.util.Log;

public class GameStateManager {
	private static final String TAG = "GameStateManager";

	private ArrayList<BaseGameState> mStates;
	private Scene mCurrentScene;
	private GameContext mGameContext;

	/**TODO
	 * Will crash if user doesn't add any state.
	 * Possible solutions
	 * 1. add a default game state during initialization
	 * 2. change the signature to force user passing initial game state 
	 */
	public GameStateManager(GameContext context) {
		mStates = new ArrayList<BaseGameState>();
		mGameContext = context;
		/*
		mStates.add(new MenuGameState(context));

		mCurrentScene = mStates.get(0).getScene();
		*/
	}

	public BaseGameState getCurrentState() {
		BaseGameState currentState = mStates.get(mStates.size() - 1); 
		Log.d(TAG, "current state: " + currentState);
		return currentState;
	}

	public Scene getCurrentScene() {
		return mCurrentScene;
	}

	/**TODO
	 * When changing to scene that have already been created before, the game
	 * state will have multiple instances in the mStates.
	 */
	public void addGameState(BaseGameState state) {
		mStates.add(state);
		mCurrentScene = mStates.get(mStates.size() - 1).getScene();
		mGameContext.getEngine().setScene(mCurrentScene);
	}

	public void removeCurrentState() {
		// TODO Auto-generated method stub
		mStates.remove(mStates.size() - 1);
		mCurrentScene = mStates.get(mStates.size() - 1).getScene();
		mGameContext.getEngine().setScene(mCurrentScene);
	}
}
