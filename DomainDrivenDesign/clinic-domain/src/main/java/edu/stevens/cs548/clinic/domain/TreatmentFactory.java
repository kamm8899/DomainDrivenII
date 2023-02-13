package edu.stevens.cs548.clinic.domain;

public class TreatmentFactory implements ITreatmentFactory {
	
	/*
	 * Patient and provider fields are set when the treatment is added (see Provider).
	 * Id field is set when the treatment entity is synced with the database (see TreatmentDAO).
	 */

	@Override
	public DrugTreatment createDrugTreatment() {
		return new DrugTreatment();
	}

	/*
	 * TODO define other factory methods
	 */
	

}
