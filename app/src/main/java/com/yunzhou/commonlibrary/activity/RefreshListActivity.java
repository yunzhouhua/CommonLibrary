package com.yunzhou.commonlibrary.activity;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.yunzhou.commonlibrary.R;


public class RefreshListActivity extends AppCompatActivity {

    private static final String TAG = "RefreshListActivity";

    private RecyclerView mRecyclerView = null;
    private TwinklingRefreshLayout mTwinkLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mTwinkLayout = (TwinklingRefreshLayout) findViewById(R.id.twinklingRefreshLayout);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new MyAdapter(this));

        mTwinkLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                Log.e(TAG, "onRefresh");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTwinkLayout.finishRefreshing();
                    }
                }, 5000);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                Log.e(TAG, "onLoadMore");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTwinkLayout.finishLoadmore();
                        mTwinkLayout.setEnableLoadmore(false);
                    }
                }, 5000);
            }

            @Override
            public void onFinishRefresh() {
                Log.e(TAG, "onFinishRefresh");
            }

            @Override
            public void onFinishLoadMore() {
                Log.e(TAG, "onFinishLoadMore");
            }

            @Override
            public void onRefreshCanceled() {
                Log.e(TAG, "onRefreshCanceled");
            }

            @Override
            public void onLoadmoreCanceled() {
                Log.e(TAG, "onLoadmoreCanceled");
            }
        });

    }

    public class MyAdapter extends RecyclerView.Adapter{

        private Context mContext;
        private LayoutInflater mInflater;

        public MyAdapter(Context context){
            this.mContext = context;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_refresh, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 20;
        }

        private final class MyViewHolder extends RecyclerView.ViewHolder{

            public MyViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
