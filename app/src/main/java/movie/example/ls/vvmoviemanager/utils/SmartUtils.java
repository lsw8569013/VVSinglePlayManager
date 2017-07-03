package movie.example.ls.vvmoviemanager.utils;

import android.app.ActionBar;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.os.Build;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SmartUtils {

    /**
     * è°ƒç”¨ ActionBar.setTabsShowAtBottom(boolean) æ–¹æ³•ã€?     *
     * <p>å¦‚æžœ android:uiOptions="splitActionBarWhenNarrow"ï¼Œåˆ™å¯è®¾ç½®ActionBar Tabsæ˜¾ç¤ºåœ¨åº•æ ã?
     *
     * <p>ç¤ºä¾‹ï¼?/p>
     * <pre class="prettyprint">
     * public class MyActivity extends Activity implements ActionBar.TabListener {
     *
     *     protected void onCreate(Bundle savedInstanceState) {
     *         super.onCreate(savedInstanceState);
     *         ...
     *
     *         final ActionBar bar = getActionBar();
     *         bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
     *         SmartBarUtils.setActionBarTabsShowAtBottom(bar, true);
     *
     *         bar.addTab(bar.newTab().setText("tab1").setTabListener(this));
     *         ...
     *     }
     * }
     * </pre>
     */
    public static void setActionBarTabsShowAtBottom(ActionBar actionbar, boolean showAtBottom) {
        try {
            Method method = Class.forName("android.app.ActionBar").getMethod(
                    "setTabsShowAtBottom", new Class[] { boolean.class });
            try {
                method.invoke(actionbar, showAtBottom);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否有SmartBar
     */
    public static boolean hasSmartBar() {

        try {
            // 新型号可用反射调用Build.hasSmartBar()
            Method[] methods = Class.forName("android.os.Build").getMethods();
            for (Method method : methods) {
                if (method.getName().equals("hasSmartBar")) {
                    return ((Boolean) method.invoke(null)).booleanValue();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 反射不到Build.hasSmartBar()，则用Build.DEVICE判断
        if (Build.DEVICE.equals("mx2")) {
            return true;
        } else if (Build.DEVICE.equals("mx") || Build.DEVICE.equals("m9")) {
            return false;
        }
        return false;
    }

    /**
     * è°ƒç”¨ ActionBar.setActionBarViewCollapsable(boolean) æ–¹æ³•ã€?     *
     * <p>è®¾ç½®ActionBaré¡¶æ æ— æ˜¾ç¤ºå†…å®¹æ—¶æ˜¯å¦éšè—ã€?     *
     * <p>ç¤ºä¾‹ï¼?/p>
     * <pre class="prettyprint">
     * public class MyActivity extends Activity {
     *
     *     protected void onCreate(Bundle savedInstanceState) {
     *         super.onCreate(savedInstanceState);
     *         ...
     *
     *         final ActionBar bar = getActionBar();
     *
     *         // è°ƒç”¨setActionBarViewCollapsableï¼Œå¹¶è®¾ç½®ActionBaræ²¡æœ‰æ˜¾ç¤ºå†…å®¹ï¼Œåˆ™ActionBaré¡¶æ ä¸æ˜¾ç¤?     *         SmartBarUtils.setActionBarViewCollapsable(bar, true);
     *         bar.setDisplayOptions(0);
     *     }
     * }
     * </pre>
     */
    public static void setActionBarViewCollapsable(ActionBar actionbar, boolean collapsable) {
        try {
            Method method = Class.forName("android.app.ActionBar").getMethod(
                    "setActionBarViewCollapsable", new Class[] { boolean.class });
            try {
                method.invoke(actionbar, collapsable);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * è°ƒç”¨ ActionBar.setActionModeHeaderHidden(boolean) æ–¹æ³•ã€?     *
     * <p>è®¾ç½®ActionModeé¡¶æ æ˜¯å¦éšè—ã€?     *
     * <p>ç¤ºä¾‹ï¼?/p>
     * <pre class="prettyprint">
     * public class MyActivity extends Activity {
     *
     *     protected void onCreate(Bundle savedInstanceState) {
     *         super.onCreate(savedInstanceState);
     *         ...
     *
     *         final ActionBar bar = getActionBar();
     *
     *         // ActionBarè½¬ä¸ºActionModeæ—¶ï¼Œä¸æ˜¾ç¤ºActionModeé¡¶æ 
     *         SmartBarUtils.setActionModeHeaderHidden(bar, true);
     *     }
     * }
     * </pre>
     */
    public static void setActionModeHeaderHidden(ActionBar actionbar, boolean hidden) {
        try {
            Method method = Class.forName("android.app.ActionBar").getMethod(
                    "setActionModeHeaderHidden", new Class[] { boolean.class });
            try {
                method.invoke(actionbar, hidden);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    /**
     * è°ƒç”¨ActionBar.setBackButtonDrawable(Drawable)æ–¹æ³•
     *
     * <p>è®¾ç½®è¿”å›žé”®å›¾æ ?     *
     * <p>ç¤ºä¾‹ï¼?/p>
     * <pre class="prettyprint">
     * public class MyActivity extends Activity {
     *
     *     protected void onCreate(Bundle savedInstanceState) {
     *         super.onCreate(savedInstanceState);
     *         ...
     *
     *         final ActionBar bar = getActionBar();
     *         // è‡ªå®šä¹‰ActionBarçš„è¿”å›žé”®å›¾æ ‡
     *         SmartBarUtils.setBackIcon(bar, getResources().getDrawable(R.drawable.ic_back));
     *         ...
     *     }
     * }
     * </pre>
     */
    public static void setBackIcon(ActionBar actionbar, Drawable backIcon) {
        try {
            Method method = Class.forName("android.app.ActionBar").getMethod(
                    "setBackButtonDrawable", new Class[] { Drawable.class });
            try {
                method.invoke(actionbar, backIcon);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
