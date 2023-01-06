package com.example.bitsdashboardassign.fragaments

import android.animation.Animator
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.bitsdashboardassign.databinding.FragmentCategoryBinding
import com.example.bitsdashboardassign.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.dashLottie.playAnimation()

        binding.dashLottie.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.welTxt.visibility = View.VISIBLE
                }, 2000)
            }

            override fun onAnimationEnd(p0: Animator?) {
                val direction = SplashFragmentDirections.actionSplashFragmentToCategoryFragment()
                findNavController().navigate(directions = direction)

            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationRepeat(p0: Animator?) {
            }
        })
    }

}