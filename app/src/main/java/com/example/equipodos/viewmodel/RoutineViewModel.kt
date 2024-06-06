import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.equipodos.model.Routine
import com.example.equipodos.model.Usuario
import com.example.equipodos.repository.RoutineRepository

// UsuarioViewModel.kt
class RoutineViewModel : ViewModel() {

    private val routineRepository = RoutineRepository()
    val exitoRegistro = MutableLiveData<Boolean>()


    fun registrarRutina(email: String, rutina: Routine) {
        routineRepository.registrarRutina(email, rutina) { exito ->
            exitoRegistro.value = exito
        }
    }

    fun obtenerIdRutinas(email: String, callback: (List<Pair<String, String>>?) -> Unit) {
        routineRepository.obtenerIDRutinas(email, callback)
    }
}
