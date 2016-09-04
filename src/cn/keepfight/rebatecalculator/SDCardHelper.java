package cn.keepfight.rebatecalculator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;

public class SDCardHelper {

	/**
	 * 判断SD卡是否被挂载
	 * 
	 * @return 是返回true，否则返回false
	 */
	public static boolean isMounted() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取SD卡的根目录
	 * 
	 * @return 根目录路径名，否无挂载返回null
	 */
	public static String getBaseDir() {
		if (isMounted()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return null;
	}

	/**
	 * 获取SD卡的完整空间大小，返回MB
	 * 
	 * @return SD空间的大小，单位MB，无挂载返回0
	 */
	public static long getSize() {
		if (isMounted()) {
			StatFs fs = new StatFs(getBaseDir());
			long count = fs.getBlockCount();
			long size = fs.getBlockSize();
			return count * size / 1024 / 1024;
		}
		return 0;
	}

	/**
	 * 获取SD卡的剩余空间大小
	 * 
	 * @return SD空间的大小，单位MB，无挂载返回0
	 */
	public static long getFreeSize() {
		if (isMounted()) {
			StatFs fs = new StatFs(getBaseDir());
			long count = fs.getFreeBlocks();
			long size = fs.getBlockSize();
			return count * size / 1024 / 1024;
		}
		return 0;
	}

	/**
	 * 获取SD卡的可用空间大小
	 * 
	 * @return SD空间的大小，单位MB，无挂载返回0
	 */
	public static long getAvailableSize() {
		if (isMounted()) {
			StatFs fs = new StatFs(getBaseDir());
			long count = fs.getAvailableBlocks();
			long size = fs.getBlockSize();
			return count * size / 1024 / 1024;
		}
		return 0;
	}

	/**
	 * 往SD卡的公有目录下保存文件
	 * 
	 * @param data
	 * @param type
	 *            The type of storage directory to return. Should be one of
	 *            {@link #DIRECTORY_MUSIC}, {@link #DIRECTORY_PODCASTS},
	 *            {@link #DIRECTORY_RINGTONES}, {@link #DIRECTORY_ALARMS},
	 *            {@link #DIRECTORY_NOTIFICATIONS}, {@link #DIRECTORY_PICTURES},
	 *            {@link #DIRECTORY_MOVIES}, {@link #DIRECTORY_DOWNLOADS}, or
	 *            {@link #DIRECTORY_DCIM}. May not be null.
	 * @param fileName
	 * @return
	 */
	public static boolean saveFileToPublicDir(byte[] data, String type,
			String fileName) {
		BufferedOutputStream bos = null;
		if (isMounted()) {
			File file = Environment.getExternalStoragePublicDirectory(type);
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(
						file, fileName)));
				bos.write(data);
				bos.flush();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 往SD卡的自定义目录下保存文件，这里的自定义目录，也就是在
	 * 
	 * @param data
	 *            欲保存的文件的字节数据
	 * @param dir
	 *            欲保存的位置，以根目录为起点路径
	 * @param fileName
	 *            欲保存的文件名
	 * @return 保存成功返回true，否则返回false
	 */
	public static boolean saveFileToCustomDir(byte[] data, String dir,
			String fileName) {
		BufferedOutputStream bos = null;
		if (isMounted()) {
			File file = new File(getBaseDir() + File.separator + dir);
			if (!file.exists()) {
				// 递归创建自定义目录
				file.mkdirs();
			}
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(
						file, fileName)));
				bos.write(data);
				bos.flush();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					bos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 往SD卡的指定文件保存数据
	 * 
	 * @param data
	 *            欲保存的文件的字节数据
	 * @param targetFile
	 *            指定文件
	 * @return 保存成功返回true，否则返回false
	 */
	public static boolean saveFileToCustomDir(byte[] data, File targetFile) {
		return saveFileToCustomDir(data, targetFile.getParentFile().getPath(),
				targetFile.getName());
	}

	/**
	 * 往SD卡的指定文件保存字符串数据
	 * 
	 * @param str
	 *            欲保存的字符串数据
	 * @param dir
	 *            欲保存的位置，以根目录为起点路径
	 * @param fileName
	 *            欲保存的文件名
	 * @return 保存成功返回true，否则返回false
	 */
	public static boolean saveFileToCustomDir(String str, String dir,
			String fileName) {
		return saveFileToCustomDir(str.getBytes(), dir, fileName);
	}

	/**
	 * 将指定的数据存储到指定
	 * @param data
	 * @param dirFile
	 * @param fileName
	 * @return
	 */
	public static boolean saveFile(byte[] data, File dirFile, String fileName) {
		BufferedOutputStream bos = null;
		if (isMounted()) {
			if (!dirFile.exists()) {
				// 递归创建自定义目录
				dirFile.mkdirs();
			}
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(
						dirFile, fileName)));
				bos.write(data);
				bos.flush();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					bos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public static boolean saveFile(String str, File dirFile, String fileName) {
		return saveFile(str.getBytes(), dirFile, fileName);
	}
	
	/**
	 * 往SD卡的私有Files目录下保存文件
	 * <hr/>
	 * 这里的私有目录也就是/data/data/com.xxx.xxx/下的
	 * 
	 * @param data
	 *            欲存储的文件字节数据
	 * @param type
	 *            文件的所属类型，也就是九大文件夹内的一个
	 * @param fileName
	 *            欲保存的文件名
	 * @param context
	 *            文件保存所在的环境
	 * @return 保存成功返回true，否则返回false
	 */
	public static boolean saveFileToPrivateFilesDir(byte[] data, String type,
			String fileName, Context context) {
		BufferedOutputStream bos = null;
		if (isMounted()) {
			File file = context.getExternalFilesDir(type);
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(
						file, fileName)));
				bos.write(data);
				bos.flush();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 往SD卡的私有Cache目录下保存文件
	 * <hr/>
	 * 这里的私有目录也就是/data/data/com.xxx.xxx/下的
	 * 
	 * @param data
	 *            欲存储的文件字节数据
	 * @param fileName
	 *            欲保存的文件名
	 * @param context
	 *            文件保存所在的环境
	 * @return 保存成功返回true，否则返回false
	 */
	public static boolean saveFileToPrivateCacheDir(byte[] data,
			String fileName, Context context) {
		BufferedOutputStream bos = null;
		if (isMounted()) {
			File file = context.getExternalCacheDir();
			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(
						file, fileName)));
				bos.write(data);
				bos.flush();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	// 保存bitmap图片到的私有Cache目录
	public static boolean saveBitmapToPrivateCacheDir(Bitmap bitmap,
			String fileName, Context context) {
		if (isMounted()) {
			BufferedOutputStream bos = null;
			// 获取私有的Cache缓存目录
			File file = context.getExternalCacheDir();

			try {
				bos = new BufferedOutputStream(new FileOutputStream(new File(
						file, fileName)));
				if (fileName != null
						&& (fileName.contains(".png") || fileName
								.contains(".PNG"))) {
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
				} else {
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
				}
				bos.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bos != null) {
					try {
						bos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 从SD卡获取文件
	 * 
	 * @param fileDir
	 *            文件目录
	 * @return 读到的文件字节
	 */
	public static byte[] loadFileFrom(String fileDir) {
		return loadFileFrom(new File(fileDir));
	}

	/**
	 * 从指定文件中加载文件数据
	 * 
	 * @param fileDir
	 *            指定文件
	 * @return 文件数据
	 */
	public static byte[] loadFileFrom(File fileDir) {

		BufferedInputStream bis = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			bis = new BufferedInputStream(new FileInputStream(fileDir));
			byte[] buffer = new byte[8 * 1024];
			int c = 0;
			while ((c = bis.read(buffer)) != -1) {
				baos.write(buffer, 0, c);
				baos.flush();
			}
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				baos.close();
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 加载指定路径的bitmap文件，返回Bitmap格式
	 * 
	 * @param filePath
	 *            指定路径
	 * @return Bitmap格式对象，否则返回null
	 */
	public static Bitmap loadBitmapFrom(String filePath) {
		byte[] data = loadFileFrom(filePath);
		if (data != null) {
			Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
			if (bm != null) {
				return bm;
			}
		}
		return null;
	}

	/**
	 * 获取SD卡公有目录的路径
	 * 
	 * @param type
	 *            指定类型
	 * @return SD卡公有目录的路径
	 */
	public static String getPublicDir(String type) {
		return Environment.getExternalStoragePublicDirectory(type).toString();
	}

	/**
	 * 获取SD卡私有Cache目录的路径
	 * 
	 * @param context
	 *            指定环境
	 * @return SD卡私有Cache目录的路径
	 */
	public static String getPrivateCacheDir(Context context) {
		return context.getExternalCacheDir().getAbsolutePath();
	}

	/**
	 * 获取SD卡私有Files目录的路径
	 * 
	 * @param context
	 *            指定环境
	 * @param type
	 *            指定类型
	 * @return SD卡私有Files目录的路径
	 */
	public static String getPrivateFilesDir(Context context, String type) {
		return context.getExternalFilesDir(type).getAbsolutePath();
	}

	/**
	 * 检查文件是否存在
	 * 
	 * @param filePath
	 *            指定文件的路径
	 * @return 存在返回true，否则返回false
	 */
	public static boolean isFileExist(String filePath) {
		File file = new File(filePath);
		return file.isFile();
	}

	/**
	 * 检查指定路径，若不存在则尝试新建它
	 * 
	 * @param dir
	 *            指定路径
	 * @return 存在返回指定路径的文件对象，否则返回null
	 */
	public static File dirCheck(String dir) {
		File path = new File(getBaseDir() + File.separator + dir);
		if (!path.exists() && !path.mkdirs() && !path.isDirectory()) {
			return null;
		}
		return path;
	}

	/**
	 * 删除指定路径文件
	 * 
	 * @param filePath
	 *            指定的路径
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean removeFileFrom(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			try {
				file.delete();
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 删除指定路径下的全部文件
	 * 
	 * @param filePath
	 *            欲删除的路径
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean removeDir(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			try {
				if (file.isDirectory()) {
					for (File f : file.listFiles()) {
						removeDir(f.getPath());
					}
				}
				file.delete();
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	public static void saveStr() {

	}
}
