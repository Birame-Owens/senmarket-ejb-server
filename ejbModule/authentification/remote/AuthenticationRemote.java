package authentification.remote;

import authentification.dto.request.LoginRequest;
import authentification.dto.request.LogoutRequest;
import authentification.dto.request.RefreshTokenRequest;
import authentification.dto.request.*;
import authentification.dto.request.ChangePasswordRequest;
import authentification.dto.request.ValidateTokenRequest;

import authentification.dto.response.LoginResponse;
import authentification.dto.response.LogoutResponse;
import authentification.dto.response.RefreshTokenResponse;
import authentification.dto.response.ResetPasswordResponse;
import authentification.dto.response.ChangePasswordResponse;
import authentification.dto.response.ValidateTokenResponse;

import authentification.dto.exception.AuthenticationException;
import authentification.dto.exception.InvalidCredentialsException;
import authentification.dto.exception.SessionExpiredException;
import authentification.dto.exception.UserNotFoundException;

import javax.ejb.Remote;

/**
 * Interface remote pour les opérations d'authentification dans SenMarket
 * Cette interface expose les services d'authentification pour les clients distants
 * 
 * @author SenMarket Team
 * @version 1.0
 */
@Remote
public interface AuthenticationRemote {
    
    /**
     * Authentifie un utilisateur avec ses identifiants
     * 
     * @param request Requête de connexion contenant email/mot de passe
     * @return Réponse de connexion avec token et informations utilisateur
     * @throws InvalidCredentialsException Si les identifiants sont incorrects
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur d'authentification
     */
    LoginResponse login(LoginRequest request) throws AuthenticationException;
    
    /**
     * Déconnecte un utilisateur et invalide sa session
     * 
     * @param request Requête de déconnexion avec token de session
     * @return Réponse confirmant la déconnexion
     * @throws SessionExpiredException Si la session est déjà expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    LogoutResponse logout(LogoutRequest request) throws AuthenticationException;
    
    /**
     * Valide un token d'authentification
     * 
     * @param request Requête de validation avec token
     * @return Réponse avec informations de validité du token
     * @throws SessionExpiredException Si le token est expiré
     * @throws AuthenticationException Pour toute autre erreur
     */
    ValidateTokenResponse validateToken(ValidateTokenRequest request) throws AuthenticationException;
    
    /**
     * Renouvelle un token d'authentification
     * 
     * @param request Requête de renouvellement avec refresh token
     * @return Réponse avec nouveau token d'accès
     * @throws SessionExpiredException Si le refresh token est expiré
     * @throws AuthenticationException Pour toute autre erreur
     */
    RefreshTokenResponse refreshToken(RefreshTokenRequest request) throws AuthenticationException;
    
    /**
     * Initie la procédure de réinitialisation de mot de passe
     * 
     * @param request Requête avec email de l'utilisateur
     * @return Réponse confirmant l'envoi du lien de réinitialisation
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur
     */
    ResetPasswordResponse initiatePasswordReset(ResetPasswordRequest request) throws AuthenticationException;
    
    /**
     * Finalise la réinitialisation de mot de passe avec le token reçu
     * 
     * @param request Requête avec token de réinitialisation et nouveau mot de passe
     * @return Réponse confirmant le changement de mot de passe
     * @throws UserNotFoundException Si le token est invalide
     * @throws AuthenticationException Pour toute autre erreur
     */
    ResetPasswordResponse completePasswordReset(ResetPasswordRequest request) throws AuthenticationException;
    
    /**
     * Change le mot de passe d'un utilisateur authentifié
     * 
     * @param request Requête avec ancien et nouveau mot de passe
     * @return Réponse confirmant le changement
     * @throws InvalidCredentialsException Si l'ancien mot de passe est incorrect
     * @throws AuthenticationException Pour toute autre erreur
     */
    ChangePasswordResponse changePassword(ChangePasswordRequest request) throws AuthenticationException;
    
    /**
     * Vérifie la force d'un mot de passe selon les politiques de sécurité
     * 
     * @param password Mot de passe à vérifier
     * @return Score de force et recommandations (0-100)
     * @throws AuthenticationException Pour toute erreur
     */
    int checkPasswordStrength(String password) throws AuthenticationException;
    
    /**
     * Authentification avec OAuth (Google, Facebook, etc.)
     * 
     * @param provider Fournisseur OAuth (google, facebook, etc.)
     * @param accessToken Token d'accès du fournisseur OAuth
     * @param deviceInfo Informations sur l'appareil de connexion
     * @return Réponse de connexion avec token SenMarket
     * @throws AuthenticationException Pour toute erreur d'authentification OAuth
     */
    LoginResponse authenticateWithOAuth(String provider, String accessToken, String deviceInfo) 
            throws AuthenticationException;
    
    /**
     * Authentification à deux facteurs - Envoi du code
     * 
     * @param userId Identifiant de l'utilisateur
     * @param method Méthode d'envoi (SMS, EMAIL, AUTHENTICATOR)
     * @return Confirmation d'envoi du code
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur
     */
    ValidateTokenResponse sendTwoFactorCode(String userId, String method) throws AuthenticationException;
    
