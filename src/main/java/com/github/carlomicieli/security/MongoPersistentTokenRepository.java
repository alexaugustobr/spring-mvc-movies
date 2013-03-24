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

import com.github.carlomicieli.models.MongoRememberMeToken;
import com.github.carlomicieli.services.MongoRememberMeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Carlo Micieli
 */
@Component("persistentTokenRepository")
public class MongoPersistentTokenRepository implements
        PersistentTokenRepository {

    private MongoRememberMeService rememberMeService;

    @Autowired
    public MongoPersistentTokenRepository(MongoRememberMeService rememberMeService) {
        this.rememberMeService = rememberMeService;
    }

    public void createNewToken(PersistentRememberMeToken token) {
        final MongoRememberMeToken t =
                new MongoRememberMeToken(token);
        rememberMeService.createNew(t);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        final MongoRememberMeToken token =
                new MongoRememberMeToken();

        token.setDate(lastUsed);
        token.setTokenValue(tokenValue);
        token.setSeries(series);
        rememberMeService.saveToken(token);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        final MongoRememberMeToken token =
                rememberMeService.findBySeries(seriesId);

        return new PersistentRememberMeToken(token.getUsername(),
                token.getSeries(),
                token.getTokenValue(),
                token.getDate());
    }

    @Override
    public void removeUserTokens(String username) {
        rememberMeService.removeToken(username);
    }

}
