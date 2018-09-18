//package fi.espoo.pythia.backend.rest;
//
//import static org.junit.Assert.assertEquals;
//
//import java.io.File;
//import java.io.InputStream;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.RequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import com.amazonaws.services.s3.model.S3ObjectInputStream;
//
//import fi.espoo.pythia.backend.converters.FileConverter;
//import fi.espoo.pythia.backend.encoders.EncoderBase64;
//import fi.espoo.pythia.backend.mgrs.S3Manager;
//import fi.espoo.pythia.backend.mgrs.StorageManager;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = StorageRestController.class, secure = false)
//public class Base64test {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	private S3Manager S3Manager;
//
//	@MockBean
//	S3ObjectInputStream inputStreamS3;
//
//	@MockBean
//	private StorageManager storageManager;
//
//	@Test
//	public void createFile() throws Exception {
//
//		// get system environment variables into a map
//		// s3public
//		// s3private
//		// s3 rest BASE64 JSON encoding
//
//		InputStream inputStream =  FileConverter.fileToInputStream("C:\\Users\\pakars4\\Desktop\\nykytila.jpg");
//		
//		//inputstreamto bytearray
//		byte[] bytes = EncoderBase64.inputStream2ByteArray(inputStream);
//		
//		//bytearray to inputstream s3
//		inputStreamS3 = EncoderBase64.byteArray2InputStream(bytes);
//		
//		String exampleFilePostJson = EncoderBase64.inputStream2Base64String(inputStreamS3);
//
//		// mocks3service
//
//		// storageManager.createPlanFile save a new file to plan s3
//
//		String mockUrl = "https://s3-eu-west-1.amazonaws.com/kirapythia-example-bucket/suunnittelu_prosessi_espoo.png";
//
//		// testaa mockUrlia Mockito.anyString(),
////		Mockito.when(S3Manager.createPlanFile(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
////				.thenReturn(mockUrl);
//
//		// Send project as body to /pythia/v1/projects/
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders
//				.post("/pythia/v1/projects/{projectId}/plans/{planId}/files/").accept(MediaType.APPLICATION_JSON)
//				.content(exampleFilePostJson).contentType(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//		MockHttpServletResponse response = result.getResponse();
//
//		assertEquals(HttpStatus.OK.value(), response.getStatus());
//
//		//
//
//	}
//}
