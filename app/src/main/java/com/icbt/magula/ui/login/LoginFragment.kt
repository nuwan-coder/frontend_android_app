package com.icbt.magula.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.icbt.magula.data.db.AppDatabase
import com.icbt.magula.data.network.MyApi
import com.icbt.magula.data.repository.UserRepository
import com.icbt.magula.databinding.LoginFragmentBinding
import com.icbt.magula.ui.listner.AuthListner
import com.icbt.magula.ui.utils.hide
import com.icbt.magula.ui.utils.show
import com.icbt.magula.ui.utils.toast

class LoginFragment : Fragment(), AuthListner {

    private lateinit var viewModel:LoginViewModel
    private lateinit var binding:LoginFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val api = MyApi();
        val db = AppDatabase(requireContext())
        val repository = UserRepository(api,db)
        val factory = LoginViewModelFactory(repository,viewLifecycleOwner,requireActivity(),requireContext())

        viewModel = ViewModelProviders.of(this,factory).get(LoginViewModel::class.java)
        binding = LoginFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            loginViewModel = viewModel
        }
        binding.loginViewModel?.authListner = this
    }

    override fun onCreate() {
        binding.progressBar.show()
    }

    override fun onSuccess(message: String) {
        binding.progressBar.hide()
        context?.toast(message)
    }

    override fun onFailure(message:String) {
        binding.progressBar.hide()
        context?.toast(message)
    }


}