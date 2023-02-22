package edu.stevens.cs548.clinic.domain;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Transient;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;


// TODO
@RequestScoped
@Transactional
public class ProviderDao implements IProviderDao {

	// TODO
	@Inject
	@ClinicDomainProducer.ClinicDomain
	private EntityManager em;
	
	// TODO
	@Inject
	private ITreatmentDao treatmentDao;

	private Logger logger = Logger.getLogger(ProviderDao.class.getCanonicalName());

	@Override
	public void addProvider(Provider provider) throws ProviderExn {
		// Add to database, and initialize the provider aggregate with a treatment DAO.
		UUID pid = provider.getProviderId();
		Query query = em.createNamedQuery("CountProviderByProviderId").setParameter("providerId", pid);
		Long numExisting = (Long) query.getSingleResult();
		
		logger.info(String.format("Adding provider with id %s, found %d existing records", pid.toString(), numExisting));
		
		if (numExisting < 1) {
			
			// Add to database, and initialize the patient aggregate with a treatment DAO.
			em.persist(provider);
			provider.setTreatmentDao(this.treatmentDao);
			
		} else {
			
			throw new ProviderExn("Insertion: Provider with provider id (" + pid + ") already exists.");
		}
	}

	@Override
	/*
	 * The boolean flag indicates if related treatments should be loaded eagerly.
	 */
	public Provider getProvider(UUID id, boolean includeTreatments) throws ProviderExn {
		/*
		 * TODO retrieve Provider using external key
		 */
		// String queryName = includeTreatments ? "SearchPatientWithTreatmentsByPatientId" : "SearchPatientByPatientId";
		String queryName = "SearchProviderByProviderId";
		TypedQuery<Provider> query = em.createNamedQuery(queryName, Provider.class).setParameter("providerId",id);
		List<Provider> providers = query.getResultList();
		
		if (providers.size() > 1) {
			throw new ProviderExn("Duplicate patient records: providers id = " + id);
		} else if (providers.size() < 1) {
			throw new ProviderExn("Providers not found: provider id = " + id);
		} else {
			Provider p = providers.get(0);
			/*
			 * Refresh from database or we will never see new treatments.
			 */
			em.refresh(p);
			p.setTreatmentDao(this.treatmentDao);
			return p;
		}
	}
	
	@Override
	/*
	 * By default, we eagerly load related treatments with a provider record.
	 */
	public Provider getProvider(UUID id) throws ProviderExn {
		return getProvider(id, true);
	}
	
	@Override
	public List<Provider> getProviders() {
		/*
		 * TODO Return a list of all providers (remember to set treatmentDAO)
		 */
		TypedQuery<Provider> query = em.createNamedQuery("SearchAllProviders", Provider.class);
		List<Provider> providers = query.getResultList();
		
		for (Provider p : providers) {
			p.setTreatmentDao(treatmentDao);
		}

		return providers;
	}

	
	@Override
	public void deleteProviders() {
		Query update = em.createNamedQuery("RemoveAllTreatments");
		update.executeUpdate();
		update = em.createNamedQuery("RemoveAllProviders");
		update.executeUpdate();
	}

}
