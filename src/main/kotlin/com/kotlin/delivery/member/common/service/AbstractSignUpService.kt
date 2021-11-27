package com.kotlin.delivery.member.common.service

import com.kotlin.delivery.common.entity.Member
import com.kotlin.delivery.member.common.dto.SignUpRequest
import com.kotlin.delivery.member.common.util.MemberType
import com.kotlin.delivery.member.common.repository.MemberRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.reflect.KClass

abstract class AbstractSignUpService<T : Member, R : MemberRepository<T, Long>> {

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder
    /**
     * 공통 회원가입을 처리하는 메소드
     * 회원 종류(Customer, Rider, Owner)에 상관 없이 공통 포맷을 가지고 최소한의 중복으로 회원가입을 처리하기 위해 노력...
     *  1. 회원 종류에 맞는 repository 를 결정한다.
     *  2. 회원 종류에 맞는 entity 를 생성한다.
     */
    fun signUp(req: SignUpRequest, type: MemberType, repositoryClass: KClass<R>) {
        val repository = decideRepository(repositoryClass)
        if (repository.existsByEmail(req.email)) {
            throw DuplicateKeyException("동일한 이메일로 가입된 사용자가 이미 존재합니다.")
        }

        val common = Member(req, passwordEncoder.encode(req.password), type)
        val entity = createTypeEntity(common)
        repository.save(entity)
    }

    abstract fun decideRepository(repositoryClass: KClass<R>): R

    abstract fun createTypeEntity(common: Member): T
}
