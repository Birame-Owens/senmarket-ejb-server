package authentification.service.interfaces;

import authentification.dto.request.LoginRequest;
import authentification.dto.request.LogoutRequest;
import authentification.dto.request.RegisterRequest;
import authentification.dto.response.LoginResponse;
import authentification.dto.response.UserResponse;
import authentification.dto.response.SessionResponse;
import authentification.base.dto.ErrorResponse;
import java.util.List;

/**
 * Interface du service d'authentification
 * Définit les opérations d'authentification, d'inscription et de gestion des sessions
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public interface AuthenticationService {
    
    /**
     * Authentifie un utilisateur avec ses identifiants
     * @param loginRequest Les données de connexion
     * @return Réponse de connexion avec token et informations utilisateur
     */
    LoginResponse login(LoginRequest loginRequest);
    
    /**
     * Déconnecte un utilisateur en invalidant sa session
     * @param logoutRequest Les données de déconnexion
     * @return true si la déconnexion a réussi, false sinon
     */
    boolean logout(LogoutRequest logoutRequest);
    
    /**
     * Inscrit un nouvel utilisateur
     * @param registerRequest Les données d'inscription
     * @return Réponse avec les informations du nouvel utilisateur
     */
    UserResponse register(RegisterRequest registerRequest);
    
    /**
     * Valide un token de session
     * @param tokenSession Le token à valider
     * @return true si le token est valide et actif
     */
    boolean validateToken(String tokenSession);
    
    /**
     * Obtient les informations utilisateur à partir d'un token
     * @param tokenSession Le token de session
     * @return Informations utilisateur ou null si token invalide
     */
    UserResponse getUserFromToken(String tokenSession);
    
    /**
     * Obtient les informations de session à partir d'un token
     * @param tokenSession Le token de session
     * @return Informations de session ou null si token invalide
     */
    SessionResponse getSessionFromToken(String tokenSession);
    
    /**
     * Rafraîchit un token de session expiré
     * @param tokenRefresh Le token de rafraîchissement
     * @return Nouveau token de session ou null si échec
     */
    String refreshToken(String tokenRefresh);
    
    /**
     * Change le mot de passe d'un utilisateur
     * @param tokenSession Token de session valide
     * @param ancienMotDePasse Ancien mot de passe
     * @param nouveauMotDePasse Nouveau mot de passe
     * @return true si le changement a réussi
     */
    boolean changePassword(String tokenSession, String ancienMotDePasse, String nouveauMotDePasse);
    
    /**
     * Initie une réinitialisation de mot de passe
     * @param email Email de l'utilisateur
     * @return true si l'email de réinitialisation a été envoyé
     */
    boolean initiatePasswordReset(String email);
    
    /**
     * Confirme une réinitialisation de mot de passe
     * @param email Email de l'utilisateur
     * @param codeReset Code de réinitialisation
     * @param nouveauMotDePasse Nouveau mot de passe
     * @return true si la réinitialisation a réussi
     */
    boolean confirmPasswordReset(String email, String codeReset, String nouveauMotDePasse);
    
    /**
     * Vérifie l'email d'un utilisateur
     * @param email Email à vérifier
     * @param codeVerification Code de vérification
     * @return true si la vérification a réussi
     */
    boolean verifyEmail(String email, String codeVerification);
    
    /**
     * Renvoie un code de vérification d'email
     * @param email Email de l'utilisateur
     * @return true si le code a été renvoyé
     */
    boolean resendEmailVerification(String email);
    
    /**
     * Active l'authentification à deux facteurs
     * @param tokenSession Token de session valide
     * @return Code secret pour configurer l'authentificateur
     */
    String enableTwoFactorAuth(String tokenSession);
    
    /**
     * Confirme l'activation de l'authentification à deux facteurs
     * @param tokenSession Token de session valide
     * @param codeVerification Code de vérification 2FA
     * @return true si l'activation a réussi
     */
    boolean confirmTwoFactorAuth(String tokenSession, String codeVerification);
    
    /**
     * Désactive l'authentification à deux facteurs
     * @param tokenSession Token de session valide
     * @param codeVerification Code de vérification 2FA
     * @return true si la désactivation a réussi
     */
    boolean disableTwoFactorAuth(String tokenSession, String codeVerification);
    
    /**
     * Vérifie un code d'authentification à deux facteurs
     * @param email Email de l'utilisateur
     * @param codeVerification Code 2FA
     * @return LoginResponse avec token si succès
     */
    LoginResponse verifyTwoFactorCode(String email, String codeVerification);
    
    /**
     * Lie un compte OAuth à un utilisateur existant
     * @param tokenSession Token de session valide
     * @param typeOAuth Type d'OAuth (google, facebook, apple)
     * @param tokenOAuth Token OAuth externe
     * @return true si la liaison a réussi
     */
    boolean linkOAuthAccount(String tokenSession, String typeOAuth, String tokenOAuth);
    
    /**
     * Délie un compte OAuth d'un utilisateur
     * @param tokenSession Token de session valide
     * @param typeOAuth Type d'OAuth à délier
     * @return true si la déliaison a réussi
     */
    boolean unlinkOAuthAccount(String tokenSession, String typeOAuth);
    
    /**
     * Authentifie un utilisateur via OAuth
     * @param typeOAuth Type d'OAuth (google, facebook, apple)
     * @param tokenOAuth Token OAuth externe
     * @param userInfo Informations utilisateur de l'OAuth
     * @return LoginResponse avec token si succès
     */
    LoginResponse authenticateWithOAuth(String typeOAuth, String tokenOAuth, Object userInfo);
    
    /**
     * Obtient toutes les sessions actives d'un utilisateur
     * @param tokenSession Token de session valide
     * @return Liste des sessions actives
     */
    List<SessionResponse> getActiveSessions(String tokenSession);
    
    /**
     * Termine une session spécifique
     * @param tokenSession Token de session de l'utilisateur
     * @param sessionIdToTerminate ID de la session à terminer
     * @return true si la session a été terminée
     */
    boolean terminateSession(String tokenSession, Integer sessionIdToTerminate);
    
    /**
     * Termine toutes les sessions d'un utilisateur sauf la session courante
     * @param tokenSession Token de session valide (sera préservé)
     * @return Nombre de sessions terminées
     */
    int terminateAllOtherSessions(String tokenSession);
    
    /**
     * Nettoie les sessions expirées du système
     * @return Nombre de sessions supprimées
     */
    int cleanExpiredSessions();
    
    /**
     * Vérifie si un email est disponible pour inscription
     * @param email Email à vérifier
     * @return true si l'email est disponible
     */
    boolean isEmailAvailable(String email);
    
    /**
     * Vérifie si un nom d'utilisateur est disponible
     * @param nomUtilisateur Nom d'utilisateur à vérifier
     * @return true si le nom est disponible
     */
    boolean isUsernameAvailable(String nomUtilisateur);
    
    /**
     * Verrouille un compte utilisateur
     * @param email Email de l'utilisateur
     * @param raisonVerrouillage Raison du verrouillage
     * @param dureeVerrouillage Durée en minutes (null = permanent)
     * @return true si le verrouillage a réussi
     */
    boolean lockAccount(String email, String raisonVerrouillage, Integer dureeVerrouillage);
    
    /**
     * Déverrouille un compte utilisateur
     * @param email Email de l'utilisateur
     * @return true si le déverrouillage a réussi
     */
    boolean unlockAccount(String email);
    
    /**
     * Vérifie si un compte est verrouillé
     * @param email Email de l'utilisateur
     * @return true si le compte est verrouillé
     */
    boolean isAccountLocked(String email);
    
    /**
     * Met à jour la dernière activité d'une session
     * @param tokenSession Token de session
     * @return true si la mise à jour a réussi
     */
    boolean updateSessionActivity(String tokenSession);
    
    /**
     * Détecte les connexions suspectes
     * @param email Email de l'utilisateur
     * @param adresseIP Adresse IP de connexion
     * @param userAgent User agent du navigateur
     * @return true si la connexion est suspecte
     */
    boolean isSuspiciousLogin(String email, String adresseIP, String userAgent);
    
    /**
     * Notifie une connexion suspecte
     * @param email Email de l'utilisateur
     * @param detailsConnexion Détails de la connexion suspecte
     * @return true si la notification a été envoyée
     */
    boolean notifySuspiciousLogin(String email, Object detailsConnexion);
    
    /**
     * Valide la force d'un mot de passe
     * @param motDePasse Mot de passe à valider
     * @return Score de force (0-100) et suggestions d'amélioration
     */
    Object validatePasswordStrength(String motDePasse);
    
    /**
     * Génère des codes de récupération pour l'authentification 2FA
     * @param tokenSession Token de session valide
     * @return Liste des codes de récupération
     */
    List<String> generateRecoveryCodes(String tokenSession);
    
    /**
     * Utilise un code de récupération pour l'authentification 2FA
     * @param email Email de l'utilisateur
     * @param codeRecuperation Code de récupération
     * @return LoginResponse avec token si succès
     */
    LoginResponse useRecoveryCode(String email, String codeRecuperation);
    
    /**
     * Obtient les statistiques d'authentification pour un utilisateur
     * @param tokenSession Token de session valide
     * @return Statistiques d'authentification
     */
    Object getAuthenticationStats(String tokenSession);
    
    /**
     * Archive une session terminée (pour audit)
     * @param sessionId ID de la session à archiver
     * @return true si l'archivage a réussi
     */
    boolean archiveSession(Integer sessionId);
}