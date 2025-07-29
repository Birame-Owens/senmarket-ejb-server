package authentification.dao.interfaces;

import authentification.base.dao.GenericDAO;
import model.utilisateur.ComptesUtilisateur;
import java.sql.Timestamp;
import java.util.List;

/**
 * Interface DAO pour l'entité ComptesUtilisateur
 * Définit les opérations spécifiques à la gestion des comptes utilisateur
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public interface CompteUtilisateurDAO extends GenericDAO<ComptesUtilisateur, Integer> {
    
    /**
     * Trouve un compte par email
     * @param email L'email du compte
     * @return Le compte trouvé ou null
     */
    ComptesUtilisateur findByEmail(String email);
    
    /**
     * Trouve un compte par nom d'utilisateur
     * @param nomUtilisateur Le nom d'utilisateur
     * @return Le compte trouvé ou null
     */
    ComptesUtilisateur findByNomUtilisateur(String nomUtilisateur);
    
    /**
     * Trouve un compte par Google ID
     * @param googleId L'ID Google
     * @return Le compte trouvé ou null
     */
    ComptesUtilisateur findByGoogleId(String googleId);
    
    /**
     * Trouve un compte par personne ID
     * @param personneId L'ID de la personne
     * @return Le compte trouvé ou null
     */
    ComptesUtilisateur findByPersonneId(Integer personneId);
    
    /**
     * Trouve tous les comptes actifs
     * @return Liste des comptes actifs
     */
    List<ComptesUtilisateur> findAllActive();
    
    /**
     * Trouve tous les comptes vérifiés
     * @return Liste des comptes vérifiés
     */
    List<ComptesUtilisateur> findAllVerified();
    
    /**
     * Trouve les comptes créés après une certaine date
     * @param date La date de référence
     * @return Liste des comptes créés après cette date
     */
    List<ComptesUtilisateur> findCreatedAfter(Timestamp date);
    
    /**
     * Vérifie si un email existe déjà
     * @param email L'email à vérifier
     * @return true si l'email existe, false sinon
     */
    boolean emailExists(String email);
    
    /**
     * Vérifie si un nom d'utilisateur existe déjà
     * @param nomUtilisateur Le nom d'utilisateur à vérifier
     * @return true si le nom existe, false sinon
     */
    boolean nomUtilisateurExists(String nomUtilisateur);
    
    /**
     * Met à jour la dernière connexion d'un compte
     * @param compteId L'ID du compte
     * @param adresseIp L'adresse IP de connexion
     * @return Nombre de lignes affectées
     */
    int updateDerniereConnexion(Integer compteId, String adresseIp);
    
    /**
     * Incrémente le nombre de tentatives d'échec
     * @param compteId L'ID du compte
     * @return Nombre de lignes affectées
     */
    int incrementTentativesEchec(Integer compteId);
    
    /**
     * Remet à zéro les tentatives d'échec
     * @param compteId L'ID du compte
     * @return Nombre de lignes affectées
     */
    int resetTentativesEchec(Integer compteId);
    
    /**
     * Verrouille un compte jusqu'à une certaine date
     * @param compteId L'ID du compte
     * @param verrouillageJusqu La date de fin de verrouillage
     * @return Nombre de lignes affectées
     */
    int verrouillerCompte(Integer compteId, Timestamp verrouillageJusqu);
    
    /**
     * Déverrouille un compte
     * @param compteId L'ID du compte
     * @return Nombre de lignes affectées
     */
    int deverrouillerCompte(Integer compteId);
    
    /**
     * Marque un email comme vérifié
     * @param compteId L'ID du compte
     * @return Nombre de lignes affectées
     */
    int marquerEmailVerifie(Integer compteId);
    
    /**
     * Met à jour le code de vérification
     * @param compteId L'ID du compte
     * @param codeVerification Le nouveau code
     * @param dateExpiration Date d'expiration du code
     * @return Nombre de lignes affectées
     */
    int updateCodeVerification(Integer compteId, String codeVerification, Timestamp dateExpiration);
    
    /**
     * Active ou désactive un compte
     * @param compteId L'ID du compte
     * @param estActif Le nouvel état (actif/inactif)
     * @return Nombre de lignes affectées
     */
    int updateStatutActif(Integer compteId, boolean estActif);
    
    /**
     * Trouve les comptes verrouillés
     * @return Liste des comptes actuellement verrouillés
     */
    List<ComptesUtilisateur> findComptesVerrouilles();
    
    /**
     * Compte les nouveaux comptes dans une période
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @return Nombre de nouveaux comptes
     */
    long countNouveauxComptes(Timestamp dateDebut, Timestamp dateFin);
    
    /**
     * Trouve les comptes avec double authentification activée
     * @return Liste des comptes avec 2FA
     */
    List<ComptesUtilisateur> findWithDoubleAuth();
    
    /**
     * Met à jour les tokens OAuth Google
     * @param compteId L'ID du compte
     * @param accessToken Le token d'accès
     * @param refreshToken Le token de rafraîchissement
     * @param expireDate Date d'expiration
     * @return Nombre de lignes affectées
     */
    int updateGoogleTokens(Integer compteId, String accessToken, String refreshToken, Timestamp expireDate);
}