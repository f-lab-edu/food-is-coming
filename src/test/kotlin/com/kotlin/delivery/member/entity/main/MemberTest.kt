package com.kotlin.delivery.member.entity.main

import com.kotlin.delivery.member.entity.Location
import com.kotlin.delivery.member.entity.Member
import com.kotlin.delivery.member.repository.MemberRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class MemberTest {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun test() {
        val member = Member(
            email = "gomsu1045@naver.com",
            password = "password",
            nickname = "Soo",
            mobile = "010-0000-0000",
            location = Location("안드로메다 어린왕자 512호", 150.0, 130.0),
            type = Member.Type.CUSTOMER
        )
        memberRepository.save(member)

        val dbMember = memberRepository.findById(6L).get()

        println("===========")
        println(member.id)
        println(dbMember.id)
        println(member)
        println(dbMember)
        println(member.id == dbMember.id)
        println("===========")

    }
}