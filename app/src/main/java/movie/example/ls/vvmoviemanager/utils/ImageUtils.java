package movie.example.ls.vvmoviemanager.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import movie.example.ls.vvmoviemanager.R;
import movie.example.ls.vvmoviemanager.application.BaseApplication;

/**
 * 图片下载工具
 * 
 * @author wangpc
 */
public class ImageUtils {

	public static final String TAG = "------ImageUtils------";

	// 第二 1)：图片按比例大小压缩方法（根据路径获取图片并压缩）：
	public static Bitmap getImageNoCompress(String srcPath) {

		BitmapFactory.Options newOpts = new BitmapFactory.Options();

		newOpts.inJustDecodeBounds = true;// 开始读入图片，此时把options.inJustDecodeBounds 设回true了

		BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bitmap为null

		newOpts.inJustDecodeBounds = false;

		int w = newOpts.outWidth;
		int h = newOpts.outHeight;

		float myH = screenHeight();
		float myW = screenWidth();

		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放

		if (w > h && w > myW) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / myW);
		} else if (w < h && h > myH) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / myH);
		}

		if (be <= 0)
			be = 1;

		newOpts.inSampleSize = be;// 设置缩放比例
		// 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		return BitmapFactory.decodeFile(srcPath, newOpts);
	}


	/**
	 *
	 * 动态改变View的高宽
	 *
	 * @param
	 * @param
	 * @param
	 */
	public static void setViewSize(Context context, View view, float width, float height) {
		LayoutParams lp = (LayoutParams) view.getLayoutParams();
		if (width == LayoutParams.MATCH_PARENT) {
			lp.width = LayoutParams.MATCH_PARENT;
		} else if (width == LayoutParams.WRAP_CONTENT) {
			lp.width = LayoutParams.WRAP_CONTENT;
		} else {
			lp.width = MathUtils.dip2px(context, width);
		}
		if (height == LayoutParams.MATCH_PARENT) {
			lp.height = LayoutParams.MATCH_PARENT;
		} else if (height == LayoutParams.WRAP_CONTENT) {
			lp.height = LayoutParams.WRAP_CONTENT;
		} else {
			lp.height = MathUtils.dip2px(context, height);
		}
		view.setLayoutParams(lp);

	}

	public static int screenWidth() {
		WindowManager wm = (WindowManager) BaseApplication.getmContext().getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	public static int screenHeight() {
		WindowManager wm = (WindowManager) BaseApplication.getmContext().getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/**
	 * 加载图片
	 * @param mContext
	 * @param path
	 * @param target
	 * @param isAvatar [0]：是否是加载头像  [1]：是否是加载本地
	 */
	public static void loadImage(Context mContext, String path, ImageView target, boolean... isAvatar) {
		boolean avatar = isAvatar.length > 0 && isAvatar[0];
		boolean isLocal = isAvatar.length > 1 && isAvatar[1];
		if (TextUtils.isEmpty(path)) {
			if (avatar) {
				target.setImageResource(R.mipmap.ic_launcher);
			}
			return;
		}
		String headPath = path;
		if (!isLocal && headPath.charAt(0) != 'h') {
//			headPath = ServerUrl.SERVICE + path;
		}
		//        target.setTag(null);
		if (avatar)
			target.setImageResource(R.mipmap.ic_launcher);
		try {
			File file=new File(headPath);
			if(file.exists()){
				Glide.with(mContext).load(file).crossFade(100).into(target);
			}else{
				Glide.with(mContext).load(headPath).crossFade(100).into(target);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		target.setVisibility(View.VISIBLE);
	}

	public static void loadLocalImage(Context mContext, String path, ImageView target, boolean... isAvatar) {
		boolean avatar = isAvatar.length > 0 && isAvatar[0];
		if (TextUtils.isEmpty(path)) {
			if (avatar) {
				target.setImageResource(R.mipmap.ic_launcher);
			}
			return;
		}

		File f = new File(path);
		if (!f.exists())
			return;

		// target.setTag(null);
		if (avatar)
			target.setImageResource(R.mipmap.ic_launcher);
		try {
			Glide.with(mContext).load(f).crossFade(0).into(target);
		} catch (Exception e) {
			e.printStackTrace();
		}
		target.setVisibility(View.VISIBLE);
	}

	public static void loadImage(Context mContext, String path, ImageView target, ImageView target1) {
		if (TextUtils.isEmpty(path)) {
			return;
		}
		target.setTag(null);
		target1.setTag(null);

		try {
			DrawableTypeRequest<String> r = Glide.with(mContext).load(path);
			r.crossFade(0);
			r.into(target);
			r.into(target1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadImageWithSize(Context mContext, String path, ImageView target, int width, int height) {
		if (TextUtils.isEmpty(path)) {
			return;
		}
		target.setTag(null);
		try {
			DrawableTypeRequest<String> r = Glide.with(mContext).load(path);
			r.crossFade(0);
			r.override(width, height).into(target);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadImageWithSize(final Context mContext, final String path, String smallPath, final ImageView target, final int width, final int height) {
		if (TextUtils.isEmpty(path)) {
			return;
		}
		target.setTag(null);
		try {
			DrawableTypeRequest<String> r = Glide.with(mContext).load(smallPath);
			r.crossFade(0);
			if (width != 0 && height != 0) {
				r.override(width, height);
			}
			r.into(new GlideDrawableImageViewTarget(target) {

				@SuppressLint("NewApi")
				@Override
				public void onResourceReady(final GlideDrawable arg0, GlideAnimation<? super GlideDrawable> arg1) {
					super.onResourceReady(arg0, arg1);
					//                    target.setBackground(null);
					DrawableTypeRequest<String> r2 = Glide.with(mContext).load(path);
					r2.crossFade(800);
					if (width != 0 && height != 0) {
						r2.override(width, height);
					}
					r2.placeholder(arg0).into(target);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadImage(final Context mContext, final String path, String smallPath, final ImageView target) {
		if (TextUtils.isEmpty(smallPath)) {
			return;
		}
		target.setTag(null);
		try {
			Glide.with(mContext).load(smallPath).crossFade(0).into(new GlideDrawableImageViewTarget(target) {

				@Override
				public void onResourceReady(GlideDrawable arg0, GlideAnimation<? super GlideDrawable> arg1) {
					super.onResourceReady(arg0, arg1);
					loadImage(mContext, path, target);
				}
			});
			target.setVisibility(View.VISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadImageInitColor(final Context mContext, final String color, final String path,
			String smallPath, final ImageView target, final ImageView mapTarget, boolean... isLoadAll) {
		boolean loadAll = isLoadAll.length > 0 && isLoadAll[0];
		if (TextUtils.isEmpty(smallPath)) {
			return;
		}
		// int red = 255;
		// int green = 255;
		// int blue = 255;
		if ((!TextUtils.isEmpty(color)) && color.length() == 6) {
			// red = Integer.valueOf(color.subSequence(0, 2).toString(), 16);
			// green = Integer.valueOf(color.subSequence(2, 4).toString(), 16);
			// blue = Integer.valueOf(color.subSequence(4, 6).toString(), 16);
			target.setBackgroundColor(Color.parseColor("#" + color));
			mapTarget.setBackgroundColor(Color.parseColor("#" + color));
			mapTarget.setImageBitmap(null);
			target.setImageBitmap(null);
		}
		// target.setTag(smallPath);
		try {
			DrawableTypeRequest<String> r = Glide.with(mContext).load(path);
			if (loadAll) {
				r.diskCacheStrategy(DiskCacheStrategy.ALL);

			}
			r.into(mapTarget);
			r.crossFade(800).into(new GlideDrawableImageViewTarget(target) {

				@SuppressLint("NewApi")
				@Override
				public void onResourceReady(GlideDrawable arg0, GlideAnimation<? super GlideDrawable> arg1) {
					super.onResourceReady(arg0, arg1);
					target.setBackground(null);
					mapTarget.setBackground(null);
					// loadImage(mContext, path, target,mapTarget);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		target.setVisibility(View.VISIBLE);
		mapTarget.setVisibility(View.VISIBLE);
	}

	public static void loadImageInitColor(final Context mContext, final String color, final String path,
			String smallPath, final ImageView target, boolean... isLoadAll) {
		boolean loadAll = isLoadAll.length > 0 && isLoadAll[0];
		if (TextUtils.isEmpty(smallPath)) {
			return;
		}
		if ((!TextUtils.isEmpty(color)) && color.length() == 6) {
			target.setBackgroundColor(Color.parseColor("#" + color));
			target.setImageBitmap(null);
		}
		// target.setTag(smallPath);
		try {
			DrawableTypeRequest<String> r = Glide.with(mContext).load(smallPath);
			if (loadAll) {
				r.diskCacheStrategy(DiskCacheStrategy.ALL);
			}
			r.crossFade(0).into(new GlideDrawableImageViewTarget(target) {

				@SuppressLint("NewApi")
				@Override
				public void onResourceReady(final GlideDrawable arg0, GlideAnimation<? super GlideDrawable> arg1) {
					super.onResourceReady(arg0, arg1);
					//                    target.setBackground(null);
					try {
						Glide.with(mContext).load(path).placeholder(arg0).crossFade(800).into(target);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			target.setVisibility(View.VISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadImageInitColor(final Context mContext, final String color, final String path,
			String smallPath, final ImageView target, final int width, final int height, boolean... isLoadAll) {
		boolean loadAll = isLoadAll.length > 0 && isLoadAll[0];
		if (TextUtils.isEmpty(smallPath)) {
			return;
		}
		if ((!TextUtils.isEmpty(color)) && color.length() == 6) {
			target.setBackgroundColor(Color.parseColor("#" + color));
			target.setImageBitmap(null);
		}
		// target.setTag(smallPath);
		try {
			DrawableTypeRequest<String> r = Glide.with(mContext).load(smallPath);
			if (loadAll) {
				r.diskCacheStrategy(DiskCacheStrategy.ALL);
			}
			r.crossFade(0).override(width, height).into(new GlideDrawableImageViewTarget(target) {

				@SuppressLint("NewApi")
				@Override
				public void onResourceReady(final GlideDrawable arg0, GlideAnimation<? super GlideDrawable> arg1) {
					super.onResourceReady(arg0, arg1);
					//                    target.setBackground(null);
					try {
						Glide.with(mContext).load(path).placeholder(arg0).crossFade(800).override(width, height).into(target);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			target.setVisibility(View.VISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadImageInitColor(final Context mContext, final String color, final String path,
			final ImageView target, boolean... isLoadAll) {
		boolean loadAll = isLoadAll.length > 0 && isLoadAll[0];
		if (TextUtils.isEmpty(path)) {
			return;
		}
		if ((!TextUtils.isEmpty(color)) && color.length() == 6) {
			target.setBackgroundColor(Color.parseColor("#" + color));
			target.setImageBitmap(null);
		}
		// target.setTag(smallPath);
		try {
			DrawableTypeRequest<String> r = Glide.with(mContext).load(path);
			if (loadAll) {
				r.diskCacheStrategy(DiskCacheStrategy.ALL);
			}
			r.crossFade(0).into(target);
			target.setVisibility(View.VISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadImageInitColor(final Context mContext, final String color, final String path,
			final ImageView target, int width, int height, boolean... isLoadAll) {
		boolean loadAll = isLoadAll.length > 0 && isLoadAll[0];
		if (TextUtils.isEmpty(path)) {
			return;
		}
		if ((!TextUtils.isEmpty(color)) && color.length() == 6) {
			target.setBackgroundColor(Color.parseColor("#" + color));
			target.setImageBitmap(null);
		}
		// target.setTag(smallPath);
		try {
			DrawableTypeRequest<String> r = Glide.with(mContext).load(path);
			if (loadAll) {
				r.diskCacheStrategy(DiskCacheStrategy.ALL);
			}
			r.crossFade(0).override(width, height).into(target);
			target.setVisibility(View.VISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Bitmap getBitmap(final Context mContext, final OnGlideDownLoadBitmapListener onGlideDownLoadBitmapListener,
			final String path, int width, int height) {
		if (TextUtils.isEmpty(path)) {
			return null;
		}
		try {
			DrawableTypeRequest<String> r = Glide.with(mContext).load(path);
			//          try {
			if (width == 0 || height == 0) {
				r.asBitmap().centerCrop().into(new SimpleTarget() {

					@Override
					public void onResourceReady(Object bitmap, GlideAnimation arg1) {
						if (onGlideDownLoadBitmapListener != null) {
							onGlideDownLoadBitmapListener.onDownloadFinish((Bitmap) bitmap);
						}
					}
				});
			} else {
				r.asBitmap().centerCrop().into(new SimpleTarget(width, height) {

					@Override
					public void onResourceReady(Object bitmap, GlideAnimation arg1) {
						if (onGlideDownLoadBitmapListener != null) {
							onGlideDownLoadBitmapListener.onDownloadFinish((Bitmap) bitmap);
						}
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取视频图片
	 * @param filePath
	 * @param tofile
	 * @return
	 */
	static public File getVideoThumbnail(String filePath, String tofile) {
		Bitmap bitmap = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			retriever.setDataSource(filePath);
//            retriever.getFrameAtTime()

			bitmap = retriever.getFrameAtTime();

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			try {
				retriever.release();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}

		File f = saveBitmapAbsolutePath(bitmap, tofile);
		if (bitmap != null)
			bitmap.recycle();
		return f;

	}

	/**
	 * 获取视频中间针
	 * @param filePath
	 * @param tofile
	 * @param duration
	 * @return
	 */
	static public File getVideoThumbnailCenter(String filePath, String tofile, int duration) {
		Bitmap bitmap = null;
		File f = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			retriever.setDataSource(filePath);
			bitmap = retriever.getFrameAtTime(duration/2 *1000);


			f = saveBitmapAbsolutePath(bitmap, tofile);
			if (bitmap != null)
				bitmap.recycle();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} finally {
			try {
				retriever.release();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}

		return f;

	}

	public static File saveBitmapAbsolutePath(Bitmap bm, String filename) {
		File f = new File(filename);
		if (bm == null)
			return f;
		try {
			if (f.exists()) {
				f.delete();
			}

			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


		return f;
	}


	/**
	 * 根据图库图片path获得图片文件
	 * @param filePath 选择的图片path
	 * @return File 图片文件
	 */
	public static File getPicByPath(Context mContext, String filePath) {
		File f = new File(filePath);
		if (!f.exists()) {
			return null;
		}
		Uri selectedImage = Uri.fromFile(f);
		String[] filePathColumn = { MediaStore.Images.Media.DATA };
		Cursor cursor = mContext.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
		if (cursor != null) {
			cursor.moveToFirst();
			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			cursor = null;

			if (picturePath == null || picturePath.equals("null")) {
//				Toast toast = Toast.makeText(mContext, R.string.cant_find_pictures, Toast.LENGTH_SHORT);
//				toast.setGravity(Gravity.CENTER, 0, 0);
//				toast.show();
				return null;
			}
			return new File(picturePath);
		} else {
			File file = new File(selectedImage.getPath());
			if (!file.exists()) {
//				Toast toast = Toast.makeText(mContext, R.string.cant_find_pictures, Toast.LENGTH_SHORT);
//				toast.setGravity(Gravity.CENTER, 0, 0);
//				toast.show();
				return null;

			}
			return new File(file.getAbsolutePath());
		}
	}

	public static File savePicture(Bitmap bm) {
		File fileFolder = new File(DataPath.getDirectory(DataPath.DATA_PATH_CAMERA));
		if (!fileFolder.exists()) {
			fileFolder.mkdir();
		}

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss"); // 格式化时间
		String filename = format.format(date) + ".jpg";

		File jpgFile = new File(fileFolder.getPath(), filename);

		if (jpgFile.exists()) {
			jpgFile.delete();
		}

		try {
			FileOutputStream out = new FileOutputStream(jpgFile);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jpgFile;
	}

	public static int getVideoTime(String fileName) {
		MediaMetadataRetriever mmr = new MediaMetadataRetriever();
		mmr.setDataSource(fileName);
		String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
		mmr.release();
		mmr = null;
		return  (int)(Long.parseLong(duration )/1000);
	}

	public static boolean deleteSmallText(EditText chatInputEt) {
		String text = chatInputEt.getText().toString();
		if (!TextUtils.isEmpty(text)) {
			if ("]".equals(text.subSequence(text.length() - 1, text.length()))) {
				int lastIndexOf = text.lastIndexOf("[");
				chatInputEt.getText().delete(lastIndexOf, text.length());
				return true;
			}
		}
		return false;
	}




	public interface OnGlideDownLoadBitmapListener {
		public void onDownloadFinish(Bitmap bitmap);
	}


	public static int dipToPX( Context ctx, float dip) {
		return  (int)(dip  * ctx.getResources().getDisplayMetrics().density + 0.5f);
	}

	public static void loadImageWithListener(Context mContext, String path, ImageView target, final Callback callBack) {
		if (TextUtils.isEmpty(path)) {
			return;
		}
		try {
			Glide.with(mContext).load(path).crossFade(0).into(new GlideDrawableImageViewTarget(target) {

				@Override
				public void onResourceReady(GlideDrawable drawable, GlideAnimation<? super GlideDrawable> arg1) {
					super.onResourceReady(drawable, arg1);
					int w = drawable.getIntrinsicWidth();
					int h = drawable.getIntrinsicHeight();
					Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
							: Bitmap.Config.RGB_565;
					Bitmap bitmap = Bitmap.createBitmap(w, h, config);
					// 注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
					Canvas canvas = new Canvas(bitmap);
					drawable.setBounds(0, 0, w, h);
					drawable.draw(canvas);
					if (callBack != null) {
						callBack.onResourceReady(bitmap, drawable);
						if (bitmap != null)
							bitmap.recycle();
					}
					bitmap.recycle();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		target.setVisibility(View.VISIBLE);
	}

	public static void loadImageWithListener(Context mContext, String path, ImageView target) {
		if (TextUtils.isEmpty(path)) {
			return;
		}
		try {
			Glide.with(mContext).load(path).crossFade(0).into(new GlideDrawableImageViewTarget(target) {

				@Override
				public void onResourceReady(GlideDrawable drawable, GlideAnimation<? super GlideDrawable> arg1) {
					super.onResourceReady(drawable, arg1);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		target.setVisibility(View.VISIBLE);
	}

	public interface Callback {
		public void onResourceReady(Bitmap bitmap, GlideDrawable drawable);
	}

	/**
	 * 根据协定的编号获取相应的表情ResID
	 *
	 * @param i协定序号
	 */
//	public static int getFaceId(int i) {
//		return i < EaseSmileUtils.bigMoticonsResId.length ? EaseSmileUtils.bigMoticonsResId[i - 1] : -1;
//	}

	/**
	 * 保存图片到本地
	 *
	 * @param bitmap 图片对象
	 */
	public static String saveBitmap(Bitmap bitmap) {
		File file = new File(DataPath.getDirectory(DataPath.DATA_PATH));
		if (!file.exists()) {
			file.mkdirs();
		}
		File file1 = new File(DataPath.getDirectory(DataPath.DATA_PATH_PIC));
		if (!file1.exists()) {
			file1.mkdirs();
		}
		File f = new File(DataPath.getDirectory(DataPath.DATA_PATH_PIC),
				System.currentTimeMillis() + ".png");
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
//			Toast.makeText(EaseConstant.APP, "保存成功", Toast.LENGTH_SHORT).show();
			return f.getAbsolutePath();
		} catch (FileNotFoundException e) {
//			Toast.makeText(EaseConstant.APP, "保存失败", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		} catch (IOException e) {
//			Toast.makeText(EaseConstant.APP, "保存失败", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return "";
	}

	// 根据图片size计算imageview's size
	public static int[] calcImageViewSize(float imgW, float imgH, float maxW, float maxH, float minW, float minH) {

		float f = 0.0f;
		float viewW, viewH;
		int[] res = new int[2];

		if (imgW >= imgH) {
			if (imgW >= maxW) {
				viewW = maxW;
				f = maxW / imgW;
				viewH = imgH * f;
			} else {
				viewW = imgW;
				viewH = imgH;
			}
		} else {
			if (imgH >= maxH) {
				viewH = maxH;
				f = maxH / imgH;
				viewW = imgW * f;
			} else {
				viewW = imgW;
				viewH = imgH;
			}
		}

		if (viewW >= viewH) {
			if (viewW < minW) {
				f = minW / viewW;
				viewW *= f;
				viewH *= f;
			}
		} else {
			if (viewH < minH) {
				f = minH / viewH;
				viewW *= f;
				viewH *= f;
			}
		}

		res[0] = (int) viewW;
		res[1] = (int) viewH;
		return res;
	}

	// 根据图片size和ImageView max/min size设置ImageWiew width/height
	public static void setImageViewSize(ImageView iv, int imgW, int imgH, float maxW, float maxH, float minW, float minH) {
		int[] ivSize = calcImageViewSize(imgW, imgH, maxW, maxH, minW, minH);

		// Logger.e("wwwwwwwwwwwwwwwwwwwwwww", " ivSize width=" + ivSize[0]//
		// + " height=" + ivSize[1]//
		// );

		LayoutParams lp = iv.getLayoutParams();
		lp.width = ivSize[0];
		lp.height = ivSize[1];
		iv.setLayoutParams(lp);
	}

	// 根据原图size计算缩略图size
	public static int[] calcThumbImageSize(final float max, float imgW, float imgH) {
		float i = -1f;
		int[] res = new int[2];

		if (imgW > imgH && imgW > max) {
			i = imgW;
		} else if (imgH > imgW && imgH > max) {
			i = imgH;
		}

		if (i > 0f) {
			float f = max / i;

			res[0] = (int) (imgW * f);
			res[1] = (int) (imgH * f);
		} else {
			res[0] = (int) imgW;
			res[1] = (int) imgH;
		}

		return res;
	}

	/** 
	 *  处理图片  
	 * @param bm 所要转换的bitmap 
	 * @return 指定宽高的bitmap
	 */ 
	public static Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片   www.2cto.com
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
		return newbm;
	}

	public static boolean imageIsTooLong(Context context, int width, int height) {
		return width > ScreenUtils.getScreenWidth(context) * 3
				|| height > ScreenUtils.getScreenHeight(context) * 3
				|| (width != 0 && height != 0 && (width > height ? width / height : height / width) > 3);
	}

	public static File image2jpg(File file) {
		File newFile = null;
		// 文件转成byte
		byte[] buffer = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] bb = new byte[1024];
			int n;
			while ((n = fis.read(bb)) != -1) {
				baos.write(bb, 0, n);
			}
			fis.close();
			baos.close();
			buffer = baos.toByteArray();
			// byte转成PNG再转成File
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			Bitmap bitmap = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
//			newFile = EaseImageUtils.saveBitmap(bitmap, "new_" + file.getName());
		} catch (Exception e1) {
			e1.printStackTrace();
			return file;
		}
		return newFile;
	}

	/**
	 * 让Gallery上能马上看到该图片
	 */
	public static void scanPhoto(Context ctx, String imgFileName) {
		Intent mediaScanIntent = new Intent(
				Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File file = new File(imgFileName);
		Uri contentUri = Uri.fromFile(file);
		mediaScanIntent.setData(contentUri);
		ctx.sendBroadcast(mediaScanIntent);
	}

	public static void ToastShort(String msg){
	};
}
