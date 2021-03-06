package com.example.lop.androidnote.lazyload;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.lop.androidnote.R;
import com.example.lop.androidnote.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivitySetMaxLifecycleExam extends BaseActivity {

    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.btn_show_fragment1)
    Button btnShowFragment1;
    @BindView(R.id.btn_show_fragment2)
    Button btnShowFragment2;
    @BindView(R.id.btn_show_fragment3)
    Button btnShowFragment3;

    private List<Fragment> fragmentList = new ArrayList<>();
    private FragmentManager fragmentManager;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private int currentIndex = -1;
    private Fragment fragment;
    private FragmentTransaction transaction;

    private final String fragment1_TAG = "fragment1_TAG";
    private final String fragment2_TAG = "fragment2_TAG";
    private final String fragment3_TAG = "fragment3_TAG";

    private int restoreIndex = -1;

    @Override
    protected void initUI() {
        ButterKnife.bind(this);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_set_max_lifecycle_exam;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }

        if (savedInstanceState != null) {
            Log.w("----___>", "onCreate savedInstanceState!=null");
            fragment1 = (Fragment1) fragmentManager.getFragment(savedInstanceState, fragment1_TAG);
            fragment2 = (Fragment2) fragmentManager.getFragment(savedInstanceState, fragment2_TAG);
            fragment3 = (Fragment3) fragmentManager.getFragment(savedInstanceState, fragment3_TAG);
            restoreIndex = savedInstanceState.getInt("index");
        }
        setFragments();
    }

    /**
     * 当活动异常退出、销毁如横竖屏旋转 就会调用此方法
     * 在这里会保存activity的一些状态信息其中就包括fragment的状态，注意只是状态信息像fragmentlist的size还是为0了
     * 该方法在fragment和activity的onDestroy之前调用
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (fragment1 != null && fragment1.isAdded()) {
            fragmentManager.putFragment(outState, fragment1_TAG, fragment1);
        }
        if (fragment2 != null && fragment2.isAdded()) {
            fragmentManager.putFragment(outState, fragment2_TAG, fragment2);
        }

        if (fragment3 != null && fragment3.isAdded()) {
            fragmentManager.putFragment(outState, fragment3_TAG, fragment3);
        }
        outState.putInt("index", currentIndex);
        super.onSaveInstanceState(outState);

    }

    /**
     * 会在fragment onResume之前调用
     * onCreate和onRestoreInstanceState中的bundle都是一样的 但是activity的onCreate需要判空
     * 由于异常退出activity会被重建 则fragment也会重建而且是默认显示的，但是fragment这些值是空的 根据setFragments方法还会再去创建实例
     * 这样就导致了重叠问题
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.w("----___>onRestore", "savedInstanceState!=null");
    }

    private void setFragments() {
        if (fragment1 == null) {
            fragment1 = Fragment1.newInstance("fragment1");
        }
        if (fragment2 == null) {
            Log.w("----___>", "fragment2 == null");
            fragment2 = Fragment2.newInstance("fragment2");
        }
        if (fragment3 == null) {
            fragment3 = Fragment3.newInstance("fragment3");
        }
        fragmentList.add(fragment1);
        fragmentList.add(fragment2);
        fragmentList.add(fragment3);
        if (restoreIndex == -1) {
            selectedFragment(0);
        } else {
            selectedFragment(restoreIndex);
        }
    }

    /**
     * 一个事务只能提交一次
     *
     * @param index
     */
    private void selectedFragment(int index) {
        if (currentIndex == index) return;
        currentIndex = index;
        transaction = fragmentManager.beginTransaction();
        hideAll(transaction);
        fragment = fragmentList.get(index);
        if (fragment.isAdded()) {
            transaction.show(fragment);
            /**
             * fragment已经add了，使用CREATED会让fragment2再走onPause,onStop,onDestroyView
             * STOPED：fragment2会走到onPause
             * RESUMED:不发生变化
             */
//            if (fragment instanceof Fragment2){
//                transaction.setMaxLifecycle(fragment, Lifecycle.State.CREATED);
//            }

        } else {
            /**
             * fragment第一次add，再调用setMaxLifecycle
             * CREATED:fragment2只走到onPause
             * STOPED:只走到stoped
             * RESUME:同理
             */
            transaction.add(R.id.content, fragment);
//            if (fragment instanceof Fragment2) {
//                transaction.setMaxLifecycle(fragment, Lifecycle.State.RESUMED);
//            }
        }
        transaction.commit();
    }

    /**
     * 隐藏所有fragment
     *
     * @param transaction
     */
    private void hideAll(FragmentTransaction transaction) {
        for (Fragment fragment : fragmentList) {
            if (fragment.isAdded()) {
                transaction.hide(fragment);
            }
        }
    }

    @OnClick({R.id.btn_show_fragment1, R.id.btn_show_fragment2, R.id.btn_show_fragment3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_fragment1:
                selectedFragment(0);
                break;
            case R.id.btn_show_fragment2:
                selectedFragment(1);
                break;
            case R.id.btn_show_fragment3:
                selectedFragment(2);
                break;
        }
    }

    /**
     * Honeycomb之前的版本，activity被认为在pause之前都不会被杀掉，这意味着onSaveInstanceState()会在onPause()之前被调用
     * 从HoneyComb开始，Activity被认为只会在stopped才会被杀掉，意味着onSaveInstanceState()现在会在onStop()之前被调用而不是在onPause()之前
     */
    @Override
    protected void onStop() {
        super.onStop();
        /**
         * 加上这句话在内存不足activity被回收的时候(也可以去开发者选项打开不保留活动)
         * 还是会出现fragment重叠的问题，因为在onSaveInstanceState调用之后fragment又进行了一次事务提交且用的是commitAllowingStateLoss
         * 在onSaveInstanceState后调用commitAllowingStateLoss会丢失提交的页面状态信息，通过日志也可以看到fragment2为空
         * 而用commit的话会崩 出现Can not perform this action after onSaveInstanceState错误
         * 这也是这两种提交事务的区别：commit会对状态进行检测如果已经保存了就抛出异常；而commitAllowingStateLoss方法不进行状态检测，因此不会抛出异常
         *
         * 所以除非状态丢失不可避免可以不用commitAllowingStateLoss
         * 应该尽早提交事务，且不应该在异步操作中执行事务提交
         */
//        selectedFragment(1);
    }
}
