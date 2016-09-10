package cn.keepfight.rebatecalculator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;

public class SDCardAccess {

	public static boolean saveManager() {

		ByteArrayOutputStream baos = null;
		byte[] bytes = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			new ObjectOutputStream(baos).writeObject(ItemManager.getInstance());
			bytes = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SDCardHelper.saveFileToPrivateCacheDir(bytes, "manager.ca",
				MyApplication.getContext());
	}

	public static void getManager(Context context) {
		ItemManager res = null;
		File manageFile = new File(SDCardHelper.getPrivateCacheDir(context),
				"manager.ca");
		try {
			res = (ItemManager) new ObjectInputStream(new ByteArrayInputStream(
					SDCardHelper.loadFileFrom(manageFile))).readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		ItemManager.setInstance(res);
	}
}
