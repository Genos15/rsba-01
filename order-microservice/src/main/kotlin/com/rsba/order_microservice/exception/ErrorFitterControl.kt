package  com.rsba.order_microservice.exception

abstract class ErrorFitterControl {

    fun fit(message: String): Boolean {
        for (indice in indices()) {
            if (!message.contains(indice)) {
                return false;
            }
        }
        return true
    }

    abstract fun indices(): List<String>
}