    /**
     * Authentification à deux facteurs - Vérification du code
     * 
     * @param userId Identifiant de l'utilisateur
     * @param code Code de vérification saisi
     * @param deviceTrust Indique si l'appareil doit être marqué comme sûr
     * @return Token d'authentification complet
     * @throws InvalidCredentialsException Si le code est incorrect
     * @throws AuthenticationException Pour toute autre erreur
     */
    LoginResponse verifyTwoFactorCode(String userId, String code, boolean deviceTrust) 
            throws AuthenticationException;
    
    /**
     * Invalide toutes les sessions actives d'un utilisateur
     * Utile en cas de compte compromis
     * 
     * @param userId Identifiant de l'utilisateur
     * @param reason Raison de l'invalidation (optionnel)
     * @return Nombre de sessions invalidées
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur
     */
    int invalidateAllUserSessions(String userId, String reason) throws AuthenticationException;
    
    /**
     * Vérifie si un utilisateur est actuellement connecté
     * 
     * @param userId Identifiant de l'utilisateur
     * @return true si l'utilisateur a une session active
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur
     */
    boolean isUserLoggedIn(String userId) throws AuthenticationException;
    
    /**
     * Récupère les informations de session active
     * 
     * @param sessionToken Token de session
     * @return Informations détaillées de la session
     * @throws SessionExpiredException Si la session est expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    ValidateTokenResponse getSessionInfo(String sessionToken) throws AuthenticationException;
    
    /**
     * Met à jour l'activité de la session (heartbeat)
     * Prolonge la durée de vie de la session
     * 
     * @param sessionToken Token de session
     * @return Nouvelle date d'expiration
     * @throws SessionExpiredException Si la session est expirée
     * @throws AuthenticationException Pour toute autre erreur
     */
    ValidateTokenResponse updateSessionActivity(String sessionToken) throws AuthenticationException;
    
    /**
     * Vérifie les permissions d'un utilisateur pour une ressource
     * 
     * @param userId Identifiant de l'utilisateur
     * @param resource Ressource à vérifier
     * @param action Action demandée (READ, WRITE, DELETE, etc.)
     * @return true si l'utilisateur a la permission
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur
     */
    boolean hasPermission(String userId, String resource, String action) throws AuthenticationException;
    
    /**
     * Active l'authentification à deux facteurs pour un utilisateur
     * 
     * @param userId Identifiant de l'utilisateur
     * @param method Méthode 2FA (SMS, EMAIL, AUTHENTICATOR)
     * @param phoneNumber Numéro de téléphone (si SMS)
     * @return QR code pour l'authenticator ou confirmation
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur
     */
    String enableTwoFactor(String userId, String method, String phoneNumber) throws AuthenticationException;
    
    /**
     * Désactive l'authentification à deux facteurs
     * 
     * @param userId Identifiant de l'utilisateur
     * @param confirmationCode Code de confirmation pour désactiver
     * @return Confirmation de désactivation
     * @throws InvalidCredentialsException Si le code est incorrect
     * @throws AuthenticationException Pour toute autre erreur
     */
    boolean disableTwoFactor(String userId, String confirmationCode) throws AuthenticationException;
    
    /**
     * Récupère l'historique des connexions d'un utilisateur
     * 
     * @param userId Identifiant de l'utilisateur
     * @param limit Nombre maximum de connexions à retourner
     * @return Liste des dernières connexions avec détails
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur
     */
    java.util.List<java.util.Map<String, Object>> getLoginHistory(String userId, int limit) 
            throws AuthenticationException;
    
    /**
     * Marque un appareil comme sûr pour un utilisateur
     * 
     * @param userId Identifiant de l'utilisateur
     * @param deviceFingerprint Empreinte unique de l'appareil
     * @param deviceName Nom convivial de l'appareil
     * @return Confirmation d'ajout de l'appareil sûr
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur
     */
    boolean addTrustedDevice(String userId, String deviceFingerprint, String deviceName) 
            throws AuthenticationException;
    
    /**
     * Supprime un appareil de la liste des appareils sûrs
     * 
     * @param userId Identifiant de l'utilisateur
     * @param deviceFingerprint Empreinte de l'appareil à supprimer
     * @return Confirmation de suppression
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur
     */
    boolean removeTrustedDevice(String userId, String deviceFingerprint) throws AuthenticationException;
    
    /**
     * Vérifie si un appareil est marqué comme sûr
     * 
     * @param userId Identifiant de l'utilisateur
     * @param deviceFingerprint Empreinte de l'appareil
     * @return true si l'appareil est dans la liste des appareils sûrs
     * @throws UserNotFoundException Si l'utilisateur n'existe pas
     * @throws AuthenticationException Pour toute autre erreur
     */
    boolean isTrustedDevice(String userId, String deviceFingerprint) throws AuthenticationException;
}