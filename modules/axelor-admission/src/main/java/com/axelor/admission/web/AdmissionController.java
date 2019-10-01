package com.axelor.admission.web;

import com.axelor.admission.db.AdmissionProcess;
import com.axelor.admission.service.AdmissionService;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;

public class AdmissionController {

	@Inject
	AdmissionService service;

	@Transactional
	public void setCollegeSelected(ActionRequest request, ActionResponse response) {
		AdmissionProcess admissionProcess = request.getContext().asType(AdmissionProcess.class);
		String message = service.updateCollegeSelected(admissionProcess);

		response.setFlash(message);
	}
}
