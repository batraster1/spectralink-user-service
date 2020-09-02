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
 * A Organization.
 */
@Document(collection = "organization")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("organization_name")
    private String organizationName;

    @Field("organization_description")
    private String organizationDescription;

    @Field("organization_icon")
    private String organizationIcon;

    @Field("created")
    private Instant created;

    @DBRef
    @Field("endUser")
    private Set<EndUser> endUsers = new HashSet<>();

    @DBRef
    @Field("customer")
    @JsonIgnoreProperties(value = "organizations", allowSetters = true)
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public Organization organizationName(String organizationName) {
        this.organizationName = organizationName;
        return this;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationDescription() {
        return organizationDescription;
    }

    public Organization organizationDescription(String organizationDescription) {
        this.organizationDescription = organizationDescription;
        return this;
    }

    public void setOrganizationDescription(String organizationDescription) {
        this.organizationDescription = organizationDescription;
    }

    public String getOrganizationIcon() {
        return organizationIcon;
    }

    public Organization organizationIcon(String organizationIcon) {
        this.organizationIcon = organizationIcon;
        return this;
    }

    public void setOrganizationIcon(String organizationIcon) {
        this.organizationIcon = organizationIcon;
    }

    public Instant getCreated() {
        return created;
    }

    public Organization created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Set<EndUser> getEndUsers() {
        return endUsers;
    }

    public Organization endUsers(Set<EndUser> endUsers) {
        this.endUsers = endUsers;
        return this;
    }

    public Organization addEndUser(EndUser endUser) {
        this.endUsers.add(endUser);
        endUser.setOrganization(this);
        return this;
    }

    public Organization removeEndUser(EndUser endUser) {
        this.endUsers.remove(endUser);
        endUser.setOrganization(null);
        return this;
    }

    public void setEndUsers(Set<EndUser> endUsers) {
        this.endUsers = endUsers;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Organization customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Organization)) {
            return false;
        }
        return id != null && id.equals(((Organization) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", organizationName='" + getOrganizationName() + "'" +
            ", organizationDescription='" + getOrganizationDescription() + "'" +
            ", organizationIcon='" + getOrganizationIcon() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
