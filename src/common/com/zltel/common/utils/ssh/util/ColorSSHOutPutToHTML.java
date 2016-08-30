package com.zltel.common.utils.ssh.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zltel.common.utils.string.StringUtil;

public class ColorSSHOutPutToHTML {

	public static final String doColor(String in) {
		if (StringUtil.isNullOrEmpty(in)) {
			return in;
		}
		String _msg = in;
		String rgx = "\\W\\[\\d+;(\\d+)m([\\w|.|-]+)?\\W\\[\\d*m";// 提取信息
		String rgxRep = "\\u\\d{3}B\\[\\d?m";
		Pattern p1 = Pattern.compile(rgx);
		Matcher m1 = p1.matcher(in);
		while (m1.find()) {
			String newMsg = "";
			int gc = m1.groupCount();

			if (gc >= 1) {
				String color = m1.group(1).trim();
				newMsg += "<color" + color + ">";
				if (gc >= 2) {
					String m = m1.group(2).trim();
					newMsg += m.trim();
				}
				newMsg += "</color" + color + ">";
			}
			_msg = _msg.replace(m1.group(), newMsg);
		}
		return _msg;
	}

	public static void main(String[] args) {
		String msg = "ls -lh\ntotal 691M\n-rw-------. 1 root root 2.4K Jan 28  2015 anaconda-ks.cfg\n-rwxr-xr-x. 1 root root 194M Feb  9  2015 \u001B[0m\u001B[01;32mDataViewer-2.0.bin\u001B[0m\ndrwxr-xr-x. 5 root root 4.0K Nov 17 09:49 \u001B[01;34mDesktop\u001B[0m\ndrwxr-xr-x. 2 root root 4.0K Mar  2  2015 \u001B[01;34mDocuments\u001B[0m\ndrwxr-xr-x. 2 root root 4.0K Mar  2  2015 \u001B[01;34mDownloads\u001B[0m\n-rw-r--r--. 1 root root   18 May 26 09:24 dump_6380.rdb\n-rw-r--r--. 1 root root  61K Jan 28  2015 install.log\n-rw-r--r--. 1 root root  12K Jan 28  2015 install.log.syslog\n-rw-r--r--. 1 root root   52 Nov  5 12:01 lcz.text\n-rw-r--r--. 1 root root  44M Jul 18 15:09 \u001B[01;31mMariaDB-5.5.44-rhel5-x86_64-server.rpm\u001B[0m\ndrwxr-xr-x. 2 root root 4.0K May 27 10:04 \u001B[01;34mMusic\u001B[0m\ndrwxr-xr-x. 5 root root 4.0K Nov 16 15:37 \u001B[01;34mng\u001B[0m\ndrwxr-xr-x. 2 root root 4.0K Mar  2  2015 \u001B[01;34mPictures\u001B[0m\ndrwxr-xr-x. 2 root root 4.0K Mar  2  2015 \u001B[01;34mPublic\u001B[0m\ndrwxr-xr-x. 4 root root 4.0K Feb  3  2015 \u001B[01;34mqmonitor-release-5.0.2\u001B[0m\n-rw-r--r--. 1 root root 415M Mar 12  2015 \u001B[01;31mqmonitor-release-5.0.2.zip\u001B[0m\ndrwxrwxr-x. 6 root root 4.0K Mar 13  2015 \u001B[01;34mredis-2.8.8\u001B[0m\n-rw-r--r--. 1 root root 1.1M Apr  3  2014 \u001B[01;31mredis-2.8.8.tar.gz\u001B[0m\ndrwxr-xr-x. 2 root root  12K Apr 30  2015 \u001B[01;34msplite\u001B[0m\n-rw-r--r--. 1 root root  40M Jul  6 10:36 \u001B[01;31mteamviewer_10.0.41499.i686.rpm\u001B[0m\ndrwxr-xr-x. 2 root root 4.0K Mar  2  2015 \u001B[01;34mTemplates\u001B[0m\ndrwxr-xr-x. 2 root root 4.0K Mar  2  2015 \u001B[01;34mVideos\u001B[0m\n-rw-r--r--. 1 root root  254 Jul  6 09:58 vmware.txt\n-rw-r--r--. 1 root root 2.8K Jul  6 10:15 \u001B[01;31myum.repos.d.zip\u001B[0m\n\u001B[m[root@host109 ~]# \n[root@host109 ~]# ";
		String m2 = doColor(msg);
		System.out.println(m2);
	}
}
