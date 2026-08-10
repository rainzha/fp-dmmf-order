package org.rainzha.dmmf.domain.records;

import org.rainzha.dmmf.domain.valueobj.String50;

/**
 * F#积类型 Record 映射：{ FirstName: String50; LastName: String50 }
 */
public record PersonalName(String50 firstName, String50 lastName) {
}