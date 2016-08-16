package com.gkk.dropdownlistpopupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

/**
 * 自定义view
 */
public class SpinnerView extends RelativeLayout implements OnClickListener {

    private EditText mEtInput;
    private ImageView mIvArrow;
    private PopupWindow mWindow;
    private ListAdapter mAdapter;
    private OnItemClickListener mListener;
    private ListView mContentView;

    public SpinnerView(Context context) {
        this(context, null);
    }

    public SpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // xml和class 绑定
        View.inflate(context, R.layout.spinner, this);
        mEtInput = (EditText) findViewById(R.id.et_input);//输入框
        mIvArrow = (ImageView) findViewById(R.id.iv_arrow);//输入框中的下拉按钮
        mIvArrow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mIvArrow) {
            clickArrow();
        }
    }

    public void setAdapter(ListAdapter adapter) {
        this.mAdapter = adapter;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    private void clickArrow() {
        // 点击箭头，需要弹出显示list数据的层
        if (mAdapter == null) {
            throw new RuntimeException("请调用setAdapter()去设置数据");
        }
        if (mWindow == null) {
            mContentView = new ListView(getContext());// 显示的view
            // 设置数据
            mContentView.setAdapter(mAdapter);// ---》adapter---》List《数据》
            mContentView.setBackgroundResource(R.drawable.listview_background);
            int width = mEtInput.getWidth(); // popup的宽和高
            int height = 380;
            mWindow = new PopupWindow(mContentView, width, height);
            mWindow.setFocusable(true);// 设置获取焦点
            mWindow.setOutsideTouchable(true);// 设置边缘点击收起
            mWindow.setBackgroundDrawable(new ColorDrawable());
        }
        // 设置item的点击事件
        mContentView.setOnItemClickListener(mListener);
        mWindow.showAsDropDown(mEtInput);//设置popup显示的位置
    }

    public void setText(String data) {
        mEtInput.setText(data);
    }

    public void setSelection(int length) {
        mEtInput.setSelection(length);
    }

    public void dismiss() {
        if (mWindow != null) {
            mWindow.dismiss();
        }
    }
}

