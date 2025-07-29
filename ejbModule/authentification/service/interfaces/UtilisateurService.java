package authentification.service.interfaces;

import authentification.dto.response.UserResponse;
import authentification.base.dto.PageableResponse;
import model.utilisateur.Personnes;
import model.utilisateur.ComptesUtilisateur;
import java.util.List;
import java.util.Map;

/**
 * Interface du service de gestion des utilisateurs
 * Définit les opérations CRUD et de gestion des utilisateurs
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public interface UtilisateurService {
    
    /**
     * Crée un nouvel utilisateur complet (Personne + Compte)
     * @param personne Entité Personne
     * @param compteUtilisateur Entité CompteUtilisateur
     * @return UserResponse avec les informations créées
     */
    UserResponse createUser(Personnes personne, ComptesUtilisateur compteUtilisateur);
    
    /**
     * Obtient un utilisateur par son ID
     * @param userId ID de l'utilisateur
     * @return UserResponse ou null si non trouvé
     */
    UserResponse getUserById(Integer userId);
    
    /**
     * Obtient un utilisateur par son UUID
     * @param uuid UUID de l'utilisateur
     * @return UserResponse ou null si non trouvé
     */
    UserResponse getUserByUuid(String uuid);
    
    /**
     * Obtient un utilisateur par son email
     * @param email Email de l'utilisateur
     * @return UserResponse ou null si non trouvé
     */
    UserResponse getUserByEmail(String email);
    
    /**
     * Obtient un utilisateur par son téléphone
     * @param telephone Numéro de téléphone
     * @return UserResponse ou null si non trouvé
     */
    UserResponse getUserByTelephone(String telephone);
    
    /**
     * Met à jour les informations personnelles d'un utilisateur
     * @param userId ID de l'utilisateur
     * @param updatedInfo Nouvelles informations
     * @return UserResponse mis à jour
     */
    UserResponse updatePersonalInfo(Integer userId, Map<String, Object> updatedInfo);
    
    /**
     * Met à jour l'adresse d'un utilisateur
     * @param userId ID de l'utilisateur
     * @param adresseInfo Nouvelles informations d'adresse
     * @return true si la mise à jour a réussi
     */
    boolean updateAddress(Integer userId, Map<String, Object> adresseInfo);
    
    /**
     * Met à jour les préférences d'un utilisateur
     * @param userId ID de l'utilisateur
     * @param preferences Nouvelles préférences
     * @return true si la mise à jour a réussi
     */
    boolean updatePreferences(Integer userId, Map<String, Object> preferences);
    
    /**
     * Met à jour la photo de profil d'un utilisateur
     * @param userId ID de l'utilisateur
     * @param photoUrl URL de la nouvelle photo
     * @return true si la mise à jour a réussi
     */
    boolean updateProfilePhoto(Integer userId, String photoUrl);
    
    /**
     * Active ou désactive un utilisateur
     * @param userId ID de l'utilisateur
     * @param actif Nouvel état (actif/inactif)
     * @return true si la mise à jour a réussi
     */
    boolean setUserActive(Integer userId, boolean actif);
    
    /**
     * Marque un utilisateur comme vérifié
     * @param userId ID de l'utilisateur
     * @return true si la vérification a réussi
     */
    boolean markUserAsVerified(Integer userId);
    
    /**
     * Supprime un utilisateur (soft delete)
     * @param userId ID de l'utilisateur
     * @param raison Raison de la suppression
     * @return true si la suppression a réussi
     */
    boolean deleteUser(Integer userId, String raison);
    
    /**
     * Recherche des utilisateurs par nom
     * @param nom Nom ou partie du nom à rechercher
     * @param page Numéro de page (0-based)
     * @param taille Taille de la page
     * @return Résultats paginés
     */
    PageableResponse<UserResponse> searchUsersByName(String nom, int page, int taille);
    
    /**
     * Recherche des utilisateurs par ville
     * @param ville Ville
     * @param page Numéro de page (0-based)
     * @param taille Taille de la page
     * @return Résultats paginés
     */
    PageableResponse<UserResponse> getUsersByVille(String ville, int page, int taille);
    
    /**
     * Obtient tous les utilisateurs avec un profil donné
     * @param codeProf Code du profil
     * @param page Numéro de page (0-based)
     * @param taille Taille de la page
     * @return Résultats paginés
     */
    PageableResponse<UserResponse> getUsersByProfil(String codeProfil, int page, int taille);
    
    /**
     * Obtient tous les utilisateurs actifs
     * @param page Numéro de page (0-based)
     * @param taille Taille de la page
     * @return Résultats paginés
     */
    PageableResponse<UserResponse> getActiveUsers(int page, int taille);
    
    /**
     * Obtient les nouveaux utilisateurs inscrits dans une période
     * @param nombreJours Nombre de jours en arrière
     * @param page Numéro de page (0-based)
     * @param taille Taille de la page
     * @return Résultats paginés
     */
    PageableResponse<UserResponse> getNewUsers(int nombreJours, int page, int taille);
    
    /**
     * Obtient les utilisateurs par statut de vérification
     * @param verifie Statut de vérification (true = vérifiés, false = non vérifiés)
     * @param page Numéro de page (0-based)
     * @param taille Taille de la page
     * @return Résultats paginés
     */
    PageableResponse<UserResponse> getUsersByVerificationStatus(boolean verifie, int page, int taille);
    
    /**
     * Compte le nombre total d'utilisateurs
     * @return Nombre total d'utilisateurs
     */
    long getTotalUsersCount();
    
    /**
     * Compte le nombre d'utilisateurs actifs
     * @return Nombre d'utilisateurs actifs
     */
    long getActiveUsersCount();
    
    /**
     * Compte le nombre de nouveaux utilisateurs sur une période
     * @param nombreJours Nombre de jours en arrière
     * @return Nombre de nouveaux utilisateurs
     */
    long getNewUsersCount(int nombreJours);
    
    /**
     * Obtient les statistiques utilisateur globales
     * @return Map avec les statistiques
     */
    Map<String, Object> getUsersStatistics();
    
    /**
     * Obtient les statistiques d'un utilisateur spécifique
     * @param userId ID de l'utilisateur
     * @return Map avec les statistiques
     */
    Map<String, Object> getUserStatistics(Integer userId);
    
    /**
     * Vérifie si un email existe déjà
     * @param email Email à vérifier
     * @return true si l'email existe
     */
    boolean emailExists(String email);
    
    /**
     * Vérifie si un téléphone existe déjà
     * @param telephone Téléphone à vérifier
     * @return true si le téléphone existe
     */
    boolean telephoneExists(String telephone);
    
    /**
     * Vérifie si un nom d'utilisateur existe déjà
     * @param nomUtilisateur Nom d'utilisateur à vérifier
     * @return true si le nom existe
     */
    boolean nomUtilisateurExists(String nomUtilisateur);
    
    /**
     * Valide les données d'un utilisateur
     * @param userData Données à valider
     * @return Liste des erreurs de validation (vide si valide)
     */
    List<String> validateUserData(Map<String, Object> userData);
    
    /**
     * Fusionne deux comptes utilisateur (en cas de doublons)
     * @param userIdPrincipal ID du compte principal à conserver
     * @param userIdSecondaire ID du compte à fusionner
     * @return true si la fusion a réussi
     */
    boolean mergeUserAccounts(Integer userIdPrincipal, Integer userIdSecondaire);
    
    /**
     * Exporte les données d'un utilisateur (RGPD)
     * @param userId ID de l'utilisateur
     * @return Données exportées au format JSON
     */
    String exportUserData(Integer userId);
    
    /**
     * Anonymise les données d'un utilisateur (RGPD)
     * @param userId ID de l'utilisateur
     * @return true si l'anonymisation a réussi
     */
    boolean anonymizeUserData(Integer userId);
    
    /**
     * Met à jour la dernière activité d'un utilisateur
     * @param userId ID de l'utilisateur
     * @return true si la mise à jour a réussi
     */
    boolean updateLastActivity(Integer userId);
    
    /**
     * Obtient les utilisateurs inactifs depuis X jours
     * @param nombreJours Nombre de jours d'inactivité
     * @param page Numéro de page (0-based)
     * @param taille Taille de la page
     * @return Résultats paginés
     */
    PageableResponse<UserResponse> getInactiveUsers(int nombreJours, int page, int taille);
    
    /**
     * Envoie un email de bienvenue à un utilisateur
     * @param userId ID de l'utilisateur
     * @return true si l'email a été envoyé
     */
    boolean sendWelcomeEmail(Integer userId);
    
    /**
     * Envoie un email de rappel à un utilisateur inactif
     * @param userId ID de l'utilisateur
     * @return true si l'email a été envoyé
     */
    boolean sendInactivityReminder(Integer userId);
    
    /**
     * Génère un rapport d'activité utilisateur
     * @param userId ID de l'utilisateur
     * @param periodeJours Période en jours
     * @return Rapport d'activité
     */
    Map<String, Object> generateUserActivityReport(Integer userId, int periodeJours);
    
    /**
     * Obtient les préférences de notification d'un utilisateur
     * @param userId ID de l'utilisateur
     * @return Préférences de notification
     */
    Map<String, Boolean> getNotificationPreferences(Integer userId);
    
    /**
     * Met à jour les préférences de notification d'un utilisateur
     * @param userId ID de l'utilisateur
     * @param preferences Nouvelles préférences
     * @return true si la mise à jour a réussi
     */
    boolean updateNotificationPreferences(Integer userId, Map<String, Boolean> preferences);
    
    /**
     * Obtient l'historique des modifications d'un utilisateur
     * @param userId ID de l'utilisateur
     * @param page Numéro de page (0-based)
     * @param taille Taille de la page
     * @return Historique paginé
     */
    PageableResponse<Object> getUserModificationHistory(Integer userId, int page, int taille);
    
    /**
     * Vérifie les permissions d'un utilisateur
     * @param userId ID de l'utilisateur
     * @param permission Permission à vérifier
     * @return true si l'utilisateur a la permission
     */
    boolean hasPermission(Integer userId, String permission);
    
    /**
     * Obtient toutes les permissions d'un utilisateur
     * @param userId ID de l'utilisateur
     * @return Liste des permissions
     */
    List<String> getUserPermissions(Integer userId);
    
    /**
     * Met à jour le profil d'un utilisateur
     * @param userId ID de l'utilisateur
     * @param nouveauCodeProfil Code du nouveau profil
     * @return true si la mise à jour a réussi
     */
    boolean updateUserProfile(Integer userId, String nouveauCodeProfil);
    
    /**
     * Verrouille temporairement un utilisateur
     * @param userId ID de l'utilisateur
     * @param dureeMinutes Durée du verrouillage en minutes
     * @param raison Raison du verrouillage
     * @return true si le verrouillage a réussi
     */
    boolean lockUserTemporary(Integer userId, int dureeMinutes, String raison);
    
    /**
     * Déverrouille un utilisateur verrouillé
     * @param userId ID de l'utilisateur
     * @return true si le déverrouillage a réussi
     */
    boolean unlockUser(Integer userId);
    
    /**
     * Vérifie si un utilisateur est verrouillé
     * @param userId ID de l'utilisateur
     * @return true si l'utilisateur est verrouillé
     */
    boolean isUserLocked(Integer userId);
}