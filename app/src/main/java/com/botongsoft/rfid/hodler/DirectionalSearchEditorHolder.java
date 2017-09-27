package com.botongsoft.rfid.hodler;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.botongsoft.rfid.R;


/**

 */
public class DirectionalSearchEditorHolder {
    private Context mContext;
    private View mContentView;
    private TextInputLayout til_bookshelf;
    public EditText et_bookshelf_name;


    public DirectionalSearchEditorHolder(Context context) {
        this(context, "");
    }

    public DirectionalSearchEditorHolder(Context context, String name) {
        mContext = context;
        initView(name);
        initEvent();
    }

    private void initView(String name) {
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.item_add_dh, null);
        til_bookshelf = (TextInputLayout) mContentView.findViewById(R.id.til_bookshelf);
        et_bookshelf_name = (EditText) mContentView.findViewById(R.id.et_bookshelf_name);
        et_bookshelf_name.setText(name);
    }

    private void initEvent() {
    }

    public View getContentView() {
        return mContentView;
    }

    public boolean check() {
        if (TextUtils.isEmpty(et_bookshelf_name.getText().toString())) {
            return false;
        } else {
            return true;
        }
    }

    public String getName() {
        return et_bookshelf_name.getText().toString();
    }


}
