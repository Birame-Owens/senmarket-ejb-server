package authentification.dto.request;

import authentification.base.dto.BaseRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * DTO pour les requêtes de changement de mot de passe
 * Utilisé pour les changements de mot de passe par des utilisateurs authentifiés
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class ChangePasswordRequest extends BaseRequest {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Identifiant de l'utilisateur qui change son mot de passe
     */
    @NotBlank(message = "L'identifiant utilisateur est obligatoire")
    private String userId;
    
    /**
     * Mot de passe actuel (pour vérification)
     */
    @NotBlank(message = "Le mot de passe actuel est obligatoire")
    @Size(min = 6, max = 255, message = "Le mot de passe actuel doit contenir entre 6 et 255 caractères")
    private String currentPassword;
    
    /**
     * Nouveau mot de passe
     */
    @NotBlank(message = "Le nouveau mot de passe est obligatoire")
    @Size(min = 8, max = 255, message = "Le nouveau mot de passe doit contenir entre 8 et 255 caractères")
    private String newPassword;
    
    /**
     * Confirmation du nouveau mot de passe
     */
    @NotBlank(message = "La confirmation du mot de passe est obligatoire")
    @Size(min = 8, max = 255, message = "La confirmation doit contenir entre 8 et 255 caractères")
    private String confirmNewPassword;
    
    /**
     * Token de session actuel (pour validation de sécurité)
     */
    private String sessionToken;
    
    /**
     * Code de vérification 2FA (si activé)
     */
    private String twoFactorCode;
    
    /**
     * Raison du changement de mot de passe
     */
    private String changeReason;
    
    /**
     * Indique si toutes les autres sessions doivent être invalidées
     */
    private boolean invalidateOtherSessions = true;
    
    /**
     * Indique si l'utilisateur doit être notifié par email
     */
    private boolean sendNotificationEmail = true;
    
    /**
     * Indique si c'est un changement forcé (expiration, première connexion)
     */
    private boolean isForced = false;
    
    /**
     * Indication de force du nouveau mot de passe (calculée côté client)
     */
    private Integer passwordStrength;
    
    /**
     * Indique si l'utilisateur accepte la politique de mot de passe
     */
    private boolean acceptPasswordPolicy = true;
    
    /**
     * Type de changement
     */
    private ChangeType changeType = ChangeType.VOLUNTARY;
    
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
    public ChangePasswordRequest() {
        super();
    }
    
    /**
     * Constructeur de base
     */
    public ChangePasswordRequest(String userId, String currentPassword, 
                               String newPassword, String confirmNewPassword) {
        this();
        this.userId = userId;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
    }
    
    /**
     * Constructeur complet
     */
    public ChangePasswordRequest(String userId, String currentPassword, 
                               String newPassword, String confirmNewPassword, 
                               String sessionToken) {
        this(userId, currentPassword, newPassword, confirmNewPassword);
        this.sessionToken = sessionToken;
    }
    
    /**
     * Méthodes statiques pour créer des requêtes typées
     */
    
    /**
     * Crée une requête de changement volontaire
     */
    public static ChangePasswordRequest voluntary(String userId, String currentPassword, 
                                                String newPassword, String confirmPassword) {
        ChangePasswordRequest request = new ChangePasswordRequest(userId, currentPassword, 
                                                                newPassword, confirmPassword);
        request.setChangeType(ChangeType.VOLUNTARY);
        return request;
    }
    
    /**
     * Crée une requête de changement forcé pour expiration
     */
    public static ChangePasswordRequest forcedExpiration(String userId, String currentPassword, 
                                                       String newPassword, String confirmPassword) {
        ChangePasswordRequest request = new ChangePasswordRequest(userId, currentPassword, 
                                                                newPassword, confirmPassword);
        request.setChangeType(ChangeType.FORCED_EXPIRATION);
        request.setForced(true);
        request.setChangeReason("Mot de passe expiré");
        return request;
    }
    
    /**
     * Crée une requête de changement forcé pour sécurité
     */
    public static ChangePasswordRequest forcedSecurity(String userId, String currentPassword, 
                                                     String newPassword, String confirmPassword, 
                                                     String securityReason) {
        ChangePasswordRequest request = new ChangePasswordRequest(userId, currentPassword, 
                                                                newPassword, confirmPassword);
        request.setChangeType(ChangeType.FORCED_SECURITY);
        request.setForced(true);
        request.setChangeReason("Sécurité: " + securityReason);
        request.setInvalidateOtherSessions(true);
        return request;
    }
    
    /**
     * Crée une requête de changement pour première connexion
     */
    public static ChangePasswordRequest firstLogin(String userId, String temporaryPassword, 
                                                 String newPassword, String confirmPassword) {
        ChangePasswordRequest request = new ChangePasswordRequest(userId, temporaryPassword, 
                                                                newPassword, confirmPassword);
        request.setChangeType(ChangeType.FORCED_FIRST_LOGIN);
        request.setForced(true);
        request.setChangeReason("Première connexion - changement obligatoire");
        return request;
    }
    
    /**
     * Valide la cohérence de la requête
     */
    public boolean isValid() {
        // Vérification des champs obligatoires
        if (userId == null || userId.trim().isEmpty()) return false;
        if (currentPassword == null || currentPassword.trim().isEmpty()) return false;
        if (newPassword == null || newPassword.trim().isEmpty()) return false;
        if (confirmNewPassword == null || confirmNewPassword.trim().isEmpty()) return false;
        
        // Vérification que les nouveaux mots de passe correspondent
        if (!newPassword.equals(confirmNewPassword)) return false;
        
        // Vérification que le nouveau mot de passe est différent de l'ancien
        if (newPassword.equals(currentPassword)) return false;
        
        return true;
    }
    
    /**
     * Vérifie si les mots de passe correspondent
     */
    public boolean passwordsMatch() {
        return newPassword != null && newPassword.equals(confirmNewPassword);
    }
    
    /**
     * Vérifie si le nouveau mot de passe est différent de l'ancien
     */
    public boolean isPasswordChanged() {
        return !newPassword.equals(currentPassword);
    }
    
    /**
     * Indique si la 2FA est requise pour cette requête
     */
    public boolean requires2FA() {
        return changeType == ChangeType.FORCED_SECURITY || 
               changeType == ChangeType.ADMINISTRATIVE;
    }
    
    // Getters et Setters
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getCurrentPassword() {
        return currentPassword;
    }
    
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    
    public String getNewPassword() {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }
    
    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
    
    public String getSessionToken() {
        return sessionToken;
    }
    
    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
    
    public String getTwoFactorCode() {
        return twoFactorCode;
    }
    
    public void setTwoFactorCode(String twoFactorCode) {
        this.twoFactorCode = twoFactorCode;
    }
    
    public String getChangeReason() {
        return changeReason;
    }
    
    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }
    
    public boolean isInvalidateOtherSessions() {
        return invalidateOtherSessions;
    }
    
    public void setInvalidateOtherSessions(boolean invalidateOtherSessions) {
        this.invalidateOtherSessions = invalidateOtherSessions;
    }
    
    public boolean isSendNotificationEmail() {
        return sendNotificationEmail;
    }
    
    public void setSendNotificationEmail(boolean sendNotificationEmail) {
        this.sendNotificationEmail = sendNotificationEmail;
    }
    
    public boolean isForced() {
        return isForced;
    }
    
    public void setForced(boolean forced) {
        isForced = forced;
    }
    
    public Integer getPasswordStrength() {
        return passwordStrength;
    }
    
    public void setPasswordStrength(Integer passwordStrength) {
        this.passwordStrength = passwordStrength;
    }
    
    public boolean isAcceptPasswordPolicy() {
        return acceptPasswordPolicy;
    }
    
    public void setAcceptPasswordPolicy(boolean acceptPasswordPolicy) {
        this.acceptPasswordPolicy = acceptPasswordPolicy;
    }
    
    public ChangeType getChangeType() {
        return changeType;
    }
    
    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }
    
    @Override
    public String toString() {
        return "ChangePasswordRequest{" +
                "userId='" + userId + '\'' +
                ", currentPassword='" + (currentPassword != null ? "***" : null) + '\'' +
                ", newPassword='" + (newPassword != null ? "***" : null) + '\'' +
                ", sessionToken='" + (sessionToken != null ? "***" : null) + '\'' +
                ", changeReason='" + changeReason + '\'' +
                ", changeType=" + changeType +
                ", invalidateOtherSessions=" + invalidateOtherSessions +
                ", sendNotificationEmail=" + sendNotificationEmail +
                ", isForced=" + isForced +
                ", passwordStrength=" + passwordStrength +
                '}';
    }
}