package com.example.weatherreport.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.weatherreport.R
import com.example.weatherreport.ui.base.BaseFragment
import com.example.weatherreport.databinding.FragmentLoginBinding
import com.example.weatherreport.ui.utils.Constants.REQUEST_CODE
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint

//oath token 537912438363-angq0a0h54h0eshotnphqkn7v8qfea5h.apps.googleusercontent.com

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {


    private lateinit var loginBinding: FragmentLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginBinding = FragmentLoginBinding.bind(view)
        initializeViews()
        setOnClickListeners()
    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in the GoogleSignInAccount will be non-null.

        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (account != null) {
            findNavController().navigate(
                R.id.weatherHomeFragment,
                bundleOf(
                    "profileUrl" to account.photoUrl.toString()
                )
            )
        }
    }

    private fun initializeViews() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }

    private fun setOnClickListeners() = with(loginBinding) {
        googleLoginIV.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == REQUEST_CODE) {
            // The Task returned from this call is always completed, no need to attach a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            Toast.makeText(requireContext(), "signed In successfully", Toast.LENGTH_SHORT).show()
            findNavController().navigate(
                R.id.weatherHomeFragment,
                bundleOf(
                    "profileUrl" to account.photoUrl.toString()
                )
            )
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.statusCode)
            Toast.makeText(requireContext(), "signed In failed", Toast.LENGTH_SHORT).show()
        }
    }

}