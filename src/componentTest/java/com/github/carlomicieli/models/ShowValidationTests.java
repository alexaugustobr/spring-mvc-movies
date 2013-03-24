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
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * @author Carlo Micieli
 */
public class ShowValidationTests extends AbstractValidationTests {

    @Before
    public void initValidator() {
        super.init(Show.class);
    }

    @Test
    public void shouldValidateCorrectShow() {
        Show show = new Show();
        show.setHostedBy("deedee");
        show.setDate(new Date());
        Set<ConstraintViolation<Show>> violations = validator.validate(show);
        assertEquals(0, violations.size());
    }

    @Test
    public void hostIsRequired() {
        Show show = new Show();
        show.setDate(new Date());
        Set<ConstraintViolation<Show>> violations = validator.validate(show);
        assertEquals(1, violations.size());
        assertEquals("hostedBy.required", violations.iterator().next().getMessage());
    }

    @Test
    public void showDateIsRequired() {
        Show show = new Show();
        show.setHostedBy("deedee");
        Set<ConstraintViolation<Show>> violations = validator.validate(show);
        assertEquals(1, violations.size());
        assertEquals("date.required", violations.iterator().next().getMessage());
    }
}
