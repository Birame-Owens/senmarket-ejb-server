package authentification.dto.common;



import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO contenant les informations d'une session utilisateur
 * Utilisé pour transférer les données de session
 * 
 * @author SenMarket Team
 * @version 1.0
 */
public class SessionInfo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String tokenSession;
    private String tokenRefresh;
    private Timestamp dateCreation;
    private Timestamp dateExpiration;
    private Timestamp derniereActivite;
    
    // Informations de l'appareil/navigateur
    private String adresseIp;
    private String userAgent;
    private String empreinteAppareil;
    private String nomAppareil;
    private String typeAppareil; // mobile, desktop, tablet
    private String navigateur;
    private String systemeExploitation;
    
    // Localisation
    private String pays;
    private String ville;
    
    // Statut
    private Boolean estActif;
    private Boolean estExpire;
    
    // Constructeurs
    public SessionInfo() {
    }
    
    public SessionInfo(String tokenSession, Timestamp dateExpiration) {
        this.tokenSession = tokenSession;
        this.dateExpiration = dateExpiration;
        this.dateCreation = new Timestamp(System.currentTimeMillis());
        this.derniereActivite = new Timestamp(System.currentTimeMillis());
        this.estActif = true;
    }
    
    /**
     * Constructeur à partir d'une entité SessionsActives
     */
    public static SessionInfo fromSessionsActives(model.utilisateur.SessionsActives session) {
        if (session == null) return null;
        
        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setId(session.getId());
        sessionInfo.setTokenSession(session.getTokenSession());
        sessionInfo.setTokenRefresh(session.getTokenRefresh());
        sessionInfo.setDateCreation(session.getCreeLe());
        sessionInfo.setDateExpiration(session.getExpireLe());
        sessionInfo.setDerniereActivite(session.getDerniereActivite());
        sessionInfo.setAdresseIp(session.getAdresseIp() != null ? session.getAdresseIp().toString() : null);
        sessionInfo.setUserAgent(session.getUserAgent());
        sessionInfo.setEmpreinteAppareil(session.getEmpreinteAppareil());
        sessionInfo.setNomAppareil(session.getNomAppareil());
        sessionInfo.setPays(session.getPays());
        sessionInfo.setVille(session.getVille());
        sessionInfo.setEstActif(session.getEstActif());
        
        return sessionInfo;
    }
    
    // Méthodes utilitaires
    public boolean isValid() {
        if (!Boolean.TRUE.equals(estActif)) {
            return false;
        }
        if (dateExpiration == null) {
            return true; // Session sans expiration
        }
        return dateExpiration.after(new Timestamp(System.currentTimeMillis()));
    }
    
    public boolean isExpired() {
        if (dateExpiration == null) {
            return false; // Session sans expiration
        }
        return dateExpiration.before(new Timestamp(System.currentTimeMillis()));
    }
    
    public long getDureeActivite() {
        if (dateCreation == null || derniereActivite == null) {
            return 0;
        }
        return derniereActivite.getTime() - dateCreation.getTime();
    }
    
    public String getDureeActiviteFormatee() {
        long duree = getDureeActivite();
        long heures = duree / (1000 * 60 * 60);
        long minutes = (duree % (1000 * 60 * 60)) / (1000 * 60);
        
        if (heures > 0) {
            return heures + "h " + minutes + "min";
        } else {
            return minutes + "min";
        }
    }
    
    public String getAppareilDescription() {
        StringBuilder description = new StringBuilder();
        
        if (nomAppareil != null && !nomAppareil.trim().isEmpty()) {
            description.append(nomAppareil);
        } else {
            if (navigateur != null) {
                description.append(navigateur);
            }
            if (systemeExploitation != null) {
                if (description.length() > 0) {
                    description.append(" sur ");
                }
                description.append(systemeExploitation);
            }
        }
        
        return description.toString();
    }
    
    public String getLocalisationDescription() {
        StringBuilder localisation = new StringBuilder();
        
        if (ville != null && !ville.trim().isEmpty()) {
            localisation.append(ville);
        }
        if (pays != null && !pays.trim().isEmpty()) {
            if (localisation.length() > 0) {
                localisation.append(", ");
            }
            localisation.append(pays);
        }
        
        return localisation.toString();
    }
    
    // Getters et Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
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
    
    public String getAdresseIp() {
        return adresseIp;
    }
    
    public void setAdresseIp(String adresseIp) {
        this.adresseIp = adresseIp;
    }
    
    public String getUserAgent() {
        return userAgent;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    
    public String getEmpreinteAppareil() {
        return empreinteAppareil;
    }
    
    public void setEmpreinteAppareil(String empreinteAppareil) {
        this.empreinteAppareil = empreinteAppareil;
    }
    
    public String getNomAppareil() {
        return nomAppareil;
    }
    
    public void setNomAppareil(String nomAppareil) {
        this.nomAppareil = nomAppareil;
    }
    
    public String getTypeAppareil() {
        return typeAppareil;
    }
    
    public void setTypeAppareil(String typeAppareil) {
        this.typeAppareil = typeAppareil;
    }
    
    public String getNavigateur() {
        return navigateur;
    }
    
    public void setNavigateur(String navigateur) {
        this.navigateur = navigateur;
    }
    
    public String getSystemeExploitation() {
        return systemeExploitation;
    }
    
    public void setSystemeExploitation(String systemeExploitation) {
        this.systemeExploitation = systemeExploitation;
    }
    
    public String getPays() {
        return pays;
    }
    
    public void setPays(String pays) {
        this.pays = pays;
    }
    
    public String getVille() {
        return ville;
    }
    
    public void setVille(String ville) {
        this.ville = ville;
    }
    
    public Boolean getEstActif() {
        return estActif;
    }
    
    public void setEstActif(Boolean estActif) {
        this.estActif = estActif;
    }
    
    public Boolean getEstExpire() {
        return estExpire;
    }
    
    public void setEstExpire(Boolean estExpire) {
        this.estExpire = estExpire;
    }
    
    @Override
    public String toString() {
        return "SessionInfo{" +
                "id=" + id +
                ", tokenSession='" + (tokenSession != null ? tokenSession.substring(0, Math.min(10, tokenSession.length())) + "..." : "null") + '\'' +
                ", dateCreation=" + dateCreation +
                ", dateExpiration=" + dateExpiration +
                ", estActif=" + estActif +
                ", appareil='" + getAppareilDescription() + '\'' +
                '}';
    }
}