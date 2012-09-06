package org.ph.game.gamestate;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.ph.game.testgame.GameContext;

import android.util.Log;

public abstract class BaseGameState {

	private static final String TAG = "BaseGameState";

	private Scene mScene;
	private Engine mEngine;

	protected GameContext mContext;

	public BaseGameState(GameContext context) {
		Log.d(TAG, "BaseGameState()");

		mContext = context;
		mEngine = mContext.getEngine();

		onCreateResources();

		mScene = onCreateScene();
	}

	public Scene getScene() {
		return mScene;
	}

	protected Engine getEngine() {
		return mEngine;
	}

	protected GameContext getGameContext() {
		return mContext;
	}

	public abstract void onCreateResources();
	public abstract Scene onCreateScene();
}
