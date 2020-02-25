/**
 * 
 */
package com.keystore.data.error;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lokeshkannadb
 *
 */
@Getter
@Setter
@ToString
@Component
public class ErrorDetails {

	String errorCode;
	String errorDesc;

}
