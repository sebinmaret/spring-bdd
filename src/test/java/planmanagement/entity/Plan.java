package planmanagement.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Plan {
	private String id;
	private String name;
	private String description;
	private Float price;
	private Integer validity;
	private PLAN_STATUS status;
	private PlanRule callRule;
	private PlanRule smsRule;
	private PlanRule dataRule;
	private PlanRule ottRule;
}
