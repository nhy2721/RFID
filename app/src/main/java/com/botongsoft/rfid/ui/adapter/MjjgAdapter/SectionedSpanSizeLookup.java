/*
 * Copyright (C) 2015 Tomás Ruiz-López.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.botongsoft.rfid.ui.adapter.MjjgAdapter;

import android.support.v7.widget.GridLayoutManager;

import com.botongsoft.rfid.ui.entity.MjjgEntity;

import java.util.ArrayList;


/**
 * A SpanSizeLookup to draw section headers or footer spanning the whole width of the RecyclerView
 * when using a GridLayoutManager
 */
public class SectionedSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    protected SectionedRecyclerViewAdapter<?, ?, ?> adapter = null;
    protected GridLayoutManager layoutManager = null;
    MjjgEntity entity;
    private  int size;
    public ArrayList<MjjgEntity.TagsEntity.TagInfo> tagInfoList;

    public SectionedSpanSizeLookup(SectionedRecyclerViewAdapter<?, ?, ?> adapter, GridLayoutManager layoutManager,int size) {
        this.adapter = adapter;
        this.layoutManager = layoutManager;
        this.size = size;

    }

    @Override
    public int getSpanSize(int position) {

        if (adapter.isSectionHeaderPosition(position) || adapter.isSectionFooterPosition(position)) {
            //            tagInfoList =    entity.allTagsList.get(position).tagInfoList;
                        return size;
//            return layoutManager.getSpanCount();
        } else {
            return 1;
        }

    }
}
