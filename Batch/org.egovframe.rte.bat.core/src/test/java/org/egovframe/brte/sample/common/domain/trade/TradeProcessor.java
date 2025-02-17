/*
 * Copyright 2006-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.egovframe.brte.sample.common.domain.trade;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

/**
 * TradeProcessor
 * 
 * @author 배치실행개발팀
 * @since 2012. 07.25
 * @version 1.0
 * @see <pre>
 *      개정이력(Modification Information)
 *   
 *   수정일      수정자           수정내용
 *  ------- -------- ---------------------------
 *  2012. 07.25  배치실행개발팀     최초 생성
 *  </pre>
 */

public class TradeProcessor implements ItemProcessor<Trade, Trade> {

	private int failure = -1;

	private int index = 0;

	private Trade failedItem = null;

	/**
	 * Public setter for the the index on which failure should occur.
	 * 
	 * @param failure the failure to set
	 */
	public void setValidationFailure(int failure) {
		this.failure = failure;
	}

	public Trade process(Trade item) throws Exception {
		if ((failedItem == null && index++ == failure) || (failedItem != null && failedItem.equals(item))) {
			failedItem = item;
			throw new ValidationException("Some bad data for " + failedItem);
		}
		return item;
	}
}
