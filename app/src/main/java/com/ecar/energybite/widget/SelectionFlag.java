package com.ecar.energybite.widget;

public enum SelectionFlag {
    SELECTED(true),
    UNSELECTED(false);

    private boolean isSelected;

    SelectionFlag(boolean isSelected){
        this.isSelected = isSelected;
    }

    public boolean isSelected(){
        return isSelected;
    }

}