package com.spectralink.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.Instant;

/**
 * A UserSettings.
 */
@Document(collection = "user_settings")
public class UserSettings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("wallpaper")
    private String wallpaper;

    @Field("ring_tone")
    private String ringTone;

    @Field("volume")
    private String volume;

    @Field("created")
    private Instant created;

    @DBRef
    @Field("endUser")
    @JsonIgnoreProperties(value = "settings", allowSetters = true)
    private EndUser endUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWallpaper() {
        return wallpaper;
    }

    public UserSettings wallpaper(String wallpaper) {
        this.wallpaper = wallpaper;
        return this;
    }

    public void setWallpaper(String wallpaper) {
        this.wallpaper = wallpaper;
    }

    public String getRingTone() {
        return ringTone;
    }

    public UserSettings ringTone(String ringTone) {
        this.ringTone = ringTone;
        return this;
    }

    public void setRingTone(String ringTone) {
        this.ringTone = ringTone;
    }

    public String getVolume() {
        return volume;
    }

    public UserSettings volume(String volume) {
        this.volume = volume;
        return this;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public Instant getCreated() {
        return created;
    }

    public UserSettings created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public EndUser getEndUser() {
        return endUser;
    }

    public UserSettings endUser(EndUser endUser) {
        this.endUser = endUser;
        return this;
    }

    public void setEndUser(EndUser endUser) {
        this.endUser = endUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserSettings)) {
            return false;
        }
        return id != null && id.equals(((UserSettings) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserSettings{" +
            "id=" + getId() +
            ", wallpaper='" + getWallpaper() + "'" +
            ", ringTone='" + getRingTone() + "'" +
            ", volume='" + getVolume() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
