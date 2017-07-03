package movie.example.ls.vvmoviemanager.keyboard;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.ImageSpan;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import movie.example.ls.vvmoviemanager.R;
import movie.example.ls.vvmoviemanager.utils.ScreenUtils;

/**
 * 表情工具类
 */
public class CIconSmileUtils {

    public static final String bee_01 = "[b):]";
    public static final String bee_02 = "[b:D]";
    public static final String bee_03 = "[b;)]";
    public static final String c_key_red = "[hongbao)]";
    public static final String bee_04 = "[b:-o]";
    public static final String bee_05 = "[b:p]";
    public static final String bee_06 = "[b(H)]";
    public static final String bee_07 = "[b:@]";
    public static final String bee_08 = "[b:s]";
    public static final String bee_09 = "[b:$]";
    public static final String bee_10 = "[b:(]";
    public static final String bee_11 = "[b:'(]";
    public static final String bee_12 = "[b:|]";
    public static final String bee_13 = "[c):]";
    public static final String bee_14 = "[c:D]";
    public static final String bee_15 = "[c;)]";
    public static final String bee_16 = "[c:-o]";
    public static final String bee_17 = "[c:p]";
    public static final String bee_18 = "[c(H)]";
    public static final String bee_19 = "[c:@]";
    public static final String bee_20 = "[c:s]";
    public static final String bee_21 = "[c:$]";
    public static final String bee_22 = "[c:(]";
    public static final String bee_23 = "[c:'(]";
    public static final String bee_24 = "[c:|]";
    public static final String bee_25 = "[d):]";
    public static final String bee_26 = "[d:D]";
    public static final String bee_27 = "[d;)]";
    public static final String bee_28 = "[d:-o]";
    public static final String bee_29 = "[d:p]";
    public static final String bee_30 = "[d(H)]";
    public static final String bee_31 = "[d:@]";
    public static final String bee_32 = "[d:s]";
    public static final String bee_33 = "[d:$]";
    public static final String bee_34 = "[d:(]";
    public static final String bee_35 = "[d:'(]";
    public static final String bee_36 = "[d:|]";
    public static final String bee_37 = "[e):]";
    public static final String bee_38 = "[e:D]";
    public static final String bee_39 = "[e;)]";
    public static final String bee_40 = "[e:-o]";
    public static final String bee_41 = "[e:p]";
    public static final String bee_42 = "[e(H)]";
    public static final String bee_43 = "[e:@]";
    public static final String bee_44 = "[e:s]";
    public static final String bee_45 = "[f;)]";
    public static final String bee_46 = "[f:-o]";
    public static final String bee_47 = "[f:p]";
    public static final String bee_48 = "[f(H)]";
    public static final String bee_49 = "[f:@]";
    public static final String bee_50 = "[f:s]";
    public static final String bee_51 = "[g;)]";
    public static final String bee_52 = "[g:-o]";
    public static final String bee_53 = "[g:p]";
    public static final String bee_54 = "[g(H)]";
    public static final String bee_55 = "[g:@]";
    public static final String bee_56 = "[g:s]";
    public static final String bee_57 = "[h;)]";
    public static final String bee_58 = "[h:-o]";
    public static final String bee_59 = "[h:p]";
    public static final String bee_60 = "[h(H)]";

    public static final String bee2_01 = "[微笑]";
    public static final String bee2_02 = "[大笑]";
    public static final String bee2_03 = "[憨笑]";
    public static final String bee2_04 = "[喜欢]";
    public static final String bee2_05 = "[笑出泪]";
    public static final String bee2_06 = "[流汗]";
    public static final String bee2_07 = "[皱眉]";
    public static final String bee2_08 = "[流泪]";
    public static final String bee2_09 = "[惊吓]";
    public static final String bee2_10 = "[亲亲]";

    public static final String bee2_11 = "[开心]";
    public static final String bee2_12 = "[大叫]";
    public static final String bee2_13 = "[嘟嘴]";
    public static final String bee2_14 = "[呲牙]";
    public static final String bee2_15 = "[酷]";
    public static final String bee2_16 = "[无奈]";
    public static final String bee2_17 = "[呕吐]";
    public static final String bee2_18 = "[无语]";
    public static final String bee2_19 = "[愉快]";
    public static final String bee2_20 = "[吐舌]";

    public static final String bee2_21 = "[惊喜]";
    public static final String bee2_22 = "[坏笑]";
    public static final String bee2_23 = "[傲慢]";
    public static final String bee2_24 = "[害羞]";
    public static final String bee2_25 = "[天使]";
    public static final String bee2_26 = "[得意]";
    public static final String bee2_27 = "[可爱]";
    public static final String bee2_28 = "[晕]";
    public static final String bee2_29 = "[无聊]";
    public static final String bee2_30 = "[委屈]";

