package com.youtube.jwt.configuration;
/*
import com.youtube.jwt.dao.UserDao;
import com.youtube.jwt.entity.Role;
import com.youtube.jwt.entity.User;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RoleModel;
import org.keycloak.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class MongoUserFederationProvider implements UserFederationProvider {

    @Autowired
    private UserDao userDao;

    @Override
    public UserModel getUserByUsername(String username, RealmModel realm) {
        User user = userDao.findByUserName(username);
        if (user != null) {
            return new UserAdapter(new KeycloakPrincipal<>(username), realm, user) {
                @Override
                public Set<RoleModel> getRoleMappings() {
                    Set<RoleModel> roleMappings = new HashSet<>();
                    for (Role role : user.getRole()) {
                        roleMappings.add(realm.getRole(role.getRoleName()));
                    }
                    return roleMappings;
                }
            };
        }
        return null;
    }

    @Override
    public UserModel getUserByEmail(String email, RealmModel realm) {
        // Not implemented
        return null;
    }

    @Override
    public UserModel getUserById(String id, RealmModel realm) {
        // Not implemented
        return null;
    }

    @Override
    public boolean synchronizeRegistrations(RealmModel realm, UserModel user) {
        // Not implemented
        return false;
    }

    @Override
    public UserModel register(RealmModel realm, UserModel user) {
        // Not implemented
        return null;
    }

    @Override
    public boolean removeUser(RealmModel realm, UserModel user) {
        // Not implemented
        return false;
    }

    @Override
    public boolean updatePassword(RealmModel realm, UserModel user, String password) {
        // Not implemented
        return false;
    }

    @Override
    public Set<String> getSupportedCredentialTypes() {
        // Not implemented
        return null;
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user) {
        // Not implemented
        return false;
    }
}*/
