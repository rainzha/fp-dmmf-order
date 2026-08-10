package org.rainzha.dmmf.domain.records;

import org.rainzha.dmmf.domain.valueobj.EmailAddress;

/**
 * F#积类型Record映射
 * { Name: PersonalName
 * EmailAddress: EmailAddress }
 */
public record CustomerInfo(
        PersonalName name,
        EmailAddress emailAddress
) {
}