    public static final String bee2_31 = "[怒火]";
    public static final String bee2_32 = "[哼哼]";
    public static final String bee2_33 = "[睡]";
    public static final String bee2_34 = "[傻笑]";
    public static final String bee2_35 = "[发烧]";
    public static final String bee2_36 = "[叹气]";
    public static final String bee2_37 = "[痛苦]";
    public static final String bee2_38 = "[瞪眼笑]";
    public static final String bee2_39 = "[笑]";
    public static final String bee2_40 = "[瞪眼]";

    public static final String bee2_41 = "[有主意]";
    public static final String bee2_42 = "[发愁]";
    public static final String bee2_43 = "[眯眯眼]";
    public static final String bee2_44 = "[怒]";
    public static final String bee2_45 = "[闭嘴]";
    public static final String bee2_46 = "[抱抱]";
    public static final String bee2_47 = "[爱心]";
    public static final String bee2_48 = "[心碎]";
    public static final String bee2_49 = "[鲜花]";
    public static final String bee2_50 = "[咖啡]";

    public static final String bee2_51 = "[蜡烛]";
    public static final String bee2_52 = "[蛋糕]";
    public static final String bee2_53 = "[猪头]";
    public static final String bee2_54 = "[联系我]";
    public static final String bee2_55 = "[白眼]";
    public static final String bee2_56 = "[偷笑]";
    public static final String bee2_57 = "[眯眼笑]";
    public static final String bee2_58 = "[囧]";
    public static final String bee2_59 = "[弹脑瓜]";
    public static final String bee2_60 = "[必胜]";

    private static final Spannable.Factory spannableFactory = Spannable.Factory.getInstance();

    private static final Map<Pattern, Integer> emoticons = new HashMap<>();
    private static final Map<Pattern, Integer> emoticonsText = new HashMap<>();

    private static int big_simlesSize = 0;

    public static final int[] bigMoticonsResId = {R.mipmap.bee_01, R.mipmap.bee_02, R.mipmap.bee_03,R.mipmap.c_key_red,
            R.mipmap.bee_04, R.mipmap.bee_05, R.mipmap.bee_06, R.mipmap.bee_07, R.mipmap.bee_08,
            R.mipmap.bee_09, R.mipmap.bee_10, R.mipmap.bee_11, R.mipmap.bee_12, R.mipmap.bee_13,
            R.mipmap.bee_14, R.mipmap.bee_15};

    static {

        //大表爱情能够
        addPattern(emoticons, bee_01, R.mipmap.bee_01);
        addPattern(emoticons, bee_02, R.mipmap.bee_02);
        addPattern(emoticons, bee_03, R.mipmap.bee_03);
        addPattern(emoticons, c_key_red, R.mipmap.c_key_red);
        addPattern(emoticons, bee_04, R.mipmap.bee_04);
        addPattern(emoticons, bee_05, R.mipmap.bee_05);
        addPattern(emoticons, bee_06, R.mipmap.bee_06);
        addPattern(emoticons, bee_07, R.mipmap.bee_07);
        addPattern(emoticons, bee_08, R.mipmap.bee_08);
        addPattern(emoticons, bee_09, R.mipmap.bee_09);
        addPattern(emoticons, bee_10, R.mipmap.bee_10);
        addPattern(emoticons, bee_11, R.mipmap.bee_11);
        addPattern(emoticons, bee_12, R.mipmap.bee_12);

        addPattern(emoticons, bee_13, R.mipmap.bee_13);
        addPattern(emoticons, bee_14, R.mipmap.bee_14);
        addPattern(emoticons, bee_15, R.mipmap.bee_15);


        big_simlesSize = emoticons.size();

        addPattern(emoticonsText, bee2_01, R.mipmap.bee_01);
        addPattern(emoticonsText, bee2_02, R.mipmap.bee_02);
        addPattern(emoticonsText, bee2_03, R.mipmap.bee_03);
        addPattern(emoticonsText, bee2_04, R.mipmap.bee_04);
        addPattern(emoticonsText, bee2_05, R.mipmap.bee_05);
        addPattern(emoticonsText, bee2_06, R.mipmap.bee_06);
        addPattern(emoticonsText, bee2_07, R.mipmap.bee_07);
        addPattern(emoticonsText, bee2_08, R.mipmap.bee_08);
        addPattern(emoticonsText, bee2_09, R.mipmap.bee_09);
        addPattern(emoticonsText, bee2_10, R.mipmap.bee_10);

        addPattern(emoticonsText, bee2_11, R.mipmap.bee_11);
        addPattern(emoticonsText, bee2_12, R.mipmap.bee_12);
        addPattern(emoticonsText, bee2_13, R.mipmap.bee_13);
        addPattern(emoticonsText, bee2_14, R.mipmap.bee_14);
        addPattern(emoticonsText, bee2_15, R.mipmap.bee_15);


    }

    private static void addPattern(Map<Pattern, Integer> map, String smile, int resource) {
        map.put(Pattern.compile(smile), resource);
    }

