package com.hkurokawa.qiitandroid.domain.repository;

import java.util.List;

/**
 * Repository to store/load information of the users to login.
 * Created by hiroshi on 8/23/15.
 */
public interface UserRepository {
    List<String> availableTeamIds();
}
