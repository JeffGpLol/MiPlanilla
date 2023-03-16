package com.example.miplanilla;

public interface OnItemClickListener<T>{
    void onItemClick(T data, int type);

    void onItemClick(Planilla data, String type);
}
