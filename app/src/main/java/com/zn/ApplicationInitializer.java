package com.zn;

import com.zn.cms.permission.model.Permission;
import com.zn.cms.permission.service.PermissionService;
import com.zn.cms.role.service.RoleService;
import com.zn.cms.user.dto.UserDTO;
import com.zn.cms.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@RequiredArgsConstructor
@Component
public class ApplicationInitializer implements CommandLineRunner {

    private final PermissionService permissionService;
    private final RoleService roleService;
    private final UserService userService;

    @Value("${application.security.user.admin}")
    private String adminUser;
    @Value("${application.security.user.contributor}")
    private String contributorUser;
    @Value("${application.security.user.designer}")
    private String designerUser;

    @Value("${application.security.user.password}")
    private String password;

    @Override
    public void run(String... args) {
        initPermissions();
        initRoles();
        initUsers();

    }


    private void initPermissions() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("app/initData/initPermission.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray permissionList = (JSONArray) obj;

            //Iterate over employee array
            permissionList.forEach(permission -> parsePermissionObject((JSONObject) permission));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void parsePermissionObject(JSONObject permission) {
        //Get employee object within list
        String name = permission.get("name").toString();
        List<String> depsList = new ArrayList<String>();
        JSONArray arr = (JSONArray) permission.get("deps");
        for (Object perObject : arr) {
            depsList.add(perObject.toString());
        }
        permissionService.createPermissionIfNotFound(name, depsList);
    }

    void initRoles() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("app/initData/initRoles.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray permissionList = (JSONArray) obj;
            //Iterate over employee array
            permissionList.forEach(role -> parseRoleObject((JSONObject) role));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void parseRoleObject(JSONObject permission) {
        //Get employee object within list
        String name = permission.get("name").toString();
        List<String> permissionList = new ArrayList<String>();
        JSONArray arr = (JSONArray) permission.get("permissionList");
        for (Object perObject : arr) {
            permissionList.add(perObject.toString());
        }
        roleService.createRoleIfNotFound(name, permissionList);
    }

    void initUsers() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("app/initData/initUsers.json")) {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONArray permissionList = (JSONArray) obj;
            //Iterate over employee array
            permissionList.forEach(user -> parseUserObject((JSONObject) user));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void parseUserObject(JSONObject permission) {
        //Get employee object within list
        String email = permission.get("email").toString();
        String firstName = permission.get("firstName").toString();
        String lastName = permission.get("lastName").toString();
        String username = permission.get("username").toString();
        String password = permission.get("password").toString();
        List<String> rolesList = new ArrayList<String>();
        JSONArray arr = (JSONArray) permission.get("rolesList");
        for (Object perObject : arr) {
            rolesList.add(perObject.toString());
        }
        userService.createInitUser(email, firstName, lastName, username, password, rolesList);
    }
}
