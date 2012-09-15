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
	public void pushState(BaseGameState state) {
		mStates.add(state);
	}

	public void popState() {
		getCurrentState().pause();
		mStates.remove(mStates.size() - 1);
		getCurrentState().resume();
		mCurrentScene = getCurrentState().getScene();
		mGameContext.getEngine().setScene(mCurrentScene);
	}

	public Scene createCurrentScene() {
		return mStates.get(mStates.size() - 1).createScene();
	}

	public void startNewState() {
		mCurrentScene = mStates.get(mStates.size() - 1).createScene();
		mGameContext.getEngine().setScene(mCurrentScene);
	}

	public void changeState(BaseGameState newState) {
		getCurrentState().pause();
		newState.resume();
		mCurrentScene = newState.getScene();
		mGameContext.getEngine().setScene(mCurrentScene);
		pushState(newState);
	}

	public Scene createInitScene(BaseGameState state) {
		state.resume();
		mCurrentScene = state.getScene();
		pushState(state);
		return mCurrentScene;
	}
}
