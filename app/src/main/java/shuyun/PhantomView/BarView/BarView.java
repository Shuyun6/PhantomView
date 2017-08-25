package shuyun.PhantomView.BarView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
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
    private int firstGap = 10;
    /**
     * Margin to each bounds
     */
    private int leftMargin = 80, topMargin = 40, rightMargin = 40, bottomMargin = 40;
    /**
     * list for setting each items
     */
    private List<BarViewItem> listOfBarViewItem = new ArrayList<BarViewItem>();
    /**
     * count of items, limit the count of showing items
     */
    private int count = -1;
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
    private String[] itemText;
    private int[] itemTextSize;
    private int[] itemTextColor;
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
    private int maxX;
    private int maxY;
    private int scaleX, scaleY;

    public static final int GRAVITY_LEFT = 0;
    public static final int GRAVITY_TOP = 1;
    public static final int GRAVITY_RIGHT = 2;
    public static final int GRAVITY_BOTTOM = 3;

    private Paint paint;
    private Handler handler;
    private boolean first = true;

    public BarView(Context context) {
        super(context);
    }

    public BarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        handler = new Handler();
        Log.e("test", "BarView");
    }

    /**
     * show a bar view after setting data for it
     */
    private void process() {
        Log.e("test", "show");
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
            if (maxY == 0)
                maxY = height - bottomMargin - topMargin;
            if(scaleY == 0)
                scaleY = maxY / 5;
            for(int i = 0; i < itemHeight.length; i++) {
                BarViewItem barViewItem = new BarViewItem();
                int itemheight = itemHeight[i];
                if(height != 0 )
                    itemheight = itemheight * (height - topMargin - bottomMargin) / maxY;
                barViewItem.setHeight(itemheight);
                if (null != itemTitle && itemTitle.length > 0) {
                    barViewItem.setTitle(itemTitle[i]);
                }else{
                    barViewItem.setTitle(""+itemHeight[i]);
                }
                if (null != itemTitleColor && itemTitleColor.length > 0) {
                    if(itemTitleColor[i] == 0)
                        itemTitleColor[i] = itemAllTitleColor;
                    barViewItem.setTitleColor(itemTitleColor[i]);
                }else{
                    barViewItem.setTitleColor(itemAllTitleColor);
                }
                if (null != itemColor && itemColor.length > 0) {
                    if(itemColor[i] == 0)
                        itemColor[i] = itemAllColor;
                    barViewItem.setItemColor(itemColor[i]);
                }else{
                    barViewItem.setItemColor(itemAllColor);
                }
                barViewItem.setTitleSize(titleSize);
                listOfBarViewItem.add(barViewItem);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("test", "measure");
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("test", "measure "+width+":"+height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("test", "ondraw");
        if(first){
            first = false;
            process();
        }
        paint.setColor(PhantomColor.Grey_300);
        paint.setStrokeWidth(2);
        paint.setTextSize(20);
        // X line
        for(int i = 0; i <= maxY / scaleY; i++) {
            int y  = scaleY * (height - topMargin - bottomMargin) / maxY;
            canvas.drawLine(leftMargin, height - bottomMargin - y * i, width - rightMargin, height - bottomMargin - y * i, paint);
            canvas.drawText(""+scaleY*i, leftMargin - 40,  height - bottomMargin - y * i, paint);
        }
        // Y line
        canvas.drawLine(leftMargin, topMargin, leftMargin, height - topMargin, paint);

        if(null != listOfBarViewItem){
            BarViewItem item;
            for (int i = 0; i < listOfBarViewItem.size(); i++) {
                item = listOfBarViewItem.get(i);
                paint.setColor(item.getItemColor());
                canvas.drawRect(leftMargin + firstGap + gap + (gap + itemAllWidth) * i,
                        height - bottomMargin - item.getHeight(),
                        leftMargin + firstGap + gap + (gap + itemAllWidth) * i + itemAllWidth,
                        height - bottomMargin - 4, paint);
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
                        canvas.drawText(item.getTitle(), 0, textLimitCount,
                                leftMargin + firstGap + gap + (gap + itemAllWidth) * i + textLeftMargin,
                                height - bottomMargin - item.getHeight() - 4, paint);
                    else
                        canvas.drawText(item.getTitle(), leftMargin + firstGap + gap + (gap + itemAllWidth) * i + textLeftMargin,
                                height - bottomMargin - item.getHeight() - 4, paint);
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
     * need invoke setItemHeight firstly
     * @param color
     * @return <tt>false</tt> if itemHeight had not be initialized
     */
    public boolean setItemColor(int... color) {
        if(null == itemHeight || itemHeight.length == 0)
            return false;
        itemColor = new int[itemHeight.length];
        if (color.length > itemHeight.length)
            System.arraycopy(color, 0, itemColor, 0, itemHeight.length);
        if (color.length <= itemHeight.length)
            System.arraycopy(color, 0, itemColor, 0, color.length);
        return true;
    }

    /**
     * quickly set items' title
     * need invoke setItemHeight firstly
     * @param title
     * @return <tt>false</tt> if itemHeight had not be initialized
     */
    public boolean setItemTitle(String... title) {
        if(null == itemHeight || itemHeight.length == 0)
            return false;
        itemTitle = new String[itemHeight.length];
        if (title.length > itemHeight.length)
            System.arraycopy(title, 0, itemTitle, 0, itemHeight.length);
        if (title.length <= itemHeight.length) {
            System.arraycopy(title, 0, itemTitle, 0, title.length);
            for(int i = title.length; i < itemHeight.length; i++)
                title[i] = "";
        }
        return true;
    }

    /**
     * quickly set items' title color
     * need invoke setItemHeight firstly
     * @param color
     * @return <tt>false</tt> if itemHeight had not be initialized
     */
    public boolean setItemTitleColor(int... color) {
        if(null == itemHeight || itemHeight.length == 0)
            return false;
        itemTitleColor = new int[itemHeight.length];
        if (color.length > itemHeight.length)
            System.arraycopy(color, 0, itemTitleColor, 0, itemHeight.length);
        if (color.length <= itemHeight.length)
            System.arraycopy(color, 0, itemTitleColor, 0, color.length);
        return true;
    }

    private boolean setItemText(String... text) {
        if(null == itemHeight || itemHeight.length == 0)
            return false;
        itemText = new String[itemHeight.length];
        if (text.length > itemHeight.length)
            System.arraycopy(text, 0, itemText, 0, itemHeight.length);
        if (text.length <= itemHeight.length)
            System.arraycopy(text, 0, itemText, 0, text.length);
        return true;
    }

    private boolean setItemTextSize(int... size) {
        if(null == itemHeight || itemHeight.length == 0)
            return false;
        itemTextSize = new int[itemHeight.length];
        if (size.length > itemHeight.length)
            System.arraycopy(size, 0, itemTextSize, 0, itemHeight.length);
        if (size.length <= itemHeight.length)
            System.arraycopy(size, 0, itemTextSize, 0, size.length);
        return true;
    }

    private boolean setItemTextColor(int... color) {
        if(null == itemHeight || itemHeight.length == 0)
            return false;
        itemTextColor = new int[itemHeight.length];
        if (color.length > itemHeight.length)
            System.arraycopy(color, 0, itemTextColor, 0, itemHeight.length);
        if (color.length <= itemHeight.length)
            System.arraycopy(color, 0, itemTextColor, 0, color.length);
        return true;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    public void setItemAllWidth(int itemAllWidth) {
        this.itemAllWidth = itemAllWidth;
    }

    public void setShowTitle(boolean showTitle) {
        isShowTitle = showTitle;
    }

    public void setTitleTextSize(int size) {
        this.titleSize = size;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public void setScaleY(int scaleY) {
        this.scaleY = scaleY;
    }
}
