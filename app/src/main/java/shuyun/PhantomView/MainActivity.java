package shuyun.PhantomView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import shuyun.PhantomView.BarView.BarView;
import shuyun.PhantomView.Utils.PhantomColor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("test", "bv1");
        BarView bv = (BarView) findViewById(R.id.bv_1);
        Log.e("test", "bv2");
        bv.setItemHeight(200, 300, 400, 500, 450, 350, 250, 150);
        bv.setItemColor(PhantomColor.Amber_500, PhantomColor.Blue_500, PhantomColor.BlueGrey_500, 0, PhantomColor.DeepPurple_500);
//        bv.setItemTitleColor(PhantomColor.Orange_500, PhantomColor.DeepPurple_500);
        bv.setItemAllWidth(50);
        bv.setGap(10);
        bv.setMaxY(500);
        bv.setScaleY(100);
        bv.setShowTitle(true);
        Log.e("test", getWindowManager().getDefaultDisplay().getWidth()+":"+getWindowManager().getDefaultDisplay().getHeight());
    }
}
