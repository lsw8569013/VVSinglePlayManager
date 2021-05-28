package movie.example.ls.vvmoviemanager.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.TextUtils;

import java.util.Random;

//生成验证码
public class SecurityCode {

	private static final char[] CHARS = { '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
			'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
			'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	private static SecurityCode bmpCode;

	public static SecurityCode getInstance() {
		if (bmpCode == null)
			bmpCode = new SecurityCode();
		return bmpCode;
	}

	private static final int DEFAULT_CODE_LENGTH = 4;// 随机字符的个数

	private String code;
	private Random random = new Random();

	/**
	 * 生成验证码图片
	 * 
	 * @param width 宽度
	 * @param height 高度
	 * @param size 字体大小(以sp为单位计算)
	 * @return
	 */
	public Bitmap createBitmap(int width, int height, int size, Context context) {

		int textSize = ScreenUtils.sp2px(context, size);

		Bitmap codeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(codeBitmap);

		TextPaint textPaint = new TextPaint();
		textPaint.setAntiAlias(true);
		textPaint.setTextSize(textSize);
		textPaint.setStrokeWidth(3);
		textPaint.setStyle(Paint.Style.STROKE);
		textPaint.setTextAlign(Paint.Align.CENTER);

		code = createCode();
		int x = (width - textSize * 3) / 2;
		int y = (height + textSize) / 2;
		for (int index = 0; index < code.length(); index++) {
			textPaint.setColor(randomColor(1));
			canvas.drawText(code.charAt(index) + "", (x + textSize * index), y, textPaint);
		}
		Random random = new Random();
		for (int i = 0; i < 5; i++) {
			textPaint.setStrokeWidth(2);
			textPaint.setColor(randomColor(1));
			canvas.drawLine(random.nextInt(width), random.nextInt(height), random.nextInt(width),
					random.nextInt(height), textPaint);
		}
//		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.save();
		canvas.restore();
		return codeBitmap;
	}

	public String getCode() {
		return code;
	}

	public boolean Verification(String input){
		if (TextUtils.isEmpty(code))
			return false;
		
		if (TextUtils.isEmpty(input))
			return false;
		
		return code.equalsIgnoreCase(input);
	}
	
	// 验证码
	private String createCode() {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < DEFAULT_CODE_LENGTH; i++) {
			buffer.append(CHARS[random.nextInt(CHARS.length)]);
		}
		return buffer.toString();
	}

	// 随机颜色
	private int randomColor(int rate) {
		int red = random.nextInt(256) / rate;
		int green = random.nextInt(256) / rate;
		int blue = random.nextInt(256) / rate;
		return Color.rgb(red, green, blue);
	}

}
