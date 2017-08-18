package shuyun.PhantomView.BarView;

/**
 * Created by Shuyun on 2017/8/18 0018.
 */
public class BarViewItem {

    private String title;
    private int height;
    private int ItemColor;
    private int titleColor;
    private Object other;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getItemColor() {
        return ItemColor;
    }

    public void setItemColor(int itemColor) {
        ItemColor = itemColor;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public Object getOther() {
        return other;
    }

    public void setOther(Object other) {
        this.other = other;
    }

}
