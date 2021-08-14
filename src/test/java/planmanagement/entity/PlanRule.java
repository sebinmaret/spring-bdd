package planmanagement.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlanRule {

	private String id;
	private String name;
	private Float allowance;
	private Integer validity;
	private SERVICE_TYPE serviceType;
	private String ottServiceId;
	private Float cost;

}
