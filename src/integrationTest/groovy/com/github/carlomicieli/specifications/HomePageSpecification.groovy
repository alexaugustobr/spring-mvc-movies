package com.github.carlomicieli.specifications

import com.github.carlomicieli.HomePage
import geb.spock.GebSpec;
import geb.spock.GebSpec

class HomePageSpecification extends GebSpec {
	def "go to add page"() {
		when:
			to HomePage

		then:
			title == "Spring Movies"
	}
}
