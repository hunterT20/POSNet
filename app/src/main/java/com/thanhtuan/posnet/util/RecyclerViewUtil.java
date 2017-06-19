package com.thanhtuan.posnet.util;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Nusib on 6/19/2017.
 */

public class RecyclerViewUtil {
    public static void setupRecyclerView(RecyclerView recyclerView, RecyclerView.Adapter adapter, Context context){
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
    }
}
