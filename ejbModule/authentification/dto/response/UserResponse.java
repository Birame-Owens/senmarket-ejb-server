package authentification.dto.response;

import authentification.base.dto.BaseResponse;
import model.utilisateur.Personnes;
import model.utilisateur.ComptesUtilisateur;
import model.reference.ProfilsUtilisateur;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * DTO pour les réponses contenant les informations utilisateur
 * Contient les données complètes d'un utilisateur de manière sécurisée
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class UserResponse extends BaseResponse {
    
    private static final long serialVersionUID = 1L;
    
    // Identifiants
    private Integer id;
    private String uuid;
    private String email;
    
    // Informations personnelles
    private String prenom;
    private String nom;
    private String nomComplet;
    private String telephone;
    private Date dateNaissance;
    private String genre;
    private String photoProfilUrl;
    
    // Adresse
    private AdresseInfo adresse;
    
    // Profil et permissions
    private ProfilInfo profil;
    
    // Préférences
    private PreferencesUtilisateur preferences;
    
    // Statut du compte
    private StatutCompte statutCompte;
    
    // Statistiques utilisateur
    private StatistiquesUtilisateur statistiques;
    
    // Paramètres de sécurité
    private ParametresSecurite securite;
    
    // Informations OAuth
    private InfoOAuth oauth;
    
    // Métadonnées
    private Map<String, Object> metadonnees;
    
    /**
     * Classe interne pour les informations d'adresse
     */
    public static class AdresseInfo {
        private String ligne1;
        private String ligne2;
        private String ville;
        private String region;
        private String codePostal;
        private String codePays;
        private String nomPays;
        private String coordonneesGPS;
        
        public AdresseInfo() {}
        
        public AdresseInfo(String ligne1, String ville, String codePays) {
            this.ligne1 = ligne1;
            this.ville = ville;
            this.codePays = codePays;
        }
        
        public boolean isComplete() {
            return ligne1 != null && !ligne1.trim().isEmpty() &&
                   ville != null && !ville.trim().isEmpty();
        }
        
        public String getAdresseComplete() {
            StringBuilder adresse = new StringBuilder();
            
            if (ligne1 != null && !ligne1.trim().isEmpty()) {
                adresse.append(ligne1);
            }
            
            if (ligne2 != null && !ligne2.trim().isEmpty()) {
                if (adresse.length() > 0) adresse.append(", ");
                adresse.append(ligne2);
            }
            
            if (ville != null && !ville.trim().isEmpty()) {
                if (adresse.length() > 0) adresse.append(", ");
                adresse.append(ville);
            }
            
            if (codePostal != null && !codePostal.trim().isEmpty()) {
                if (adresse.length() > 0) adresse.append(" ");
                adresse.append(codePostal);
            }
            
            if (nomPays != null && !nomPays.trim().isEmpty()) {
                if (adresse.length() > 0) adresse.append(", ");
                adresse.append(nomPays);
            }
            
            return adresse.toString();
        }
        
        // Getters et Setters
        public String getLigne1() { return ligne1; }
        public void setLigne1(String ligne1) { this.ligne1 = ligne1; }
        
        public String getLigne2() { return ligne2; }
        public void setLigne2(String ligne2) { this.ligne2 = ligne2; }
        
        public String getVille() { return ville; }
        public void setVille(String ville) { this.ville = ville; }
        
        public String getRegion() { return region; }
        public void setRegion(String region) { this.region = region; }
        
        public String getCodePostal() { return codePostal; }
        public void setCodePostal(String codePostal) { this.codePostal = codePostal; }
        
        public String getCodePays() { return codePays; }
        public void setCodePays(String codePays) { this.codePays = codePays; }
        
        public String getNomPays() { return nomPays; }
        public void setNomPays(String nomPays) { this.nomPays = nomPays; }
        
        public String getCoordonneesGPS() { return coordonneesGPS; }
        public void setCoordonneesGPS(String coordonneesGPS) { this.coordonneesGPS = coordonneesGPS; }
    }
    
    /**
     * Classe interne pour les informations de profil
     */
    public static class ProfilInfo {
        private String code;
        private String nom;
        private String description;
        private Integer niveauAcces;
        private String[] permissions;
        
        public ProfilInfo() {}
        
        public ProfilInfo(String code, String nom, Integer niveauAcces) {
            this.code = code;
            this.nom = nom;
            this.niveauAcces = niveauAcces;
        }
        
        public boolean isAdmin() {
            return niveauAcces != null && niveauAcces >= 4;
        }
        
        public boolean canAccess(int requiredLevel) {
            return niveauAcces != null && niveauAcces >= requiredLevel;
        }
        
        public String getNiveauAccessString() {
            if (niveauAcces == null) return "Inconnu";
            switch (niveauAcces) {
                case 1: return "Basique";
                case 2: return "Intermédiaire";
                case 3: return "Avancé";
                case 4: return "Administrateur";
                default: return "Personnalisé";
            }
        }
        
        // Getters et Setters
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        
        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public Integer getNiveauAcces() { return niveauAcces; }
        public void setNiveauAcces(Integer niveauAcces) { this.niveauAcces = niveauAcces; }
        
        public String[] getPermissions() { return permissions; }
        public void setPermissions(String[] permissions) { this.permissions = permissions; }
    }
    
    /**
     * Classe interne pour les préférences utilisateur
     */
    public static class PreferencesUtilisateur {
        private String langue;
        private String fuseauHoraire;
        private String theme;
        private boolean notificationsEmail;
        private boolean notificationsSMS;
        private boolean notificationsPush;
        private boolean newsletter;
        private Map<String, Object> preferencesPersonnalisees;
        
        public PreferencesUtilisateur() {
            this.preferencesPersonnalisees = new HashMap<>();
            this.langue = "fr";
            this.theme = "light";
            this.notificationsEmail = true;
            this.notificationsSMS = false;
            this.notificationsPush = true;
            this.newsletter = false;
        }
        
        public void addPreferencePersonnalisee(String cle, Object valeur) {
            if (this.preferencesPersonnalisees == null) {
                this.preferencesPersonnalisees = new HashMap<>();
            }
            this.preferencesPersonnalisees.put(cle, valeur);
        }
        
        // Getters et Setters
        public String getLangue() { return langue; }
        public void setLangue(String langue) { this.langue = langue; }
        
        public String getFuseauHoraire() { return fuseauHoraire; }
        public void setFuseauHoraire(String fuseauHoraire) { this.fuseauHoraire = fuseauHoraire; }
        
        public String getTheme() { return theme; }
        public void setTheme(String theme) { this.theme = theme; }
        
        public boolean isNotificationsEmail() { return notificationsEmail; }
        public void setNotificationsEmail(boolean notificationsEmail) { this.notificationsEmail = notificationsEmail; }
        
        public boolean isNotificationsSMS() { return notificationsSMS; }
        public void setNotificationsSMS(boolean notificationsSMS) { this.notificationsSMS = notificationsSMS; }
        
        public boolean isNotificationsPush() { return notificationsPush; }
        public void setNotificationsPush(boolean notificationsPush) { this.notificationsPush = notificationsPush; }
        
        public boolean isNewsletter() { return newsletter; }
        public void setNewsletter(boolean newsletter) { this.newsletter = newsletter; }
        
        public Map<String, Object> getPreferencesPersonnalisees() { return preferencesPersonnalisees; }
        public void setPreferencesPersonnalisees(Map<String, Object> preferencesPersonnalisees) { 
            this.preferencesPersonnalisees = preferencesPersonnalisees; 
        }
    }
    
    /**
     * Classe interne pour le statut du compte
     */
    public static class StatutCompte {
        private boolean estActif;
        private boolean estVerifie;
        private boolean emailVerifie;
        private boolean telephoneVerifie;
        private Timestamp dateInscription;
        private Timestamp derniereConnexion;
        private Timestamp derniereMiseAJour;
        private String statutDescription;
        
        public StatutCompte() {}
        
        public String getStatutGlobal() {
            if (!estActif) return "Inactif";
            if (!estVerifie) return "Non vérifié";
            if (!emailVerifie) return "Email non vérifié";
            return "Actif";
        }
        
        public boolean isCompletementVerifie() {
            return estActif && estVerifie && emailVerifie;
        }
        
        // Getters et Setters
        public boolean isEstActif() { return estActif; }
        public void setEstActif(boolean estActif) { this.estActif = estActif; }
        
        public boolean isEstVerifie() { return estVerifie; }
        public void setEstVerifie(boolean estVerifie) { this.estVerifie = estVerifie; }
        
        public boolean isEmailVerifie() { return emailVerifie; }
        public void setEmailVerifie(boolean emailVerifie) { this.emailVerifie = emailVerifie; }
        
        public boolean isTelephoneVerifie() { return telephoneVerifie; }
        public void setTelephoneVerifie(boolean telephoneVerifie) { this.telephoneVerifie = telephoneVerifie; }
        
        public Timestamp getDateInscription() { return dateInscription; }
        public void setDateInscription(Timestamp dateInscription) { this.dateInscription = dateInscription; }
        
        public Timestamp getDerniereConnexion() { return derniereConnexion; }
        public void setDerniereConnexion(Timestamp derniereConnexion) { this.derniereConnexion = derniereConnexion; }
        
        public Timestamp getDerniereMiseAJour() { return derniereMiseAJour; }
        public void setDerniereMiseAJour(Timestamp derniereMiseAJour) { this.derniereMiseAJour = derniereMiseAJour; }
        
        public String getStatutDescription() { return statutDescription; }
        public void setStatutDescription(String statutDescription) { this.statutDescription = statutDescription; }
    }
    
    /**
     * Classe interne pour les statistiques utilisateur
     */
    public static class StatistiquesUtilisateur {
        private int nombreConnexions;
        private int nombreCommandes;
        private Long dureeSessionMoyenne; // en minutes
        private Timestamp premiereConnexion;
        private String appareilPrincipal;
        private String navigateurPrincipal;
        
        public StatistiquesUtilisateur() {}
        
        // Getters et Setters
        public int getNombreConnexions() { return nombreConnexions; }
        public void setNombreConnexions(int nombreConnexions) { this.nombreConnexions = nombreConnexions; }
        
        public int getNombreCommandes() { return nombreCommandes; }
        public void setNombreCommandes(int nombreCommandes) { this.nombreCommandes = nombreCommandes; }
        
        public Long getDureeSessionMoyenne() { return dureeSessionMoyenne; }
        public void setDureeSessionMoyenne(Long dureeSessionMoyenne) { this.dureeSessionMoyenne = dureeSessionMoyenne; }
        
        public Timestamp getPremiereConnexion() { return premiereConnexion; }
        public void setPremiereConnexion(Timestamp premiereConnexion) { this.premiereConnexion = premiereConnexion; }
        
        public String getAppareilPrincipal() { return appareilPrincipal; }
        public void setAppareilPrincipal(String appareilPrincipal) { this.appareilPrincipal = appareilPrincipal; }
        
        public String getNavigateurPrincipal() { return navigateurPrincipal; }
        public void setNavigateurPrincipal(String navigateurPrincipal) { this.navigateurPrincipal = navigateurPrincipal; }
    }
    
    /**
     * Classe interne pour les paramètres de sécurité
     */
    public static class ParametresSecurite {
        private boolean doubleAuthentificationActive;
        private boolean motDePasseExpire;
        private Timestamp prochainChangementMotDePasse;
        private int nombreSessionsActives;
        private List<String> appareilsAutorises;
        
        public ParametresSecurite() {}
        
        // Getters et Setters
        public boolean isDoubleAuthentificationActive() { return doubleAuthentificationActive; }
        public void setDoubleAuthentificationActive(boolean doubleAuthentificationActive) { 
            this.doubleAuthentificationActive = doubleAuthentificationActive; 
        }
        
        public boolean isMotDePasseExpire() { return motDePasseExpire; }
        public void setMotDePasseExpire(boolean motDePasseExpire) { this.motDePasseExpire = motDePasseExpire; }
        
        public Timestamp getProchainChangementMotDePasse() { return prochainChangementMotDePasse; }
        public void setProchainChangementMotDePasse(Timestamp prochainChangementMotDePasse) { 
            this.prochainChangementMotDePasse = prochainChangementMotDePasse; 
        }
        
        public int getNombreSessionsActives() { return nombreSessionsActives; }
        public void setNombreSessionsActives(int nombreSessionsActives) { this.nombreSessionsActives = nombreSessionsActives; }
        
        public List<String> getAppareilsAutorises() { return appareilsAutorises; }
        public void setAppareilsAutorises(List<String> appareilsAutorises) { this.appareilsAutorises = appareilsAutorises; }
    }
    
    /**
     * Classe interne pour les informations OAuth
     */
    public static class InfoOAuth {
        private boolean googleConnecte;
        private boolean facebookConnecte;
        private boolean appleConnecte;
        private Timestamp derniereConnexionOAuth;
        private String typeOAuthPrincipal;
        
        public InfoOAuth() {}
        
        public boolean hasAnyOAuthConnection() {
            return googleConnecte || facebookConnecte || appleConnecte;
        }
        
        // Getters et Setters
        public boolean isGoogleConnecte() { return googleConnecte; }
        public void setGoogleConnecte(boolean googleConnecte) { this.googleConnecte = googleConnecte; }
        
        public boolean isFacebookConnecte() { return facebookConnecte; }
        public void setFacebookConnecte(boolean facebookConnecte) { this.facebookConnecte = facebookConnecte; }
        
        public boolean isAppleConnecte() { return appleConnecte; }
        public void setAppleConnecte(boolean appleConnecte) { this.appleConnecte = appleConnecte; }
        
        public Timestamp getDerniereConnexionOAuth() { return derniereConnexionOAuth; }
        public void setDerniereConnexionOAuth(Timestamp derniereConnexionOAuth) { 
            this.derniereConnexionOAuth = derniereConnexionOAuth; 
        }
        
        public String getTypeOAuthPrincipal() { return typeOAuthPrincipal; }
        public void setTypeOAuthPrincipal(String typeOAuthPrincipal) { this.typeOAuthPrincipal = typeOAuthPrincipal; }
    }
    
    // Constructeurs
    public UserResponse() {
        super(true, "Informations utilisateur récupérées");
        this.adresse = new AdresseInfo();
        this.profil = new ProfilInfo();
        this.preferences = new PreferencesUtilisateur();
        this.statutCompte = new StatutCompte();
        this.statistiques = new StatistiquesUtilisateur();
        this.securite = new ParametresSecurite();
        this.oauth = new InfoOAuth();
        this.metadonnees = new HashMap<>();
    }
    
    public UserResponse(Integer id, String email, String prenom, String nom) {
        this();
        this.id = id;
        this.email = email;
        this.prenom = prenom;
        this.nom = nom;
        this.nomComplet = prenom + " " + nom;
    }
    
    /**
     * Méthodes statiques pour créer des UserResponse depuis les entités
     */
    
    /**
     * Crée un UserResponse à partir d'une entité Personnes
     */
    public static UserResponse fromPersonnes(Personnes personne) {
        if (personne == null) return null;
        
        UserResponse user = new UserResponse();
        
        // Informations de base
        user.setId(personne.getId());
        user.setUuid(personne.getUuid() != null ? personne.getUuid().toString() : null);
        user.setPrenom(personne.getPrenom());
        user.setNom(personne.getNom());
        user.setNomComplet(personne.getNomComplet());
        user.setEmail(personne.getEmail());
        user.setTelephone(personne.getTelephone());
        user.setDateNaissance(personne.getDateNaissance());
        user.setGenre(personne.getGenre());
        user.setPhotoProfilUrl(personne.getPhotoProfilUrl());
        
        // Adresse
        if (personne.getAdresseLigne1() != null || personne.getVille() != null) {
            AdresseInfo adresse = new AdresseInfo();
            adresse.setLigne1(personne.getAdresseLigne1());
            adresse.setLigne2(personne.getAdresseLigne2());
            adresse.setVille(personne.getVille());
            adresse.setRegion(personne.getRegion());
            adresse.setCodePostal(personne.getCodePostal());
            adresse.setCoordonneesGPS(personne.getCoordonneesGps() != null ? 
                                    personne.getCoordonneesGps().toString() : null);
            user.setAdresse(adresse);
        }
        
        // Profil
        if (personne.getProfilsUtilisateur() != null) {
            ProfilInfo profil = new ProfilInfo();
            profil.setCode(personne.getProfilsUtilisateur().getCode());
            profil.setNom(personne.getProfilsUtilisateur().getNom());
            profil.setDescription(personne.getProfilsUtilisateur().getDescription());
            profil.setNiveauAcces(personne.getProfilsUtilisateur().getNiveauAcces());
            user.setProfil(profil);
        }
        
        // Préférences
        PreferencesUtilisateur prefs = new PreferencesUtilisateur();
        prefs.setLangue(personne.getLanguePreferee());
        prefs.setFuseauHoraire(personne.getFuseauHoraire());
        user.setPreferences(prefs);
        
        // Statut
        StatutCompte statut = new StatutCompte();
        statut.setEstActif(personne.getEstActif());
        statut.setDateInscription(personne.getCreeLe());
        statut.setDerniereMiseAJour(personne.getModifieLe());
        user.setStatutCompte(statut);
        
        return user;
    }
    
    /**
     * Enrichit avec les informations du compte utilisateur
     */
    public void enrichWithCompteUtilisateur(ComptesUtilisateur compte) {
        if (compte == null) return;
        
        // Statut du compte
        this.statutCompte.setEstVerifie(compte.getEstVerifie());
        this.statutCompte.setEmailVerifie(compte.getEmailVerifieLe() != null);
        this.statutCompte.setTelephoneVerifie(compte.getTelephoneVerifieLe() != null);
        this.statutCompte.setDerniereConnexion(compte.getDerniereConnexionLe());
        
        // Sécurité
        this.securite.setDoubleAuthentificationActive(compte.getDoubleAuthentificationActive());
        this.securite.setMotDePasseExpire(compte.getMotDePasseExpireLe() != null && 
                                        compte.getMotDePasseExpireLe().before(new Timestamp(System.currentTimeMillis())));
        
        // OAuth
        this.oauth.setGoogleConnecte(compte.getGoogleId() != null);
        this.oauth.setFacebookConnecte(compte.getFacebookId() != null);
        this.oauth.setAppleConnecte(compte.getAppleId() != null);
        
        // Notifications
        this.preferences.setNotificationsEmail(compte.getNotificationsEmail());
        this.preferences.setNotificationsSMS(compte.getNotificationsSms());
        this.preferences.setNotificationsPush(compte.getNotificationsPush());
    }
    
    /**
     * Méthodes utilitaires
     */
    
    /**
     * Calcule un score de complétude du profil (0-100)
     */
    public int getProfileCompletionScore() {
        int score = 0;
        int totalFields = 12;
        
        // Champs obligatoires (40 points)
        if (prenom != null && !prenom.trim().isEmpty()) score++;
        if (nom != null && !nom.trim().isEmpty()) score++;
        if (email != null && !email.trim().isEmpty()) score++;
        if (statutCompte.isEmailVerifie()) score++;
        
        // Champs recommandés (40 points)
        if (telephone != null && !telephone.trim().isEmpty()) score++;
        if (dateNaissance != null) score++;
        if (genre != null && !genre.trim().isEmpty()) score++;
        if (photoProfilUrl != null && !photoProfilUrl.trim().isEmpty()) score++;
        
        // Champs optionnels (20 points)
        if (adresse != null && adresse.isComplete()) score++;
        if (preferences.getFuseauHoraire() != null) score++;
        if (statutCompte.isTelephoneVerifie()) score++;
        if (securite.isDoubleAuthentificationActive()) score++;
        
        return (score * 100) / totalFields;
    }
    
    /**
     * Retourne les initiales de l'utilisateur
     */
    public String getInitiales() {
        StringBuilder initiales = new StringBuilder();
        
        if (prenom != null && !prenom.isEmpty()) {
            initiales.append(prenom.charAt(0));
        }
        
        if (nom != null && !nom.isEmpty()) {
            initiales.append(nom.charAt(0));
        }
        
        return initiales.toString().toUpperCase();
    }
    
    /**
     * Vérifie si l'utilisateur peut accéder à un niveau spécifique
     */
    public boolean canAccess(int requiredLevel) {
        return profil != null && profil.canAccess(requiredLevel);
    }
    
    /**
     * Vérifie si l'utilisateur est un administrateur
     */
    public boolean isAdmin() {
        return profil != null && profil.isAdmin();
    }
    
    /**
     * Nettoie les données sensibles pour les logs
     */
    public UserResponse sanitizeForLogging() {
        UserResponse sanitized = new UserResponse();
        
        // Copie les données non sensibles
        sanitized.setId(this.id);
        sanitized.setEmail(this.email);
        sanitized.setPrenom(this.prenom);
        sanitized.setNom(this.nom);
        sanitized.setGenre(this.genre);
        
        // Masque le téléphone
        if (this.telephone != null && this.telephone.length() > 4) {
            String maskedPhone = "***" + this.telephone.substring(this.telephone.length() - 4);
            sanitized.setTelephone(maskedPhone);
        }
        
        // Copie les informations non sensibles
        sanitized.setProfil(this.profil);
        sanitized.setStatutCompte(this.statutCompte);
        sanitized.setStatistiques(this.statistiques);
        
        return sanitized;
    }
    
    // Getters et Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUuid() {
        return uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPrenom() {
        return prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String getNom() {
        return nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public String getNomComplet() {
        return nomComplet;
    }
    
    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }
    
    public String getTelephone() {
        return telephone;
    }
    
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    public Date getDateNaissance() {
        return dateNaissance;
    }
    
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public String getPhotoProfilUrl() {
        return photoProfilUrl;
    }
    
    public void setPhotoProfilUrl(String photoProfilUrl) {
        this.photoProfilUrl = photoProfilUrl;
    }
    
    public AdresseInfo getAdresse() {
        return adresse;
    }
    
    public void setAdresse(AdresseInfo adresse) {
        this.adresse = adresse;
    }
    
    public ProfilInfo getProfil() {
        return profil;
    }
    
    public void setProfil(ProfilInfo profil) {
        this.profil = profil;
    }
    
    public PreferencesUtilisateur getPreferences() {
        return preferences;
    }
    
    public void setPreferences(PreferencesUtilisateur preferences) {
        this.preferences = preferences;
    }
    
    public StatutCompte getStatutCompte() {
        return statutCompte;
    }
    
    public void setStatutCompte(StatutCompte statutCompte) {
        this.statutCompte = statutCompte;
    }
    
    public StatistiquesUtilisateur getStatistiques() {
        return statistiques;
    }
    
    public void setStatistiques(StatistiquesUtilisateur statistiques) {
        this.statistiques = statistiques;
    }
    
    public ParametresSecurite getSecurite() {
        return securite;
    }
    
    public void setSecurite(ParametresSecurite securite) {
        this.securite = securite;
    }
    
    public InfoOAuth getOauth() {
        return oauth;
    }
    
    public void setOauth(InfoOAuth oauth) {
        this.oauth = oauth;
    }
    
    public Map<String, Object> getMetadonnees() {
        return metadonnees;
    }
    
    public void setMetadonnees(Map<String, Object> metadonnees) {
        this.metadonnees = metadonnees;
    }
    
    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nomComplet='" + nomComplet + '\'' +
                ", profil=" + (profil != null ? profil.getCode() : "null") +
                ", estActif=" + (statutCompte != null ? statutCompte.isEstActif() : "null") +
                ", estVerifie=" + (statutCompte != null ? statutCompte.isEstVerifie() : "null") +
                ", completionScore=" + getProfileCompletionScore() + "%" +
                ", isAdmin=" + isAdmin() +
                '}';
    }
}