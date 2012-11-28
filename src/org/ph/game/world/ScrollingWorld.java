package org.ph.game.world;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.ph.game.testgame.GameContext;

import android.hardware.SensorManager;
import android.view.KeyEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class ScrollingWorld extends BaseGameWorld{
    private GameContext mContext;
    private Engine mEngine;
    private Camera mCamera;
    private Scene mScene;
    private VertexBufferObjectManager mVboMgr;
    
    private Background mBackground;
    private Sprite mPlayerSprite;
    
    private PhysicsWorld mPhysicsWorld;
    
    public ScrollingWorld(GameContext pContext) {
        super(pContext);
        init();
    }
    
    public ScrollingWorld(GameContext pContext, Scene pScene) {
        super(pContext, pScene);
        init();
    }
    
    private void init() {
        mContext = getGameContext();
        mEngine = mContext.getEngine();
        mCamera = mEngine.getCamera();
        mScene = getScene();
        mVboMgr = mEngine.getVertexBufferObjectManager();
        mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);
        mEngine.enableAccelerationSensor(mContext.getGameActivity(), new IAccelerationListener() {
            @Override
            public void onAccelerationChanged(AccelerationData pAccelerationData) {
                float gravityX = pAccelerationData.getX();
                float gravityY = pAccelerationData.getY();

                final Vector2 gravity = Vector2Pool.obtain(gravityX, gravityY);
                mPhysicsWorld.setGravity(gravity);
                Vector2Pool.recycle(gravity);
            }
            
            @Override
            public void onAccelerationAccuracyChanged(AccelerationData pAccelerationData) {
            }
        });
    }
    
    public void create() {
        mEngine.registerUpdateHandler(mUpdateHandler);
        mScene.setBackground(mBackground);
        mScene.setOnSceneTouchListener(new IOnSceneTouchListener() {
            @Override
            public boolean onSceneTouchEvent(Scene pScene, TouchEvent pEvent) {
                //if (pEvent.getAction() == TouchEvent.ACTION_MOVE) {
                //    ((AutoParallaxBackground)mBackground).setParallaxValue(-pEvent.getX());
                //}
                return false;
            }
        });
        
        createPlayer();
        
        createPhysicGround();
        
        mScene.registerUpdateHandler(mPhysicsWorld);
    }
    
    private void createPhysicGround() {
        float camera_width = mCamera.getWidth();
        float camera_height = mCamera.getHeight();
        final Rectangle ground = new Rectangle(0, camera_height - 2, 
                camera_width, 2, mVboMgr);
        
        final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
        PhysicsFactory.createBoxBody(this.mPhysicsWorld, ground, BodyType.StaticBody, wallFixtureDef);
        
        mScene.attachChild(ground);
    }

    private void createPlayer() {
        float camera_width = mCamera.getWidth();
        float camera_height = mCamera.getHeight();
        //mPlayerSprite.setScaleCenterY(mPlayerSprite.getHeight());
        //mPlayerSprite.setScale(3);
        final float playerX = (camera_width - mPlayerSprite.getWidth()) / 2;
        final float playerY = camera_height - mPlayerSprite.getHeight();
        mPlayerSprite.setPosition(playerX, playerY);
        if (mPlayerSprite instanceof AnimatedSprite) {
            ((AnimatedSprite)mPlayerSprite).animate(new long[]{200,200, 200}, 3, 5, true);
        }
        mScene.attachChild(mPlayerSprite);
        
        final Body body;
        final FixtureDef objectFixtureDef = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
        body = PhysicsFactory.createBoxBody(this.mPhysicsWorld, 
                mPlayerSprite, BodyType.DynamicBody, objectFixtureDef);
        this.mPhysicsWorld.registerPhysicsConnector(
                new PhysicsConnector(mPlayerSprite, body, true, true));
    }
    
    public void setBackground(Background pBackground) {
        mBackground = pBackground;
    }
    
    public void addPlayer(Sprite pPlayerSprite) {
        mPlayerSprite = pPlayerSprite;
        //mScene.attachChild(pPlayerSprite);
    }
    
    private IUpdateHandler mUpdateHandler = new IUpdateHandler() {
        @Override
        public void onUpdate(float pSecondsElapsed) {
            //((AutoParallaxBackground)mBackground)
        }
        
        @Override
        public void reset() {
        }
    };
    
    @Override
    public boolean onKeyDown(int pKeyCode, KeyEvent pEvent) {
        return super.onKeyDown(pKeyCode, pEvent);
    }
}
