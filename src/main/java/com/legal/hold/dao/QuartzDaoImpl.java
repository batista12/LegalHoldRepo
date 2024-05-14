package com.legal.hold.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class QuartzDaoImpl implements QuartzDao {

	@Autowired
	DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void populateO365Data(String file) throws SQLException, IOException {
		String delQuery = "delete from legal_hold_list";
		String insQuery = "insert into legal_hold_list (name,ssoid,email,matter_status,matter_name,matter_identifier,matter_security_group,attorney,legal_assistant_contact,legal_assistant_email,matter_key,silent_custodian) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		List<String> resultList = Files.lines(Paths.get(file)).collect(Collectors.toList());
		ArrayList<String> alnlist = new ArrayList<String>();
		ArrayList<String> alsclist = new ArrayList<String>();
		try {
			for (int j = 0; j < resultList.size(); j++) {
				String[] arrayD = resultList.get(j).split("\\|");
				alnlist.add(arrayD[0]); // List of Names
				alsclist.add(arrayD[11]); // List of SilentCustodians
			}
		} catch (IndexOutOfBoundsException e1) {
			System.out.println("IndexOutOfBoundsException => " + e1.getMessage());
		}
		if (alnlist.size() == alsclist.size()) {
			jdbcTemplate.execute(delQuery);
			System.out.println("DB Table Cleared");
			System.out.println("Insertion Started");
			jdbcTemplate.batchUpdate(insQuery, new BatchPreparedStatementSetter() {
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					String[] arrayData = resultList.get(i).split("\\|");
					ps.setString(1, arrayData[0]);
					ps.setString(2, arrayData[1]);
					ps.setString(3, arrayData[2]);
					ps.setString(4, arrayData[3]);
					ps.setString(5, arrayData[4]);
					ps.setString(6, arrayData[5]);
					ps.setString(7, arrayData[6]);
					ps.setString(8, arrayData[7]);
					ps.setString(9, arrayData[8]);
					ps.setString(10, arrayData[9]);
					ps.setString(11, arrayData[10]);
					ps.setString(12, arrayData[11]);
				}

				public int getBatchSize() {
					return resultList.size();
				}
			});
			System.out.println("Insertion Completed - No of Records Inserted : " + alnlist.size());
		} else {
			System.out.println("Please check the O365.DAT File for Discrepancy");
		}
	}
}
