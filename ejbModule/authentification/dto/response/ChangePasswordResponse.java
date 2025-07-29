package authentification.dto.response;

import authentification.base.dto.BaseResponse;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

/**
 * DTO pour les réponses de changement de mot de passe
 * Contient les informations sur le succès du changement et les actions effectuées
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class ChangePasswordResponse extends BaseResponse {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Identifiant de l'utilisateur
     */
    private String userId;
    
    /**
     * Indique si le mot de passe a été changé avec succès
     */
    private boolean passwordChanged = false;
    
    /**
     * Date du changement de mot de passe
     */
    private Timestamp passwordChangedDate;
    
    /**
     * Score de force du nouveau mot de passe (0-100)
     */
    private Integer passwordStrengthScore;
    
    /**
     * Niveau de force du mot de passe
     */
    private PasswordStrengthLevel passwordStrengthLevel;
    
    /**
     * Indique si les autres sessions ont été invalidées
     */
    private boolean otherSessionsInvalidated = false;
    
    /**
     * Nombre de sessions invalidées
     */
    private Integer invalidatedSessionsCount;
    
    /**
     * Liste des appareils/sessions invalidés
     */
    private List<String> invalidatedDevices;
    
    /**
     * Indique si un email de notification a été envoyé
     */
    private boolean notificationEmailSent = false;
    
    /**
     * Indique si c'était un changement forcé
     */
    private boolean wasForcedChange = false;
    
    /**
     * Prochaine date d'expiration du mot de passe
     */
    private Timestamp nextPasswordExpirationDate;
    
    /**
     * Nombre de jours avant la prochaine expiration
     */
    private Integer daysUntilNextExpiration;
    
    /**
     * Recommandations de sécurité
     */
    private List<String> securityRecommendations;
    
    /**
     * Avertissements de sécurité
     */
    private List<String> securityWarnings;
    
    /**
     * Instructions pour l'utilisateur
     */
    private String userInstructions;
    
    /**
     * Lien vers les paramètres de sécurité
     */
    private String securitySettingsUrl;
    
    /**
     * Indique si l'activation de la 2FA est recommandée
     */
    private boolean twoFactorRecommended = false;
    
    /**
     * Type de changement effectué
     */
    private ChangeType changeType;
    
    /**
     * Énumération des niveaux de force de mot de passe
     */
    public enum PasswordStrengthLevel {
        VERY_WEAK("Très faible"),
        WEAK("Faible"),
        FAIR("Acceptable"),
        GOOD("Bon"),
        STRONG("Fort"),
        VERY_STRONG("Très fort");
        
        private final String description;
        
        PasswordStrengthLevel(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
        
        public static PasswordStrengthLevel fromScore(int score) {
            if (score < 20) return VERY_WEAK;
            if (score < 40) return WEAK;
            if (score < 60) return FAIR;
            if (score < 80) return GOOD;
            if (score < 95) return STRONG;
            return VERY_STRONG;
        }
    }
    
    /**
     * Énumération des types de changement
     */
    public enum ChangeType {
        VOLUNTARY("Changement volontaire"),
        FORCED_EXPIRATION("Changement forcé - expiration"),
        FORCED_SECURITY("Changement forcé - sécurité"),
        FORCED_FIRST_LOGIN("Changement forcé - première connexion"),
        ADMINISTRATIVE("Changement administratif");
        
        private final String description;
        
        ChangeType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    // Constructeur par défaut
    public ChangePasswordResponse() {
        super();
        this.invalidatedDevices = new ArrayList<>();
        this.securityRecommendations = new ArrayList<>();
        this.securityWarnings = new ArrayList<>();
    }
    
    /**
     * Constructeur de succès
     */
    public ChangePasswordResponse(boolean success, String message) {
        this();
        this.setSuccess(success);
        this.setMessage(message);
        if (success) {
            this.passwordChanged = true;
            this.passwordChangedDate = new Timestamp(System.currentTimeMillis());
        }
    }
    
    /**
     * Méthodes statiques pour créer des réponses typées
     */
    
    /**
     * Crée une réponse de succès simple
     */
    public static ChangePasswordResponse success(String userId) {
        ChangePasswordResponse response = new ChangePasswordResponse(true, 
            "Mot de passe changé avec succès");
        response.setUserId(userId);
        response.setChangeType(ChangeType.VOLUNTARY);
        response.setUserInstructions("Votre mot de passe a été mis à jour avec succès");
        return response;
    }
    
    /**
     * Crée une réponse de succès avec invalidation de sessions
     */
    public static ChangePasswordResponse successWithSessionInvalidation(String userId, 
                                                                       int invalidatedCount) {
        ChangePasswordResponse response = success(userId);
        response.setOtherSessionsInvalidated(true);
        response.setInvalidatedSessionsCount(invalidatedCount);
        response.setUserInstructions("Mot de passe changé avec succès. " + invalidatedCount + 
                                   " autres sessions ont été fermées pour votre sécurité");
        return response;
    }
    
    /**
     * Crée une réponse de succès avec évaluation de force
     */
    public static ChangePasswordResponse successWithStrengthEvaluation(String userId, 
                                                                      int strengthScore) {
        ChangePasswordResponse response = success(userId);
        response.setPasswordStrengthScore(strengthScore);
        response.setPasswordStrengthLevel(PasswordStrengthLevel.fromScore(strengthScore));
        
        // Ajouter des recommandations selon le score
        if (strengthScore < 60) {
            response.addSecurityRecommendation("Considérez utiliser un mot de passe plus fort");
            response.setTwoFactorRecommended(true);
        }
        if (strengthScore < 40) {
            response.addSecurityWarning("Votre mot de passe est relativement faible");
        }
        
        return response;
    }
    
    /**
     * Crée une réponse de succès pour changement forcé
     */
    public static ChangePasswordResponse successForced(String userId, ChangeType forceReason) {
        ChangePasswordResponse response = success(userId);
        response.setWasForcedChange(true);
        response.setChangeType(forceReason);
        response.setOtherSessionsInvalidated(true);
        
        String instructions = "Mot de passe changé avec succès suite à : " + 
                            forceReason.getDescription();
        response.setUserInstructions(instructions);
        
        // Recommandations spécifiques selon la raison
        switch (forceReason) {
            case FORCED_EXPIRATION:
                response.addSecurityRecommendation("Configurez un rappel pour changer votre mot de passe régulièrement");
                break;
            case FORCED_SECURITY:
                response.addSecurityRecommendation("Activez l'authentification à deux facteurs");
                response.addSecurityRecommendation("Vérifiez votre historique de connexions");
                response.setTwoFactorRecommended(true);
                break;
            case FORCED_FIRST_LOGIN:
                response.addSecurityRecommendation("Explorez les paramètres de sécurité de votre compte");
                break;
        }
        
        return response;
    }
    
    /**
     * Crée une réponse de succès avec expiration programmée
     */
    public static ChangePasswordResponse successWithExpiration(String userId, 
                                                              Timestamp nextExpiration) {
        ChangePasswordResponse response = success(userId);
        response.setNextPasswordExpirationDate(nextExpiration);
        
        long now = System.currentTimeMillis();
        long daysUntilExpiration = (nextExpiration.getTime() - now) / (1000 * 60 * 60 * 24);
        response.setDaysUntilNextExpiration((int) daysUntilExpiration);
        
        response.setUserInstructions("Mot de passe changé avec succès. " +
                                   "Il expirera dans " + daysUntilExpiration + " jours");
        
        return response;
    }
    
    /**
     * Crée une réponse d'échec
     */
    public static ChangePasswordResponse failure(String userId, String reason) {
        ChangePasswordResponse response = new ChangePasswordResponse(false, 
            "Échec du changement de mot de passe : " + reason);
        response.setUserId(userId);
        response.setUserInstructions("Le changement de mot de passe a échoué. " + reason);
        return response;
    }
    
    /**
     * Ajoute une recommandation de sécurité
     */
    public void addSecurityRecommendation(String recommendation) {
        if (this.securityRecommendations == null) {
            this.securityRecommendations = new ArrayList<>();
        }
        this.securityRecommendations.add(recommendation);
    }
    
    /**
     * Ajoute un avertissement de sécurité
     */
    public void addSecurityWarning(String warning) {
        if (this.securityWarnings == null) {
            this.securityWarnings = new ArrayList<>();
        }
        this.securityWarnings.add(warning);
    }
    
    /**
     * Ajoute un appareil invalidé
     */
    public void addInvalidatedDevice(String deviceInfo) {
        if (this.invalidatedDevices == null) {
            this.invalidatedDevices = new ArrayList<>();
        }
        this.invalidatedDevices.add(deviceInfo);
    }
    
    /**
     * Indique si le mot de passe est considéré comme fort
     */
    public boolean isPasswordStrong() {
        return passwordStrengthScore != null && passwordStrengthScore >= 80;
    }
    
    /**
     * Indique si des actions de sécurité sont recommandées
     */
    public boolean hasSecurityRecommendations() {
        return securityRecommendations != null && !securityRecommendations.isEmpty();
    }
    
    /**
     * Indique si il y a des avertissements de sécurité
     */
    public boolean hasSecurityWarnings() {
        return securityWarnings != null && !securityWarnings.isEmpty();
    }
    
    // Getters et Setters
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public boolean isPasswordChanged() {
        return passwordChanged;
    }
    
    public void setPasswordChanged(boolean passwordChanged) {
        this.passwordChanged = passwordChanged;
    }
    
    public Timestamp getPasswordChangedDate() {
        return passwordChangedDate;
    }
    
    public void setPasswordChangedDate(Timestamp passwordChangedDate) {
        this.passwordChangedDate = passwordChangedDate;
    }
    
    public Integer getPasswordStrengthScore() {
        return passwordStrengthScore;
    }
    
    public void setPasswordStrengthScore(Integer passwordStrengthScore) {
        this.passwordStrengthScore = passwordStrengthScore;
        if (passwordStrengthScore != null) {
            this.passwordStrengthLevel = PasswordStrengthLevel.fromScore(passwordStrengthScore);
        }
    }
    
    public PasswordStrengthLevel getPasswordStrengthLevel() {
        return passwordStrengthLevel;
    }
    
    public void setPasswordStrengthLevel(PasswordStrengthLevel passwordStrengthLevel) {
        this.passwordStrengthLevel = passwordStrengthLevel;
    }
    
    public boolean isOtherSessionsInvalidated() {
        return otherSessionsInvalidated;
    }
    
    public void setOtherSessionsInvalidated(boolean otherSessionsInvalidated) {
        this.otherSessionsInvalidated = otherSessionsInvalidated;
    }
    
    public Integer getInvalidatedSessionsCount() {
        return invalidatedSessionsCount;
    }
    
    public void setInvalidatedSessionsCount(Integer invalidatedSessionsCount) {
        this.invalidatedSessionsCount = invalidatedSessionsCount;
    }
    
    public List<String> getInvalidatedDevices() {
        return invalidatedDevices;
    }
    
    public void setInvalidatedDevices(List<String> invalidatedDevices) {
        this.invalidatedDevices = invalidatedDevices;
    }
    
    public boolean isNotificationEmailSent() {
        return notificationEmailSent;
    }
    
    public void setNotificationEmailSent(boolean notificationEmailSent) {
        this.notificationEmailSent = notificationEmailSent;
    }
    
    public boolean isWasForcedChange() {
        return wasForcedChange;
    }
    
    public void setWasForcedChange(boolean wasForcedChange) {
        this.wasForcedChange = wasForcedChange;
    }
    
    public Timestamp getNextPasswordExpirationDate() {
        return nextPasswordExpirationDate;
    }
    
    public void setNextPasswordExpirationDate(Timestamp nextPasswordExpirationDate) {
        this.nextPasswordExpirationDate = nextPasswordExpirationDate;
    }
    
    public Integer getDaysUntilNextExpiration() {
        return daysUntilNextExpiration;
    }
    
    public void setDaysUntilNextExpiration(Integer daysUntilNextExpiration) {
        this.daysUntilNextExpiration = daysUntilNextExpiration;
    }
    
    public List<String> getSecurityRecommendations() {
        return securityRecommendations;
    }
    
    public void setSecurityRecommendations(List<String> securityRecommendations) {
        this.securityRecommendations = securityRecommendations;
    }
    
    public List<String> getSecurityWarnings() {
        return securityWarnings;
    }
    
    public void setSecurityWarnings(List<String> securityWarnings) {
        this.securityWarnings = securityWarnings;
    }
    
    public String getUserInstructions() {
        return userInstructions;
    }
    
    public void setUserInstructions(String userInstructions) {
        this.userInstructions = userInstructions;
    }
    
    public String getSecuritySettingsUrl() {
        return securitySettingsUrl;
    }
    
    public void setSecuritySettingsUrl(String securitySettingsUrl) {
        this.securitySettingsUrl = securitySettingsUrl;
    }
    
    public boolean isTwoFactorRecommended() {
        return twoFactorRecommended;
    }
    
    public void setTwoFactorRecommended(boolean twoFactorRecommended) {
        this.twoFactorRecommended = twoFactorRecommended;
    }
    
    public ChangeType getChangeType() {
        return changeType;
    }
    
    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }
    
    @Override
    public String toString() {
        return "ChangePasswordResponse{" +
                "success=" + getSuccess() +
                ", message='" + getMessage() + '\'' +
                ", userId='" + userId + '\'' +
                ", passwordChanged=" + passwordChanged +
                ", passwordChangedDate=" + passwordChangedDate +
                ", passwordStrengthScore=" + passwordStrengthScore +
                ", passwordStrengthLevel=" + passwordStrengthLevel +
                ", otherSessionsInvalidated=" + otherSessionsInvalidated +
                ", invalidatedSessionsCount=" + invalidatedSessionsCount +
                ", notificationEmailSent=" + notificationEmailSent +
                ", wasForcedChange=" + wasForcedChange +
                ", changeType=" + changeType +
                ", twoFactorRecommended=" + twoFactorRecommended +
                '}';
    }
}