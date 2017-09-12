package shuyun.PhantomView.BarView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Shuyun on 2017/9/4 0004.
 */
public class Bar extends View {

    private int width, height;
    private int[] itemColor;
    private int backgroundColor ;
    private String[] topText;
    private int topTextSize;
    private int topTextMarginToBar;
    private String[] bottomText;
    private int bottomTextSize;
    private int bottomTextMarginToBar;
    private int marginLeft, marginRight, marginTop, marginBottom;
    private int itemMargin;
    private int barCount = 1;
    private int[] itemValue;
    private int itemWeight;
    private Context context;
    private Paint paint;

    public Bar(Context context) {
        super(context);
        this.context = context;
        this.paint = new Paint();
    }

    public static Bar create(Context context) {
        return new Bar(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(0 != backgroundColor){
            paint.setColor(backgroundColor);
            canvas.drawRect(0, height, width, 0, paint);
        }

        for(int i = 0; i < barCount; i++){
            paint.setColor(itemColor[i]);
            canvas.drawRect(marginLeft + i * (itemMargin + itemWeight), itemValue[i],
                    marginLeft + i * (itemMargin + itemWeight) + itemWeight, 0, paint);
        }
    }

    public Bar setBarCount(int barCount) {
        this.barCount = barCount;
        itemColor = new int[barCount];
        itemValue = new int[barCount];
        return this;
    }
}
