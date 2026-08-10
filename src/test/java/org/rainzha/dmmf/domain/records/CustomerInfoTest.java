package org.rainzha.dmmf.domain.records;

import org.junit.jupiter.api.Test;
import org.rainzha.dmmf.domain.valueobj.EmailAddress;
import org.rainzha.dmmf.domain.valueobj.String50;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerInfoTest {
    private final String50 first = String50.create("Tom");
    private final String50 last = String50.create("Lee");
    private final PersonalName name = new PersonalName(first, last);
    private final EmailAddress email = EmailAddress.create("tomlee@example.com");

    @Test
    void construct_ValidCompositeValue_HoldDataCorrectly() {
        CustomerInfo customer = new CustomerInfo(name, email);
        assertThat(customer.name()).isEqualTo(name);
        assertThat(customer.emailAddress()).isEqualTo(email);
        assertThat(customer.name().firstName().value()).isEqualTo("Tom");
    }

    @Test
    void equals_SameInnerValues_EqualInstance() {
        PersonalName name2 = new PersonalName(first, last);
        EmailAddress email2 = EmailAddress.create("tomlee@example.com");
        CustomerInfo c1 = new CustomerInfo(name, email);
        CustomerInfo c2 = new CustomerInfo(name2, email2);
        assertThat(c1).isEqualTo(c2);
        assertThat(c1.hashCode()).isEqualTo(c2.hashCode());
    }
}