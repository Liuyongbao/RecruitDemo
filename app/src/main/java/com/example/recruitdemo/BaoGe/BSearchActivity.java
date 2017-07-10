package com.example.recruitdemo.BaoGe;
/**
 * 搜索界面
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.recruitdemo.R;

public class BSearchActivity extends AppCompatActivity implements View.OnClickListener{

    private Button sSearch;
    private ImageView sfinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bsearch);
        initView();
    }

    private void initView(){
        sSearch=(Button)findViewById(R.id.sSearch);
        sfinish=(ImageView)findViewById(R.id.sfinish);
        sSearch.setOnClickListener(this);
        sfinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sSearch:
                Intent intent=new Intent(this,BSearch_DetailsActivity.class);
                startActivity(intent);
            break;

            case R.id.sfinish:
                finish();
                break;
        }
    }
}
