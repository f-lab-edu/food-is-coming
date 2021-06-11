package com.kotlin.delivery.model.entity.member

import com.kotlin.delivery.MemberRepository
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
            nickname = "Soo",
            mobile = "010-0000-0000",
            location = Location("안드로메다 어린왕자 512호", 150.0, 130.0),
            profileImage = ProfileImage(imageName = null, imagePath = null),
            type = Member.Type.CUSTOMER
        )
        memberRepository.save(member)

        val dbMember = memberRepository.findById(3L).get()

        println("===========")
        println(member.id)
        println(dbMember.id)
        println(member)
        println(dbMember)
        println(member.id == dbMember.id)
        println("===========")

    }
}