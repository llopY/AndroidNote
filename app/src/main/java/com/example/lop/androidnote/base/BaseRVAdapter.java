package com.example.lop.androidnote.base;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.lop.androidnote.R;

import org.jetbrains.annotations.NotNull;

/**
 * @author: lop
 * @createTime: 2020-05-15
 * @desc:
 */
public class BaseRVAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public BaseRVAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, String bean) {
        helper.setText(R.id.tv_content,bean);
    }
}
