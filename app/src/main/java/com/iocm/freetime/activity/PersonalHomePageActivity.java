package com.iocm.freetime.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iocm.administrator.freetime.R;
import com.iocm.freetime.bean.Tasks;

import java.util.ArrayList;

/**
 * Created by liubo on 15/12/5.
 */
public class PersonalHomePageActivity extends BaseActivity {

    private static final String TAG = "PersonalHomePageActivity";

    private RecyclerView taskRecyclerView;

    private ArrayList<Tasks> list;

    private ImageView back;


    @Override
    void initView() {
        setContentView(R.layout.activity_personal_homepage);

        taskRecyclerView = (RecyclerView) findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        back = (ImageView) findViewById(R.id.back);


    }

    @Override
    void initListener() {

        back.setOnClickListener(this);

    }

    @Override
    void loadData() {
        list = new ArrayList<>();
        Tasks tasks = new Tasks("title", "body", "2015-11-21", "2015-11-1", "12345664", "name", null, "msg", 11.11,22.1,"build");
        for (int i = 0 ; i< 10 ;i++)
            list.add(tasks);
        taskRecyclerView.setAdapter(new PersonalHomePageAdapter());

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id  == R.id.back) {
            onBackPressed();
        }

    }

    @Override
    public void leftClickListener() {

    }

    @Override
    public void rightClickListener() {

    }

    class PersonalHomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recnetly_released, null);
            return new PersonalHomePageViewHolder(root);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            PersonalHomePageViewHolder vh = (PersonalHomePageViewHolder) holder;
            Tasks tasks = list.get(position);

            vh.titleTextView.setText(tasks.getTitle());
            vh.bodyTextView.setText(tasks.getBody());
            vh.endTimeTextView.setText(tasks.getEndTime());
            vh.joinedNumTextView.setText(String.format(getResources().getString(R.string.joined_person_num), tasks.getJoinedNum()));


        }

        @Override
        public int getItemCount() {
            return null == list ? 0 : list.size();
        }

        class PersonalHomePageViewHolder extends RecyclerView.ViewHolder {

            public TextView titleTextView;
            public TextView bodyTextView;
            public TextView endTimeTextView;
            public TextView joinedNumTextView;

            public PersonalHomePageViewHolder(View itemView) {
                super(itemView);

                titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
                bodyTextView = (TextView) itemView.findViewById(R.id.bodyTextView);
                endTimeTextView = (TextView) itemView.findViewById(R.id.endTimeTextView);
                joinedNumTextView = (TextView) itemView.findViewById(R.id.joinedNumTextView);
            }
        }
    }


}
