package com.legal.hold.dao;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

@Repository
public interface QuartzDao {

	public void populateO365Data(String file) throws SQLException, IOException;

}
