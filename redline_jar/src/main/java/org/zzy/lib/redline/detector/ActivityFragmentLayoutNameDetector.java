package org.zzy.lib.redline.detector;

import com.android.tools.lint.detector.api.Detector;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/19 17:36
 * 描    述：检测Activity和Fragment中的布局是否以
 * activity_和fragment_开头。
 * 比如:activity_main.xml fragment_main.xml
 * 做法：
 * 通过判断在调用setContentView和inflate方法时的布局名称
 * 修订历史：
 * ================================================
 */
public class ActivityFragmentLayoutNameDetector extends Detector implements Detector.UastScanner {
}
