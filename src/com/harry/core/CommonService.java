package com.harry.core;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Provides means for controllers to access data from database.
 * 
 * @author harry
 * 
 */
public class CommonService {

	/* Shared by subclasses */
	protected JdbcTemplate jdbcTemplate;

	/**
	 * Injects jdbcTemplate from XML
	 * 
	 * @param jdbcTemplate
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
