package org.ph.game.widget;

import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;

import android.util.Log;

public abstract class AnimatedSpriteButton extends ButtonSprite {
	private static final String TAG = "AnimatedSpriteButton";

	private boolean mIsPressed = false;

	public AnimatedSpriteButton(float pX, float pY,
			ITiledTextureRegion pTiledTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
	}

	public void setText(String pText, IFont pFont) {
		Text text = new Text(0, 0, pFont, pText, 
				new TextOptions(HorizontalAlign.CENTER), 
				getVertexBufferObjectManager());
		text.setPosition((getWidth() - text.getWidth()) / 2, 
				(getHeight() - text.getHeight()) / 2);
		attachChild(text);
	}

	/**
	 * To make this onAreaTouched() works, user must call 
	 * mScene.setTouchAreaBindingOnActionDownEnabled(true), so this button
	 * can keep receiving ACTION_MOVE & ACTION_UP after user moving finger 
	 * outside the widget area.
	 */
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
			mIsPressed = true;
			setCurrentTileIndex(1);
			return true;
		case TouchEvent.ACTION_MOVE:
			Log.d(TAG, pTouchAreaLocalX + ", " + pTouchAreaLocalY);
			if (pTouchAreaLocalX <= 0 || pTouchAreaLocalX >= getWidth() || 
			    pTouchAreaLocalY <= 0 || pTouchAreaLocalY >= getHeight()) {
				mIsPressed = false;
				Log.d(TAG, "outside");
				setCurrentTileIndex(0);
			}
			return true;
		case TouchEvent.ACTION_UP:
			Log.d(TAG, "ACTION_UP");
			setCurrentTileIndex(0);
			if (mIsPressed) {
				mIsPressed = false;
				onClick();
			}
			return true;
		};
		return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	}

	public abstract void onClick();
}
