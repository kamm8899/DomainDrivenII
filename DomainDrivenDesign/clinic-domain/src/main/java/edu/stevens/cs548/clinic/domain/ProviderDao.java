package edu.stevens.cs548.clinic.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;


// TODO
public class ProviderDao implements IProviderDao {

	// TODO
	private EntityManager em;
	
	// TODO
	private ITreatmentDao treatmentDao;

	private Logger logger = Logger.getLogger(ProviderDao.class.getCanonicalName());

	@Override
	public void addProvider(Provider provider) throws ProviderExn {
		// Add to database, and initialize the provider aggregate with a treatment DAO.

	}

	@Override
	/*
	 * The boolean flag indicates if related treatments should be loaded eagerly.
	 */
	public Provider getProvider(UUID id, boolean includeTreatments) throws ProviderExn {
		/*
		 * TODO retrieve Provider using external key
		 */
		return null;
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


		return null;
	}
	
	@Override
	public void deleteProviders() {
		Query update = em.createNamedQuery("RemoveAllTreatments");
		update.executeUpdate();
		update = em.createNamedQuery("RemoveAllProviders");
		update.executeUpdate();
	}

}
