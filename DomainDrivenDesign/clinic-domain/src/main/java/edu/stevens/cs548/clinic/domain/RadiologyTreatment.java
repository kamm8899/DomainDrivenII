package edu.stevens.cs548.clinic.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


//TODO JPA annotations
public class RadiologyTreatment extends Treatment {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3656673416179492428L;

	// TODO  including order by date
	protected List<LocalDate> treatmentDates;

	public void addTreatmentDate(LocalDate date) {
		treatmentDates.add(date);
	}

	@Override
	public <T> T export(ITreatmentExporter<T> visitor) {
		// TODO
		return null;
	}
	
	public RadiologyTreatment() {
		super();
		treatmentDates = new ArrayList<>();
	}
	
}
