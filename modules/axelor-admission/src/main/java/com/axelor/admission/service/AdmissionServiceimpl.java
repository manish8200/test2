package com.axelor.admission.service;

import java.util.Comparator;
import java.util.List;

import com.axelor.admission.db.AdmissionEntry;
import com.axelor.admission.db.AdmissionProcess;
import com.axelor.admission.db.CollegeEntry;
import com.axelor.admission.db.FacultyEntry;
import com.axelor.admission.db.repo.AdmissionEntryRepository;
import com.axelor.admission.db.repo.FacultyEntryRepository;
import com.axelor.inject.Beans;
import com.google.inject.Inject;

public class AdmissionServiceimpl implements AdmissionService {

	@Inject
	AdmissionEntryRepository repo;
	@Inject
	FacultyEntryRepository facultyrepo;

	@Override
	public String updateCollegeSelected(AdmissionProcess admissionProcess) {
		// TODO Auto-generated method stub
		String message = null;
/*		LocalDate toDate = admissionProcess.getToDate();
		LocalDate fromDate = admissionProcess.getFromDate();
		Integer duration = Period.between(fromDate, toDate).getDays();*/
		Integer seats = null;
		Integer seq;
		List<AdmissionEntry> admissionEntriesList = Beans.get(AdmissionEntryRepository.class).all()
				.filter("self.status = '2' ").fetch();
		admissionEntriesList.sort(Comparator.comparing(AdmissionEntry::getMerit).reversed());
		for (AdmissionEntry admissionEntry : admissionEntriesList) {
/*			Integer registrationDateDuration = Period.between(admissionEntry.getRegistrationDate(), toDate).getDays();*/
			List<CollegeEntry> collegeList = admissionEntry.getCollegeList();
			collegeList.sort(Comparator.comparing(CollegeEntry::getSequence));
			for (CollegeEntry collegeEntry : collegeList) {
				List<FacultyEntry> facultyEntryList = collegeEntry.getCollege().getFacultiesList();
				for (FacultyEntry facultyEntry : facultyEntryList) {
					if (admissionEntry.getFaculty() == facultyEntry.getFaculty() && facultyEntry.getSeats() != null) {
						seats = facultyEntry.getSeats();
						Integer i = facultyEntry.getSeats() - 1;
						facultyEntry.setSeats(i);
						facultyrepo.save(facultyEntry);
					}
					seq = collegeEntry.getSequence();
					if (seq >= 1 && seats >= 1) {
						admissionEntry.setCollegeSelected(collegeEntry.getCollege());
						admissionEntry.setStatus(3);
						break;
					} else {
						admissionEntry.setStatus(4);
					}
				}
				repo.save(admissionEntry);
				message = "College Allocated Sucessfully...";
			}
		}

		return message;
	}
}
