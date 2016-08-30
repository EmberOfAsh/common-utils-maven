package com.zltel.common.utils.string;

/**
 * 字符串 相似程度计算 方法
 * @author Sharped
 *
 */
public class LevenshteinDistance {
	// / <summary>
	// / 取最小的一位数
	// / </summary>
	// / <param name="first"></param>
	// / <param name="second"></param>
	// / <param name="third"></param>
	// / <returns></returns>
	private int LowerOfThree(int first, int second, int third) {
		int min = Math.min(first, second);
		return Math.min(min, third);
	}

	private int Levenshtein_Distance(String str1, String str2) {
		int[][] Matrix;
		int n = str1.length();
		int m = str2.length();

		int temp = 0;
		char ch1;
		char ch2;
		int i = 0;
		int j = 0;
		if (n == 0) {
			return m;
		}
		if (m == 0) {

			return n;
		}
		Matrix = new int[n + 1][m + 1];

		for (i = 0; i <= n; i++) {
			// 初始化第一列
			Matrix[i][0] = i;
		}

		for (j = 0; j <= m; j++) {
			// 初始化第一行
			Matrix[0][j] = j;
		}

		for (i = 1; i <= n; i++) {
			ch1 = str1.charAt(i - 1);
			for (j = 1; j <= m; j++) {
				ch2 = str2.charAt(j - 1);
				if (ch1 == ch2) {
					temp = 0;
				} else {
					temp = 1;
				}
				Matrix[i][j] = LowerOfThree(Matrix[i - 1][j] + 1, Matrix[i][j - 1] + 1, Matrix[i - 1][j - 1] + temp);
			}
		}
		for (i = 0; i <= n; i++) {
			for (j = 0; j <= m; j++) {
				// System.out.println(Matrix[i][j]);
			}
		}

		return Matrix[n][m];
	}

	// / <summary>
	// / 计算字符串相似度
	// / </summary>
	// / <param name="str1"></param>
	// / <param name="str2"></param>
	// / <returns></returns>
	public int LevenshteinDistancePercent(String str1, String str2) {
		// int maxLenth = str1.Length > str2.Length ? str1.Length : str2.Length;
		int val = Levenshtein_Distance(str1, str2);
		return 100 - val * 100 / Math.max(str1.length(), str2.length());
	}

	private static LevenshteinDistance currentInstince = null;

	/**
	 * 获取 字符串 相似 百分比
	 * @param w1
	 * @param w2
	 * @return
	 */
	public static int getSamePercent(String w1, String w2) {
		if (null == currentInstince) {
			currentInstince = new LevenshteinDistance();
		}
		return currentInstince.LevenshteinDistancePercent(w1, w2);
	}

	public static void main(String[] args0) {
		String str1 = "This system only supports 667 MHz Front Side Bus Speed Processors. One or more 800 MHz Front Side Bus Speed Processors have been initialized at 667 MHz.System Halted";
		String str2 = "This system only supports 667 MHz Front Side Bus Speed Processors. One or more 800 MHz Front Side Bus Speed Processors have been initialized at 667 MHz.System Halted!";
		long time = System.currentTimeMillis();
		System.out.println(new LevenshteinDistance().LevenshteinDistancePercent(str1, str2));
		time = System.currentTimeMillis() - time;
		System.out.println(time);
	}
}
