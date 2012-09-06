package org.ph.game.testgame;

import org.andengine.engine.Engine;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.res.AssetManager;

public class GameContext {

	private SimpleBaseGameActivity mActivity;
	private Engine mGameEngine;

	public GameContext(SimpleBaseGameActivity activity) {
		mActivity = activity;
		mGameEngine = activity.getEngine();
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
}
