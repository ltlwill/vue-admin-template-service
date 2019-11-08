package com.efe.ms.bankservice.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcelImpDetail extends BaseModel {
	
	private Long impId;
	private String accountName;
	private String accountNo;
	private String bankName;
	private String bankNo;
	private String transactionDate;
	private String transactionTime;
	private Double income;
	private Double expend;
	
	private String userId;
	private String userName;
	private Date importTime;
	
}
