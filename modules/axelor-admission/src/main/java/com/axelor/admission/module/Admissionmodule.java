package com.axelor.admission.module;

import com.axelor.admission.service.AdmissionService;
import com.axelor.admission.service.AdmissionServiceimpl;
import com.axelor.app.AxelorModule;

public class Admissionmodule extends AxelorModule {
	
	@Override
	protected void configure() {
		// TODO Auto-generated method stub
		bind(AdmissionService.class).to(AdmissionServiceimpl.class);
	}

}
