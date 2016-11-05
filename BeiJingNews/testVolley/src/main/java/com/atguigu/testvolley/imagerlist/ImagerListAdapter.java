package com.atguigu.testvolley.imagerlist;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.atguigu.testvolley.R;

public class ImagerListAdapter extends BaseAdapter implements AbsListView.OnScrollListener {

    private static final String TAG = ImagerListAdapter.class.getSimpleName();
    private final String[] imageThumbUrls;
    private final Context context;
    private final ListView listView;


    /**
     * 标识是否是第一次执行，如果是第一次执行 onScrollStateChanged是不调用的
     */
    private boolean isFirstEnter;
    /**
     * 第一个可以看见的item
     */
    private int firstSeeItem;

    /**
     * 记录上一次可以看见的第一个
     */
    private int orifirstItem;
    /**
     * 可以看见item的总数
     */
    private int totalSeeItem;

    /**
     * 在ListView停止滚动时加载
     */
    public static final int LOADING_AT_IDLE = 1;
    /**
     * 在ListView滚动时加载
     */
    public static final int LOADING_AT_ONSCROLL = 2;

    /**
     * 什么时候加载图片
     */
    private int currentLoadState = LOADING_AT_ONSCROLL;

    public ImagerListAdapter(Context context, String[] imageThumbUrls, ListView listView) {
        this.context = context;
        this.imageThumbUrls = imageThumbUrls;
        this.listView = listView;
        isFirstEnter = true;
        //设置ListView滚动监听
        listView.setOnScrollListener(this);
    }

    @Override
    public int getCount() {
        return imageThumbUrls.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.image_items, null);
            viewHolder = new ViewHolder();
            viewHolder.imageview = (ImageView) convertView.findViewById(R.id.imageview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imageview.setImageResource(R.drawable.empty_photo);
        //得到数据
        String imageurl = imageThumbUrls[position];
        viewHolder.imageview.setTag(imageurl);
        //直接在这里请求会乱位置
//        ImageUtils.loadImage(imageurl,listView);
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (orifirstItem != firstSeeItem) {
            if (scrollState == SCROLL_STATE_IDLE) {
                startLoadImages(firstSeeItem, totalSeeItem);
                orifirstItem = firstSeeItem;
            } else {
                ImageUtils.cancelAllImageRequests();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        firstSeeItem = firstVisibleItem;
        totalSeeItem = visibleItemCount;
        if(currentLoadState ==LOADING_AT_IDLE){
            if (isFirstEnter && visibleItemCount > 0) {
                orifirstItem = firstVisibleItem;
                startLoadImages(firstSeeItem, totalSeeItem);
                isFirstEnter = false;
            }
        }else if(currentLoadState ==LOADING_AT_ONSCROLL){
            startLoadImages(firstSeeItem, totalSeeItem);
        }

    }


    static class ViewHolder {
        ImageView imageview;
    }

    private void startLoadImages(int first, int total) {
        Log.v(TAG, "imagedown--->startLoadImages,first-->" + first + ",total-->" + total);
        for (int i = first; i < first + total; i++) {
            ImageUtils.loadImage(imageThumbUrls[i], listView);
        }
    }
}
