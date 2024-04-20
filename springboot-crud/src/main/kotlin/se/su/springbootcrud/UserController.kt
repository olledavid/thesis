package se.su.springbootcrud

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.view.RedirectView
import se.su.springbootcrud.user.User
import se.su.springbootcrud.user.UserDao

@RestController
class UserController(private val userDao: UserDao) {

    @GetMapping("/users")
    fun getAllUsers() = userDao.users.values

    @GetMapping("/")
    fun handleRoot(): RedirectView {
        return RedirectView("/users")
    }

    @GetMapping("users/{id}")
    fun getUserById(@PathVariable id: Int) = userDao.findById(id)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")

    @GetMapping("users/email/{email}")
    fun getUserByEmail(@PathVariable email: String) = userDao.findByEmail(email)
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody user: User) {
        userDao.save(user.name, user.email)
    }

    @PatchMapping("users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateUser(@PathVariable id: Int, @RequestBody user: User) {
        userDao.update(id, user)
    }

    @DeleteMapping("users/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable id: Int) {
        userDao.delete(id)
    }
}