package com.botongsoft.rfid.bean.JsonBean;

import java.util.List;

/**
 * Created by pc on 2017/7/11.
 */

public class MjjJson {

    /**
     * res : {"code":"0","recordCount":"3","rows":[{"id":"426","noleft":"0","zlbq":"","zs":"5","noright":"0","mc":"文书档案架","bz":"","kfid":"151","ylbq":"","cs":"6"},{"id":"427","noleft":"0","zlbq":"","zs":"5","noright":"0","mc":"文书档案架","bz":"","kfid":"151","ylbq":"","cs":"6"},{"id":"428","noleft":"0","zlbq":"","zs":"5","noright":"0","mc":"文书档案架","bz":"","kfid":"151","ylbq":"","cs":"6"}]}
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
         * recordCount : 3
         * rows : [{"id":"426","noleft":"0","zlbq":"","zs":"5","noright":"0","mc":"文书档案架","bz":"","kfid":"151","ylbq":"","cs":"6"},{"id":"427","noleft":"0","zlbq":"","zs":"5","noright":"0","mc":"文书档案架","bz":"","kfid":"151","ylbq":"","cs":"6"},{"id":"428","noleft":"0","zlbq":"","zs":"5","noright":"0","mc":"文书档案架","bz":"","kfid":"151","ylbq":"","cs":"6"}]
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
             * id : 426
             * noleft : 0
             * zlbq :
             * zs : 5
             * noright : 0
             * mc : 文书档案架
             * bz :
             * kfid : 151
             * ylbq :
             * cs : 6
             */

            private String id;
            private String noleft;
            private String zlbq;
            private String zs;
            private String noright;
            private String mc;
            private String bz;
            private String kfid;
            private String ylbq;
            private String cs;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNoleft() {
                return noleft;
            }

            public void setNoleft(String noleft) {
                this.noleft = noleft;
            }

            public String getZlbq() {
                return zlbq;
            }

            public void setZlbq(String zlbq) {
                this.zlbq = zlbq;
            }

            public String getZs() {
                return zs;
            }

            public void setZs(String zs) {
                this.zs = zs;
            }

            public String getNoright() {
                return noright;
            }

            public void setNoright(String noright) {
                this.noright = noright;
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

            public String getKfid() {
                return kfid;
            }

            public void setKfid(String kfid) {
                this.kfid = kfid;
            }

            public String getYlbq() {
                return ylbq;
            }

            public void setYlbq(String ylbq) {
                this.ylbq = ylbq;
            }

            public String getCs() {
                return cs;
            }

            public void setCs(String cs) {
                this.cs = cs;
            }
        }
    }
}
