package se.su.springbootcrud.user

import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

@Repository
class UserDao {

    // Use ConcurrentHashMap for thread-safe operations
    val users = ConcurrentHashMap<Int, User>(
        mapOf(
            0 to User(name = "Alice", email = "alice@alice.kt", id = 0),
            1 to User(name = "Bob", email = "bob@bob.kt", id = 1),
            2 to User(name = "Carol", email = "carol@carol.kt", id = 2),
            3 to User(name = "Dave", email = "dave@dave.kt", id = 3)
        )
    )
    var lastId: AtomicInteger = AtomicInteger(users.size - 1)

    fun save(name: String, email: String) {
        val id = lastId.incrementAndGet()
        users[id] = User(name = name, email = email, id = id)
    }

    fun findById(id: Int): User? {
        return users[id]
    }

    fun findByEmail(email: String): User? {
        return users.values.find { it.email == email }
    }

    fun update(id: Int, user: User) {
        users[id] = User(name = user.name, email = user.email, id = id)
    }

    fun delete(id: Int) {
        users.remove(id)
    }

}