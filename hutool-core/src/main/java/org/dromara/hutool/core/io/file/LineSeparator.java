/*
 * Copyright (c) 2023 looly(loolly@aliyun.com)
 * Hutool is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *          https://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */

package org.dromara.hutool.core.io.file;

/**
 * 换行符枚举<br>
 * 换行符包括：
 * <pre>
 * Mac系统换行符："\r"
 * Linux系统换行符："\n"
 * Windows系统换行符："\r\n"
 * </pre>
 *
 * @author Looly
 * @see #MAC
 * @see #LINUX
 * @see #WINDOWS
 * @since 3.1.0
 */
public enum LineSeparator {

	/**
	 * Mac系统换行符："\r"
	 */
	MAC("\r"),
	/**
	 * Linux系统换行符："\n"
	 */
	LINUX("\n"),
	/**
	 * Windows系统换行符："\r\n"
	 */
	WINDOWS("\r\n");

	private final String value;

	/**
	 * 构造
	 *
	 * @param lineSeparator 换行符
	 */
	LineSeparator(final String lineSeparator) {
		this.value = lineSeparator;
	}

	/**
	 * 获取换行符值
	 *
	 * @return 值
	 */
	public String getValue() {
		return this.value;
	}
}
