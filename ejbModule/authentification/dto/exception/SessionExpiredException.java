package authentification.dto.exception;

import java.sql.Timestamp;

/**
 * Exception spécifique pour les sessions expirées
 * Cette exception est levée lorsqu'une session utilisateur a expiré ou n'est plus valide
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class SessionExpiredException extends AuthenticationException {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Code d'erreur spécifique à cette exception
     */
    public static final String ERROR_CODE = "AUTH003";
    
    /**
     * Identifiant de la session expirée
     */
    private String sessionId;
    
    /**
     * Token de session expiré
     */
    private String sessionToken;
    
    /**
     * Date de création de la session
     */
    private Timestamp dateCreationSession;
    
    /**
     * Date d'expiration de la session
     */
    private Timestamp dateExpirationSession;
    
    /**
     * Dernière activité de la session
     */
    private Timestamp derniereActivite;
    
    /**
     * Durée de vie de la session en minutes
     */
    private Integer dureeVieSession;
    
    /**
     * Raison de l'expiration
     */
    private RaisonExpiration raisonExpiration;
    
    /**
     * Indique si une reconnexion automatique est possible
     */
    private boolean reconnexionAutomatiquePossible = false;
    
    /**
     * URL de redirection pour nouvelle connexion
     */
    private String urlReconnexion;
    
    /**
     * Indique si la session a été fermée manuellement
     */
    private boolean fermetureManuelle = false;
    
    /**
     * Appareil/navigateur de la session
     */
    private String infoAppareil;
    
    /**
     * Énumération des raisons d'expiration
     */
    public enum RaisonExpiration {
        TIMEOUT_INACTIVITE("Session expirée par inactivité"),
        TIMEOUT_ABSOLUE("Durée maximale de session atteinte"),
        DECONNEXION_MANUELLE("Déconnexion manuelle de l'utilisateur"),
        FERMETURE_ADMINISTRATEUR("Session fermée par un administrateur"),
        CONNEXION_SIMULTANEE("Session fermée suite à une nouvelle connexion"),
        CHANGEMENT_MOT_PASSE("Session fermée suite à un changement de mot de passe"),
        CHANGEMENT_PROFIL("Session fermée suite à une modification de profil"),
        ACTIVITE_SUSPECTE("Session fermée pour activité suspecte"),
        MAINTENANCE_SYSTEME("Session fermée pour maintenance système"),
        TOKEN_INVALIDE("Token de session invalide ou corrompu"),
        REVOCATION_TOKEN("Token révoqué par sécurité");
        
        private final String description;
        
        RaisonExpiration(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    /**
     * Constructeur par défaut
     */
    public SessionExpiredException() {
        super("Session expirée. Veuillez vous reconnecter.");
        this.setCodeErreur(ERROR_CODE);
        this.raisonExpiration = RaisonExpiration.TIMEOUT_INACTIVITE;
    }
    
    /**
     * Constructeur avec message personnalisé
     */
    public SessionExpiredException(String message) {
        super(message);
        this.setCodeErreur(ERROR_CODE);
    }
    
    /**
     * Constructeur avec message et cause
     */
    public SessionExpiredException(String message, Throwable cause) {
        super(message, cause);
        this.setCodeErreur(ERROR_CODE);
    }
    
    /**
     * Constructeur avec session ID
     */
    public SessionExpiredException(String message, String sessionId, 
                                 RaisonExpiration raisonExpiration) {
        super(message);
        this.setCodeErreur(ERROR_CODE);
        this.sessionId = sessionId;
        this.raisonExpiration = raisonExpiration;
    }
    
    /**
     * Constructeur complet
     */
    public SessionExpiredException(String message, String sessionId, String sessionToken,
                                 Timestamp dateExpiration, RaisonExpiration raisonExpiration,
                                 String identifiantUtilisateur) {
        super(message, identifiantUtilisateur, null, null);
        this.setCodeErreur(ERROR_CODE);
        this.sessionId = sessionId;
        this.sessionToken = sessionToken;
        this.dateExpirationSession = dateExpiration;
        this.raisonExpiration = raisonExpiration;
    }
    
    /**
     * Méthodes statiques pour créer des exceptions typiques
     */
    
    /**
     * Exception pour timeout d'inactivité
     */
    public static SessionExpiredException timeoutInactivite(String sessionId, 
                                                          String identifiantUtilisateur,
                                                          Integer dureeInactivite) {
        String message = "Votre session a expiré après " + dureeInactivite + 
                        " minutes d'inactivité. Reconnectez-vous pour continuer.";
        SessionExpiredException exception = new SessionExpiredException(message, sessionId, 
                                           RaisonExpiration.TIMEOUT_INACTIVITE);
        exception.setIdentifiantUtilisateur(identifiantUtilisateur);
        exception.setDureeVieSession(dureeInactivite);
        exception.setReconnexionAutomatiquePossible(true);
        return exception;
    }
    
    /**
     * Exception pour timeout absolu
     */
    public static SessionExpiredException timeoutAbsolu(String sessionId, 
                                                      String identifiantUtilisateur,
                                                      Integer dureeMaximale) {
        String message = "Votre session a atteint sa durée maximale de " + dureeMaximale + 
                        " minutes. Reconnectez-vous pour continuer.";
        SessionExpiredException exception = new SessionExpiredException(message, sessionId, 
                                           RaisonExpiration.TIMEOUT_ABSOLUE);
        exception.setIdentifiantUtilisateur(identifiantUtilisateur);
        exception.setDureeVieSession(dureeMaximale);
        exception.setReconnexionAutomatiquePossible(true);
        return exception;
    }
    
    /**
     * Exception pour déconnexion manuelle
     */
    public static SessionExpiredException deconnexionManuelle(String sessionId, 
                                                            String identifiantUtilisateur) {
        String message = "Vous avez été déconnecté avec succès.";
        SessionExpiredException exception = new SessionExpiredException(message, sessionId, 
                                           RaisonExpiration.DECONNEXION_MANUELLE);
        exception.setIdentifiantUtilisateur(identifiantUtilisateur);
        exception.setFermetureManuelle(true);
        return exception;
    }
    
    /**
     * Exception pour fermeture par administrateur
     */
    public static SessionExpiredException fermetureAdministrateur(String sessionId, 
                                                               String identifiantUtilisateur,
                                                               String raisonAdmin) {
        String message = "Votre session a été fermée par un administrateur. Raison : " + raisonAdmin;
        SessionExpiredException exception = new SessionExpiredException(message, sessionId, 
                                           RaisonExpiration.FERMETURE_ADMINISTRATEUR);
        exception.setIdentifiantUtilisateur(identifiantUtilisateur);
        return exception;
    }
    
    /**
     * Exception pour connexion simultanée
     */
    public static SessionExpiredException connexionSimultanee(String sessionId, 
                                                            String identifiantUtilisateur,
                                                            String nouvelAppareil) {
        String message = "Vous avez été déconnecté car une nouvelle connexion a été établie depuis : " + 
                        nouvelAppareil;
        SessionExpiredException exception = new SessionExpiredException(message, sessionId, 
                                           RaisonExpiration.CONNEXION_SIMULTANEE);
        exception.setIdentifiantUtilisateur(identifiantUtilisateur);
        exception.setInfoAppareil(nouvelAppareil);
        return exception;
    }
    
    /**
     * Exception pour changement de mot de passe
     */
    public static SessionExpiredException changementMotDePasse(String sessionId, 
                                                             String identifiantUtilisateur) {
        String message = "Votre session a été fermée suite au changement de votre mot de passe. " +
                        "Reconnectez-vous avec vos nouveaux identifiants.";
        SessionExpiredException exception = new SessionExpiredException(message, sessionId, 
                                           RaisonExpiration.CHANGEMENT_MOT_PASSE);
        exception.setIdentifiantUtilisateur(identifiantUtilisateur);
        exception.setReconnexionAutomatiquePossible(false);
        return exception;
    }
    
    /**
     * Exception pour activité suspecte
     */
    public static SessionExpiredException activiteSuspecte(String sessionId, 
                                                         String identifiantUtilisateur,
                                                         String detailsActivite) {
        String message = "Votre session a été fermée suite à une activité suspecte détectée : " + 
                        detailsActivite + ". Reconnectez-vous pour continuer.";
        SessionExpiredException exception = new SessionExpiredException(message, sessionId, 
                                           RaisonExpiration.ACTIVITE_SUSPECTE);
        exception.setIdentifiantUtilisateur(identifiantUtilisateur);
        exception.setReconnexionAutomatiquePossible(false);
        return exception;
    }
    
    /**
     * Exception pour token invalide
     */
    public static SessionExpiredException tokenInvalide(String sessionToken) {
        String message = "Le token de session est invalide ou corrompu. Veuillez vous reconnecter.";
        SessionExpiredException exception = new SessionExpiredException(message, null, 
                                           RaisonExpiration.TOKEN_INVALIDE);
        exception.setSessionToken(sessionToken);
        return exception;
    }
    
    // Getters et Setters
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public String getSessionToken() {
        return sessionToken;
    }
    
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
    
    public Timestamp getDateCreationSession() {
        return dateCreationSession;
    }
    
    public void setDateCreationSession(Timestamp dateCreationSession) {
        this.dateCreationSession = dateCreationSession;
    }
    
    public Timestamp getDateExpirationSession() {
        return dateExpirationSession;
    }
    
    public void setDateExpirationSession(Timestamp dateExpirationSession) {
        this.dateExpirationSession = dateExpirationSession;
    }
    
    public Timestamp getDerniereActivite() {
        return derniereActivite;
    }
    
    public void setDerniereActivite(Timestamp derniereActivite) {
        this.derniereActivite = derniereActivite;
    }
    
    public Integer getDureeVieSession() {
        return dureeVieSession;
    }
    
    public void setDureeVieSession(Integer dureeVieSession) {
        this.dureeVieSession = dureeVieSession;
    }
    
    public RaisonExpiration getRaisonExpiration() {
        return raisonExpiration;
    }
    
    public void setRaisonExpiration(RaisonExpiration raisonExpiration) {
        this.raisonExpiration = raisonExpiration;
    }
    
    public boolean isReconnexionAutomatiquePossible() {
        return reconnexionAutomatiquePossible;
    }
    
    public void setReconnexionAutomatiquePossible(boolean reconnexionAutomatiquePossible) {
        this.reconnexionAutomatiquePossible = reconnexionAutomatiquePossible;
    }
    
    public String getUrlReconnexion() {
        return urlReconnexion;
    }
    
    public void setUrlReconnexion(String urlReconnexion) {
        this.urlReconnexion = urlReconnexion;
    }
    
    public boolean isFermetureManuelle() {
        return fermetureManuelle;
    }
    
    public void setFermetureManuelle(boolean fermetureManuelle) {
        this.fermetureManuelle = fermetureManuelle;
    }
    
    public String getInfoAppareil() {
        return infoAppareil;
    }
    
    public void setInfoAppareil(String infoAppareil) {
        this.infoAppareil = infoAppareil;
    }
    
    @Override
    public String toString() {
        return "SessionExpiredException{" +
                "message='" + getMessage() + '\'' +
                ", codeErreur='" + getCodeErreur() + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", raisonExpiration=" + raisonExpiration +
                ", dateExpirationSession=" + dateExpirationSession +
                ", dureeVieSession=" + dureeVieSession +
                ", reconnexionAutomatiquePossible=" + reconnexionAutomatiquePossible +
                ", fermetureManuelle=" + fermetureManuelle +
                '}';
    }
}