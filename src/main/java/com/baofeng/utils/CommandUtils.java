package com.baofeng.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandUtils {

	private static final Logger logger = LoggerFactory.getLogger(CommandUtils.class);

	/** 执行下载 */
	public static String execDownload(String command) {
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;
		try {
			logger.info(Thread.currentThread().getName() + "开始执行linux指令:" + command);
			Process exec = Runtime.getRuntime().exec(command);
			inputStream = exec.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			bufferedReader = new BufferedReader(inputStreamReader);
			String line = "";
			StringBuilder buff = new StringBuilder();
			while ((line = bufferedReader.readLine()) != null) {
				if (line != null && line.length() > 0) {
					line = bufferedReader.readLine();
					buff.append(line);
				}
			}
			int rs = exec.waitFor();
			if (rs == 0) {
				return null;
			} else {
				logger.info(Thread.currentThread().getName() + "指令返回值" + rs + "输出信息：" + buff.toString());
				return buff.toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/** 视频转码 */
	public static boolean runConsole(String inFile, String outfile, Integer bitrate, Integer autoWidth, String fileLog) {
		String ffmpeg = CommandUtils.class.getResource("/ffmpeg.sh").getFile();
		String commands = String.format("sh " + ffmpeg + " %s %s %s %s %s", inFile, outfile, bitrate, autoWidth, fileLog);
		logger.info(Thread.currentThread().getName() + "开始执行脚本命令:" + commands);
		String[] result = CommandUtils.execShell(commands);
		logger.info(Thread.currentThread().getName() + "执行脚本命令结果:" + Arrays.toString(result));
		if (result != null && result.length > 0) {
			if(Integer.valueOf(0).intValue() == Integer.valueOf(result[0]).intValue())
				return true;
		}
		return false;
	}

	/** 执行脚本 */
	public static String[] execShell(String commands) {
		Process exec = null;
		InputStream inStream = null;
		InputStreamReader inReader = null;
		BufferedReader buReader = null;
		List<String> pids = new ArrayList<String>();
		try {
			exec = Runtime.getRuntime().exec(commands);
			inStream = exec.getInputStream();
			inReader = new InputStreamReader(inStream);
			buReader = new BufferedReader(inReader);
			String line = String.valueOf("");
			while ((line = buReader.readLine()) != null) {
				if (line != null && line.trim().length() > 0) {
					pids.add(line);
				}
			}
			int result = exec.waitFor();
			if (result == 0) {
				return pids.toArray(new String[pids.size()]);
			} else {
				logger.info("执行" + commands + ",返回：" + result);
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			close(buReader);
			close(inReader);
			close(inStream);
			exec.destroy();
		}
		return pids.toArray(new String[pids.size()]);
	}

	/** 执行命令 */
	public static boolean execCommand(String commands) {
		if (System.getProperty("os.name").equals("Linux")) {
			Process process = null;
			try {
				process = Runtime.getRuntime().exec(commands);
				int result = process.waitFor();
				if (Integer.valueOf(0).intValue() == result) {
					return true;
				}
			} catch (Exception e) {
				logger.error("执行命令失败", e);
			} finally {
				process.destroy();
			}
		}
		return false;
	}

	/** 终止进程 */
	public static boolean killCommand(String chars) {
		String psaux = CommandUtils.class.getResource("/psaux.sh").getFile();
		String commands = String.format("%s %s", psaux, chars);
		String[] pid = CommandUtils.execShell(commands);
		if (pid != null && pid.length > 0) {
			for (String $id : pid) {
				String command = String.format("kill -9 %s", $id);
				if (CommandUtils.execCommand(command)) {
					logger.info("执行终止" + chars + "任务进程成功!");
				}
			}
			return true;
		}
		return false;
	}

	/** 关流对像 */
	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (IOException e) {
				logger.error("执行脚本关闭失败", e);
			}
		}
	}
}
