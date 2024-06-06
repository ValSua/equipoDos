import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.equipodos.model.Routine
import com.example.equipodos.model.Usuario

// UsuarioViewModel.kt
class RoutineViewModel : ViewModel() {

    private val routineRepository = RoutineRepository()

    val usuario = MutableLiveData<Usuario?>()
    val exitoRegistro = MutableLiveData<Boolean>()

    fun obtenerUsuario(email: String) {
        routineRepository.obtenerUsuario(email) { usuarioEncontrado ->
            usuario.value = usuarioEncontrado
        }
    }

    fun registrarUsuario(email: String) {
        val nuevoUsuario = Usuario(email)
        routineRepository.registrarUsuario(nuevoUsuario) { exito ->
            exitoRegistro.value = exito
        }
    }

    fun registrarRutina(email: String, rutina: Routine) {
        routineRepository.registrarRutina(email, rutina) { exito ->
            exitoRegistro.value = exito
        }
    }
}
