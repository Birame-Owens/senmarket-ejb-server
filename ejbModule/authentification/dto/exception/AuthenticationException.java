package authentification.dto.exception;

import authentification.base.exception.SenMarketException;

/**
 * Exception de base pour toutes les erreurs d'authentification
 * Cette exception est levée lorsqu'un problème général d'authentification survient
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class AuthenticationException extends SenMarketException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Code d'erreur spécifique à cette exception
     */
    public static final String ERROR_CODE = "AUTH001";
    
    /**
     * Type d'erreur pour cette exception
     */
    public static final String ERROR_TYPE = "AUTHENTICATION";
    
    /**
     * Tentatives de connexion restantes
     */
    private Integer tentativesRestantes;
    
    /**
     * Délai d'attente avant prochaine tentative (en secondes)
     */
    private Integer delaiAttente;
    
    /**
     * Indique si le compte est temporairement bloqué
     */
    private boolean compteBloque = false;
    
    /**
     * Email ou identifiant de l'utilisateur concerné
     */
    private String identifiantUtilisateur;
    
    /**
     * Adresse IP d'origine de la tentative
     */
    private String adresseIp;
    
    /**
     * Timestamp de la tentative d'authentification
     */
    private java.sql.Timestamp timestampTentative;
    
    /**
     * Constructeur par défaut
     */
    public AuthenticationException() {
        super("Erreur d'authentification");
        this.setCodeErreur(ERROR_CODE);
        this.setTypeErreur(ERROR_TYPE);
        this.timestampTentative = new java.sql.Timestamp(System.currentTimeMillis());
    }
    
    /**
     * Constructeur avec message personnalisé
     */
    public AuthenticationException(String message) {
        super(message);
        this.setCodeErreur(ERROR_CODE);
        this.setTypeErreur(ERROR_TYPE);
        this.timestampTentative = new java.sql.Timestamp(System.currentTimeMillis());
    }
    
    /**
     * Constructeur avec message et cause
     */
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
        this.setCodeErreur(ERROR_CODE);
        this.setTypeErreur(ERROR_TYPE);
        this.timestampTentative = new java.sql.Timestamp(System.currentTimeMillis());
    }
    
    /**
     * Constructeur complet
     */
    public AuthenticationException(String message, String identifiantUtilisateur, 
                                 String adresseIp, Integer tentativesRestantes) {
        this(message);
        this.identifiantUtilisateur = identifiantUtilisateur;
        this.adresseIp = adresseIp;
        this.tentativesRestantes = tentativesRestantes;
    }
    
    /**
     * Constructeur pour compte bloqué
     */
    public AuthenticationException(String message, String identifiantUtilisateur, 
                                 Integer delaiAttente, boolean compteBloque) {
        this(message);
        this.identifiantUtilisateur = identifiantUtilisateur;
        this.delaiAttente = delaiAttente;
        this.compteBloque = compteBloque;
    }
    
    /**
     * Méthodes statiques pour créer des exceptions typiques
     */
    
    /**
     * Exception pour échec d'authentification général
     */
    public static AuthenticationException echecAuthentification(String identifiant) {
        String message = "Échec de l'authentification pour l'utilisateur : " + identifiant;
        return new AuthenticationException(message, identifiant, null, null);
    }
    
    /**
     * Exception pour tentatives multiples
     */
    public static AuthenticationException tropTentatives(String identifiant, 
                                                       Integer tentativesRestantes, 
                                                       String adresseIp) {
        String message = "Trop de tentatives d'authentification échouées. " +
                        "Tentatives restantes : " + tentativesRestantes;
        return new AuthenticationException(message, identifiant, adresseIp, tentativesRestantes);
    }
    
    /**
     * Exception pour compte bloqué
     */
    public static AuthenticationException compteBloque(String identifiant, Integer delaiAttente) {
        String message = "Compte temporairement bloqué en raison de tentatives suspectes. " +
                        "Réessayez dans " + delaiAttente + " secondes.";
        return new AuthenticationException(message, identifiant, delaiAttente, true);
    }
    
    /**
     * Exception pour méthode d'authentification non supportée
     */
    public static AuthenticationException methodeNonSupportee(String methode) {
        String message = "Méthode d'authentification non supportée : " + methode;
        return new AuthenticationException(message);
    }
    
    // Getters et Setters
    public Integer getTentativesRestantes() {
        return tentativesRestantes;
    }
    
    public void setTentativesRestantes(Integer tentativesRestantes) {
        this.tentativesRestantes = tentativesRestantes;
    }
    
    public Integer getDelaiAttente() {
        return delaiAttente;
    }
    
    public void setDelaiAttente(Integer delaiAttente) {
        this.delaiAttente = delaiAttente;
    }
    
    public boolean isCompteBloque() {
        return compteBloque;
    }
    
    public void setCompteBloque(boolean compteBloque) {
        this.compteBloque = compteBloque;
    }
    
    public String getIdentifiantUtilisateur() {
        return identifiantUtilisateur;
    }
    
    public void setIdentifiantUtilisateur(String identifiantUtilisateur) {
        this.identifiantUtilisateur = identifiantUtilisateur;
    }
    
    public String getAdresseIp() {
        return adresseIp;
    }
    
    public void setAdresseIp(String adresseIp) {
        this.adresseIp = adresseIp;
    }
    
    public java.sql.Timestamp getTimestampTentative() {
        return timestampTentative;
    }
    
    public void setTimestampTentative(java.sql.Timestamp timestampTentative) {
        this.timestampTentative = timestampTentative;
    }
    
    @Override
    public String toString() {
        return "AuthenticationException{" +
                "message='" + getMessage() + '\'' +
                ", codeErreur='" + getCodeErreur() + '\'' +
                ", identifiantUtilisateur='" + identifiantUtilisateur + '\'' +
                ", tentativesRestantes=" + tentativesRestantes +
                ", compteBloque=" + compteBloque +
                ", adresseIp='" + adresseIp + '\'' +
                ", timestampTentative=" + timestampTentative +
                '}';
    }
}