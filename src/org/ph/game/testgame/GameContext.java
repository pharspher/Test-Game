package org.ph.game.testgame;

import org.andengine.engine.Engine;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.ph.game.gamestate.GameStateManager;

import android.content.res.AssetManager;

public class GameContext {

	private SimpleBaseGameActivity mActivity;
	private Engine mGameEngine;
	private GameStateManager mGameStateManager;

	public GameContext(SimpleBaseGameActivity activity) {
		mActivity = activity;
		mGameEngine = activity.getEngine();
		mGameStateManager = new GameStateManager(this);
	}

	public Engine getEngine() {
		return mGameEngine;
	}

	public AssetManager getAssets() {
		return mActivity.getAssets();
	}

	public SimpleBaseGameActivity getGameActivity() {
		return mActivity;
	}

	public GameStateManager getGameStateManager() {
		return mGameStateManager;
	}
}
