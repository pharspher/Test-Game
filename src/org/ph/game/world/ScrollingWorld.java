package org.ph.game.world;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.ph.game.testgame.GameContext;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import android.view.KeyEvent;

public class ScrollingWorld extends BaseGameWorld{
    private Background mBackground;
    private Sprite mPlayerSprite;
    
    private PhysicsWorld mPhysicsWorld;
    
    public ScrollingWorld(GameContext pContext) {
        super(pContext);
    }
    
    public ScrollingWorld(GameContext pContext, Scene pScene) {
        super(pContext, pScene);
    }
    
    public void create() {
        getGameContext().getEngine().registerUpdateHandler(mUpdateHandler);
        getScene().setBackground(mBackground);
        getScene().setOnSceneTouchListener(new IOnSceneTouchListener() {
            @Override
            public boolean onSceneTouchEvent(Scene pScene, TouchEvent pEvent) {
                //if (pEvent.getAction() == TouchEvent.ACTION_MOVE) {
                //    ((AutoParallaxBackground)mBackground).setParallaxValue(-pEvent.getX());
                //}
                return false;
            }
        });
        
        createPlayer();
        
        final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
        PhysicsFactory.createBoxBody(this.mPhysicsWorld, ground, BodyType.StaticBody, wallFixtureDef);
        PhysicsFactory.createBoxBody(this.mPhysicsWorld, roof, BodyType.StaticBody, wallFixtureDef);
        PhysicsFactory.createBoxBody(this.mPhysicsWorld, left, BodyType.StaticBody, wallFixtureDef);
        PhysicsFactory.createBoxBody(this.mPhysicsWorld, right, BodyType.StaticBody, wallFixtureDef);

        this.mScene.attachChild(ground);
        this.mScene.attachChild(roof);
        this.mScene.attachChild(left);
        this.mScene.attachChild(right);

        this.mScene.registerUpdateHandler(this.mPhysicsWorld);

    }
    
    private void createPlayer() {
        float camera_width = this.getGameContext().getEngine().getCamera().getWidth();
        float camera_height = this.getGameContext().getEngine().getCamera().getHeight();
        mPlayerSprite.setScaleCenterY(mPlayerSprite.getHeight());
        mPlayerSprite.setScale(3);
        final float playerX = (camera_width - mPlayerSprite.getWidth()) / 2;
        final float playerY = camera_height - mPlayerSprite.getHeight();
        mPlayerSprite.setPosition(playerX, playerY);
        if (mPlayerSprite instanceof AnimatedSprite) {
            ((AnimatedSprite)mPlayerSprite).animate(new long[]{200,200, 200}, 3, 5, true);
        }
        getScene().attachChild(mPlayerSprite);
        
        
    }

    public void setBackground(Background pBackground) {
        mBackground = pBackground;
    }
    
    public void addPlayer(Sprite pPlayerSprite) {
        mPlayerSprite = pPlayerSprite;
        //this.getScene().attachChild(pPlayerSprite);
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
