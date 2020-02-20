package com.ecar.energybite.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.ecar.energybite.R;
import com.ecar.energybite.util.CollectionUtility;
import com.ecar.energybite.util.KeyboardUtility;
import com.ecar.energybite.util.MessageUtility;
import com.ecar.energybite.util.ResourceUtility;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by navin on 9/9/2019.
 */
public class ZSpinner extends ZEditText {

    private static final int DIALOG_TYPE_BOTTOM_SHEET = 1;
    private static final int DIALOG_TYPE_POPUP = 2;

    public static final int LINEAR_VERTICAL = 1;
    public static final int LINEAR_HORIZONTAL = 2;
    public static final int GRID = 3;
    public static final int CUSTOM_LAYOUT = 4;

    private static final int DEFAULT_GRID_COLUMN = 3;
    private static final int MAX_ROW_COUNT_FOR_FULLSCREEN = 10;

    // Widget attributes
    protected int dialogType;
    protected int dialogHeaderBGColor;
    protected int dialogBGColor;
    protected int orientation;
    protected int gridColumn;
    protected int maxRowCountForFullscreen;
    protected CharSequence title;
    @LayoutRes
    protected int customLayout;
    @LayoutRes
    protected int childLayoutId;
    protected int bottomSheetHeight;

    protected boolean isMultiSelect;

    protected ZSpinnerAdapter recyclerViewAdapter;
    protected List<IZSpinnerItem> mItems;


    private IZSpinnerItem selectedItem;
    private int selectedItemPos;
    private boolean isShowDropDown;
    private boolean isDeselectable = false;


    protected OnItemSelectedListener onItemSelectedListener;
    protected View.OnClickListener onSelectionListener;

    protected View dialogContentView;

    protected AppCompatDialog zSpinnerDialog;
    protected Class<? extends ZSpinnerViewHolder> viewHolderClass;
    protected View.OnClickListener m_deselectClickListener;
    private boolean isUseIconTintAttr = true;

    public ZSpinner(Context context) {
        this(context, null);
    }

