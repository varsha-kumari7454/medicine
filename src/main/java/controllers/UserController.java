/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import models.User;
import ninja.AuthenticityFilter;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.Router;
import ninja.params.Param;
import ninja.params.PathParam;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.UserDao;

@Singleton
public class UserController {
	Logger log=Logger.getLogger(UserController.class.getName());
	
    @Inject
    User user;
    
    @Inject
    UserDao userDao;

    @Inject
    Router route;
    
	private int id=0;
    
    public UserController() {}
    
    public Result index() {
    	List<User> userList=userDao.findAll();
    	return Results.html().template("views/LoginLogoutController/login.ftl.html");
    }
    
    public Result newUser(){
    	return Results.html().template("views/UserController/newUser.ftl.html");
    }
    
    @FilterWith(AuthenticityFilter.class)
    public Result create(Context context, @JSR303Validation User user, Validation validation){
    	if(validation.hasViolations()){
    		flashError(context, user);
    		return Results.redirect(route.getReverseRoute(UserController.class, "newUser"));
    	}
    	userDao.save(user);
    	context.getFlashScope().put("success", "save.success");
    	return Results.redirect(route.getReverseRoute(UserController.class, "newUser"));
    }
    
    public Result editUser(@PathParam("id") int userId){
    	this.id=userId;
    	User user=userDao.findUserById(userId);
    	return Results.html().render("user", user);
    }
    
    @FilterWith(AuthenticityFilter.class)
    public Result update(Context context, @JSR303Validation User user, Validation validation){
    	Map<String, Object> parameterMap=Maps.newHashMap();
    	user.setId(this.id);
	    if(validation.hasViolations()){
	   		flashError(context, user);
	   		parameterMap.put("id", this.id);
	   		return Results.redirect(route.getReverseRoute(UserController.class, "editUser", parameterMap));
	   	}    	
	   	userDao.saveOrUpdate(user);
    	context.getFlashScope().put("success", "update.success");
    	return Results.redirect(route.getReverseRoute(UserController.class, "index"));
    }
    
//    @FilterWith(AuthenticityFilter.class)
//    public Result delete(Context context){
//    	user.setId(this.id);
//    	userDao.delete(user);
//    	context.getFlashScope().put("success", "delete.success");
//    	return Results.redirect(route.getReverseRoute(UserController.class, "index"));
//    }
    public Result deleted(@Param("userid") String userid) {


      

            userDao.deleteUser(userid);
            return Results.html().template("views/ApplicationController/index.ftl.html");
        

       

    }
    public Result updated(@Param("userid1") Integer userid1,
            @Param("username") String username) {
    	
    	userDao.updateitem(userid1,username);
    	return Results.html().template("views/ApplicationController/index.ftl.html");
    	
    }
    
    private void flashError(Context context, User user){
		context.getFlashScope().put("error", "missing.required");
		if(user.getName().trim().length()<3){
			context.getFlashScope().put("invalidName", "name.short");			
		}
		context.getFlashScope().put("name", user.getName());
		context.getFlashScope().put("email", user.getEmail());
	}
    
    
}
