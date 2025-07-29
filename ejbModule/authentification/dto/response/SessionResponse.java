package authentification.dto.response;

import authentification.base.dto.BaseResponse;
import model.utilisateur.SessionsActives;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * DTO pour les réponses contenant les informations de session
 * Contient les données d'une session utilisateur de manière sécurisée
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class SessionResponse extends BaseResponse {
    
    private static final long serialVersionUID = 1L;
    
    // Identifiants de session
    private Integer sessionId;
    private String tokenSession;
    private String tokenRefresh;
    
    // Informations temporelles
    private Timestamp dateCreation;
    private Timestamp dateExpiration;
    private Timestamp derniereActivite;
    private Long dureeSession; // en minutes
    private Long tempsRestant; // en minutes
    
    // Informations d'appareil
    private InfoAppareil appareil;
    
    // Informations de localisation
    private InfoLocalisation localisation;
    
    // Statut et sécurité
    private StatutSession statut;
    
    // Statistiques de session
    private StatistiquesSession statistiques;
    
    // Métadonnées
    private Map<String, Object> metadonnees;
    
    /**
     * Classe interne pour les informations d'appareil
     */
    public static class InfoAppareil {
        private String userAgent;
        private String empreinteAppareil;
        private String nomAppareil;
        private String typeAppareil; // mobile, desktop, tablet
        private String navigateur;
        private String versionNavigateur;
        private String systemeExploitation;
        private String versionOS;
        private String resolutionEcran;
        private boolean estAppareilConnu;
        
        public InfoAppareil() {}
        
        public InfoAppareil(String userAgent, String typeAppareil, String navigateur) {
            this.userAgent = userAgent;
            this.typeAppareil = typeAppareil;
            this.navigateur = navigateur;
            this.estAppareilConnu = false;
        }
        
        /**
         * Retourne une description lisible de l'appareil
         */
        public String getDescriptionAppareil() {
            StringBuilder description = new StringBuilder();
            
            if (nomAppareil != null && !nomAppareil.trim().isEmpty()) {
                description.append(nomAppareil);
            } else {
                if (navigateur != null && !navigateur.trim().isEmpty()) {
                    description.append(navigateur);
                    if (versionNavigateur != null) {
                        description.append(" ").append(versionNavigateur);
                    }
                }
                
                if (systemeExploitation != null && !systemeExploitation.trim().isEmpty()) {
                    if (description.length() > 0) {
                        description.append(" sur ");
                    }
                    description.append(systemeExploitation);
                    if (versionOS != null) {
                        description.append(" ").append(versionOS);
                    }
                }
                
                if (typeAppareil != null && !typeAppareil.trim().isEmpty()) {
                    if (description.length() > 0) {
                        description.append(" (").append(typeAppareil).append(")");
                    } else {
                        description.append(typeAppareil);
                    }
                }
            }
            
            return description.toString();
        }
        
        /**
         * Vérifie si c'est un appareil mobile
         */
        public boolean isMobile() {
            return "mobile".equalsIgnoreCase(typeAppareil);
        }
        
        /**
         * Vérifie si c'est un appareil de bureau
         */
        public boolean isDesktop() {
            return "desktop".equalsIgnoreCase(typeAppareil);
        }
        
        // Getters et Setters
        public String getUserAgent() { return userAgent; }
        public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
        
        public String getEmpreinteAppareil() { return empreinteAppareil; }
        public void setEmpreinteAppareil(String empreinteAppareil) { this.empreinteAppareil = empreinteAppareil; }
        
        public String getNomAppareil() { return nomAppareil; }
        public void setNomAppareil(String nomAppareil) { this.nomAppareil = nomAppareil; }
        
        public String getTypeAppareil() { return typeAppareil; }
        public void setTypeAppareil(String typeAppareil) { this.typeAppareil = typeAppareil; }
        
        public String getNavigateur() { return navigateur; }
        public void setNavigateur(String navigateur) { this.navigateur = navigateur; }
        
        public String getVersionNavigateur() { return versionNavigateur; }
        public void setVersionNavigateur(String versionNavigateur) { this.versionNavigateur = versionNavigateur; }
        
        public String getSystemeExploitation() { return systemeExploitation; }
        public void setSystemeExploitation(String systemeExploitation) { this.systemeExploitation = systemeExploitation; }
        
        public String getVersionOS() { return versionOS; }
        public void setVersionOS(String versionOS) { this.versionOS = versionOS; }
        
        public String getResolutionEcran() { return resolutionEcran; }
        public void setResolutionEcran(String resolutionEcran) { this.resolutionEcran = resolutionEcran; }
        
        public boolean isEstAppareilConnu() { return estAppareilConnu; }
        public void setEstAppareilConnu(boolean estAppareilConnu) { this.estAppareilConnu = estAppareilConnu; }
    }
    
    /**
     * Classe interne pour les informations de localisation
     */
    public static class InfoLocalisation {
        private String adresseIP;
        private String pays;
        private String codePays;
        private String ville;
        private String region;
        private String codePostal;
        private String coordonneesGPS;
        private String fuseauHoraire;
        private boolean localisationApproximative;
        
        public InfoLocalisation() {}
        
        public InfoLocalisation(String adresseIP, String pays, String ville) {
            this.adresseIP = adresseIP;
            this.pays = pays;
            this.ville = ville;
            this.localisationApproximative = true;
        }
        
        /**
         * Retourne la localisation complète
         */
        public String getLocalisationComplete() {
            StringBuilder localisation = new StringBuilder();
            
            if (ville != null && !ville.trim().isEmpty()) {
                localisation.append(ville);
            }
            
            if (region != null && !region.trim().isEmpty()) {
                if (localisation.length() > 0) {
                    localisation.append(", ");
                }
                localisation.append(region);
            }
            
            if (pays != null && !pays.trim().isEmpty()) {
                if (localisation.length() > 0) {
                    localisation.append(", ");
                }
                localisation.append(pays);
            }
            
            return localisation.toString();
        }
        
        /**
         * Masque l'adresse IP pour la sécurité
         */
        public String getAdresseIPMasquee() {
            if (adresseIP == null || adresseIP.trim().isEmpty()) {
                return null;
            }
            
            String[] parts = adresseIP.split("\\.");
            if (parts.length == 4) {
                return parts[0] + "." + parts[1] + ".***." + parts[3];
            }
            
            // Pour IPv6 ou autres formats
            if (adresseIP.length() > 8) {
                return adresseIP.substring(0, 4) + "***" + 
                       adresseIP.substring(adresseIP.length() - 4);
            }
            
            return "***";
        }
        
        // Getters et Setters
        public String getAdresseIP() { return adresseIP; }
        public void setAdresseIP(String adresseIP) { this.adresseIP = adresseIP; }
        
        public String getPays() { return pays; }
        public void setPays(String pays) { this.pays = pays; }
        
        public String getCodePays() { return codePays; }
        public void setCodePays(String codePays) { this.codePays = codePays; }
        
        public String getVille() { return ville; }
        public void setVille(String ville) { this.ville = ville; }
        
        public String getRegion() { return region; }
        public void setRegion(String region) { this.region = region; }
        
        public String getCodePostal() { return codePostal; }
        public void setCodePostal(String codePostal) { this.codePostal = codePostal; }
        
        public String getCoordonneesGPS() { return coordonneesGPS; }
        public void setCoordonneesGPS(String coordonneesGPS) { this.coordonneesGPS = coordonneesGPS; }
        
        public String getFuseauHoraire() { return fuseauHoraire; }
        public void setFuseauHoraire(String fuseauHoraire) { this.fuseauHoraire = fuseauHoraire; }
        
        public boolean isLocalisationApproximative() { return localisationApproximative; }
        public void setLocalisationApproximative(boolean localisationApproximative) { 
            this.localisationApproximative = localisationApproximative; 
        }
    }
    
    /**
     * Classe interne pour le statut de session
     */
    public static class StatutSession {
        private boolean estActive;
        private boolean estExpiree;
        private boolean estSuspecte;
        private boolean peutEtreRafraichie;
        private String raisonSuspicion;
        private NiveauRisque niveauRisque;
        
        public enum NiveauRisque {
            FAIBLE, MOYEN, ELEVE, CRITIQUE
        }
        
        public StatutSession() {
            this.estActive = true;
            this.estExpiree = false;
            this.estSuspecte = false;
            this.peutEtreRafraichie = true;
            this.niveauRisque = NiveauRisque.FAIBLE;
        }
        
        /**
         * Détermine le statut global de la session
         */
        public String getStatutGlobal() {
            if (estExpiree) return "Expirée";
            if (estSuspecte) return "Suspecte";
            if (!estActive) return "Inactive";
            return "Active";
        }
        
        /**
         * Vérifie si la session est valide pour utilisation
         */
        public boolean isValide() {
            return estActive && !estExpiree && !estSuspecte;
        }
        
        // Getters et Setters
        public boolean isEstActive() { return estActive; }
        public void setEstActive(boolean estActive) { this.estActive = estActive; }
        
        public boolean isEstExpiree() { return estExpiree; }
        public void setEstExpiree(boolean estExpiree) { this.estExpiree = estExpiree; }
        
        public boolean isEstSuspecte() { return estSuspecte; }
        public void setEstSuspecte(boolean estSuspecte) { this.estSuspecte = estSuspecte; }
        
        public boolean isPeutEtreRafraichie() { return peutEtreRafraichie; }
        public void setPeutEtreRafraichie(boolean peutEtreRafraichie) { this.peutEtreRafraichie = peutEtreRafraichie; }
        
        public String getRaisonSuspicion() { return raisonSuspicion; }
        public void setRaisonSuspicion(String raisonSuspicion) { this.raisonSuspicion = raisonSuspicion; }
        
        public NiveauRisque getNiveauRisque() { return niveauRisque; }
        public void setNiveauRisque(NiveauRisque niveauRisque) { this.niveauRisque = niveauRisque; }
    }
    
    /**
     * Classe interne pour les statistiques de session
     */
    public static class StatistiquesSession {
        private int nombreRequetes;
        private Long tempsMoyenReponse; // en millisecondes
        private int nombreErreurs;
        private Timestamp derniereRequete;
        private String pageLaPlusVisitee;
        private Map<String, Integer> actionsCounts;
        
        public StatistiquesSession() {
            this.actionsCounts = new HashMap<>();
        }
        
        /**
         * Ajoute une action à la session
         */
        public void ajouterAction(String action) {
            if (actionsCounts == null) {
                actionsCounts = new HashMap<>();
            }
            actionsCounts.put(action, actionsCounts.getOrDefault(action, 0) + 1);
        }
        
        /**
         * Calcule le taux d'erreur
         */
        public double getTauxErreur() {
            if (nombreRequetes == 0) return 0.0;
            return (double) nombreErreurs / nombreRequetes * 100;
        }
        
        // Getters et Setters
        public int getNombreRequetes() { return nombreRequetes; }
        public void setNombreRequetes(int nombreRequetes) { this.nombreRequetes = nombreRequetes; }
        
        public Long getTempsMoyenReponse() { return tempsMoyenReponse; }
        public void setTempsMoyenReponse(Long tempsMoyenReponse) { this.tempsMoyenReponse = tempsMoyenReponse; }
        
        public int getNombreErreurs() { return nombreErreurs; }
        public void setNombreErreurs(int nombreErreurs) { this.nombreErreurs = nombreErreurs; }
        
        public Timestamp getDerniereRequete() { return derniereRequete; }
        public void setDerniereRequete(Timestamp derniereRequete) { this.derniereRequete = derniereRequete; }
        
        public String getPageLaPlusVisitee() { return pageLaPlusVisitee; }
        public void setPageLaPlusVisitee(String pageLaPlusVisitee) { this.pageLaPlusVisitee = pageLaPlusVisitee; }
        
        public Map<String, Integer> getActionsCounts() { return actionsCounts; }
        public void setActionsCounts(Map<String, Integer> actionsCounts) { this.actionsCounts = actionsCounts; }
    }
    
    // Constructeurs
    public SessionResponse() {
        super(true, "Informations de session récupérées");
        this.appareil = new InfoAppareil();
        this.localisation = new InfoLocalisation();
        this.statut = new StatutSession();
        this.statistiques = new StatistiquesSession();
        this.metadonnees = new HashMap<>();
    }
    
    public SessionResponse(String tokenSession, Timestamp dateExpiration) {
        this();
        this.tokenSession = tokenSession;
        this.dateExpiration = dateExpiration;
        this.dateCreation = new Timestamp(System.currentTimeMillis());
        this.derniereActivite = new Timestamp(System.currentTimeMillis());
    }
    
    /**
     * Méthodes statiques pour créer des SessionResponse depuis les entités
     */
    
    /**
     * Crée un SessionResponse à partir d'une entité SessionsActives
     */
    public static SessionResponse fromSessionsActives(SessionsActives session) {
        if (session == null) return null;
        
        SessionResponse response = new SessionResponse();
        
        // Informations de base
        response.setSessionId(session.getId());
        response.setTokenSession(session.getTokenSession());
        response.setTokenRefresh(session.getTokenRefresh());
        response.setDateCreation(session.getCreeLe());
        response.setDateExpiration(session.getExpireLe());
        response.setDerniereActivite(session.getDerniereActivite());
        
        // Informations d'appareil
        InfoAppareil appareil = new InfoAppareil();
        appareil.setUserAgent(session.getUserAgent());
        appareil.setEmpreinteAppareil(session.getEmpreinteAppareil());
        appareil.setNomAppareil(session.getNomAppareil());
        response.setAppareil(appareil);
        
        // Informations de localisation
        InfoLocalisation localisation = new InfoLocalisation();
        localisation.setAdresseIP(session.getAdresseIp() != null ? session.getAdresseIp().toString() : null);
        localisation.setPays(session.getPays());
        localisation.setVille(session.getVille());
        response.setLocalisation(localisation);
        
        // Statut
        StatutSession statut = new StatutSession();
        statut.setEstActive(session.getEstActif());
        statut.setEstExpiree(session.getExpireLe() != null && 
                            session.getExpireLe().before(new Timestamp(System.currentTimeMillis())));
        response.setStatut(statut);
        
        return response;
    }
    
    /**
     * Méthodes utilitaires
     */
    
    /**
     * Vérifie si la session est valide
     */
    public boolean isValid() {
        return statut != null && statut.isValide();
    }
    
    /**
     * Vérifie si la session expire bientôt (dans les 5 prochaines minutes)
     */
    public boolean expiresSoon() {
        if (dateExpiration == null) {
            return false; // Session sans expiration
        }
        
        long maintenant = System.currentTimeMillis();
        long expiration = dateExpiration.getTime();
        long cinqMinutes = 5 * 60 * 1000; // 5 minutes en millisecondes
        
        return (expiration - maintenant) <= cinqMinutes;
    }
    
    /**
     * Calcule la durée de la session en minutes
     */
    public Long calculateDureeSession() {
        if (dateCreation == null || derniereActivite == null) {
            return null;
        }
        
        long dureeMs = derniereActivite.getTime() - dateCreation.getTime();
        return dureeMs / (1000 * 60); // Conversion en minutes
    }
    
    /**
     * Calcule le temps restant avant expiration
     */
    public Long calculateTempsRestant() {
        if (dateExpiration == null) {
            return null; // Session sans expiration
        }
        
        long maintenant = System.currentTimeMillis();
        long expiration = dateExpiration.getTime();
        
        if (expiration <= maintenant) {
            return 0L; // Session expirée
        }
        
        return (expiration - maintenant) / (1000 * 60); // Conversion en minutes
    }
    
    /**
     * Met à jour les durées calculées
     */
    public void updateCalculatedFields() {
        this.dureeSession = calculateDureeSession();
        this.tempsRestant = calculateTempsRestant();
        
        if (this.statut != null && this.tempsRestant != null && this.tempsRestant <= 0) {
            this.statut.setEstExpiree(true);
            this.statut.setEstActive(false);
        }
    }
    
    /**
     * Formate la durée de session pour affichage
     */
    public String getDureeSessionFormatee() {
        Long duree = this.dureeSession != null ? this.dureeSession : calculateDureeSession();
        if (duree == null) return "Inconnue";
        
        if (duree < 60) {
            return duree + " minutes";
        } else {
            long heures = duree / 60;
            long minutes = duree % 60;
            return heures + "h " + minutes + "min";
        }
    }
    
    /**
     * Nettoie les données sensibles pour les logs
     */
    public SessionResponse sanitizeForLogging() {
        SessionResponse sanitized = new SessionResponse();
        
        // Copie les données non sensibles
        sanitized.setSessionId(this.sessionId);
        sanitized.setDateCreation(this.dateCreation);
        sanitized.setDateExpiration(this.dateExpiration);
        sanitized.setDerniereActivite(this.derniereActivite);
        sanitized.setDureeSession(this.dureeSession);
        sanitized.setTempsRestant(this.tempsRestant);
        
        // Masque les tokens
        if (this.tokenSession != null && this.tokenSession.length() > 10) {
            String maskedToken = this.tokenSession.substring(0, 4) + "***" + 
                               this.tokenSession.substring(this.tokenSession.length() - 4);
            sanitized.setTokenSession(maskedToken);
        }
        
        // Copie les informations d'appareil (non sensibles)
        sanitized.setAppareil(this.appareil);
        
        // Copie la localisation avec IP masquée
        if (this.localisation != null) {
            InfoLocalisation locSanitized = new InfoLocalisation();
            locSanitized.setAdresseIP(this.localisation.getAdresseIPMasquee());
            locSanitized.setPays(this.localisation.getPays());
            locSanitized.setVille(this.localisation.getVille());
            locSanitized.setRegion(this.localisation.getRegion());
            sanitized.setLocalisation(locSanitized);
        }
        
        // Copie le statut et les statistiques
        sanitized.setStatut(this.statut);
        sanitized.setStatistiques(this.statistiques);
        
        return sanitized;
    }
    
    // Getters et Setters
    public Integer getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }
    
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
    
    public Timestamp getDateCreation() {
        return dateCreation;
    }
    
    public void setDateCreation(Timestamp dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    public Timestamp getDateExpiration() {
        return dateExpiration;
    }
    
    public void setDateExpiration(Timestamp dateExpiration) {
        this.dateExpiration = dateExpiration;
    }
    
    public Timestamp getDerniereActivite() {
        return derniereActivite;
    }
    
    public void setDerniereActivite(Timestamp derniereActivite) {
        this.derniereActivite = derniereActivite;
    }
    
    public Long getDureeSession() {
        return dureeSession;
    }
    
    public void setDureeSession(Long dureeSession) {
        this.dureeSession = dureeSession;
    }
    
    public Long getTempsRestant() {
        return tempsRestant;
    }
    
    public void setTempsRestant(Long tempsRestant) {
        this.tempsRestant = tempsRestant;
    }
    
    public InfoAppareil getAppareil() {
        return appareil;
    }
    
    public void setAppareil(InfoAppareil appareil) {
        this.appareil = appareil;
    }
    
    public InfoLocalisation getLocalisation() {
        return localisation;
    }
    
    public void setLocalisation(InfoLocalisation localisation) {
        this.localisation = localisation;
    }
    
    public StatutSession getStatut() {
        return statut;
    }
    
    public void setStatut(StatutSession statut) {
        this.statut = statut;
    }
    
    public StatistiquesSession getStatistiques() {
        return statistiques;
    }
    
    public void setStatistiques(StatistiquesSession statistiques) {
        this.statistiques = statistiques;
    }
    
    public Map<String, Object> getMetadonnees() {
        return metadonnees;
    }
    
    public void setMetadonnees(Map<String, Object> metadonnees) {
        this.metadonnees = metadonnees;
    }
    
    @Override
    public String toString() {
        return "SessionResponse{" +
                "sessionId=" + sessionId +
                ", tokenSession='" + (tokenSession != null ? tokenSession.substring(0, Math.min(8, tokenSession.length())) + "..." : "null") + '\'' +
                ", dateCreation=" + dateCreation +
                ", dateExpiration=" + dateExpiration +
                ", dureeSession=" + getDureeSessionFormatee() +
                ", tempsRestant=" + tempsRestant + "min" +
                ", estActive=" + (statut != null ? statut.isEstActive() : "null") +
                ", estValide=" + isValid() +
                ", appareil='" + (appareil != null ? appareil.getDescriptionAppareil() : "null") + '\'' +
                ", localisation='" + (localisation != null ? localisation.getLocalisationComplete() : "null") + '\'' +
                '}';
    }
}