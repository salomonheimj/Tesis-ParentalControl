/*
 * Copyright 2019 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.fido2.ui.auth

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.android.fido2.MainActivity
import com.example.android.fido2.databinding.AuthFragmentBinding
import com.example.android.fido2.ui.observeOnce

class AuthFragment : Fragment() {

    companion object {
        private const val TAG = "AuthFragment"
    }

    private val viewModel: AuthViewModel by viewModels()
    private lateinit var binding: AuthFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AuthFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.processing.observe(viewLifecycleOwner) { processing ->
            if (processing) {
                binding.processing.show()
            } else {
                binding.processing.hide()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.signinIntent.observeOnce(this) { intent ->
            val a = activity
            if (intent.hasPendingIntent() && a != null) {
                try {
                    intent.launchPendingIntent(a, MainActivity.REQUEST_FIDO2_SIGNIN)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(TAG, "Error al iniciar sesión", e)
                }
            }
        }
    }

    fun handleSignin(data: Intent) {
        viewModel.signinResponse(data)
    }

    /*override fun onPause() {

        val noCheating = Intent(activity, MainActivity::class.java)
        startActivity(noCheating)
        activity?.finish()
        super.onPause()
    }*/

   /* override fun onStop() {

        val noCheating = Intent(activity, MainActivity::class.java)
        startActivity(noCheating)
        activity?.finish()
        super.onStop()
    }
*/
    override fun onDestroy() {
        val noCheating = Intent(activity, MainActivity::class.java)
        startActivity(noCheating)
       activity?.finish()
       Toast.makeText(activity, "No puede salir de la aplicación hasta que genere sus credenciales", Toast.LENGTH_SHORT).show()
        super.onDestroy()

    }

}
