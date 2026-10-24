package ma.emsi.tp3.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * Implementation par defaut pour la base de donnees.
 * L'annotation @Primary indique a Spring de selectionner cette implementation
 * en cas d'ambiguite s'il n'y a pas de @Qualifier specifie.
 */
@Repository("daoDB")
@Primary
public class DaoDatabaseImpl implements IDao {

    @Override
    public double getData() {
        return 180.0;
    }

    @Override
    public String getSourceType() {
        return "BASE_DE_DONNEES_RELATIONNELLE_SQL";
    }
}
