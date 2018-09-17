package fi.espoo.pythia.backend.mgrs;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.amazonaws.services.s3.model.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import fi.espoo.pythia.backend.converters.FileConverter;

@Component
public class S3Manager {


    public String createPlanMultipartFile(String bucketName, MultipartFile mfile, short version) throws IOException {

        AmazonS3 s3client = this.authenticate();

        File file = FileConverter.multipartFileToFile(mfile);

        String fileName = file.getName();

        int idxDot = fileName.indexOf(".");

        String key_start = fileName.substring(0, idxDot);
        String key_end = fileName.substring(idxDot);

        String key = key_start+"_"+version+key_end;
        //String key = file.getName();
        String url = uploadObject(s3client, file, key, bucketName);
        giveReadPermissionToObject(s3client, bucketName, key);

        return url;
    }

    public String createPlanInputStream(String bucketName, InputStream inputStream, String fileName, short version) throws IOException {

        AmazonS3 s3client = this.authenticate();

        int idxDot = fileName.indexOf(".");

        String key_start = fileName.substring(0, idxDot);
        String key_end = fileName.substring(idxDot);

        String key = key_start+"_"+version+key_end;
        //String key = file.getName();
        String url = uploadStream(s3client, inputStream, key, bucketName);
        giveReadPermissionToObject(s3client, bucketName, key);

        return url;
    }

    // -----------------------AUTHENTICATION WITH ENVIRONMENTAL VARIABLES

    public AmazonS3 authenticate() {
        // Note! the environmental variables are in capitals
        String envPublicKey = System.getenv("AWS_ACCESS_KEY_ID");
        String envPrivateKey = System.getenv("AWS_SECRET_ACCESS_KEY");

        String publicKey = envPublicKey != null ? envPublicKey : "";
        String privateKey = envPrivateKey != null ? envPrivateKey : "";

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

    public String uploadStream(AmazonS3 s3client, InputStream inputStream, String key, String bucketName) {

        s3client.putObject(bucketName, key, inputStream, null);
        URL url = s3client.getUrl(bucketName, key);
        return url.toString();
    }

    /**
     * Give read permission to an object in a bucket (by default everything is private).
     * TODO: This is not necessary when S3 bucket policy will be implemented to terraform scripts
     *
     * @param s3client
     * @param bucketName
     * @param key
     */
    public void giveReadPermissionToObject(AmazonS3 s3client, String bucketName, String key) {
        AccessControlList objectAcl = s3client.getObjectAcl(bucketName, key);
        GroupGrantee allUsers = GroupGrantee.AllUsers;
        objectAcl.grantPermission(allUsers, Permission.Read);
        s3client.setObjectAcl(bucketName, key, objectAcl);
    }

    @SuppressWarnings("unused")
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
}
