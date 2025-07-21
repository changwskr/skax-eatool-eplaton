package com.skax.eatool.mbc.dc.runtimedc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Vector;

import com.skax.eatool.ksa.exception.NewBusinessException;
import com.skax.eatool.ksa.logger.NewIKesaLogger;
import com.skax.eatool.ksa.logger.NewKesaLoggerFactory;
import com.skax.eatool.ksa.oltp.biz.NewIDomainComponent;
import com.skax.eatool.mbc.dc.runtimedc.dto.ResourceCopyDDTO;

public class DCResourceCopy implements NewIDomainComponent {

	protected NewIKesaLogger logger = NewKesaLoggerFactory.getLogger("log.ifrs.mbc");
	Process process = null;
	ProcessInputStream in = null;
	ProcessInputStream error = null;

	public List getListResourceInfo(ResourceCopyDDTO rsrcCopyDDTO) throws NewBusinessException {

		List resultList = new Vector();
		StringBuffer logBuf = rsrcCopyDDTO.getLogBuffer();
		StringBuffer resultBuf = new StringBuffer();
		long time = System.currentTimeMillis();
		try {
			String cmd = "/fscm/SRC/fsapp/ifr_dev/script/f_t.sh " + rsrcCopyDDTO.getUapplDstcd() + "  "
					+ rsrcCopyDDTO.getCmpoIdnfr() + "  " + rsrcCopyDDTO.getFileIdnfr() + "  "
					+ rsrcCopyDDTO.getKywrCtnt();

			logBuf.append("star time     :" + new java.util.Date() + "\n");

			logBuf.append("command       :" + cmd + "\n");

			logBuf.append("command output:\n");
			logBuf.append("--------------------------------------------------------------------------------\n");

			process = Runtime.getRuntime().exec(cmd);
			in = new ProcessInputStream(process.getInputStream(), resultBuf, logBuf);
			in.start();
			error = new ProcessInputStream(process.getErrorStream(), new StringBuffer(), logBuf);
			error.start();
			int count = 0;
			while (!isDone(logBuf) || count <= 10) {
				// Thread.currentThread().sleep(500);
				count++;
			}
			if (count == 10) {
				in.interrupt();
				error.interrupt();
			}
			process.destroy();
		} catch (IOException ioe) {
			logBuf.append(stackTraceToString(ioe) + "\n");
		} finally {

			// resultBuf processing
			String resultStr = resultBuf.toString();
			String[] resultArray = resultStr.split("#");
			String[] rowArray = null;

			String uapplDstcd = null;
			String cmpoIdnfr = null;
			String fileIdnfr = null;
			String kywrCtnt = null;
			String originfilePathName = null;
			String originfileSize = null;
			String dsttfilePathName = null;
			String dsttfileSize = null;

			ResourceCopyDDTO rsrcCpDDTO = null;

			for (int i = 0; i < resultArray.length; i++) {

				rsrcCopyDDTO = new ResourceCopyDDTO();

				uapplDstcd = rsrcCopyDDTO.getUapplDstcd();
				cmpoIdnfr = rsrcCopyDDTO.getCmpoIdnfr();
				fileIdnfr = rsrcCopyDDTO.getFileIdnfr();
				kywrCtnt = rsrcCopyDDTO.getKywrCtnt();
				if (kywrCtnt == null || kywrCtnt.trim().equals("")) {
					kywrCtnt = "all";
				}

				originfilePathName = rowArray[0];
				originfileSize = rowArray[1];
				dsttfilePathName = rowArray[3];
				dsttfileSize = rowArray[2];

				rowArray = resultArray[i].split(",");

				if (!resultArray[0].startsWith("/fscm")) {
					continue;
				}

				rsrcCpDDTO.setUapplDstcd(uapplDstcd);
				rsrcCpDDTO.setCmpoIdnfr(cmpoIdnfr);
				rsrcCpDDTO.setFileIdnfr(fileIdnfr);
				rsrcCpDDTO.setKywrCtnt(kywrCtnt);
				rsrcCpDDTO.setOriginfilePathName(originfilePathName);
				rsrcCpDDTO.setOriginfileSize(originfileSize);
				rsrcCpDDTO.setDsttfilePathName(dsttfilePathName);
				rsrcCpDDTO.setDsttfileSize(dsttfileSize);

				resultList.add(rsrcCpDDTO);
			}

		}
		logBuf.append("--------------------------------------------------------------------------------\n");
		logBuf.append("elaspsed time :" + (System.currentTimeMillis() - time) / 1000f + "\n");

		return resultList;
	}

