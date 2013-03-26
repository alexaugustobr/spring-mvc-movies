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
package com.github.carlomicieli.nerdmovies.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Carlo Micieli
 */
public class MailUserTests {
    @Test
    public void shouldAddNewRoleToUsers() {
        MailUser user = new MailUser();
        user.setEmailAddress("joey@ramones.com");
        user.setPassword("secret");
        user.addRole("ROLE_USER");
        user.addRole("ROLE_ADMIN");

        assertEquals(2, user.getRoles().size());
        assertEquals("[ROLE_USER, ROLE_ADMIN]", user.getRoles().toString());
    }

    @Test
    public void shouldInitializeNewUsers() {
        MailUser user = new MailUser();
        user.init();

        assertEquals(true, user.isEnabled());
        assertEquals("[ROLE_USER]", user.getRoles().toString());
    }
}
