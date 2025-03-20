package org.hello.repository;

import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import org.hello.entity.AccessToken;

@EzyRepository
public interface AccessTokenRepository extends EzyDatabaseRepository<String, AccessToken> {
    AccessToken findByAccessToken(String accessToken);
}