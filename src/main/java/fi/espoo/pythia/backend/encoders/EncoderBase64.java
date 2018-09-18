/*
 * this class is never used
 */
package fi.espoo.pythia.backend.encoders;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

public class EncoderBase64 {
	
	
	/**
	 * get base64 encoded String from
	 */
	public static String inputStream2Base64String(S3ObjectInputStream is) {

		byte[] bytes = inputStream2ByteArray(is);
		String returnString = encodeImage(bytes);

		return returnString;
	}

	/**
	 * get String from base64 encoded String
	 * @throws IOException 
	 */
	public static S3ObjectInputStream base64String2InputStream(String json) throws IOException {
		byte[] bytes = decodeImage(json);
		S3ObjectInputStream returnInput = byteArray2InputStream(bytes);
		return returnInput;
	}

	// --------------------------- ENCODING HELP METHODS
	// -------------------------------

	public static S3ObjectInputStream byteArray2InputStream(byte[] bytes) throws IOException {
		InputStream is;
		is = new ByteArrayInputStream(bytes);
		S3ObjectInputStream is3 = new S3ObjectInputStream (is, null);
		is3.read(bytes);
		return is3;
	}
	

	

	public static byte[] inputStreamS3ToByteArray(S3ObjectInputStream is) {

		byte[] bytes = new byte[16384];

		try {
			bytes = IOUtils.toByteArray(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}
	
	
	public static byte[] inputStream2ByteArray(InputStream is) {

		byte[] bytes = new byte[16384];

		try {
			bytes = IOUtils.toByteArray(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}
	

	/**
	 * Encodes the byte array into base64 string
	 *
	 * @param imageByteArray
	 *            - byte array
	 * @return String a {@link java.lang.String}
	 */
	public static String encodeImage(byte[] imageByteArray) {
		return Base64.encodeBase64URLSafeString(imageByteArray);
	}

	/**
	 * Decodes the base64 string into byte array
	 *
	 * @param imageDataString
	 *            - a {@link java.lang.String}
	 * @return byte array
	 */
	public static byte[] decodeImage(String imageDataString) {
		return Base64.decodeBase64(imageDataString);
	}

}
