package edu.stevens.cs548.clinic.service.init;

import edu.stevens.cs548.clinic.domain.IPatientDao;
import edu.stevens.cs548.clinic.domain.IProviderDao;
import edu.stevens.cs548.clinic.domain.ITreatmentExporter;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Destroyed;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.logging.Logger;

@Singleton
@LocalBean
@Startup
//@ApplicationScoped
//@Transactional
public class InitBean implements ITreatmentExporter<Void> {

	private static final Logger logger = Logger.getLogger(InitBean.class.getCanonicalName());

	private static final ZoneId ZONE_ID = ZoneOffset.UTC;

	private PatientFactory patientFactory = new PatientFactory();

	private ProviderFactory providerFactory = new ProviderFactory();

	// TODO
	@Inject
	private IPatientDao patientDao;

	// TODO
	@Inject
	private IProviderDao providerDao;

	/*
	 * Initialize using EJB logic
	 */
	@PostConstruct
	/*
	 * This should work to initialize with CDI bean, but there is a bug in
	 * Payara.....
	 */
	// public void init(@Observes @Initialized(ApplicationScoped.class)
	// ServletContext init) {
	public void init() {
		/*
		 * Put your testing logic here. Use the logger to display testing output in the
		 * server logs.
		 */
		logger.info("Jessica Kamman: ");
		System.err.println("Jessica Kamman");

		try {

			/*
			 * Clear the database and populate with fresh data.
			 * 
			 * Deletion of providers also deletes treatments.
			 */

			providerDao.deleteProviders();
			patientDao.deletePatients();

			Patient john = patientFactory.createPatient();
			john.setPatientId(UUID.randomUUID());
			john.setName("John Doe");
			john.setDob(LocalDate.parse("1995-08-15"));
			patientDao.addPatient(john);

			Provider jane = providerFactory.createProvider();
			jane.setProviderId(UUID.randomUUID());
			jane.setName("Jane Doe");
			jane.setNpi("1234");
			providerDao.addProvider(jane);

			jane.importDrugTreatment(UUID.randomUUID(), john, jane, "Headache", "Aspirin", 10,
					LocalDate.ofInstant(Instant.now(), ZONE_ID), LocalDate.ofInstant(Instant.now(), ZONE_ID),
					3, null);

			// TODO add more testing, including treatments and providers
			// add radiology to john
			List<LocalDate> radiologyDates = new ArrayList<>();
			radiologyDates.add(LocalDate.of(2023, 1, 8));
			radiologyDates.add(LocalDate.of(2023, 1, 16));
			radiologyDates.add(LocalDate.of(2023, 1, 24));
			jane.importRadiology(UUID.randomUUID(), john, jane, "sick", radiologyDates, null);

			// add surgery treatment to john
			jane.importSurgery(UUID.randomUUID(), john, jane, "sick", LocalDate.of(2023, 2, 2), "rest", null);

			// add physiotherapy treatment to john
			List<LocalDate> physiotherapyDates = new ArrayList<>();
			physiotherapyDates.add(LocalDate.of(2023, 2, 8));
			physiotherapyDates.add(LocalDate.of(2023, 2, 16));
			physiotherapyDates.add(LocalDate.of(2023, 2, 24));
			jane.importPhysiotherapy(UUID.randomUUID(), john, jane, "sick", physiotherapyDates, null);

			// Now show in the logs what has been added

			Collection<Patient> patients = patientDao.getPatients();
			for (Patient p : patients) {
				String dob = p.getDob().toString();
				logger.info(String.format("Patient %s, ID %s, DOB %s", p.getName(), p.getPatientId().toString(), dob));
				p.exportTreatments(this);
			}

			Collection<Provider> providers = providerDao.getProviders();
			for (Provider p : providers) {
				logger.info(String.format("Provider %s, ID %s", p.getName(), p.getProviderId().toString()));
				p.exportTreatments(this);
			}

		} catch (Exception e) {
			;
			throw new IllegalStateException("Failed to add record.", e);

		}

	}

	public void shutdown(@Observes @Destroyed(ApplicationScoped.class) ServletContext init) {
		logger.info("App shutting down....");
	}

	@Override
	public Void exportDrugTreatment(UUID tid, UUID patientId, String patientName, UUID providerId, String providerName,
			String diagnosis, String drug, float dosage, LocalDate start, LocalDate end, int frequency,
			Supplier<Collection<Void>> followups) {
		logger.info(String.format("...Drug treatment for %s, drug %s", patientName, drug));
		followups.get();
		return null;
	}

	@Override
	public Void exportRadiology(UUID tid, UUID patientId, String patientName, UUID providerId, String providerName,
			String diagnosis, List<LocalDate> dates, Supplier<Collection<Void>> followups) {
		logger.info(String.format("...Radiology treatment for %s", patientName));
		followups.get();
		return null;
	}

	@Override
	public Void exportSurgery(UUID tid, UUID patientId, String patientName, UUID providerId, String providerName,
			String diagnosis, LocalDate date, String dischargeInstructions, Supplier<Collection<Void>> followups) {
		logger.info(String.format("...Surgery treatment for %s", patientName));
		followups.get();
		return null;
	}

	@Override
	public Void exportPhysiotherapy(UUID tid, UUID patientId, String patientName, UUID providerId, String providerName,
			String diagnosis, List<LocalDate> dates, Supplier<Collection<Void>> followups) {
		logger.info(String.format("...Physiotherapy treatment for %s", patientName));
		followups.get();
		return null;
	}

}
