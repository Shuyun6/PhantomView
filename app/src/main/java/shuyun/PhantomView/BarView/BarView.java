package shuyun.PhantomView.BarView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
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
    private int gap = -1;
    /**
     * Margin to each bounds
     */
    private int leftMargin = 20, topMargin = 20, rightMargin = 20, bottomMargin = 20;
    /**
     * list for setting each items
     */
    private List<BarViewItem> listOfBarViewItem = new ArrayList<BarViewItem>();
    /**
     * count of items, limit the count of showing items
     */
    private int count = -1;
    /**
     * the largest height in bar view
     */
    private int scale = 0;
    //simple mode
    /**
     * height of each items
     */
    private int[] itemHeight;
    /**
     * color of each items
     */
    private int[] itemColor;
    /**
     * title name of each items
     */
    private String[] itemTitle;
    /**
     * title color of each items
     */
    private int[] itemTitleColor;
    /**
     * gravity of whole bars, default is @GRAVITY_BOTTOM, means bars base on bottom and growth to top
     */
    private int gravity = GRAVITY_BOTTOM;

    private int itemAllWidth = 0;
    private int itemAllColor = PhantomColor.Amber_500;
    private int itemAllTitleColor = PhantomColor.BlueGrey_500;
    /**
     * whether showing the title text
     */
    private boolean isShowTitle = false;
    private int titleSize = -1;

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

    /**
     * show a bar view after setting data for it
     */
    public void show() {
        if (null != itemHeight && itemHeight.length > 0) {
            if(count == -1)
                count = listOfBarViewItem.size();
            if(itemAllWidth == 0 && count != 0)
                itemAllWidth = (width - leftMargin - rightMargin)/count * 2 / 3;
            if(gap == -1)
                gap = (int) (0.5 * itemAllWidth);
            if(titleSize == -1)
                titleSize = (int) (0.2 * itemAllWidth);
            if(titleSize == 0)
                titleSize = 16;

            for(int i = 0; i < itemHeight.length; i++) {
                BarViewItem barViewItem = new BarViewItem();
                barViewItem.setHeight(itemHeight[i]);
                if (null != itemTitle && itemTitle.length > 0) {
                    barViewItem.setTitle(itemTitle[i]);
                }else{
                    barViewItem.setTitle(""+itemHeight[i]);
                }
                if (null != itemTitleColor && itemTitleColor.length > 0) {
                    barViewItem.setTitleColor(itemTitleColor[i]);
                }else{
                    barViewItem.setTitleColor(itemAllTitleColor);
                }
                if (null != itemColor && itemColor.length > 0) {
                    barViewItem.setItemColor(itemColor[i]);
                }else{
                    barViewItem.setItemColor(itemAllColor);
                }
                barViewItem.setTitleSize(titleSize);
                listOfBarViewItem.add(barViewItem);
            }
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                postInvalidate();
            }
        });
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
        if(null != listOfBarViewItem){
            BarViewItem item;
            for (int i = 0; i < listOfBarViewItem.size(); i++) {
                item = listOfBarViewItem.get(i);
                paint.setColor(item.getItemColor());
                canvas.drawRect(rightMargin + gap + (gap + itemAllWidth) * i, height - bottomMargin - item.getHeight(),
                        rightMargin + gap + (gap + itemAllWidth) * i + itemAllWidth, height - bottomMargin, paint);
            }
            for (int i = 0; i < listOfBarViewItem.size(); i++) {
                item = listOfBarViewItem.get(i);
                if(isShowTitle){
                    paint.setColor(item.getTitleColor());
                    String text = item.getTitle();
                    int size = item.getTitleSize();
                    int textWidth = (int) Math.sqrt(size*size/2) * text.length();
                    int textLimitCount = (int) (itemAllWidth/Math.sqrt(size*size/2));
                    int textLeftMargin = 0;
                    if(itemAllWidth > textWidth)
                        textLeftMargin = (itemAllWidth - textWidth)/2;
                    paint.setTextSize(size);
                    if(text.length() > textLimitCount)
                        canvas.drawText(item.getTitle(), 0, textLimitCount, rightMargin + gap + (gap + itemAllWidth) * i + textLeftMargin, height - bottomMargin - item.getHeight(), paint);
                    else
                        canvas.drawText(item.getTitle(), rightMargin + gap + (gap + itemAllWidth) * i + textLeftMargin, height - bottomMargin - item.getHeight(), paint);
                }
            }
        }
    }

    /**
     * directly setting list of bar view item
     * @param listOfBarViewItem
     */
    public void setListOfBarViewItem(List<BarViewItem> listOfBarViewItem) {
        this.listOfBarViewItem = listOfBarViewItem;
    }

    /**
     * quickly set items' height
     * @param item
     */
    public void setItemHeight(int... item) {
        itemHeight = new int[item.length];
        for(int i = 0; i < item.length; i++) {
            itemHeight[i] = item[i];
        }
    }

    /**
     * quickly set items' color
     * @param color
     * @return <tt>true</tt> if this color array's length matches itemHeight's length
     */
    public boolean setItemColor(int... color) {
        if(null == itemHeight || itemHeight.length == 0 || itemHeight.length != color.length)
            return false;
        itemColor = new int[color.length];
        for(int i = 0; i < color.length; i++) {
            itemColor[i] = color[i];
        }
        return true;
    }

    /**
     * quickly set items' title
     * @param title
     * @return <tt>true</tt> if this title array's length matches itemHeight's length
     */
    public boolean setItemTitle(String... title) {
        if(null == itemHeight || itemHeight.length == 0 || itemHeight.length != title.length)
            return false;
        itemTitle = new String[title.length];
        for(int i = 0; i < title.length; i++) {
            itemTitle[i] = title[i];
        }
        return true;
    }

    /**
     * quickly set items' title color
     * @param color
     * @return <tt>true</tt> if this color array's length matches itemHeight's length
     */
    public boolean setItemTitleColor(int... color) {
        if(null == itemHeight || itemHeight.length == 0 || itemHeight.length != color.length)
            return false;
        itemTitleColor = new int[color.length];
        for(int i = 0; i < color.length; i++) {
            itemTitleColor[i] = color[i];
        }
        return true;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public void setitemAllWidth(int itemAllWidth) {
        this.itemAllWidth = itemAllWidth;
    }

    public void setShowTitle(boolean showTitle) {
        isShowTitle = showTitle;
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }
}
