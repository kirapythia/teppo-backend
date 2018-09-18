//package fi.espoo.pythia.backend.validators;
//
//import static org.hamcrest.CoreMatchers.equalTo;
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertThat;
//
//import java.io.File;
//
//import org.junit.Test;
//
//import fi.espoo.pythia.backend.validators.PlanValidator;
//
//public class ExtractMainNoSubNoTest {
//
//	PlanValidator validator = new PlanValidator();
//	String dirPath = System.getProperty("user.dir");
//
//	File dir = new File(dirPath);
//
//	File mfile1 = new File(dirPath+"\\src\\6034_001.pdf");
//	
//	short mainNo = validator.extractMainNoFromFileName(mfile1.getName());
//	short subNo = validator.extractSubNoFromFileName(mfile1.getName());
//
//	File mfile2 = new File(dirPath+"\\src\\6034_010.pdf");
//	short mainNo2 = validator.extractMainNoFromFileName(mfile2.getName());
//	short subNo2 = validator.extractSubNoFromFileName(mfile2.getName());
//
//	File mfile3 = new File(dirPath+"\\src\\6034_1000.pdf");
//	short mainNo3 = validator.extractMainNoFromFileName(mfile3.getName());
//	short subNo3 = validator.extractSubNoFromFileName(mfile3.getName());
//
//	short actualMainNo = 6034;
//	short actualSubNo = 1;
//
//	short actualMainNo2 = 6034;
//	short actualSubNo2 = 10;
//
//	short actualMainNo3 = 6034;
//	short actualSubNo3 = 1000;
//
//	@Test
//	public void testFile1() {
//		assertThat(actualMainNo, is(equalTo(mainNo)));
//		assertThat(actualSubNo, is(equalTo(subNo)));
//
//	}
//
//	@Test
//	public void testFile2() {
//		assertThat(actualMainNo2, is(equalTo(mainNo2)));
//		assertThat(actualSubNo2, is(equalTo(subNo2)));
//
//	}
//
//	@Test
//	public void testFile3() {
//		assertThat(actualMainNo3, is(equalTo(mainNo3)));
//		assertThat(actualSubNo3, is(equalTo(subNo3)));
//
//	}
//
//}
