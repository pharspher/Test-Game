package org.ph.game.widget;

import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.ph.game.testgame.GameContext;
import org.ph.game.util.Logger;

public class BackgroundScroller {
	private static final String TAG = "BackgroundScroller";

	private GameContext mContext;
	private Engine mEngine;
	private Camera mCamera;
	private BitmapTextureAtlas mBackgroundAtlas;

	private int mPageWidth;
	private int mPageHeight;

	private ArrayList<Page> mContainer;

	private class Page {
		private ITextureRegion mAtlasRegion;
		private Sprite mSprite;
		private String mFilePath;
	}

	public BackgroundScroller(GameContext context, int pageWidth, int pageHeight) {
		mContext = context;
		mEngine = context.getEngine();
		mCamera = mEngine.getCamera();
		mContainer = new ArrayList<Page>();
		mPageWidth = pageWidth;
		mPageHeight = pageHeight;
	}

	public void addBackground(String fileName) {
		Logger.d(TAG, "add background " + fileName);
		Page page = new Page();
		page.mFilePath = fileName;
		mContainer.add(page);
	}

	public void load() {
		Logger.d(TAG, "load()");
		TextureManager txtMgr = mEngine.getTextureManager();

		int atlasWidth = mPageWidth;
		int atlasHeight = mPageHeight * mContainer.size();
		
		Logger.d(TAG,  "atlas size: " + atlasWidth + " x " + atlasHeight);

		mBackgroundAtlas = new BitmapTextureAtlas(txtMgr, atlasWidth,
				atlasHeight, TextureOptions.BILINEAR);
		
		for (int i = 0; i < mContainer.size(); i++) {
			Page p = mContainer.get(i);
			Logger.d(TAG, "file: " + p.mFilePath + ", x: " + 0 + ", y: " + (i * mPageHeight));
			p.mAtlasRegion = BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(mBackgroundAtlas, 
							mContext.getGameActivity(), p.mFilePath, 
							0, i * mPageHeight);
			//p.mSprite = new Sprite(0, 0, mPageWidth, mPageHeight, p.mAtlasRegion,
			//		mEngine.getVertexBufferObjectManager());
		}
		
		mBackgroundAtlas.load();
		for (int i = 0; i < mContainer.size(); i++) {
			//p.mAtlasRegion = BitmapTextureAtlasTextureRegionFactory
			//		.createFromAsset(mBackgroundAtlas, 
			//				mContext.getGameActivity(), p.mFilePath);
			Page p = mContainer.get(i);
			p.mSprite = new Sprite(0, 0, p.mAtlasRegion,
					mEngine.getVertexBufferObjectManager());
		}
		//mEngine.getScene().attachChild(mContainer.get(0).mSprite);
		
	}

	public void apply(Scene scene) {
		Logger.d(TAG, "apply()");
		for (int i = 0; i < mContainer.size(); i++) {
			//p.mAtlasRegion = BitmapTextureAtlasTextureRegionFactory
			//		.createFromAsset(mBackgroundAtlas, 
			//				mContext.getGameActivity(), p.mFilePath);
			Page p = mContainer.get(i);
			p.mSprite = new Sprite(0, 0, p.mAtlasRegion,
					mEngine.getVertexBufferObjectManager());
		}
		//scene.attachChild(mContainer.get(1).mSprite);
		
		AutoParallaxBackground autoParallaxBackground = 
				new AutoParallaxBackground(0, 0, 0, 5);
		autoParallaxBackground.attachParallaxEntity(
				new ParallaxEntity(10.0f, 
						new Sprite(0, 0, 
								mContainer.get(0).mAtlasRegion, 
								mEngine.getVertexBufferObjectManager())));
		autoParallaxBackground.attachParallaxEntity(
				new ParallaxEntity(10.0f, 
						new Sprite(0, mCamera.getWidth(), 
								mContainer.get(1).mAtlasRegion, 
								mEngine.getVertexBufferObjectManager())));
		scene.setBackground(autoParallaxBackground);
	}

	public Sprite getPageSprite(int index) {
		return mContainer.get(index).mSprite;
	}

	public ITextureRegion getPageRegion(int index) {
		return mContainer.get(index).mAtlasRegion;
	}

	public BitmapTextureAtlas getTextureAtlas() {
		return mBackgroundAtlas;
	}
}
