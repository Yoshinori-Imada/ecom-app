package com.app.ecom.controller

import com.app.ecom.dto.UserRequestDto
import com.app.ecom.dto.UserResponseDto
import com.app.ecom.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController (
    private val userService: UserService
) {

    // URL: http://localhost:8080/api/users
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserResponseDto>> {
        val users = userService.getAllUsers()
        // ステータス200OKと共にデータを返す
        return ResponseEntity.ok(users)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserResponseDto> {
        return userService.getUserById(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createUsers(
        @RequestBody
        userRequest: UserRequestDto
    ): ResponseEntity<String> {
        userService.addUser(userRequest)
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("User added successfully")
    }

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody userRequest: UserRequestDto
    ): ResponseEntity<String> {
        val isSuccess = userService.updateUser(id, userRequest)

        return if (isSuccess) {
            ResponseEntity.ok("User updated successfully")
            } else {
            ResponseEntity.notFound().build()
        }
    }
}