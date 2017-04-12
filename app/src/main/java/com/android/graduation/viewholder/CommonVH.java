package com.android.graduation.viewholder;

import android.view.View;
import android.widget.TextView;

import com.android.graduation.R;
import com.android.graduation.view.ToggleView;

import butterknife.BindView;
import butterknife.ButterKnife;

/*public class CommonVH{
        @BindView(R.id.tv_common_name)
        public TextView name;
        @BindView(R.id.tv_common_desc)
        public TextView desc;
        @BindView(R.id.view_common_toggle)
        public ToggleView toggleView;

        public CommonVH(View view) {
            ButterKnife.bind(view);
        }
    }*/

public class CommonVH{
    //        @BindView(R.id.tv_common_name)
    public TextView name;
    //        @BindView(R.id.tv_common_desc)
    public TextView desc;
    //        @BindView(R.id.view_common_toggle)
    public ToggleView toggleView;

    public CommonVH(View view) {
//            ButterKnife.bind(view);
        name = (TextView) view.findViewById(R.id.tv_common_name);
        desc = (TextView) view.findViewById(R.id.tv_common_desc);
        toggleView = (ToggleView) view.findViewById(R.id.view_common_toggle);
    }
}
