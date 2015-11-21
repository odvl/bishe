package com.iocm.scrolldatepick;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iocm.scrolldatepick.modle.NameValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liubo on 15/10/16.
 */
public class ScrollDatePick extends LinearLayout {

    private static final String TAG = "ScrollDatePick";

    private List<RecyclerView> mRecyclerViews;
    private List<Integer> mViewIds;
    private int mSize = 2;
    private Context mContext;

    public ScrollDatePick(Context context) {
        this(context, null);
    }

    public ScrollDatePick(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollDatePick(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;
        init();
        initView(context);
        initListener();
    }

    private void init() {
        mRecyclerViews = new ArrayList<>();
        mViewIds = new ArrayList<>();
    }

    private void initListener() {
        int size = mRecyclerViews.size();
        if (size <= 0) {
            return;
        } else if (size == 1) {
            RecyclerView recyclerView = mRecyclerViews.get(0);

        }
    }

    private void initView(Context context) {
        ViewGroup root = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_srcoll_datepick, this, true);

        RecyclerView view1 = (RecyclerView) root.findViewById(R.id.recycler_view1);
        StringBuilder sb = new StringBuilder("R.id.recycler_view");



    }
    int position;

    public void setDatas(List<NameValue>... lists) {
        this.mSize = lists.length;
        for (int i = 0; i < mSize; i++) {
            List<NameValue> list = lists[i];
            position = i;
            RecyclerView recyclerView = mRecyclerViews.get(i);
            ScrollAdapter adapter = new ScrollAdapter();
            adapter.setData(list);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            recyclerView.setAdapter(new ScrollAdapter());

            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    notifyScroll(position);
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                }


            });
        }

    }

    private void notifyScroll(int position) {
        

    }



    public class ScrollAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<NameValue> mDatas;

        public void setData(List<NameValue> nameValue) {
            this.mDatas = nameValue;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scroll_datepick, parent, false);
            return new ScrollViewHolder(root);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ScrollViewHolder vh = (ScrollViewHolder) holder;
            vh.text.setText(mDatas.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        private class ScrollViewHolder extends RecyclerView.ViewHolder {
            public TextView text;

            public ScrollViewHolder(View itemView) {
                super(itemView);
                text = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }
}
