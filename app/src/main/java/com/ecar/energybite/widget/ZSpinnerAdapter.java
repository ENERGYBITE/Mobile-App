package com.ecar.energybite.widget;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

import androidx.recyclerview.widget.RecyclerView;

import com.ecar.energybite.R;

/**
 * Created by anoop.gupta on 9/20/2016.
 */
public class ZSpinnerAdapter extends RecyclerView.Adapter<ZSpinnerViewHolder> {

    protected Context m_activity;
    protected Class m_viewHolderClass;
    protected List<? extends IZSpinnerItem> m_itemList;
    protected IZSpinnerItem selectedItem;
    protected int m_itemLayoutId;
    protected Map<Integer, Integer> m_viewTypeLayoutMap;
    protected View.OnClickListener m_itemClickListener;
    protected View.OnClickListener m_deselectClickListener;

    public ZSpinnerAdapter(Context activity, Class viewHolderClass, List<? extends IZSpinnerItem> items, int itemLayoutId, View.OnClickListener itemClickListener, View.OnClickListener deselectClickListener) {
        m_activity = activity;
        m_viewHolderClass = viewHolderClass;
        m_itemList = items;
        m_itemLayoutId = itemLayoutId;
        m_itemClickListener = itemClickListener;
        m_deselectClickListener = deselectClickListener;
    }

    public ZSpinnerAdapter(Context activity, Class viewHolderClass, List<? extends IZSpinnerItem> items, int itemLayoutId, View.OnClickListener itemClickListener) {
        this(activity, viewHolderClass, items, itemLayoutId, itemClickListener, null);
    }

    @Override
    public ZSpinnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ZSpinnerViewHolder viewHolder = null;
        Log.e(ZSpinnerAdapter.class.getSimpleName(), m_viewHolderClass.getName());
        if (m_viewHolderClass != null) {
            try {
                View itemView = LayoutInflater.from(m_activity).inflate(m_itemLayoutId, parent, false);
                Constructor<? extends ZSpinnerViewHolder> cons = m_viewHolderClass.getDeclaredConstructor(View.class, View.OnClickListener.class);
                if (cons != null) {
                    Log.e(ZSpinnerAdapter.class.getSimpleName(), "Constructor Found and not Null");
                    Log.e(ZSpinnerAdapter.class.getSimpleName(), cons.getName());
                    viewHolder = cons.newInstance(itemView, m_deselectClickListener);
                } else {
                    Log.e(ZSpinnerAdapter.class.getSimpleName(), "ConstructorNo FOund");
                    throw new RuntimeException("No Constructor Found");
                }
                if (m_itemClickListener != null) {
                    itemView.setOnClickListener(m_itemClickListener);
                }
                itemView.setTag(R.id.tag_zspinner_view_holder_tag, viewHolder);
                if (itemView.findViewById(R.id.iv_btn_deselect) != null) {
                    itemView.findViewById(R.id.iv_btn_deselect).setTag(R.id.tag_zspinner_view_holder_tag, viewHolder);  // for deselect functionality
                }
            } catch (Exception e) {
                Log.e(ZSpinnerAdapter.class.getSimpleName(), "Error while creating view holder" + e.getMessage(), e);
            }
        }
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return m_itemList != null ? m_itemList.size() : 0;
    }

    @Override
    public void onBindViewHolder(ZSpinnerViewHolder holder, int position) {
        if (holder != null && m_itemList != null && m_itemList.size() > position) {
            holder.bindView(m_itemList.get(position), isSelectedItem(m_itemList.get(position)));
            holder.setItem(m_itemList.get(position));
            holder.itemView.setTag(holder);
        }
    }

    public boolean isSelectedItem(IZSpinnerItem item) {
        return (item != null && selectedItem != null && selectedItem.getItemId().equals(item.getItemId()));
    }

    public IZSpinnerItem getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(IZSpinnerItem selectedItem) {
        this.selectedItem = selectedItem;
    }
}
