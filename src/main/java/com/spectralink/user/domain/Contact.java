package com.spectralink.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Contact.
 */
@Document(collection = "contact")
public class Contact implements Serializable {

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

    @Field("sip_extension")
    private String sipExtension;

    @Field("created")
    private Instant created;

    @Field("contact_icon")
    private String contactIcon;

    @DBRef
    @Field("endUser")
    @JsonIgnoreProperties(value = "contacts", allowSetters = true)
    private EndUser endUser;

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

    public Contact firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Contact lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Contact email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Contact phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSipExtension() {
        return sipExtension;
    }

    public Contact sipExtension(String sipExtension) {
        this.sipExtension = sipExtension;
        return this;
    }

    public void setSipExtension(String sipExtension) {
        this.sipExtension = sipExtension;
    }

    public Instant getCreated() {
        return created;
    }

    public Contact created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public String getContactIcon() {
        return contactIcon;
    }

    public Contact contactIcon(String contactIcon) {
        this.contactIcon = contactIcon;
        return this;
    }

    public void setContactIcon(String contactIcon) {
        this.contactIcon = contactIcon;
    }

    public EndUser getEndUser() {
        return endUser;
    }

    public Contact endUser(EndUser endUser) {
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
        if (!(o instanceof Contact)) {
            return false;
        }
        return id != null && id.equals(((Contact) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Contact{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", sipExtension='" + getSipExtension() + "'" +
            ", created='" + getCreated() + "'" +
            ", contactIcon='" + getContactIcon() + "'" +
            "}";
    }
}
