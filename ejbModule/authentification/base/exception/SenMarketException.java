package authentification.base.exception;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Exception de base pour toute l'application SenMarket
 * Toutes les exceptions spécifiques du système doivent hériter de cette classe
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class SenMarketException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Code d'erreur unique pour cette exception
     */
    private String codeErreur;
    
    /**
     * Type d'erreur (AUTHENTICATION, VALIDATION, BUSINESS, SYSTEM, etc.)
     */
    private String typeErreur;
    
    /**
     * Timestamp de l'occurrence de l'exception
     */
    private Timestamp timestampException;
    
    /**
     * Contexte d'exécution où l'erreur s'est produite
     */
    private String contexteExecution;
    
    /**
     * Identifiant de l'utilisateur concerné (si applicable)
     */
    private String identifiantUtilisateur;
    
    /**
     * Identifiant de la session active (si applicable)
     */
    private String sessionId;
    
    /**
     * Adresse IP d'origine de la requête
     */
    private String adresseIp;
    
    /**
     * User-Agent du client
     */
    private String userAgent;
    
    /**
     * Niveau de gravité de l'exception
     */
    private NiveauGravite niveauGravite = NiveauGravite.MOYEN;
    
    /**
     * Indique si l'exception peut être réessayée
     */
    private boolean peutReessayer = false;
    
    /**
     * Métadonnées additionnelles de l'exception
     */
    private Map<String, Object> metadonnees;
    
    /**
     * Cause technique détaillée
     */
    private String causeTechnique;
    
    /**
     * Message destiné aux utilisateurs finaux
     */
    private String messageUtilisateur;
    
    /**
     * Suggestions pour résoudre l'erreur
     */
    private String suggestionResolution;
    
    /**
     * URL de documentation pour cette erreur
     */
    private String urlDocumentation;
    
    /**
     * Indique si l'erreur doit être loggée
     */
    private boolean doitEtreLoggee = true;
    
    /**
     * Indique si l'erreur doit déclencher une notification
     */
    private boolean doitNotifier = false;
    
    /**
     * Énumération des niveaux de gravité
     */
    public enum NiveauGravite {
        FAIBLE("Faible - Impact minimal"),
        MOYEN("Moyen - Impact modéré"), 
        ELEVE("Élevé - Impact important"),
        CRITIQUE("Critique - Impact majeur");
        
        private final String description;
        
        NiveauGravite(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Énumération des types d'erreur principaux
     */
    public enum TypeErreurPrincipal {
        AUTHENTICATION("AUTHENTICATION"),
        AUTHORIZATION("AUTHORIZATION"),
        VALIDATION("VALIDATION"),
        BUSINESS("BUSINESS"),
        DATA_ACCESS("DATA_ACCESS"),
        SYSTEM("SYSTEM"),
        NETWORK("NETWORK"),
        SECURITY("SECURITY"),
        CONFIGURATION("CONFIGURATION"),
        EXTERNAL_SERVICE("EXTERNAL_SERVICE");
        
        private final String code;
        
        TypeErreurPrincipal(String code) {
            this.code = code;
        }
        
        public String getCode() {
            return code;
        }
    }
    
    /**
     * Constructeur par défaut
     */
    public SenMarketException() {
        super();
        this.timestampException = new Timestamp(System.currentTimeMillis());
        this.metadonnees = new HashMap<>();
    }
    
    /**
     * Constructeur avec message
     */
    public SenMarketException(String message) {
        super(message);
        this.timestampException = new Timestamp(System.currentTimeMillis());
        this.metadonnees = new HashMap<>();
        this.messageUtilisateur = message;
    }
    
    /**
     * Constructeur avec message et cause
     */
    public SenMarketException(String message, Throwable cause) {
        super(message, cause);
        this.timestampException = new Timestamp(System.currentTimeMillis());
        this.metadonnees = new HashMap<>();
        this.messageUtilisateur = message;
        if (cause != null) {
            this.causeTechnique = cause.getClass().getSimpleName() + ": " + cause.getMessage();
        }
    }
    
    /**
     * Constructeur complet
     */
    public SenMarketException(String message, String codeErreur, String typeErreur, 
                            NiveauGravite niveauGravite) {
        this(message);
        this.codeErreur = codeErreur;
        this.typeErreur = typeErreur;
        this.niveauGravite = niveauGravite;
    }
    
    /**
     * Constructeur avec contexte utilisateur
     */
    public SenMarketException(String message, String identifiantUtilisateur, 
                            String sessionId, String adresseIp) {
        this(message);
        this.identifiantUtilisateur = identifiantUtilisateur;
        this.sessionId = sessionId;
        this.adresseIp = adresseIp;
    }
    
    /**
     * Méthodes utilitaires
     */
    
    /**
     * Ajoute une métadonnée à l'exception
     */
    public void addMetadonnee(String cle, Object valeur) {
        if (this.metadonnees == null) {
            this.metadonnees = new HashMap<>();
        }
        this.metadonnees.put(cle, valeur);
    }
    
    /**
     * Récupère une métadonnée
     */
    public Object getMetadonnee(String cle) {
        return this.metadonnees != null ? this.metadonnees.get(cle) : null;
    }
    
    /**
     * Définit le contexte d'exécution
     */
    public void setContexteExecution(String classeMethode) {
        this.contexteExecution = classeMethode;
    }
    
    /**
     * Définit les informations client
     */
    public void setInformationsClient(String adresseIp, String userAgent) {
        this.adresseIp = adresseIp;
        this.userAgent = userAgent;
    }
    
    /**
     * Définit les informations de session
     */
    public void setInformationsSession(String identifiantUtilisateur, String sessionId) {
        this.identifiantUtilisateur = identifiantUtilisateur;
        this.sessionId = sessionId;
    }
    
    /**
     * Marque l'exception comme critique
     */
    public void marquerCommeCritique() {
        this.niveauGravite = NiveauGravite.CRITIQUE;
        this.doitNotifier = true;
        this.doitEtreLoggee = true;
    }
    
    /**
     * Marque l'exception comme réessayable
     */
    public void marquerCommeReessayable() {
        this.peutReessayer = true;
    }
    
    /**
     * Génère un identifiant unique pour cette exception
     */
    public String genererIdException() {
        return "EXC_" + System.currentTimeMillis() + "_" + 
               Integer.toHexString(this.hashCode());
    }
    
    /**
     * Formate l'exception pour le logging
     */
    public String formaterPourLog() {
        StringBuilder sb = new StringBuilder();
        sb.append("Exception SenMarket: ").append(getMessage()).append("\n");
        sb.append("Code: ").append(codeErreur).append("\n");
        sb.append("Type: ").append(typeErreur).append("\n");
        sb.append("Gravité: ").append(niveauGravite).append("\n");
        sb.append("Timestamp: ").append(timestampException).append("\n");
        
        if (identifiantUtilisateur != null) {
            sb.append("Utilisateur: ").append(identifiantUtilisateur).append("\n");
        }
        if (sessionId != null) {
            sb.append("Session: ").append(sessionId).append("\n");
        }
        if (adresseIp != null) {
            sb.append("IP: ").append(adresseIp).append("\n");
        }
        if (contexteExecution != null) {
            sb.append("Contexte: ").append(contexteExecution).append("\n");
        }
        
        return sb.toString();
    }
    
    // Getters et Setters
    public String getCodeErreur() {
        return codeErreur;
    }
    
    public void setCodeErreur(String codeErreur) {
        this.codeErreur = codeErreur;
    }
    
    public String getTypeErreur() {
        return typeErreur;
    }
    
    public void setTypeErreur(String typeErreur) {
        this.typeErreur = typeErreur;
    }
    
    public Timestamp getTimestampException() {
        return timestampException;
    }
    
    public void setTimestampException(Timestamp timestampException) {
        this.timestampException = timestampException;
    }
    
    public String getContexteExecution() {
        return contexteExecution;
    }
    
    public void setContexteExecution(String contexteExecution) {
        this.contexteExecution = contexteExecution;
    }
    
    public String getIdentifiantUtilisateur() {
        return identifiantUtilisateur;
    }
    
    public void setIdentifiantUtilisateur(String identifiantUtilisateur) {
        this.identifiantUtilisateur = identifiantUtilisateur;
    }
    
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public String getAdresseIp() {
        return adresseIp;
    }
    
    public void setAdresseIp(String adresseIp) {
        this.adresseIp = adresseIp;
    }
    
    public String getUserAgent() {
        return userAgent;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    public NiveauGravite getNiveauGravite() {
        return niveauGravite;
    }
    
    public void setNiveauGravite(NiveauGravite niveauGravite) {
        this.niveauGravite = niveauGravite;
    }
    
    public boolean isPeutReessayer() {
        return peutReessayer;
    }
    
    public void setPeutReessayer(boolean peutReessayer) {
        this.peutReessayer = peutReessayer;
    }
    
    public Map<String, Object> getMetadonnees() {
        return metadonnees;
    }
    
    public void setMetadonnees(Map<String, Object> metadonnees) {
        this.metadonnees = metadonnees;
    }
    
    public String getCauseTechnique() {
        return causeTechnique;
    }
    
    public void setCauseTechnique(String causeTechnique) {
        this.causeTechnique = causeTechnique;
    }
    
    public String getMessageUtilisateur() {
        return messageUtilisateur;
    }
    
    public void setMessageUtilisateur(String messageUtilisateur) {
        this.messageUtilisateur = messageUtilisateur;
    }
    
    public String getSuggestionResolution() {
        return suggestionResolution;
    }
    
    public void setSuggestionResolution(String suggestionResolution) {
        this.suggestionResolution = suggestionResolution;
    }
    
    public String getUrlDocumentation() {
        return urlDocumentation;
    }
    
    public void setUrlDocumentation(String urlDocumentation) {
        this.urlDocumentation = urlDocumentation;
    }
    
    public boolean isDoitEtreLoggee() {
        return doitEtreLoggee;
    }
    
    public void setDoitEtreLoggee(boolean doitEtreLoggee) {
        this.doitEtreLoggee = doitEtreLoggee;
    }
    
    public boolean isDoitNotifier() {
        return doitNotifier;
    }
    
    public void setDoitNotifier(boolean doitNotifier) {
        this.doitNotifier = doitNotifier;
    }
    
    @Override
    public String toString() {
        return "SenMarketException{" +
                "message='" + getMessage() + '\'' +
                ", codeErreur='" + codeErreur + '\'' +
                ", typeErreur='" + typeErreur + '\'' +
                ", niveauGravite=" + niveauGravite +
                ", identifiantUtilisateur='" + identifiantUtilisateur + '\'' +
                ", timestampException=" + timestampException +
                '}';
    }
}