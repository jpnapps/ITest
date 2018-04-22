package com.jpnapps.itest;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.jpnapps.itest.adapter.AdapterRcvItem;
import com.jpnapps.itest.aquery_network.NetworkApi;
import com.jpnapps.itest.model.MItem;
import com.jpnapps.itest.network.GsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Mani-Ceino on 4/22/2018.
 */

public class HomeActivity  extends AppCompatActivity{

	private RecyclerView listRcv;
	AdapterRcvItem adapterRcvItem;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		listRcv = (RecyclerView) findViewById(R.id.list_rcv);
		LinearLayoutManager mLayoutManager;
		mLayoutManager = new LinearLayoutManager(HomeActivity.this);
		listRcv.setLayoutManager(mLayoutManager);
		AjaxCallback<JSONArray> cb = new AjaxCallback<JSONArray>(){
			@Override
			public void callback(String url, JSONArray json, AjaxStatus status) {
				try {
					ArrayList<MItem> itemlist=GsonUtils.getInstance().gsonToMItems(json);
					adapterRcvItem = new AdapterRcvItem(HomeActivity.this,itemlist );
					listRcv.setAdapter(adapterRcvItem);
					status.getMessage();
				}catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		};
		NetworkApi.getInstance().getBaseArrayWeb(HomeActivity.this,"https://s3.amazonaws.com/ceinodemo/data.json",cb);
	}
}
