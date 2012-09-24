package org.ph.game.gamestate;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.ph.game.testgame.GameContext;

import android.util.Log;
import android.view.KeyEvent;

public abstract class BaseGameState {

	private static final String TAG = "BaseGameState";

	private Scene mScene;
	private Engine mEngine;

	protected GameContext mContext;

	public abstract void onCreateResources();
	public abstract Scene onCreateScene();
	public abstract void onResume();
	public abstract void onPause();
	public abstract void onDestroy();

	private boolean mIsInit = false;

	public BaseGameState(GameContext context) {
		Log.d(TAG, this + ".BaseGameState()");
		mContext = context;
		mEngine = mContext.getEngine();
	}

	private void init() {
		Log.d(TAG, this + ".init()");
		onCreateResources();
		mIsInit = true;
	}

	//private void cleanup() {
	//}

	public void pause() {
		Log.d(TAG, this + ".pause()");
		onPause();
	}

	public void destroy() {
		Log.d(TAG, this + ".destroy()");
		onDestroy();
	}

	public void resume() {
		if (!mIsInit) {
			init();
		}
		Log.d(TAG, this + ".resume()");
		onResume();
	}

	//private void onResume() {
	//}

	//private void onPause() {
	//}

	public Scene getScene() {
		Scene scene = mScene;
		if (scene == null) {
			scene = createScene();
		}
		Log.d(TAG, this + ".getScene()");
		return scene;
	}

	protected Engine getEngine() {
		return mEngine;
	}

	protected GameContext getGameContext() {
		return mContext;
	}

	public Scene createScene() {
		Log.d(TAG, this + ".createScene()");
		mScene = onCreateScene();
		return mScene;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return false;
	}
}
