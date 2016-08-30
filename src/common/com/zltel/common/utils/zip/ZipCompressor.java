package com.zltel.common.utils.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.zltel.common.utils.file.FileUtil;

/**
 * 调用 Zip 压缩算法压缩目录
 * 
 * <pre>
 * 
 * String src = "D:/dataExchange/bak/";// 输入压缩路径
 * String out = "d:/zipTest.zip"; // 压缩文件保存路径
 * ZipCompressor compressor = new ZipCompressor();
 * compressor.zipCompress(src, out);
 * </pre>
 * 
 * @author Wangch
 *
 */
public class ZipCompressor {
	/**
	 * @param pathOrFile
	 *            需压缩的文件路径或文件名
	 * @param desFile
	 *            保存的文件名及路径
	 * @return 如果压缩成功返回true
	 */
	public boolean zipCompress(String pathOrFile, String desFile) {
		boolean isSuccessful = false;
		File pf = new File(pathOrFile);
		List<File> srcFiles = allSonFiles(pathOrFile);
		String[] fileNames = new String[srcFiles.size()];
		for (int i = 0; i < srcFiles.size(); i++) {
			if (FileUtil.isFileAndExists(pathOrFile)) {
				fileNames[i] = parse(srcFiles.get(i).getPath());
			} else {
				fileNames[i] = parse(srcFiles.get(i).getPath(), pf.getPath());
			}
		}
		ZipOutputStream zos = null;
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(desFile));
			zos = new ZipOutputStream(bos);
			String entryName = null;

			for (int i = 0; i < fileNames.length; i++) {
				entryName = fileNames[i];

				// 创建Zip条目
				ZipEntry entry = new ZipEntry(entryName);
				zos.putNextEntry(entry);

				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFiles.get(i)));

				byte[] b = new byte[1024];
				while (bis.read(b, 0, 1024) != -1) {
					zos.write(b, 0, 1024);
				}
				bis.close();
				zos.closeEntry();
			}

			zos.flush();
			isSuccessful = true;
		} catch (IOException e) {
		} finally {
			try {
				if (zos != null)
					zos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (bos != null)
					bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return isSuccessful;
	}

	private List<File> allSonFiles(String pathOrFile) {
		return FileUtil.getFiles(new File(pathOrFile));
	}

	// 解析文件名
	private String parse(String srcFile) {
		int location = srcFile.lastIndexOf("/");
		String fileName = srcFile.substring(location + 1);
		return fileName;
	}

	// 解析文件名
	private String parse(String srcFile, String basePath) {
		return srcFile.replace(basePath + File.separator, "");
	}
}
