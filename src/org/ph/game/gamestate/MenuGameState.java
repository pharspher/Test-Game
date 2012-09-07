package org.ph.game.gamestate;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.BitmapFont;
import org.andengine.util.HorizontalAlign;
import org.ph.game.testgame.GameContext;

import android.util.Log;

public class MenuGameState extends BaseGameState {

	private static final String TAG = "MenuGameState";
	private static final String BITMAP_FONT_PATH = "font/BitmapFont.fnt";

	private Scene mScene;
	private BitmapFont mBitmapFont;

	private GameContext mGameContext;

	private static String[] mMenuItemList = new String[] {
		"New Game", "Resume Game", "Options", "Quit"
	};

	public MenuGameState(GameContext context) {
		super(context);
		Log.d(TAG, "MenuGameState()");
	}

	@Override
	public void onCreateResources() {
		Log.d(TAG, "onCreateResource()");

		mGameContext = getGameContext();

		mBitmapFont = 
		new BitmapFont(mGameContext.getEngine().getTextureManager(),
				mGameContext.getAssets(), BITMAP_FONT_PATH);
		mBitmapFont.load();
	}

	@Override
	public Scene onCreateScene() {
		Log.d(TAG, "onCreateScene()");

		mScene = new Scene();
		mScene.setBackground(new Background(1.0f, 1.0f, 1.0f));

		final Engine engine = mGameContext.getEngine();
		final Camera camera = engine.getCamera();
		int itemInterval = 
				(int)(camera.getHeight() / (mMenuItemList.length + 1));

		for (int i = 0; i < mMenuItemList.length; i++) {
			Text itemText = new Text(0, 
					(i + 1) * itemInterval - mBitmapFont.getLineHeight() / 2, 
					mBitmapFont, mMenuItemList[i], 
					new TextOptions(HorizontalAlign.CENTER), 
					mGameContext.getEngine().getVertexBufferObjectManager()) {
				@Override
				public boolean onAreaTouched(final TouchEvent event, final float x, final float y) {
					switch (event.getAction()) {
					case TouchEvent.ACTION_DOWN:
						mGameContext.getGameActivity().finish();
						return true;
					default:
						return false;
					}
				}
			};

			mScene.registerTouchArea(itemText);
			itemText.setScale(2);
			itemText.setX(camera.getWidth() / 2 - (itemText.getWidth()) / 2);

			/*
			ButtonSprite btn = new ButtonSprite(0, 
					(i + 1) * itemInterval - mBitmapFont.getLineHeight() / 2,
					engine.
			*/

			mScene.attachChild(itemText);
		}

		return mScene;
	}
}
