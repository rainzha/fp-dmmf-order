package org.rainzha.dmmf.domain.records;

import org.junit.jupiter.api.Test;
import org.rainzha.dmmf.domain.valueobj.String50;
import org.rainzha.dmmf.domain.valueobj.ZipCode;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AddressTest {

    private final String50 line1 = String50.create("123 Main Street");
    private final String50 line2 = String50.create("Floor 8");
    private final String50 city = String50.create("Nanjing");
    private final ZipCode zip = ZipCode.create("210000");

    @Test
    void build_AllFieldsPresent_FieldsMatch() {
        Address address = new Address(
                line1,
                Optional.of(line2),
                Optional.empty(),
                Optional.empty(),
                city,
                zip
        );

        assertThat(address.addressLine1()).isEqualTo(line1);
        assertThat(address.addressLine2()).contains(line2);
        assertThat(address.addressLine3()).isEmpty();
        assertThat(address.addressLine4()).isEmpty();
        assertThat(address.city()).isEqualTo(city);
        assertThat(address.zipCode()).isEqualTo(zip);
    }

    @Test
    void equals_SameValues_Equal() {
        Address a1 = new Address(line1, Optional.empty(), Optional.empty(), Optional.empty(), city, zip);
        Address a2 = new Address(line1, Optional.empty(), Optional.empty(), Optional.empty(), city, zip);
        assertThat(a1).isEqualTo(a2);
        assertThat(a1.hashCode()).isEqualTo(a2.hashCode());
    }
}