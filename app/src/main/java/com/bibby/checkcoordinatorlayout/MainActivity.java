package com.bibby.checkcoordinatorlayout;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager mViewPager;
    private ArrayList<String> myDataset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }
    private void initData(){
        myDataset = new ArrayList<>();
        for(int i = 0; i < 15; i++){
            myDataset.add(i + "");
        }
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());
    }

    private class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = new View(MainActivity.this);
            switch(position){
                case 0:
                    view = getLayoutInflater().inflate(R.layout.pager_item1,
                            container, false);
                    MyAdapter myAdapter = new MyAdapter(myDataset);
                    RecyclerView mList = (RecyclerView) view.findViewById(R.id.list_view);
                    final LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    mList.setLayoutManager(layoutManager);
                    mList.setAdapter(myAdapter);
                    break;
                case 1:
                    view = getLayoutInflater().inflate(R.layout.pager_item2,
                            container, false);

                    container.addView(view);

                    WebView web = (WebView) view.findViewById(R.id.web);

                    web.getSettings().setJavaScriptEnabled(true);
                    web.loadUrl("https://m.mobile01.com/");

                    final SwipeRefreshLayout laySwipe = (SwipeRefreshLayout) view.findViewById(R.id.laySwipe);

                    laySwipe.setColorSchemeResources(
                            android.R.color.holo_red_light,
                            android.R.color.holo_blue_light,
                            android.R.color.holo_green_light,
                            android.R.color.holo_orange_light);

                    SwipeRefreshLayout.OnRefreshListener onSwipeToRefresh = new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            laySwipe.setRefreshing(true);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    laySwipe.setRefreshing(false);
                                    Toast.makeText(getApplicationContext(), "Refresh done!", Toast.LENGTH_SHORT).show();
                                }
                            }, 3000);
                        }
                    };

                    laySwipe.setOnRefreshListener(onSwipeToRefresh);
            }
            container.addView(view);
            return view;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<String> mData;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.info_text);
            }
        }

        public MyAdapter(List<String> data) {
            mData = data;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_view_item, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTextView.setText(mData.get(position));

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
