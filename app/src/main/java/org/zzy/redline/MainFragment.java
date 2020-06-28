package org.zzy.redline;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/28 21:31
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MainFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_main, container, false);
        return view;
    }
}
