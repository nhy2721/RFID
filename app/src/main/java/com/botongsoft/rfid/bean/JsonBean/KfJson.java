package com.botongsoft.rfid.bean.JsonBean;

import java.util.List;

/**
 * Created by pc on 2017/7/10.
 */

public class KfJson {

    /**
     * res : {"code":"0","recordCount ":"2","rows":[{"id":"151","mc":"2号库房","bz":"","qzh":"0000"},{"id":"161","mc":"3号库房","bz":"","qzh":"0001"}]}
     */

    private ResBean res;

    public ResBean getRes() {
        return res;
    }

    public void setRes(ResBean res) {
        this.res = res;
    }

    public static class ResBean {
        /**
         * code : 0
         * recordCount  : 2
         * rows : [{"id":"151","mc":"2号库房","bz":"","qzh":"0000"},{"id":"161","mc":"3号库房","bz":"","qzh":"0001"}]
         */

        private String code;
        private String recordCount;
        private List<RowsBean> rows;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getRecordCount() {
            return recordCount;
        }

        public void setRecordCount(String recordCount) {
            this.recordCount = recordCount;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * id : 151
             * mc : 2号库房
             * bz :
             * qzh : 0000
             */

            private String id;
            private String mc;
            private String bz;
            private String qzh;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMc() {
                return mc;
            }

            public void setMc(String mc) {
                this.mc = mc;
            }

            public String getBz() {
                return bz;
            }

            public void setBz(String bz) {
                this.bz = bz;
            }

            public String getQzh() {
                return qzh;
            }

            public void setQzh(String qzh) {
                this.qzh = qzh;
            }
        }
    }
}
