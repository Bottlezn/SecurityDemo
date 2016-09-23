package com.example.wdh.securitydemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.wdh.securitydemo.R;
import com.example.wdh.securitydemo.utils.AESUtil;
import com.example.wdh.securitydemo.utils.DESUtil;
import com.example.wdh.securitydemo.utils.SHAUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by wdh on 2016/9/21.
 * 有一个按钮用来加密解密事件
 */

public class MainActivity extends Activity implements View.OnClickListener {


    private Button mBtnEncrypt, mBtnDecrypt;

    private EditText mET;

    private TextView tvEncrypt, tvDecrypt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_main);
        initView();
        setViewEvent();
    }

    private void initView() {
        mBtnEncrypt = (Button) findViewById(R.id.btnEncrypt);
        mBtnDecrypt = (Button) findViewById(R.id.btnDecrypt);
        mET = (EditText) findViewById(R.id.et);
        tvEncrypt = (TextView) findViewById(R.id.tvEncrypt);
        tvDecrypt = (TextView) findViewById(R.id.tvDecrypt);
    }

    private void setViewEvent() {
        mBtnEncrypt.setOnClickListener(this);
        mBtnDecrypt.setOnClickListener(this);
    }


    private void encryptImage() {
        try {
            File parent = Environment.getExternalStorageDirectory().getAbsoluteFile();
            File imageFile = new File(parent, "abc.jpg");
            FileInputStream fis = new FileInputStream(imageFile);
            File copy = new File(parent, "copy.jpg");
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes, 0, bytes.length);
            bytes = AESUtil.encryptData(bytes, null);
            if (copy.exists()) {
                copy.delete();
            }
            copy.createNewFile();
            FileOutputStream fos = new FileOutputStream(copy);
            fos.write(bytes);
            fis.close();
            fos.close();
            bytes = null;

        } catch (Exception e) {
            Log.e("WTF", e.toString());
        }
    }

    public static byte[] File2byte(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    private void decryptImage() {
        try {
            File parent = Environment.getExternalStorageDirectory().getAbsoluteFile();
            File copy = new File(parent, "copy.jpg");
            byte[] bytes = File2byte(copy.getAbsolutePath());
            bytes = AESUtil.decryptData(bytes, null);
            File newFile = new File(parent, "newFile.jpg");
            if (newFile.exists()) {
                newFile.delete();
            }
            newFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(newFile);
            fos.write(bytes, 0, bytes.length);
            fos.close();
        } catch (Exception e) {
            Log.e("WTF", e.toString());
        }
    }


    @Override
    public void onClick(View v) {
        if (mET.getText().toString().length() == 0) {
            return;
        }
        String s = mET.getText().toString();
        switch (v.getId()) {
            case R.id.btnEncrypt:
                try {
//                    encryptImage();
                    tvEncrypt.setText(DESUtil.encryptStr(s.getBytes("UTF-8"), null));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("WTF", e.toString());
                }
                break;
            case R.id.btnDecrypt:
                try {
//                    decryptImage();
                    tvDecrypt.setText(SHAUtil.getSpecificSHA(s, SHAUtil.SHA_512));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("WTF", e.toString());
                }
                break;
            default:
                break;
        }
    }
}
