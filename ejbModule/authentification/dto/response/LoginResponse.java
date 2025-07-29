package authentification.dto.response;

import authentification.dto.common.*;



import authentification.base.dto.BaseResponse;
import java.sql.Timestamp;

/**
 * DTO pour les réponses de connexion utilisateur
 * Contient les informations de l'utilisateur connecté et de sa session
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class LoginResponse extends BaseResponse {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Informations de l'utilisateur connecté
     */
    private UserResponse utilisateur;
    
    /**
     * Informations de la session créée
     */
    private SessionResponse session;
    
    /**
     * Token d'accès pour les requêtes suivantes
     */
    private String tokenAcces;
    
    /**
     * Token de rafraîchissement
     */
    private String tokenRefresh;
    
    /**
     * Date d'expiration du token d'accès
     */
    private Timestamp dateExpirationToken;
    
    /**
     * Durée de validité du token en secondes
     */
    private Long dureeValiditeToken;
    
    /**
     * Permissions de l'utilisateur
     */
    private String[] permissions;
    
    /**
     * Indique si c'est une première connexion
     */
    private boolean premiereConnexion = false;
    
    /**
     * Indique si l'authentification 2FA est requise
     */
    private boolean requiresDoubleAuth = false;
    
    /**
     * Indique si le mot de passe doit être changé
     */
    private boolean doitChangerMotDePasse = false;
    
    /**
     * URL de redirection après connexion
     */
    private String urlRedirection;
    
    /**
     * Préférences utilisateur essentielles
     */
    private PreferencesConnexion preferences;
    
    /**
     * Informations de sécurité
     */
    private SecurityInfo securite;
    
    /**
     * Statistiques de connexion
     */
    private StatistiquesConnexion statistiques;
    
    /**
     * Classe interne pour les préférences de connexion
     */
    public static class PreferencesConnexion {
        private String langue;
        private String theme;
        private String fuseauHoraire;
        private boolean notificationsActivees;
        private String urlPageAccueil;
        
        public PreferencesConnexion() {}
        
        public PreferencesConnexion(String langue, String theme, String fuseauHoraire) {
            this.langue = langue;
            this.theme = theme;
            this.fuseauHoraire = fuseauHoraire;
            this.notificationsActivees = true;
        }
        
        // Getters et Setters
        public String getLangue() { return langue; }
        public void setLangue(String langue) { this.langue = langue; }
        
        public String getTheme() { return theme; }
        public void setTheme(String theme) { this.theme = theme; }
        
        public String getFuseauHoraire() { return fuseauHoraire; }
        public void setFuseauHoraire(String fuseauHoraire) { this.fuseauHoraire = fuseauHoraire; }
        
        public boolean isNotificationsActivees() { return notificationsActivees; }
        public void setNotificationsActivees(boolean notificationsActivees) { this.notificationsActivees = notificationsActivees; }
        
        public String getUrlPageAccueil() { return urlPageAccueil; }
        public void setUrlPageAccueil(String urlPageAccueil) { this.urlPageAccueil = urlPageAccueil; }
    }
    
    /**
     * Classe interne pour les informations de sécurité
     */
    public static class SecurityInfo {
        private boolean compteVerrouille;
        private boolean motDePasseExpire;
        private int tentativesRestantes;
        private Timestamp derniereTentativeEchec;
        private boolean nouvelleAdresseIP;
        private boolean nouvelAppareil;
        
        public SecurityInfo() {}
        
        // Getters et Setters
        public boolean isCompteVerrouille() { return compteVerrouille; }
        public void setCompteVerrouille(boolean compteVerrouille) { this.compteVerrouille = compteVerrouille; }
        
        public boolean isMotDePasseExpire() { return motDePasseExpire; }
        public void setMotDePasseExpire(boolean motDePasseExpire) { this.motDePasseExpire = motDePasseExpire; }
        
        public int getTentativesRestantes() { return tentativesRestantes; }
        public void setTentativesRestantes(int tentativesRestantes) { this.tentativesRestantes = tentativesRestantes; }
        
        public Timestamp getDerniereTentativeEchec() { return derniereTentativeEchec; }
        public void setDerniereTentativeEchec(Timestamp derniereTentativeEchec) { this.derniereTentativeEchec = derniereTentativeEchec; }
        
        public boolean isNouvelleAdresseIP() { return nouvelleAdresseIP; }
        public void setNouvelleAdresseIP(boolean nouvelleAdresseIP) { this.nouvelleAdresseIP = nouvelleAdresseIP; }
        
        public boolean isNouvelAppareil() { return nouvelAppareil; }
        public void setNouvelAppareil(boolean nouvelAppareil) { this.nouvelAppareil = nouvelAppareil; }
    }
    
    /**
     * Classe interne pour les statistiques de connexion
     */
    public static class StatistiquesConnexion {
        private int nombreConnexionsTotal;
        private Timestamp derniereConnexionReussie;
        private String derniereAdresseIP;
        private int nombreSessionsActives;
        private Long dureeSessionMoyenne; // en minutes
        
        public StatistiquesConnexion() {}
        
        // Getters et Setters
        public int getNombreConnexionsTotal() { return nombreConnexionsTotal; }
        public void setNombreConnexionsTotal(int nombreConnexionsTotal) { this.nombreConnexionsTotal = nombreConnexionsTotal; }
        
        public Timestamp getDerniereConnexionReussie() { return derniereConnexionReussie; }
        public void setDerniereConnexionReussie(Timestamp derniereConnexionReussie) { this.derniereConnexionReussie = derniereConnexionReussie; }
        
        public String getDerniereAdresseIP() { return derniereAdresseIP; }
        public void setDerniereAdresseIP(String derniereAdresseIP) { this.derniereAdresseIP = derniereAdresseIP; }
        
        public int getNombreSessionsActives() { return nombreSessionsActives; }
        public void setNombreSessionsActives(int nombreSessionsActives) { this.nombreSessionsActives = nombreSessionsActives; }
        
        public Long getDureeSessionMoyenne() { return dureeSessionMoyenne; }
        public void setDureeSessionMoyenne(Long dureeSessionMoyenne) { this.dureeSessionMoyenne = dureeSessionMoyenne; }
    }
    
    // Constructeurs
    public LoginResponse() {
        super(true, "Connexion réussie");
        this.preferences = new PreferencesConnexion();
        this.securite = new SecurityInfo();
        this.statistiques = new StatistiquesConnexion();
    }
    
    public LoginResponse(UserResponse utilisateur, SessionResponse session, String tokenAcces) {
        this();
        this.utilisateur = utilisateur;
        this.session = session;
        this.tokenAcces = tokenAcces;
    }
    
    /**
     * Méthodes statiques pour créer des réponses spécifiques
     */
    
    /**
     * Connexion réussie standard
     */
    public static LoginResponse success(UserResponse utilisateur, SessionResponse session, String tokenAcces) {
        LoginResponse response = new LoginResponse(utilisateur, session, tokenAcces);
        response.setSuccess(true);
        response.setMessage("Connexion réussie");
        response.setStatusCode("LOGIN_SUCCESS");
        return response;
    }
    
    /**
     * Connexion réussie mais nécessite 2FA
     */
    public static LoginResponse requiresTwoFactor(String email) {
        LoginResponse response = new LoginResponse();
        response.setSuccess(false);
        response.setMessage("Authentification à deux facteurs requise");
        response.setStatusCode("REQUIRES_2FA");
        response.setRequiresDoubleAuth(true);
        response.addMetadonnee("email", email);
        return response;
    }
    
    /**
     * Connexion refusée - identifiants incorrects
     */
    public static LoginResponse invalidCredentials(int tentativesRestantes) {
        LoginResponse response = new LoginResponse();
        response.setSuccess(false);
        response.setMessage("Email ou mot de passe incorrect");
        response.setStatusCode("INVALID_CREDENTIALS");
        response.getSecurite().setTentativesRestantes(tentativesRestantes);
        return response;
    }
    
    /**
     * Compte verrouillé
     */
    public static LoginResponse accountLocked(Timestamp verrouillageJusqu) {
        LoginResponse response = new LoginResponse();
        response.setSuccess(false);
        response.setMessage("Compte temporairement verrouillé suite à plusieurs tentatives d'échec");
        response.setStatusCode("ACCOUNT_LOCKED");
        response.getSecurite().setCompteVerrouille(true);
        response.addMetadonnee("verrouillageJusqu", verrouillageJusqu);
        return response;
    }
    
    /**
     * Mot de passe expiré
     */
    public static LoginResponse passwordExpired() {
        LoginResponse response = new LoginResponse();
        response.setSuccess(false);
        response.setMessage("Votre mot de passe a expiré, veuillez le changer");
        response.setStatusCode("PASSWORD_EXPIRED");
        response.setDoitChangerMotDePasse(true);
        response.getSecurite().setMotDePasseExpire(true);
        return response;
    }
    
    /**
     * Première connexion
     */
    public static LoginResponse firstLogin(UserResponse utilisateur, SessionResponse session, String tokenAcces) {
        LoginResponse response = success(utilisateur, session, tokenAcces);
        response.setPremiereConnexion(true);
        response.setMessage("Première connexion réussie ! Bienvenue sur SenMarket");
        response.setStatusCode("FIRST_LOGIN_SUCCESS");
        response.setUrlRedirection("/onboarding");
        return response;
    }
    
    /**
     * Méthodes utilitaires
     */
    
    /**
     * Vérifie si la connexion a réussi et que l'utilisateur est connecté
     */
    public boolean isUserLoggedIn() {
        return getSuccess() && utilisateur != null && tokenAcces != null && !tokenAcces.trim().isEmpty();
    }
    
    /**
     * Vérifie si des actions supplémentaires sont requises
     */
    public boolean requiresAdditionalAction() {
        return requiresDoubleAuth || doitChangerMotDePasse || premiereConnexion;
    }
    
    /**
     * Vérifie si le token va bientôt expirer (dans les 5 prochaines minutes)
     */
    public boolean tokenExpiresSoon() {
        if (dateExpirationToken == null) {
            return false;
        }
        
        long maintenant = System.currentTimeMillis();
        long expiration = dateExpirationToken.getTime();
        long cinqMinutes = 5 * 60 * 1000; // 5 minutes en millisecondes
        
        return (expiration - maintenant) <= cinqMinutes;
    }
    
    /**
     * Retourne le temps restant avant expiration du token (en minutes)
     */
    public Long getTempsRestantToken() {
        if (dateExpirationToken == null) {
            return null;
        }
        
        long maintenant = System.currentTimeMillis();
        long expiration = dateExpirationToken.getTime();
        
        if (expiration <= maintenant) {
            return 0L; // Token déjà expiré
        }
        
        return (expiration - maintenant) / (1000 * 60); // Conversion en minutes
    }
    
    /**
     * Masque les données sensibles pour les logs
     */
    public LoginResponse sanitizeForLogging() {
        LoginResponse sanitized = new LoginResponse();
        
        // Copie les données non sensibles
        sanitized.setSuccess(this.getSuccess());
        sanitized.setMessage(this.getMessage());
        sanitized.setStatusCode(this.getStatusCode());
        sanitized.setPremiereConnexion(this.premiereConnexion);
        sanitized.setRequiresDoubleAuth(this.requiresDoubleAuth);
        sanitized.setDoitChangerMotDePasse(this.doitChangerMotDePasse);
        sanitized.setUrlRedirection(this.urlRedirection);
        
        // Masque les tokens
        if (this.tokenAcces != null && this.tokenAcces.length() > 10) {
            String maskedToken = this.tokenAcces.substring(0, 4) + "***" + 
                               this.tokenAcces.substring(this.tokenAcces.length() - 4);
            sanitized.setTokenAcces(maskedToken);
        }
        
        // Copie l'utilisateur sans données sensibles
        if (this.utilisateur != null) {
            sanitized.setUtilisateur(this.utilisateur.sanitizeForLogging());
        }
        
        // Copie les statistiques
        sanitized.setStatistiques(this.statistiques);
        sanitized.setSecurite(this.securite);
        
        return sanitized;
    }
    
    // Getters et Setters
    public UserResponse getUtilisateur() {
        return utilisateur;
    }
    
    public void setUtilisateur(UserResponse utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    public SessionResponse getSession() {
        return session;
    }
    
    public void setSession(SessionResponse session) {
        this.session = session;
    }
    
    public String getTokenAcces() {
        return tokenAcces;
    }
    
    public void setTokenAcces(String tokenAcces) {
        this.tokenAcces = tokenAcces;
    }
    
    public String getTokenRefresh() {
        return tokenRefresh;
    }
    
    public void setTokenRefresh(String tokenRefresh) {
        this.tokenRefresh = tokenRefresh;
    }
    
    public Timestamp getDateExpirationToken() {
        return dateExpirationToken;
    }
    
    public void setDateExpirationToken(Timestamp dateExpirationToken) {
        this.dateExpirationToken = dateExpirationToken;
    }
    
    public Long getDureeValiditeToken() {
        return dureeValiditeToken;
    }
    
    public void setDureeValiditeToken(Long dureeValiditeToken) {
        this.dureeValiditeToken = dureeValiditeToken;
    }
    
    public String[] getPermissions() {
        return permissions;
    }
    
    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }
    
    public boolean isPremiereConnexion() {
        return premiereConnexion;
    }
    
    public void setPremiereConnexion(boolean premiereConnexion) {
        this.premiereConnexion = premiereConnexion;
    }
    
    public boolean isRequiresDoubleAuth() {
        return requiresDoubleAuth;
    }
    
    public void setRequiresDoubleAuth(boolean requiresDoubleAuth) {
        this.requiresDoubleAuth = requiresDoubleAuth;
    }
    
    public boolean isDoitChangerMotDePasse() {
        return doitChangerMotDePasse;
    }
    
    public void setDoitChangerMotDePasse(boolean doitChangerMotDePasse) {
        this.doitChangerMotDePasse = doitChangerMotDePasse;
    }
    
    public String getUrlRedirection() {
        return urlRedirection;
    }
    
    public void setUrlRedirection(String urlRedirection) {
        this.urlRedirection = urlRedirection;
    }
    
    public PreferencesConnexion getPreferences() {
        return preferences;
    }
    
    public void setPreferences(PreferencesConnexion preferences) {
        this.preferences = preferences;
    }
    
    public SecurityInfo getSecurite() {
        return securite;
    }
    
    public void setSecurite(SecurityInfo securite) {
        this.securite = securite;
    }
    
    public StatistiquesConnexion getStatistiques() {
        return statistiques;
    }
    
    public void setStatistiques(StatistiquesConnexion statistiques) {
        this.statistiques = statistiques;
    }
    
    @Override
    public String toString() {
        return "LoginResponse{" +
                "success=" + getSuccess() +
                ", statusCode='" + getStatusCode() + '\'' +
                ", utilisateur=" + (utilisateur != null ? utilisateur.getEmail() : "null") +
                ", hasToken=" + (tokenAcces != null && !tokenAcces.isEmpty()) +
                ", premiereConnexion=" + premiereConnexion +
                ", requiresDoubleAuth=" + requiresDoubleAuth +
                ", doitChangerMotDePasse=" + doitChangerMotDePasse +
                ", tempsRestantToken=" + getTempsRestantToken() + "min" +
                '}';
    }
}