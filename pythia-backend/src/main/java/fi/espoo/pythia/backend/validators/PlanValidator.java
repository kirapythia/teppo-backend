package fi.espoo.pythia.backend.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

import fi.espoo.pythia.backend.transfer.PlanValue;

public class PlanValidator {

	// general validation
	public String generalValidation = "^(\\d{4,})_(\\d{3,4})([a-zA-Z]{1,2})?\\.(pdf|xml|dwg)$";
	// ^ (only from beginning), 4 * [0-9]
	// public String mainNoRegex = "^\\d{4}";
	// // anywhere , _ , 1 OR 2 * 0 , 1 OR 2 * [1-9], .
	// String subNoRegexZero = "\\_(0{1,2})[1-9]{1,2}\\.";
	// // anywhere , _ , 3 OR 4 * [0-9], .
	// public String subNoRegexNonZero = "\\_\\d{3,4}\\.";
	// // ^ (only from beginning), 4 * [0-9] , r OR R , _
	// public String referenceRegex = "^\\d{4,5}(r|R)\\_";

	private short mainNo = 0;
	private short subNo = 0;
	private String version = "";

	/**
	 * 
	 * @param mfile
	 * @return
	 */
	public boolean isValidFile(MultipartFile mfile) {
		String name = mfile.getOriginalFilename();
		Pattern pattern = Pattern.compile(generalValidation);
		Matcher matcher = pattern.matcher(name);

		while (matcher.find()) {
			setMainNo(Short.parseShort(matcher.group(1)));
			setSubNo(Short.parseShort(matcher.group(2)));
			setVersion(matcher.group(3));
		}
//
		if (!pattern.matcher(name).find()) {
			 		System.out.println("no match");
			 			return false;
			 
			 		} else {
			 			return true;
			 		}
	}
	
	
	/**
	 * 
	 * @param mfile
	 * @param planV
	 * @return
	 */
	public boolean isMainNoAndSubNo(MultipartFile mfile, PlanValue planV) {

		if (isValidFile(mfile)) {
			short planMainNo = planV.getMainNo();
			short planSubNo = planV.getSubNo();
			short mFileMainNo = getMainNo();
			short mFileSubNo = getSubNo();
			if (planMainNo == mFileMainNo && planSubNo == mFileSubNo) {
				return true;
			}
		}
		return false;
	}

	
	public short getMainNo() {
		return mainNo;
	}

	public void setMainNo(short mainNo) {
		this.mainNo = mainNo;
	}

	public short getSubNo() {
		return subNo;
	}

	public void setSubNo(short subNo) {
		this.subNo = subNo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}


}