    /**
     * replace existing spannable with smiles
     *
     * @param context
     * @param spannable
     * @return
     */
    public static boolean addSmiles(Context context, Spannable spannable) {
        boolean hasChanges = false;
        for (Map.Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(spannable);
            while (matcher.find()) {
                boolean set = true;
                for (ImageSpan span : spannable.getSpans(matcher.start(), matcher.end(), ImageSpan.class))
                    if (spannable.getSpanStart(span) >= matcher.start() && spannable.getSpanEnd(span) <= matcher.end())
                        spannable.removeSpan(span);
                    else {
                        set = false;
                        break;
                    }
                if (set) {
                    hasChanges = true;
                    spannable.setSpan(new ImageSpan(context, entry.getValue()), matcher.start(), matcher.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return hasChanges;
    }

    /**
     * replace existing spannable with smiles
     *
     * @param context
     * @param spannable
     * @return
     */
    public static boolean addSmilesText(Context context, Spannable spannable, int type) {
        boolean hasChanges = false;
        for (Map.Entry<Pattern, Integer> entry : emoticonsText.entrySet()) {
            String s = entry.getKey().toString();
            if (s.contains("[")){
                s = s.replace("[", "");
            }
            if (s.contains("]")){
                s = s.replace("]", "");
            }
            Pattern pattern = Pattern.compile("\\[" + s + "\\]");
            Matcher matcher = pattern.matcher(spannable);
            while (matcher.find()) {
                boolean set = true;
                for (ImageSpan span : spannable.getSpans(matcher.start(), matcher.end(), ImageSpan.class))
                    if (spannable.getSpanStart(span) >= matcher.start() && spannable.getSpanEnd(span) <= matcher.end())
                        spannable.removeSpan(span);
                    else {
                        set = false;
                        break;
                    }
                if (set) {
                    hasChanges = true;
                    //spannable.setSpan(new ImageSpan(context, entry.getValue()), matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    Drawable drawable = context.getResources().getDrawable(entry.getValue());
                    if (ScreenUtils.getScreenHeight(context) >= 1800) {
                        if (1 == type) {
                            drawable.setBounds(0, 0, 70, 70);
                        } else {
                            drawable.setBounds(0, 0, 60, 60);
                        }
                    } else {
                        if (1 == type) {
                            drawable.setBounds(0, 0, 50, 50);
                        } else {
                            drawable.setBounds(0, 0, 45, 45);
                        }
                    }
                    ImageSpan imageSpan = new ImageSpan(drawable);
                    spannable.setSpan(imageSpan, matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return hasChanges;
    }

    public static Spannable getSmiledText(Context context, CharSequence text) {
        Spannable spannable = spannableFactory.newSpannable(text);
        addSmiles(context, spannable);
        return spannable;
    }

    public static Spannable getSmiledTextStr(Context context, CharSequence text, int type) {
        Spannable spannable = spannableFactory.newSpannable(text);
        addSmilesText(context, spannable, type);
        return spannable;
    }

    /**
     * 返回该表情针对的传输语言
     *
     * @param red
     * @return
     */
    public static String getSelectSmile2Value(@DrawableRes int red) {
        if (red == 0) {
            return "";
        }
        String s = "";
        for (Map.Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            if (entry.getValue() == red) {
                s = entry.getKey().pattern();
                break;
            }
        }
        return s;
    }

    /**
     * 返回该小表情针对的传输语言
     *
     * @param red
     * @return
     */
    public static String getSelectSmall2Value(@DrawableRes int red) {
        if (red == 0) {
            return "";
        }
        String s = "";
        for (Map.Entry<Pattern, Integer> entry : emoticonsText.entrySet()) {
            if (entry.getValue() == red) {
                s = entry.getKey().pattern();
                break;
            }
        }
        return s;
    }

    /**
     * 根据字符串匹配 资源文件
     *
     * @param str
     * @return
     */
    public static int getSelectRes2Int(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int id = 0;
        for (Map.Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            if (entry.getKey().pattern().equals(str)) {
                id = entry.getValue();
                break;
            }
        }
        return id;
    }

    /**
     * 资源文件中是否包含此 表情图片
     *
     * @param red
     * @return
     */
    public static boolean containsValue(@DrawableRes int red) {

        if (red == 0) {
            return false;
        }
        boolean b = false;
        for (Map.Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            if (entry.getValue() == red) {
                b = true;
                break;
            }
        }
        return b;
    }

    public static int getDrawResourceID(Context ctx, String resourceName) {
        Resources res=ctx.getResources();
        int picid = res.getIdentifier(resourceName,"mipmap",ctx.getPackageName());
        return picid;
    }

    /**
     * 检测是否包含 该表情对应的图片
     *
     * @param key
     * @return
     */
    public static boolean containsKey(String key) {
        boolean b = false;
        for (Map.Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(key);
            if (matcher.find()) {
                b = true;
                break;
            }
        }
        return b;
    }

    public static int getBigSmilesSize() {
        return big_simlesSize;
    }

}
