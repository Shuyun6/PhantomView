package shuyun.PhantomView.BarView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;

import shuyun.PhantomView.Utils.PhantomColor;

/**
 * Created by Shuyun on 2017/8/18 0018.
 */
public class BarView extends View {
    /**
     * whole view's width and height
     */
    private int width, height;
    /**
     * gap width between two items
     */
    private int gap;
    /**
     * Margin to each bounds
     */
    private int leftMargin = 20, topMargin = 20, rightMargin = 20, bottomMargin = 20;
    /**
     * list for setting each items
     */
    private List<BarViewItem> listOfBarViewItem;
    /**
     * count of items, you can limit the count of showing items
     */
    private int count = -1;
    /**
     * the largest height in bar view
     */
    private int scale = 0;
    //simple mode
    /**
     * height of each items in simple mode
     */
    private int[] item;
    /**
     * color of each items' content in simple mode
     */
    private int itemColor = PhantomColor.Amber_500;
    /**
     * title color of each items in simple mode
     */
    private int itemTitleColor = PhantomColor.Brown_500;
    /**
     * gravity of whole bars, default is @GRAVITY_BOTTOM, means bars base on bottom and growth to top
     */
    private int gravity = GRAVITY_BOTTOM;
    /**
     * default width of item for simple mode
     */
    private int itemDefaultWidth;

    public static final int GRAVITY_LEFT = 0;
    public static final int GRAVITY_TOP = 1;
    public static final int GRAVITY_RIGHT = 2;
    public static final int GRAVITY_BOTTOM = 3;

    private Paint paint;
    private Handler handler;

    public BarView(Context context) {
        super(context);
    }

    public BarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        handler = new Handler();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        //setting simple mode data
        if(null == listOfBarViewItem){
            if(count == -1)
                count = item.length;
            itemDefaultWidth = (width - leftMargin - rightMargin)/count * 2 / 3;
            gap = (int) (0.5 * itemDefaultWidth);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(null != listOfBarViewItem){
            BarViewItem item;
            int lastX = 0;
            for (int i = 0; i < listOfBarViewItem.size(); i++) {
                item = listOfBarViewItem.get(i);
                paint.setColor(item.getItemColor());
                canvas.drawRect(rightMargin + gap * (i + 1) + lastX, height - bottomMargin - item.getHeight(),
                        rightMargin + gap * (i + 1) + lastX + itemDefaultWidth, height - bottomMargin, paint);
                lastX = itemDefaultWidth;
            }
        }else if(null != item){
            int lastX = 0;
            paint.setColor(itemColor);
            for (int i = 0; i < item.length; i++) {
                canvas.drawRect(rightMargin + gap * (i + 1) + lastX, height - bottomMargin - item[i],
                        rightMargin + gap * (i + 1) + lastX + itemDefaultWidth, height - bottomMargin, paint);
                lastX += itemDefaultWidth;
            }
        }
    }

    public void setListOfBarViewItem(List<BarViewItem> listOfBarViewItem) {
        this.listOfBarViewItem = listOfBarViewItem;
    }

    public void setItem(int... item) {
        this.item = new int[item.length];
        for(int i = 0; i < item.length; i++) {
            this.item[i] = item[i];
        }
    }
}
