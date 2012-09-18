package org.ph.game.gamestate;

import java.util.ArrayList;
import java.util.Random;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.ph.game.testgame.GameContext;

import android.opengl.GLES20;

public class ShootGameState extends BaseGameState {
	//private static final String TAG = "ShootGameState";

	private Scene mScene;
	private GameContext mContext;
	private Engine mEngine;

	private BuildableBitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mPlayerTextureRegion;
	private TiledTextureRegion mEnemyTextureRegion;

	private BitmapTextureAtlas mOnScreenControlTextureAtlas;
	private ITextureRegion mOnScreenControlBaseTextureRegion;
	private ITextureRegion mOnScreenControlKnobTextureRegion;

	private AnimatedSprite mPlayer;
	private ArrayList<AnimatedSprite> mEnemies;

	public ShootGameState(GameContext context) {
		super(context);
		mContext = context;
		mEngine = mContext.getEngine();
	}

	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("sprite/");

		TextureManager textureMgr = mEngine.getTextureManager();
		mBitmapTextureAtlas = new BuildableBitmapTextureAtlas(textureMgr, 
				2048, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		mPlayerTextureRegion = 
				BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(
						mBitmapTextureAtlas, 
						mContext.getGameActivity(), 
						"shooter_sprite0.png",12, 1);

		mEnemyTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mBitmapTextureAtlas, 
						mContext.getGameActivity(), 
						"enemy_sprite0.png", 12, 1);

		try {
			mBitmapTextureAtlas.build(
					new BlackPawnTextureAtlasBuilder
					<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
		mBitmapTextureAtlas.load();

		mPlayer = new AnimatedSprite(0, 0, 85, 110, mPlayerTextureRegion, 
				mEngine.getVertexBufferObjectManager());

		mEnemies = new ArrayList<AnimatedSprite>();

		mOnScreenControlTextureAtlas = new BitmapTextureAtlas(
				textureMgr, 256, 128, TextureOptions.BILINEAR);
		mOnScreenControlBaseTextureRegion = 
				BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(mOnScreenControlTextureAtlas, 
							mContext.getGameActivity(), 
							"onscreen_control_base.png", 0, 0);
		mOnScreenControlKnobTextureRegion = 
				BitmapTextureAtlasTextureRegionFactory
					.createFromAsset(mOnScreenControlTextureAtlas, 
							mContext.getGameActivity(), 
							"onscreen_control_knob.png", 128, 0);
		mOnScreenControlTextureAtlas.load();
	}

	@Override
	public Scene onCreateScene() {
		mScene = new Scene();
		mScene.setBackground(new Background(0.09f, 0.5f, 0.7f));

		initEnemyGenerator();

		Camera camera = mEngine.getCamera();

		final PhysicsHandler physicsHandler = new PhysicsHandler(mPlayer);
		mPlayer.registerUpdateHandler(physicsHandler);
		final float x1 = 0;
		final float y1 = camera.getHeight() - 
				mOnScreenControlBaseTextureRegion.getHeight();
		final AnalogOnScreenControl velocityOnScreenControl = 
				new AnalogOnScreenControl(x1, y1, camera, 
						mOnScreenControlBaseTextureRegion, 
						mOnScreenControlKnobTextureRegion, 0.1f, 
						mEngine.getVertexBufferObjectManager(), 
						new IAnalogOnScreenControlListener() {
			@Override
			public void onControlChange(
					final BaseOnScreenControl pBaseOnScreenControl, 
					final float pValueX, final float pValueY) {
				physicsHandler.setVelocity(pValueX * 200, pValueY * 200);
			}

			@Override
			public void onControlClick(
					final AnalogOnScreenControl pAnalogOnScreenControl) {
			}
		});
		velocityOnScreenControl.getControlBase().setBlendFunction(
				GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		velocityOnScreenControl.getControlBase().setAlpha(0.5f);
		mScene.setChildScene(velocityOnScreenControl);

		mScene.attachChild(mPlayer);

		return mScene;
	}

	private void initEnemyGenerator() {
		TimerHandler handler = new TimerHandler(1, true, 
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						addEnemy();
					}
		});
		mEngine.registerUpdateHandler(handler);
	}


	public void addEnemy() {
		Random rand = new Random();
		Camera camera = mEngine.getCamera();

		int x = (int)(camera.getWidth() + mEnemyTextureRegion.getWidth());
		int minY = (int)mEnemyTextureRegion.getHeight();
		int maxY = (int)(camera.getHeight() - mEnemyTextureRegion
				.getHeight());
		int rangeY = maxY - minY;
		int y = rand.nextInt(rangeY) + minY;

		AnimatedSprite target = new AnimatedSprite(x, y, mEnemyTextureRegion, 
				mEngine.getVertexBufferObjectManager());
		mScene.attachChild(target);

		int minDuration = 2;
		int maxDuration = 4;
		int rangeDuration = maxDuration - minDuration;
		int actualDuration = rand.nextInt(rangeDuration) + minDuration;
	
		MoveXModifier mod = new MoveXModifier(actualDuration, target.getX(),
				-target.getWidth());
		target.registerEntityModifier(mod.deepCopy());

		mEnemies.add(target);
		target.animate(15);
	}

	@Override
	public void onResume() {
	}
}
