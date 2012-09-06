package org.ph.game.gamestate;

import java.util.ArrayList;

import org.andengine.entity.scene.Scene;

import android.util.Log;

public class GameStateManager {
	private static final String TAG = "GameStateManager";

	private static GameStateManager mInstance = new GameStateManager();

	private ArrayList<BaseGameState> mStates;
	private Scene mCurrentScene;

	private GameStateManager() {
		mStates = new ArrayList<BaseGameState>();
		mStates.add(new MenuGameState());
		mCurrentScene = mStates.get(0).getScene();
	}

	public static GameStateManager getInstance() {
		return mInstance;
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
