package authentification.base.exception;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Exception spécialisée pour les erreurs métier de l'application SenMarket
 * Cette exception est utilisée pour les violations de règles métier, contraintes fonctionnelles
 * et autres erreurs liées à la logique applicative
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class BusinessException extends SenMarketException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Préfixe pour les codes d'erreur métier
     */
    public static final String ERROR_CODE_PREFIX = "BUS";
    
    /**
     * Type d'erreur par défaut pour les exceptions métier
     */
    public static final String DEFAULT_ERROR_TYPE = "BUSINESS";
    
    /**
     * Règle métier violée
     */
    private String regleMetierViolee;
    
    /**
     * Entité concernée par l'erreur métier
     */
    private String entiteConcernee;
    
    /**
     * Identifiant de l'entité concernée
     */
    private String identifiantEntite;
    
    /**
     * Opération métier qui a échoué
     */
    private String operationMetier;
    
    /**
     * Conditions non respectées
     */
    private List<String> conditionsNonRespectees;
    
    /**
     * Actions correctives suggérées
     */
    private List<String> actionsCorrectives;
    
    /**
     * Données contextuelles de l'erreur métier
     */
    private String donneesContextuelles;
    
    /**
     * Indique si l'erreur est récupérable par l'utilisateur
     */
    private boolean erreurRecuperable = true;
    
    /**
     * Niveau d'impact métier
     */
    private ImpactMetier impactMetier = ImpactMetier.MOYEN;
    
    /**
     * Domaine fonctionnel concerné
     */
    private DomaineFonctionnel domaineFonctionnel;
    
    /**
     * Énumération des niveaux d'impact métier
     */
    public enum ImpactMetier {
        FAIBLE("Impact faible sur les processus métier"),
        MOYEN("Impact modéré sur les processus métier"),
        ELEVE("Impact important sur les processus métier"),
        CRITIQUE("Impact critique sur les processus métier");
        
        private final String description;
        
        ImpactMetier(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Énumération des domaines fonctionnels
     */
    public enum DomaineFonctionnel {
        AUTHENTIFICATION("Authentification et sécurité"),
        GESTION_UTILISATEURS("Gestion des utilisateurs"),
        GESTION_PRODUITS("Gestion des produits"),
        GESTION_COMMANDES("Gestion des commandes"),
        GESTION_PAIEMENTS("Gestion des paiements"),
        GESTION_STOCK("Gestion des stocks"),
        GESTION_LIVRAISONS("Gestion des livraisons"),
        MARKETING("Marketing et promotions"),
        REPORTING("Reporting et analyse"),
        CONFIGURATION("Configuration système");
        
        private final String description;
        
        DomaineFonctionnel(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Constructeur par défaut
     */
    public BusinessException() {
        super();
        this.setTypeErreur(DEFAULT_ERROR_TYPE);
        this.setNiveauGravite(NiveauGravite.MOYEN);
        this.conditionsNonRespectees = new ArrayList<>();
        this.actionsCorrectives = new ArrayList<>();
    }
    
    /**
     * Constructeur avec message
     */
    public BusinessException(String message) {
        super(message);
        this.setTypeErreur(DEFAULT_ERROR_TYPE);
        this.setNiveauGravite(NiveauGravite.MOYEN);
        this.conditionsNonRespectees = new ArrayList<>();
        this.actionsCorrectives = new ArrayList<>();
    }
    
    /**
     * Constructeur avec message et cause
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.setTypeErreur(DEFAULT_ERROR_TYPE);
        this.setNiveauGravite(NiveauGravite.MOYEN);
        this.conditionsNonRespectees = new ArrayList<>();
        this.actionsCorrectives = new ArrayList<>();
    }
    
    /**
     * Constructeur avec message et code d'erreur
     */
    public BusinessException(String message, String codeErreur) {
        super(message, ERROR_CODE_PREFIX + codeErreur, DEFAULT_ERROR_TYPE, NiveauGravite.MOYEN);
        this.conditionsNonRespectees = new ArrayList<>();
        this.actionsCorrectives = new ArrayList<>();
    }
    
    /**
     * Constructeur complet
     */
    public BusinessException(String message, String codeErreur, String regleMetierViolee,
                           String entiteConcernee, ImpactMetier impactMetier) {
        this(message, codeErreur);
        this.regleMetierViolee = regleMetierViolee;
        this.entiteConcernee = entiteConcernee;
        this.impactMetier = impactMetier;
        
        // Ajuster le niveau de gravité selon l'impact métier
        switch (impactMetier) {
            case FAIBLE:
                this.setNiveauGravite(NiveauGravite.FAIBLE);
                break;
            case MOYEN:
                this.setNiveauGravite(NiveauGravite.MOYEN);
                break;
            case ELEVE:
                this.setNiveauGravite(NiveauGravite.ELEVE);
                break;
            case CRITIQUE:
                this.setNiveauGravite(NiveauGravite.CRITIQUE);
                this.setDoitNotifier(true);
                break;
        }
    }
    
    /**
     * Méthodes statiques pour créer des exceptions métier courantes
     */
    
    /**
     * Exception pour règle métier violée
     */
    public static BusinessException regleViolee(String regleMetier, String entite, 
                                              String identifiantEntite) {
        String message = "Règle métier violée: " + regleMetier + " pour l'entité " + 
                        entite + " (ID: " + identifiantEntite + ")";
        BusinessException exception = new BusinessException(message, "001", regleMetier, 
                                                           entite, ImpactMetier.MOYEN);
        exception.setIdentifiantEntite(identifiantEntite);
        return exception;
    }
    
    /**
     * Exception pour contrainte d'intégrité violée
     */
    public static BusinessException contrainteIntegriteViolee(String contrainte, String entite) {
        String message = "Contrainte d'intégrité violée: " + contrainte + " sur l'entité " + entite;
        BusinessException exception = new BusinessException(message, "002", contrainte, 
                                                           entite, ImpactMetier.ELEVE);
        exception.addActionCorrective("Vérifiez les données saisies");
        exception.addActionCorrective("Contactez l'administrateur si le problème persiste");
        return exception;
    }
    
    /**
     * Exception pour état invalide
     */
    public static BusinessException etatInvalide(String entite, String etatActuel, 
                                               String operationDemandee) {
        String message = "Opération '" + operationDemandee + "' impossible: l'entité " + 
                        entite + " est dans l'état '" + etatActuel + "'";
        BusinessException exception = new BusinessException(message, "003", 
                                      "Transition d'état invalide", entite, ImpactMetier.MOYEN);
        exception.setOperationMetier(operationDemandee);
        exception.addConditionNonRespectee("État requis différent de: " + etatActuel);
        return exception;
    }
    
    /**
     * Exception pour ressource insuffisante
     */
    public static BusinessException ressourceInsuffisante(String ressource, 
                                                         int quantiteDemandee, 
                                                         int quantiteDisponible) {
        String message = "Ressource insuffisante: " + ressource + 
                        ". Demandé: " + quantiteDemandee + 
                        ", Disponible: " + quantiteDisponible;
        BusinessException exception = new BusinessException(message, "004", 
                                      "Stock insuffisant", ressource, ImpactMetier.ELEVE);
        exception.addConditionNonRespectee("Quantité disponible >= " + quantiteDemandee);
        exception.addActionCorrective("Réduire la quantité demandée");
        exception.addActionCorrective("Attendre un réapprovisionnement");
        return exception;
    }
    
    /**
     * Exception pour limite dépassée
     */
    public static BusinessException limiteDepassee(String typeLimit, Object valeurActuelle, 
                                                  Object valeurLimite, String entite) {
        String message = "Limite dépassée pour " + typeLimit + ": " + valeurActuelle + 
                        " > " + valeurLimite + " (entité: " + entite + ")";
        BusinessException exception = new BusinessException(message, "005", 
                                      "Limite " + typeLimit + " dépassée", entite, ImpactMetier.MOYEN);
        exception.addConditionNonRespectee(typeLimit + " <= " + valeurLimite);
        return exception;
    }
    
    /**
     * Exception pour conflit de données
     */
    public static BusinessException conflitDonnees(String entite, String identifiant, 
                                                  String typeConflit) {
        String message = "Conflit de données détecté: " + typeConflit + 
                        " pour l'entité " + entite + " (ID: " + identifiant + ")";
        BusinessException exception = new BusinessException(message, "006", 
                                      "Conflit de données", entite, ImpactMetier.ELEVE);
        exception.setIdentifiantEntite(identifiant);
        exception.addActionCorrective("Actualiser les données et réessayer");
        exception.addActionCorrective("Vérifier les modifications concurrentes");
        return exception;
    }
    
    /**
     * Exception pour processus métier incomplet
     */
    public static BusinessException processusIncomplet(String processus, String etapeManquante) {
        String message = "Processus métier incomplet: " + processus + 
                        ". Étape manquante: " + etapeManquante;
        BusinessException exception = new BusinessException(message, "007", 
                                      "Processus incomplet", processus, ImpactMetier.ELEVE);
        exception.setOperationMetier(processus);
        exception.addConditionNonRespectee("Étape '" + etapeManquante + "' doit être complétée");
        exception.addActionCorrective("Compléter l'étape: " + etapeManquante);
        return exception;
    }
    
    /**
     * Exception pour autorisation métier refusée
     */
    public static BusinessException autorisationRefusee(String operation, String utilisateur, 
                                                       String raisonRefus) {
        String message = "Autorisation refusée pour l'opération '" + operation + 
                        "' à l'utilisateur '" + utilisateur + "'. Raison: " + raisonRefus;
        BusinessException exception = new BusinessException(message, "008", 
                                      "Autorisation métier insuffisante", "Utilisateur", ImpactMetier.MOYEN);
        exception.setOperationMetier(operation);
        exception.setIdentifiantEntite(utilisateur);
        exception.addActionCorrective("Contacter un administrateur pour obtenir les autorisations");
        return exception;
    }
    
    /**
     * Méthodes utilitaires
     */
    
    /**
     * Ajoute une condition non respectée
     */
    public void addConditionNonRespectee(String condition) {
        if (this.conditionsNonRespectees == null) {
            this.conditionsNonRespectees = new ArrayList<>();
        }
        this.conditionsNonRespectees.add(condition);
    }
    
    /**
     * Ajoute une action corrective
     */
    public void addActionCorrective(String action) {
        if (this.actionsCorrectives == null) {
            this.actionsCorrectives = new ArrayList<>();
        }
        this.actionsCorrectives.add(action);
    }
    
    /**
     * Marque l'erreur comme non récupérable
     */
    public void marquerCommeNonRecuperable() {
        this.erreurRecuperable = false;
        this.setNiveauGravite(NiveauGravite.ELEVE);
    }
    
    /**
     * Définit le domaine fonctionnel et ajuste les métadonnées
     */
    public void setDomaineFonctionnel(DomaineFonctionnel domaine) {
        this.domaineFonctionnel = domaine;
        this.addMetadonnee("domaineFonctionnel", domaine.name());
        this.addMetadonnee("descriptionDomaine", domaine.getDescription());
    }
    
    /**
     * Génère un résumé de l'erreur métier
     */
    public String genererResumeErreur() {
        StringBuilder resume = new StringBuilder();
        resume.append("Erreur métier: ").append(getMessage()).append("\n");
        
        if (regleMetierViolee != null) {
            resume.append("Règle violée: ").append(regleMetierViolee).append("\n");
        }
        
        if (entiteConcernee != null) {
            resume.append("Entité: ").append(entiteConcernee);
            if (identifiantEntite != null) {
                resume.append(" (ID: ").append(identifiantEntite).append(")");
            }
            resume.append("\n");
        }
        
        if (operationMetier != null) {
            resume.append("Opération: ").append(operationMetier).append("\n");
        }
        
        resume.append("Impact: ").append(impactMetier.getDescription()).append("\n");
        resume.append("Récupérable: ").append(erreurRecuperable ? "Oui" : "Non");
        
        return resume.toString();
    }
    
    // Getters et Setters
    public String getRegleMetierViolee() {
        return regleMetierViolee;
    }
    
    public void setRegleMetierViolee(String regleMetierViolee) {
        this.regleMetierViolee = regleMetierViolee;
    }
    
    public String getEntiteConcernee() {
        return entiteConcernee;
    }
    
    public void setEntiteConcernee(String entiteConcernee) {
        this.entiteConcernee = entiteConcernee;
    }
    
    public String getIdentifiantEntite() {
        return identifiantEntite;
    }
    
    public void setIdentifiantEntite(String identifiantEntite) {
        this.identifiantEntite = identifiantEntite;
    }
    
    public String getOperationMetier() {
        return operationMetier;
    }
    
    public void setOperationMetier(String operationMetier) {
        this.operationMetier = operationMetier;
    }
    
    public List<String> getConditionsNonRespectees() {
        return conditionsNonRespectees;
    }
    
    public void setConditionsNonRespectees(List<String> conditionsNonRespectees) {
        this.conditionsNonRespectees = conditionsNonRespectees;
    }
    
    public List<String> getActionsCorrectives() {
        return actionsCorrectives;
    }
    
    public void setActionsCorrectives(List<String> actionsCorrectives) {
        this.actionsCorrectives = actionsCorrectives;
    }
    
    public String getDonneesContextuelles() {
        return donneesContextuelles;
    }
    
    public void setDonneesContextuelles(String donneesContextuelles) {
        this.donneesContextuelles = donneesContextuelles;
    }
    
    public boolean isErreurRecuperable() {
        return erreurRecuperable;
    }
    
    public void setErreurRecuperable(boolean erreurRecuperable) {
        this.erreurRecuperable = erreurRecuperable;
    }
    
    public ImpactMetier getImpactMetier() {
        return impactMetier;
    }
    
    public void setImpactMetier(ImpactMetier impactMetier) {
        this.impactMetier = impactMetier;
    }
    
    public DomaineFonctionnel getDomaineFonctionnel() {
        return domaineFonctionnel;
    }
    
    @Override
    public String toString() {
        return "BusinessException{" +
                "message='" + getMessage() + '\'' +
                ", codeErreur='" + getCodeErreur() + '\'' +
                ", regleMetierViolee='" + regleMetierViolee + '\'' +
                ", entiteConcernee='" + entiteConcernee + '\'' +
                ", operationMetier='" + operationMetier + '\'' +
                ", impactMetier=" + impactMetier +
                ", erreurRecuperable=" + erreurRecuperable +
                ", domaineFonctionnel=" + domaineFonctionnel +
                '}';
    }
}