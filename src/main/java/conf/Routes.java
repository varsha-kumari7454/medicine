/**
 * Copyright (C) 2012-2015 the original author or authors.
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

package conf;

import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;

import controllers.ApplicationController;
import controllers.LoginLogoutController;
import controllers.UserController;

public class Routes implements ApplicationRoutes {
    
    @Inject
    NinjaProperties ninjaProperties;

    /**
     * Using a (almost) nice DSL we can configure the router.
     * 
     * The second argument NinjaModuleDemoRouter contains all routes of a
     * submodule. By simply injecting it we activate the routes.
     * 
     * @param router
     *            The default router of this application
     */
    @Override
    public void init(Router router) {  
    	//route to landing page
    	router.GET().route("/").with(ApplicationController.class, "index");
    	 router.GET().route("/login").with(LoginLogoutController::login);
         router.POST().route("/login").with(LoginLogoutController::loginPost);
    	router.GET().route("/user").with(UserController.class, "index");
    	router.GET().route("/user/new").with(UserController.class, "newUser");
    	router.POST().route("/user/new").with(UserController.class, "create");
    	router.GET().route("/user/edit/{id}").with(UserController.class, "editUser");
//    	router.POST().route("/user/update").with(UserController.class, "update");
//    	router.POST().route("/user/delete").with(UserController.class, "delete");
    	 router.GET().route("/user/delete").with(UserController.class,"deleted");
    	 router.POST().route("/user/update").with(UserController.class, "updated");
        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController.class, "serveWebJars");
        router.GET().route("/assets/{fileName: .*}").with(AssetsController.class, "serveStatic");
        
		///////////////////////////////////////////////////////////////////////
		// Index / Catchall shows index page, leave this at the end load this after all resources are loaded
		///////////////////////////////////////////////////////////////////////
		router.GET().route("/.*").with(ApplicationController.class, "index");
    }

}
