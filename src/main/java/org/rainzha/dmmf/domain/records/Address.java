package org.rainzha.dmmf.domain.records;

import org.rainzha.dmmf.domain.valueobj.String50;
import org.rainzha.dmmf.domain.valueobj.ZipCode;

import java.util.Optional;

/**
 * F#积类型Record映射
 * { AddressLine1: String50
 * AddressLine2: String50 option
 * AddressLine3: String50 option
 * AddressLine4: String50 option
 * City: String50
 * ZipCode: ZipCode }
 */
public record Address(
        String50 addressLine1,
        Optional<String50> addressLine2,
        Optional<String50> addressLine3,
        Optional<String50> addressLine4,
        String50 city,
        ZipCode zipCode
) {
}