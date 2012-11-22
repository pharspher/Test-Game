package org.ph.game.widget;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.opengl.util.GLState;

public class ScrollingBackground extends Background {
	//private static final String TAG = "ScrollingBackground";

	@Override
	public void onUpdate(float pSecondsElapsed) {
		mOffset = (mOffset + 5);// % mTotalWidth;
		if (mOffset >= 1280) {
			mCurrentIndex = (mCurrentIndex + 1) % mBackgroundPages.size();
			mOffset %= 1280;;
		}
		super.onUpdate(pSecondsElapsed);
	}

	private ArrayList<BackgroundPage> mBackgroundPages = 
			new ArrayList<BackgroundPage>();

	private int mCurrentIndex = -1;
	private static int mTotalWidth;
	private int mOffset;

	@Override
	public void onDraw(GLState pGLState, Camera pCamera) {
		super.onDraw(pGLState, pCamera);
		mBackgroundPages.get(mCurrentIndex).onDraw(pGLState, pCamera, mOffset);
		mBackgroundPages.get((mCurrentIndex + 1) % mBackgroundPages.size())
				.onDraw(pGLState, pCamera, mOffset - pCamera.getWidth());
	}

	public void addBackgroundPage(final BackgroundPage page) {
		mCurrentIndex = 0;
		mTotalWidth += page.mAreaShape.getWidth();
		this.mBackgroundPages.add(page);;
	}

	public static class BackgroundPage {
		final IAreaShape mAreaShape;
		public BackgroundPage(final IAreaShape areaShape) {
			this.mAreaShape = areaShape;
		}

		public void onDraw(final GLState pGLState, final Camera pCamera, float offset) {
			pGLState.pushModelViewGLMatrix();
			{
				final float cameraWidth = pCamera.getWidth();
				final float shapeWidthScaled = this.mAreaShape.getWidthScaled();
				float baseOffset = 0 % mTotalWidth;

				//while(baseOffset > 0) {
				//	baseOffset -= shapeWidthScaled;
				//}
				pGLState.translateModelViewGLMatrixf(offset, 0, 0);

				//float currentMaxX = baseOffset;
				
				//do {
					this.mAreaShape.onDraw(pGLState, pCamera);
				//	pGLState.translateModelViewGLMatrixf(shapeWidthScaled, 0, 0);
				//	currentMaxX += shapeWidthScaled;
				//} while(currentMaxX < cameraWidth);
			}
			pGLState.popModelViewGLMatrix();
		}
	}
}
