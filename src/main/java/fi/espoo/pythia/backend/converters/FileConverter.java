package fi.espoo.pythia.backend.converters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

public class FileConverter {

	// ---------------- FILE METHODS--------------------------------------

	public static File multipartFileToFile(MultipartFile file) throws IOException
	{   
		
	    File convFile = new File(file.getOriginalFilename());
	    
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}
	
	
	public static MultipartFile fileToMultipartFile(File file){
		return null;
	}
	
	
		public static File inputStreamToFile(S3ObjectInputStream inputStream2, String output) {

			InputStream inputStream = inputStream2;
			OutputStream outputStream = null;

			try {
				// read this file into InputStream
				// inputStream = new FileInputStream(input);

				// write the inputStream to a FileOutputStream
				outputStream = new FileOutputStream(new File(output));

				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}

				System.out.println("Done!");

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (outputStream != null) {
					try {
						// outputStream.flush();
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}

			
			File returnFile = new File(output);
			return returnFile;

		}

		
		
		public static File inputStreamToVirtualFile(InputStream in) throws IOException{
			  String PREFIX = "stream2file";
			    String SUFFIX = ".tmp";

			        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
			        tempFile.deleteOnExit();
			        try (FileOutputStream out = new FileOutputStream(tempFile)) {
			            IOUtils.copy(in, out);
			        }
			        return tempFile;
			    
		}
		
		
		
		/**
		 * get InputStream from File
		 * 
		 * S3ObjectInputStream inputStream = downloadObject(s3client, "onnellisuusseina.jpg");
			File onni = inputStreamToFile(inputStream, "C:\\Users\\pakars4\\Desktop\\onni.jpg");
		 * 
		 * @return
		 */
		public static InputStream fileToInputStream(String path) throws IOException {
			File initialFile = new File(path);
			InputStream targetStream = new FileInputStream(initialFile);
			return targetStream;
		}

		
}
