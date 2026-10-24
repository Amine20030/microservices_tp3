package ma.emsi.tp3.service;

import ma.emsi.tp3.dao.IDao;
import org.springframework.stereotype.Service;

/**
 * Service illustrant la strategie recommandee :
 * 1. Injection par CONSTRUCTEUR (Constructor Injection) :
 *    - Immutabilite garantie (attribut 'final').
 *    - Facilite des tests unitaires purs sans contexte Spring.
 * 2. Selection automatique via @Primary.
 */
@Service("servicePrimary")
public class MetierConstructorPrimaryService implements IMetierService {

    private final IDao dao;

    // Pas besoin de @Autowired sur le constructeur si un seul constructeur existe (Spring 4.3+)
    public MetierConstructorPrimaryService(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double traiterCalcul() {
        return dao.getData() * 2.0;
    }

    @Override
    public String getStrategieDescription() {
        return "Injection par Constructeur (Recommandee) avec selection automatique @Primary -> " + dao.getSourceType();
    }

    public IDao getDao() {
        return dao;
    }
}
