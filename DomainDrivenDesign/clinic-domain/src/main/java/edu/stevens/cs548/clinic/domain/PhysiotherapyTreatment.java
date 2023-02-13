package edu.stevens.cs548.clinic.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


// TODO
public class PhysiotherapyTreatment extends Treatment {

	private static final long serialVersionUID = 5602950140629148756L;
	
	// TODO (including order by date)
	protected List<LocalDate> treatmentDates;

	public void addTreatmentDate(LocalDate date) {
		treatmentDates.add(date);
	}

	@Override
	public <T> T export(ITreatmentExporter<T> visitor) {
            // TODO
		return null;	
	}
	
	public PhysiotherapyTreatment() {
		treatmentDates = new ArrayList<>();
	}

}
