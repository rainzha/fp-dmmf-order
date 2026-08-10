package org.rainzha.dmmf.domain.records;

import org.junit.jupiter.api.Test;
import org.rainzha.dmmf.domain.valueobj.String50;

import static org.assertj.core.api.Assertions.assertThat;

class PersonalNameTest {

    @Test
    void construct_WithValidString50_StoreFieldsCorrectly() {
        String50 first = String50.create("Alice");
        String50 last = String50.create("Smith");

        PersonalName name = new PersonalName(first, last);

        assertThat(name.firstName()).isEqualTo(first);
        assertThat(name.lastName()).isEqualTo(last);
        assertThat(name.firstName().value()).isEqualTo("Alice");
        assertThat(name.lastName().value()).isEqualTo("Smith");
    }

    @Test
    void equals_HaveSameNameValue_Equal() {
        String50 f1 = String50.create("Bob");
        String50 l1 = String50.create("Brown");
        String50 f2 = String50.create("Bob");
        String50 l2 = String50.create("Brown");

        PersonalName n1 = new PersonalName(f1, l1);
        PersonalName n2 = new PersonalName(f2, l2);

        assertThat(n1).isEqualTo(n2);
        assertThat(n1.hashCode()).isEqualTo(n2.hashCode());
    }
}