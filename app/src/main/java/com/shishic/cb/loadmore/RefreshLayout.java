package com.shishic.cb.loadmore;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.headerfooter.songhang.library.SmartRecyclerAdapter;
import com.shishic.cb.R;
import com.shishic.cb.util.LogUtil;
import com.shishic.cb.util.ToastUtils;
import com.shishic.cb.view.NfProgressBar;
import java.util.ArrayList;
import java.util.List;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 参考 https://github.com/fengjingyu/Android-Utils
 *
 * @description 封装了上下拉 ， 分页 ，无数据背景
 */
abstract public class RefreshLayout extends FrameLayout {
    /**
     * 上下拉效果的控件
     */
    //  protected PtrFrameLayout mPtrRefreshLayout; // 下拉
    protected PtrNFClassicFrameLayout mPtrRefreshLayout; // 下拉
    protected PlusRecyclerView recyclerView; // 可以监听滑动到底部
    protected PlusRecyclerAdapter adapter; // 添加了getList方法
    protected SmartRecyclerAdapter smartAdapter; // 可以添加headview footview
    /**
     * 上拉加载的footview
     */
    protected LinearLayout loadingLayout;
    private TextView loadingTextview;
    private NfProgressBar loadingProgressBar;
    /**
     * 一进入页面是否自动加载
     */
    private boolean autoRefresh;
    protected IRefreshHandler mRefreshHandler;
    /**
     * 数据为0的背景
     */
    public LinearLayout dataZeroLayout;
    /**
     * 当前页
     */
    public int currentPage = 1;
    /**
     * 总页数
     */
    public int totalPage = -1;
    /**
     * 是否正在刷新(下拉和上拉)
     */
    public boolean isRequesting;
    /**
     * 数据为0时，背景上显示的图片
     */
    public ImageView dataZeroImageView;
    /**
     * 数据为0时，背景上显示的文字
     */
    public TextView dataZeroTextView;
    /**
     * 数据为0时，背景上显示的文字
     */
    public TextView dataZeroTextView2;
    /**
     * 数据为0时的按钮
     */
    public Button dataZeroButton;
    /**
     * 0数据显示的文本信息
     */
    public String dataZeroHint = "";
    public String dataZerohint2 = "";
    public String dataZeroButtonHint = "";
    //为空时顶部视图
    public LinearLayout emptyTopView;

    /**
     * 0数据图片id
     */
    public int dataZeroImageId;
    /**
     * 记录上一次请求是否成功
     */
    boolean lastRequestResult;

    private boolean isShowLastTips = true;

    /**
     * 是否显示toast,默认显示
     */
    private boolean isShowLastTipsToast = true;

    private boolean isSearchStyleShowlastTips = false;


    private boolean isNeedTransverseSliding;    //是否需要横向滑动
    private int mDownX;
    private int mDownY;
    private String showAllContent;

    public RefreshLayout(Context context) {
        super(context);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context, attrs);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context, attrs);
    }

    public void initLayout(Context context, AttributeSet attrs) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initLoadingView();
