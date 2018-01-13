package com.example.mfr.multifunctionrecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListActivity extends AppCompatActivity {

    ListView listView;
    TextView textView;
    ArrayList<Map<String,Object>> listems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            CityFragment.SerializableMap serializableMap = (CityFragment.SerializableMap) bundle
                    .get("city");
            if (serializableMap != null) {
                Map<String, Object> map = serializableMap.getMap();
                listems = (ArrayList<Map<String, Object>>) map.get("city");
            } else {
                ArrayList<String> listArea = bundle.getStringArrayList("area");
                listems = new ArrayList<>();
                for (int i = 0; i < listArea.size(); i++) {
                    Map<String, Object> submap = new HashMap<String, Object>();
                    submap.put("name", listArea.get(i));
                    listems.add(submap);
                }
            }
            initCity();

            textView = (TextView) findViewById(R.id.list_title);
            textView.setText(bundle.getString("name"));
        }
    }


    private void initCity() {

        //ArrayList<String>
        if (listems != null) {
            listView = (ListView)findViewById(R.id.List_listview);

            SimpleAdapter simplead = new SimpleAdapter(ListActivity.this, listems,
                    android.R.layout.simple_list_item_1, new String[]{"name"},
                    new int[]{android.R.id.text1});

            listView.setAdapter(simplead);
            //监听标签点击事件
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Map<String, Object> map = listems.get(i);
                    String text = (String) map.get("name");

                    if (map.get("city") != null || map.get("area") != null){

                        ArrayList<Object> list = (ArrayList<Object>) map.get("city");
                        if (list != null)
                        {
                            Map<String, Object> map2 = new HashMap<String, Object>();
                            map2.put("city",list);

                            //新建一个显式意图，第一个参数为当前Activity类对象，第二个参数为你要打开的Activity类
                            Intent intent =new Intent(ListActivity.this,ListActivity.class);

                            //用Bundle携带数据
                            Bundle bundle=new Bundle();

                            CityFragment.SerializableMap tmpmap=new CityFragment.SerializableMap();
                            tmpmap.setMap(map2);
                            bundle.putSerializable("city", tmpmap);
                            bundle.putString("name",text);
                            intent.putExtras(bundle);

                            startActivity(intent);
                        }else {
                            ArrayList<String> arrList = (ArrayList<String>)map.get("area");
                            Intent intent =new Intent(ListActivity.this,ListActivity.class);
                            //用Bundle携带数据
                            Bundle bundle=new Bundle();
                            bundle.putString("name",text);
                            bundle.putStringArrayList("area", arrList);

                            intent.putExtras(bundle);

                            startActivity(intent);

                        }
                    }else {
                        Toast.makeText(ListActivity.this, text, Toast.LENGTH_LONG).show();
                    }

                }
            });

        }
    }

}
