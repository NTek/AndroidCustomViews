package com.ntek.accelerated.software;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.ntek.R;

/**
 * Custom ImageView, working only with Software Accelerated.
 * 
 * @author Milos Milanovic
 */
public class RoundedImageView extends ImageView {

	private float mCornerRadius = 0f;
	private boolean[] mCorners = { false, false, false, false };

	public RoundedImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		/** Set ScaleXY. */
		setScaleType(ScaleType.FIT_XY);
		/** Clip Path, Works Only on Software Acceleration. */
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);

		/** Get Attributes. */
		TypedArray lTypedArray = context.obtainStyledAttributes(attrs,
				R.styleable.RoundedImageView);

		mCornerRadius = lTypedArray.getDimensionPixelSize(
				R.styleable.RoundedImageView_corner_radius, -1);
		mCornerRadius = mCornerRadius > 0 ? mCornerRadius : 0f;

		mCorners[0] = lTypedArray.getBoolean(
				R.styleable.RoundedImageView_corner_top_left, false);
		mCorners[1] = lTypedArray.getBoolean(
				R.styleable.RoundedImageView_corner_top_right, false);
		mCorners[2] = lTypedArray.getBoolean(
				R.styleable.RoundedImageView_corner_bottom_left, false);
		mCorners[3] = lTypedArray.getBoolean(
				R.styleable.RoundedImageView_corner_bottom_right, false);
		lTypedArray.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.clipPath(drawRoundedImage(canvas));
		super.onDraw(canvas);
	}

	private Path drawRoundedImage(Canvas canvas) {
		Path lPath = new Path();
		RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
		lPath.addRoundRect(rect, mCornerRadius, mCornerRadius,
				Path.Direction.CW);
		/** Top-Left */
		if (!mCorners[0]) {
			lPath.addRect(0, 0, canvas.getWidth() / 2, canvas.getHeight() / 2,
					Direction.CW);
		}
		/** Top-Right */
		if (!mCorners[1]) {
			lPath.addRect(canvas.getWidth() / 2, 0, canvas.getWidth(),
					canvas.getHeight() / 2, Direction.CW);
		}
		/** Bottom-Left */
		if (!mCorners[2]) {
			lPath.addRect(0, canvas.getHeight() / 2, canvas.getWidth() / 2,
					canvas.getHeight(), Direction.CW);
		}
		/** Bottom-Right */
		if (!mCorners[3]) {
			lPath.addRect(canvas.getWidth() / 2, canvas.getHeight() / 2,
					canvas.getWidth(), canvas.getHeight(), Direction.CW);
		}
		return lPath;
	}

}
