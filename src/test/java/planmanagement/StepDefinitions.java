package planmanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import planmanagement.entity.Plan;
import planmanagement.entity.PlanRule;
import planmanagement.entity.SERVICE_TYPE;

public class StepDefinitions {
	private List<PlanRule> planRuleList;
	private List<Plan> planList;
	private Plan plan;
	private PlanRule planRule;
	private ResponseEntity<?> response;
	private final RestTemplate restTemplate = new RestTemplate();

	@When("user sends GET request to the api {string}")
	public void userSendGETRequest(String endpoint) {
		if (endpoint.startsWith("/api/v1/plan-management/rule")) {
			planRuleList = Arrays
					.asList(restTemplate.getForEntity("http://localhost:8082" + endpoint, PlanRule[].class).getBody());
		} else if (endpoint.startsWith("/api/v1/plan-management/plan")) {
			planList = Arrays
					.asList(restTemplate.getForEntity("http://localhost:8082" + endpoint, Plan[].class).getBody());
		}

	}

	@Then("list of plan rules are returned")
	public void expectListOfPlanRules() {
		assertNotNull(planRuleList);
		if (planRuleList.size() > 0) {
			assertEquals(PlanRule.class, planRuleList.get(0).getClass());
		}
	}

	@Then("list of plans are returned")
	public void expectListOfPlans() {
		assertNotNull(planList);
		if (planList.size() > 0) {
			assertEquals(Plan.class, planList.get(0).getClass());
		}
	}

	@When("user sends GET request to {string}")
	public void user_send_get_request_for_plan_with_id(String endpoint) {
		if (endpoint.contains("plan-management/plan")) {
			plan = restTemplate.getForEntity("http://localhost:8082" + endpoint, Plan.class).getBody();
		} else {
			planRule = restTemplate.getForEntity("http://localhost:8082" + endpoint, PlanRule.class).getBody();
		}

	}

	@Then("a plan is returned")
	public void plan_is_returned() {
		assertNotNull(plan);
		assertEquals(Plan.class, plan.getClass());
	}

	@And("the returned plan has the name {string}")
	public void returned_plan_has_name(String name) {
		assertEquals(name, plan.getName());
	}

	@Then("a plan rule is returned")
	public void plan_rule_is_returned() {
		assertNotNull(planRule);
		assertEquals(PlanRule.class, planRule.getClass());
	}

	@And("the returned plan rule has the name {string}")
	public void returned_plan_rule_has_name(String name) {
		assertEquals(name, planRule.getName());
	}

	@And("list of plan rules contains rules for {string} service")
	public void list_of_rules_contains_call_rules(String serviceType) {
		if (planRuleList.size() > 0) {
			assertEquals(SERVICE_TYPE.valueOf(serviceType), planRuleList.get(0).getServiceType());
		}
	}

	@And("list of plan rules contains rules with validty greater than or equal to {int}")
	public void list_of_rules_contains_rules_with_validity_greater_or_equal(Integer validity) {
		if (planRuleList.size() > 0) {
			assertTrue(planRuleList.get(0).getValidity() >= 1);
		}
	}

	@When("user selects create rule")
	public void user_create_rule() {
		planRule = new PlanRule();
	}

	@And("user enters rule name as {string}")
	public void plan_rule_add_name(String name) {
		planRule.setName(name);
	}

	@And("user enters rule allowance as {}")
	public void plan_rule_add_allowance(Integer allowance) {
		planRule.setAllowance((float) allowance);
	}

	@And("user enters rule service type as {string}")
	public void plan_rule_add_service(String service) {
		planRule.setServiceType(SERVICE_TYPE.valueOf(service));
	}

	@And("user enters rule validity as {int}")
	public void plan_rule_add_validity(Integer validity) {
		planRule.setValidity(validity);
	}

	@And("user enters rule cost as {int}")
	public void plan_rule_add_cost(Integer cost) {
		planRule.setCost((float) cost);
	}

	@And("user sends POST request to {string}")
	public void plan_rule_send_post(String endpoint) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		if (endpoint.startsWith("/api/v1/plan-management/rule")) {
			HttpEntity<PlanRule> request = new HttpEntity<>(planRule, headers);
			response = restTemplate.postForEntity("http://localhost:8082" + endpoint, request, String.class);
		} else if (endpoint.startsWith("/api/v1/plan-management/plan")) {
			HttpEntity<Plan> request = new HttpEntity<>(plan, headers);
			response = restTemplate.postForEntity("http://localhost:8082" + endpoint, request, String.class);
		}

	}

	@Then("OK response is obtained")
	public void response_is_OK() {
		assertEquals(true, response.getStatusCode().is2xxSuccessful());
	}

	@And("a plan rule is returned and added to a new plan")
	public void plan_rule_added_to_plan() {
		plan = new Plan();
		plan.setDataRule(planRule);
	}

	@And("user enters plan name as {string}")
	public void plan_add_name(String name) {
		plan.setName(name);
	}

	@And("user enters plan description as {string}")
	public void plan_add_description(String description) {
		plan.setDescription(description);
	}

	@And("user enters plan price as {int}")
	public void plan_add_price(Integer price) {
		plan.setPrice((float) price);
	}

	@And("user enters plan validity as {int}")
	public void plan_add_validity(Integer validity) {
		plan.setValidity(validity);
	}

}
