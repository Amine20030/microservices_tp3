package ma.emsi.tp3;

import ma.emsi.tp3.config.AppConfig;
import ma.emsi.tp3.dao.IDao;
import ma.emsi.tp3.dao.DaoDatabaseImpl;
import ma.emsi.tp3.dao.DaoWebServiceRestImpl;
import ma.emsi.tp3.service.IMetierService;
import ma.emsi.tp3.service.MetierConstructorPrimaryService;
import ma.emsi.tp3.service.MetierQualifierService;
import ma.emsi.tp3.service.MetierRouterService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SpringIoCOCPTest {

    private static AnnotationConfigApplicationContext context;

    @BeforeAll
    public static void setUp() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    @AfterAll
    public static void tearDown() {
        if (context != null) {
            context.close();
        }
    }

    @Test
    @DisplayName("Validation de la selection automatique de l'implementation par defaut (@Primary)")
    public void testSelectionPrimaire() {
        MetierConstructorPrimaryService service = context.getBean(MetierConstructorPrimaryService.class);

        assertThat(service).isNotNull();
        assertThat(service.getDao()).isInstanceOf(DaoDatabaseImpl.class);

        // 180.0 * 2.0 = 360.0
        assertThat(service.traiterCalcul()).isEqualTo(360.0);
    }

    @Test
    @DisplayName("Validation de la selection ciblee d'implementation avec @Qualifier('daoWS')")
    public void testSelectionQualifier() {
        MetierQualifierService service = context.getBean(MetierQualifierService.class);

        assertThat(service).isNotNull();
        assertThat(service.getDao()).isInstanceOf(DaoWebServiceRestImpl.class);

        // 340.0 * 2.0 = 680.0
        assertThat(service.traiterCalcul()).isEqualTo(680.0);
    }

    @Test
    @DisplayName("Validation du routage dynamique via Map<String, IDao> (Respect du principe OCP)")
    public void testRouterDynamiqueMap() {
        MetierRouterService router = context.getBean(MetierRouterService.class);

        assertThat(router.getDaoImplementations()).containsKeys("daoDB", "daoWS", "daoCache");

        assertThat(router.calculerAvecImplementation("daoDB")).isEqualTo(360.0);
        assertThat(router.calculerAvecImplementation("daoWS")).isEqualTo(680.0);
        assertThat(router.calculerAvecImplementation("daoCache")).isEqualTo(1000.0);
    }

    @Test
    @DisplayName("Verification du comportement en cas de nom de bean inexistant dans le routeur")
    public void testRouterBeanInexistant() {
        MetierRouterService router = context.getBean(MetierRouterService.class);
        assertThrows(IllegalArgumentException.class, () -> router.calculerAvecImplementation("beanInconnu"));
    }

    @Test
    @DisplayName("Validation des tests unitaires purs sans contexte Spring (Constructor Injection)")
    public void testTestabiliteUnitairePure() {
        // Grâce a l'injection par constructeur, on peut instancier et tester le service sans Spring
        IDao mockDao = new IDao() {
            @Override
            public double getData() {
                return 50.0;
            }

            @Override
            public String getSourceType() {
                return "TEST_UNITAIRE_MOCK";
            }
        };
        MetierConstructorPrimaryService mockService = new MetierConstructorPrimaryService(mockDao);
        assertThat(mockService.traiterCalcul()).isEqualTo(100.0);
    }
}
