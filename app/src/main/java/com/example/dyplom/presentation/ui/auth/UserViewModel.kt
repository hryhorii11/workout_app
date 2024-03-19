package com.example.dyplom.presentation.ui.auth
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dyplom.data.UIState
import com.example.dyplom.data.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val authRepository: UserRepository
) : ViewModel() {

    private val _isRegistered = MutableStateFlow<UIState<Unit>>(UIState.Idle())
    val isRegistered = _isRegistered.asStateFlow()

    val user=MutableLiveData<String?>()
    fun loginUser( email: String, password: String) {
        _isRegistered.value=UIState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signIn( email, password).collect{
                if(it.isFailure)
                    _isRegistered.value=UIState.Error(it.exceptionOrNull()?.message?:"Wrong email or password")
                else _isRegistered.value=UIState.Success(Unit)
            }
        }
    }
    fun createUser(name: String, email: String, password: String) {
        _isRegistered.value=UIState.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            authRepository.signUp(name, email, password).collect{
                if(it.isFailure)
                    _isRegistered.value=UIState.Error("")
                else _isRegistered.value=UIState.Success(Unit)
            }
        }
    }
    fun autoLogin()
    {

    }

}