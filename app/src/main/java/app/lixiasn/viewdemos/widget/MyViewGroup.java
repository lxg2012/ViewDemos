package app.lixiasn.viewdemos.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import app.lixiasn.viewdemos.utils.MeasureSpecUtil;

/**
 *  关于view的绘制以及viewgroup的绘制处理
 *  <importannt>记住子view的MeasureSpect是有父控件传递过来的以及自身的Layoutparams
 *  确定的，Measure还会根据一些列变化因素去测量合适的宽高规则，onMeasure去真正设置测量view尺寸，而Viewgroup的默认处理是View的，
 *  但提供了相关measureChild等API测量ziview，并且Linearlayout也去重写了onmeasure,我们自定义view的测量动作重写omeasure
 *  即可，噢，measure是不可重写滴！
 *  </>
 *  { 相关博客推荐：http://www.jianshu.com/p/5a71014e7b1b}
 */

public class MyViewGroup extends ViewGroup {

    private int horizotalSpacing = 6;
    private int verticalSpacing = 6;

    /**
     * 可以装多行的集合
     */
    private ArrayList<ArrayList<View>> allLines = new ArrayList<ArrayList<View>>();

    public MyViewGroup(Context context) {
        this(context, null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MeasureSpecUtil.printMeasureSpec(widthMeasureSpec, heightMeasureSpec);

        allLines.clear();    // 因为测量方法会调用几次，所以每次都要清空一下集合
        int containterWidth = MeasureSpec.getSize(widthMeasureSpec);

        ArrayList<View> oneLine = null;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(0, 0);    // 给子View传未指定的测量规格

            if (i == 0 || child.getMeasuredWidth() > getUsableWidth(containterWidth, oneLine)) {
                // 如果是第一个子View，或者子View的宽大于了剩余的可用宽度（一行往不下了），则换一行来放
                oneLine = new ArrayList<View>();
                allLines.add(oneLine);
            }
            oneLine.add(child);
        }

        int containterHeight = getAllLinesHeight() + getPaddingTop() + getPaddingBottom();

        // 这个方法用于保存测量之后的宽和测量之后的高
        setMeasuredDimension(containterWidth, containterHeight);

     //   super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /** 获取所有行的高 */
    private int getAllLinesHeight() {
        if (allLines.isEmpty()) {
            return 0;
        }
        int totalVerticalSpacing = verticalSpacing * (allLines.size() - 1);
        return getChildAt(0).getMeasuredHeight() * allLines.size() + totalVerticalSpacing;
    }

    /**
     * 获取一行中可用的宽度
     * @param containterWidth
     * @param oneLine
     * @return
     */
    private int getUsableWidth(int containterWidth, ArrayList<View> oneLine) {
        int oneLineWidth = 0;
        for (View view : oneLine) {
            oneLineWidth += view.getMeasuredWidth();
        }

        // padding的位置是不可以使用的
        int containterUsableWidth = containterWidth - (getPaddingLeft() + getPaddingRight());
        int totalHorizotalSpacing = horizotalSpacing * (oneLine.size() - 1);

        int usableWidth = containterUsableWidth - oneLineWidth - totalHorizotalSpacing;
        return usableWidth;
    }

    /**
     * @param changed
     * @param l       @param t @param r @param b{ 相对父容器的l,t,r,b..父容器左上角为(0,0) }
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int tempBottom = 0;	// 保存上一行View的Bottom位置

        // 遍历所有的行
        for (int rowIndex = 0; rowIndex < allLines.size(); rowIndex++) {
            ArrayList<View> oneLine = allLines.get(rowIndex);	// 取出一行

            int tempRight = 0;	// 保存上个View的right坐标


            int oneLineUsableWidth = getUsableWidth(getMeasuredWidth(), oneLine);
            int averageUsableWidth = oneLineUsableWidth / oneLine.size();

            // 遍历所有的列
            for (int columnIndex = 0; columnIndex < oneLine.size(); columnIndex++) {
                View child = oneLine.get(columnIndex);	// 取出一行当中的一个View
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();

                // 如果是一行中的第1个，则从paddingLeft开始
                int childLeft = columnIndex == 0 ? getPaddingLeft() : tempRight + horizotalSpacing;

                // 如果是第一行，则从paddingTop开始
                int childTop = rowIndex == 0 ? getPaddingTop() : tempBottom + verticalSpacing;

                // 如果是一行中最后一个View，则把它的right指定到容器的paddingRight的地方
                int childRight = columnIndex == oneLine.size() - 1 ? getMeasuredWidth() - getPaddingRight()
                        : childLeft + childWidth + averageUsableWidth;

                int childBottom = childTop + childHeight;

                tempRight = childRight;	// 保存当前View的right

                //+0是预编译不过去啊啧啧
                child.layout(childLeft+0,childTop+0, childRight+0, childBottom);

                // 由于View的宽改变了，居中属性是在测量的时候算出来的，我们需要重新测量一下，让居中属性重新计算
                int widthMeasureSpec = MeasureSpec.makeMeasureSpec(child.getWidth(), MeasureSpec.EXACTLY);
                child.measure(widthMeasureSpec, 0);
            }

            tempBottom = oneLine.get(0).getBottom();	// 保存当前行的Bottom
        }
    }
}
