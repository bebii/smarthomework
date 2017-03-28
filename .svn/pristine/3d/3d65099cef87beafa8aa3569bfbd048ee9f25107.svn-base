package com.habebe.projecthomework.piechart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import android.view.View;

import com.habebe.projecthomework.R;

public class PieView extends View {
    private static final int TOP_OFFSET = 10;
    private static final float LINE_LEFT_OFFSET = 10;
    public static int DONUT_HALF_SIZE;
    private int TEXT_WIDTH;

    public static final String MAIN_PATH = "fonts/trebuc-";

    private ShapeDrawable[] mDrawables;
    private PieModel pieModel;
    private float AnswerTruePercent;
    private float DonotPercent;
    private float AnswerWrongPercent;

    private float blankPercent;

    private String sentText;
    private String rejectText;
    private String openText;
    private String receiveText;

    private int textGreenColor = 0xFF4CAF50;
    private int textOrangeColor = 0xFFFF5722;
    private int textRedColor = 0xFFF44336;
    private int textBlueColor = 0xFF2962FF;
    private int backgroundColor;
    private Paint textLegendPaint;
    private static int DONUT_SIZE;
    private int DONUT_OVERLAY_SIZE;
    private int centerPointX;
    private Paint centerLinePaint;
    private String sentTotal;
    private String rejectTotal;
    private String openTotal;
    private String receiveTotal;
    private Paint centerTextPaint;
    private int INSIDE_TOP_TEXT_OFFSET;

    public PieView(Context context) {
        super(context);
        init(context);
    }

    PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    PieView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public PieView(Context context, PieModel games) {
        super(context);
        this.pieModel = games;
        init(context);
    }

