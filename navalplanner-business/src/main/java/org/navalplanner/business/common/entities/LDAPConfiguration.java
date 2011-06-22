/*
 * This file is part of NavalPlan
 *
 * Copyright (C) 2011 Comtecsf
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

package org.navalplanner.business.common.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.navalplanner.business.common.BaseEntity;
import org.navalplanner.business.users.entities.UserRole;

/**
 *
 * This entity will be used to store the LDAP connection properties for
 * authentication
 *
 * @author Ignacio Diaz Teijido <ignacio.diaz@comtecsf.es>
 * @author Cristina Alvarino Perez<cristina.alvarino@comtecsf.es>
 *
 */
public class LDAPConfiguration extends BaseEntity {

    public static LDAPConfiguration create() {
        return create(new LDAPConfiguration());
    }

    // Place in LDAP where username is
    private String ldapUserId;

    private String ldapHost;

    private String ldapPort;

    private String ldapBase;

    private String ldapUserDn;

    private String ldapPassword;

    private String ldapGroupPath;

    private String ldapRoleProperty;

    // LDAP passwords will be imported to DB or not
    private Boolean ldapSavePasswordsDB = true;

    // LDAP Authentication will be used or not
    private Boolean ldapAuthEnabled = false;

    // LDAP roles will be used or not
    private Boolean ldapSaveRolesDB = false;

    // A list which stores the matching between LDAP roles and LibrePlan roles
    private List<ConfigurationRolesLDAP> configurationRolesLdap = new ArrayList<ConfigurationRolesLDAP>();

    private Map<String, List<String>> mapMatchingRoles = new HashMap<String, List<String>>();

    public String getLdapUserId() {
        return ldapUserId;
    }

    public void setLdapUserId(String ldapUserId) {
        this.ldapUserId = ldapUserId;
    }

    public String getLdapHost() {
        return ldapHost;
    }

    public void setLdapHost(String ldapHost) {
        this.ldapHost = ldapHost;
    }

    public String getLdapPort() {
        return ldapPort;
    }

    public void setLdapPort(String ldapPort) {
        this.ldapPort = ldapPort;
    }

    public String getLdapBase() {
        return ldapBase;
    }

    public void setLdapBase(String ldapBase) {
        this.ldapBase = ldapBase;
    }

    public String getLdapUserDn() {
        return ldapUserDn;
    }

    public void setLdapUserDn(String ldapUserDn) {
        this.ldapUserDn = ldapUserDn;
    }

    public String getLdapPassword() {
        return ldapPassword;
    }

    public void setLdapPassword(String ldapPassword) {
        this.ldapPassword = ldapPassword;
    }

    public Boolean isLdapSavePasswordsDB() {
        return ldapSavePasswordsDB;
    }

    public void setLdapSavePasswordsDB(Boolean ldapSavePasswordsDB) {
        this.ldapSavePasswordsDB = ldapSavePasswordsDB;
    }

    public Boolean getLdapAuthEnabled() {
        return ldapAuthEnabled;
    }

    public void setLdapAuthEnabled(Boolean ldapAuthEnabled) {
        this.ldapAuthEnabled = ldapAuthEnabled;
    }

    public Boolean getLdapSaveRolesDB() {
        return ldapSaveRolesDB;
    }

    public void setLdapSaveRolesDB(Boolean ldapSaveRolesDB) {
        this.ldapSaveRolesDB = ldapSaveRolesDB;
    }

    public Boolean getLdapSavePasswordsDB() {
        return ldapSavePasswordsDB;
    }

    public String getLdapGroupPath() {
        return ldapGroupPath;
    }

    public void setLdapGroupPath(String ldapGroupPath) {
        this.ldapGroupPath = ldapGroupPath;
    }

    public String getLdapRoleProperty() {
        return ldapRoleProperty;
    }

    public void setLdapRoleProperty(String ldapRoleProperty) {
        this.ldapRoleProperty = ldapRoleProperty;
    }

    public List<ConfigurationRolesLDAP> getConfigurationRolesLdap() {
        return configurationRolesLdap;
    }

    public void setConfigurationRolesLdap(
            List<ConfigurationRolesLDAP> configurationRolesLdap) {
        this.configurationRolesLdap = configurationRolesLdap;
    }

    public Map<String, List<String>> getMapMatchingRoles() {

        for (UserRole role : UserRole.values()) {

            List<String> listRolesLdap = new ArrayList<String>();
            for (ConfigurationRolesLDAP roleLdap : this.configurationRolesLdap) {

                // if the role of librePlan is equals to role stored in
                // configurationLdap, it is added to list
                if (roleLdap != null
                        && role.name().equals(roleLdap.getRoleLibreplan())) {
                    listRolesLdap.add(roleLdap.getRoleLdap());
                }
            }
            mapMatchingRoles.put(role.name(), listRolesLdap);
        }

        return mapMatchingRoles;
    }

    public void setMapMatchingRoles(Map<String, List<String>> mapMatchingRoles) {
        this.mapMatchingRoles = mapMatchingRoles;
    }
}