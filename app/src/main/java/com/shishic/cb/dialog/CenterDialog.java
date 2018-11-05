package com.shishic.cb.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.shishic.cb.R;
import com.shishic.cb.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class CenterDialog {
    private Context context;
    private Dialog dialog;
    private TextView txt_title;
    private boolean showTitle = false;
    private List<SheetItem> sheetItemList;
    private Display display;
    private FrameLayout mLayoutContent;
    private OnTouchOutsideListener mOnTouchOutsideListener;

    public CenterDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public CenterDialog builder(String isFrom, int themeResId) {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_bottomdialog, null);

        // 设置Dialog最小宽度为屏幕宽度
//        view.setMinimumWidth(display.getWidth());

        // 获取自定义Dialog布局中的控件
        mLayoutContent = (FrameLayout) view.findViewById(R.id.lLayout_content);
        txt_title = (TextView) view.findViewById(R.id.txt_title);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, themeResId);
        dialog.setContentView(view);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mOnTouchOutsideListener != null) {
                    mOnTouchOutsideListener.onTouchOutside();
                }
            }
        });
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);

        dialogWindow.setDimAmount(0.7f);// 修改背景透明度为0.7
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        if (isFrom.equals("isFromClearCache")) {
            lp.width = DensityUtils.dipTopx(context, 270);
        }
        if (isFrom.equals("isFromQuitLogin")) {
            lp.width = DensityUtils.dipTopx(context, 270);
        }
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        return this;
    }

    public CenterDialog setTitle(String title) {
        showTitle = true;
        txt_title.setVisibility(View.VISIBLE);
        txt_title.setText(title);
        return this;
    }

    public CenterDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public CenterDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void setOnTouchOutsideListener(OnTouchOutsideListener outSideClick) {
        mOnTouchOutsideListener = outSideClick;
    }


    public interface OnTouchOutsideListener {
        void onTouchOutside();
    }

    /**
     * @param strItem  条目名称
     * @param color    条目字体颜色，设置null则默认蓝色
     * @param listener
     * @return
     */
    public CenterDialog addSheetItem(String strItem, SheetItemColor color,
                                     OnSheetItemClickListener listener) {
        if (sheetItemList == null) {
            sheetItemList = new ArrayList<SheetItem>();
        }
        sheetItemList.add(new SheetItem(strItem, color, listener));
        return this;
    }

    public void show() {
        // setSheetItems();
        dialog.show();
    }

    public CenterDialog setView(View v) {
        v.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        mLayoutContent.addView(v);
        return this;
    }

    public View getView() {
        return mLayoutContent;
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public enum SheetItemColor {
        Blue("#037BFF"), Red("#FD4A2E");

        private String name;

        SheetItemColor(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public interface OnSheetItemClickListener {
        void onClick(int which);
    }

    public class SheetItem {
        String name;
        OnSheetItemClickListener itemClickListener;
        SheetItemColor color;

        public SheetItem(String name, SheetItemColor color,
                         OnSheetItemClickListener itemClickListener) {
            this.name = name;
            this.color = color;
            this.itemClickListener = itemClickListener;
        }
    }
}
