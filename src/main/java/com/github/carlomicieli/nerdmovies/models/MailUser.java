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

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * It represents an application account.
 *
 * @author Carlo Micieli
 */
@Document(collection = "users")
public class MailUser implements Serializable {
    private static final long serialVersionUID = 3346614940089556537L;

    @Id
    private ObjectId id;

    @NotEmpty(message = "emailAddress.required")
    @Email(message = "emailAddress.notValid")
    @Length(min = 0, max = 50, message = "emailAddress.length")
    @Indexed(unique = true)
    private String emailAddress;

    @NotEmpty(message = "password.required")
    @Length(min = 0, max = 25, message = "password.length")
    private String password;

    @NotEmpty(message = "displayName.required")
    @Length(min = 0, max = 25, message = "displayName.length")
    private String displayName;

    private boolean isEnabled;
    private List<String> roles;

    public MailUser() {
    }

    public MailUser(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public ObjectId getId() {
        return id;
    }

    protected void setId(ObjectId id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void addRole(String roleName) {
        if (roles == null) roles = new ArrayList<String>();
        roles.add(roleName);
    }

    public void init() {
        isEnabled = true;
        addRole("ROLE_USER");
    }

    @Override
    public String toString() {
        return String.format("%s", getEmailAddress());
    }
}