//
//        // footview 一开始的时候不显示底部栏
//        loadingLayout = (LinearLayout) mInflater.inflate(R.layout.list_item_footer_view_text, null);
//        loadingTextview = (TextView) loadingLayout.findViewById(R.id.footer_view_title);
//        loadingProgressBar = (NfProgressBar) loadingLayout.findViewById(R.id.footer_view_progress);
//        loadingLayout.setVisibility(View.GONE);
        // 下拉刷新的view
        mInflater.inflate(R.layout.view_nf_refresh, this, true);
        mPtrRefreshLayout = findViewById(R.id.ptrRefreshLayout);
        mPtrRefreshLayout.disableWhenHorizontalMove(true);
        recyclerView = findViewById(R.id.recyclerView);

        dataZeroLayout = findViewById(R.id.dataZeroLayout);

        initRefreshLayoutParams(attrs);

        initHeadStyle();

        checkAutoRefresh(context, attrs);

        initZeroDataLayout();
        showAllContent = "已显示全部内容";
    }

    private void initLoadingView() {
        if (isSearchStyleShowlastTips) {
            LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // footview 一开始的时候不显示底部栏
            loadingLayout = (LinearLayout) mInflater.inflate(R.layout.list_item_footer_view_search_style, null);
            loadingTextview = loadingLayout.findViewById(R.id.footer_view_title);
            loadingProgressBar = loadingLayout.findViewById(R.id.footer_view_progress);
            loadingLayout.setVisibility(View.GONE);
        } else {
            LayoutInflater mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // footview 一开始的时候不显示底部栏
            loadingLayout = (LinearLayout) mInflater.inflate(R.layout.list_item_footer_view_text, null);
            loadingTextview = loadingLayout.findViewById(R.id.footer_view_title);
            loadingProgressBar = loadingLayout.findViewById(R.id.footer_view_progress);
            loadingLayout.setVisibility(View.GONE);
        }

    }

    private void initZeroDataLayout() {
        dataZeroImageView = dataZeroLayout.findViewById(R.id.dataZeroImageView);
        dataZeroTextView = dataZeroLayout.findViewById(R.id.dataZeroTextView);
        dataZeroTextView2 = dataZeroLayout.findViewById(R.id.dataZeroTextView2);
        dataZeroButton = dataZeroLayout.findViewById(R.id.dataZeroButton);
        emptyTopView = dataZeroLayout.findViewById(R.id.empty_top_view);
    }

    public void setHandler(IRefreshHandler refreshHandler) {
        this.mRefreshHandler = refreshHandler;
        registerRefreshHandler();
        registerLoadHandler();
    }

    public void registerLoadHandler() {
        if (mRefreshHandler.canLoad()) {
            recyclerView.setOnBottomListener(new PlusRecyclerView.OnBottomListener() {
                @Override
                public void onBottom() {
                    checkAndLoad();
                }
            });
        }
    }

    public boolean isShowLastTips() {
        return isShowLastTips;
    }

    public void setShowLastTips(boolean showLastTips) {
        isShowLastTips = showLastTips;
    }

    public boolean isShowLastTipsToast() {
        return isShowLastTipsToast;
    }

    public void setShowLastTipsToast(boolean showLastTipsToast) {
        isShowLastTipsToast = showLastTipsToast;
    }

    public boolean isSearchStyleShowlastTips() {
        return isSearchStyleShowlastTips;
    }

    public void setSearchStyleShowlastTips(boolean searchStyleShowlastTips) {
        isSearchStyleShowlastTips = searchStyleShowlastTips;
    }

    public void checkAndLoad() {
        try {
            if (totalPage > 1 && !isRequesting) { // 必须有多页 && 必须是之前的请求已返回后
                if (currentPage > 1 || currentPage == 1 && lastRequestResult) { // 不是第一页 或者 是第一页但必须第一页刷新成功后
                    if (currentPage < totalPage || (currentPage == totalPage && !lastRequestResult)) {//不是最后一页 或者 是最后一页但是最后一页访问失败了
                        loading();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerRefreshHandler() {
        // 由于该库提供的只有下拉刷新的handler，没有上拉加载的监听，所以另外得实现上拉的逻辑(在setHandler（）方法里)
        // 试过了，如果不设置ptrhandler，也可以下拉，所以得在checkCanDoRefresh里控制是否可以下拉
        mPtrRefreshLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                // 这个refreshing()只有在checkCanDoRefresh()返回为true时才会调用
                refreshing();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if (mRefreshHandler.canRefresh()) {
                    /**
                     * 如果是可下拉刷新的listview，这句必须调用，否则下拉与listview滑动会有冲突
                     */
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                } else {
                    return false;
                }
            }
        });
    }

    /**
     * 该控件下有一个autoRefresh的属性 ，用于控制一进入页面是否自动刷新
     * <p/>
     * autoRefresh：true:一进入页面就自动刷新
     * autoRefresh：false：不会自动刷新
     */
    private void checkAutoRefresh(Context context, AttributeSet attrs) {
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.RefreshLayout, 0, 0);
        if (arr != null) {
            autoRefresh = arr.getBoolean(R.styleable.RefreshLayout_autorefresh, false);
        }

        if (autoRefresh) {
            autoRefresh();
        }
    }

    public void autoRefresh() {
        autoRefresh(150);
    }

    public void autoRefresh(int time) {
        mPtrRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrRefreshLayout.autoRefresh();
            }
        }, time);
    }

    /**
     * 这里没用xml ， 如果是xml则默认的配置
     * xmlns:cube_ptr="http://schemas.android.com/apk/res-auto"
     * cube_ptr:ptr_duration_to_close="200"
     * cube_ptr:ptr_duration_to_close_header="1000"
     * cube_ptr:ptr_ratio_of_header_height_to_refresh="1.2"
     * cube_ptr:ptr_resistance="1.7"
     * cube_ptr:ptr_keep_header_when_refresh="true"
     * cube_ptr:ptr_pull_to_fresh="false"
     */
    public void initRefreshLayoutParams(AttributeSet attributeSet) {
        // 默认的设置
        mPtrRefreshLayout.setResistance(1.7f);
        mPtrRefreshLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrRefreshLayout.setDurationToClose(200);
        mPtrRefreshLayout.setDurationToCloseHeader(1000);
        /*
         * true为拉的时候就刷新， false为拉了之后且释放时才刷新
         */
        mPtrRefreshLayout.setPullToRefresh(false);
        /*
         * 刷新的过程中，是否显示head
         */
        mPtrRefreshLayout.setVisibility(VISIBLE);
        mPtrRefreshLayout.setKeepHeaderWhenRefresh(true);
        mPtrRefreshLayout.setLoadingMinTime(0);
//        TypedArray arr = getContext().obtainStyledAttributes(attributeSet, R.styleable.PtrFrameLayout, 0, 0);
//        if (arr != null) {
//            int cube_ptr_ptr_duration_to_close = arr.getInteger(R.styleable.PtrFrameLayout_ptr_duration_to_close, 200);
//            int ptr_duration_to_close_header = arr.getInteger(R.styleable.PtrFrameLayout_ptr_duration_to_close_header, 1000);
//            float ptr_ratio_of_header_height_to_refresh = arr.getFloat(R.styleable.PtrFrameLayout_ptr_keep_header_when_refresh, 1.8f);
//            mPtrRefreshLayout.setRatioOfHeaderHeightToRefresh(ptr_ratio_of_header_height_to_refresh);
//            mPtrRefreshLayout.setDurationToClose(cube_ptr_ptr_duration_to_close);
//            mPtrRefreshLayout.setDurationToCloseHeader(ptr_duration_to_close_header);
//        arr.recycle();
//        }
    }

    public abstract void initHeadStyle();

    /**
     * 下拉刷新
     */
    protected void refreshing() {
        if (!isRequesting) {
            isRequesting = true;
            hiddenLoadingView();
            currentPage = 1;
            if (mRefreshHandler != null) {
                mRefreshHandler.refresh(currentPage);
            } else {
                throw new RuntimeException(this + "回调handler为null");
            }
        }
    }

    /**
     * 上拉加载
     */
    protected void loading() {
        if (!isRequesting) {
            isRequesting = true;
            showLoadingView();
            if (lastRequestResult) {
                currentPage = currentPage + 1;
            }
            if (mRefreshHandler != null) {
                mRefreshHandler.load(currentPage);
            } else {
                throw new RuntimeException(this + "回调handler为null");
            }
        }
    }

    public void showLoadingView() {
        loadingLayout.setVisibility(View.VISIBLE);
        loadingTextview.setText(getResources().getString(R.string.newslist_more_loading_text));
        loadingTextview.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.VISIBLE);
    }

    public void hiddenLoadingView() {
        loadingLayout.setVisibility(View.GONE);
    }

    /**
     * 定制成 最后一页的时候不显示加载更多底部view
     */
    public void showNoNextPage() {
        if (isSearchStyleShowlastTips) {
            loadingLayout.setVisibility(View.VISIBLE);
            loadingTextview.setText(showAllContent);
            loadingTextview.setVisibility(View.VISIBLE);
            loadingProgressBar.setVisibility(View.GONE);
            loadingTextview.setTextColor(getResources().getColor(R.color.c_gray_999999));
            loadingLayout.setBackgroundColor(getResources().getColor(R.color.c_gray_f2f2f2));
        } else {
            if (totalPage > 1) {
                loadingLayout.setVisibility(View.VISIBLE);
                if (isShowLastTips) {
                    if (isShowLastTipsToast) {
                        ToastUtils.toastShow(getContext(), R.string.newslist_no_more_loading_text);
                    }
                    loadingTextview.setText(showAllContent);
                    loadingTextview.setVisibility(View.VISIBLE);
                } else {
                    loadingTextview.setVisibility(View.GONE);
                }
                loadingProgressBar.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 刷新完成,需要外部调用
     */
    public void completeRefresh(boolean requestSuccess) {
        this.lastRequestResult = requestSuccess;

        if (requestSuccess) {
            if (adapter.getList().isEmpty()) {
                if (dataZeroImageId <= 0) {
                    dataZeroImageView.setVisibility(View.GONE);
                } else {
                    dataZeroImageView.setImageResource(dataZeroImageId);
                    dataZeroImageView.setVisibility(View.VISIBLE);
                }

                if (TextUtils.isEmpty(dataZeroHint)) {
                    dataZeroTextView.setVisibility(View.GONE);
                } else {
                    dataZeroTextView.setText(dataZeroHint);
                    dataZeroTextView.setVisibility(View.VISIBLE);
                }

                if (TextUtils.isEmpty(dataZerohint2)) {
                    dataZeroTextView2.setVisibility(View.GONE);
                } else {
                    dataZeroTextView2.setText(dataZerohint2);
                    dataZeroTextView2.setVisibility(View.VISIBLE);
                }

                if (TextUtils.isEmpty(dataZeroButtonHint)) {
                    dataZeroButton.setVisibility(View.GONE);
                } else {
                    dataZeroButton.setText(dataZeroButtonHint);
                    dataZeroButton.setVisibility(View.VISIBLE);
                }

                dataZeroLayout.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                mPtrRefreshLayout.setVisibility(View.INVISIBLE);

                hiddenLoadingView();
            } else {
                dataZeroLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                mPtrRefreshLayout.setVisibility(View.VISIBLE);
                if (currentPage >= totalPage) {
                    if (adapter.getList().size() >= 1) {
                        showNoNextPage();
                    } else {
                        //未满一页
                        hiddenLoadingView();
                    }
                } else {
                    // 不是最后一页
                    hiddenLoadingView();
                }
            }
        } else {
            // 比如刚打开页面访问失败,这时可以刷新,虽然数据是0,但不显示0背景页面
            // 比如第一页加载成功,第二页失败了,这时不显示0背景页面
            hiddenLoadingView();
        }

        mPtrRefreshLayout.refreshComplete();
        isRequesting = false;
    }

    /**
     * 一定要处理页码的，不能整除的要取上限
     *
     * @param totalPage
     */
    public void setTotalPage(int totalPage) {
        if (totalPage < 1) {
            totalPage = 1;
        }
        this.totalPage = totalPage;
    }

    /**
     * 如果是第一页则需要清空集合
     */
    protected void clearWhenPageOne() {
        if (currentPage == 1) {
            adapter.getList().clear();
        }
    }

    protected synchronized void updateList(boolean append, List list, PlusRecyclerAdapter adapter) {
        if (list == null) {
            list = new ArrayList();
        }
        LogUtil.e("追加：", append);

        synchronized (adapter) {
            if (!append) {
                adapter.getList().clear();
            } else {
                clearWhenPageOne();
            }

            adapter.getList().addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

    public void updateAdd(List list) {
        updateList(true, list, adapter);
    }

    public void updateClearAndAdd(List list) {
        updateList(false, list, adapter);
    }

    public Button getDataZeroButton() {
        return dataZeroButton;
    }

    /**
     * 设置数据为零时候的背景
     */
    public void setDataZeroHint(String dataZeroHint, String dataZerohint2, int dataZeroImageId, String dataZeroButtonHint) {
        if (dataZeroHint == null) {
            this.dataZeroHint = "";
        } else {
            this.dataZeroHint = dataZeroHint;
        }

        if (dataZerohint2 == null) {
            this.dataZerohint2 = "";
        } else {
            this.dataZerohint2 = dataZerohint2;
        }

        this.dataZeroImageId = dataZeroImageId;
        this.dataZeroButtonHint = dataZeroButtonHint;
    }


    /**
     * 设置数据为零时候的背景
     */
    public void setDataZeroHint(String dataZeroHint, String dataZerohint2, int dataZeroImageId, String dataZeroButtonHint, View topView) {
        if (dataZeroHint == null) {
            this.dataZeroHint = "";
        } else {
            this.dataZeroHint = dataZeroHint;
        }

        if (dataZerohint2 == null) {
            this.dataZerohint2 = "";
        } else {
            this.dataZerohint2 = dataZerohint2;
        }

        this.dataZeroImageId = dataZeroImageId;
        this.dataZeroButtonHint = dataZeroButtonHint;
        emptyTopView.addView(topView);

    }

    public void setRecyclerAdapter(PlusRecyclerAdapter adapter) {
        this.adapter = adapter;
        this.smartAdapter = new SmartRecyclerAdapter(adapter);
        this.smartAdapter.setFooterView(loadingLayout);
        if (recyclerView != null) {
            recyclerView.setAdapter(smartAdapter);
        }
    }

    public void setRecyclerLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decoration) {
        recyclerView.addItemDecoration(decoration);
    }

    public void removeFooter() {
        smartAdapter.removeFooterView();
    }

    public void setHeaderAndFooterBgColor(int color) {
        loadingLayout.setBackgroundColor(color);
        mPtrRefreshLayout.setBackgroundColor(color);
    }

    public RefreshLayout setFooterTextColor(int color) {
        loadingTextview.setTextColor(color);
        return this;
    }

    public RefreshLayout setShowAllContent(String showAllContent) {
        this.showAllContent = showAllContent;
        return this;
    }

    public void setBackgroundColorResource(int resId) {
        mPtrRefreshLayout.setBackgroundResource(resId);
    }

    public void setNeedTransverseSliding(boolean needTransverseSliding) {
        isNeedTransverseSliding = needTransverseSliding;
    }

    public void showNoMore() {
        if (loadingLayout != null) {
            loadingLayout.setVisibility(View.VISIBLE);
            loadingTextview.setText(showAllContent);
            loadingTextview.setVisibility(View.VISIBLE);
            loadingProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isNeedTransverseSliding) {
            int moveX = (int) ev.getX();
            int moveY = (int) ev.getY();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDownX = moveX;
                    mDownY = moveY;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (Math.abs(moveX - mDownX) > ViewConfiguration.get(getContext()).getScaledTouchSlop() && Math.abs(moveX - mDownX) > Math.abs(moveY - mDownY)) {
                        //横向滑动
                        recyclerView.requestDisallowInterceptTouchEvent(true);
                        mPtrRefreshLayout.setEnabled(false);
                    } else {
                        //rv滑动
                        recyclerView.requestDisallowInterceptTouchEvent(false);
                        mPtrRefreshLayout.setEnabled(true);
                    }
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    mDownY = 0;
                    mDownY = 0;
                    mPtrRefreshLayout.setEnabled(true);
                    break;
                default:
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
