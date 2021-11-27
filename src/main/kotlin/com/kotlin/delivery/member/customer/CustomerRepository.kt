package com.kotlin.delivery.member.customer

import com.kotlin.delivery.member.common.repository.MemberRepository

interface CustomerRepository : MemberRepository<Customer, Long>
