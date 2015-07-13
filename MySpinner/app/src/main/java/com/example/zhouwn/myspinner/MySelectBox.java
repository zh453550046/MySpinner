package com.example.zhouwn.myspinner;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouwn on 2015/7/7.
 */
public class MySelectBox extends RelativeLayout {

    private TextView tv;
    private PopupWindow popupWindow;
    private OnPopItemClickListener listener;
    private ArrayAdapter<String> adapter;
    private List<String> list = new ArrayList<String>();
    private int popContentItem = -1, titleSize = -1;
    private CharSequence titleText;
    private boolean isInit = false;
    private static final String TAG = MySelectBox.class.getSimpleName();

    public MySelectBox(Context context) {
        super(context);
        init(context);

    }

    public MySelectBox(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init(context);
    }

    public MySelectBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        if (popContentItem != -1) {
            adapter = new ArrayAdapter<String>(context, popContentItem, list);
        } else {
            adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.pop_content, null);
        ListView lv = (ListView) view.findViewById(R.id.lv_pop_content);
        lv.setAdapter(adapter);
        popupWindow = new PopupWindow();
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.onPopItemClick(position);
                    tv.setText(list.get(position));
                    popupWindow.dismiss();
                }
            }
        });
        tv = new TextView(context);
        tv.setPadding(5, 5, 5, 5);
        if (titleSize == -1) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        } else {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, titleSize);
        }
        if (TextUtils.isEmpty(titleText)) {
            tv.setText("请选择");
        } else {
            tv.setText(titleText);
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tv.setLayoutParams(layoutParams);
        tv.setGravity(Gravity.CENTER);
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && !popupWindow.isShowing()) {
                    popupWindow.update();
                    popupWindow.showAsDropDown(tv, 0, 10);
                }

            }
        });
        addView(tv);
        isInit = true;
    }


    /**
     * 设置popwindow显示的内容和布局,布局里需要有一个textview用来显示内容
     * @param list
     * @param popContentItem
     */
    public void setResource(List list, int popContentItem) {
        this.list.clear();
        this.list.addAll(list);
        this.popContentItem = popContentItem;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置popwindow显示的内容,布局默认为android.R.layout.simple_list_item_1
     * @param list
     */
    public void setResource(List list) {
        this.list.clear();
        this.list.addAll(list);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置显示框的文字和文字大小
     * @param text
     * @param size
     */
    public void setTitle(CharSequence text, int size) {
        if (!isInit) {
            this.titleSize = size;
            this.titleText = text;
        } else {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
            tv.setText(text);
        }
    }


    /**
     * 设置popwindow的点击回调事件
     * @param listener
     */
    public void setOnPopItemClickListener(OnPopItemClickListener listener) {
        this.listener = listener;
    }

    interface OnPopItemClickListener {
        void onPopItemClick(int position);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        tv.setWidth(getMeasuredWidth());
        popupWindow.setWidth(getMeasuredWidth());
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
