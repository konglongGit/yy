package com.example.mfr.multifunctionrecord;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/13.
 */

public class CityFragment extends Fragment {

    private View parentView;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(R.layout.calendar, container, false);
        listView = (ListView) parentView.findViewById(R.id.listView);
        initCity();
        return parentView;
    }

    private void initCity() {
        final ArrayList<Map<String, Object>> listems = readFromAssets();
        //ArrayList<String>
        if (listems != null) {
            listView = (ListView) parentView.findViewById(R.id.listView);

            SimpleAdapter simplead = new SimpleAdapter(getActivity(), listems,
                    android.R.layout.simple_list_item_1, new String[]{"name"},
                    new int[]{android.R.id.text1});

            listView.setAdapter(simplead);
            //监听标签点击事件
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                   Map<String, Object> map = listems.get(i);
                    String text = (String) map.get("name");

                   // map.get("city") != null || map.get("area") != null
                    if (map.get("city") != null || map.get("area") != null){

                        ArrayList<Object> list = (ArrayList<Object>) map.get("city");
                        if (list != null)
                        {
                            Map<String, Object> map2 = new HashMap<String, Object>();
                            map2.put("city",list);

                            //新建一个显式意图，第一个参数为当前Activity类对象，第二个参数为你要打开的Activity类
                            Intent intent =new Intent(getActivity(),ListActivity.class);

                            //用Bundle携带数据
                            Bundle bundle=new Bundle();

                            SerializableMap tmpmap=new SerializableMap();
                            tmpmap.setMap(map2);
                            bundle.putSerializable("city", tmpmap);
                            bundle.putString("name",text);
                            intent.putExtras(bundle);

                            startActivity(intent);
                        }else {
                            ArrayList<String> arrList = (ArrayList<String>)map.get("area");
                            Intent intent =new Intent(getActivity(),ListActivity.class);
                            //用Bundle携带数据
                            Bundle bundle=new Bundle();

                            bundle.putStringArrayList("area", arrList);
                            bundle.putString("name",text);
                            intent.putExtras(bundle);

                            startActivity(intent);

                        }
                    }else {
                        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
                    }
                }
            });

        }
    }

    static public class SerializableMap implements Serializable {
        private Map<String,Object> map;
        public Map<String,Object> getMap()
        {
            return map;
        }
        public void setMap(Map<String,Object> map)
        {
            this.map=map;
        }
    }

    /**
     * 从assets中读取txt
     */
    ArrayList<Map<String, Object>> readFromAssets() {
        try {
            InputStream is = getActivity().getAssets().open("City.txt");
            if (is != null) {
                String text = readText(is);
                return (ArrayList<Map<String, Object>>)JsonUtil.jsonParse(text);
            }
        } catch (Exception e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 按行读取txt
     *
     * @param is
     * @return
     * @throws Exception
     */
    private String readText(InputStream is) throws Exception {
        InputStreamReader reader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuffer buffer = new StringBuffer("");
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            buffer.append(str);
            buffer.append("\n");
        }
        return buffer.toString();
    }
}
