package com.spectralink.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A EndUser.
 */
@Document(collection = "end_user")
public class EndUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("first_name")
    private String firstName;

    @Field("last_name")
    private String lastName;

    @Field("email")
    private String email;

    @Field("phone_number")
    private String phoneNumber;

    @Field("created")
    private Instant created;

    @DBRef
    @Field("contacts")
    private Set<Contact> contacts = new HashSet<>();

    @DBRef
    @Field("settings")
    private Set<UserSettings> settings = new HashSet<>();

    @DBRef
    @Field("organization")
    @JsonIgnoreProperties(value = "endUsers", allowSetters = true)
    private Organization organization;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public EndUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public EndUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public EndUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public EndUser phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Instant getCreated() {
        return created;
    }

    public EndUser created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public EndUser contacts(Set<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }

    public EndUser addContacts(Contact contact) {
        this.contacts.add(contact);
        contact.setEndUser(this);
        return this;
    }

    public EndUser removeContacts(Contact contact) {
        this.contacts.remove(contact);
        contact.setEndUser(null);
        return this;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<UserSettings> getSettings() {
        return settings;
    }

    public EndUser settings(Set<UserSettings> userSettings) {
        this.settings = userSettings;
        return this;
    }

    public EndUser addSettings(UserSettings userSettings) {
        this.settings.add(userSettings);
        userSettings.setEndUser(this);
        return this;
    }

    public EndUser removeSettings(UserSettings userSettings) {
        this.settings.remove(userSettings);
        userSettings.setEndUser(null);
        return this;
    }

    public void setSettings(Set<UserSettings> userSettings) {
        this.settings = userSettings;
    }

    public Organization getOrganization() {
        return organization;
    }

    public EndUser organization(Organization organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EndUser)) {
            return false;
        }
        return id != null && id.equals(((EndUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EndUser{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
