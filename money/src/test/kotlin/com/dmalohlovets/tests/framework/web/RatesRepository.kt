package com.dmalohlovets.tests.framework.web

import com.dmalohlovets.tests.framework.web.pojo.Rates
import org.socialsignin.spring.data.dynamodb.repository.EnableScan
import org.springframework.data.repository.CrudRepository
import java.util.*


@EnableScan
interface RatesRepository : CrudRepository<Rates, String> {
    override fun findById(source: String): Optional<Rates>
}