/*
 * Copyright 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.carlomicieli.models;

import com.github.carlomicieli.AbstractValidationTests;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author Carlo Micieli
 */
public class ShowFormValidationTests extends AbstractValidationTests {

    @Before
    public void initValidator() {
        super.init(MailUser.class);
    }

    @Test
    public void shouldValidateAddressAfterMovieIsFound() {
        ShowForm sf = new ShowForm();
        sf.setVenue(""); // (1)
        sf.setAddress(""); // (2)
        sf.setCity(""); // (3)
        sf.setPostalCode(""); // (4)

        Set<ConstraintViolation<ShowForm>> violations = validator.validate(sf, AddressGroup.class);
        assertEquals(4, violations.size());
    }
}
