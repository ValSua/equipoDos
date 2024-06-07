import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.equipodos.model.Exercise
import com.example.equipodos.model.Routine
import com.example.equipodos.model.Usuario
import com.example.equipodos.repository.RoutineRepository

// UsuarioViewModel.kt
class RoutineViewModel : ViewModel() {

    private val routineRepository = RoutineRepository()
    val exitoRegistro = MutableLiveData<Boolean>()
    private val _routine = MutableLiveData<Routine?>()
    val routine: LiveData<Routine?> get() = _routine
    private val _updateSuccess = MutableLiveData<Boolean>()
    val updateSuccess: LiveData<Boolean> get() = _updateSuccess




    fun registrarRutina(email: String, nombreRutina: String, exercises: List<Exercise>) {
        val nuevaRutina = Routine(nombreRutina, exercises)
        routineRepository.registrarRutina(email, nuevaRutina) { success ->
            // Manejar el resultado de la operación, por ejemplo, actualizando la UI
            exitoRegistro.value = success
        }
    }


    fun obtenerRutina(email: String, key: Int) {
        routineRepository.obtenerRutina(email, key) { rutina ->
            _routine.value = rutina
        }
    }

    fun actualizarRutina(email: String, key: Int, nuevosEjercicios: List<Exercise>) {
        routineRepository.actualizarRutina(email, key, nuevosEjercicios) { success ->
            _updateSuccess.value = success
        }
    }

    
}
