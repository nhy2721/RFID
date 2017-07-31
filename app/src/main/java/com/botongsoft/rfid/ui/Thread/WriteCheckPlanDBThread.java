package com.botongsoft.rfid.ui.Thread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.botongsoft.rfid.bean.classity.CheckPlan;
import com.botongsoft.rfid.common.db.DBDataUtils;

import java.util.ArrayList;
import java.util.List;

import static com.botongsoft.rfid.common.constants.Constant.BackThread_GETCHECKPLAN_SUCCESS_PB;
import static com.botongsoft.rfid.common.utils.StringFormatUtil.getDateOfGMTToDateStr;

/**
 * Created by pc on 2017/7/19.
 */

public class WriteCheckPlanDBThread extends Thread {
    private List<CheckPlan> objList = null;
    private List<CheckPlan> saveList = new ArrayList<CheckPlan>();
    private List<CheckPlan> newList = new ArrayList<CheckPlan>();
    private CheckPlan mCheckPlanOld;
    private Handler mhandler;
    private Message uiMsg;

    public WriteCheckPlanDBThread(Handler mhandler, Message uiMsg) {
        this.mhandler = mhandler;
        this.uiMsg = uiMsg;
    }

    public void setList(List list) {
        this.objList = list;
    }

    @Override
    public void run() {
        int size =objList.size();
        for (int i = 0; i < size; i++) {
            CheckPlan mCheckPlan = objList.get(i);
            uiMsg = mhandler.obtainMessage();
            Bundle b = new Bundle();
            b.putInt("checkplan",i+1);
            uiMsg.setData(b);
            uiMsg.what = BackThread_GETCHECKPLAN_SUCCESS_PB;
            mhandler.sendMessage(uiMsg);
            mCheckPlanOld = (CheckPlan) DBDataUtils.getInfo(CheckPlan.class, "id", mCheckPlan.getId() + "");
            if (mCheckPlanOld != null) {
                mCheckPlanOld.setAnchor(mCheckPlan.getAnchor());
                mCheckPlanOld.setId(mCheckPlan.getId());
                mCheckPlanOld.setBz(mCheckPlan.getBz());
                mCheckPlanOld.setFw(mCheckPlan.getFw());
                mCheckPlanOld.setPdid(mCheckPlan.getPdid());
                if (mCheckPlanOld.getKssj() != null && !"".equals(mCheckPlanOld.getKssj()) && !"null".equals(mCheckPlanOld.getKssj())) {
                    try {
                        String value = getDateOfGMTToDateStr(String.valueOf(mCheckPlan.getKssj()));
                        //                        mCheckPlanOld.setKssj(ObjectFormatUtil.convertVarcharToDate(value, "yyyy-MM-dd "));
                        mCheckPlanOld.setKssj(value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (mCheckPlanOld.getJssj() != null && !"".equals(mCheckPlanOld.getJssj()) && !"null".equals(mCheckPlanOld.getJssj())) {
                    try {
                        String value = getDateOfGMTToDateStr(String.valueOf(mCheckPlan.getJssj()));
                        //                        mCheckPlanOld.setJssj(ObjectFormatUtil.convertVarcharToDate(value, "yyyy-MM-dd "));
                        mCheckPlanOld.setJssj(value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //                mCheckPlanOld.setJssj(mCheckPlan.getJssj());
                //                mCheckPlanOld.setKssj(mCheckPlan.getKssj());
                mCheckPlanOld.setPdr(mCheckPlan.getPdr());
                mCheckPlanOld.setStatus(9);
                saveList.add(mCheckPlanOld);
            } else {
                if (mCheckPlan.getKssj() != null && !"".equals(mCheckPlan.getKssj()) && !"null".equals(mCheckPlan.getKssj())) {
                    try {
                        String value = getDateOfGMTToDateStr(String.valueOf(mCheckPlan.getKssj()));
                        //                        mCheckPlan.setKssj(ObjectFormatUtil.convertVarcharToDate(value, "yyyy-MM-dd "));
                        mCheckPlan.setKssj(value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (mCheckPlan.getJssj() != null && !"".equals(mCheckPlan.getJssj()) && !"null".equals(mCheckPlan.getJssj())) {
                    try {
                        String value = getDateOfGMTToDateStr(String.valueOf(mCheckPlan.getJssj()));
                        //                        mCheckPlan.setJssj(ObjectFormatUtil.convertVarcharToDate(value, "yyyy-MM-dd "));
                        mCheckPlan.setJssj(value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                mCheckPlan.setStatus(9);
                newList.add(mCheckPlan);
            }

        }
//        for (CheckPlan mCheckPlan : objList) {
//            mCheckPlanOld = (CheckPlan) DBDataUtils.getInfo(CheckPlan.class, "id", mCheckPlan.getId() + "");
//            if (mCheckPlanOld != null) {
//                mCheckPlanOld.setAnchor(mCheckPlan.getAnchor());
//                mCheckPlanOld.setId(mCheckPlan.getId());
//                mCheckPlanOld.setBz(mCheckPlan.getBz());
//                mCheckPlanOld.setFw(mCheckPlan.getFw());
//                mCheckPlanOld.setPdid(mCheckPlan.getPdid());
//                if (mCheckPlanOld.getKssj() != null && !"".equals(mCheckPlanOld.getKssj()) && !"null".equals(mCheckPlanOld.getKssj())) {
//                    try {
//                        String value = getDateOfGMTToDateStr(String.valueOf(mCheckPlan.getKssj()));
////                        mCheckPlanOld.setKssj(ObjectFormatUtil.convertVarcharToDate(value, "yyyy-MM-dd "));
//                        mCheckPlanOld.setKssj(value);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (mCheckPlanOld.getJssj() != null && !"".equals(mCheckPlanOld.getJssj()) && !"null".equals(mCheckPlanOld.getJssj())) {
//                    try {
//                        String value = StringFormatUtil.getDateOfGMTToDateStr(String.valueOf(mCheckPlan.getJssj()));
////                        mCheckPlanOld.setJssj(ObjectFormatUtil.convertVarcharToDate(value, "yyyy-MM-dd "));
//                        mCheckPlanOld.setJssj(value);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                //                mCheckPlanOld.setJssj(mCheckPlan.getJssj());
//                //                mCheckPlanOld.setKssj(mCheckPlan.getKssj());
//                mCheckPlanOld.setPdr(mCheckPlan.getPdr());
//                mCheckPlanOld.setStatus(9);
//                saveList.add(mCheckPlanOld);
//            } else {
//                if (mCheckPlan.getKssj() != null && !"".equals(mCheckPlan.getKssj()) && !"null".equals(mCheckPlan.getKssj())) {
//                    try {
//                        String value = getDateOfGMTToDateStr(String.valueOf(mCheckPlan.getKssj()));
////                        mCheckPlan.setKssj(ObjectFormatUtil.convertVarcharToDate(value, "yyyy-MM-dd "));
//                        mCheckPlan.setKssj(value);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (mCheckPlan.getJssj() != null && !"".equals(mCheckPlan.getJssj()) && !"null".equals(mCheckPlan.getJssj())) {
//                    try {
//                        String value = StringFormatUtil.getDateOfGMTToDateStr(String.valueOf(mCheckPlan.getJssj()));
////                        mCheckPlan.setJssj(ObjectFormatUtil.convertVarcharToDate(value, "yyyy-MM-dd "));
//                        mCheckPlan.setJssj(value);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                mCheckPlan.setStatus(9);
//                newList.add(mCheckPlan);
//            }
//        }
        if (newList != null && !newList.isEmpty()) {
            DBDataUtils.saveAll(newList);
        }
        if (saveList != null && !saveList.isEmpty()) {
            DBDataUtils.updateAll(saveList);
        }
//        mhandler.obtainMessage(Constant.BackThread_SUCCESS).sendToTarget();
    }
}
