package edu.stevens.cs548.clinic.domain;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//TODO JPA annotations
@Entity
@Table(name = "RadiologyTreatment")
public class RadiologyTreatment extends Treatment {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3656673416179492428L;

	// TODO including order by date
	@OrderBy
	@ElementCollection
	protected List<LocalDate> treatmentDates;

	public void addTreatmentDate(LocalDate date) {
		treatmentDates.add(date);
	}

	@Override
	public <T> T export(ITreatmentExporter<T> visitor) {
		// TODOx
		return visitor.exportRadiology(treatmentId,
				patient.getPatientId(),
				patient.getName(),
				provider.getProviderId(),
				provider.getName(),
				diagnosis,
				treatmentDates,
				() -> exportFollowupTreatments(visitor));
	}

	public RadiologyTreatment() {
		super();
		treatmentDates = new ArrayList<>();
	}

}
