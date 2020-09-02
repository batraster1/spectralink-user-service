package com.spectralink.user.domain;

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
 * A Customer.
 */
@Document(collection = "customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("customer_name")
    private String customerName;

    @Field("customer_icon")
    private String customerIcon;

    @Field("created")
    private Instant created;

    @DBRef
    @Field("organization")
    private Set<Organization> organizations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Customer customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerIcon() {
        return customerIcon;
    }

    public Customer customerIcon(String customerIcon) {
        this.customerIcon = customerIcon;
        return this;
    }

    public void setCustomerIcon(String customerIcon) {
        this.customerIcon = customerIcon;
    }

    public Instant getCreated() {
        return created;
    }

    public Customer created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Set<Organization> getOrganizations() {
        return organizations;
    }

    public Customer organizations(Set<Organization> organizations) {
        this.organizations = organizations;
        return this;
    }

    public Customer addOrganization(Organization organization) {
        this.organizations.add(organization);
        organization.setCustomer(this);
        return this;
    }

    public Customer removeOrganization(Organization organization) {
        this.organizations.remove(organization);
        organization.setCustomer(null);
        return this;
    }

    public void setOrganizations(Set<Organization> organizations) {
        this.organizations = organizations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", customerName='" + getCustomerName() + "'" +
            ", customerIcon='" + getCustomerIcon() + "'" +
            ", created='" + getCreated() + "'" +
            "}";
    }
}
