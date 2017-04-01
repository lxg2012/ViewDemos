package app.lixiasn.viewdemos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.viewgroup)
    Button viewgroup;
    @InjectView(R.id.attrsview)
    Button attrsview;
    @InjectView(R.id.listeview)
    Button listeview;

    private Intent mIntent  = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.attrsview)
    public void showaAttaview(){

    }

    @OnClick(R.id.viewgroup)
    public void showaViewgroup(){
        mIntent.setClass(MainActivity.this,ViewGroupActivity.class);
        startActivity(mIntent);
    }

    @OnClick(R.id.listeview)
    public void showaListeview(){

    }

    @OnClick(R.id.waveview)
    public void showaWaveview(){
        mIntent.setClass(MainActivity.this,WaveViewActivity.class);
        startActivity(mIntent);
    }
}
