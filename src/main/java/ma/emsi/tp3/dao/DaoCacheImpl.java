package ma.emsi.tp3.dao;

import org.springframework.stereotype.Repository;

/**
 * Implementation simulant une couche Cache (ex: Redis / Memcached).
 */
@Repository("daoCache")
public class DaoCacheImpl implements IDao {

    @Override
    public double getData() {
        return 500.0;
    }

    @Override
    public String getSourceType() {
        return "CACHE_MEMOIRE_REDIS";
    }
}