    public ZSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public ZSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ZSpinner);
        try {
            dialogType = a.getInt(R.styleable.ZSpinner_dialog_type, DIALOG_TYPE_BOTTOM_SHEET);
            dialogBGColor = a.getColor(R.styleable.ZSpinner_dialog_background, ResourceUtility.getColor(R.color.colorPrimary));
            dialogHeaderBGColor = a.getColor(R.styleable.ZSpinner_dialog_header_background, ResourceUtility.getColor(R.color.colorBackground));
            orientation = a.getInt(R.styleable.ZSpinner_orientation, LINEAR_VERTICAL);
            gridColumn = a.getInt(R.styleable.ZSpinner_grid_column, DEFAULT_GRID_COLUMN);
            maxRowCountForFullscreen = a.getInt(R.styleable.ZSpinner_orientation, MAX_ROW_COUNT_FOR_FULLSCREEN);
            title = a.getString(R.styleable.ZSpinner_dialog_title);
            customLayout = a.getResourceId(R.styleable.ZSpinner_custom_layout, -1);
            childLayoutId = a.getResourceId(R.styleable.ZSpinner_item_view_layout, -1);
            bottomSheetHeight = a.getDimensionPixelSize(R.styleable.ZSpinner_options_view_height, context.getResources().getDimensionPixelSize(R.dimen.bottom_sheet_height));
            isMultiSelect = a.getBoolean(R.styleable.ZSpinner_multiselect, false);
            isShowDropDown = a.getBoolean(R.styleable.ZSpinner_isShowDropDown, true);
            if (getBackground() == null) {
                setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), 0);
            }
            if (isShowDropDown) {
                setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1], ResourceUtility.getDrawable(R.drawable.ic_dropdown), getCompoundDrawables()[3]);
            }

        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setFocusable(false);
        setClickable(true);
    }

    public <T extends IZSpinnerItem> void setItems(List<T> items, boolean isHideOnSingleElem) {
        setItems(items, isHideOnSingleElem, false);
    }

    public <T extends IZSpinnerItem> void setItems(List<T> items, boolean isHideOnSingleElem, boolean isAutoSelectFirstElement) {
        if (this.mItems == null) {
            this.mItems = new ArrayList<IZSpinnerItem>();
        }
        this.mItems.clear();
        if (!CollectionUtility.isCollectionNullOrEmpty(items)) {
            this.mItems.addAll(items);
        }
        if (isHideOnSingleElem && mItems != null && mItems.size() == 1) {
            setVisibility(View.GONE);
            if (!CollectionUtility.isCollectionNullOrEmpty(mItems)) {
                setSelectedItem(mItems.get(0));
            }
        } else {
            configureOnClickListener();
        }
        isAutoSelectFirstElement = mItems.size() == 1;
        if (isAutoSelectFirstElement) {
            selectedItem = mItems.get(0);
            if (!CollectionUtility.isCollectionNullOrEmpty(mItems)) {
                if (selectedItem != null && !mItems.contains(selectedItem)) {
                    selectedItem = null;
                    setSelectedItem(mItems.get(0));
                } else {
                    setSelectedItem(selectedItem);
                }
            }
        } else {
            notifySelectionOnItemsChange();
        }
        if (recyclerViewAdapter != null) {
            recyclerViewAdapter.notifyDataSetChanged();
        }
    }

    public void notifySelectionOnItemsChange() {
        if (selectedItem != null && !mItems.contains(selectedItem)) {
            setSelectedItem(null);
        }
    }

    public void setItems(List<IZSpinnerItem> items) {
        this.mItems = items;
        configureOnClickListener();
    }

    protected void setDefaultSelectionListener() {
        onSelectionListener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.getTag(R.id.tag_zspinner_view_holder_tag) != null && v.getTag(
                        R.id.tag_zspinner_view_holder_tag) instanceof ZSpinnerViewHolder) {
                    ZSpinnerViewHolder holder = (ZSpinnerViewHolder) v
                            .getTag(R.id.tag_zspinner_view_holder_tag);
                    setSelectedItem(holder.getItem());
                    if (onItemSelectedListener != null) {
                        onItemSelectedListener.onItemSelectedListener(selectedItem,
                                getItemPosition(selectedItem), SelectionFlag.SELECTED);
                    }
                    if (zSpinnerDialog != null && zSpinnerDialog.isShowing()) {
                        zSpinnerDialog.dismiss();
                    }
                }
            }
        };

        if (isDeselectable) {
            m_deselectClickListener = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearSelection();
                    if (zSpinnerDialog != null && zSpinnerDialog.isShowing()) {
                        zSpinnerDialog.dismiss();
                    }
                    if (view.getTag(R.id.tag_zspinner_view_holder_tag) != null && view.getTag(R.id.tag_zspinner_view_holder_tag) instanceof ZSpinnerViewHolder) {
                        if (onItemSelectedListener != null) {
                            onItemSelectedListener.onItemSelectedListener(selectedItem, getItemPosition(selectedItem), SelectionFlag.UNSELECTED);
                        }
                        if (zSpinnerDialog != null && zSpinnerDialog.isShowing()) {
                            zSpinnerDialog.dismiss();
                        }
                    }
                }
            };
        }

    }

    protected void setDefaultAdapter() {
        recyclerViewAdapter = new ZSpinnerAdapter(EasyBite.getCurrentBaseActivity(),
                viewHolderClass, mItems, childLayoutId, onSelectionListener, m_deselectClickListener);
    }

    protected void createDialogView() {
        LayoutInflater inflater = EasyBite.getCurrentBaseActivity().getLayoutInflater();
        dialogContentView = inflater.inflate(R.layout.zspinner_adapter_input_layout, null);
        RecyclerView rvZSpinner = (RecyclerView) dialogContentView.findViewById(R.id.rvZSpinner);
        RecyclerView.LayoutManager layoutManager = null;
        if (orientation == LINEAR_HORIZONTAL) {
            layoutManager = new LinearLayoutManager(ZSpinner.this.getContext(),
                    LinearLayoutManager.HORIZONTAL, false);
            childLayoutId = childLayoutId > 0 ? childLayoutId
                    : R.layout.zspinner_default_grid_item_layout;
            viewHolderClass = viewHolderClass != null ? viewHolderClass : ZSpinnerGridViewHolder.class;
            rvZSpinner.addItemDecoration(new DefaultHorizontalDividerItemDecorator(getContext(), R.drawable.horizontal_divider));
        } else if (orientation == LINEAR_VERTICAL) {
            layoutManager = new LinearLayoutManager(ZSpinner.this.getContext(),
                    LinearLayoutManager.VERTICAL, false);
            childLayoutId = childLayoutId > 0 ? childLayoutId
                    : R.layout.zspinner_default_vertical_item_layout;
            viewHolderClass = viewHolderClass != null ? viewHolderClass : ZSpinnerViewHolder.class;
        } else if (orientation == GRID) {
            layoutManager = new GridLayoutManager(ZSpinner.this.getContext(), gridColumn);
            childLayoutId = childLayoutId > 0 ? childLayoutId
                    : R.layout.zspinner_default_grid_item_layout;
            viewHolderClass = viewHolderClass != null ? viewHolderClass : ZSpinnerGridViewHolder.class;
        }

        setDefaultSelectionListener();

        if (viewHolderClass != null && childLayoutId > 0) {
            rvZSpinner.setLayoutManager(layoutManager);
            setDefaultAdapter();
            if (recyclerViewAdapter != null) {
                recyclerViewAdapter.setSelectedItem(selectedItem);
                rvZSpinner.setAdapter(recyclerViewAdapter);
            }
        }
    }

    protected void configureOnClickListener() {
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CollectionUtility.isCollectionNullOrEmpty(mItems)) {
                    MessageUtility.showErrorMessage(EasyBite.getCurrentBaseActivity(), R.string.error_value);
                }
                if (dialogContentView == null) {
                    createDialogView();
                }
                recyclerViewAdapter.notifyDataSetChanged();
                if (zSpinnerDialog == null) {
                    initializeDialog();
                } else {
                    if (zSpinnerDialog instanceof ZBottomSheetDialog) {
                        ZBottomSheetDialog dialog = (ZBottomSheetDialog) zSpinnerDialog;
                        if (orientation == LINEAR_VERTICAL) {
                            dialog.setPadding(0, 24, 0, 24);
                        }
                        BottomSheetBehavior bottomSheetBehavior = dialog.getBottomSheetBehaviour();
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }

                KeyboardUtility.hide(EasyBite.getCurrentBaseActivity());
                customizeAndShowDialog();
            }
        });
    }

    protected void customizeAndShowDialog() {
        zSpinnerDialog.show();
    }

    protected void initializeDialog() {
        if (zSpinnerDialog == null) {
            if (dialogType == DIALOG_TYPE_BOTTOM_SHEET) {
                zSpinnerDialog = new ZBottomSheetDialog(EasyBite.getCurrentBaseActivity());
                zSpinnerDialog.setContentView(dialogContentView);
                zSpinnerDialog.setTitle(title);
                if (zSpinnerDialog instanceof ZSpinnerBottomSheetDialog) {
                    ZBottomSheetDialog dialog = (ZBottomSheetDialog) zSpinnerDialog;
                    if (orientation == LINEAR_VERTICAL) {
                        dialog.setPadding(0, 24, 0, 24);
                    }
                    BottomSheetBehavior bottomSheetBehavior = dialog.getBottomSheetBehaviour();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            } else {
                zSpinnerDialog = new ZDialog(EasyBite.getCurrentBaseActivity());
                zSpinnerDialog.setContentView(dialogContentView);
                zSpinnerDialog.setTitle(title);
                if (zSpinnerDialog instanceof ZDialog) {
                    ZDialog dialog = (ZDialog) zSpinnerDialog;
                    if (orientation == LINEAR_VERTICAL) {
                        dialog.setPadding(0, 24, 0, 24);
                    }
                }
            }
        }
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemSelectedListener {
        void onItemSelectedListener(IZSpinnerItem item, int selectedIndex, SelectionFlag selectionFlag);
    }

    public int getItemPosition(IZSpinnerItem item) {
        if (!CollectionUtility.isCollectionNullOrEmpty(mItems) && item != null) {
            int index = 0;
            for (IZSpinnerItem it : mItems) {
                if (item.getItemId().equalsIgnoreCase(item.getItemId())) {
                    return index;
                }
                index++;
            }
        }
        return -1;
    }

    public IZSpinnerItem getItemByPosition(int pos) {
        if (!CollectionUtility.isCollectionNullOrEmpty(mItems) && pos < mItems.size()) {
            return mItems.get(pos);
        }
        return null;
    }

    public int getGridColumn() {
        return gridColumn;
    }

    public void setGridColumn(int gridColumn) {
        this.gridColumn = gridColumn;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getMaxRowCountForFullscreen() {
        return maxRowCountForFullscreen;
    }

    public void setMaxRowCountForFullscreen(int maxRowCountForFullscreen) {
        this.maxRowCountForFullscreen = maxRowCountForFullscreen;
    }

    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public int getCustomLayout() {
        return customLayout;
    }

    public void setCustomLayout(int customLayout) {
        this.customLayout = customLayout;
    }

    public int getChildLayoutId() {
        return childLayoutId;
    }

    public void setChildLayoutId(int childLayoutId) {
        this.childLayoutId = childLayoutId;
    }

    public int getBottomSheetHeight() {
        return bottomSheetHeight;
    }

    public void setBottomSheetHeight(int bottomSheetHeight) {
        this.bottomSheetHeight = bottomSheetHeight;
    }

    public List<? extends IZSpinnerItem> getmItems() {
        return mItems;
    }

    public IZSpinnerItem getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(IZSpinnerItem selectedItem) {
        if (!CollectionUtility.isCollectionNullOrEmpty(mItems)) {
            this.selectedItem = selectedItem;
            if (recyclerViewAdapter != null) {
                recyclerViewAdapter.setSelectedItem(selectedItem);
            }
            if (selectedItem != null) {
                setText(selectedItem.getDisplayString());

                Drawable selectedDrawable = selectedItem.getSelectedDrawable(isUseIconTintAttr);
                if (selectedDrawable != null) {
                    selectedDrawable.setBounds(0, 0, (int) getTextSize(), (int) getTextSize());
                    setCompoundDrawables(selectedDrawable, null, getCompoundDrawables()[2], null);
                } else {
                    setCompoundDrawables(null, null, getCompoundDrawables()[2], null);
                }
            } else {
                setText(null);
                setCompoundDrawables(null, null, getCompoundDrawables()[2], null);
            }
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelectedListener(this.selectedItem, mItems.indexOf(this.selectedItem), SelectionFlag.SELECTED);
            }
        }

    }

    public int getSelectedItemPos() {
        return selectedItemPos;
    }

    public void setSelectedItemPos(int selectedItemPos) {
        this.selectedItemPos = selectedItemPos;
    }

    public void clearSelection() {
        selectedItem = null;
        if (recyclerViewAdapter != null) {
            recyclerViewAdapter.setSelectedItem(null);
            setText(null);
            setCompoundDrawables(null, null, getCompoundDrawables()[2], null);
        }
    }

    public void showDropDown(boolean isShowDropDown) {
        if (!isShowDropDown) {
            setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
    }

    public void setUseIconTintAttr(boolean isUseIconTintAttr) {
        this.isUseIconTintAttr = isUseIconTintAttr;
    }

    public boolean isDeselectable() {
        return isDeselectable;
    }

    public void setIsDeselectable(boolean isDeselectable) {
        this.isDeselectable = isDeselectable;
    }

    public void setAjacentDrawable(Drawable drawableLeft, Drawable drawableTop, Drawable drawableRight, Drawable drawableBottom) {
        if (drawableLeft != null) {
            drawableLeft.setBounds(0, 0, (int) getTextSize(), (int) getTextSize());
        }
        if (drawableTop != null) {
            drawableTop.setBounds(0, 0, (int) getTextSize(), (int) getTextSize());
        }
        if (drawableRight != null) {
            drawableRight.setBounds(0, 0, (int) getTextSize(), (int) getTextSize());
        }
        if (drawableBottom != null) {
            drawableBottom.setBounds(0, 0, (int) getTextSize(), (int) getTextSize());
        }
        this.setCompoundDrawablesWithIntrinsicBounds(drawableLeft == null ? getCompoundDrawables()[0] : drawableLeft,
                drawableTop == null ? getCompoundDrawables()[1] : drawableTop,
                drawableRight == null ? getCompoundDrawables()[2] : drawableRight,
                drawableBottom == null ? getCompoundDrawables()[3] : drawableBottom);
        this.setCompoundDrawablePadding(ResourceUtility.dpToPx(5));
    }
}