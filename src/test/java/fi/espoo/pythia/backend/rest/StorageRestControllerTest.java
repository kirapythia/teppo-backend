///**
// * http://www.springboottutorial.com/unit-testing-for-spring-boot-rest-services
// */
//
//package fi.espoo.pythia.backend.rest;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * @author saara
// */
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.skyscreamer.jsonassert.JSONAssert;
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
//import fi.espoo.pythia.backend.mgrs.StorageManager;
//import fi.espoo.pythia.backend.repos.entities.ProjectUpdate;
//import fi.espoo.pythia.backend.transfer.PlanValue;
//import fi.espoo.pythia.backend.transfer.ProjectUpdateValue;
//import fi.espoo.pythia.backend.transfer.ProjectValue2;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(value = StorageRestController.class, secure = false)
//public class StorageRestControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//
//	@MockBean
//	private StorageManager storageManager;
//
//	//Long projectId = 14L;
//	String hansuProjectId = "E3456";
//	String name = "testproject";
//	short mainNo = 2345;
//	String description = "some desc";
//	List<PlanValue> plans = new ArrayList<PlanValue>();
//
//	ProjectUpdateValue mockProjectUpdateValue = new ProjectUpdateValue();
//	ProjectValue2 mockProjectValue2 = new ProjectValue2();
//	ProjectUpdate mockProjectUpdate = new ProjectUpdate();
//
//	String exampleProjectGetJson = "{\"projectId\": 14,\"hansuProjectId\": \"E3456\",\"name\": \"testproject\",\"mainNo\": 2345,\"description\": \"some desc\",\"plans\": []}";
//	String exampleProjectPostJson = "{\"hansuProjectId\": \"E3456\",\"name\": \"testproject\",\"mainNo\": 2345,\"description\": \"some desc\",\"plans\": []}";
//
//	@Test
//	public void retrieveDetailsForProject() throws Exception {
//
//		Mockito.when(storageManager.getProject2(Mockito.anyLong())).thenReturn(mockProjectValue2);
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/pythia/v1/projects/14")
//				.accept(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//		System.out.println(result.getResponse());
//		JSONAssert.assertEquals(exampleProjectGetJson, result.getResponse().getContentAsString(), false);
//	}
//
//	// not working status
//	@Test
//	public void createProject() throws Exception {
//
//		// storageManager.createProject to respond back with mockProject
//		Mockito.when(storageManager.createProject(Mockito.any(ProjectUpdateValue.class))).thenReturn(mockProjectUpdate);
//
//		// Send project as body to /pythia/v1/projects/
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/pythia/v1/projects/")
//				.accept(MediaType.APPLICATION_JSON).content(exampleProjectPostJson)
//				.contentType(MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//
//		MockHttpServletResponse response = result.getResponse();
//
//		assertEquals(HttpStatus.OK.value(), response.getStatus());
//
//	}
//
//}
