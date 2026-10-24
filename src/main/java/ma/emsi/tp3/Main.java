package ma.emsi.tp3;

import ma.emsi.tp3.config.AppConfig;
import ma.emsi.tp3.service.IMetierService;
import ma.emsi.tp3.service.MetierRouterService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println(" TP 3 : SPRING IOC & OCP - STRATEGIES D'INJECTION & SELECTION D'IMPLEMENTATIONS");
        System.out.println("================================================================================\n");

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // 1. Strategie Constructor Injection avec @Primary
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(" 1. Strategie Recommandee : Constructor Injection + @Primary");
        System.out.println("--------------------------------------------------------------------------------");
        IMetierService servicePrimary = context.getBean("servicePrimary", IMetierService.class);
        System.out.println("-> Description : " + servicePrimary.getStrategieDescription());
        System.out.println("-> Resultat calcul : " + servicePrimary.traiterCalcul());

        // 2. Strategie Selection Explicite avec @Qualifier
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(" 2. Strategie de Selection Ciblee : @Qualifier(\"daoWS\")");
        System.out.println("--------------------------------------------------------------------------------");
        IMetierService serviceQualifier = context.getBean("serviceQualifier", IMetierService.class);
        System.out.println("-> Description : " + serviceQualifier.getStrategieDescription());
        System.out.println("-> Resultat calcul : " + serviceQualifier.traiterCalcul());

        // 3. Strategie Routeur Multi-Implementations (Map Routing OCP)
        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(" 3. Strategie Routeur Multi-Implementations (Map<String, IDao> - Principe OCP)");
        System.out.println("--------------------------------------------------------------------------------");
        MetierRouterService router = context.getBean(MetierRouterService.class);
        System.out.println("-> Implementations decouvertes par Spring : " + router.getDaoImplementations().keySet());

        double resDB = router.calculerAvecImplementation("daoDB");
        System.out.println("   Calcul via daoDB    : " + resDB);

        double resWS = router.calculerAvecImplementation("daoWS");
        System.out.println("   Calcul via daoWS    : " + resWS);

        double resCache = router.calculerAvecImplementation("daoCache");
        System.out.println("   Calcul via daoCache : " + resCache);

        System.out.println("\n================================================================================");
        System.out.println(" BILAN : Flexibilite et Principe Ouvert/Ferme (OCP) 100% demontres avec succes");
        System.out.println("================================================================================");

        context.close();
    }
}
