package edu.stevens.cs548.clinic.domain;

import java.time.LocalDate;
import java.util.Date;


//TODO JPA annotations
public class SurgeryTreatment extends Treatment {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4173146640306267418L;
	
	//TODO annotations
	private LocalDate surgeryDate;
	
	private String dischargeInstructions;
	
	public LocalDate getSurgeryDate() {
		return surgeryDate;
	}

	public void setSurgeryDate(LocalDate surgeryDate) {
		this.surgeryDate = surgeryDate;
	}

	public String getDischargeInstructions() {
		return dischargeInstructions;
	}

	public void setDischargeInstructions(String dischargeInstructions) {
		this.dischargeInstructions = dischargeInstructions;
	}
	
	@Override
	public <T> T export(ITreatmentExporter<T> visitor) {
		// TODO
		return null;
	}
	
	public SurgeryTreatment() {
		super();
	}

}
