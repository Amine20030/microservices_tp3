package ma.emsi.tp3.service;

import ma.emsi.tp3.dao.IDao;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service Routeur dynamique : Spring injecte automatiquement TOUTES les implementations
 * de l'interface IDao dans une Map avec comme cle le nom du bean Spring.
 * Permet une extensibilite totale sans aucune modification de code (Principe OCP).
 */
@Service("serviceRouter")
public class MetierRouterService {

    private final Map<String, IDao> daoImplementations;

    public MetierRouterService(Map<String, IDao> daoImplementations) {
        this.daoImplementations = daoImplementations;
    }

    public double calculerAvecImplementation(String beanName) {
        IDao selectedDao = daoImplementations.get(beanName);
        if (selectedDao == null) {
            throw new IllegalArgumentException("Aucune implementation DAO trouvee pour le nom de bean : " + beanName);
        }
        System.out.println("[Router OCP] Selection dynamique du bean : " + beanName + " (" + selectedDao.getSourceType() + ")");
        return selectedDao.getData() * 2.0;
    }

    public Map<String, IDao> getDaoImplementations() {
        return daoImplementations;
    }
}
