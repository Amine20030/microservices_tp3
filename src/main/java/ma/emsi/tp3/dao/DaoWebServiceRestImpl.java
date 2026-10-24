package ma.emsi.tp3.dao;

import org.springframework.stereotype.Repository;

/**
 * Implementation simulant l'appel a un Microservice REST externe.
 */
@Repository("daoWS")
public class DaoWebServiceRestImpl implements IDao {

    @Override
    public double getData() {
        return 340.0;
    }

    @Override
    public String getSourceType() {
        return "MICROSERVICE_REST_EXTERNE";
    }
}
