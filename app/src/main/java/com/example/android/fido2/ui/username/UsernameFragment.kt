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

package com.example.android.fido2.ui.username

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.example.android.fido2.MainActivity
import com.example.android.fido2.databinding.UsernameFragmentBinding
import com.google.firebase.auth.FirebaseAuth

class UsernameFragment : Fragment() {

    private val viewModel: UsernameViewModel by viewModels()
    private lateinit var binding: UsernameFragmentBinding

    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UsernameFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
        auth = FirebaseAuth.getInstance()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.sending.observe(viewLifecycleOwner) { sending ->
            if (sending) {
                binding.sending.show()
            } else {
                binding.sending.hide()
            }
        }
        binding.inputUsername.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                viewModel.sendUsername()
                true
            } else {
                false
            }
        }
    }

   /* override fun onPause() {

        val noCheating = Intent(activity, MainActivity::class.java)
        startActivity(noCheating)
        activity?.finish()
        super.onPause()

    }*/
    /*override fun onStop() {

        val noCheating = Intent(activity, MainActivity::class.java)
        startActivity(noCheating)
        activity?.finish()
        super.onStop()
    }*/
    override fun onDestroy() {
        val noCheating = Intent(activity, MainActivity::class.java)
        startActivity(noCheating)
       activity?.finish()
       Toast.makeText(activity, "No puede salir de la aplicación hasta que genere sus credenciales", Toast.LENGTH_SHORT).show()
        super.onDestroy()

    }

}
