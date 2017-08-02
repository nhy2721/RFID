package com.botongsoft.rfid.ui.entity;

import java.util.ArrayList;

/**
 * Created by lyd10892 on 2016/8/23.
 */

public class MjjgEntity {

    public ArrayList<TagsEntity> allTagsList;

    public class TagsEntity {
        public String tagsName;
        public ArrayList<TagInfo> tagInfoList;

        public class TagInfo {
            private String tagName;
            private String updateColor = "";
            private int tagId;

            public int getTagId() {
                return tagId;
            }

            public void setTagId(int tagId) {
                this.tagId = tagId;
            }


            public String getTagName() {
                return tagName;
            }

            public void setTagName(String tagName) {
                this.tagName = tagName;
            }

            public String getUpdateColor() {
                return updateColor;
            }

            public void setUpdateColor(String updateColor) {
                this.updateColor = updateColor;
            }
        }
    }

}
