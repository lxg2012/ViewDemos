package app.lixiasn.viewdemos.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * 封装Ui相关的操作
 */
public class UiUtils {


    /**
     * 创建一个拥有随机颜色选择器的TextView
     */
    public static TextView createRandomColorTextView(Context context) {
        TextView tv = new TextView(context);
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.WHITE);
        int padding = 6;
        tv.setPadding(padding, padding, padding, padding);
        tv.setBackgroundDrawable(createRandomColorSelector());
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), ((TextView) v).getText(),Toast.LENGTH_SHORT).show();
            }
        });
        return tv;
    }

    /**
     * 创建一个拥有随机颜色选择器
     */
    public static Drawable createRandomColorSelector() {
        StateListDrawable stateListDrawable = new StateListDrawable();

        // 创建按下状态、正常状态
        int[] pressState = {android.R.attr.state_pressed, android.R.attr.state_enabled};
        int[] normalState = {};

        // 创建按下状态的要显示的Drawable
        Drawable pressDrawable = createRandomColorDrawable();
        Drawable normalDrawable = createRandomColorDrawable();


        stateListDrawable.addState(pressState, pressDrawable);    // 设置按下状态显示对应的Drawable
        stateListDrawable.addState(normalState, normalDrawable);// 设置正常状态显示对应的Drawable

        return stateListDrawable;
    }

    /**
     * 创建一个拥有随机颜色的圆角Drawable
     */
    public static Drawable createRandomColorDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(5);
        drawable.setColor(createRandomColor());
        return drawable;
    }

    /**
     * 创建一个随机颜色
     */
    public static int createRandomColor() {
        Random random = new Random();
        int red = 50 + random.nextInt(151);        // 返回50 ~ 200
        int green = 50 + random.nextInt(151);    // 返回50 ~ 200
        int blue = 50 + random.nextInt(151);    // 返回50 ~ 200
        int color = Color.rgb(red, green, blue);
        return color;
    }

}














