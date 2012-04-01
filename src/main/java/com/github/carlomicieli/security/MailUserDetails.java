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
package com.github.carlomicieli.security;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.github.carlomicieli.models.MailUser;

/**
 * It represents a user details wrapper for the Authentication object.
 * 
 * @author Carlo P. Micieli
 *
 */
public class MailUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<String> l = mailUser.getRoles();
		String[] roles = l==null ?
				new String[] {"ROLE_USER"} :
				l.toArray(new String[l.size()]);
		
		return Collections.unmodifiableList(
				AuthorityUtils.createAuthorityList(roles));
	}

	private MailUser mailUser;
	public MailUserDetails(MailUser mailUser) {
		this.mailUser = mailUser;
	}
	
	public String getPassword() {
		return mailUser.getPassword();
	}

	public String getUsername() {
		return mailUser.getEmailAddress();
	}

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return mailUser.isEnabled();
    }
}