    private void init(Context context) {
        setFocusable(true);

        float density = context.getResources().getDisplayMetrics().density;

        DONUT_SIZE = (int) (getResources().getInteger(R.integer.DONUT_SIZE) * density);
        DONUT_HALF_SIZE = DONUT_SIZE / 2;
        DONUT_OVERLAY_SIZE = (int) (getResources().getInteger(R.integer.DONUT_OVERLAY_SIZE) * density);
        INSIDE_TOP_TEXT_OFFSET = (int) (8 * density);
        TEXT_WIDTH = (int) (30 * density);

        backgroundColor = context.getResources().getColor(R.color.white);

        int totalDegree = 360;
        // set wins
        // calc percent
        int startDegree = -90;

        if (pieModel != null) {
            if (pieModel.getTotal() > 0) {
                mDrawables = new ShapeDrawable[4];
                if (pieModel != null) {
                    AnswerTruePercent = ((float) pieModel.getAnswercorrect() / pieModel.getTotal());
                }

                float answertrueDegree = totalDegree * AnswerTruePercent;
                sentText = getResources().getString(R.string.pie_chart_sent_legend, AnswerTruePercent * 100);

                if (pieModel != null) {
                    AnswerWrongPercent = ((float) pieModel.getAnswerwrong() / pieModel.getTotal());
                }

                float answerwrongDegree = totalDegree * AnswerWrongPercent;
                float answerwrongStartDegree = startDegree - answertrueDegree;
                openText = getResources().getString(R.string.pie_chart_open_legend, AnswerWrongPercent * 100);

                if (pieModel != null) {
                    DonotPercent = ((float) pieModel.getDonothomework() / pieModel.getTotal());
                }

                float donotDegree = totalDegree * DonotPercent;
                float donotStartDegree = startDegree - answertrueDegree - answerwrongDegree;
                rejectText = getResources().getString(R.string.pie_chart_reject_legend, DonotPercent * 100);

                //รf (pieModel != null) {
                //      receivePercent = ((float) pieModel.getReceived() / pieModel.getTotal());
                //}
                //float receiveDegree = totalDegree * receivePercent;
                //float receiveStartDegree = startDegree - answertrueDegree - answerwrongDegree - donotDegree;
                //eceiveText = getResources().getString(R.string.pie_chart_receive_legend, receivePercent * 100);

                mDrawables[0] = new MyShapeDrawable(new ArcShape(startDegree, -answertrueDegree));
                mDrawables[1] = new MyShapeDrawable(new ArcShape(answerwrongStartDegree, -answerwrongDegree));
                mDrawables[2] = new MyShapeDrawable(new ArcShape(donotStartDegree, -donotDegree));
                mDrawables[3] = new ShapeDrawable(new OvalShape());

                mDrawables[3].getPaint().setColor(backgroundColor);

                int greenColor1 = context.getResources().getColor(R.color.donut_green);
                int greenColor2 = context.getResources().getColor(R.color.donut_green);
                mDrawables[0].getPaint().setShader(makeRadial(greenColor1, greenColor2));

                int greyColor1 = context.getResources().getColor(R.color.donut_red);
                int greyColor2 = context.getResources().getColor(R.color.donut_red);
                mDrawables[1].getPaint().setShader(makeRadial(greyColor1, greyColor2));

                int orangeColor1 = context.getResources().getColor(R.color.donut_orange);
                int orangeColor2 = context.getResources().getColor(R.color.donut_orange);
                mDrawables[2].getPaint().setShader(makeRadial(orangeColor1, orangeColor2));

                //int blueColor1 = context.getResources().getColor(R.color.blue_light);
                //int blueColor2 = context.getResources().getColor(R.color.blue_light);
                //mDrawables[3].getPaint().setShader(makeRadial(blueColor1, blueColor2));

                {
                    MyShapeDrawable msd = (MyShapeDrawable) mDrawables[0];
                    msd.getStrokePaint().setStrokeWidth(1);
                }

                {
                    MyShapeDrawable msd = (MyShapeDrawable) mDrawables[1];
                    msd.getStrokePaint().setStrokeWidth(1);
                }

                {
                    MyShapeDrawable msd = (MyShapeDrawable) mDrawables[2];
                    msd.getStrokePaint().setStrokeWidth(1);
                }

            } else {
                mDrawables = new ShapeDrawable[2];
                if (pieModel != null) {
                    blankPercent = ((float) 1);
                }
                float blankDegree = 360;

                mDrawables[0] = new MyShapeDrawable(new ArcShape(startDegree, -blankDegree));
                mDrawables[1] = new ShapeDrawable(new OvalShape());

                mDrawables[1].getPaint().setColor(backgroundColor);

                int grayColor1 = context.getResources().getColor(R.color.gray);
                int grayColor2 = context.getResources().getColor(R.color.gray);
                mDrawables[0].getPaint().setShader(makeRadial(grayColor1, grayColor2));

                {
                    MyShapeDrawable msd = (MyShapeDrawable) mDrawables[0];
                    msd.getStrokePaint().setStrokeWidth(1);
                }

            }
        }

        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), MAIN_PATH + "Bold" + ".ttf");
        {// legend paint setup
            textLegendPaint = new Paint();
            textLegendPaint.setTypeface(typeface);
            textLegendPaint.setTextSize(context.getResources().getInteger(R.integer.piechart_fontsize) * density);

            // center lines
            centerLinePaint = new Paint();
            centerLinePaint.setColor(0xFFdfdfdf);
            centerLinePaint.setStyle(Paint.Style.STROKE);
            centerLinePaint.setStrokeWidth(2);

        }
    }

    private static Shader makeRadial(int color1, int color2) {
        return new RadialGradient(DONUT_HALF_SIZE, DONUT_HALF_SIZE, DONUT_HALF_SIZE, color1, color2, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        centerPointX = (width / 2);

        canvas.drawColor(backgroundColor);

        drawDonut(canvas);

        int overlayCenter = DONUT_OVERLAY_SIZE / 2;
        int widthBetweenLines = DONUT_OVERLAY_SIZE / 3;
        int topLineY = overlayCenter - widthBetweenLines / 2;

       /* Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_action_map);*/

//      draw legend labels
        {// sent
//			int sentX = centerPointX-(TEXT_WIDTH);
//			int sentY = TOP_OFFSET + 2*overlayCenter;
//			textLegendPaint.setColor(textGreenColor);
//			canvas.drawText("SKYFROG", sentX, sentY, textLegendPaint);
//            canvas.drawBitmap(bmp,sentX,sentY,null);
        }
//
//		{// receive
//			int receiveX = centerPointX + DONUT_HALF_SIZE + TEXT_WIDTH;
//			int receiveY = topLineY + 3*TOP_OFFSET;
//			textLegendPaint.setColor(textBlueColor);
//			canvas.drawText(receiveText, receiveX, receiveY, textLegendPaint);
//			
//		}
//
//		{// reject
//			int rejectX = centerPointX + DONUT_HALF_SIZE + TEXT_WIDTH;
//			int rejectY = topLineY + 5*TOP_OFFSET;
//			textLegendPaint.setColor(textOrangeColor);
//			canvas.drawText(rejectText, rejectX, rejectY, textLegendPaint);
//		
//		}
//		
//		{// open
//			int openX = centerPointX + DONUT_HALF_SIZE + TEXT_WIDTH;
//			int openY = topLineY + TOP_OFFSET;
//			textLegendPaint.setColor(textRedColor);
//			canvas.drawText(openText, openX, openY, textLegendPaint);
//		}
    }

    private void drawDonut(Canvas canvas) {
        int x = 0;
        int y = 0;

        canvas.save();
        canvas.translate(centerPointX - DONUT_HALF_SIZE, TOP_OFFSET);
        if (pieModel != null) {
            if (pieModel.getTotal() > 0) {
                // pie 1
                mDrawables[0].setBounds(x, y, x + DONUT_SIZE, y + DONUT_SIZE);
                mDrawables[0].draw(canvas);

                // pie 2
                mDrawables[1].setBounds(x, y, x + DONUT_SIZE, y + DONUT_SIZE);
                mDrawables[1].draw(canvas);

                // pie 3
                mDrawables[2].setBounds(x, y, x + DONUT_SIZE, y + DONUT_SIZE);
                mDrawables[2].draw(canvas);

                // white overlay
                canvas.save();
                int overlayCenter = DONUT_OVERLAY_SIZE / 2;
                int xOffset = DONUT_HALF_SIZE - overlayCenter;
                canvas.translate(xOffset, xOffset);

                mDrawables[3].setBounds(x, y, x + DONUT_OVERLAY_SIZE, y + DONUT_OVERLAY_SIZE);
                mDrawables[3].draw(canvas);

                int widthBetweenLines = DONUT_OVERLAY_SIZE / 3;

                // center lines
                int topLineY = overlayCenter - widthBetweenLines / 2;
                //canvas.drawLine(LINE_LEFT_OFFSET, topLineY, DONUT_OVERLAY_SIZE - LINE_LEFT_OFFSET, topLineY, centerLinePaint);

                int bottomLineY = overlayCenter + widthBetweenLines / 2;
                //canvas.drawLine(LINE_LEFT_OFFSET, bottomLineY, DONUT_OVERLAY_SIZE - LINE_LEFT_OFFSET, bottomLineY, centerLinePaint);


            } else {
                // pie 1
                mDrawables[0].setBounds(x, y, x + DONUT_SIZE, y + DONUT_SIZE);
                mDrawables[0].draw(canvas);

                // white overlay
                canvas.save();
                int overlayCenter = DONUT_OVERLAY_SIZE / 2;
                int xOffset = DONUT_HALF_SIZE - overlayCenter;
                canvas.translate(xOffset, xOffset);

                mDrawables[1].setBounds(x, y, x + DONUT_OVERLAY_SIZE, y + DONUT_OVERLAY_SIZE);
                mDrawables[1].draw(canvas);

                int widthBetweenLines = DONUT_OVERLAY_SIZE / 3;

                // center lines
                int topLineY = overlayCenter - widthBetweenLines / 2;
                //canvas.drawLine(LINE_LEFT_OFFSET, topLineY, DONUT_OVERLAY_SIZE - LINE_LEFT_OFFSET, topLineY, centerLinePaint);

                int bottomLineY = overlayCenter + widthBetweenLines / 2;
                //canvas.drawLine(LINE_LEFT_OFFSET, bottomLineY, DONUT_OVERLAY_SIZE - LINE_LEFT_OFFSET, bottomLineY, centerLinePaint);
            }
        }

        canvas.restore();
        canvas.restore();
    }

    public void setPieModel(PieModel model) {
        this.pieModel = model;
        init(getContext());
        invalidate();
    }

    private static class MyShapeDrawable extends ShapeDrawable {
        private Paint mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        public MyShapeDrawable(Shape s) {
            super(s);
            mStrokePaint.setStyle(Paint.Style.STROKE);
            mStrokePaint.setColor(0x26FFFFFF);  // TODO set properly
        }

        public Paint getStrokePaint() {
            return mStrokePaint;
        }

        @Override
        protected void onDraw(Shape s, Canvas c, Paint p) {
            s.draw(c, p);
            s.draw(c, mStrokePaint);
        }
    }
}
