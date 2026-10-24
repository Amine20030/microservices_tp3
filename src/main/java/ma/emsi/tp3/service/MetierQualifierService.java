package ma.emsi.tp3.service;

import ma.emsi.tp3.dao.IDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service illustrant la selection explicite d'implementation via @Qualifier("daoWS").
 */
@Service("serviceQualifier")
public class MetierQualifierService implements IMetierService {

    private final IDao dao;

    public MetierQualifierService(@Qualifier("daoWS") IDao dao) {
        this.dao = dao;
    }

    @Override
    public double traiterCalcul() {
        return dao.getData() * 2.0;
    }

    @Override
    public String getStrategieDescription() {
        return "Selection explicite via @Qualifier(\"daoWS\") -> " + dao.getSourceType();
    }

    public IDao getDao() {
        return dao;
    }
}
