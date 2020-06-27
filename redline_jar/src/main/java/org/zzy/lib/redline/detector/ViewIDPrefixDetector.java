package org.zzy.lib.redline.detector;

import com.android.SdkConstants;
import com.android.ide.common.resources.ResourceUrl;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.LayoutDetector;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.XmlContext;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.Collection;

/**
 * ================================================
 * 作    者：ZhouZhengyi
 * 创建日期：2020/6/22 11:32
 * 描    述：检测xml布局中，View的id命名是否规范
 * 修订历史：
 * ================================================
 */
public class ViewIDPrefixDetector extends LayoutDetector {

    public static final Issue ISSUE = Issue.create(
            "org.zzy.ViewIdPrefix",
            "View_Id 前缀命名不规范！",
            "View_Id 前缀命名不规范！",
            Category.TYPOGRAPHY,
            6,
            Severity.WARNING,
            new Implementation(
                    ViewIDPrefixDetector.class,
                    Scope.RESOURCE_FILE_SCOPE
            )
    );


    @Nullable
    @Override
    public Collection<String> getApplicableElements() {
        return Arrays.asList(
                SdkConstants.VIEW,
                SdkConstants.BUTTON,
                SdkConstants.IMAGE_VIEW,
                SdkConstants.IMAGE_BUTTON,
                SdkConstants.TEXT_VIEW,
                SdkConstants.LIST_VIEW,
                "android.support.v7.widget.RecyclerView",
                "com.android.internal.widget.RecyclerView",
                "androidx.recyclerview.widget.RecyclerView",
                SdkConstants.LINEAR_LAYOUT,
                SdkConstants.RELATIVE_LAYOUT,
                SdkConstants.ABSOLUTE_LAYOUT,
                SdkConstants.FRAME_LAYOUT,
                SdkConstants.GRID_LAYOUT
        );
    }

    @Override
    public void visitElement(@NotNull XmlContext context, @NotNull Element element) {
        String nodeName = element.getNodeName();
        Attr attributeNode = element.getAttributeNode("android:id");
        if(attributeNode!=null){
            String value = attributeNode.getValue();
            ResourceUrl parse = ResourceUrl.parse(value);

            switch (nodeName){
                case SdkConstants.VIEW:
                    checkViewNameWithSpecificPrefix(parse, "view_", context, attributeNode);
                    break;

                case SdkConstants.BUTTON:
                    checkViewNameWithSpecificPrefix(parse, "btn_", context, attributeNode);
                    break;

                case SdkConstants.IMAGE_VIEW:
                    checkViewNameWithSpecificPrefix(parse, "iv_", context, attributeNode);
                    break;

                case SdkConstants.IMAGE_BUTTON:
                    checkViewNameWithSpecificPrefix(parse, "ib_", context, attributeNode);
                    break;

                case SdkConstants.TEXT_VIEW:
                    checkViewNameWithSpecificPrefix(parse, "tv_", context, attributeNode);
                    break;

                case SdkConstants.LIST_VIEW:
                    checkViewNameWithSpecificPrefix(parse, "lv_", context, attributeNode);
                    break;

                case "android.support.v7.widget.RecyclerView":
                case "com.android.internal.widget.RecyclerView":
                case "androidx.recyclerview.widget.RecyclerView":
                    checkViewNameWithSpecificPrefix(parse, "rv_", context, attributeNode);
                    break;
                case SdkConstants.LINEAR_LAYOUT:
                    checkViewNameWithSpecificPrefix(parse, "ll_", context, attributeNode);
                    break;

                case SdkConstants.RELATIVE_LAYOUT:
                    checkViewNameWithSpecificPrefix(parse, "rl_", context, attributeNode);
                    break;

                case SdkConstants.ABSOLUTE_LAYOUT:
                    checkViewNameWithSpecificPrefix(parse, "al_", context, attributeNode);
                    break;

                case SdkConstants.FRAME_LAYOUT:
                    checkViewNameWithSpecificPrefix(parse, "fl_", context, attributeNode);
                    break;

                case SdkConstants.GRID_LAYOUT:
                    checkViewNameWithSpecificPrefix(parse, "gl_", context, attributeNode);
                    break;

                default:
                    break;
            }
        }
    }

    private boolean checkViewNameWithSpecificPrefix(ResourceUrl resourceUrl,String prefix,XmlContext context,Attr attrNode){
        if(!resourceUrl.name.startsWith(prefix)){
            context.report(ISSUE,attrNode,context.getLocation(attrNode),"View_Id 前缀命名不规范！");
            return false;
        }else{
            return true;
        }
    }
}
