package org.ph.game.gamestate;

import java.util.ArrayList;

import org.andengine.entity.scene.Scene;
import org.ph.game.testgame.GameContext;

import android.util.Log;

public class GameStateManager {
	private static final String TAG = "GameStateManager";

	private ArrayList<BaseGameState> mStates;
	private Scene mCurrentScene;

	public GameStateManager(GameContext context) {
		mStates = new ArrayList<BaseGameState>();
		mStates.add(new MenuGameState(context));

		mCurrentScene = mStates.get(0).getScene();
	}

	public BaseGameState getCurrentState() {
		BaseGameState currentState = mStates.get(mStates.size() - 1); 
		Log.d(TAG, "current state: " + currentState);
		return currentState;
	}

	public Scene getCurrentScene() {
		return mCurrentScene;
	}
}
