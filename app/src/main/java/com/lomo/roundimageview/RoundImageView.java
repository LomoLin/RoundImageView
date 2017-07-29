package com.lomo.roundimageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by Lomo on 2017/7/29.
 */

public class RoundImageView extends AppCompatImageView {

    private int mCornerRadius;
    private int mCornerRadiusTL;
    private int mCornerRadiusTR;
    private int mCornerRadiusBL;
    private int mCornerRadiusBR;

    private Paint mPaint;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        mCornerRadius = typedArray.getDimensionPixelSize(R.styleable.RoundImageView_riv_cornerRadius, -1);
        mCornerRadiusTL = typedArray.getDimensionPixelSize(R.styleable.RoundImageView_riv_cornerRadius_TL, -1);
        mCornerRadiusTR = typedArray.getDimensionPixelSize(R.styleable.RoundImageView_riv_cornerRadius_TR, -1);
        mCornerRadiusBL = typedArray.getDimensionPixelSize(R.styleable.RoundImageView_riv_cornerRadius_BL, -1);
        mCornerRadiusBR = typedArray.getDimensionPixelSize(R.styleable.RoundImageView_riv_cornerRadius_BR, -1);
        typedArray.recycle();
        //
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(bitmap);
        super.onDraw(canvas1);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        if (-1 != mCornerRadius) {
            drawRoundCorner(canvas1, mCornerRadius);
        } else {
            if (-1 != mCornerRadiusTL) {
                drawRoundCornerTL(canvas1, mCornerRadiusTL);
            }

            if (-1 != mCornerRadiusTR) {
                drawRoundCornerTR(canvas1, mCornerRadiusTR);
            }

            if (-1 != mCornerRadiusBL) {
                drawRoundCornerBL(canvas1, mCornerRadiusBL);
            }

            if (-1 != mCornerRadiusBR) {
                drawRoundCornerBR(canvas1, mCornerRadiusBR);
            }
        }


        mPaint.setXfermode(null);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);

        bitmap.recycle();
    }

    /**
     * 设置圆角
     */
    private void drawRoundCorner(Canvas canvas, int cornerRadius) {
        drawRoundCornerTL(canvas, cornerRadius);
        drawRoundCornerTR(canvas, cornerRadius);
        drawRoundCornerBL(canvas, cornerRadius);
        drawRoundCornerBR(canvas, cornerRadius);
    }

    /**
     * 设置左上角圆角
     */
    private void drawRoundCornerTL(Canvas canvas, int cornerRadius) {
        Path path = new Path();
        path.moveTo(0, cornerRadius);
        path.lineTo(0, 0);
        path.lineTo(cornerRadius, 0);
        RectF rectF = new RectF(0, 0, cornerRadius * 2, cornerRadius * 2);
        path.arcTo(rectF, -90, -90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    /**
     * 设置右上角圆角
     */
    private void drawRoundCornerTR(Canvas canvas, int cornerRadius) {
        Path path = new Path();
        path.moveTo(getWidth(), cornerRadius);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - cornerRadius, 0);
        RectF rectF = new RectF(getWidth() - 2 * cornerRadius, 0, getWidth(), cornerRadius * 2);
        path.arcTo(rectF, -90, 90);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    /**
     * 设置左下角圆角
     */
    private void drawRoundCornerBL(Canvas canvas, int cornerRadius) {
        Path path = new Path();
        path.moveTo(0, getHeight() - cornerRadius);
        path.lineTo(0, getHeight());
        path.lineTo(getWidth() - cornerRadius, getHeight());
        RectF rectF = new RectF(0, getHeight() - 2 * cornerRadius, cornerRadius * 2, getHeight());
        path.arcTo(rectF, 90, 90);
        path.close();
        canvas.drawPath(path, mPaint);

    }

    /**
     * 设置右下角圆角
     */
    private void drawRoundCornerBR(Canvas canvas, int cornerRadius) {
        Path path = new Path();
        path.moveTo(getWidth() - cornerRadius, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - cornerRadius);
        RectF rectF = new RectF(getWidth() - 2 * cornerRadius, getHeight() - 2 * cornerRadius, getWidth(), getHeight());
        path.arcTo(rectF, 0, 90);
        path.close();
        canvas.drawPath(path, mPaint);
    }
}
