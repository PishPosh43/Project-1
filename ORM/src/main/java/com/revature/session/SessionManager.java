package com.revature.session;

import java.sql.SQLException;

import com.revature.util.Configuration;

public class SessionManager {
	
	private Configuration cfg;
	
	public SessionManager(Configuration cfg) {
		this.cfg = cfg;
	}
		
	public Session openSession() {
		return new Session(Configuration.getConnection());
	}
	
	public Session closeSession() {
		return null;
	}

}