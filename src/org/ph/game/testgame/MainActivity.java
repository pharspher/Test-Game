package org.ph.game.testgame;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.ph.game.gamestate.GameStateManager;
import org.ph.game.gamestate.MenuGameState;

import android.util.Log;
import android.view.KeyEvent;

public class MainActivity extends SimpleBaseGameActivity {
	private static final String TAG = "MainActivity";

	private static final int CAMERA_WIDTH = 1280;
	private static final int CAMERA_HEIGHT = 720;

	private GameContext mContext;
	private GameStateManager mGameStateManager;

	@Override
	public EngineOptions onCreateEngineOptions() {
		return new EngineOptions(true, 
				ScreenOrientation.LANDSCAPE_FIXED, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), 
				new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT));
	}

	@Override
	protected void onCreateResources() {
		mContext = new GameContext(this);
		mGameStateManager = mContext.getGameStateManager();
	}

	@Override
	protected Scene onCreateScene() {
		this.getEngine().registerUpdateHandler(new FPSLogger());
		return mGameStateManager.createInitScene(new MenuGameState(mContext));
	}

	@Override
	public void onResumeGame() {
		super.onResumeGame();
	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();
		Log.d(TAG, "onPauseGame()");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return mGameStateManager.dispatchKeyEvent(keyCode, event);
	}
}
