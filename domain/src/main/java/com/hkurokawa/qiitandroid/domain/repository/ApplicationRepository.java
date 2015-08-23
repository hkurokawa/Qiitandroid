package com.hkurokawa.qiitandroid.domain.repository;

import com.hkurokawa.qiitandroid.domain.user.User;

/**
 * Repository to store/load information about the status of the application.
 * Created by hiroshi on 8/23/15.
 */
public interface ApplicationRepository {
    User loadUser();
}
