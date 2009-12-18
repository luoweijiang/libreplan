/*
 * This file is part of ###PROJECT_NAME###
 *
 * Copyright (C) 2009 Fundación para o Fomento da Calidade Industrial e
 *                    Desenvolvemento Tecnolóxico de Galicia
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.navalplanner.web.users;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.navalplanner.business.common.exceptions.InstanceNotFoundException;
import org.navalplanner.business.common.exceptions.ValidationException;
import org.navalplanner.business.users.daos.IUserDAO;
import org.navalplanner.business.users.entities.Profile;
import org.navalplanner.business.users.entities.User;
import org.navalplanner.business.users.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Model for UI operations related to {@link User}
 *
 * @author Jacobo Aragunde Perez <jaragunde@igalia.com>
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserModel implements IUserModel {

    @Autowired
    private IUserDAO userDAO;

    private User user;

    @Override
    @Transactional(readOnly=true)
    public List<User> getUsers() {
        return userDAO.list(User.class);
    }

    @Override
    @Transactional
    public void confirmSave() throws ValidationException {
        userDAO.save(user);
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void initCreate() {
        this.user = User.create();
    }

    @Override
    @Transactional(readOnly = true)
    public void initEdit(User user) {
        Validate.notNull(user);
        this.user = getFromDB(user);
    }

    @Transactional(readOnly = true)
    private User getFromDB(User user) {
        return getFromDB(user.getId());
    }

    @Transactional(readOnly = true)
    private User getFromDB(Long id) {
        try {
            User result = userDAO.find(id);
            forceLoadEntities(result);
            return result;
        } catch (InstanceNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Load entities that will be needed in the conversation
     *
     * @param costCategory
     */
    private void forceLoadEntities(User user) {
        user.getLoginName();
        for (UserRole each : user.getRoles()) {
            each.name();
        }
        for (Profile each : user.getProfiles()) {
            each.getProfileName();
        }
    }

    @Override
    public List<UserRole> getRoles() {
        List<UserRole> list = new ArrayList<UserRole>();
        if (user != null) {
            list.addAll(user.getRoles());
        }
        return list;
    }

    @Override
    public void removeRole(UserRole role) {
        user.removeRole(role);
    }

    @Override
    public void addRole(UserRole role) {
        user.addRole(role);
    }

    @Override
    public List<Profile> getProfiles() {
        List<Profile> list = new ArrayList<Profile>();
        if (user != null) {
            list.addAll(user.getProfiles());
        }
        return list;
    }
}
