package vttp2022.ssf.POAssessment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import vttp2022.ssf.POAssessment.model.Quotation;
import vttp2022.ssf.POAssessment.services.QuotationService;

@SpringBootTest
class PoAssessmentApplicationTests {

	@Autowired
	QuotationService quoteSvc;
	
	@Test
	void contextLoads() {

		List<String> toTest = new ArrayList<>();
		toTest.add("durian");
		toTest.add("plum");
		toTest.add("pear");

		Optional<Quotation> opt = quoteSvc.getQuotations(toTest);

		Assertions.assertTrue(opt.isEmpty());

	}

}
