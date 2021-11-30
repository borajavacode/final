package kr.or.table.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address 
{
	private int addrNo;
	private int memberNo;
	private String receiverName;
	private String receiverPhone;
	private String addr;
}
