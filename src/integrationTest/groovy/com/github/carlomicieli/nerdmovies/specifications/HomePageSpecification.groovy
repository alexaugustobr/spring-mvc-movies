package com.github.carlomicieli.nerdmovies.specifications

import com.github.carlomicieli.nerdmovies.HomePage
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
