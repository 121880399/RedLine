package org.zzy.plugin.redline;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/7/9 8:21
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class RedLineExtension {
    /**
     * 被排除的目录里集合
     * */
    public List<String> excludeDirs  = new ArrayList<>();

    /**
     * 被排除的文件集合
     * */
    public List<String> excludeFiles = new ArrayList<>();
}
