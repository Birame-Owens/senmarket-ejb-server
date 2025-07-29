package authentification.dto.request;

import authentification.base.dto.BaseRequest;
import javax.validation.constraints.NotBlank;

/**
 * DTO pour les requêtes de déconnexion utilisateur
 * Contient les informations nécessaires pour terminer une session
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class LogoutRequest extends BaseRequest {
    
    private static final long serialVersionUID = 1L;
    
    @NotBlank(message = "Le token de session est obligatoire")
    private String tokenSession;
    
    /**
     * Token de rafraîchissement (optionnel)
     */
    private String tokenRefresh;
    
    /**
     * Type de déconnexion
     * Valeurs possibles: "current_session", "all_sessions", "all_devices"
     */
    private String typeDeconnexion = "current_session";
    
    /**
     * Raison de la déconnexion
     * Valeurs possibles: "user_request", "session_expired", "security_logout", "admin_action"
     */
    private String raisonDeconnexion = "user_request";
    
    /**
     * Indique si l'utilisateur souhaite être redirigé après déconnexion
     */
    private boolean avecRedirection = true;
    
    /**
     * URL de redirection après déconnexion (optionnelle)
     */
    private String urlRedirection;
    
    /**
     * Indique si c'est une déconnexion forcée (sécurité)
     */
    private boolean deconnexionForcee = false;
    
    /**
     * Message personnalisé pour la déconnexion
     */
    private String messagePersonnalise;
    
    /**
     * Identifiant de la session spécifique à fermer (pour fermeture sélective)
     */
    private String sessionIdACfermer;
    
    // Constructeurs
    public LogoutRequest() {
        super();
    }
    
    public LogoutRequest(String tokenSession) {
        super();
        this.tokenSession = tokenSession;
    }
    
    public LogoutRequest(String tokenSession, String typeDeconnexion) {
        this(tokenSession);
        this.typeDeconnexion = typeDeconnexion;
    }
    
    /**
     * Constructeur pour déconnexion forcée
     */
    public LogoutRequest(String tokenSession, String raisonDeconnexion, boolean deconnexionForcee) {
        this(tokenSession);
        this.raisonDeconnexion = raisonDeconnexion;
        this.deconnexionForcee = deconnexionForcee;
    }
    
    /**
     * Méthodes de validation spécifiques
     */
    @Override
    public boolean isValid() {
        if (!super.isValid()) {
            return false;
        }
        
        // Le token de session est obligatoire
        if (tokenSession == null || tokenSession.trim().isEmpty()) {
            return false;
        }
        
        // Validation du type de déconnexion
        if (typeDeconnexion != null) {
            switch (typeDeconnexion) {
                case "current_session":
                case "all_sessions":
                case "all_devices":
                    break;
                default:
                    return false;
            }
        }
        
        return true;
    }
    
    /**
     * Vérifie si c'est une déconnexion de toutes les sessions
     */
    public boolean isLogoutAllSessions() {
        return "all_sessions".equals(typeDeconnexion);
    }
    
    /**
     * Vérifie si c'est une déconnexion de tous les appareils
     */
    public boolean isLogoutAllDevices() {
        return "all_devices".equals(typeDeconnexion);
    }
    
    /**
     * Vérifie si c'est une déconnexion pour raison de sécurité
     */
    public boolean isSecurityLogout() {
        return "security_logout".equals(raisonDeconnexion) || deconnexionForcee;
    }
    
    /**
     * Vérifie si c'est une déconnexion sélective d'une session spécifique
     */
    public boolean isSelectiveLogout() {
        return sessionIdACfermer != null && !sessionIdACfermer.trim().isEmpty();
    }
    
    /**
     * Crée une requête de déconnexion d'urgence (sécurité)
     */
    public static LogoutRequest createSecurityLogout(String tokenSession, String raison) {
        LogoutRequest request = new LogoutRequest(tokenSession, "security_logout", true);
        request.setTypeDeconnexion("all_sessions");
        request.setAvecRedirection(false);
        request.setMessagePersonnalise("Déconnexion pour des raisons de sécurité: " + raison);
        return request;
    }
    
    /**
     * Crée une requête de déconnexion de tous les appareils
     */
    public static LogoutRequest createLogoutAllDevices(String tokenSession) {
        LogoutRequest request = new LogoutRequest(tokenSession, "all_devices");
        request.setRaisonDeconnexion("user_request");
        request.setMessagePersonnalise("Déconnexion de tous les appareils demandée par l'utilisateur");
        return request;
    }
    
    /**
     * Détermine le niveau de priorité de la déconnexion
     */
    public int getPrioriteDeconnexion() {
        if (deconnexionForcee || "security_logout".equals(raisonDeconnexion)) {
            return 3; // Priorité haute
        } else if ("all_sessions".equals(typeDeconnexion) || "all_devices".equals(typeDeconnexion)) {
            return 2; // Priorité moyenne
        } else {
            return 1; // Priorité normale
        }
    }
    
    /**
     * Nettoie les données sensibles pour les logs
     */
    public LogoutRequest sanitizeForLogging() {
        LogoutRequest sanitized = new LogoutRequest();
        
        // Masquer le token complet, ne garder que les premiers/derniers caractères
        if (this.tokenSession != null && this.tokenSession.length() > 10) {
            String masked = this.tokenSession.substring(0, 4) + "***" + 
                           this.tokenSession.substring(this.tokenSession.length() - 4);
            sanitized.setTokenSession(masked);
        }
        
        sanitized.setTypeDeconnexion(this.typeDeconnexion);
        sanitized.setRaisonDeconnexion(this.raisonDeconnexion);
        sanitized.setDeconnexionForcee(this.deconnexionForcee);
        sanitized.setAvecRedirection(this.avecRedirection);
        sanitized.setMessagePersonnalise(this.messagePersonnalise);
        
        // Copie des métadonnées de base
        sanitized.setRequestId(this.getRequestId());
        sanitized.setTimestamp(this.getTimestamp());
        sanitized.setLangue(this.getLangue());
        sanitized.setAdresseIp(this.getAdresseIp());
        
        return sanitized;
    }
    
    // Getters et Setters
    public String getTokenSession() {
        return tokenSession;
    }
    
    public void setTokenSession(String tokenSession) {
        this.tokenSession = tokenSession;
    }
    
    public String getTokenRefresh() {
        return tokenRefresh;
    }
    
    public void setTokenRefresh(String tokenRefresh) {
        this.tokenRefresh = tokenRefresh;
    }
    
    public String getTypeDeconnexion() {
        return typeDeconnexion;
    }
    
    public void setTypeDeconnexion(String typeDeconnexion) {
        this.typeDeconnexion = typeDeconnexion;
    }
    
    public String getRaisonDeconnexion() {
        return raisonDeconnexion;
    }
    
    public void setRaisonDeconnexion(String raisonDeconnexion) {
        this.raisonDeconnexion = raisonDeconnexion;
    }
    
    public boolean isAvecRedirection() {
        return avecRedirection;
    }
    
    public void setAvecRedirection(boolean avecRedirection) {
        this.avecRedirection = avecRedirection;
    }
    
    public String getUrlRedirection() {
        return urlRedirection;
    }
    
    public void setUrlRedirection(String urlRedirection) {
        this.urlRedirection = urlRedirection;
    }
    
    public boolean isDeconnexionForcee() {
        return deconnexionForcee;
    }
    
    public void setDeconnexionForcee(boolean deconnexionForcee) {
        this.deconnexionForcee = deconnexionForcee;
    }
    
    public String getMessagePersonnalise() {
        return messagePersonnalise;
    }
    
    public void setMessagePersonnalise(String messagePersonnalise) {
        this.messagePersonnalise = messagePersonnalise;
    }
    
    public String getSessionIdACfermer() {
        return sessionIdACfermer;
    }
    
    public void setSessionIdACfermer(String sessionIdACfermer) {
        this.sessionIdACfermer = sessionIdACfermer;
    }
    
    @Override
    public String toString() {
        return "LogoutRequest{" +
                "tokenSession='" + (tokenSession != null ? tokenSession.substring(0, Math.min(8, tokenSession.length())) + "..." : "null") + '\'' +
                ", typeDeconnexion='" + typeDeconnexion + '\'' +
                ", raisonDeconnexion='" + raisonDeconnexion + '\'' +
                ", deconnexionForcee=" + deconnexionForcee +
                ", priorite=" + getPrioriteDeconnexion() +
                ", requestId='" + getRequestId() + '\'' +
                '}';
    }
}