package com.hengda.smart.xhnyw.d.tools;




import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hengda.smart.xhnyw.d.R;


/**
 * 作者：Tailyou （祝文飞）
 * 时间：2016/6/7 08:52
 * 邮箱：tailyou@163.com
 * 描述：
 */
public class FragmentUtil {

    /**
     * 添加Fragment
     *
     * @param fragmentManager
     * @param containerViewId
     * @param fragment
     * @param tag
     * @param withAnim
     * @param addToBackStack
     */
    public static void addFragment(FragmentManager fragmentManager,
                                   int containerViewId,
                                   Fragment fragment,
                                   String tag,
                                   boolean withAnim,
                                   boolean addToBackStack) {
        FragmentTransaction trans = fragmentManager.beginTransaction();
        if (withAnim) {
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

//            trans.setCustomAnimations(R.animator.slide_right_in,
//                    R.animator.slide_left_out,
//                    R.animator.slide_left_in,
//                    R.animator.slide_right_out);
        }
        trans.add(containerViewId, fragment, tag);
        if (addToBackStack)
            trans.addToBackStack(tag);
        try {
            trans.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 替换Fragment
     *
     * @param fragmentManager
     * @param fragment
     * @param containerViewId
     */
    public static void replaceFragment(FragmentManager fragmentManager,
                                       int containerViewId,
                                       Fragment fragment,
                                       boolean withAnim) {
        FragmentTransaction trans = fragmentManager.beginTransaction();
        if (withAnim) {
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).setCustomAnimations(R.anim.fade_enter,R.anim.fade_exit);
        }
        trans.replace(containerViewId, fragment);
        try {
            trans.commitAllowingStateLoss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空回退栈中的Fragment
     *
     * @param fragmentManager
     */
    public static void clearBackStack(FragmentManager fragmentManager) {
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        while (backStackEntryCount > 0) {
            fragmentManager.popBackStack();
            backStackEntryCount--;
        }
    }

    /**
     * 移除Fragment
     *
     * @param fragmentManager
     * @param fragment
     */
    public static void removeFragment(FragmentManager fragmentManager, Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction trans = fragmentManager.beginTransaction();
            trans.remove(fragment).commitAllowingStateLoss();
        }
    }

}
