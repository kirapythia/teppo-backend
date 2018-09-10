package fi.espoo.pythia.backend.mgrs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import fi.espoo.pythia.backend.converters.FileConverter;
import fi.espoo.pythia.backend.encoders.EncoderBase64;

@Component
public class S3Manager {


	public String createPlanMultipartFile(String bucketName, MultipartFile mfile, short version) throws IOException {

		String publicKey = "";
		String privateKey = "";

		Map<String, String> env = System.getenv();

		Iterator it = env.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if (pair.getKey().equals("AWS_ACCESS_KEY_ID")) {
				publicKey = (String) pair.getValue();
			} else if (pair.getKey().equals("AWS_SECRET_ACCESS_KEY")) {
				privateKey = (String) pair.getValue();
			}
		}

		AWSCredentials credentials = new BasicAWSCredentials(publicKey, privateKey);
		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.EU_WEST_1).build();

		File file = FileConverter.multipartFileToFile(mfile);
//
		String fileName = file.getName();
		
		int point = fileName.indexOf(".");
		
		String key_start = fileName.substring(0, point);
		String key_end = fileName.substring(point);
		
		String key = key_start+"_"+version+key_end;
		//String key = file.getName();
		String url = uploadObject(s3client, file, key, bucketName);

		// UI should not allow but pdf or dwg filetypes.
		// If you want to add more filetypes modify the method
		// getFileList(String dirPath)
		clearFiles();
		return url;

	}

	// -----------------------AUTHENTICATION WITH ENVIRONMENTAL VARIABLES

	public AmazonS3 authenticate() {
		String publicKey = "";
		String privateKey = "";

		Map<String, String> env = System.getenv();
		Iterator it = env.entrySet().iterator();

		// Note! the environmental variables are in capitals
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if (pair.getKey().equals("AWS_ACCESS_KEY_ID")) {
				publicKey = (String) pair.getValue();
			} else if (pair.getKey().equals("AWS_SECRET_ACCESS_KEY")) {
				privateKey = (String) pair.getValue();
			}

		}

		// First, we need to create a client connection to access Amazon S3 web
		// service. We’ll use AmazonS3 interface for this purpose:
		AWSCredentials credentials = new BasicAWSCredentials(publicKey, privateKey);
		// And then configure the client:
		// http://docs.aws.amazon.com/general/latest/gr/rande.html
		// EU Ireland Eu_WEST_1
		AmazonS3 s3client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.EU_WEST_1).build();

		return s3client;
	}

	// --------------------AMAZON S3 METHODS
	// ----------------------------------

	/**
	 * upload inputStream to S3 and return the url of the object
	 * 
	 * @return
	 */
	public String uploadObject(AmazonS3 s3client, File file, String key, String bucketName) {

		s3client.putObject(bucketName, key, file);
		URL url = s3client.getUrl(bucketName, key);
		return url.toString();
	}

	/**
	 * 
	 * @param s3client
	 * @param uploadFile
	 * @return
	 */
	public S3ObjectInputStream downloadObject(AmazonS3 s3client, String downloadFile) {
		String bucketName = "kirapythia-example-bucket";
		S3Object s3object = s3client.getObject(bucketName, downloadFile);
		S3ObjectInputStream inputStream = s3object.getObjectContent();
		return inputStream;
	}

	@SuppressWarnings("unused")
	private void listBuckets(AmazonS3 s3client) {
		List<Bucket> buckets = s3client.listBuckets();
		for (Bucket bucket : buckets) {
			System.out.println(bucket.getName());
		}
	}

	@SuppressWarnings("unused")
	private void createBucket(String bucketName, AmazonS3 s3client) {

		if (s3client.doesBucketExist(bucketName)) {
			System.out.println("Bucket name is not available." + " Try again with a different Bucket name.");
			return;
		}

		s3client.createBucket(bucketName);

	}

	@SuppressWarnings("unused")
	private void deleteBucket(AmazonS3 s3client) {
		// DELETE BUCKET
		try {
			s3client.deleteBucket("saara");
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			return;
		}

	}

	/**
	 * clear temp files pdf, dwg, xml
	 * 
	 * @param path
	 */
	private void clearFiles() {

		String dirPath = System.getProperty("user.dir");
		System.out.println(dirPath);

		File[] files = getFileList(dirPath);

		for (File f : files) {
			System.out.println(f.getName());
			try {
				Files.deleteIfExists(f.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//
		//

	}

	private static File[] getFileList(String dirPath) {
		File dir = new File(dirPath);

		File[] fileList = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".pdf") || name.endsWith(".dwg") || name.endsWith(".xml") || name.endsWith(".DAT")
						|| name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".gif")
						|| name.endsWith(".tiff") || name.endsWith(".dwg");

			}
		});
		return fileList;
	}

}
