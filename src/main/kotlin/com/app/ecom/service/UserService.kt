package com.app.ecom.service

import com.app.ecom.dto.AddressDto
import com.app.ecom.dto.UserRequestDto
import com.app.ecom.dto.UserResponseDto
import com.app.ecom.model.Address
import com.app.ecom.repositories.UserRepository
import com.app.ecom.model.User
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
    private val userRepository: UserRepository
) {

    @Transactional(readOnly = true)
    fun getAllUsers(): List<UserResponseDto> {
        return userRepository.findAll().map { it.toUserResponseDto() }
    }

    @Transactional(readOnly = true)
    fun getUserById(id: Long): UserResponseDto? {
        return userRepository.findByIdOrNull(id)?.toUserResponseDto()
    }

    // [Create] DTO -> Entity に変換
    @Transactional
    fun addUser(userRequest: UserRequestDto) {
        val user = userRequest.toEntity()
        userRepository.save(user)
    }

    // [Update] DTO -> Entity に変換
    @Transactional
    fun updateUser(id: Long, userRequest: UserRequestDto): Boolean {
        val existingUser = userRepository.findByIdOrNull(id)

        if(existingUser != null) {
            performPartialUpdate(existingUser, userRequest)
            return true
        }
        return false
    }

    // --- マッパー　（変換ロジック）　---
    // Entity -> ResponseDto に変換
    private fun User.toUserResponseDto(): UserResponseDto {
        val addressDto = this.address?.let {
            AddressDto(it.street, it.city, it.state, it.country, it.zipCode)
        }
        return UserResponseDto(
            id = this.id,
            firstName = this.firstName,
            lastName = this.lastName,
            email = this.email,
            phone = this.phone,
            role = this.role,
            address = addressDto
        )
    }

    // RequestDto -> Entity に変換
    private fun UserRequestDto.toEntity(): User {
        val addressEntity = this.address?.let {
            Address(
                street = it.street,
                city = it.city,
                state = it.state,
                country = it.country,
                zipCode = it.zipCode
            )
        }
        return User(
            firstName = this.firstName!!,
            lastName = this.lastName!!,
            email = this.email,
            phone = this.phone,
            address = addressEntity
        )
    }

    /**
     * 実際の更新処理を行うprivateヘルパー関数
      */
    private fun performPartialUpdate(
        existingUser: User,
        userRequest: UserRequestDto
    ) {
        // --- addressの部分変更ロジック ---
        val updatedAddress = userRequest.address?.let { addressDto ->
            val baseAddress = existingUser.address?:Address()
            baseAddress.copy(
                street = addressDto.street?:baseAddress.street,
                city = addressDto.city?:baseAddress.city,
                state = addressDto.state?:baseAddress.state,
                country = addressDto.country?:baseAddress.country,
                zipCode = addressDto.zipCode?:baseAddress.zipCode
            )
        } ?: existingUser.address
        // --- Userの部分変更ロジック ---
        val updatedUser = existingUser.copy(
            firstName = userRequest.firstName?:existingUser.firstName,
            lastName = userRequest.lastName?:existingUser.lastName,
            email = userRequest.email?:existingUser.email,
            phone = userRequest.phone?:existingUser.phone,
            address = updatedAddress
        )
        userRepository.save(updatedUser)
    }

}