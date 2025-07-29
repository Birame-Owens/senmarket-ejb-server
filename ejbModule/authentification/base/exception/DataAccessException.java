package authentification.base.exception;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Exception spécialisée pour les erreurs d'accès aux données dans l'application SenMarket
 * Cette exception encapsule les erreurs liées à la persistance, base de données, 
 * et autres opérations d'accès aux données
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class DataAccessException extends SenMarketException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Préfixe pour les codes d'erreur d'accès aux données
     */
    public static final String ERROR_CODE_PREFIX = "DAT";
    
    /**
     * Type d'erreur par défaut pour les exceptions d'accès aux données
     */
    public static final String DEFAULT_ERROR_TYPE = "DATA_ACCESS";
    
    /**
     * Type d'opération qui a échoué
     */
    private OperationType operationType;
    
    /**
     * Nom de l'entité concernée
     */
    private String nomEntite;
    
    /**
     * Identifiant de l'entité concernée
     */
    private String identifiantEntite;
    
    /**
     * Nom de la table/collection affectée
     */
    private String nomTable;
    
    /**
     * Requête SQL/HQL qui a échoué (si applicable)
     */
    private String requeteEchouee;
    
    /**
     * Code d'erreur de la base de données
     */
    private String codeBD;
    
    /**
     * État SQL (SQLState)
     */
    private String etatSQL;
    
    /**
     * Contraintes violées
     */
    private List<String> contraintesViolees;
    
    /**
     * Paramètres de la requête
     */
    private List<String> parametresRequete;
    
    /**
     * Source de données affectée
     */
    private String sourceDonnees;
    
    /**
     * Indique si l'erreur est temporaire et peut être réessayée
     */
    private boolean erreurTemporaire = false;
    
    /**
     * Durée suggérée avant de réessayer (en secondes)
     */
    private Integer delaiReessai;
    
    /**
     * Informations de connexion (masquées pour sécurité)
     */
    private String infoConnexion;
    
    /**
     * Énumération des types d'opération
     */
    public enum OperationType {
        SELECT("Lecture de données"),
        INSERT("Insertion de données"),
        UPDATE("Mise à jour de données"),
        DELETE("Suppression de données"),
        CREATE_TABLE("Création de table"),
        ALTER_TABLE("Modification de table"),
        DROP_TABLE("Suppression de table"),
        CREATE_INDEX("Création d'index"),
        TRANSACTION_COMMIT("Validation de transaction"),
        TRANSACTION_ROLLBACK("Annulation de transaction"),
        CONNECTION_OPEN("Ouverture de connexion"),
        CONNECTION_CLOSE("Fermeture de connexion"),
        BATCH_OPERATION("Opération en lot"),
        STORED_PROCEDURE("Procédure stockée"),
        FUNCTION_CALL("Appel de fonction");
        
        private final String description;
        
        OperationType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Énumération des catégories d'erreur de base de données
     */
    public enum CategorieErreurBD {
        CONTRAINTE_VIOLATION("Violation de contrainte"),
        CONNEXION_ECHEC("Échec de connexion"),
        TIMEOUT("Timeout d'opération"),
        VERROU_DETECTION("Détection de verrou"),
        TRANSACTION_ECHEC("Échec de transaction"),
        SYNTAXE_ERREUR("Erreur de syntaxe"),
        PERMISSION_REFUSEE("Permission refusée"),
        RESSOURCE_INDISPONIBLE("Ressource indisponible"),
        DONNEES_CORROMPUES("Données corrompues"),
        ESPACE_INSUFFISANT("Espace disque insuffisant");
        
        private final String description;
        
        CategorieErreurBD(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Constructeur par défaut
     */
    public DataAccessException() {
        super();
        this.setTypeErreur(DEFAULT_ERROR_TYPE);
        this.setNiveauGravite(NiveauGravite.ELEVE);
        this.contraintesViolees = new ArrayList<>();
        this.parametresRequete = new ArrayList<>();
    }
    
    /**
     * Constructeur avec message
     */
    public DataAccessException(String message) {
        super(message);
        this.setTypeErreur(DEFAULT_ERROR_TYPE);
        this.setNiveauGravite(NiveauGravite.ELEVE);
        this.contraintesViolees = new ArrayList<>();
        this.parametresRequete = new ArrayList<>();
    }
    
    /**
     * Constructeur avec message et cause
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
        this.setTypeErreur(DEFAULT_ERROR_TYPE);
        this.setNiveauGravite(NiveauGravite.ELEVE);
        this.contraintesViolees = new ArrayList<>();
        this.parametresRequete = new ArrayList<>();
        
        // Extraire les informations de SQLException si disponible
        if (cause instanceof SQLException) {
            SQLException sqlEx = (SQLException) cause;
            this.codeBD = String.valueOf(sqlEx.getErrorCode());
            this.etatSQL = sqlEx.getSQLState();
        }
    }
    
    /**
     * Constructeur avec opération et entité
     */
    public DataAccessException(String message, OperationType operationType, String nomEntite) {
        this(message);
        this.operationType = operationType;
        this.nomEntite = nomEntite;
        this.setCodeErreur(ERROR_CODE_PREFIX + "001");
    }
    
    /**
     * Constructeur complet
     */
    public DataAccessException(String message, OperationType operationType, String nomEntite,
                             String identifiantEntite, Throwable cause) {
        this(message, cause);
        this.operationType = operationType;
        this.nomEntite = nomEntite;
        this.identifiantEntite = identifiantEntite;
        this.setCodeErreur(ERROR_CODE_PREFIX + "001");
    }
    
    /**
     * Méthodes statiques pour créer des exceptions d'accès aux données courantes
     */
    
    /**
     * Exception pour entité non trouvée
     */
    public static DataAccessException entiteNonTrouvee(String entite, String identifiant) {
        String message = "Entité '" + entite + "' avec l'identifiant '" + identifiant + "' non trouvée";
        DataAccessException exception = new DataAccessException(message, OperationType.SELECT, entite);
        exception.setIdentifiantEntite(identifiant);
        exception.setCodeErreur(ERROR_CODE_PREFIX + "001");
        return exception;
    }
    
    /**
     * Exception pour violation de contrainte d'unicité
     */
    public static DataAccessException contrainteUniciteViolee(String entite, String champ, 
                                                            String valeur) {
        String message = "Violation de contrainte d'unicité pour l'entité '" + entite + 
                        "', champ '" + champ + "', valeur '" + valeur + "'";
        DataAccessException exception = new DataAccessException(message, OperationType.INSERT, entite);
        exception.addContrainteViolee("UNIQUE_" + champ.toUpperCase());
        exception.setCodeErreur(ERROR_CODE_PREFIX + "002");
        return exception;
    }
    
    /**
     * Exception pour violation de clé étrangère
     */
    public static DataAccessException contrainteCleEtrangereViolee(String entiteParente, 
                                                                 String entiteEnfant,
                                                                 String identifiantParent) {
        String message = "Violation de contrainte de clé étrangère: l'entité parente '" + 
                        entiteParente + "' (ID: " + identifiantParent + 
                        ") est référencée par l'entité '" + entiteEnfant + "'";
        DataAccessException exception = new DataAccessException(message, OperationType.DELETE, 
                                                               entiteParente);
        exception.setIdentifiantEntite(identifiantParent);
        exception.addContrainteViolee("FK_" + entiteEnfant.toUpperCase());
        exception.setCodeErreur(ERROR_CODE_PREFIX + "003");
        return exception;
    }
    
    /**
     * Exception pour erreur de connexion à la base de données
     */
    public static DataAccessException erreurConnexion(String sourceDonnees, Throwable cause) {
        String message = "Impossible de se connecter à la source de données '" + sourceDonnees + "'";
        DataAccessException exception = new DataAccessException(message, cause);
        exception.setOperationType(OperationType.CONNECTION_OPEN);
        exception.setSourceDonnees(sourceDonnees);
        exception.setErreurTemporaire(true);
        exception.setDelaiReessai(30); // 30 secondes
        exception.setCodeErreur(ERROR_CODE_PREFIX + "004");
        exception.setNiveauGravite(NiveauGravite.CRITIQUE);
        return exception;
    }
    
    /**
     * Exception pour timeout d'opération
     */
    public static DataAccessException timeoutOperation(OperationType operationType, 
                                                     String entite, int timeoutSeconds) {
        String message = "Timeout lors de l'opération '" + operationType.getDescription() + 
                        "' sur l'entité '" + entite + "' après " + timeoutSeconds + " secondes";
        DataAccessException exception = new DataAccessException(message, operationType, entite);
        exception.setErreurTemporaire(true);
        exception.setDelaiReessai(timeoutSeconds * 2);
        exception.setCodeErreur(ERROR_CODE_PREFIX + "005");
        return exception;
    }
    
    /**
     * Exception pour deadlock détecté
     */
    public static DataAccessException deadlockDetecte(String entite1, String entite2) {
        String message = "Deadlock détecté entre les entités '" + entite1 + "' et '" + entite2 + "'";
        DataAccessException exception = new DataAccessException(message, OperationType.UPDATE, entite1);
        exception.setErreurTemporaire(true);
        exception.setDelaiReessai(5); // 5 secondes
        exception.setCodeErreur(ERROR_CODE_PREFIX + "006");
        exception.addMetadonnee("entiteConflictuelle", entite2);
        return exception;
    }
    
    /**
     * Exception pour erreur de syntaxe SQL
     */
    public static DataAccessException erreurSyntaxeSQL(String requete, String erreurSyntaxe) {
        String message = "Erreur de syntaxe SQL: " + erreurSyntaxe;
        DataAccessException exception = new DataAccessException(message);
        exception.setRequeteEchouee(masquerParametresSensibles(requete));
        exception.setCodeErreur(ERROR_CODE_PREFIX + "007");
        exception.setNiveauGravite(NiveauGravite.CRITIQUE);
        return exception;
    }
    
    /**
     * Exception pour transaction échouée
     */
    public static DataAccessException transactionEchouee(String raisonEchec, Throwable cause) {
        String message = "Échec de la transaction: " + raisonEchec;
        DataAccessException exception = new DataAccessException(message, cause);
        exception.setOperationType(OperationType.TRANSACTION_COMMIT);
        exception.setCodeErreur(ERROR_CODE_PREFIX + "008");
        return exception;
    }
    
    /**
     * Exception pour ressource indisponible
     */
    public static DataAccessException ressourceIndisponible(String ressource, String raison) {
        String message = "Ressource '" + ressource + "' indisponible: " + raison;
        DataAccessException exception = new DataAccessException(message);
        exception.setErreurTemporaire(true);
        exception.setDelaiReessai(60); // 1 minute
        exception.setCodeErreur(ERROR_CODE_PREFIX + "009");
        exception.addMetadonnee("ressource", ressource);
        return exception;
    }
    
    /**
     * Exception pour optimistic locking
     */
    public static DataAccessException optimisticLockingEchec(String entite, String identifiant, 
                                                           int versionAttendue, int versionActuelle) {
        String message = "Échec de verrouillage optimiste pour l'entité '" + entite + 
                        "' (ID: " + identifiant + "). Version attendue: " + versionAttendue + 
                        ", version actuelle: " + versionActuelle;
        DataAccessException exception = new DataAccessException(message, OperationType.UPDATE, entite);
        exception.setIdentifiantEntite(identifiant);
        exception.setCodeErreur(ERROR_CODE_PREFIX + "010");
        exception.addMetadonnee("versionAttendue", versionAttendue);
        exception.addMetadonnee("versionActuelle", versionActuelle);
        return exception;
    }
    
    /**
     * Méthodes utilitaires
     */
    
    /**
     * Ajoute une contrainte violée
     */
    public void addContrainteViolee(String contrainte) {
        if (this.contraintesViolees == null) {
            this.contraintesViolees = new ArrayList<>();
        }
        this.contraintesViolees.add(contrainte);
    }
    
    /**
     * Ajoute un paramètre de requête (masqué si sensible)
     */
    public void addParametreRequete(String parametre) {
        if (this.parametresRequete == null) {
            this.parametresRequete = new ArrayList<>();
        }
        this.parametresRequete.add(masquerSiSensible(parametre));
    }
    
    /**
     * Masque les paramètres sensibles dans une requête
     */
    private static String masquerParametresSensibles(String requete) {
        if (requete == null) return null;
        
        // Masquer les mots de passe, tokens, etc.
        return requete.replaceAll("(?i)(password|token|secret)\\s*[=:]\\s*['\"]?[^'\"\\s,)]+", 
                                 "$1='***'");
    }
    
    /**
     * Masque un paramètre s'il est sensible
     */
    private String masquerSiSensible(String parametre) {
        if (parametre == null) return null;
        
        String param = parametre.toLowerCase();
        if (param.contains("password") || param.contains("token") || 
            param.contains("secret") || param.contains("key")) {
            return "*** (masqué pour sécurité)";
        }
        return parametre;
    }
    
    /**
     * Génère un rapport d'erreur détaillé
     */
    public String genererRapportErreur() {
        StringBuilder rapport = new StringBuilder();
        rapport.append("=== RAPPORT D'ERREUR D'ACCÈS AUX DONNÉES ===\n");
        rapport.append("Message: ").append(getMessage()).append("\n");
        rapport.append("Code erreur: ").append(getCodeErreur()).append("\n");
        rapport.append("Timestamp: ").append(getTimestampException()).append("\n");
        
        if (operationType != null) {
            rapport.append("Type d'opération: ").append(operationType.getDescription()).append("\n");
        }
        
        if (nomEntite != null) {
            rapport.append("Entité: ").append(nomEntite);
            if (identifiantEntite != null) {
                rapport.append(" (ID: ").append(identifiantEntite).append(")");
            }
            rapport.append("\n");
        }
        
        if (nomTable != null) {
            rapport.append("Table: ").append(nomTable).append("\n");
        }
        
        if (sourceDonnees != null) {
            rapport.append("Source de données: ").append(sourceDonnees).append("\n");
        }
        
        if (codeBD != null) {
            rapport.append("Code BD: ").append(codeBD);
            if (etatSQL != null) {
                rapport.append(" (État SQL: ").append(etatSQL).append(")");
            }
            rapport.append("\n");
        }
        
        if (contraintesViolees != null && !contraintesViolees.isEmpty()) {
            rapport.append("Contraintes violées: ").append(String.join(", ", contraintesViolees)).append("\n");
        }
        
        if (erreurTemporaire) {
            rapport.append("Erreur temporaire: Oui");
            if (delaiReessai != null) {
                rapport.append(" (délai de réessai: ").append(delaiReessai).append("s)");
            }
            rapport.append("\n");
        }
        
        if (requeteEchouee != null) {
            rapport.append("Requête: ").append(requeteEchouee).append("\n");
        }
        
        return rapport.toString();
    }
    
    // Getters et Setters
    public OperationType getOperationType() {
        return operationType;
    }
    
    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }
    
    public String getNomEntite() {
        return nomEntite;
    }
    
    public void setNomEntite(String nomEntite) {
        this.nomEntite = nomEntite;
    }
    
    public String getIdentifiantEntite() {
        return identifiantEntite;
    }
    
    public void setIdentifiantEntite(String identifiantEntite) {
        this.identifiantEntite = identifiantEntite;
    }
    
    public String getNomTable() {
        return nomTable;
    }
    
    public void setNomTable(String nomTable) {
        this.nomTable = nomTable;
    }
    
    public String getRequeteEchouee() {
        return requeteEchouee;
    }
    
    public void setRequeteEchouee(String requeteEchouee) {
        this.requeteEchouee = masquerParametresSensibles(requeteEchouee);
    }
    
    public String getCodeBD() {
        return codeBD;
    }
    
    public void setCodeBD(String codeBD) {
        this.codeBD = codeBD;
    }
    
    public String getEtatSQL() {
        return etatSQL;
    }
    
    public void setEtatSQL(String etatSQL) {
        this.etatSQL = etatSQL;
    }
    
    public List<String> getContraintesViolees() {
        return contraintesViolees;
    }
    
    public void setContraintesViolees(List<String> contraintesViolees) {
        this.contraintesViolees = contraintesViolees;
    }
    
    public List<String> getParametresRequete() {
        return parametresRequete;
    }
    
    public void setParametresRequete(List<String> parametresRequete) {
        this.parametresRequete = parametresRequete;
    }
    
    public String getSourceDonnees() {
        return sourceDonnees;
    }
    
    public void setSourceDonnees(String sourceDonnees) {
        this.sourceDonnees = sourceDonnees;
    }
    
    public boolean isErreurTemporaire() {
        return erreurTemporaire;
    }
    
    public void setErreurTemporaire(boolean erreurTemporaire) {
        this.erreurTemporaire = erreurTemporaire;
    }
    
    public Integer getDelaiReessai() {
        return delaiReessai;
    }
    
    public void setDelaiReessai(Integer delaiReessai) {
        this.delaiReessai = delaiReessai;
    }
    
    public String getInfoConnexion() {
        return infoConnexion;
    }
    
    public void setInfoConnexion(String infoConnexion) {
        this.infoConnexion = infoConnexion;
    }
    
    @Override
    public String toString() {
        return "DataAccessException{" +
                "message='" + getMessage() + '\'' +
                ", codeErreur='" + getCodeErreur() + '\'' +
                ", operationType=" + operationType +
                ", nomEntite='" + nomEntite + '\'' +
                ", identifiantEntite='" + identifiantEntite + '\'' +
                ", codeBD='" + codeBD + '\'' +
                ", etatSQL='" + etatSQL + '\'' +
                ", erreurTemporaire=" + erreurTemporaire +
                ", delaiReessai=" + delaiReessai +
                '}';
    }
}