	public void copyResource(ResourceCopyDDTO[] rsrcCopyDDTOs) throws NewBusinessException {

		ResourceCopyDDTO rsrcCopyDDTO = null;
		String srcFile = null;
		String tgtFile = null;

		StringBuffer logBuf = rsrcCopyDDTO.getLogBuffer();
		long time = System.currentTimeMillis();

		logBuf.append("star time     :" + new java.util.Date() + "\n");
		logBuf.append("copyResource Start...\n");
		logBuf.append("--------------------------------------------------------------------------------\n");

		for (int i = 0; i < rsrcCopyDDTOs.length; i++) {
			rsrcCopyDDTO = rsrcCopyDDTOs[i];
			srcFile = rsrcCopyDDTO.getOriginfilePathName();
			tgtFile = rsrcCopyDDTO.getDsttfilePathName();
			// File copy
			boolean bResult = copyFile(srcFile, tgtFile);

			logBuf.append(" copy (" + srcFile + ") => (" + tgtFile + ")");

			if (bResult) {
				logBuf.append(" : success.\n");
			} else {
				logBuf.append(" : fail.\n");
			}
		}

		logBuf.append("--------------------------------------------------------------------------------\n");
		logBuf.append("elaspsed time :" + (System.currentTimeMillis() - time) / 1000f + "\n");
	}

	public boolean copyFile(String szOriginFileName, String szCopyFileName) {
		if (szOriginFileName == null || szCopyFileName == null || szCopyFileName.length() < 1) {
			return false;
		}
		char delim = '/';

		int idx = szCopyFileName.lastIndexOf(delim);
		if (idx == -1 || idx == 0) {
			idx = szCopyFileName.length();
		}
		File copyFullPath = new File(szCopyFileName.substring(0, idx));
		byte[] buf = new byte[1024];

		if (!copyFullPath.exists()) {
			if (!copyFullPath.mkdirs()) {
				return false;
			}
		}

		try {
			FileInputStream fin = new FileInputStream(szOriginFileName);
			FileOutputStream fout = new FileOutputStream(szCopyFileName);
			for (int iBytesThisTime = fin.read(buf); iBytesThisTime > 0; iBytesThisTime = fin.read(buf)) {
				fout.write(buf, 0, iBytesThisTime);
			}
			fin.close();
			fout.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean isDone(StringBuffer logBuf) {
		try {
			process.waitFor();
		} catch (Exception e) {
			logBuf.append(stackTraceToString(e) + "\n");
		}
		return in.isDone() && error.isDone();
	}

	public String stackTraceToString(Throwable throwable) {
		StringWriter sw = new StringWriter();
		throwable.printStackTrace(new PrintWriter(sw, true));
		return sw.toString();
	}
}

class ProcessInputStream extends Thread {
	InputStream in;
	private boolean done = true;
	StringBuffer resultBuf;
	StringBuffer logBuf;

	public ProcessInputStream(InputStream in, StringBuffer resultBuf, StringBuffer logBuf) {
		this.in = in;
		this.resultBuf = resultBuf;
		this.logBuf = logBuf;
		setDaemon(true);
	}

	public void run() {
		done = false;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = reader.readLine()) != null) {
				resultBuf.append(line + "#");
				logBuf.append("\t" + line + "\n");
			}
		} catch (Exception e) {
			logBuf.append(stackTraceToString(e) + "\n");
		}
		done = true;
	}

	public boolean isDone() {
		return done;
	}

	public String stackTraceToString(Throwable throwable) {
		StringWriter sw = new StringWriter();
		throwable.printStackTrace(new PrintWriter(sw, true));
		return sw.toString();
	}

}
