package  com.rsba.usermicroservice.query.database

import org.springframework.stereotype.Component
import java.util.*

@Component
class UserDatabaseQuery {
    fun onLogout(input: UUID) = "SELECT on_logout_user('$input')"
}
