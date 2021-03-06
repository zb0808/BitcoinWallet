package com.lrkj.bitcoinwallet.major.receive;


import android.content.ClipboardManager;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lrkj.bitcoinwallet.MyApplication;
import com.lrkj.bitcoinwallet.R;
import com.lrkj.bitcoinwallet.base.BaseView;
import com.lrkj.bitcoinwallet.databinding.FragmentReceiveCoinBinding;
import com.lrkj.bitcoinwallet.util.QRCodeUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class ReceiveCoinFragment extends BaseView<ReceiveCoinViewModel, FragmentReceiveCoinBinding> {

    public ReceiveCoinFragment() {
    }

    public static ReceiveCoinFragment newInstance() {
        return new ReceiveCoinFragment();
    }

    private Button button;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_receive_coin, container, false);
        if (getActivity() != null) {
            binding.setFragmentManager(getActivity().getSupportFragmentManager());
        }
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        String address = viewModel.getAddress().replaceAll("\\s*", "");

        super.onActivityCreated(savedInstanceState);
        button = getActivity().findViewById(R.id.copyBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取剪贴板，并把内容设置在剪切板
                ClipboardManager cbm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                cbm.setText(address);
                Toast.makeText(MyApplication.getContext(), "地址已经复制", Toast.LENGTH_LONG).show();
            }
        });

        //二维码
        ImageView qrImageView = getActivity().findViewById(R.id.QR);
        Bitmap mBitmap =CodeUtils.createImage("bitcoin:" + address, 800, 800, null);
        qrImageView.setImageBitmap(mBitmap);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (viewModel != null) {
            viewModel.stop();
        }
    }
}
