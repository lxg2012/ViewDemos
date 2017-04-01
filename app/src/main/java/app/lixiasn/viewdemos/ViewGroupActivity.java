package app.lixiasn.viewdemos;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import java.util.ArrayList;

import app.lixiasn.viewdemos.utils.UiUtils;
import app.lixiasn.viewdemos.widget.MyViewGroup;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class ViewGroupActivity extends AppCompatActivity {

    @InjectView(R.id.myviewgroup)
    MyViewGroup myviewgroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group);
        ButterKnife.inject(this);
        ArrayList<String> dataList = new ArrayList<String>();
        String string = getResources().getString(R.string.app_names);
        String[] split = string.split(",");
        for (String value: split) {
            dataList.add(value);
        }
        showData(dataList);
    }

    private void showData(ArrayList<String> dataList) {
        for (String data : dataList) {
            TextView textView = creatRandomColorTextView();
            textView.setText(data);
            myviewgroup.addView(textView);
        }
    }

    private TextView creatRandomColorTextView() {
        TextView textView = new TextView(this);
        textView.setPadding(6, 6, 6, 6);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setBackgroundDrawable(UiUtils.createRandomColorSelector());
        return textView;
    }

}
