package com.abk.ensino;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 申請番号をAES256で暗号化
 */
public class Main {

	public static final String ENCRYPT_KEY = "aeonaeonaeonaeonaeonaeonaeonaeon";
	public static final String ENCRYPT_IV = "aeonaeonaeonaeon";

	public static void main(String[] args) {

		if(args.length == 0) {
			System.out.println("暗号化する申請番号を入力して下さい。");
			System.exit(0);
		}

		String path = args.length >= 2 ? args[1] : "/key.properties";
		Map<String, String> keyMap = loadProperties(path);

		// 暗号化メソッド呼出
		System.out.println(encrypt(args[0], keyMap));
	}

	private static Map<String, String> loadProperties(String path) {
		InputStream in = Main.class.getResourceAsStream(path);
		if (in == null) {
			throw new IllegalArgumentException(path + " not found.");
		}
		Properties props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Map<String, String> propMap = new HashMap<String, String>();
		propMap.put("secretkey", props.getProperty("SECRETKEY"));
		propMap.put("initializedvector", props.getProperty("INITIALIZEDVECTOR"));
		return propMap;
	}

	/**
	 * 暗号化メソッド
	 *
	 * @param text 暗号化する文字列
	 * @param keyMap 秘密鍵と初期化ベクトルを格納したマップ
	 * @return 暗号化文字列
	 */
	public static String encrypt(String text, Map<String, String> keyMap) {
		// 秘密鍵と初期化ベクトル取得
		String secretkey = keyMap.get("secretkey");
		String initializedvector = keyMap.get("initializedvector");

		// 変数初期化
		String strResult = null;

		try {
			// 文字列をバイト配列へ変換
			byte[] byteText = text.getBytes("UTF-8");

			// 秘密鍵と初期化ベクトルをバイト配列へ変換
			byte[] byteKey = secretkey.getBytes("UTF-8");
			byte[] byteIv = initializedvector.getBytes("UTF-8");

			// 秘密鍵と初期化ベクトルのオブジェクト生成
			SecretKeySpec key = new SecretKeySpec(byteKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(byteIv);

			// Cipherオブジェクト生成
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			// Cipherオブジェクトの初期化
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);

			// 暗号化の結果格納
			byte[] byteResult = cipher.doFinal(byteText);

			// Base64へエンコード
			strResult = Base64.encodeBase64String(byteResult);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}

		// 暗号化文字列を返却
		return strResult;
	}
}
