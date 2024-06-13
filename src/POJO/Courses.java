package POJO;

import java.util.List;
import POJO.Course.API;
import POJO.Course.Mobile;
import POJO.Course.WebAutomation;

public class Courses {
	
	private List<WebAutomation> webAutomation;
	private List<API> api;
	private List<Mobile> mobile;
	
	public Courses() {
	}

	public List<WebAutomation> getWebAutomation() {
		return webAutomation;
	}

	public void setWebAutomation(List<WebAutomation> webAutomation) {
		this.webAutomation = webAutomation;
	}

	public List<API> getApi() {
		return api;
	}

	public void setApi(List<API> api) {
		this.api = api;
	}

	public List<Mobile> getMobile() {
		return mobile;
	}

	public void setMobile(List<Mobile> mobile) {
		this.mobile = mobile;
	}

	
}
