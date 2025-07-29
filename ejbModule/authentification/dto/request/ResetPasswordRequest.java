package authentification.dto.request;

import authentification.base.dto.BaseRequest;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * DTO pour les requêtes de réinitialisation de mot de passe
 * Supporte à la fois l'initiation et la finalisation de la réinitialisation
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class ResetPasswordRequest extends BaseRequest {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Email de l'utilisateur (pour initier la réinitialisation)
     */
    @Email(message = "Format d'email invalide")
    @Size(max = 150, message = "L'email ne peut pas dépasser 150 caractères")
    private String email;
    
    /**
     * Token de réinitialisation reçu par email (pour finaliser)
     */
    @Size(min = 10, max = 255, message = "Le token doit contenir entre 10 et 255 caractères")
    private String resetToken;
    
    /**
     * Nouveau mot de passe (pour finaliser la réinitialisation)
     */
    @Size(min = 8, max = 255, message = "Le mot de passe doit contenir entre 8 et 255 caractères")
    private String newPassword;
    
    /**
     * Confirmation du nouveau mot de passe
     */
    @Size(min = 8, max = 255, message = "La confirmation doit contenir entre 8 et 255 caractères")
    private String confirmNewPassword;
    
    /**
     * Type d'opération demandée
     */
    private OperationType operationType = OperationType.INITIATE;
    
    /**
     * Méthode d'envoi du token (EMAIL, SMS)
     */
    private String deliveryMethod = "EMAIL";
    
    /**
     * Numéro de téléphone (si deliveryMethod = SMS)
     */
    private String phoneNumber;
    
    /**
     * Code de sécurité additionnel (si requis)
     */
    private String securityCode;
    
    /**
     * Indique si toutes les sessions doivent être invalidées après réinitialisation
     */
    private boolean invalidateAllSessions = true;
    
    /**
     * Indique si l'utilisateur doit être connecté automatiquement après réinitialisation
     */
    private boolean autoLogin = false;
    
    /**
     * Raison de la réinitialisation (optionnel)
     */
    private String resetReason;
    
    /**
     * Énumération des types d'opération
     */
    public enum OperationType {
        INITIATE("Initier la réinitialisation"),
        COMPLETE("Finaliser la réinitialisation"),
        VERIFY_TOKEN("Vérifier le token seulement");
        
        private final String description;
        
        OperationType(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }
    
    // Constructeur par défaut
    public ResetPasswordRequest() {
        super();
    }
    
    /**
     * Constructeur pour initier la réinitialisation
     */
    public ResetPasswordRequest(String email) {
        this();
        this.email = email;
        this.operationType = OperationType.INITIATE;
    }
    
    /**
     * Constructeur pour finaliser la réinitialisation
     */
    public ResetPasswordRequest(String resetToken, String newPassword, String confirmNewPassword) {
        this();
        this.resetToken = resetToken;
        this.newPassword = newPassword;
        this.confirmNewPassword = confirmNewPassword;
        this.operationType = OperationType.COMPLETE;
    }
    
    /**
     * Méthodes statiques pour créer des requêtes typées
     */
    
    /**
     * Crée une requête d'initiation de réinitialisation
     */
    public static ResetPasswordRequest initiate(String email) {
        return new ResetPasswordRequest(email);
    }
    
    /**
     * Crée une requête d'initiation avec SMS
     */
    public static ResetPasswordRequest initiateWithSMS(String email, String phoneNumber) {
        ResetPasswordRequest request = new ResetPasswordRequest(email);
        request.setDeliveryMethod("SMS");
        request.setPhoneNumber(phoneNumber);
        return request;
    }
    
    /**
     * Crée une requête de finalisation
     */
    public static ResetPasswordRequest complete(String resetToken, String newPassword, String confirmPassword) {
        return new ResetPasswordRequest(resetToken, newPassword, confirmPassword);
    }
    
    /**
     * Crée une requête de vérification de token
     */
    public static ResetPasswordRequest verifyToken(String resetToken) {
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setResetToken(resetToken);
        request.setOperationType(OperationType.VERIFY_TOKEN);
        return request;
    }
    
    /**
     * Valide la cohérence de la requête selon le type d'opération
     */
    public boolean isValid() {
        switch (operationType) {
            case INITIATE:
                return email != null && !email.trim().isEmpty();
            case COMPLETE:
                return resetToken != null && !resetToken.trim().isEmpty() &&
                       newPassword != null && !newPassword.trim().isEmpty() &&
                       confirmNewPassword != null && newPassword.equals(confirmNewPassword);
            case VERIFY_TOKEN:
                return resetToken != null && !resetToken.trim().isEmpty();
            default:
                return false;
        }
    }
    
    /**
     * Indique si c'est une requête d'initiation
     */
    public boolean isInitiateOperation() {
        return OperationType.INITIATE.equals(operationType);
    }
    
    /**
     * Indique si c'est une requête de finalisation
     */
    public boolean isCompleteOperation() {
        return OperationType.COMPLETE.equals(operationType);
    }
    
    /**
     * Indique si c'est une requête de vérification
     */
    public boolean isVerifyOperation() {
        return OperationType.VERIFY_TOKEN.equals(operationType);
    }
    
    // Getters et Setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getResetToken() {
        return resetToken;
    }
    
    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
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
    
    public OperationType getOperationType() {
        return operationType;
    }
    
    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }
    
    public String getDeliveryMethod() {
        return deliveryMethod;
    }
    
    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getSecurityCode() {
        return securityCode;
    }
    
    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
    
    public boolean isInvalidateAllSessions() {
        return invalidateAllSessions;
    }
    
    public void setInvalidateAllSessions(boolean invalidateAllSessions) {
        this.invalidateAllSessions = invalidateAllSessions;
    }
    
    public boolean isAutoLogin() {
        return autoLogin;
    }
    
    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }
    
    public String getResetReason() {
        return resetReason;
    }
    
    public void setResetReason(String resetReason) {
        this.resetReason = resetReason;
    }
    
    @Override
    public String toString() {
        return "ResetPasswordRequest{" +
                "email='" + email + '\'' +
                ", resetToken='" + (resetToken != null ? "***" : null) + '\'' +
                ", newPassword='" + (newPassword != null ? "***" : null) + '\'' +
                ", operationType=" + operationType +
                ", deliveryMethod='" + deliveryMethod + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", invalidateAllSessions=" + invalidateAllSessions +
                ", autoLogin=" + autoLogin +
                ", resetReason='" + resetReason + '\'' +
                '}';
    }
}