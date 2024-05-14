package com.legal.hold.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.legal.hold.dao.QuartzDao;

@Service
public class QuartzJobService {

	@Value("${globalscape.hostName}")
	private String gsHost;

	@Value("${globalscape.port}")
	private int port;

	@Value("${globalscape.userName}")
	private String userName;

	@Value("${globalscape.keyPath}")
	private String keyPath;

	@Value("${globalscape.remotePath}")
	private String remotePath;

	@Value("${globalscape.fileName}")
	private String fileName;

	@Value("${globalscape.filePath}")
	private String filePath;

	@Autowired
	private QuartzDao quartzDao;

	public void doStartJob() throws Exception {
		try {
			String file = sftpGet(); // Get file from SFTP folder
			quartzDao.populateO365Data(file);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public String sftpGet() throws JSchException, SftpException {
		String file;
		final Session session = getJSchSession();
		session.connect();
		final Channel channel = session.openChannel("sftp");
		channel.connect();
		final ChannelSftp sftpChannel = (ChannelSftp) channel;
		sftpChannel.cd(remotePath);
		file = filePath + fileName;
		sftpChannel.get(fileName, file);
		sftpChannel.exit();
		session.disconnect();
		return file;
	}

	private Session getJSchSession() throws JSchException {
		final JSch jsch = new JSch();
		final Session session = jsch.getSession(userName, gsHost, port);
		jsch.addIdentity(keyPath);
		final java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		session.setConfig(config);
		return session;
	}
}
